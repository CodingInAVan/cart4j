create table c4_cart
(
	id bigint auto_increment
		primary key,
	session text not null,
	username varchar(64) null,
	account_id bigint not null,
	added_at datetime not null
)
engine=InnoDB charset=utf8
;

create table c4_cart_item
(
  id         bigint auto_increment
    primary key,
  cart_id    bigint   not null,
  product_id bigint   null,
  quantity   int(5)   not null,
  `option`   text     not null,
  added_at   datetime not null
)
  engine = InnoDB
  charset = utf8;
