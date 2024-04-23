DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS publishers CASCADE;

CREATE TABLE publishers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(255)       NOT NULL,
    author       VARCHAR(255)       NOT NULL,
    isbn         VARCHAR(13) UNIQUE NOT NULL,
    year         INTEGER,
    publisher_id INTEGER REFERENCES publishers (id) ON DELETE CASCADE
);
