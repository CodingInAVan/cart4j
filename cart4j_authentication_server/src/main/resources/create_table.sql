create table c4_access_token
(
  id bigint auto_increment
    primary key,
  token_key varchar(255) null,
  token_value blob null,
  expiration_date datetime null,
  authentication_key varchar(255) null,
  authentication blob null,
  refresh_token_id bigint null,
  created_at datetime default CURRENT_TIMESTAMP null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_client
(
  id bigint auto_increment
    primary key,
  client_unique_id varchar(64) null,
  client_secret varchar(128) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_client_redirect_uri
(
  client_id bigint not null,
  redirect_uri_id bigint not null,
  constraint client_id
    unique (client_id, redirect_uri_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_client_resource
(
  client_id bigint not null,
  resource_id bigint not null,
  constraint client_id
    unique (client_id, resource_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_client_role
(
  client_id bigint not null,
  role_id bigint not null,
  constraint client_id
    unique (client_id, role_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_client_scope
(
  client_id bigint not null,
  scope_id bigint not null,
  constraint client_id
    unique (client_id, scope_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_refresh_token
(
  id bigint auto_increment
    primary key,
  refresh_token_key varchar(255) null,
  refresh_token_value blob null,
  authentication_value blob null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_resource
(
  id bigint auto_increment
    primary key,
  resource_unique_id varchar(255) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_role
(
  id bigint auto_increment
    primary key,
  role varchar(64) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_scope
(
  id bigint auto_increment
    primary key,
  scope varchar(64) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_user
(
  id bigint auto_increment
    primary key,
  username varchar(64) null,
  email varchar(255) null,
  password varchar(128) null,
  created_at datetime null,
  activated tinyint(1) default '1' null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_user_access_token
(
  access_token_id bigint not null,
  user_id bigint not null,
  constraint access_token_id
    unique (access_token_id, user_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table c4_user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint user_id
    unique (user_id, role_id)
)
  engine=InnoDB collate=utf8_unicode_ci
;

