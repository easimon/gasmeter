CREATE TABLE `smarthome`.`gas_raw` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `ts` datetime not null,
  `amount` int(8) NOT NULL,
  `correction` varchar(32) NOT NULL DEFAULT 'NORMAL'
);

-- indexes missing!

/*
CREATE TABLE smarthome.gas (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,  
  year int(4) unsigned NOT NULL,
  month int(2) unsigned NOT NULL,
  day int(2) unsigned NOT NULL,
  hour int(2) unsigned NOT NULL,
  minute int(2) unsigned NOT NULL,
  second int(2) unsigned NOT NULL,
  amount int(8) NOT NULL,
  correction varchar(32) NOT NULL DEFAULT 'normal'
);
*/

CREATE VIEW `smarthome`.`gas` AS
SELECT 
	`id`, 
	`ts`, 
	YEAR(`ts`) as `year`, 
	MONTH(`ts`) as `month`, 
	DAY(`ts`) as `day`, 
	HOUR(`ts`) as `hour`,
	MINUTE(`ts`) as `minute`,
	SECOND(`ts`) as `second`,
	`amount`,
	`correction`
FROM `smarthome`.`gas_raw`;

