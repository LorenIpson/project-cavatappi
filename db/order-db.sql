create schema order_schema;

create table "order"
(
    id             bigserial primary key,
    member_id      uuid           not null,
    username       text           not null,
    buyer_name     text           not null,
    buyer_phone    text           not null,
    buyer_message  text,
    ordered_date   timestamp      not null,
    receive_date   timestamp      not null,
    is_edited      bool           not null default false,
    edited_at      timestamp,
    order_status   text           not null,
    payment_status text           not null,
    total_price    decimal(10, 2) not null
);

create table order_details
(
    id              bigserial primary key,
    order_id        bigint         not null references "order" (id),
    item_id         bigint         not null,
    item_name       text           not null,
    item_specs      jsonb,
    item_addons     jsonb,
    item_base_price decimal(10, 2) not null
);

create table order_payment
(
    id             bigint references "order" (id) primary key,
    payment_method text           not null,
    transaction_id text,
    provider       text,
    payment_time   timestamp,
    total_price    decimal(10, 2) not null
);