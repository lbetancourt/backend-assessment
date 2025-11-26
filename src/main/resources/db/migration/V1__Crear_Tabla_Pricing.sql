create table pricing (
    id varchar(7) PRIMARY KEY,
    date_from timestamp without time zone,
    date_to timestamp without time zone,
    duration INTEGER,
    price numeric(10, 2) not null check (price > 0)
);