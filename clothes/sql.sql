/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - dress_san
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dress_san` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `dress_san`;

/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
  `agent_id` int(12) NOT NULL AUTO_INCREMENT,
  `login_id` int(12) DEFAULT NULL,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  `phone` int(10) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `states` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`agent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `agent` */

insert  into `agent`(`agent_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`,`states`) values 
(1,3,'adss','cffs','dsr',132424325,'rejsnjsj@gmail','1');

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int(12) NOT NULL AUTO_INCREMENT,
  `product_id` int(12) DEFAULT NULL,
  `customer_id` int(12) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `date` varchar(12) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

insert  into `booking`(`booking_id`,`product_id`,`customer_id`,`amount`,`date`,`status`) values 
(1,1,1,'5635','12-20-103','pending'),
(2,2,2,'100','2023-03-15','Booked'),
(3,1,2,'None','2023-03-15','deliver');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`customer_id`,`complaint`,`reply`,`date`) values 
(1,1,'afadf','pending','123243');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_id` int(12) NOT NULL AUTO_INCREMENT,
  `login_id` int(12) DEFAULT NULL,
  `fname` varchar(30) DEFAULT NULL,
  `lname` varchar(30) DEFAULT NULL,
  `place` varchar(30) DEFAULT NULL,
  `phone` int(10) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `customer` */

insert  into `customer`(`customer_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values 
(1,1,'dfds','fdds','place',3212412,'dfdss'),
(2,5,'Renuka Kamath','Renuka Kamath','vklm',1234567892,'renukakamath2@gmail.com');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(12) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `usertype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(5,'hai','hai','user'),
(1,'admin','admin','admin'),
(3,'user','user','agent');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(12) DEFAULT NULL,
  `booking_id` int(12) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `date` varchar(12) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`booking_id`,`amount`,`date`) values 
(1,1,'100','12-12-3992'),
(NULL,3,'None','2023-03-15');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` int(12) NOT NULL AUTO_INCREMENT,
  `agent_id` int(12) DEFAULT NULL,
  `product` varchar(40) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `product` */

insert  into `product`(`product_id`,`agent_id`,`product`,`image`,`status`,`amount`) values 
(1,1,'dwada','dfdsf','1',NULL),
(2,1,'fghjkl','static/imagef170895e-fc34-4a6c-be99-aaf416cdf90cmicrosoft-365-oUbzU87d1Gc-unsplash.jpg','pending','100');

/*Table structure for table `request` */

DROP TABLE IF EXISTS `request`;

CREATE TABLE `request` (
  `request_id` int(12) NOT NULL AUTO_INCREMENT,
  `customer_id` int(12) DEFAULT NULL,
  `request` varchar(20) DEFAULT NULL,
  `details` varchar(20) DEFAULT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `agent_id` int(12) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `request` */

insert  into `request`(`request_id`,`customer_id`,`request`,`details`,`image`,`status`,`agent_id`) values 
(1,1,'dfsf','dfsf','rfer','sanitised',1),
(2,2,'hjdjd','hdhhdh','static/image0b35e7fe-9978-438a-b650-66d967545b1babc.jpg','sanitised',1),
(3,2,'hjdjd','hdhhdh','static/imageae22a235-bf77-4506-a4e9-5b3565a6ff7aabc.jpg','sanitised',1);

/*Table structure for table `wallet` */

DROP TABLE IF EXISTS `wallet`;

CREATE TABLE `wallet` (
  `wallet_id` int(12) NOT NULL AUTO_INCREMENT,
  `customer_id` int(12) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `date` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`wallet_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `wallet` */

insert  into `wallet`(`wallet_id`,`customer_id`,`amount`,`date`) values 
(5,2,'1000','2023-03-15'),
(2,1,'1000','2023-03-15'),
(3,2,'1000','2023-03-15'),
(4,2,'1000','2023-03-15');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
