CREATE DATABASE acervo_referencia
	DEFAULT CHARSET utf8mb4;

CREATE TABLE tb_usuario(
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    login_usuario VARCHAR(30) NOT NULL UNIQUE,
    senha_usuario VARCHAR(150) NOT NULL,
    nome_usuario VARCHAR(50) NOT NULL,
    email_usuario VARCHAR(100) NOT NULL UNIQUE,
    admin_usuario BOOLEAN
);

CREATE TABLE tb_referencias_bibliograficas(
	id_referencia INT PRIMARY KEY AUTO_INCREMENT,
    autor_referencia VARCHAR(100) NOT NULL,
    titulo_referencia VARCHAR(200) NOT NULL,
    ano_referencia INT(4), 
    referencia_referencia VARCHAR(300) NOT NULL,
    anotacoes_referencia VARCHAR(300),
    id_usuario_fk INT,
    FOREIGN KEY (id_usuario_fk) REFERENCES tb_usuario (id_usuario)
);