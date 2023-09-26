INSERT INTO versao(versao, data) VALUES('06', now());

ALTER TABLE usuario_flyway ADD COLUMN usuario_enum_type_enum CHAR(1);
