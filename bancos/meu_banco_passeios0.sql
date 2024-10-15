-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: meu_banco
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `passeios`
--

DROP TABLE IF EXISTS `passeios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passeios` (
  `id_passeio` int(11) NOT NULL AUTO_INCREMENT,
  `nome_do_hospede` varchar(255) NOT NULL,
  `data_do_passeio` datetime NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `tipo_passeio` int(11) NOT NULL,
  `data_de_registro_passeio` datetime NOT NULL,
  `id_responsavel_registro_passeio` int(11) NOT NULL,
  `id_colaborador_passeio` int(11) NOT NULL,
  `status_passeio` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_passeio`),
  KEY `id_tipo_passeio` (`tipo_passeio`),
  KEY `id_responsavel_registro_passeio` (`id_responsavel_registro_passeio`),
  KEY `id_colaborador_passeio` (`id_colaborador_passeio`),
  CONSTRAINT `passeios_ibfk_1` FOREIGN KEY (`tipo_passeio`) REFERENCES `tipos_passeios` (`id_tipo_passeio`),
  CONSTRAINT `passeios_ibfk_2` FOREIGN KEY (`id_responsavel_registro_passeio`) REFERENCES `usuarios` (`id_usuario`),
  CONSTRAINT `passeios_ibfk_3` FOREIGN KEY (`id_colaborador_passeio`) REFERENCES `colaboradores` (`id_colaborador`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passeios`
--

LOCK TABLES `passeios` WRITE;
/*!40000 ALTER TABLE `passeios` DISABLE KEYS */;
INSERT INTO `passeios` VALUES (3,'Lucas','2024-10-20 00:00:00',172.50,1,'2024-10-11 10:27:00',1,1,NULL),(4,'Lucas','2024-10-10 00:00:00',105.50,1,'2024-10-13 17:50:10',1,1,1),(5,'George','2024-10-13 00:00:00',10.30,1,'2024-10-12 17:50:10',1,1,1),(6,'Gaile','2024-10-17 00:00:00',856.30,1,'2024-10-09 17:50:10',1,1,1);
/*!40000 ALTER TABLE `passeios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-15 16:12:23
