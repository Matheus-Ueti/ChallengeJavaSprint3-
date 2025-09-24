CREATE TABLE perfil_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    github_username VARCHAR(255) UNIQUE,
    nome_completo VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255),
    cargo VARCHAR(255)
);
