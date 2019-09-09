# --- !Ups

create table "SHOPPING-ITEMS" ("ID" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR(254) NOT NULL,"orderDate" VARCHAR(254) NOT NULL, "boughtDate" VARCHAR(254) NOT NULL);

# --- !Downs

drop table "SHOPPING-ITEMS";