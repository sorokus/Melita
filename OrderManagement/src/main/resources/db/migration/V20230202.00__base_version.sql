create table public.product
(
    id          serial
        constraint product_pk
            primary key,
    name        varchar not null,
    description varchar
);

alter table public.product
    owner to postgres;

create table public.package
(
    id          serial,
    name        varchar not null,
    product_id  serial
        constraint package_fk
            references public.product,
    description varchar
);

alter table public.package
    owner to postgres;

