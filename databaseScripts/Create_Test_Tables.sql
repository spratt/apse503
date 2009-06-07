/* Just a temporary table for mocking credit card validation */
CREATE TABLE `credit_cards` (
  `card_id` int(11) NOT NULL AUTO_INCREMENT,
  `card_type` varchar(11) NOT NULL,
  `card_number` varchar(16) NOT NULL UNIQUE,
  `card_holder_name` varchar(32) NOT NULL,
  `card_expiry_month` int(1) NOT NULL,
  `card_expiry_year` int(4) NOT NULL,
  `card_code` int(2) NOT NULL,
  PRIMARY KEY (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
