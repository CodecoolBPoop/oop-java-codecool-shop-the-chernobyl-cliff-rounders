CREATE TABLE customer (
   id serial  NOT NULL,
   name varchar(255)  NOT NULL,
   email varchar(255)  NOT NULL,
   password varchar(100)  NOT NULL,
   CONSTRAINT customer_pk PRIMARY KEY (id)
);

CREATE TABLE "order" (
   id serial  NOT NULL,
   custumer_id int  NOT NULL,
   CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE TABLE purchase_item (
   id serial  NOT NULL,
   order_id int  NOT NULL,
   product_id int  NOT NULL,
   number_of_items int  NOT NULL,
   CONSTRAINT purchase_item_pk PRIMARY KEY (id)
);

CREATE TABLE product (
   id serial  NOT NULL,
   name varchar(255)  NOT NULL,
   default_price numeric  NOT NULL,
   currency varchar(255)  NOT NULL,
   description varchar(255)  NOT NULL,
   product_category_id int  NOT NULL,
   supplier_id int  NOT NULL,
   CONSTRAINT product_pk PRIMARY KEY (id)
);

CREATE TABLE product_category (
   id serial  NOT NULL,
   name varchar(255)  NOT NULL,
   department varchar(255)  NOT NULL,
   description varchar(255)  NOT NULL,
   CONSTRAINT product_category_pk PRIMARY KEY (id)
);

CREATE TABLE supplier (
   id serial  NOT NULL,
   name varchar(255)  NOT NULL,
   description varchar(255)  NOT NULL,
   CONSTRAINT supplier_pk PRIMARY KEY (id)
);