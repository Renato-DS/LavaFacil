CREATE DATABASE lavafacildb;
USE lavafacildb;

CREATE TABLE IF NOT EXISTS `lavafacildb`.`carros` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `placa` VARCHAR(90) NOT NULL,
  `nome_cliente` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(40),
  `marca_carro` VARCHAR(80),
  `observacoes` TEXT,
  `visitas` INT DEFAULT(0),
  `lavagens` INT DEFAULT(0),
  `gratis` INT DEFAULT(0),
  `tempo_limite` TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lavafacildb`.`usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(90) NOT NULL,
  `login` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(70) NOT NULL,
  `tipo` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lavafacildb`.`lavagens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_carro` INT NOT NULL,
  `placa` VARCHAR(90) NOT NULL,
  `nome_cliente` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(40),
  `marca_carro` VARCHAR(80),
  `valor` DECIMAL(9,2) NOT NULL,
  `completo` BOOLEAN NOT NULL,
  `observacoes` TEXT,  
  `data` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `lavafacildb`.`opcoes` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `nome_opcao` VARCHAR(255) NOT NULL,
    `valor_opcao` VARCHAR(255) NOT NULL, 
    `descricao` TEXT,
    PRIMARY KEY (`id`))
 ENGINE = InnoDB;

INSERT INTO usuario(nome, login, senha, tipo) VALUES ("ADM","admin","$2a$10$1tgzxusmWj1fqbZKuq8T8eitAybipxXwSLqyhsOMVb194U6KoEXeK","Administrador"); /* Senha: admin */
INSERT INTO usuario(nome, login, senha, tipo) VALUES ("Funcionário","teste", "$2a$10$hYtXM2W9YHJVRDIlx/OhA.lp/POJX.SgNqYUwmGnUInQto0h3f7Je", "Usuário"); /* Senha: 123 */

INSERT INTO opcoes(nome_opcao, valor_opcao, descricao) VALUES 
("funcGratis", "true", "Habilitar/desabilitar a funcao de lavagem gratis"), 
("lavagens", "4", "Lavagens necessarias para receber uma lavagem gratis"), 
("dias", "30", "Quantidade de dias para alcancar as lavagens necessarias para receber uma gratis"), 
("telefone", "false", "Ativar/desativar a coluna 'telefone' para funcionarios");