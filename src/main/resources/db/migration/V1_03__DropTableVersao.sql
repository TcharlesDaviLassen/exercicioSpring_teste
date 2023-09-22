INSERT INTO versao(versao, data) VALUES('03', now());

DROP TABLE versao;

CREATE TABLE versao (
    id serial PRIMARY KEY,
    versao VARCHAR(10) NOT NULL,
    data TIMESTAMP NOT NULL
);
