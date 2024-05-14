CREATE TABLE IF NOT EXISTS revenue (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(20),
    description varchar(70),
    date timestamp
    );

CREATE TABLE IF NOT EXISTS expense (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(20),
    description` varchar(70),
    date timestamp
    );