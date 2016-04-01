-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: hb
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

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
-- Table structure for table `ContactUs`
--

DROP TABLE IF EXISTS `ContactUs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactUs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` longtext NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ContactUs`
--

LOCK TABLES `ContactUs` WRITE;
/*!40000 ALTER TABLE `ContactUs` DISABLE KEYS */;
/*!40000 ALTER TABLE `ContactUs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Donation`
--

DROP TABLE IF EXISTS `Donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Donation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Donation`
--

LOCK TABLES `Donation` WRITE;
/*!40000 ALTER TABLE `Donation` DISABLE KEYS */;
INSERT INTO `Donation` VALUES (1,NULL,'2015-11-20 01:01:04',NULL,'2015-11-20 01:01:04','Hong Kong',6500,'2015-09-30','Basanti Thapa'),(2,NULL,'2015-11-20 00:57:37',NULL,'2015-11-20 00:57:37','Hong Kong',2600,'2015-10-16','Limi Noona Gurung'),(3,NULL,'2015-11-20 01:02:07',NULL,'2015-11-20 01:02:07','Hong Kong',6500,'2015-10-31','Deepu Thapa'),(4,NULL,'2015-11-20 01:02:34',NULL,'2015-11-20 01:02:34','Hong Kong',6500,'2015-10-31','Roshan Rana'),(5,NULL,'2015-11-20 01:04:10',NULL,'2015-11-20 01:04:10','Hong Kong',6500,'2015-10-01','Yuvraj Pun'),(6,NULL,'2015-11-22 08:38:25',NULL,'2015-11-22 08:38:25','Hong Kong',14700,'2015-10-01','Shanti Malla'),(7,NULL,'2015-11-22 08:39:11',NULL,'2015-11-22 08:39:11','Qatar',15007,'2015-09-01','Keshav Khatri'),(8,NULL,'2015-11-20 01:06:53',NULL,'2015-11-20 01:06:53','Qatar',2645,'2015-09-01','Manoj Gupta'),(9,NULL,'2015-11-20 01:07:16',NULL,'2015-11-20 01:07:16','Qatar',5250,'2015-09-01','Dhan Bahadur Bayalkoti'),(10,NULL,'2015-11-22 08:39:49',NULL,'2015-11-22 08:39:49','Qatar',10438,'2015-09-01','Dhan Bahadur Thapa'),(11,NULL,'2015-11-20 01:08:34',NULL,'2015-11-20 01:08:34','Nepal',2000,'2015-09-01','Showsun Sushling'),(12,NULL,'2015-11-22 08:40:18',NULL,'2015-11-22 08:40:18','Qatar',13089,'2015-09-01','Keshav Khatri'),(13,NULL,'2015-11-22 08:41:17',NULL,'2015-11-22 08:41:17','Hong Kong',14721,'2015-10-01','Mamata Pun'),(14,NULL,'2015-11-22 08:41:43',NULL,'2015-11-22 08:41:43','Hong Kong',13250,'2015-10-01','Karki Kamal'),(15,NULL,'2015-11-20 01:11:58',NULL,'2015-11-20 01:11:58','Japan',37160,'2015-10-01','Sachin KC'),(16,NULL,'2015-11-20 01:12:23',NULL,'2015-11-20 01:12:23','Hong Kong',28000,'2015-10-01','Sunita Gurung'),(17,NULL,'2015-11-20 01:13:05',NULL,'2015-11-20 01:13:05','Hong Kong',21000,'2015-10-01','Om Bahadur Taramu'),(18,NULL,'2015-11-22 08:43:26',NULL,'2015-11-22 08:43:26','South Korea',4000,'2015-11-22','Yogesh Shrestha'),(19,NULL,'2015-11-22 08:44:15',NULL,'2015-11-22 08:44:15','Qatar',26000,'2015-11-22','Dhan Bahadur Thapa '),(20,NULL,'2015-11-22 08:45:14',NULL,'2015-11-22 08:45:14','United States of America',45000,'2015-11-22','Devendra Gurung '),(21,NULL,'2015-11-22 08:46:16',NULL,'2015-11-22 08:46:16','Hong Kong ',14600,'2015-11-22','Shanti Malla'),(22,NULL,'2015-11-22 08:46:44',NULL,'2015-11-22 08:46:44','Hong Kong ',13250,'2015-11-22','Yuvraj Pun '),(23,NULL,'2015-11-22 08:47:20',NULL,'2015-11-22 08:47:20','Hong Kong',13250,'2015-11-22','Som Bahadur Thapa Singjali '),(24,NULL,'2015-11-22 08:47:47',NULL,'2015-11-22 08:47:47','Qatar',13160,'2015-11-22','Keshav Khatri '),(25,NULL,'2015-11-22 08:48:17',NULL,'2015-11-22 08:48:17','Hong Kong ',14721,'2015-11-22','Sarun Sherchan ');
/*!40000 ALTER TABLE `Donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FinancialReport`
--

DROP TABLE IF EXISTS `FinancialReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FinancialReport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FinancialReport`
--

LOCK TABLES `FinancialReport` WRITE;
/*!40000 ALTER TABLE `FinancialReport` DISABLE KEYS */;
INSERT INTO `FinancialReport` VALUES (1,NULL,'2015-11-18 02:43:19',NULL,'2015-11-18 02:43:19','Income and expense summary for year 2071 B.S.');
/*!40000 ALTER TABLE `FinancialReport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Login`
--

DROP TABLE IF EXISTS `Login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `isActive` bit(1) NOT NULL,
  `isAdmin` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_mgb97de8dtcpqpcxg9aabaxqu` (`user_id`),
  CONSTRAINT `FK_mgb97de8dtcpqpcxg9aabaxqu` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Login`
