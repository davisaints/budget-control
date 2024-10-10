CREATE TABLE
    IF NOT EXISTS category (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(70) NOT NULL UNIQUE
    );

MERGE INTO category (name) KEY (name)
VALUES
    ('Education'),
    ('Food'),
    ('Health'),
    ('Rent'),
    ('Other'),
    ('Recreation'),
    ('Transportation'),
    ('Unexpected expense');