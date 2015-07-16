CREATE TABLE album (id BIGSERIAL PRIMARY KEY, title VARCHAR(256) NOT NULL, description TEXT, creating_date DATE, creating_time TIME);
CREATE TABLE image (id BIGSERIAL PRIMARY KEY, title VARCHAR(256), description TEXT, creating_date DATE, creating_time TIME, link TEXT NOT NULL, is_head BOOLEAN DEFAULT 'false', album INTEGER REFERENCES album(id));

INSERT INTO album (title, description) VALUES ('test album', 'test description');

INSERT INTO image (title, link, album) VALUES ('test album', 'test link', 1);
INSERT INTO image (title, link, album, is_head) VALUES ('test album2', 'test link2', 1, 'true');


