create table users (
    id int primary key generated by default as identity,
    email varchar(100) unique,
    password varchar(512),
    name varchar(50),
    employment varchar(100)
);

create table roles (
    id int primary key generated by default as identity,
    name varchar(15)
);
insert into roles (name) values ('ROLE_ADMIN'),
                                ('ROLE_OPERATOR'),
                                ('ROLE_CLIENT');

create table user_role (
    user_id int references users(id),
    role_id int references roles(id),
    primary key (user_id, role_id)
);

create table sessions (
    id bigint primary key generated by default as identity,
    user_id int references users(id),
    issued_at timestamp,
    expired_at timestamp,
    is_enable boolean
);

create table acl_sid(
    id bigint primary key generated by default as identity,
    principal boolean,
    sid varchar(100),
    constraint unique_uk_1 unique(sid, principal)
);

create table acl_class(
    id bigint primary key generated by default as identity,
    class varchar(500) unique,
    class_id_type varchar(500)
);

create table acl_object_identity(
    id bigint primary key generated by default as identity,
    object_id_class bigint references acl_class(id),
    object_id_identity varchar(36),
    parent_object bigint references acl_object_identity(id),
    owner_sid bigint references acl_sid(id),
    entries_inheriting boolean,
    constraint unique_uk_3 unique(object_id_class, object_id_identity)
);

create table acl_entry(
    id bigserial primary key,
    acl_object_identity bigint,
    ace_order int,
    sid bigint,
    mask integer,
    granting boolean,
    audit_success boolean,
    audit_failure boolean,
    constraint unique_uk_4 unique(acl_object_identity,ace_order),
    constraint foreign_fk_4 foreign key(acl_object_identity) references acl_object_identity(id),
    constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);


create table employees (
    id int references users(id) primary key,
    position varchar(50),
    manager_id int,
    foreign key (manager_id) references employees(id)
);

create table credit_tariffs (
    id int primary key generated by default as identity,
    type varchar(100),
    up_to_amount numeric(15, 2),
    up_to_credit_period int,
    credit_percent int
);
insert into credit_tariffs (type, up_to_amount, up_to_credit_period, credit_percent)
values ('t1', 1000000, 24, 16),
       ('t2', 2000000, 36, 16),
       ('t3', 3000000, 48, 15),
       ('t4', 4000000, 60, 15),
       ('t5', 5000000, 72, 14);

create table credit_requests (
    id bigint primary key generated by default as identity,
    credit_tariff_id int references credit_tariffs(id),
    credit_amount numeric(15, 2),
    credit_duration int,
    created_at timestamp,
    user_id int references users(id)
);

create table credits_scoring (
    score numeric(15, 2),
    credit_request_id bigint primary key references credit_requests(id),
    status varchar(10)
);

create table credits (
    id bigint primary key generated by default as identity,
    initial_amount numeric(15, 2),
    taking_date date,
    percent int,
    credit_period int,
    month_amount numeric(15, 2),
    credit_status varchar(10),
    user_id int references users(id),
    credit_tariff_id int references credit_tariffs(id)
);

create table balances (
    id bigint primary key generated by default as identity,
    credit_id int references credits(id),
    remaining_debt numeric(15, 2),
    remaining_month_debt numeric(15, 2),
    accrued_by_percent numeric(15, 2),
    created_at date
);

create table penalties (
    id bigint primary key generated by default as identity,
    credit_id bigint references credits(id),
    type varchar(20),
    amount numeric(15, 2),
    status varchar(10),
    created_at date
);

create table payments (
    id bigint primary key generated by default as identity,
    credit_id bigint references credits(id),
    amount numeric(15, 2),
    commission_amount decimal(15, 2),
    created_at timestamp
);

insert into users(email, password, name, employment)
values ('email1@gmail.com', 'password1', 'name1', 'employment1');

insert into user_role(user_id, role_id) values (1, 1),
                                               (1, 3);

insert into sessions(id, user_id, issued_at, expired_at, is_enable)
values ('1', '1', current_timestamp - interval '3 month', current_timestamp + interval '3 month', true);

truncate table credits cascade;
alter sequence credits_id_seq restart with 1;
