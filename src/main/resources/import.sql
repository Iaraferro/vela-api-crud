-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

--insert into vela( nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel) 
--values('Espanta Espirito', 'PROTECAO_ESPIRITUAL', 'canela', 29.90 ,'Cera de soja, ervas','Proteção espiritual',true );


INSERT INTO CategoriaVela (nome, descricao) VALUES 
('Ritualística', 'Velas para rituais espirituais'),
('Decorativa', 'Velas para decoração e ambientação'),
('Aromática', 'Velas com essências terapêuticas');

-- Inserções para Usuario (Clientes e Admins)
-- Clientes
INSERT INTO Usuario (tipo_usuario, nome, email, telefone, ativo, senha, role) VALUES
('CLIENTE', 'Maria Silva', 'maria@email.com', '11999990000', true, 'senha123', 'USER'),
('CLIENTE', 'João Santos', 'joao@email.com', '21999991111', true, 'senha123', 'USER');

-- Admins
INSERT INTO Usuario (tipo_usuario, email, senha, role, departamento) VALUES
('ADMIN', 'admin1@email.com', 'admin123', 'ADMIN', 'TI'),
('ADMIN', 'admin2@email.com', 'admin123', 'ADMIN', 'Vendas');

-- Inserções para Vela (relacionadas com Categoria)
INSERT INTO Vela (nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel, categoria_id) VALUES
('Proteção Ancestral', 'PROTECAO_ESPIRITUAL', 'Arruda', 35.90, 'Cera de abelha, ervas sagradas', 'Proteção contra energias negativas', true, 1),
('Harmonia Familiar', 'LIMPEZA_ENERGETICA', 'Lavanda', 42.50, 'Cera de soja, óleo essencial', 'Harmonização de ambientes', true, 1),
('Aroma Relaxante', 'LIMPEZA_ENERGETICA', 'Camomila', 28.00, 'Cera vegetal, essências naturais', 'Relaxamento e meditação', true, 3);

-- Inserções para Pedido (relacionados com Cliente)
INSERT INTO Pedido (cliente_id, data, total, status) VALUES
(1, '2025-04-20 14:30:00', 0, 'PENDENTE'),
(2, '2025-04-21 10:15:00', 0, 'PENDENTE');

-- Inserções para ItemPedido (relacionados com Pedido e Vela)
INSERT INTO ItemPedido (pedido_id, vela_id, quantidade, precoUnitario) VALUES
(1, 1, 2, 35.90),
(1, 3, 1, 28.00),
(2, 2, 3, 42.50);

-- Atualiza os totais dos pedidos
UPDATE Pedido SET total = (SELECT SUM(precoUnitario * quantidade) FROM ItemPedido WHERE pedido_id = 1) WHERE id = 1;
UPDATE Pedido SET total = (SELECT SUM(precoUnitario * quantidade) FROM ItemPedido WHERE pedido_id = 2) WHERE id = 2;

-- Inserções para Pagamento (relacionados com Pedido)
INSERT INTO Pagamento (pedido_id, valor, metodo, status, dataPagamento) VALUES
(1, 99.80, 'PIX', 'APROVADO', '2025-04-20 15:00:00'),
(2, 127.50, 'CARTAO', 'PENDENTE', NULL);

-- Atualiza o status dos pedidos com pagamento aprovado
UPDATE Pedido SET status = 'PAGO' WHERE id = 1;




--insert into Usuario(tipo_usuario, nome, email, telefone, ativo) 
--values('CLIENTE', 'Iara Martins', 'iara@email.com', '6399999999', true);

--insert into Usuario(tipo_usuario, email, senha, role, departamento) 
--values('ADMIN', 'admin@email.com', 'senha123', 'ADMIN', 'TI');

--insert into Vela(nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel) 
--values('Espanta Espirito', 'PROTECAO_ESPIRITUAL', 'canela', 29.90, 'Cera de soja, ervas', 'Proteção espiritual', true);
-- tabela Cliente
--insert into cliente(nome, email, telefone) values('Iara Martins', 'iara@email.com', '6399999999');