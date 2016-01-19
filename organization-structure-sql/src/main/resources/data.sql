-- id, name, last_name, position, salary, sex
INSERT INTO person VALUES (0, 'Frodo', 'Baggins', 0, 9000, 1),
(1, 'Bilbo', 'Baggins', 1, 10000, 1),
(2, 'Sam', 'Gamgee', 1 ,11000, 1),
(3, 'Meriadoc', 'Brandybuck', 0, 12000, 1),
(4, 'Lucy', 'Valinor', 2, 13000, 0),
(5, 'Eva', 'Celebrimbor', 2, 14000, 0),
(6, 'Charlie', 'Big', 2, 15000, 1),
(7, 'Mad', 'Kaz', 3, 16000, 1),
(8, 'Crazy', 'Stefan', 3, 17000, 1),
(9, 'Uncle', 'Bob', 4, 20000, 1),
(10, 'Stan', 'Marsh', 1, 9000, 1),
(11, 'Kyle', 'Broflovski', 1, 9000, 1),
(12, 'Eric', 'Cartman', 1, 9000, 1),
(13, 'Kenny', 'McCormick', 1, 9000, 1);

-- id, name, person_id
INSERT INTO employee VALUES ('frodo.baggins', 0),
('bilbo.baggins', 1),
('sam.gamgee', 2),
('meriadoc.randybuck', 3),
('lucy.valinor', 4),
('eva.celebrimbor', 5),
('charlie.big', 6),
('mad.kaz', 7),
('crazy.stefan', 8),
('uncle.bob', 9),
('stan.marsh', 10),
('kyle.broflovski', 11),
('eric.cartman', 12),
('kenny.mcCormick', 13);

-- id, name, root_id
INSERT INTO hierarchy VALUES ('2013'),
('2014'),
('2015'),
('2016');

-- subordinate_id, hierarchy_id, boss_id
INSERT INTO hierarchy_employees VALUES (0, 'kenny.mcCormick', '2013', NULL);
INSERT INTO hierarchy_employees VALUES (1, 'bilbo.baggins', '2013', 'kenny.mcCormick');
INSERT INTO hierarchy_employees VALUES (2, 'stan.marsh', '2013', 'kenny.mcCormick');
INSERT INTO hierarchy_employees VALUES (3, 'eva.celebrimbor', '2013', 'stan.marsh');
INSERT INTO hierarchy_employees VALUES (4, 'lucy.valinor', '2013', 'stan.marsh');
INSERT INTO hierarchy_employees VALUES (5, 'sam.gamgee', '2013', 'stan.marsh');
INSERT INTO hierarchy_employees VALUES (6, 'bilbo.baggins', '2013', 'kenny.mcCormick');
INSERT INTO hierarchy_employees VALUES (7, 'meriadoc.randybuck', '2013', 'bilbo.baggins');
INSERT INTO hierarchy_employees VALUES (8, 'charlie.big', '2013', 'bilbo.baggins');
INSERT INTO hierarchy_employees VALUES (9, 'crazy.stefan', '2013', 'bilbo.baggins');
INSERT INTO hierarchy_employees VALUES (10, 'kyle.broflovski', '2013', 'eva.celebrimbor');
INSERT INTO hierarchy_employees VALUES (11, 'eric.cartman', '2013', 'eva.celebrimbor');