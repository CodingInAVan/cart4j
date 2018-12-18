create table cart4j_product.c4_product
(
  id                     bigint auto_increment
    primary key,
  sku                    varchar(64)  not null,
  name                   varchar(255) not null,
  barcode                varchar(64)  not null,
  product_description_id bigint       null,
  product_size_id        bigint       null,
  main_image             varchar(255) null,
  vendor_id              bigint       null,
  tax_class_id           int          null,
  available_at           datetime     null,
  weight_id              bigint       null,
  activated              tinyint(1)   default 1,
  constraint c4_product_product_description_fk
    foreign key (product_description_id) references cart4j.c4_product_description (id),
  constraint c4_product_c4_product_size_id_fk
    foreign key (product_size_id) references cart4j.c4_product_size (id),
  constraint c4_product_c4_product_weight_id_fk
    foreign key (weight_id) references cart4j.c4_product_weight (id)
)
  engine = InnoDB
  collate = utf8_unicode_ci;

create index c4_product_c4_product_size_id_fk
on cart4j_product.c4_product (product_size_id);

create index c4_product_c4_product_weight_id_fk
on cart4j_product.c4_product (weight_id);

create index c4_product_product_description_fk
on cart4j_product.c4_product (product_description_id);

create table c4_product_description
(
  id               bigint auto_increment primary key,
  description      mediumtext   null,
  meta_title       varchar(255) null,
  meta_description varchar(255) null,
  meta_keyword     varchar(255) null
)
  engine = InnoDB
  collate = utf8_unicode_ci;

create table c4_product_size
(
  id     bigint auto_increment
    primary key,
  length decimal(15, 8) null,
  width  decimal(15, 8) null,
  height decimal(15, 8) null,
  unit   varchar(10)    null
)
  engine = InnoDB
  collate = utf8_unicode_ci;

create table c4_product_tag
(
  id         bigint auto_increment
    primary key,
  product_id bigint       not null,
  tag_key    varchar(60)  null,
  tag_value  varchar(255) null,
  constraint c4_product_tag_c4_product_id_fk
    foreign key (product_id) references c4_product (id)
)
  engine = InnoDB
  collate = utf8_unicode_ci;

create index c4_product_tag_c4_product_id_fk
on c4_product_tag (product_id);

create table c4_product_weight
(
  id     bigint auto_increment
    primary key,
  weight decimal(15, 8) null,
  unit   varchar(10)    null
)
  engine = InnoDB
  collate = utf8_unicode_ci;





