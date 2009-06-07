/*Insert statements for category*/
INSERT INTO category (category_id, category) VALUES (1, 'Finance');
INSERT INTO category (category_id, category) VALUES (2, 'Accounting');
INSERT INTO category (category_id, category) VALUES (3, 'Entertainment');
INSERT INTO category (category_id, category) VALUES (4, 'Travel');
INSERT INTO category (category_id, category) VALUES (5, 'GEO Mapping');
INSERT INTO category (category_id, category) VALUES (6, 'Oil & Gas');
select * from category;

/*Insert statements for status */
INSERT INTO status (status_id, status) VALUES (1, 'Active');
INSERT INTO status (status_id, status) VALUES (2, 'Pending Approval');
INSERT INTO status (status_id, status) VALUES (3, 'Depricated');
select * from status;

/*Insert statements for method_price_status */
INSERT INTO method_price_status (method_price_status_id, status) VALUES (1, 'Active');
INSERT INTO method_price_status (method_price_status_id, status) VALUES (2, 'Inactive');
select * from method_price_status;