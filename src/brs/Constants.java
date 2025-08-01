package brs;

import java.util.Calendar;
import java.util.TimeZone;

public final class Constants {

  public static final String SIGNUM_NETWORK_NAME = "Signum";

  public static final int SIGNUM_DIFF_ADJUST_CHANGE_BLOCK = 2700;

  public static final long SIGNA_REWARD_RECIPIENT_ASSIGNMENT_WAIT_TIME = 4;

  // not sure when these were enabled, but they each do an alias lookup every block if greater than the current height
  public static final long SIGNUM_ESCROW_START_BLOCK = 0;
  public static final long SIGNUM_SUBSCRIPTION_START_BLOCK = 0;
  public static final int SIGNUM_SUBSCRIPTION_MIN_FREQ = 3600;
  public static final int SIGNUM_SUBSCRIPTION_MAX_FREQ = 31536000;

  public static final int BLOCK_HEADER_LENGTH = 232;

  public static final long MAX_BALANCE_SIGNA = 2158812800L;

  public static final long FEE_QUANT_SIP3 = 735_000;
  public static final long FEE_QUANT_SIP34 = 1_000_000;
  public static final long ONE_SIGNA = 100000000;
  public static final long INITIAL_COMMITMENT = 1000 * ONE_SIGNA;
  public static final int COMMITMENT_WAIT = 60;

  public static final long MAX_BALANCE_NQT = MAX_BALANCE_SIGNA * ONE_SIGNA;
  public static final long INITIAL_BASE_TARGET = 18325193796L;
  public static final int CAPACITY_ESTIMATION_BLOCKS_MAX = 10800 * 3;
  public static final int CAPACITY_ESTIMATION_BLOCKS = 360;
  public static final int CAPACITY_ESTIMATION_BLOCKS_MID = 360 * 4;
  public static final long MAX_BASE_TARGET = 18325193796L;
  public static final int MAX_ROLLBACK = 1440;

  public static final int ORDINARY_TRANSACTION_BYTES = 176;

  public static final int MAX_ALIAS_URI_LENGTH = 1000;
  public static final int MAX_ALIAS_LENGTH = 100;
  public static final int MAX_TLD_LENGTH = 40;

  public static final int MAX_ARBITRARY_MESSAGE_LENGTH = 1000;
  public static final int MAX_ENCRYPTED_MESSAGE_LENGTH = 1000;

  public static final int MAX_MULTI_OUT_RECIPIENTS = 64;
  public static final int MAX_MULTI_SAME_OUT_RECIPIENTS = 128;

  public static final int MAX_MULTI_ASSET_IDS = 4;

  public static final int MAX_ACCOUNT_NAME_LENGTH = 100;
  public static final int MAX_ACCOUNT_DESCRIPTION_LENGTH = 1000;

  public static final long MAX_ASSET_QUANTITY_QNT = 1000000000L * 100000000L;
  public static final long ASSET_ISSUANCE_FEE_NQT = 1000 * ONE_SIGNA;
  public static final int MIN_ASSET_NAME_LENGTH = 3;
  public static final int MAX_ASSET_NAME_LENGTH = 10;
  public static final int MAX_ASSET_DESCRIPTION_LENGTH = 1000;
  public static final int MAX_ASSET_TRANSFER_COMMENT_LENGTH = 1000;

  public static final int MAX_DGS_LISTING_QUANTITY = 1000000000;
  public static final int MAX_DGS_LISTING_NAME_LENGTH = 100;
  public static final int MAX_DGS_LISTING_DESCRIPTION_LENGTH = 1000;
  public static final int MAX_DGS_LISTING_TAGS_LENGTH = 100;
  public static final int MAX_DGS_GOODS_LENGTH = 10240;


  public static final int MAX_AUTOMATED_TRANSACTION_NAME_LENGTH = 30;
  public static final int MAX_AUTOMATED_TRANSACTION_DESCRIPTION_LENGTH = 1000;


  /**
   * Must be 1 or greater.
   */
  public static final int MAX_API_RETURNED_ITEMS = 500;

  public static final String HTTP = "http://";

  static final long UNCONFIRMED_POOL_DEPOSIT_NQT = 100 * ONE_SIGNA;

  // TODO burstkit4j integration
  public static final long EPOCH_BEGINNING;

  static {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    calendar.set(Calendar.YEAR, 2014);
    calendar.set(Calendar.MONTH, Calendar.AUGUST);
    calendar.set(Calendar.DAY_OF_MONTH, 11);
    calendar.set(Calendar.HOUR_OF_DAY, 2);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    EPOCH_BEGINNING = calendar.getTimeInMillis();
  }

  public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
  public static final String ALPHABET_ = ALPHABET + "_";

  public static final int EC_RULE_TERMINATOR = 2400; /* cfb: This constant defines a straight edge when "longest chain"
                                                        rule is outweighed by "economic majority" rule; the terminator
                                                        is set as number of seconds before the current time. */

  public static final int EC_BLOCK_DISTANCE_LIMIT = 60;
  public static final int EC_CHANGE_BLOCK_1 = 67000;

  public static final String RESPONSE = "response";
  public static final String TOKEN = "token";
  public static final String WEBSITE = "website";
  public static final String PROTOCOL = "protocol";

  public static final byte[] AT_PUBLIC_KEY_BYTES = new byte[32];;

  private Constants() {
  } // never

}
