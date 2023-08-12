DROP TABLE if EXISTS item_card_details;
DROP TABLE if EXISTS employee_issue_details;
DROP TABLE if EXISTS employee_card_details;
DROP TABLE if EXISTS item_master;
DROP TABLE if EXISTS loan_card_master;
DROP TABLE if EXISTS employee_master;

CREATE TABLE IF NOT EXISTS employee_master(
employee_id VARCHAR(36) PRIMARY KEY,
employee_name varchar(20) NOT NULL,
designation VARCHAR(25),
department varchar(25),
gender CHAR(1),
dob DATE,
doj DATE,
isAdmin char(1) NOT NULL DEFAULT '0');

CREATE TABLE if NOT EXISTS loan_card_master(
loan_type_id VARCHAR(36) PRIMARY KEY,
loan_type VARCHAR(15) NOT NULL DEFAULT 'household',
duration_in_years INT(2));

CREATE TABLE if NOT EXISTS item_master(
item_id VARCHAR(36) PRIMARY KEY,
item_description VARCHAR(25),
issue_status CHAR(1) NOT NULL DEFAULT '0',
item_make VARCHAR(25),
item_category VARCHAR(20),
item_valuation INT(6));

CREATE TABLE if NOT EXISTS employee_card_details(
employee_id VARCHAR(36),
loan_type_id VARCHAR(36) ,
card_issue_date DATE,
CONSTRAINT card_employee_constraint FOREIGN KEY(employee_id) REFERENCES employee_master(employee_id),
CONSTRAINT loan_employee_constraint FOREIGN KEY(loan_type_id) REFERENCES loan_card_master(loan_type_id));


CREATE TABLE if NOT exists employee_issue_details(
issue_id VARCHAR(36) PRIMARY KEY,
employee_id VARCHAR(36),
item_id VARCHAR(36),
issue_date DATE NOT null,
return_date DATE NOT NULL,
CONSTRAINT employee_item_constraint FOREIGN KEY(employee_id) REFERENCES employee_master(employee_id),
CONSTRAINT item_issue_constraint FOREIGN KEY(item_id) REFERENCES item_master(item_id)
);

CREATE TABLE if NOT EXISTS item_card_details(
item_id VARCHAR(36),
loan_type_id VARCHAR(36),
CONSTRAINT item_loan_card_constraint_1 FOREIGN KEY(item_id) REFERENCES item_master(item_id),
CONSTRAINT item_loan_card_constraint_2 FOREIGN KEY(loan_type_id) REFERENCES loan_card_master(loan_type_id));
