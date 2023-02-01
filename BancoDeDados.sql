-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: dferias
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `equipe`
--

DROP TABLE IF EXISTS `equipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_lider` int DEFAULT NULL,
  `nome` varchar(50) NOT NULL DEFAULT '',
  `cor` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipe`
--

LOCK TABLES `equipe` WRITE;
/*!40000 ALTER TABLE `equipe` DISABLE KEYS */;
INSERT INTO `equipe` VALUES (1,18,'dev','#333333'),(2,18,'Documentacao','#ff00ff'),(3,18,'Infraestrutura','#0000ff'),(4,18,'teste','#000000');
/*!40000 ALTER TABLE `equipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ferias`
--

DROP TABLE IF EXISTS `ferias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ferias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fim` date DEFAULT NULL,
  `id_funcionario` bigint DEFAULT NULL,
  `id_lider` bigint DEFAULT NULL,
  `id_rh` bigint DEFAULT NULL,
  `inicio` date DEFAULT NULL,
  `observacoes_lider` varchar(255) DEFAULT NULL,
  `observacoes_rh` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ferias`
--

LOCK TABLES `ferias` WRITE;
/*!40000 ALTER TABLE `ferias` DISABLE KEYS */;
INSERT INTO `ferias` VALUES (1,'2023-02-28',9,18,NULL,'2023-02-18',NULL,NULL,'PENDENTE');
/*!40000 ALTER TABLE `ferias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
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
  UNIQUE KEY `funcionario_pk` (`email`),
  KEY `FK_funcionario_equipe` (`id_equipe`),
  CONSTRAINT `FK_funcionario_equipe` FOREIGN KEY (`id_equipe`) REFERENCES `equipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (9,'Andre','andrevoronhuk@gmail.com','2022-11-20',0,'$2a$12$zGRwWnQQy6GGvPQcka3.vef555MsnnTFQh1oZPp3m7Ej7txZSGiwW',1,'irati','funcionario','pr',0),(13,'Andre','andre2voronhuk@gmail.com','2022-11-19',0,'$2a$10$C2d7jfkg7uwUqwMW7VQXeOJN1cV59OoU37.fKrxrbucwqM/FV2tje',1,'irati','funcionario','pr',0),(18,'Andre','andre3voronhuk@gmail.com','2022-11-19',0,'$2a$10$7jqj1W1D08Th.ZoUCaYOLeGnjjGgssMu7FEfm6S9u89.LYENe.EPy',1,'irati','funcionario','pr',0);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario_perfis`
--

DROP TABLE IF EXISTS `funcionario_perfis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario_perfis` (
  `funcionario_id` int NOT NULL,
  `perfis_id` int NOT NULL,
  PRIMARY KEY (`funcionario_id`,`perfis_id`),
  KEY `FKtay4xgsw3ngb29uxwtj7w0o0` (`perfis_id`),
  CONSTRAINT `FKtay4xgsw3ngb29uxwtj7w0o0` FOREIGN KEY (`perfis_id`) REFERENCES `perfil` (`id`),
  CONSTRAINT `FKtrmbhcnmjioqkr1xs9n9byfml` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario_perfis`
--

LOCK TABLES `funcionario_perfis` WRITE;
/*!40000 ALTER TABLE `funcionario_perfis` DISABLE KEYS */;
INSERT INTO `funcionario_perfis` VALUES (9,1),(13,2),(18,2);
/*!40000 ALTER TABLE `funcionario_perfis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'funcionario'),(2,'RH');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-01 20:07:31
