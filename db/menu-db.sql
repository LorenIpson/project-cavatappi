set search_path to menu_db;
create schema drink_schema;

set search_path to drink_schema;

create table drink
(
    id          bigserial primary key,
    name        text           not null,
    description text,
    image       bytea,
    base_price  decimal(10, 2) not null,
    stocked     boolean        not null default true,
    available   boolean        not null default true -- false 則隱藏餐點
);

create table drink_size
(
    id          bigserial primary key,
    drink_id    bigint references drink (id),
    size        text           not null, -- S, M, L
    extra_price decimal(10, 2) not null, -- S = 0, M = +5, L = +10
    unique (drink_id, size)
);

create table drink_addon
(
    id        bigserial primary key,
    drink_id  bigint references drink (id),
    name      text           not null,
    price     decimal(10, 2) not null,
    stocked   boolean        not null default true,
    available boolean        not null default true
);

create table drink_addon_size_price
(
    id          bigserial primary key,
    addon_id    bigint references drink_addon (id),
    size        text           not null,
    extra_price decimal(10, 2) not null,
    unique (addon_id, size)
);

create schema pizza_schema;

set search_path to pizza_schema;

create table pizza
(
    id          bigserial primary key,
    name        text           not null,
    description text,
    image       bytea,
    base_price  decimal(10, 2) not null,
    stocked     bool           not null default true,
    available   bool           not null default true
);

create table pizza_size
(
    id          bigserial primary key,
    pizza_id    bigint references pizza (id),
    size        text           not null, -- S, M, L or 6, 9, 12 inches
    extra_price decimal(10, 2) not null,
    unique (pizza_id, size)
);

create table pizza_dough
(
    id          bigserial primary key,
    pizza_id    bigint references pizza (id),
    dough       text           not null,
    extra_price decimal(10, 2) not null
);

create table pizza_addon
(
    id       bigserial primary key,
    pizza_id bigint references pizza (id),
    name     text not null
);

create table pizza_addon_size_price
(
    id          bigserial primary key,
    addon_id    bigint references pizza_addon (id),
    size        text           not null,
    extra_price decimal(10, 2) not null,
    unique (addon_id, size)
);