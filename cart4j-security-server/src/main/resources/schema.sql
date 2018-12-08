create table if not exists c4_access_token
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

create table if not exists c4_client
(
  id bigint auto_increment
    primary key,
  client_unique_id varchar(64) null,
  client_secret varchar(128) null,
  grant_types varchar(255) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table if not exists c4_client_access_token
(
  client_id bigint not null,
  access_token_id bigint not null,
  constraint c4_client_access_token_client_id_access_token_id_pk
    unique (client_id, access_token_id),
  constraint c4_client_access_token_c4_client_id_fk
    foreign key (client_id) references c4_client (id)
      on update cascade on delete cascade,
  constraint c4_client_access_token_c4_access_token_id_fk
    foreign key (access_token_id) references c4_access_token (id)
      on update cascade on delete cascade
)
  engine=InnoDB
;

create index c4_client_access_token_c4_access_token_id_fk
on c4_client_access_token (access_token_id)
;

create table if not exists c4_client_role
(
  client_id bigint not null,
  role_id bigint not null,
  constraint client_id
    unique (client_id, role_id),
  constraint c4_client_role_c4_client_id_fk
    foreign key (client_id) references c4_client (id)
      on update cascade on delete cascade,
  constraint c4_client_role_c4_role_id_fk
    foreign key (role_id) references cart4j.c4_role (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_client_role_c4_role_id_fk
on c4_client_role (role_id)
;

create table if not exists c4_redirect_uri
(
  id           bigint auto_increment
    primary key,
  client_id    bigint       not null,
  redirect_uri varchar(255) null
)
  engine = InnoDB
  collate = utf8_unicode_ci;

create table if not exists c4_client_redirect_uri
(
  client_id bigint not null,
  redirect_uri_id bigint not null,
  constraint client_id
    unique (client_id, redirect_uri_id),
  constraint c4_client_redirect_uri_c4_client_id_fk
    foreign key (client_id) references c4_client (id)
      on update cascade on delete cascade,
  constraint c4_client_redirect_uri_c4_redirect_uri_id_fk
    foreign key (redirect_uri_id) references c4_redirect_uri (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_client_redirect_uri_c4_redirect_uri_id_fk
on c4_client_redirect_uri (redirect_uri_id)
;

create table if not exists c4_refresh_token
(
  id bigint auto_increment
    primary key,
  refresh_token_key varchar(255) null,
  refresh_token_value blob null,
  authentication_value blob null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table if not exists c4_resource
(
  id bigint auto_increment
    primary key,
  resource_unique_id varchar(255) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table if not exists c4_client_resource
(
  client_id bigint not null,
  resource_id bigint not null,
  constraint client_id
    unique (client_id, resource_id),
  constraint c4_client_resource_c4_client_id_fk
    foreign key (client_id) references c4_client (id)
      on update cascade on delete cascade,
  constraint c4_client_resource_c4_resource_id_fk
    foreign key (resource_id) references c4_resource (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_client_resource_c4_resource_id_fk
on c4_client_resource (resource_id)
;

create table if not exists c4_role
(
  id bigint auto_increment
    primary key,
  role varchar(64) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table if not exists c4_scope
(
  id bigint auto_increment
    primary key,
  scope varchar(64) null,
  description varchar(3000) null
)
  engine=InnoDB collate=utf8_unicode_ci
;

create table if not exists c4_client_scope
(
  client_id bigint not null,
  scope_id bigint not null,
  constraint client_id
    unique (client_id, scope_id),
  constraint c4_client_scope_c4_client_id_fk
    foreign key (client_id) references c4_client (id)
      on update cascade on delete cascade,
  constraint c4_client_scope_c4_scope_id_fk
    foreign key (scope_id) references c4_scope (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_client_scope_c4_scope_id_fk
on c4_client_scope (scope_id)
;

create table if not exists c4_user
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

create table if not exists c4_user_access_token
(
  access_token_id bigint not null,
  user_id bigint not null,
  constraint access_token_id
    unique (access_token_id, user_id),
  constraint c4_user_access_token_c4_access_token_id_fk
    foreign key (access_token_id) references c4_access_token (id)
      on update cascade on delete cascade,
  constraint c4_user_access_token_c4_user_id_fk
    foreign key (user_id) references c4_user (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_user_access_token_c4_user_id_fk
on c4_user_access_token (user_id)
;

create table if not exists c4_user_role
(
  user_id bigint not null,
  role_id bigint not null,
  constraint user_id
    unique (user_id, role_id),
  constraint c4_user_role_c4_user_id_fk
    foreign key (user_id) references c4_user (id)
      on update cascade on delete cascade,
  constraint c4_user_role_c4_role_id_fk
    foreign key (role_id) references cart4j.c4_role (id)
      on update cascade on delete cascade
)
  engine=InnoDB collate=utf8_unicode_ci
;

create index c4_user_role_c4_role_id_fk
on c4_user_role (role_id)
;