--
-- Table structure for table `date_dimension`
--

DROP TABLE IF EXISTS `data_dimension`;

CREATE TABLE `data_dimension`(
	`data_key` int(11) NOT NULL,
	`dia` int(11) NOT NULL,
	`semana` int(11) NOT NULL,
	`mes` int(11) NOT NULL,
	`semestre` int(11) NOT NULL,
	`ano` int(11) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `user_dimension`
--

DROP TABLE IF EXISTS `user_dimension`;

CREATE TABLE `user_dimension` (
	`nif_key` varchar(9) NOT NULL,
    `nome` varchar(80) NOT NULL,
    `telefone` varchar(26) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `localizacao_dimension`
--

DROP TABLE IF EXISTS `localizacao_dimension`;

CREATE TABLE `localizacao_dimension` (
	`localizacao_key` varchar(255) NOT NULL,
	`morada` varchar(255) NOT NULL,
	`codigo` varchar(255) NOT NULL,
	`codigo_espaco` varchar(255) 
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `tempo_dimension`
--

DROP TABLE IF EXISTS `tempo_dimension`;

CREATE TABLE `tempo_dimension` (
	`tempo_key` int(11) NOT NULL,
	`hora_do_dia` int(11) NOT NULL,
	`minuto_do_dia` int(11) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `reservas_informacao`
--

DROP TABLE IF EXISTS `reservas_informacao`;

CREATE TABLE `reservas_informacao` (
	`montante` numeric(19,4) NOT NULL,
	`duracao` int(11) NOT NULL,
	`data_key` int(11) NOT NULL,
	`nif_key` varchar(9) NOT NULL,
	`localizacao_key` varchar(255) NOT NULL,
	`tempo_key` int(11) NOT NULL,
	KEY `idx_reservas` (`data_key`,`nif_key`,`localizacao_key`, `tempo_key`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=latin1;