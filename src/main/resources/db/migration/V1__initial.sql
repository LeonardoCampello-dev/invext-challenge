CREATE TABLE attendant (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   department VARCHAR(255) NOT NULL,
   CONSTRAINT pk_attendant PRIMARY KEY (id)
);

CREATE TABLE customer (
  id INT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE ticket (
  id INT AUTO_INCREMENT NOT NULL,
   department VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL,
   title VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NOT NULL,
   created_at datetime NOT NULL,
   updated_at datetime NULL,
   closed_at datetime NULL,
   assignee_id INT NULL,
   customer_id INT NOT NULL,
   CONSTRAINT pk_ticket PRIMARY KEY (id)
);

ALTER TABLE ticket ADD CONSTRAINT FK_TICKET_ON_ASSIGNEE FOREIGN KEY (assignee_id) REFERENCES attendant (id);

ALTER TABLE ticket ADD CONSTRAINT FK_TICKET_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);