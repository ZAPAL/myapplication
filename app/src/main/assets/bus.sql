BEGIN TRANSACTION;
CREATE TABLE `bus` (
	`NUMBER`	INTEGER,
	`ROUTE`	TEXT,
	PRIMARY KEY(NUMBER)
);
INSERT INTO `bus` (NUMBER,ROUTE) VALUES (1,'База ОПС - ПДО - Кладбище русино'),
 (2,'С-п "Магистральный" - СПАБ'),
 (3,'М-н Северный - База ОПС'),
 (4,'С-п "Магистральный" - СПАБ'),
 (16,'Сквер Героя Карвата - Северный');
COMMIT;
