CREATE TABLE content_of_pages (id BIGSERIAL PRIMARY KEY, title VARCHAR(256), description TEXT, creating_date DATE, creating_time TIME, type TEXT NOT NULL DEFAULT '');

INSERT INTO content_of_pages (title, description, creating_date, creating_time) VALUES ('Test', '<h2> Hello world!!!</h2>', 'today', 'now');
INSERT INTO content_of_pages (title, description, creating_date, creating_time) VALUES ('Test1', '<h2> Hello world!!!</h2>', 'today', 'now');
INSERT INTO content_of_pages (title, description, creating_date, creating_time) VALUES ('Test2', '<h2> Hello world!!!</h2>', 'today', 'now');
INSERT INTO content_of_pages (title, description, creating_date, creating_time) VALUES ('Test3', '<h2> Hello world!!!</h2>', 'today', 'now');