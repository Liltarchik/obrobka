-- 1. Створюємо користувача
CREATE USER project_user WITH ENCRYPTED PASSWORD 'password';

-- 2. Даємо йому права
GRANT ALL PRIVILEGES ON DATABASE project_db TO project_user;

-- 3. Створюємо таблицю для фільмів
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(255)
);