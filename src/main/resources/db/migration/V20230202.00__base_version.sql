create table public.product
(
    id          serial
        constraint product_pk primary key,
    name        varchar not null,
    description varchar
);

alter table public.product
    owner to postgres;

create table public.package
(
    id          serial
        constraint package_pk primary key,
    name        varchar not null,
    description varchar,
    product_id  int4
        constraint package_fk references public.product (id)
);

alter table public.package
    owner to postgres;

