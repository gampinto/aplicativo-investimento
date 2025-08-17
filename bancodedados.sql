CREATE DATABASE Investimentos;

USE Investimentos; 
CREATE TABLE transacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    quantidade DECIMAL(20,8) NOT NULL,
    preco_unitario DECIMAL(20,8) NOT NULL,
    tipo ENUM('COMPRA', 'VENDA') NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lotes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transacao_id INT NOT NULL,
    quantidade DECIMAL(20,8) NOT NULL,
    preco_unitario DECIMAL(20,8) NOT NULL,
    FOREIGN KEY (transacao_id) REFERENCES transacoes(id)
);