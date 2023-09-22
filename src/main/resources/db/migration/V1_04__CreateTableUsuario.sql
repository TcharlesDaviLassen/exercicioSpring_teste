INSERT INTO versao(versao, data) VALUES('04', now());

CREATE TABLE usuario_flyway (
    id serial PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    numero VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    data TIMESTAMP NOT NULL,
    CONSTRAINT unique_nome_email UNIQUE (nome, email)
);