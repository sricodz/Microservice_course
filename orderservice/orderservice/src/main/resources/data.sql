-- Inserting sample orders (let DB generate ID)
INSERT INTO orders (user_id, total_amount, status, create_at, updated_at)
VALUES
  ('john.doe@example.com', 150.00, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('jane.smith@example.com', 300.00, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('bob.davis@example.com', 450.00, 'DELIVERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Manually inserting order items requires knowing the correct order IDs
-- Assuming IDs inserted are: 1, 2, 3 in that order (most embedded DBs will assign in sequence)

INSERT INTO order_item (product_id, quantity, price, order_id)
VALUES
  ('1', 2, 50.00, 1),
  ('2', 1, 50.00, 1),
  ('3', 3, 100.00, 2),
  ('4', 2, 150.00, 3),
  ('5', 1, 150.00, 3);

-- Cart items
INSERT INTO cart_item (user_id, product_id, quantity, price, created_at, udpated_at)
VALUES
  ('bob.davis@example.com', '2', 2, 75.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('jane.smith@example.com', '3', 1, 120.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('bob.davis@example.com', '4', 4, 50.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
