# NodeJS we're using
ARG NODE_VERSION=20

FROM node:${NODE_VERSION}-alpine AS builder

# Add the latest alpine repositories
RUN echo "http://dl-3.alpinelinux.org/alpine/latest-stable/main" > /etc/apk/repositories \
  && echo "http://dl-3.alpinelinux.org/alpine/latest-stable/community" >> /etc/apk/repositories \
  && apk update && apk upgrade --available --no-cache

RUN  apk update && apk upgrade \
  && apk add --no-cache --update --upgrade --virtual .build-deps-full \
    coreutils \
    bind-tools \
    git \
    unzip \
    wget \
    curl \
    gcompat \
    openjdk21-jdk \
    binutils \
  && rm -rf /var/cache/apk/*

ENV JAVA_HOME="/usr/lib/jvm/java-21-openjdk"

WORKDIR /signum-node

COPY . .

# fail early if missing either instead of waiting for gradle to use it
RUN node -v && npm -v

# Not needed as node is contained in the base image
RUN sed -i 's/download = true/download = false/g' /signum-node/build.gradle

# Build Signum Node Jar
RUN chmod +x /signum-node/gradlew \
  && /signum-node/gradlew clean dist jdeps \
    --no-daemon \
    -Pjdeps.recursive=true \
    -Pjdeps.ignore.missing.deps=true \
    -Pjdeps.print.module.deps=true

# Unpack the build to /signum
RUN unzip -o build/distributions/signum-node.zip -d /signum

# We use the bootstrap folder to copy the config files to the host machine in the start-node.sh script
ARG database=sqlite
ARG network=mainnet

# provide needed update scripts
COPY update-phoenix.sh /signum/update-phoenix.sh
COPY update-classic.sh /signum/update-classic.sh
COPY docker/scripts/start-node.sh /signum/start-node.sh

WORKDIR /signum
RUN mkdir ./bootstrap

COPY conf/logging-default.properties ./bootstrap/logging-default.properties
COPY conf/node-default.properties ./bootstrap/node-default.properties
COPY conf/${network}/node.${database}.properties ./bootstrap/node.properties

RUN chmod +x start-node.sh update-phoenix.sh update-classic.sh

# Clean up /signum
RUN rm -rf /signum/signum-node.exe 2> /dev/null || true \
  && rm -rf /signum/signum-node.zip 2> /dev/null || true

## Create a custom JRE
RUN $JAVA_HOME/bin/jlink \
  --add-modules $(cat /signum-node/build/reports/jdeps/print-module-deps-main.txt) \
  --strip-debug \
  --no-man-pages \
  --no-header-files \
  --compress=2 \
  --output /jre

# Copy the required libraries
RUN mkdir -p /requirements \
  && ldd /jre/bin/java | awk 'NF == 4 { system("cp --parents " $3 " /requirements") }'

# Prepare final image
FROM alpine:3.22
LABEL name="Signum Node"
LABEL description="This is the official Signum Node image"
LABEL credits="gittrekt,damccull,ohager"
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# minimum required env needed for wallet update scripts on container restarts -> see start-node.sh
RUN  apk update && apk upgrade \
  && apk add --no-cache --update coreutils bind-tools git unzip wget curl bash \
  && rm -rf /var/cache/apk/*

COPY --from=builder /jre $JAVA_HOME
COPY --from=builder /signum /signum
COPY --from=builder /requirements/ /

WORKDIR /signum

VOLUME ["/conf", "/db"]
RUN ln -s /conf /signum/conf && ln -s /db /signum/db

# Injectable ports defaulting to mainnet
ARG port_p2p=8123
ARG port_http=8125
ARG port_ws=8126

EXPOSE $port_ws $port_http $port_p2p

ENTRYPOINT [ "./start-node.sh" ]
