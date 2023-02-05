CREATE TABLE public.address
(
    id          serial  NOT NULL,
    province    varchar NOT NULL,
    city        varchar NOT NULL,
    street      varchar NOT NULL,
    apartment   varchar NOT NULL,
    postal_code varchar NOT NULL,
    CONSTRAINT address_pk PRIMARY KEY (id)
);

alter table public.address
    owner to postgres;

CREATE TABLE public.person
(
    id          serial  NOT NULL,
    first_name  varchar NOT NULL,
    last_name   varchar NOT NULL,
    mobile_no   varchar NOT NULL,
    email       varchar NOT NULL,
    passport_no varchar NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (id)
);

alter table public.person
    owner to postgres;

CREATE TABLE public."order"
(
    id             serial    NOT NULL,
    submitted_at   timestamp NOT NULL DEFAULT now(),
    approved_at    timestamp NULL,
    approved_by    varchar   NULL,
    pref_date_from timestamp NULL,
    pref_date_to   timestamp NULL,
    address_id     int       NOT NULL,
    person_id      int       NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id),
    CONSTRAINT order_fk FOREIGN KEY (person_id) REFERENCES public.person (id),
    CONSTRAINT order_fk_1 FOREIGN KEY (address_id) REFERENCES public.address (id)
);

alter table public."order"
    owner to postgres;

CREATE TABLE public.order_package
(
    id         serial NOT NULL,
    order_id   int    NOT NULL,
    package_id int    NOT NULL,
    CONSTRAINT order_package_pk PRIMARY KEY (id),
    CONSTRAINT order_package_fk FOREIGN KEY (package_id) REFERENCES public.package (id),
    CONSTRAINT order_package_fk_1 FOREIGN KEY (order_id) REFERENCES public."order" (id)
);

alter table public.order_package
    owner to postgres;
