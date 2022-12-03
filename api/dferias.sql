-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.31 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.2.0.6576
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para dferias
DROP DATABASE IF EXISTS `dferias`;
CREATE DATABASE IF NOT EXISTS `dferias` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dferias`;

-- Copiando estrutura para tabela dferias.equipe
DROP TABLE IF EXISTS `equipe`;
CREATE TABLE IF NOT EXISTS `equipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_lider` int DEFAULT NULL,
  `nome` varchar(50) NOT NULL DEFAULT '',
  `cor` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela dferias.equipe: ~0 rows (aproximadamente)
DELETE FROM `equipe`;
INSERT INTO `equipe` (`id`, `id_lider`, `nome`, `cor`) VALUES
	(1, 5, 'dev', '#333333');

-- Copiando estrutura para tabela dferias.funcionario
DROP TABLE IF EXISTS `funcionario`;
CREATE TABLE IF NOT EXISTS `funcionario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `data_admissao` date NOT NULL,
  `saldo_de_ferias` int NOT NULL DEFAULT '0',
  `pass` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `id_equipe` int DEFAULT '0',
  `cidade` varchar(100) NOT NULL DEFAULT '0',
  `modalidade` varchar(255) DEFAULT NULL,
  `uf` varchar(255) DEFAULT NULL,
  `saldo_ferias` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_funcionario_equipe` (`id_equipe`),
  CONSTRAINT `FK_funcionario_equipe` FOREIGN KEY (`id_equipe`) REFERENCES `equipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela dferias.funcionario: ~1 rows (aproximadamente)
DELETE FROM `funcionario`;
INSERT INTO `funcionario` (`id`, `nome`, `email`, `data_admissao`, `saldo_de_ferias`, `pass`, `id_equipe`, `cidade`, `modalidade`, `uf`, `saldo_ferias`) VALUES
	(9, 'Andre', 'andrevoronhuk@gmail.com', '2022-11-20', 0, '$2a$12$zGRwWnQQy6GGvPQcka3.vef555MsnnTFQh1oZPp3m7Ej7txZSGiwW', 1, 'irati', 'funcionario', 'pr', 0);

-- Copiando estrutura para tabela dferias.funcionario_perfis
DROP TABLE IF EXISTS `funcionario_perfis`;
CREATE TABLE IF NOT EXISTS `funcionario_perfis` (
  `funcionario_id` int NOT NULL,
  `perfis_id` int NOT NULL,
  PRIMARY KEY (`funcionario_id`,`perfis_id`),
  KEY `FKtay4xgsw3ngb29uxwtj7w0o0` (`perfis_id`),
  CONSTRAINT `FKtay4xgsw3ngb29uxwtj7w0o0` FOREIGN KEY (`perfis_id`) REFERENCES `perfil` (`id`),
  CONSTRAINT `FKtrmbhcnmjioqkr1xs9n9byfml` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela dferias.funcionario_perfis: ~0 rows (aproximadamente)
DELETE FROM `funcionario_perfis`;

-- Copiando estrutura para tabela dferias.perfil
DROP TABLE IF EXISTS `perfil`;
CREATE TABLE IF NOT EXISTS `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Copiando dados para a tabela dferias.perfil: ~0 rows (aproximadamente)
DELETE FROM `perfil`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
