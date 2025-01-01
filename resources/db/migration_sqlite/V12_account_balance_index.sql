DROP INDEX IF EXISTS aaccount_asset_assetid_idx ON account_asset;
CREATE INDEX IF NOT EXISTS account_asset_assetid_latest_idx ON account_asset (account_id, asset_id, latest);