DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM information_schema.columns
            WHERE table_name = 'expense' AND column_name = 'category_id'
        ) THEN
            ALTER TABLE expense
                ADD COLUMN category_id INT NOT NULL;

            ALTER TABLE expense
                ADD CONSTRAINT fk_expense_category FOREIGN KEY (category_id) REFERENCES category (id);
        END IF;
    END $$;
