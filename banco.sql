CREATE DATABASE db_prova;

CREATE TABLE lancamento (
  oid INT NOT NULL AUTO_INCREMENT,
  dt_inicial DATETIME NOT NULL,
  dt_final DATETIME NOT NULL,
  vl_total DECIMAL(8,2) NOT NULL,
  observacao VARCHAR(1000) NULL,
  PRIMARY KEY (oid)
);



CREATE TABLE item (
    oid INT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(8,2) NOT NULL,
    PRIMARY KEY (oid)
);

CREATE TABLE lancamentoItem  (
    oid INT NOT NULL AUTO_INCREMENT,
    oid_lancamento INT NOT NULL,
    oid_item INT NOT NULL,
    PRIMARY KEY (oid),
    FOREIGN KEY (oid_lancamento)
        REFERENCES lancamento (oid)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (oid_item)
        REFERENCES item (oid)
        ON UPDATE CASCADE ON DELETE CASCADE
);

#POPULAR O BD

INSERT INTO item (descricao, valor) VALUES ('PS4', '1600');
INSERT INTO item (descricao, valor) VALUES ('XBOX', '1500');
INSERT INTO item (descricao, valor) VALUES ('NOTEBOOK', '2300');
INSERT INTO item (descricao, valor) VALUES ('IMPRESSORA', '500');
INSERT INTO item (descricao, valor) VALUES ('MONITOR', '550');
INSERT INTO item (descricao, valor) VALUES ('MOUSE', '30');
INSERT INTO item (descricao, valor) VALUES ('FONE', '60');
INSERT INTO item (descricao, valor) VALUES ('TECLADO', '25');
INSERT INTO item (descricao, valor) VALUES ('CARTAO', '5');
INSERT INTO item (descricao, valor) VALUES ('PEN DRIVE', '35');
INSERT INTO item (descricao, valor) VALUES ('ARMA DE BRINQUEDO', '30');
INSERT INTO item (descricao, valor) VALUES ('AMOEBA', '30');
INSERT INTO item (descricao, valor) VALUES ('AMIDO', '35');

INSERT INTO lancamento (dt_inicial, dt_final, vl_total, observacao) VALUES ('2017-12-12', '2017-12-22', '3100', 'OK');
INSERT INTO lancamento (dt_inicial, dt_final, vl_total, observacao) VALUES ('2017-12-12', '2017-12-22', '640', 'ENTREGUE');
INSERT INTO lancamento (dt_inicial, dt_final, vl_total, observacao) VALUES ('2017-12-14', '2017-12-22', '2300', 'FATURADO');
INSERT INTO lancamento (dt_inicial, dt_final, vl_total) VALUES ('2017-12-14', '2017-12-20', '65');
INSERT INTO lancamento (dt_inicial, dt_final, vl_total) VALUES ('2017-12-14', '2017-12-22', '130');
INSERT INTO lancamento (dt_inicial, dt_final, vl_total, observacao) VALUES ('2017-12-08', '2017-12-20', '6700', 'Antigo');

INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('1', '1');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('1', '2');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('2', '5');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('2', '6');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('2', '7');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('3', '3');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('4', '11');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('4', '12');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('5', '10');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('5', '11');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('5', '12');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES ('5', '13');
INSERT INTO lancamentoItem (oid_lancamento, oid_item) VALUES (6,1),(6,2),(6,3),(6,4),(6,5),(6,6),
(6,7),(6,8),(6,9),(6,10),(6,11),(6,12),(6,13);

/*
CONSULTA 10
Fazer uma consulta para somar o total dos lançamentos, cujo a média dos itens foi maior ou igual à R$ 100,00.
*/

SELECT SUM(l.vl_total) FROM lancamento l WHERE l.oid IN (SELECT l.oid_lancamento FROM lancamentoItem l JOIN item i ON i.oid = l.oid_item
GROUP BY oid_lancamento HAVING AVG(i.valor) >= 100);

/*
CONSULTA 11
Fazer uma consulta para trazer os 10 lançamentos que possuam o maior valor de itens e tenham a 
descrição começando com a letra A. Sendo que só devem mostrar lançamentos no qual o somatório desses itens
sejam maiores que R$ 50,00. 
*/

SELECT l.oid_lancamento, SUM(i.valor) AS soma FROM 
lancamentoItem l JOIN item i ON i.oid = l.oid_item WHERE i.descricao LIKE 'A%' 
GROUP BY oid_lancamento HAVING soma >= 50 ORDER BY soma DESC LIMIT 10;

/*
SCRIPT 12
Crie um script para selecionar todos os lançamentos que possuam mais que 10 itens e alterar a observação dos lançamentos selecionados 
anteriormente concatenando a observação atual com a seguinte texto ("- Possuem mais que 10 itens").
*/
UPDATE lancamento l SET  l.observacao = CONCAT(l.observacao, "- Possuem mais que 10 itens") WHERE l.oid IN (SELECT l.oid_lancamento FROM lancamentoItem l GROUP BY l.oid_lancamento HAVING COUNT(*) > 10);






