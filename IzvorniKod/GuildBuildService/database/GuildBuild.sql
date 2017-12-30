CREATE DATABASE  IF NOT EXISTS `guild_build` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `guild_build`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: guild_build
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ceh`
--

DROP TABLE IF EXISTS `ceh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ceh` (
  `sifCeh` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(20) NOT NULL,
  `sifIgre` int(11) NOT NULL,
  `opis` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`sifCeh`),
  KEY `igra_idx` (`sifIgre`),
  CONSTRAINT `igra` FOREIGN KEY (`sifIgre`) REFERENCES `igra` (`sifIgre`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ceh`
--

LOCK TABLES `ceh` WRITE;
/*!40000 ALTER TABLE `ceh` DISABLE KEYS */;
/*!40000 ALTER TABLE `ceh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cilj`
--

DROP TABLE IF EXISTS `cilj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cilj` (
  `sifCilja` int(11) NOT NULL AUTO_INCREMENT,
  `nazivCilja` varchar(50) NOT NULL,
  `sifDog` int(11) NOT NULL,
  `ispunjen` bit(1) DEFAULT b'0',
  PRIMARY KEY (`sifCilja`),
  KEY `dogadaj_idx` (`sifDog`),
  CONSTRAINT `dogadaj` FOREIGN KEY (`sifDog`) REFERENCES `dogadaj` (`sifDog`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cilj`
--

LOCK TABLES `cilj` WRITE;
/*!40000 ALTER TABLE `cilj` DISABLE KEYS */;
/*!40000 ALTER TABLE `cilj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dogadaj`
--

DROP TABLE IF EXISTS `dogadaj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dogadaj` (
  `sifDog` int(11) NOT NULL AUTO_INCREMENT,
  `nazivDog` varchar(100) NOT NULL,
  `sifCeh` int(11) NOT NULL,
  `ispunjen` bit(1) DEFAULT b'0',
  `vidljiv` bit(1) DEFAULT b'0',
  PRIMARY KEY (`sifDog`),
  KEY `ceh_idx` (`sifCeh`),
  CONSTRAINT `ceh` FOREIGN KEY (`sifCeh`) REFERENCES `ceh` (`sifCeh`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dogadaj`
--

LOCK TABLES `dogadaj` WRITE;
/*!40000 ALTER TABLE `dogadaj` DISABLE KEYS */;
/*!40000 ALTER TABLE `dogadaj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `igra`
--

DROP TABLE IF EXISTS `igra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `igra` (
  `sifIgre` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`sifIgre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `igra`
--

LOCK TABLES `igra` WRITE;
/*!40000 ALTER TABLE `igra` DISABLE KEYS */;
/*!40000 ALTER TABLE `igra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klasa`
--

DROP TABLE IF EXISTS `klasa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `klasa` (
  `sifKlase` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(20) NOT NULL,
  `sifIgre` int(11) NOT NULL,
  PRIMARY KEY (`sifKlase`),
  KEY `igraa_idx` (`sifIgre`),
  CONSTRAINT `igraa` FOREIGN KEY (`sifIgre`) REFERENCES `igra` (`sifIgre`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klasa`
--

LOCK TABLES `klasa` WRITE;
/*!40000 ALTER TABLE `klasa` DISABLE KEYS */;
/*!40000 ALTER TABLE `klasa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnik` (
  `nadimak` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `lozinka` varchar(16) NOT NULL,
  `statusR` bit(1) DEFAULT b'0',
  `rang` varchar(11) DEFAULT NULL,
  `sifCeh` int(11) DEFAULT '0',
  `statusP` bit(1) DEFAULT b'0',
  `opis` varchar(2500) DEFAULT NULL,
  `isAdmin` bit(1) DEFAULT b'0',
  PRIMARY KEY (`nadimak`,`email`),
  KEY `cehh_idx` (`sifCeh`),
  CONSTRAINT `cehh` FOREIGN KEY (`sifCeh`) REFERENCES `ceh` (`sifCeh`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lik`
--

DROP TABLE IF EXISTS `lik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lik` (
  `nadimak` varchar(20) NOT NULL,
  `level` int(11) DEFAULT '1',
  `sifKlase` int(11) NOT NULL,
  `craftingSkills` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`nadimak`,`sifKlase`),
  KEY `klasaa_idx` (`sifKlase`),
  CONSTRAINT `klasaa` FOREIGN KEY (`sifKlase`) REFERENCES `klasa` (`sifKlase`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `vlasnik` FOREIGN KEY (`nadimak`) REFERENCES `korisnik` (`nadimak`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lik`
--

LOCK TABLES `lik` WRITE;
/*!40000 ALTER TABLE `lik` DISABLE KEYS */;
/*!40000 ALTER TABLE `lik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obrazac`
--

DROP TABLE IF EXISTS `obrazac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `obrazac` (
  `sifCeha` int(11) NOT NULL,
  `nadimak` varchar(20) NOT NULL,
  `poruka` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`sifCeha`,`nadimak`),
  KEY `vlasnikO_idx` (`nadimak`),
  CONSTRAINT `ceh1` FOREIGN KEY (`sifCeha`) REFERENCES `ceh` (`sifCeh`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `vlasnikO` FOREIGN KEY (`nadimak`) REFERENCES `korisnik` (`nadimak`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obrazac`
--

LOCK TABLES `obrazac` WRITE;
/*!40000 ALTER TABLE `obrazac` DISABLE KEYS */;
/*!40000 ALTER TABLE `obrazac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `podcilj`
--

DROP TABLE IF EXISTS `podcilj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `podcilj` (
  `sifPodcilja` int(11) NOT NULL AUTO_INCREMENT,
  `nazivCilja` varchar(50) COLLATE utf8_general_mysql500_ci NOT NULL,
  `sifCilja` int(11) NOT NULL,
  `ispunjen` bit(1) DEFAULT b'0',
  PRIMARY KEY (`sifPodcilja`),
  KEY `cilj_idx` (`sifCilja`),
  CONSTRAINT `cilj` FOREIGN KEY (`sifCilja`) REFERENCES `cilj` (`sifCilja`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_mysql500_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `podcilj`
--

LOCK TABLES `podcilj` WRITE;
/*!40000 ALTER TABLE `podcilj` DISABLE KEYS */;
/*!40000 ALTER TABLE `podcilj` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-30 22:45:59
