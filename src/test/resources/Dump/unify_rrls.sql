-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 01, 2018 at 01:53 PM
-- Server version: 5.7.9-log
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `unify_rrls`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_file_upload`
--

CREATE TABLE IF NOT EXISTS `add_file_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) DEFAULT NULL,
  `file_data` varchar(100) DEFAULT NULL,
  `file_data_content_type` varchar(150) DEFAULT NULL,
  `ra_file_upload_id` int(11) DEFAULT NULL,
  `created_by` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `last_modified_by` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_AFU_FU_idx` (`ra_file_upload_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `add_file_upload`
--

INSERT INTO `add_file_upload` (`id`, `file_name`, `file_data`, `file_data_content_type`, `ra_file_upload_id`, `created_by`, `last_modified_by`, `created_date`, `last_modified_date`) VALUES
(1, 'UnifiBRS(2)-add.docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(2)/UnifiBRS(2)-add.docx', NULL, 24, 'admin', 'admin', '2018-01-23 07:41:42', '2018-01-23 07:41:42'),
(2, 'UnifiBRS(2)-add(1).docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(2)/UnifiBRS(2)-add(1).docx', NULL, 24, 'admin', 'admin', '2018-01-23 07:45:57', '2018-01-23 07:45:57'),
(3, 'UnifiBRS(2)-add(2).docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(2)/UnifiBRS(2)-add(2).docx', NULL, 24, 'admin', 'admin', '2018-01-23 07:47:18', '2018-01-23 07:47:18'),
(4, 'UnifiBRS(2)-add(3).docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(2)/UnifiBRS(2)-add(3).docx', NULL, 24, 'admin', 'admin', '2018-01-23 09:02:25', '2018-01-23 09:02:25');

-- --------------------------------------------------------

--
-- Table structure for table `databasechangelog`
--

CREATE TABLE IF NOT EXISTS `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2017-11-09 13:20:47', 1, 'EXECUTED', '7:289534c28c020e6fefc04422562ad4a2', 'createTable tableName=jhi_user; createIndex indexName=idx_user_login, tableName=jhi_user; createIndex indexName=idx_user_email, tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableN...', '', NULL, '3.5.3', NULL, NULL, '0213846772'),
('20170902022119-1', 'jhipster', 'config/liquibase/changelog/20170902022119_added_entity_Employee.xml', '2017-11-10 11:52:34', 2, 'EXECUTED', '7:85c62f78b01cccab3b34ced79f2b1b12', 'createTable tableName=mp_client_role', '', NULL, '3.5.3', NULL, NULL, '0294954551'),
('20170902022121-1', 'jhipster', 'config/liquibase/changelog/20170902022121_added_entity_RoleMaster.xml', '2017-11-10 16:09:38', 3, 'EXECUTED', '7:1d7d29716599636b5d5c763b4f80a5d4', 'createTable tableName=role_master', '', NULL, '3.5.3', NULL, NULL, '0310378084'),
('20170902022121-1', 'jhipster', 'config/liquibase/changelog/20170902022121_added_entity_StrategyMaster.xml', '2017-11-10 16:14:23', 4, 'EXECUTED', '7:3459be3c384033fb358916146d93eadc', 'createTable tableName=strategy_master; dropDefaultValue columnName=date_active, tableName=strategy_master; dropDefaultValue columnName=updated_date, tableName=strategy_master', '', NULL, '3.5.3', NULL, NULL, '0310662947'),
('20170902022115-1', 'jhipster', 'config/liquibase/changelog/20170902022115_added_entity_OpportunityMaster.xml', '2017-11-10 16:56:48', 5, 'EXECUTED', '7:0b34644e6eb127cd3b95684963b31f4a', 'createTable tableName=opportunity_master; dropDefaultValue columnName=updated_date, tableName=opportunity_master', '', NULL, '3.5.3', NULL, NULL, '0313208502'),
('20170902022115-2', 'jhipster', 'config/liquibase/changelog/20170902022115_added_entity_constraints_OpportunityMaster.xml', '2017-11-10 16:56:48', 6, 'EXECUTED', '7:f89d14093eddc6f4e72040245b0654a8', 'addForeignKeyConstraint baseTableName=opportunity_master, constraintName=fk_opportunity_master_opportunity_master_id, referencedTableName=strategy_master', '', NULL, '3.5.3', NULL, NULL, '0313208502');

-- --------------------------------------------------------

--
-- Table structure for table `databasechangeloglock`
--

CREATE TABLE IF NOT EXISTS `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'1', '2017-11-17 14:53:35', 'girija-PC (192.168.1.10)');

-- --------------------------------------------------------

--
-- Table structure for table `file_upload_comments`
--

