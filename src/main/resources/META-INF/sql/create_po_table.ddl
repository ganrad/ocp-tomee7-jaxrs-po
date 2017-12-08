CREATE TABLE po_table 
(
	id 		INT unsigned NOT NULL AUTO_INCREMENT,
	item 		VARCHAR(50) NOT NULL, 
	price 		DECIMAL(5,2) NOT NULL, 
	quantity 	INT NOT NULL, 
	description 	VARCHAR(50), 
	cname 		VARCHAR(35) NOT NULL, 
	dcode 		VARCHAR(15), 
	origin 		VARCHAR(25),
	PRIMARY KEY	(id)
);
