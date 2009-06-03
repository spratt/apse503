CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `postal_code` varchar(7) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `province_state` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `email` varchar(50) UNIQUE NOT NULL,
  `datetime` datetime DEFAULT NULL,
  `user_name` varchar(50) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `method` (
  `method_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`method_id`),
  KEY `user_id` (`user_id`),
  KEY `status_id` (`status_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `method_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `method_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`),
  CONSTRAINT `method_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `audit` (
  `audit_id` int(11) NOT NULL AUTO_INCREMENT,
  `method_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`audit_id`),
  KEY `method_id` (`method_id`),
  KEY `user_id` (`user_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `audit_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`),
  CONSTRAINT `audit_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `audit_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `method_price_status` (
  `method_price_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`method_price_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `method_price` (
  `method_price_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `method_id` int(11) NOT NULL,
  `method_price_status_id` int(11) NOT NULL,
  PRIMARY KEY (`method_price_id`),
  KEY `method_id` (`method_id`),
  KEY `method_price_status_id` (`method_price_status_id`),
  CONSTRAINT `method_price_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`),
  CONSTRAINT `method_price_ibfk_2` FOREIGN KEY (`method_price_status_id`) REFERENCES `method_price_status` (`method_price_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `method_purchase` (
  `method_purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `paid_developer` tinyint(1) DEFAULT NULL,
  `method_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `method_price_id` int(11) NOT NULL,
  PRIMARY KEY (`method_purchase_id`),
  KEY `method_id` (`method_id`),
  KEY `user_id` (`user_id`),
  KEY `method_price_id` (`method_price_id`),
  CONSTRAINT `method_purchase_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`),
  CONSTRAINT `method_purchase_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `method_purchase_ibfk_3` FOREIGN KEY (`method_price_id`) REFERENCES `method_price` (`method_price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `method_use` (
  `method_use_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `method_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`method_use_id`),
  KEY `method_id` (`method_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `method_use_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`),
  CONSTRAINT `method_use_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rating` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` text,
  `method_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`rating_id`),
  KEY `method_id` (`method_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