CREATE TABLE IF NOT EXISTS `file_upload_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_comments` varchar(500) DEFAULT NULL,
  `ra_file_upload_id` int(11) DEFAULT NULL,
  `add_file_upload_id` int(11) DEFAULT NULL,
  `created_by` varchar(105) CHARACTER SET latin1 DEFAULT NULL,
  `last_modified_by` varchar(105) CHARACTER SET latin1 DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FUC_FU_idx` (`ra_file_upload_id`),
  KEY `FK_FUC_AFU_idx` (`add_file_upload_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `file_upload_comments`
--

INSERT INTO `file_upload_comments` (`id`, `file_comments`, `ra_file_upload_id`, `add_file_upload_id`, `created_by`, `last_modified_by`, `created_date`, `last_modified_date`) VALUES
(2, 'testing', 1, NULL, 'admin', 'admin', '2017-11-21 20:56:54', '2017-11-21 20:56:54'),
(3, 'Change request', 1, NULL, NULL, NULL, NULL, NULL),
(6, 'Bank statement has been reviewed', 6, NULL, 'admin', NULL, '2017-12-03 20:56:54', NULL),
(11, 'Bank Book format is it fixed?', 6, NULL, 'user', NULL, '2017-12-04 06:30:00', NULL),
(12, 'How many bank statement formats?', 6, NULL, 'admin', NULL, '2017-12-18 09:11:39', NULL),
(14, 'hi', 7, NULL, 'admin', 'admin', '2017-12-20 10:13:27', '2017-12-20 10:13:27'),
(15, 'hi', 24, NULL, 'admin', 'admin', '2018-01-22 07:46:00', '2018-01-22 07:46:00'),
(16, 'how', 24, NULL, 'admin', 'admin', '2018-01-23 07:41:01', '2018-01-23 07:41:01'),
(17, '2', 6, NULL, 'admin', 'admin', '2018-01-23 11:57:19', '2018-01-23 11:57:19'),
(18, 'DFa', 23, NULL, 'admin', 'admin', '2018-01-24 07:09:43', '2018-01-24 07:09:43');

-- --------------------------------------------------------

--
-- Table structure for table `history_opportunity_status`
--

CREATE TABLE IF NOT EXISTS `history_opportunity_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opp_id` int(11) DEFAULT NULL,
  `created_by` varchar(45) NOT NULL,
  `created_time` varchar(45) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `action` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_OpportunityHistory` (`opp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `jhi_authority`
--

