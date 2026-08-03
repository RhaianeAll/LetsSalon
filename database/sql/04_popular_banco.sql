-- =====================================================
-- 04_popular_banco.sql
-- População do banco de demonstração
-- Projeto: Let's Salon
-- =====================================================


BEGIN;
-- =====================================================
-- COLABORADORES
-- =====================================================

INSERT INTO colaborador
(
    id_colaborador,
    cpf_colaborador,
    nome_colaborador,
    email_colaborador,
    funcao_colaborador,
    telefone_colaborador,
    foto_colaborador,
    senha_colaborador,
    perfil,
    codigo_empresa
)
VALUES

(
    2,
    '566.789.280-40',
    'Ana Souza',
    'ana@letssalon.com',
    'Cabeleireiro',
    '(49) 99999-0002',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
),
(
    3,
    '168.995.350-09',
    'Clara',
    'clara@letssalon.com',
    'Manicure',
    '(49) 99999-0003',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
),
(
    4,
    '390.533.447-05',
    'Fernanda Lima',
    'fernanda@letssalon.com',
    'Esteticista',
    '(49) 99999-0004',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
),
(
    5,
    '153.509.460-56',
    'Lucas Rocha',
    'lucas@letssalon.com',
    'Cabeleireiro',
    '(49) 99999-0005',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
),
(
    6,
    '987.654.321-00',
    'Bianca Oliveira',
    'bianca@letssalon.com',
    'Recepcionista',
    '(49) 99999-0006',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
),
(
    7,
    '321.654.987-00',
    'Maria',
    'maria@letssalon.com',
    'Designer de Sobrancelhas',
    '(49) 99999-0007',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
);
COMMIT;

BEGIN;
-- =====================================================
-- CLIENTES
-- =====================================================

INSERT INTO cliente
(
    cpf_cliente,
    nome_cliente,
    endereco_cliente,
    telefone_cliente,
    email_cliente,
    data_nascimento,
    senha_cliente,
    perfil,
    codigo_empresa
)
VALUES

(
'026.542.351-14',
'Mariana Alves Pereira',
'Rua das Flores, 123, Porto Alegre - RS',
'(51) 99876-5432',
'mariana.pereira@email.com',
'1992-03-14',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'161.559.407-89',
'João Vitor Santos',
'Av. Ipiranga, 450, Porto Alegre - RS',
'(51) 98765-1234',
'joao.santos@email.com',
'1988-07-22',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'816.184.959-50',
'Camila Ribeiro Souza',
'Rua Voluntários da Pátria, 890, Porto Alegre - RS',
'(51) 99654-8821',
'camila.souza@email.com',
'1995-11-05',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'310.341.316-56',
'Rafael Augusto Barbosa',
'Rua Padre Chagas, 210, Porto Alegre - RS',
'(51) 98543-9910',
'rafael.barbosa@email.com',
'1990-01-30',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'475.255.341-44',
'Juliana Cristina Nunes',
'Av. Protásio Alves, 3200, Porto Alegre - RS',
'(51) 99432-7766',
'juliana.nunes@email.com',
'1998-05-18',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'928.327.648-51',
'Pedro Henrique Almeida',
'Rua General Câmara, 77, Porto Alegre - RS',
'(51) 98321-6654',
'pedro.almeida@email.com',
'1985-09-09',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'350.305.641-60',
'Larissa Beatriz Cardoso',
'Rua Fernando Machado, 340, Porto Alegre - RS',
'(51) 99210-5543',
'larissa.cardoso@email.com',
'1993-12-25',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'395.376.724-09',
'Thiago Matheus Farias',
'Av. Assis Brasil, 1500, Porto Alegre - RS',
'(51) 98109-4432',
'thiago.farias@email.com',
'1991-04-11',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'238.849.696-92',
'Bruna Letícia Teixeira',
'Rua João Alfredo, 65, Porto Alegre - RS',
'(51) 99098-3321',
'bruna.teixeira@email.com',
'1997-08-03',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
),

