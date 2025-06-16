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
INSERT INTO Usuario (
    tipo_usuario, nome, email, telefone, ativo, senha, role, 
    departamento, created_at, update_at
) VALUES
-- Clientes (tipo_usuario = 'CLIENTE')
(
     'CLIENTE', 'Maria Silva', 'maria@email.com', '11999990000', 
    true, 'senha123', 'CLIENTE', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CLIENTE', 'João Santos', 'joao@email.com', '21999991111', 
    true, 'senha123', 'ADMIN', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
-- Admins (tipo_usuario = 'ADMIN')
(
     'ADMIN', 'Admin Loja', 'admin@email.com', NULL, 
    true, 'admin123', 'ADMIN', 'TI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

ALTER SEQUENCE usuario_id_seq RESTART WITH 4;


-- Inserções para Vela (relacionadas com Categoria)
INSERT INTO Vela (nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel, estoque, categoria_id) VALUES
('Proteção Ancestral', 'PROTECAO_ESPIRITUAL', 'Arruda', 35.90, 'Cera de abelha, ervas sagradas', 'Proteção contra energias negativas', true, 100, 1),
('Harmonia Familiar', 'LIMPEZA_ENERGETICA', 'Lavanda', 42.50, 'Cera de soja, óleo essencial', 'Harmonização de ambientes', true, 100, 1),
('Aroma Relaxante', 'LIMPEZA_ENERGETICA', 'Camomila', 28.00, 'Cera vegetal, essências naturais', 'Relaxamento e meditação', true,100 ,3);

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

INSERT INTO Fornecedor (nome, cnpj, telefone) VALUES 
('Casa das Velas Místicas', '12345678000199', '11999998888'),
('Distribuidora de Ervas Sagradas', '98765432000155', '21999997777');

UPDATE Vela SET fornecedor_id = (SELECT id FROM Fornecedor WHERE cnpj = '12345678000199') WHERE id = 1;
UPDATE Vela SET fornecedor_id = (SELECT id FROM Fornecedor WHERE cnpj = '12345678000199') WHERE id = 2;
UPDATE Vela SET fornecedor_id = (SELECT id FROM Fornecedor WHERE cnpj = '98765432000155') WHERE id = 3;





--insert into Usuario(tipo_usuario, nome, email, telefone, ativo) 
--values('CLIENTE', 'Iara Martins', 'iara@email.com', '6399999999', true);

--insert into Usuario(tipo_usuario, email, senha, role, departamento) 
--values('ADMIN', 'admin@email.com', 'senha123', 'ADMIN', 'TI');

--insert into Vela(nome, tipo, aroma, preco, ingrediente, ritualAssociado, disponivel) 
--values('Espanta Espirito', 'PROTECAO_ESPIRITUAL', 'canela', 29.90, 'Cera de soja, ervas', 'Proteção espiritual', true);
-- tabela Cliente
--insert into cliente(nome, email, telefone) values('Iara Martins', 'iara@email.com', '6399999999');