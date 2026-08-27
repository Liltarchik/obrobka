-- Run this as a PostgreSQL superuser or as the owner of project_db.
-- PostgreSQL 15+ no longer grants CREATE on schema public to every user by default.

\connect project_db

GRANT CONNECT ON DATABASE project_db TO project_user;
GRANT USAGE, CREATE ON SCHEMA public TO project_user;

-- Optional cleanup if a table was created manually by another owner.
-- ALTER TABLE IF EXISTS public.movies OWNER TO project_user;
