-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema SYOS
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SYOS
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SYOS` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `SYOS` ;

-- -----------------------------------------------------
-- Table `SYOS`.`Bills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Bills` (
  `bill_id` INT NOT NULL AUTO_INCREMENT,
  `bill_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `total` DECIMAL(10,2) NOT NULL,
  `final_total` DECIMAL(10,2) NOT NULL,
  `cash_tendered` DECIMAL(10,2) NOT NULL,
  `change_amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`bill_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Items` (
  `item_code` VARCHAR(10) NOT NULL,
  `item_name` VARCHAR(50) NOT NULL,
  `category_id` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `discount` DECIMAL(5,2) NULL DEFAULT '0.00',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`BillItems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`BillItems` (
  `bill_item_id` INT NOT NULL AUTO_INCREMENT,
  `bill_id` INT NOT NULL,
  `item_code` VARCHAR(10) NOT NULL,
  `quantity` INT NOT NULL,
  `total_price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`bill_item_id`),
  INDEX `bill_id` (`bill_id` ASC) VISIBLE,
  INDEX `item_code` (`item_code` ASC) VISIBLE,
  CONSTRAINT `billitems_ibfk_1`
    FOREIGN KEY (`bill_id`)
    REFERENCES `SYOS`.`Bills` (`bill_id`),
  CONSTRAINT `billitems_ibfk_2`
    FOREIGN KEY (`item_code`)
    REFERENCES `SYOS`.`Items` (`item_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `category_name` (`category_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Discounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Discounts` (
  `discount_id` INT NOT NULL AUTO_INCREMENT,
  `item_code` VARCHAR(10) NULL DEFAULT NULL,
  `category_id` INT NULL DEFAULT NULL,
  `discount_type` ENUM('percentage', 'flat') NOT NULL,
  `discount_value` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`discount_id`),
  INDEX `item_code` (`item_code` ASC) VISIBLE,
  INDEX `category_id` (`category_id` ASC) VISIBLE,
  CONSTRAINT `discounts_ibfk_1`
    FOREIGN KEY (`item_code`)
    REFERENCES `SYOS`.`Items` (`item_code`),
  CONSTRAINT `discounts_ibfk_2`
    FOREIGN KEY (`category_id`)
    REFERENCES `SYOS`.`Categories` (`category_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`SYOS_Store`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`SYOS_Store` (
  `store_id` INT NOT NULL AUTO_INCREMENT,
  `item_code` VARCHAR(10) NOT NULL,
  `purchase_date` DATE NOT NULL,
  `quantity` INT NOT NULL,
  `expiry_date` DATE NOT NULL,
  PRIMARY KEY (`store_id`),
  INDEX `item_code` (`item_code` ASC) VISIBLE,
  CONSTRAINT `syos_store_ibfk_1`
    FOREIGN KEY (`item_code`)
    REFERENCES `SYOS`.`Items` (`item_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Shelf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Shelf` (
  `shelf_id` INT NOT NULL AUTO_INCREMENT,
  `item_code` VARCHAR(10) NOT NULL,
  `quantity` INT NOT NULL,
  `expiry_date` DATE NOT NULL,
  PRIMARY KEY (`shelf_id`),
  INDEX `item_code` (`item_code` ASC) VISIBLE,
  CONSTRAINT `shelf_ibfk_1`
    FOREIGN KEY (`item_code`)
    REFERENCES `SYOS`.`Items` (`item_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Stock` (
  `stock_id` INT NOT NULL AUTO_INCREMENT,
  `item_code` VARCHAR(10) NOT NULL,
  `purchase_date` DATE NOT NULL,
  `quantity` INT NOT NULL,
  `expiry_date` DATE NOT NULL,
  PRIMARY KEY (`stock_id`),
  INDEX `item_code` (`item_code` ASC) VISIBLE,
  CONSTRAINT `stock_ibfk_1`
    FOREIGN KEY (`item_code`)
    REFERENCES `SYOS`.`Items` (`item_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `SYOS`.`Transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SYOS`.`Transactions` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `bill_id` INT NOT NULL,
  `transaction_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  INDEX `bill_id` (`bill_id` ASC) VISIBLE,
  CONSTRAINT `transactions_ibfk_1`
    FOREIGN KEY (`bill_id`)
    REFERENCES `SYOS`.`Bills` (`bill_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `SYOS`.`Users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
