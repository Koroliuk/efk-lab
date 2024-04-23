CREATE DATABASE library_db WITH ENCODING 'UTF8';
CREATE USER library_app with password 'library';
GRANT ALL PRIVILEGES ON DATABASE library_db to library_app;
