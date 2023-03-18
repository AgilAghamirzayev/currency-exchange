CREATE TABLE currency_info
(
    id           SERIAL PRIMARY KEY,
    date         VARCHAR(50),
    base         VARCHAR(50),
    description  VARCHAR(500),
    created_date TIMESTAMP
);


CREATE TABLE currency
(
    id               SERIAL PRIMARY KEY,
    nominal          VARCHAR(10),
    code             VARCHAR(10),
    name             VARCHAR(50),
    value            NUMERIC(19, 4),
    currency_info_id BIGINT REFERENCES currency_info (id)
);


ALTER TABLE currency
    ADD CONSTRAINT currency_info_fk FOREIGN KEY (currency_info_id) REFERENCES currency_info (id);
