INSERT INTO publishers (name)
VALUES ('Penguin Random House'),
       ('HarperCollins'),
       ('Simon & Schuster');

INSERT INTO books (title, author, isbn, year, publisher_id)
VALUES ('1984', 'George Orwell', '9780141036144', 1949, 1),
       ('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 1960, 2),
       ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 1925, 3),
       ('Moby Dick', 'Herman Melville', '9781503280786', 1851, 1),
       ('War and Peace', 'Leo Tolstoy', '9780300060997', 1869, 2);
