DELIMITER $$
CREATE TRIGGER insert_entity
AFTER UPDATE ON stock_amount FOR EACH ROW
BEGIN

	SET @change_info = '';
	elseif OLD.available = 1 AND NEW.available = 0 THEN
		SET @change_info = 'SOLD_OUT';
	elseif OLD.amount < NEW.amount THEN
		SET @change_info = 'PROVIDED';
	elseif OLD.amount > NEW.amount THEN
		SET @change_info = 'SOLD';
	elseif OLD.available = 0 AND NEW.available = 1 THEN
		SET @change_info = 'PROVIDED';
	END IF;

	INSERT INTO stock_amount_change (stock_amount_stock_amount_id, previous_amount, current_amount, change_date_time, change_info)
	VALUES (OLD.stock_amount_id, OLD.amount, NEW.amount, NOW(), @change_info);
END
$$
DELIMITER;