CREATE TABLE
    IF NOT EXISTS category (
        id SERIAL PRIMARY KEY,
        name VARCHAR(70) NOT NULL UNIQUE
    );

INSERT INTO
    category (name)
VALUES
    ('Education'),
    ('Food'),
    ('Health'),
    ('Rent'),
    ('Other'),
    ('Recreation'),
    ('Transportation'),
    ('Unexpected expense') ON CONFLICT (name) DO NOTHING;