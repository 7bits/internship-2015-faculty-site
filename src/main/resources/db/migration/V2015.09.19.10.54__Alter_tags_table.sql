CREATE TABLE tags (id BIGSERIAL PRIMARY KEY, title TEXT UNIQUE);
INSERT INTO tags (title) VALUES('Новости');

CREATE TABLE content_tags (content INTEGER REFERENCES content_tags(id), tag INTEGER REFERENCES tags(id));
CREATE INDEX content_tags_index ON content_tags(content, tag);