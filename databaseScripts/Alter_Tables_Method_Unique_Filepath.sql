ALTER TABLE `method`
	DROP COLUMN `filepath`;
ALTER TABLE `method`
	ADD COLUMN `filepath` varchar(255) NOT NULL UNIQUE;