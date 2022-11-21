CREATE TABLE `funcionario` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`nome` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`email` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`data_admissao` DATE NOT NULL,
	`saldo_de_ferias` INT(10) NOT NULL DEFAULT '0',
	`id_equipe` INT(10) NOT NULL DEFAULT '0',
	`senha` VARCHAR(100) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_0900_ai_ci',
	`cidade` VARCHAR(100) NOT NULL DEFAULT '0' COLLATE 'utf8mb4_0900_ai_ci',
	INDEX `Index 1` (`id`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
