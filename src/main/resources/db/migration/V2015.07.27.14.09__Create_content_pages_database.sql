CREATE TABLE content_of_pages (id BIGSERIAL PRIMARY KEY, title VARCHAR(256), description TEXT, creating_date DATE, creating_time TIME, type TEXT);

INSERT INTO content_of_pages (title, description) VALUES ('Test', '<h2> Hello world!!!</h2>');