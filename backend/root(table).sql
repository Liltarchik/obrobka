-- 1. Даємо права на саму таблицю
GRANT ALL PRIVILEGES ON TABLE items TO project_user;

-- 2. ВАЖЛИВО: Даємо права на використання лічильника ID (щоб працював SERIAL)
GRANT USAGE, SELECT ON SEQUENCE items_id_seq TO project_user;

-- 3. (Опціонально) Даємо права на всі таблиці в схемі public на майбутнє
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO project_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO project_user;