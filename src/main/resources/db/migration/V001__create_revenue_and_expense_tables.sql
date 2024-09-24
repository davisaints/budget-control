CREATE TABLE
    IF NOT EXISTS revenue (
        id SERIAL PRIMARY KEY,
        description VARCHAR(70) NOT NULL,
        amount NUMERIC(19, 4) NOT NULL,
        transaction_date DATE
    );

CREATE TABLE
    IF NOT EXISTS expense (
        id SERIAL PRIMARY KEY,
        description VARCHAR(70) NOT NULL,
        amount NUMERIC(19, 4) NOT NULL,
        transaction_date DATE
    );