/*Insert statements for credit_cards */
INSERT INTO credit_cards VALUES (1, 'Visa', '1234567890123456', 'John Smith', '02', '2010', '123');
INSERT INTO credit_cards VALUES (2, 'MasterCard', '6543210987654321', 'John Smith', '02', '2010', '123');
select * from credit_cards;

/*Insert static data for testing methods */
INSERT INTO user 
	VALUES (101,'Developer','Software','123 4th Avenue','H0H0H0','Calgary','Alberta','Canada','dev@software.com',NOW(),'dev','testing','123456');
INSERT INTO user 
	VALUES (102,'User','Smith','123 4th Avenue','H0H0H0','Calgary','Alberta','Canada','user@smith.com',NOW(),'user','testing','123456');