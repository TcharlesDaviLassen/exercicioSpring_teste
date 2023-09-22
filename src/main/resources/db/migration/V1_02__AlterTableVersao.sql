INSERT INTO versao(versao, data) VALUES('02', now());

ALTER TABLE versao ALTER COLUMN data TYPE TIMESTAMP;