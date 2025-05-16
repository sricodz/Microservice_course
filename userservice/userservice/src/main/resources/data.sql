-- Insert Users (no 'id' field, as it's auto-generated)
INSERT INTO users (first_name, last_name, email, phone, role, created_at, updated_at)
VALUES
  ('John', 'Doe', 'john.doe@example.com', '1234567890', 'CUSTOMER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Jane', 'Smith', 'jane.smith@example.com', '0987654321', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Alice', 'Brown', 'alice.brown@example.com', '1122334455', 'CUSTOMER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Bob', 'Davis', 'bob.davis@example.com', '5566778899', 'CUSTOMER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Addresses for User 1 (John Doe)
INSERT INTO address (street, city, state, country, zipcode, user_id)
VALUES
  ('123 Elm St', 'Springfield', 'Illinois', 'USA', '62701', 1),
  ('456 Oak St', 'Chicago', 'Illinois', 'USA', '60601', 1);

-- Insert Addresses for User 2 (Jane Smith)
INSERT INTO address (street, city, state, country, zipcode, user_id)
VALUES
  ('789 Pine St', 'New York', 'New York', 'USA', '10001', 2);

-- Insert Addresses for User 3 (Alice Brown)
INSERT INTO address (street, city, state, country, zipcode, user_id)
VALUES
  ('101 Maple St', 'Los Angeles', 'California', 'USA', '90001', 3);

-- Insert Addresses for User 4 (Bob Davis)
INSERT INTO address (street, city, state, country, zipcode, user_id)
VALUES
  ('202 Birch St', 'Houston', 'Texas', 'USA', '77001', 4);