CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(70) NOT NULL UNIQUE
);

INSERT IGNORE INTO category (name)
VALUES ('Education'),
       ('Food'),
       ('Health'),
       ('Rent'),
       ('Other'),
       ('Recreation'),
       ('Transportation'),
       ('Unexpected expense');
