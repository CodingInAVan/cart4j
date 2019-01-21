create table cart4j_cart.c4_cart
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