(
'532.871.012-69',
'Gustavo Henrique Moura',
'Rua Lima e Silva, 210, Porto Alegre - RS',
'(51) 98987-2210',
'gustavo.moura@email.com',
'1989-06-27',
'$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6',
'cliente',
1
);
COMMIT;

BEGIN;

-- =====================================================
-- INSUMOS
-- =====================================================

INSERT INTO insumo (
    codigo_insumo,
    nome_insumo,
    valor_insumo,
    quantidade_insumo,
    capacidade,
    codigo_empresa
)
VALUES
(1,'Álcool 70%',12.50,20,'1L',1),
(2,'Shampoo Profissional',45.90,20,'1L',1),
(3,'Condicionador Profissional',42.50,20,'1L',1),
(4,'Máscara Hidratante',65.00,15,'500g',1),
(5,'Tintura Castanho',28.00,25,'60g',1),
(6,'Tintura Loiro',30.00,20,'60g',1),
(7,'Oxidante 20 Vol',18.50,30,'900ml',1),
(8,'Esmalte Vermelho',8.90,40,'8ml',1),
(9,'Esmalte Nude',8.90,40,'8ml',1),
(10,'Cera Modeladora',35.00,15,'100g',1),
(11,'Esmalte Azul',8.90,5,'8ml',1);

COMMIT;

BEGIN;

-- =====================================================
-- SERVIÇOS
-- =====================================================

INSERT INTO servico (
    codigo_servico,
    nome_servico,
    valor_servico,
    duracao_aproximada,
    id_colaborador,
    codigo_insumo,
    quantidade_insumo,
    codigo_empresa
)
VALUES
(1,'Corte Feminino',80.00,'60 min',1,2,1,1),
(2,'Escova',60.00,'45 min',1,3,1,1),
(3,'Coloração',150.00,'120 min',1,5,1,1),
(4,'Progressiva',250.00,'180 min',1,4,1,1),
(5,'Hidratação',90.00,'60 min',1,4,1,1),

(6,'Manicure',40.00,'40 min',2,8,1,1),
(7,'Pedicure',40.00,'50 min',2,9,1,1),

(8,'Limpeza de Pele',120.00,'60 min',3,1,1,1),

(9,'Corte Masculino',45.00,'30 min',4,10,1,1),
(10,'Barba',35.00,'20 min',4,1,1,1),

(11,'Design de Sobrancelhas',70.00,'30 min',7,5,1,1);

COMMIT;

BEGIN;

-- =====================================================
-- CADASTRO DE INSUMOS
-- =====================================================

INSERT INTO cadastro_insumo (
    codigo_insumo,
    rende_aplicacoes,
    codigo_servico
)
VALUES
(2,20,1),
(3,20,2),
(5,2,3),
(4,5,4),
(4,8,5),
(8,25,6),
(9,25,7),
(1,30,8),
(10,15,9),
(1,30,10),
(5,30,11);

COMMIT;

BEGIN;

-- =====================================================
-- SERVIÇO x COLABORADOR
-- =====================================================

INSERT INTO servico_colaborador
(id, codigo_servico, id_colaborador)
VALUES
(1, 1, 2), -- Corte Feminino -> Ana
(2, 2, 2), -- Escova -> Ana
(3, 3, 2), -- Coloração -> Ana
(4, 4, 2), -- Progressiva -> Ana
(5, 5, 2), -- Hidratação -> Ana

(6, 6, 3), -- Manicure -> Clara
(7, 7, 3), -- Pedicure -> Clara

(8, 8, 4), -- Limpeza de Pele -> Fernanda

(9, 9, 5), -- Corte Masculino -> Lucas
(10,10,5), -- Barba -> Lucas

(11,11,7); -- Design de Sobrancelhas -> Maria

COMMIT;

BEGIN;

