INSERT INTO books (title, author, isbn, published_year)
VALUES
  ('Clean Code', 'Robert C. Martin', '978-0132350884', 2008),
  ('Effective Java', 'Joshua Bloch', '978-0134685991', 2018),
  ('Spring in Action', 'Craig Walls', '978-1617294945', 2022);

INSERT INTO book_copy (book_id, available)
VALUES
  (1, true),
  (1, false),
  (2, true);