--

LOCK TABLES `Login` WRITE;
/*!40000 ALTER TABLE `Login` DISABLE KEYS */;
INSERT INTO `Login` VALUES (1,NULL,'2015-11-08 21:58:51',NULL,'2015-11-08 21:58:51','','','$2a$13$P7rGK7v/7PemrpuWs.P./.PtKi7fL6JkkTsaIzPBLj.mFRQV0Ppxa',1),(2,NULL,'2015-11-05 12:26:10','budthapa@gmail.com','2015-11-05 12:26:10','','\0','$2a$13$ng0vXVI/I1ca1rjVpbYSJefifcstSw.Y/0olK4wuVndc5cDjVlUVW',2);
/*!40000 ALTER TABLE `Login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Picture`
--

DROP TABLE IF EXISTS `Picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `picture_name` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `report_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i4vyqsp4ebc669raatskxv0h2` (`project_id`),
  KEY `FK_kna7qhxcd4tdm0nddx94j4axs` (`user_id`),
  KEY `FK_7xcronkj5k4fxht4bi9cduuo7` (`report_id`),
  CONSTRAINT `FK_7xcronkj5k4fxht4bi9cduuo7` FOREIGN KEY (`report_id`) REFERENCES `FinancialReport` (`id`),
  CONSTRAINT `FK_i4vyqsp4ebc669raatskxv0h2` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK_kna7qhxcd4tdm0nddx94j4axs` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Picture`
--

LOCK TABLES `Picture` WRITE;
/*!40000 ALTER TABLE `Picture` DISABLE KEYS */;
INSERT INTO `Picture` VALUES (1,NULL,'2015-11-05 12:31:27',NULL,'2015-11-05 12:31:27','d8a7c3be-d81a-4ebb-b706-8cf01d0fb6dd11207374_10203289810564541_1196047952531316961_n.jpg',NULL,1,NULL),(2,NULL,'2015-11-05 12:37:07',NULL,'2015-11-05 12:37:07','61d84830-5f48-4047-9e4b-f3e358c6ddc112241262_10206872722049663_5527185953215274861_n (3).jpg',NULL,2,NULL),(3,NULL,'2015-11-18 02:43:19',NULL,'2015-11-18 02:43:19','48c9cd4a-e69d-4351-8512-aec2365b7edf income expense.pdf',NULL,NULL,1),(4,NULL,'2015-11-19 12:52:23',NULL,'2015-11-19 12:52:23','9deb45fa-8d3b-445c-94f9-e0e09d383bddimg18.jpg',1,NULL,NULL),(5,NULL,'2015-11-20 01:22:16',NULL,'2015-11-20 01:22:16','85696ad9-80bc-4ae3-b4f6-dd6a7a575f93img23.jpg',2,NULL,NULL),(6,NULL,'2015-11-20 01:28:15',NULL,'2015-11-20 01:28:15','f0c4e5d0-0521-4a75-a0c1-3b53055bc204img4.jpg',3,NULL,NULL),(7,NULL,'2015-11-20 01:34:35',NULL,'2015-11-20 01:34:35','cded4000-71b5-4237-b6b4-1b141b7423baimg20.jpg',4,NULL,NULL),(8,NULL,'2015-11-20 01:44:40',NULL,'2015-11-20 01:44:40','303c9708-ba2b-4bdc-ae7f-791ca482a08fimg33.jpg',5,NULL,NULL),(9,NULL,'2015-11-20 01:50:59',NULL,'2015-11-20 01:50:59','3c68bcc9-c027-403b-9cfb-93e94988aaff10592884_694679890582004_4723924601741495463_n.jpg',6,NULL,NULL),(10,NULL,'2015-11-20 01:52:17',NULL,'2015-11-20 01:52:17','3dc25040-664a-47f7-ac15-2693754c47b6img36.jpg',7,NULL,NULL),(11,NULL,'2015-11-20 01:55:41',NULL,'2015-11-20 01:55:41','43a76b2d-48a7-44f4-9e85-54d0cb5bdc4cimg31.jpg',8,NULL,NULL),(12,NULL,'2015-12-27 06:41:07',NULL,'2015-12-27 06:41:07','0b90cf2e-ddbc-4faf-886d-405b92e199dd12241557_10206860287938818_3260529484223383040_n.jpg',9,NULL,NULL),(13,NULL,'2016-01-15 11:06:33',NULL,'2016-01-15 11:06:33','ae26fde2-19f7-4ca6-947e-dc890d34af4c10411349_451684108370976_80022467641569544_n.jpg',10,NULL,NULL);
/*!40000 ALTER TABLE `Picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `description` longtext NOT NULL,
  `project_category` varchar(255) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,NULL,'2015-11-22 08:56:09','madhu@gmail.com','2015-11-22 08:56:09','We have distributed educational material and warm clothes (School sweaters) to students of Shree Saraswati ma. vi, Tamnagar. Stationary items were also distrubuted to students of class one. This was all possible by the fund arranged internally and the donation we  received from Mr. Keshav Khatri who is currently residing in Qatar. He donated NRs. 5007 for Hamro Bhabisya. We are greatful for his help and hope for more in the near future.','Project','Distributing clothes to students of Saraswati Ma. Vi.'),(2,'budthapa@gmail.com','2015-11-20 01:22:16',NULL,'2015-11-20 01:22:16','Our founder member Mr. Dhan Bahadur Rahadi and Mr. Mahesh Thapa participated in annual function program of Shree Saraswati Ma. Vi., Tamnagar. They distributed prizes to students who topped their class on final exam. They also donated some sports items to students.','News & Events','Prize and sports item distributed to students'),(3,NULL,'2015-11-22 08:54:58','madhu@gmail.com','2015-11-22 08:54:58','As our motto is to help deprived children get the education they want; we team of Hamrobhabisya stood together to bear all the cost for her education in a private boarding school. We are proud to all the generous people who donated us so that we can move forward to help her. Together, we can.','Project','Initiative to teach a student'),(4,NULL,'2015-11-22 08:54:47','madhu@gmail.com','2015-11-22 08:54:47','Every child has a right to education. As everyone knows that we have already started helping the children to get education. Last time we helped a child with all her tuition fees and stationary items. We didn\'t stop here; this time we along with \"Open Space Nepal\" moved forward to help two children who are in very much need of our help. We feel proud to announce that we will help them with their tuition fees.\r\n\r\nAgain, thank you all for your support.','Project','Collaboration with Open Space Nepal'),(5,'budthapa@gmail.com','2015-11-20 01:44:40',NULL,'2015-11-20 01:44:40','Our founder member Mr. Dhan Bahadur Rahadi and Mr. Mahesh Kumar Thapa with Yashoda Thapa providing cloth to the needy student.','News & Events','Distributing clothes to needy students'),(6,NULL,'2015-11-22 09:03:04','madhu@gmail.com','2015-11-22 09:03:04','Give what you get, you will get more. We do not only receive donation but we donate too. Mr. Mahesh Kumar Thapa from Hamro bhabisya donated on installment basis yearly to Youth for Blood operating in differenet parts of Nepal. We have already donated i) NRS.5,000.00 ii) 12,000.00 & iii) 15,000.00 till to them. They will be spending this money for athe operation cost incurred during searching fresh bloods and donors in the emergency cases as well as administrative and to pay telephone expenses.','Project','Donation made to youth for blood'),(7,'budthapa@gmail.com','2015-11-20 01:52:17',NULL,'2015-11-20 01:52:17','We were very much pleased to present 15x sets of Football Jerseys, 2x footballs, 1x Basketball and a Hand Pump as a Token of Love to the youths of Butwal- 14 ( Evolution Club Of Butwal (ECOB)) during the visit of our Founder Member- Mr. Madhu Bom Malla.\r\n\r\nWe wish you all the best ahead and may all the titles and trophies will kiss you in the days ahead.\r\n\r\nThank you very much all the folks for your warm adherence. ','News & Events','Sports items donated to Evolution club of Butwal (ECOB)'),(8,NULL,'2015-11-22 08:53:41','madhu@gmail.com','2015-11-22 08:53:41','We team of Hamro bhabisya are very much grateful to Yasoda Thapa for providing us an opportunity to help Fulmaya Nepali and Rabin B.K for their further study. We hope for more generous support from you in near future.Thanks Yasoda :) ','Project','We are grateful to Yasoda Thapa'),(9,'budthapa@gmail.com','2015-12-27 06:41:07',NULL,'2015-12-27 06:41:07','We are very much happy to announce the born of Mr. Little Malla. Congratulation to both of you. Hope that you together will prove the great parenthood for your child. \r\n\r\nCongratulation for being Mom and Dad from Hamrobhabisya.\r\n\r\nFeeling great. :)','News & Events','Congratulation to Madhu and Shanti '),(10,NULL,'2016-01-15 11:07:54','budthapa@gmail.com','2016-01-15 11:07:54','Butwal-14, Tamnagar (Shree Nawaratna Boarding School premise)\r\n\r\nHamro Bhabisya team is thankful to Mr. Madhu Bom Malla for providing warm clothes(sweaters and caps), various stationery items like copy, pencil, eraser, pen, stationery box for students of 5 schools around the Butwal-14 locality.\r\n\r\n55 students were specially chosen and selected from various schools whose parents are unable to afford warm clothes in the winter season for their childrens.\r\n\r\nMr. Madhu Bom Malla contributed and sponsored for 20 students in the name of his newly born baby boy \"Sahash\" whereas rest of the contribution to 35 students were done by Hamro Bhabisya team.\r\n\r\nWe are thankful to every team member and others who helped us in making this program successful. We hope for similar support in the coming futures.\r\n\r\nThank you\r\nHamro Bhabisya Team !','Project','Hamro Bhabisya team distributed stationery items and warm clothes');
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ResetPassword`
--

DROP TABLE IF EXISTS `ResetPassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResetPassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `hashToken` varchar(255) DEFAULT NULL,
  `randomToken` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `validTill` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ResetPassword`
--

LOCK TABLES `ResetPassword` WRITE;
/*!40000 ALTER TABLE `ResetPassword` DISABLE KEYS */;
/*!40000 ALTER TABLE `ResetPassword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contactNumber` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `hasLoginCredentials` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e6gkqunxajvyxl5uctpl2vl2p` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,NULL,'2015-11-21 01:18:43','madhu@gmail.com','2015-11-21 01:18:43','Nepal','','Creator','budthapa@gmail.com','\0','Buddhi'),(2,NULL,'2015-11-21 01:17:56','madhu@gmail.com','2015-11-21 01:17:56','Hong Kong','','Administrator','madhu@gmail.com','\0','Madhu Bom Malla');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,1,'1','','SQL','V1__.sql',215275598,'root','2015-11-04 07:11:14',623,1),(2,2,'2','','SQL','V2__.sql',-1118045003,'root','2015-11-04 07:11:14',5,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-01 17:42:53
