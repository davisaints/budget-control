ALTER TABLE expense ADD COLUMN category_id INT NOT NULL;

ALTER TABLE revenue ADD COLUMN category_id INT NOT NULL;

ALTER TABLE expense ADD CONSTRAINT fk_expense_category FOREIGN KEY (category_id) REFERENCES category(id);

ALTER TABLE revenue ADD CONSTRAINT fk_revenue_category FOREIGN KEY (category_id) REFERENCES category(id);
