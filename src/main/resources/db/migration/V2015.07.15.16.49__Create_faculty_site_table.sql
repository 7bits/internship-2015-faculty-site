CREATE TABLE album (id BIGSERIAL PRIMARY KEY, title VARCHAR(256) NOT NULL, description TEXT, creating_date DATE, creating_time TIME);
CREATE TABLE image (id BIGSERIAL PRIMARY KEY, title VARCHAR(256), description TEXT, creating_date DATE, creating_time TIME, link TEXT NOT NULL, is_head BOOLEAN DEFAULT 'false', album INTEGER REFERENCES album(id));

INSERT INTO album (title, description) VALUES ('test album1', 'test description1');
INSERT INTO album (title, description) VALUES ('test album2', 'test description2');

INSERT INTO image (title, link, album) VALUES ('test image1', 'test link1', 1);
INSERT INTO image (title, link, album) VALUES ('test image2', 'test link2', 1);
INSERT INTO image (title, link, album) VALUES ('test image3', 'test link3', 1);
INSERT INTO image (title, link, album) VALUES ('test image4', 'test link4', 1);
INSERT INTO image (title, link, album) VALUES ('test image5', 'test link5', 1);
INSERT INTO image (title, link, album, is_head) VALUES ('test image6', 'test link6', 1, 'true');


INSERT INTO image (title, link, album) VALUES ('test image12', 'test link1', 2);
INSERT INTO image (title, link, album) VALUES ('test image22', 'test link2', 2);
INSERT INTO image (title, link, album) VALUES ('test image32', 'test link3', 2);
INSERT INTO image (title, link, album, is_head) VALUES ('test image42', 'test link42', 2, 'true');


