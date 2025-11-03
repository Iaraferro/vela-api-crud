

INSERT INTO CategoriaVela (nome, descricao) VALUES 
('Ritualística', 'Velas para rituais espirituais'),
('Decorativa', 'Velas para decoração e ambientação'),
('Aromática', 'Velas com essências terapêuticas');

-- Inserções para Usuario (Clientes e Admins)
INSERT INTO Usuario (
    tipo_usuario, nome, email, telefone, ativo, senha, role, 
    departamento, created_at, updated_at
) VALUES
(
    'CLIENTE', 'Maria Silva', 'maria@email.com', '11999990000', 
    true, 'senha123', 'CLIENTE', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CLIENTE', 'João Santos', 'joao@email.com', '21999991111', 
    true, 'senha123', 'ADMIN', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'ADMIN', 'Admin Loja', 'admin@email.com', NULL, 
    true, 'admin123', 'ADMIN', 'TI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);

-- Inserções para Fornecedor (usando a tabela Pessoa com DTYPE)
INSERT INTO Pessoa (DTYPE, nome, cnpj, numero) VALUES 
('FORNECEDOR', 'Casa das Velas Místicas', '12345678000199', '11999998888'),
('FORNECEDOR', 'Distribuidora de Ervas Sagradas', '98765432000155', '21999997777');

-- Inserções para Vela
INSERT INTO Vela (nome, preco, ritualAssociado, disponivel, estoque, categoria_id, fornecedor_id) VALUES
('Proteção Ancestral', 35.90, 'Proteção contra energias negativas', true, 100, 1, 1),
('Harmonia Familiar', 42.50, 'Harmonização de ambientes', true, 100, 1, 1),
('Aroma Relaxante', 28.00, 'Relaxamento e meditação', true, 100, 3, 2),
('Vela da Prosperidade', 45.90, 'Atrair abundância financeira', true, 80, 1, 1),
('Vela do Amor', 38.50, 'Atrair relacionamentos harmoniosos', true, 60, 1, 1),
('Vela de Limpeza Energética', 32.00, 'Purificação de ambientes', true, 90, 1, 2),
('Vela de Meditação', 29.90, 'Auxílio na prática meditativa', true, 75, 1, 1),
('Vela de Lavanda', 26.50, 'Relaxamento e sono tranquilo', true, 110, 3, 2),
('Vela de Baunilha', 24.90, 'Conforto e aconchego', true, 95, 3, 2),
('Vela de Alecrim', 27.50, 'Concentração e clareza mental', true, 70, 3, 2),
('Vela Decorativa Cristal', 52.90, 'Decoração e energização', true, 40, 2, 1),
('Vela Decorativa Flor', 48.50, 'Ambientação e beleza', true, 35, 2, 1),
('Vela da Intuição', 41.90, 'Despertar a intuição interior', true, 55, 1, 1);

-- Inserções para Pedido
INSERT INTO Pedido (cliente_id, data, total, status) VALUES
(1, '2025-04-20 14:30:00', 0, 'PENDENTE'),
(2, '2025-04-21 10:15:00', 0, 'PENDENTE');

-- Inserções para ItemPedido
INSERT INTO ItemPedido (pedido_id, vela_id, quantidade, precoUnitario) VALUES
(1, 1, 2, 35.90),
(1, 3, 1, 28.00),
(2, 2, 3, 42.50);

-- Atualiza os totais dos pedidos
UPDATE Pedido SET total = (SELECT SUM(precoUnitario * quantidade) FROM ItemPedido WHERE pedido_id = 1) WHERE id = 1;
UPDATE Pedido SET total = (SELECT SUM(precoUnitario * quantidade) FROM ItemPedido WHERE pedido_id = 2) WHERE id = 2;

-- Inserções para Pagamento
INSERT INTO Pagamento (pedido_id, valor, metodo, status, dataPagamento) VALUES
(1, 99.80, 'PIX', 'APROVADO', '2025-04-20 15:00:00'),
(2, 127.50, 'CARTAO', 'PENDENTE', NULL);

-- INSERTS PARA INGREDIENTE (adicionar ao import.sql existente)
INSERT INTO Ingrediente (pavio, recipiente, tipoCera, created_at, updated_at) VALUES
('Pavio de algodão', 'Pote de vidro transparente', 'Cera de soja', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pavio de madeira', 'Pote de cerâmica', 'Cera de abelha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pavio eco', 'Pote de metal', 'Cera de coco', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Pavio tradicional', 'Taça decorativa', 'Cera de parafina', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- INSERTS PARA AROMA (adicionar ao import.sql existente)

-- INSERTS CORRETOS PARA AROMA

INSERT INTO Aroma (essenciaAromatica, created_at, updated_at) VALUES 
('Lavanda', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Alecrim', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Baunilha', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vela_aroma (vela_id, aroma_id) VALUES 
(1, 1),
(1, 2),
(2, 3);

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