INSERT INTO users (id, user_name, mobile_number, user_type) SELECT 'a1b07c6d-4839-457e-8499-e87ececbea20', 'vikshak', '+919043408421',0 WHERE NOT EXISTS(SELECT user_name FROM users WHERE mobile_number = '+919043408421');