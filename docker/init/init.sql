SELECT 'CREATE DATABASE melita_db_test'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'mydb')\gexec
grant all privileges on database melita_db_test to postgres;