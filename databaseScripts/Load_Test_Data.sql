/*Insert statements for credit_cards */
INSERT INTO credit_cards VALUES (1, 'Visa', '1234567890123456', 'Foo Bar', '02', '2010', '666');
INSERT INTO credit_cards VALUES (2, 'MasterCard', '6543210987654321', 'Foo Bar', '02', '2010', '666');
select * from credit_cards;

/*Insert static data for testing methods */
INSERT INTO user 
	VALUES (101,'Foo','Bar','123 4th Avenue','H0H0H0','Calgary','Alberta','Canada','foo@bar.com',NOW(),'foo','testing','123456');
INSERT INTO method 
	VALUES (102,'testPrintStringMethod', 101, 1, 1, 'Awesomesauce', 'The best method evar',NOW(),'apse503/useMethod/?methodID=1');
INSERT INTO method_price VALUES (103,NOW(),9.99,10,102,1);

/*Insert dynamic data for testing methods */
INSERT INTO method_purchase (date_time,paid_developer,method_id,user_id,method_price_id) 
	VALUES (NOW(),0,102,101,103);
INSERT INTO method_purchase (date_time,paid_developer,method_id,user_id,method_price_id) 
	VALUES (NOW(),0,102,101,103);
INSERT INTO method_use (date_time,method_id,user_id) 
	VALUES (NOW(),102,101);
INSERT INTO method_use (date_time,method_id,user_id) 
	VALUES (NOW(),102,101);
	
/* Get the uses, should return 2 */
select 
	count(*) as used 
from method_use 
where user_id=101;

/* Get the total quantity purchased, should return 20 */
select
	user_id,
	method_purchase.method_id,
	sum(quantity) as purchased
from method_purchase
inner join method_price
on method_purchase.method_price_id = method_price.method_price_id
where user_id=101;