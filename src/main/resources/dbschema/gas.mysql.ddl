CREATE TABLE gas (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ts bigint(20) not NULL,
  amount int(8) NOT NULL,
  recordtype varchar(32) NOT NULL DEFAULT 'NORMAL'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE gas ADD INDEX(ts);

CREATE OR REPLACE VIEW gas_v AS
SELECT 
	id, 
	ts,
	from_unixtime(ts) as date_time,
	amount,
	recordtype
FROM gas;