-- =====================================================
-- AGENDA
-- =====================================================

INSERT INTO agenda
(
id_agendamento,
cpf_cliente,
data,
horario,
id_colaborador,
codigo_servico,
codigo_empresa,
status
)
VALUES

(1,
'475.255.341-44',
'2026-07-17',
'09:30',
4,
8,
1,
'CONFIRMADO'),

(2,
'928.327.648-51',
'2026-07-10',
'11:00',
2,
3,
1,
'FINALIZADO'),

(3,
'350.305.641-60',
'2026-07-09',
'13:30',
2,
4,
1,
'FINALIZADO'),

(4,
'395.376.724-09',
'2026-07-08',
'16:00',
3,
7,
1,
'FINALIZADO'),

(5,
'238.849.696-92',
'2026-07-07',
'10:00',
5,
9,
1,
'CANCELADO'),

(6,
'532.871.012-69',
'2026-07-18',
'17:00',
2,
5,
1,
'PENDENTE'),

(7,
'026.542.351-14',
'2026-07-15',
'09:00',
2,
1,
1,
'FINALIZADO'),

(8,
'161.559.407-89',
'2026-07-15',
'10:30',
2,
2,
1,
'CANCELADO'),

(9,
'816.184.959-50',
'2026-07-16',
'14:00',
3,
6,
1,
'FINALIZADO'),

(10,
'310.341.316-56',
'2026-07-16',
'15:30',
5,
10,
1,
'CANCELADO'),

(11,
'395.376.724-09',
'2026-07-20',
'10:00',
5,
10,
1,
'CONFIRMADO'),

(12,
'026.542.351-14',
'2026-07-20',
'16:30',
3,
6,
1,
'FINALIZADO'),

(13,
'026.542.351-14',
'2026-07-20',
'10:30',
3,
7,
1,
'FINALIZADO'),

(14,
'026.542.351-14',
'2026-07-20',
'11:30',
3,
6,
1,
'FINALIZADO'),

(15,
'816.184.959-50',
'2026-07-20',
'13:30',
3,
6,
1,
'FINALIZADO');

COMMIT;

BEGIN;

-- =====================================================
-- ATENDIMENTO
-- =====================================================

INSERT INTO atendimento
(
id_atendimento,
id_colaborador,
codigo_servico,
cpf_cliente,
data_hora,
valor_arrecadado,
codigo_insumo,
qtd_aplicacoes,
custo_insumo,
codigo_empresa
)
VALUES

(
1,
2,
3,
'928.327.648-51',
'2026-07-10 11:00:00',
150.00,
4,
1,
15.00,
1
),

(
2,
2,
4,
'350.305.641-60',
'2026-07-09 13:30:00',
250.00,
3,
1,
15.00,
1
),

(
3,
3,
7,
'395.376.724-09',
'2026-07-08 16:00:00',
40.00,
8,
1,
15.00,
1
);

COMMIT;

BEGIN;

-- =====================================================
-- AJUSTE DAS SEQUENCES
-- =====================================================

SELECT setval(
'empresa_codigo_empresa_seq1',
(SELECT MAX(codigo_empresa) FROM empresa)
);

SELECT setval(
'colaborador_id_colaborador_seq1',
(SELECT MAX(id_colaborador) FROM colaborador)
);

SELECT setval(
'servico_codigo_servico_seq1',
(SELECT MAX(codigo_servico) FROM servico)
);

SELECT setval(
'insumo_codigo_insumo_seq1',
(SELECT MAX(codigo_insumo) FROM insumo)
);

SELECT setval(
'agenda_id_agendamento_seq1',
(SELECT MAX(id_agendamento) FROM agenda)
);

SELECT setval(
'atendimento_id_atendimento_seq1',
(SELECT MAX(id_atendimento) FROM atendimento)
);

SELECT setval(
'servico_colaborador_id_seq',
(SELECT MAX(id) FROM servico_colaborador)
);

COMMIT;