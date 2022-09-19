create table product (id bigint not null, addition_description varchar(255), cost integer, count_stock integer,
                      id_type integer not null, manufacturer varchar(255), primary key (id));
create table type (id integer not null AUTO_INCREMENT, name_description varchar(255)  , name_type varchar(255),
                   description_features varchar(255), primary key (id));
create sequence hibernate_sequence start with 1 increment by 1;
alter table product add constraint FKrhxj6mgk7eb68xd7ln6epbvk9 foreign key (id_type) references type
INSERT INTO type(name_description,name_type,description_features) VALUES('Форм-фактор', 'настольный компьютер','{variants:[десктоп,неттоп,моноблок]}');
INSERT INTO type(name_description,name_type,description_features) VALUES('Размер', 'ноутбук','{variants:[13,14,15,17]}');
INSERT INTO type(name_description,name_type,description_features) VALUES('Диагональ', 'монитор','{type:float}');
INSERT INTO type(name_description,name_type,description_features) VALUES('Объём', 'жёсткий диск','{type:float}');