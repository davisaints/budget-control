CREATE TABLE IF NOT EXISTS revenue (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(70) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    transaction_date DATE
);

CREATE TABLE IF NOT EXISTS expense (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(70) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    transaction_date DATE
);
