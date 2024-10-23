CREATE TABLE IF NOT EXISTS subject (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO subject (name) VALUES ('math');
INSERT INTO subject (name) VALUES ('informatics');
INSERT INTO subject (name) VALUES ('tatar language');
INSERT INTO subject (name) VALUES ('physics');
INSERT INTO subject (name) VALUES ('history');
INSERT INTO subject (name) VALUES ('chemistry');
INSERT INTO subject (name) VALUES ('biology');
INSERT INTO subject (name) VALUES ('literature');
INSERT INTO subject (name) VALUES ('physical culture');
