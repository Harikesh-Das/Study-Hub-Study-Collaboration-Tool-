DROP DATABASE StudyTools;
CREATE DATABASE IF NOT EXISTS StudyTools;
USE StudyTools;

CREATE TABLE `StudyTools`.`User` (
  `email` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL,
  `password` VARCHAR(255) NULL,
  PRIMARY KEY (`email`)
);


CREATE TABLE `StudyTools`.`UserSchedule` (
  `email` VARCHAR(255) NOT NULL,
  `coursename` VARCHAR(255) NULL,
  FOREIGN KEY (email) REFERENCES User(email)
);

CREATE TABLE `StudyTools`.`StudyGroup` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `creator_email` VARCHAR(255) NOT NULL,
  `coursename` VARCHAR(255) NULL,
  `meeting_date` VARCHAR(255) NULL,
  `meeting_time_start` VARCHAR(255) NULL,
  `capacity` INT NULL,
  `location` VARCHAR(255) NULL,
  PRIMARY KEY (`group_id`),
  FOREIGN KEY (`creator_email`) REFERENCES `User`(`email`)
);

CREATE TABLE `StudyTools`.`StudyGroupMembers` (
 `entry` INT NOT NULL,
 `group_id` INT,
  `user_email` VARCHAR(255),
  
  PRIMARY KEY (`entry`),
  FOREIGN KEY (`group_id`) REFERENCES `StudyGroup`(`group_id`),
  FOREIGN KEY (`user_email`) REFERENCES `User`(`email`)
);