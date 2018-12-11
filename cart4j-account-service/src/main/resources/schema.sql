create table cart4j_account.c4_account (
  id bigint not null primary key auto_increment,
  account_unique_id varchar(64) not null,
  account_name varchar(128) not null,
  description text,
  status tinyint(2)
) ENGINE=Innodb DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

create table cart4j_account.c4_account_user (
  id bigint not null primary key auto_increment,
  account_id bigint not null,
  username varchar(64) not null
) ENGINE=Innodb DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;