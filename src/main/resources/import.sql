-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into vela(id, nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel) 
values(1, 'Espanta Espirito', 'PROTECAO_ESPIRITUAL', 'canela', 29.90 ,'Cera de soja, ervas','Proteção espiritual',true );



-- tabela Cliente
insert into cliente(id,nome, email, telefone) values(1,'Iara Martins', 'iara@email.com', '6399999999');