CREATE TABLE IF NOT EXISTS `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_CIO'),
('ROLE_DEALER'),
('ROLE_RA'),
('ROLE_SALE'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `jhi_persistent_audit_event`
--

CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=117 ;

--
-- Dumping data for table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'user', '2017-11-09 08:57:41', 'AUTHENTICATION_SUCCESS'),
(2, 'admin', '2017-11-09 09:07:41', 'AUTHENTICATION_SUCCESS'),
(3, 'user', '2017-11-10 15:56:13', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2017-11-10 16:06:26', 'AUTHENTICATION_SUCCESS'),
(5, 'admin', '2017-11-10 16:30:19', 'AUTHENTICATION_SUCCESS'),
(6, 'user', '2017-11-17 06:34:15', 'AUTHENTICATION_SUCCESS'),
(7, 'user', '2017-11-17 06:34:16', 'AUTHENTICATION_SUCCESS'),
(8, 'user', '2017-11-19 09:10:36', 'AUTHENTICATION_SUCCESS'),
(9, 'user', '2017-11-20 07:21:41', 'AUTHENTICATION_SUCCESS'),
(10, 'admin', '2017-11-20 07:22:32', 'AUTHENTICATION_SUCCESS'),
(11, 'admin', '2017-11-20 12:05:57', 'AUTHENTICATION_SUCCESS'),
(12, 'admin', '2017-11-21 11:37:45', 'AUTHENTICATION_SUCCESS'),
(13, 'admin', '2017-11-21 20:20:16', 'AUTHENTICATION_SUCCESS'),
(14, 'admin', '2017-11-22 10:39:27', 'AUTHENTICATION_SUCCESS'),
(15, 'admin', '2017-11-23 11:58:42', 'AUTHENTICATION_SUCCESS'),
(16, 'user', '2017-11-24 10:09:47', 'AUTHENTICATION_SUCCESS'),
(17, 'admin', '2017-11-24 10:17:37', 'AUTHENTICATION_SUCCESS'),
(18, 'admin', '2017-11-27 12:24:35', 'AUTHENTICATION_SUCCESS'),
(19, 'admin', '2017-11-29 09:19:00', 'AUTHENTICATION_SUCCESS'),
(20, 'admin', '2017-11-29 10:49:07', 'AUTHENTICATION_SUCCESS'),
(21, 'admin', '2017-11-30 10:58:54', 'AUTHENTICATION_SUCCESS'),
(22, 'admin', '2017-11-30 11:49:04', 'AUTHENTICATION_SUCCESS'),
(23, 'admin', '2017-11-30 11:59:55', 'AUTHENTICATION_SUCCESS'),
(24, 'admin', '2017-12-04 07:32:58', 'AUTHENTICATION_SUCCESS'),
(25, 'user', '2017-12-04 07:33:55', 'AUTHENTICATION_SUCCESS'),
(26, 'admin', '2017-12-04 09:52:51', 'AUTHENTICATION_SUCCESS'),
(27, 'user', '2017-12-04 09:54:26', 'AUTHENTICATION_SUCCESS'),
(28, 'user', '2017-12-04 09:59:05', 'AUTHENTICATION_SUCCESS'),
(29, 'user', '2017-12-04 10:29:24', 'AUTHENTICATION_SUCCESS'),
(30, 'user', '2017-12-04 10:38:02', 'AUTHENTICATION_SUCCESS'),
(31, 'user', '2017-12-04 11:15:01', 'AUTHENTICATION_SUCCESS'),
(32, 'user', '2017-12-04 12:10:35', 'AUTHENTICATION_SUCCESS'),
(33, 'admin', '2017-12-04 12:10:54', 'AUTHENTICATION_SUCCESS'),
(34, 'admin', '2017-12-04 12:15:04', 'AUTHENTICATION_SUCCESS'),
(35, 'admin', '2017-12-04 12:34:18', 'AUTHENTICATION_SUCCESS'),
(36, 'admin', '2017-12-06 05:41:46', 'AUTHENTICATION_SUCCESS'),
(37, 'user', '2017-12-06 05:42:05', 'AUTHENTICATION_SUCCESS'),
(38, 'admin', '2017-12-06 05:48:40', 'AUTHENTICATION_SUCCESS'),
(39, 'user', '2017-12-06 05:48:53', 'AUTHENTICATION_SUCCESS'),
(40, 'user', '2017-12-06 05:50:29', 'AUTHENTICATION_SUCCESS'),
(41, 'admin', '2017-12-06 05:51:14', 'AUTHENTICATION_SUCCESS'),
(42, 'admin', '2017-12-06 06:02:13', 'AUTHENTICATION_SUCCESS'),
(43, 'admin', '2017-12-06 07:13:59', 'AUTHENTICATION_SUCCESS'),
(44, 'baidik', '2017-12-06 09:26:58', 'AUTHENTICATION_SUCCESS'),
(45, 'admin', '2017-12-06 10:14:26', 'AUTHENTICATION_SUCCESS'),
(46, 'admin', '2017-12-06 10:21:41', 'AUTHENTICATION_SUCCESS'),
(47, 'girija', '2017-12-06 11:19:18', 'AUTHENTICATION_SUCCESS'),
(48, 'girija', '2017-12-06 12:07:49', 'AUTHENTICATION_SUCCESS'),
(49, 'admin', '2017-12-07 06:05:02', 'AUTHENTICATION_SUCCESS'),
(50, 'admin', '2017-12-07 07:36:10', 'AUTHENTICATION_SUCCESS'),
(51, 'baidik', '2017-12-07 07:37:48', 'AUTHENTICATION_SUCCESS'),
(52, 'admin', '2017-12-12 08:57:35', 'AUTHENTICATION_SUCCESS'),
(53, 'admin', '2017-12-12 12:01:01', 'AUTHENTICATION_SUCCESS'),
(54, 'baidik', '2017-12-13 04:34:06', 'AUTHENTICATION_SUCCESS'),
(55, 'baidik', '2017-12-13 04:49:20', 'AUTHENTICATION_SUCCESS'),
(56, 'baidik', '2017-12-13 05:01:51', 'AUTHENTICATION_SUCCESS'),
(57, 'admin', '2017-12-13 05:02:16', 'AUTHENTICATION_SUCCESS'),
(58, 'baidik', '2017-12-13 05:06:33', 'AUTHENTICATION_SUCCESS'),
(59, 'admin', '2017-12-13 05:09:12', 'AUTHENTICATION_SUCCESS'),
(60, 'admin', '2017-12-13 05:21:55', 'AUTHENTICATION_SUCCESS'),
(61, 'admin', '2017-12-13 05:24:23', 'AUTHENTICATION_SUCCESS'),
(62, 'baidik', '2017-12-13 05:24:51', 'AUTHENTICATION_SUCCESS'),
(63, 'admin', '2017-12-13 05:25:06', 'AUTHENTICATION_SUCCESS'),
(64, 'baidik', '2017-12-13 05:29:26', 'AUTHENTICATION_SUCCESS'),
(65, 'admin', '2017-12-13 05:38:49', 'AUTHENTICATION_SUCCESS'),
(66, 'admin', '2017-12-13 05:42:36', 'AUTHENTICATION_SUCCESS'),
(67, 'baidik', '2017-12-13 05:43:20', 'AUTHENTICATION_SUCCESS'),
(68, 'baidik', '2017-12-13 05:46:28', 'AUTHENTICATION_SUCCESS'),
(69, 'admin', '2017-12-13 05:52:46', 'AUTHENTICATION_SUCCESS'),
(70, 'baidik', '2017-12-13 05:54:51', 'AUTHENTICATION_SUCCESS'),
(71, 'admin', '2017-12-13 10:25:14', 'AUTHENTICATION_SUCCESS'),
(72, 'admin', '2017-12-13 10:27:43', 'AUTHENTICATION_SUCCESS'),
(73, 'admin', '2017-12-13 11:01:23', 'AUTHENTICATION_SUCCESS'),
(74, 'admin', '2017-12-14 05:09:34', 'AUTHENTICATION_SUCCESS'),
(75, 'admin', '2017-12-14 05:10:06', 'AUTHENTICATION_SUCCESS'),
(76, 'girija.p', '2017-12-14 05:12:23', 'AUTHENTICATION_SUCCESS'),
(77, 'admin', '2017-12-14 05:12:58', 'AUTHENTICATION_SUCCESS'),
(78, 'admin', '2017-12-15 05:30:11', 'AUTHENTICATION_SUCCESS'),
(79, 'admin', '2017-12-18 06:08:17', 'AUTHENTICATION_SUCCESS'),
(80, 'admin', '2017-12-21 10:03:32', 'AUTHENTICATION_SUCCESS'),
(81, 'user', '2018-01-02 05:17:21', 'AUTHENTICATION_SUCCESS'),
(82, 'admin', '2018-01-02 05:23:02', 'AUTHENTICATION_SUCCESS'),
(83, 'sai', '2018-01-02 05:58:55', 'AUTHENTICATION_FAILURE'),
(84, 'admin', '2018-01-02 06:00:10', 'AUTHENTICATION_SUCCESS'),
(85, 'girija', '2018-01-02 06:07:05', 'AUTHENTICATION_SUCCESS'),
(86, 'user', '2018-01-04 09:20:19', 'AUTHENTICATION_SUCCESS'),
(87, 'admin', '2018-01-08 09:51:50', 'AUTHENTICATION_SUCCESS'),
(88, 'admin', '2018-01-08 10:09:49', 'AUTHENTICATION_SUCCESS'),
(89, 'admin', '2018-01-08 12:08:15', 'AUTHENTICATION_SUCCESS'),
(90, 'admin', '2018-01-08 12:26:38', 'AUTHENTICATION_SUCCESS'),
(91, 'admin', '2018-01-23 13:06:44', 'AUTHENTICATION_SUCCESS'),
(92, 'girija.p', '2018-01-23 13:44:07', 'AUTHENTICATION_FAILURE'),
(93, 'girija.p', '2018-01-23 13:45:28', 'AUTHENTICATION_SUCCESS'),
(94, 'girija', '2018-01-23 13:48:43', 'AUTHENTICATION_SUCCESS'),
(95, 'girija.p', '2018-01-23 13:52:42', 'AUTHENTICATION_SUCCESS'),
(96, 'girija', '2018-01-24 07:09:10', 'AUTHENTICATION_SUCCESS'),
(97, 'admin', '2018-01-24 07:09:22', 'AUTHENTICATION_SUCCESS'),
(98, 'girija.p', '2018-01-24 07:54:20', 'AUTHENTICATION_SUCCESS'),
(99, 'girija', '2018-01-24 07:57:12', 'AUTHENTICATION_SUCCESS'),
(100, 'admin', '2018-01-24 08:59:42', 'AUTHENTICATION_SUCCESS'),
(101, 'girija', '2018-01-24 10:21:52', 'AUTHENTICATION_SUCCESS'),
(102, 'admin', '2018-01-24 10:23:50', 'AUTHENTICATION_SUCCESS'),
(103, 'girija', '2018-01-24 10:28:31', 'AUTHENTICATION_SUCCESS'),
(104, 'admin', '2018-01-24 10:33:21', 'AUTHENTICATION_SUCCESS'),
(105, 'girija.p', '2018-01-24 10:36:50', 'AUTHENTICATION_SUCCESS'),
(106, 'girija', '2018-01-24 10:44:25', 'AUTHENTICATION_SUCCESS'),
(107, 'admin', '2018-01-25 10:48:51', 'AUTHENTICATION_SUCCESS'),
(108, 'girija.p', '2018-01-29 06:24:31', 'AUTHENTICATION_SUCCESS'),
(109, 'girija', '2018-01-29 06:24:52', 'AUTHENTICATION_SUCCESS'),
(110, 'girija.p', '2018-01-29 07:16:15', 'AUTHENTICATION_SUCCESS'),
(111, 'girija', '2018-01-29 07:16:35', 'AUTHENTICATION_SUCCESS'),
(112, 'admin', '2018-01-29 09:33:36', 'AUTHENTICATION_SUCCESS'),
(113, 'girija', '2018-01-29 10:02:23', 'AUTHENTICATION_SUCCESS'),
(114, 'admin', '2018-01-29 13:25:18', 'AUTHENTICATION_SUCCESS'),
(115, 'girija.p', '2018-02-01 06:26:21', 'AUTHENTICATION_SUCCESS'),
(116, 'girija.p', '2018-02-01 11:14:30', 'AUTHENTICATION_SUCCESS');

-- --------------------------------------------------------

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

CREATE TABLE IF NOT EXISTS `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jhi_persistent_audit_evt_data`
--

INSERT INTO `jhi_persistent_audit_evt_data` (`event_id`, `name`, `value`) VALUES
(83, 'message', 'Bad credentials'),
(83, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(92, 'message', 'Bad credentials'),
(92, 'type', 'org.springframework.security.authentication.BadCredentialsException');

-- --------------------------------------------------------

--
-- Table structure for table `jhi_user`
--

CREATE TABLE IF NOT EXISTS `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `mapped_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`),
  KEY `FK_UM_RM_idx` (`role_id`),
  KEY `FK_UM-UMM_idx` (`mapped_user_id`),
  KEY `FK_UM_UMM_idx` (`mapped_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`, `role_id`, `mapped_user_id`) VALUES
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', '', b'1', 'en', NULL, NULL, 'system', '2017-11-09 07:50:46', NULL, 'system', NULL, NULL, NULL),
(2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', b'1', 'en', NULL, NULL, 'system', '2017-11-09 07:50:46', NULL, 'system', NULL, NULL, NULL),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'en', NULL, NULL, 'system', '2017-11-09 07:50:46', NULL, 'system', NULL, NULL, NULL),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'en', NULL, '90477917256127554975', 'system', '2017-11-09 07:50:46', '2017-11-29 09:26:46', 'admin', '2017-11-30 12:01:53', NULL, NULL),
(19, 'baidik', '$2a$10$lQEaBdsL2YtycBt2ToxlZ..EzZeHtq7oxTVvORsJ3N2tQ80.WReX.', 'Baidik', 'Sarkar', 'girija@gmail.com', NULL, b'1', 'en', '46360879480707060302', NULL, 'anonymousUser', '2017-12-06 10:48:43', NULL, 'anonymousUser', '2017-12-06 09:26:28', NULL, NULL),
(24, 'girija.p', '$2a$10$O1TepOthHddH9fvzV3iz8.Fo8qzCGGkrR5wPF6.BS4db6K9DZ1tHW', 'Girija', 'P', 'girija.p@noahdatatech.com', NULL, b'1', 'en', NULL, NULL, 'admin', '2017-12-14 12:15:05', NULL, 'girija', '2018-01-23 13:45:10', 1, NULL),
(27, 'girija', '$2a$10$ELly7Hmq4m5luScjjFpaHeILmU28Nb.pmdn8Q8AfuEIvnV5KFUopu', 'Girija', 'P', 'girija0209@gmail.com', NULL, b'1', 'en', NULL, NULL, 'admin', '2018-01-02 06:05:15', NULL, 'admin', '2018-01-24 07:53:29', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `jhi_user_authority`
--

CREATE TABLE IF NOT EXISTS `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(3, 'ROLE_ADMIN'),
(27, 'ROLE_CIO'),
(24, 'ROLE_RA'),
(1, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(19, 'ROLE_USER'),
(24, 'ROLE_USER'),
(27, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `opportunity_master`
--

CREATE TABLE IF NOT EXISTS `opportunity_master` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `opp_description` varchar(255) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `master_name` int(10) DEFAULT NULL,
  `opp_status` varchar(30) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_OM_ON_idx` (`master_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `opportunity_master`
--

INSERT INTO `opportunity_master` (`id`, `opp_description`, `created_by`, `last_modified_by`, `created_date`, `last_modified_date`, `master_name`, `opp_status`, `description`) VALUES
(7, 'Financial institution', 'user', 'girija', NULL, '2018-01-29 10:45:19', 7, 'Approve', NULL),
(9, 'Software company', 'admin', 'user', '2017-12-04 09:46:07', '2017-12-04 09:46:07', 15, NULL, NULL),
(12, 'The oil and gas industry', 'admin', 'admin', NULL, '2017-12-06 06:12:50', 1, NULL, NULL),
(13, 'Software company', 'admin', 'girija.p', NULL, '2018-01-24 07:07:29', 11, 'Rejected', NULL),
(20, 'The oil and gas industry', 'admin', 'admin', NULL, '2017-12-06 06:13:09', 1, NULL, NULL),
(24, 'Trading distri', 'admin', 'admin', NULL, '2017-12-14 06:13:30', 12, NULL, NULL),
(26, 'test', 'admin', 'girija', NULL, '2018-01-29 13:24:17', 5, NULL, NULL),
(27, 'furniture', 'girija', 'girija', NULL, '2018-01-29 13:14:16', 9, NULL, NULL);

--
-- Triggers `opportunity_master`
--
DROP TRIGGER IF EXISTS `tri_after_opportunity_status_update`;
DELIMITER //
CREATE TRIGGER `tri_after_opportunity_status_update` AFTER UPDATE ON `opportunity_master`
 FOR EACH ROW begin
insert into history_opportunity_status(opp_id,created_by,created_time,description,action) values(new.id,new.last_modified_by,now(),new.description,new.opp_status);
end
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `opportunity_name`
--

CREATE TABLE IF NOT EXISTS `opportunity_name` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opp_name` varchar(100) DEFAULT NULL,
  `sector_type` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `opportunity_name`
--

INSERT INTO `opportunity_name` (`id`, `opp_name`, `sector_type`) VALUES
(1, 'ABAN OFFSHORE LTD', 'Exploration & Production'),
(2, 'BCL FORGINGS LTD', 'Auto Parts & Equipment'),
(3, 'ICDS LTD', 'Finance (including NBFCs)'),
(4, 'DELTA MAGNETS LTD', 'Other Elect.Equip./ Prod'),
(5, 'Effingo Textile & Trading Limited', 'Textiles'),
(6, 'Ghushine Fintrrade Ocean Ltd', 'Textiles'),
(7, 'VIJAYA BANK', 'Banks'),
(8, 'KLG SYSTEL LTD', 'IT Consulting & Software'),
(9, 'MSR INDIA LTD', 'Houseware'),
(10, 'OPTO CIRCUITS (INDIA) LTD', 'Medical Equipment'),
(11, 'Polaris Consulting & Services Limited', 'IT Software Products'),
(12, 'RUIA AQUACULTURE FARMS LTD', 'Comm.Trading & Distribution'),
(13, 'SAKTHI SUGARS LTD', 'Sugar'),
(14, 'TASHI INDIA LTD', 'Misc.Commercial Services'),
(15, 'DHRUV ESTATES LTD', 'Realty'),
(16, 'WEST COAST PAPER MILLS LTD', 'Paper & Paper Products'),
(17, 'GALAXY BEARINGS LTD', 'Other Industrial Goods'),
(18, 'ASIS LOGISTICS LIMITED', 'Transportation - Logistics'),
(19, 'SANGHI CORPORATE SERVICES LTD', 'Finance (including NBFCs)'),
(20, 'INTEGRA SWITCHGEAR LTD', 'Other Elect.Equip./ Prod');

-- --------------------------------------------------------

--
-- Table structure for table `ra_file_upload`
--

CREATE TABLE IF NOT EXISTS `ra_file_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(300) DEFAULT NULL,
  `file_data` varchar(155) DEFAULT NULL,
  `file_data_content_type` varchar(100) DEFAULT NULL,
  `add_file_flag` int(11) DEFAULT NULL,
  `opportunity_master_id` int(11) DEFAULT NULL,
  `file_status` varchar(20) DEFAULT NULL,
  `created_by` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `last_modified_by` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FUP_OM_idx` (`opportunity_master_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `ra_file_upload`
--

INSERT INTO `ra_file_upload` (`id`, `file_name`, `file_data`, `file_data_content_type`, `add_file_flag`, `opportunity_master_id`, `file_status`, `created_by`, `last_modified_by`, `created_date`, `last_modified_date`) VALUES
(1, 'Unifi1.docx', 'src\\main\\resources\\fileUpload\\Unifi1.docx', NULL, NULL, 20, NULL, 'admin', 'admin', '2017-11-21 12:30:41', '2017-11-21 12:30:41'),
(2, 'Unifi1.docx', 'src\\main\\resources\\fileUpload\\Unifi1.docx', NULL, NULL, NULL, NULL, 'admin', 'admin', '2017-11-27 11:43:29', '2017-11-27 11:43:29'),
(6, 'Unifi Sample.docx', 'src\\main\\resources\\fileUpload\\Unifi Sample.docx', NULL, NULL, 7, NULL, 'admin', 'admin', '2017-11-27 12:29:12', '2017-11-27 12:29:12'),
(7, 'DFA samples.docx', 'src\\main\\resources\\fileUpload\\DFA samples.docx', NULL, NULL, 7, NULL, NULL, NULL, '2017-12-19 09:56:14', NULL),
(22, 'UnifiBRS.docx', 'src/main/resources/VIJAYA BANK/user/UnifiBRS.docx', NULL, NULL, 7, NULL, 'user', 'user', '2018-01-09 06:08:10', '2018-01-08 06:21:46'),
(23, 'UnifiBRS(1).docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(1).docx', NULL, NULL, 7, NULL, 'admin', 'admin', '2018-01-09 06:09:21', '2018-01-09 06:09:21'),
(24, 'UnifiBRS(2).docx', 'src/main/resources/VIJAYA BANK/admin/UnifiBRS(2).docx', NULL, NULL, 7, 'RE', 'admin', 'admin', '2018-01-22 09:05:47', '2018-01-18 13:51:48'),
(25, 'Series NAV Details Nov 20178.xlsx', 'src\\main\\resources\\VIJAYA BANK\\admin\\xlsx\\Series NAV Details Nov 20178.xlsx', NULL, NULL, 7, NULL, 'admin', 'admin', '2018-01-18 08:05:48', '2018-01-18 08:05:48'),
(26, 'UnifiBRS.docx', 'src/main/resources/VIJAYA BANK/admin/docx/UnifiBRS.docx', NULL, NULL, 7, NULL, 'admin', 'admin', '2018-01-23 11:57:43', '2018-01-23 11:57:43'),
(27, '1', 'src\\main\\resources\\VIJAYA BANK\\girija.p\\image\\1.png', 'png', NULL, 7, NULL, 'girija.p', 'girija.p', '2018-02-01 06:26:41', '2018-02-01 06:26:41');

-- --------------------------------------------------------

--
-- Table structure for table `role_master`
--

CREATE TABLE IF NOT EXISTS `role_master` (
  `id` int(20) NOT NULL,
  `role_code` varchar(30) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_master`
--

INSERT INTO `role_master` (`id`, `role_code`, `role_name`) VALUES
(1, 'R0001', 'Research Analyst'),
(3, 'R0002', 'RM'),
(4, 'R0003', 'CIO'),
(5, 'R0004', 'Dealer'),
(6, 'R0005', 'Sales'),
(7, 'R0006', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `stock_market`
--

CREATE TABLE IF NOT EXISTS `stock_market` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opportunity_id` int(11) DEFAULT NULL,
  `weight` float(15,5) DEFAULT NULL,
  `cmp` float(15,5) DEFAULT NULL,
  `mcap` float(15,5) DEFAULT NULL,
  `pat_curryear` float(15,5) DEFAULT NULL,
  `pat_nxtone` float(15,5) DEFAULT NULL,
  `pat_nxtsecond` float(15,5) DEFAULT NULL,
  `pe_curryear` float(15,5) DEFAULT NULL,
  `pe_nxtone` float(15,5) DEFAULT NULL,
  `pe_nxtsecond` float(15,5) DEFAULT NULL,
  `roe_nxtone` float(15,5) DEFAULT NULL,
  `roe_nxtsecond` float(15,5) DEFAULT NULL,
  `de_nxtyear` float(15,5) DEFAULT NULL,
  `pat_growth_nxtone` float(15,5) DEFAULT NULL,
  `pat_groth_nxtsecond` float(15,5) DEFAULT NULL,
  `port_pe_curryear` float(15,5) DEFAULT NULL,
  `port_pe_nxtyear` float(15,5) DEFAULT NULL,
  `earnings_nxtone` float(15,5) DEFAULT NULL,
  `earnings_nextsecond` float(15,5) DEFAULT NULL,
  `wt.avgmcap` float(15,5) DEFAULT NULL,
  `roe` float(15,5) DEFAULT NULL,
  `peg-oj_nxtyear` float(15,5) DEFAULT NULL,
  `peg_peg_nxtyear` float(15,5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `strategy_mapping`
--

CREATE TABLE IF NOT EXISTS `strategy_mapping` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `opp_master_id` int(11) DEFAULT NULL,
  `strategy_master_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_SM_OPP_idx` (`opp_master_id`),
  KEY `FK_SMM_SM_idx` (`strategy_master_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=106 ;

--
-- Dumping data for table `strategy_mapping`
--

INSERT INTO `strategy_mapping` (`id`, `opp_master_id`, `strategy_master_id`) VALUES
(1, 20, 2),
(2, 20, 3),
(5, 24, 2),
(6, 24, 8),
(7, 24, 2),
(22, 7, 2),
(92, 27, 2),
(104, 26, 3),
(105, 26, 5);

-- --------------------------------------------------------

--
-- Table structure for table `strategy_master`
--

CREATE TABLE IF NOT EXISTS `strategy_master` (
  `id` int(20) NOT NULL,
  `strategy_code` varchar(20) DEFAULT NULL,
  `strategy_name` varchar(40) DEFAULT NULL,
  `s_status` int(11) DEFAULT NULL,
  `date_active` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(50) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `strategy_master`
--

INSERT INTO `strategy_master` (`id`, `strategy_code`, `strategy_name`, `s_status`, `date_active`, `created_by`, `last_modified_by`, `created_date`, `last_modified_date`) VALUES
(2, 'S001', 'Event Arbitrage Fund', 1, '2017-11-25 18:30:00', NULL, 'admin', '2017-11-29 10:52:15', '2017-11-29 10:52:15'),
(3, 'S002', 'Insider Shadow Fund', 1, '2017-11-26 18:30:00', NULL, 'admin', '2017-11-29 10:52:38', '2017-11-29 10:52:38'),
(5, 'S003', 'India Sector Trend Fund', 1, '2017-11-26 18:30:00', 'admin', 'admin', '2017-11-29 10:53:05', '2017-11-29 10:53:05'),
(6, 'S004', 'Deep Value Discount Fund', 1, '2017-12-22 09:16:39', 'admin', 'admin', '2017-12-22 09:16:39', '2017-11-29 10:53:31'),
(7, 'S005', 'Holdo Co Fund', 1, '2017-12-22 09:16:43', 'admin', 'admin', '2017-12-22 09:16:43', '2017-11-29 10:54:09'),
(8, 'S006', 'Spin Off Fund', 1, '2017-11-26 18:30:00', 'admin', 'admin', '2017-11-29 10:54:35', '2017-11-29 10:54:35'),
(9, 'S007', 'APJ 20', 1, '2017-11-26 18:30:00', 'admin', 'admin', '2017-11-29 10:54:56', '2017-11-29 10:54:56'),
(10, 'S008', 'The Green Fund', 1, '2017-12-22 07:21:45', 'admin', 'admin', '2017-12-22 07:22:10', '2017-11-29 10:55:20'),
(11, 'S009', 'Blended Fund', 1, '2017-12-22 09:39:32', NULL, NULL, '2017-12-22 09:39:32', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `add_file_upload`
--
ALTER TABLE `add_file_upload`
  ADD CONSTRAINT `FK_AFU_FU` FOREIGN KEY (`ra_file_upload_id`) REFERENCES `ra_file_upload` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `file_upload_comments`
--
ALTER TABLE `file_upload_comments`
  ADD CONSTRAINT `FK_FUC_AFU` FOREIGN KEY (`add_file_upload_id`) REFERENCES `add_file_upload` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_FUC_FU` FOREIGN KEY (`ra_file_upload_id`) REFERENCES `ra_file_upload` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `history_opportunity_status`
--
ALTER TABLE `history_opportunity_status`
  ADD CONSTRAINT `FK_OpportunityHistory` FOREIGN KEY (`opp_id`) REFERENCES `opportunity_master` (`id`);

--
-- Constraints for table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`);

--
-- Constraints for table `jhi_user`
--
ALTER TABLE `jhi_user`
  ADD CONSTRAINT `FK_UM_RM` FOREIGN KEY (`role_id`) REFERENCES `role_master` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_UM_UMM` FOREIGN KEY (`mapped_user_id`) REFERENCES `jhi_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Constraints for table `opportunity_master`
--
ALTER TABLE `opportunity_master`
  ADD CONSTRAINT `FK_OM_ON` FOREIGN KEY (`master_name`) REFERENCES `opportunity_name` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ra_file_upload`
--
ALTER TABLE `ra_file_upload`
  ADD CONSTRAINT `FK_FUP_OM` FOREIGN KEY (`opportunity_master_id`) REFERENCES `opportunity_master` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `strategy_mapping`
--
ALTER TABLE `strategy_mapping`
  ADD CONSTRAINT `FK_OMM_SM` FOREIGN KEY (`opp_master_id`) REFERENCES `opportunity_master` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_OMM_SMM` FOREIGN KEY (`strategy_master_id`) REFERENCES `strategy_master` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `update_status` ON SCHEDULE EVERY 1 SECOND STARTS '2017-12-22 13:02:04' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE strategy_master SET s_status=1 WHERE DATE(date_active)=CURDATE()$$

DELIMITER ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
