ALTER TABLE expense
ADD COLUMN category_id INT NOT NULL;

ALTER TABLE expense ADD CONSTRAINT fk_expense_category FOREIGN KEY (category_id) REFERENCES category (id);