-- =====================================================
-- Let's Salon
-- Script: 03_dados_iniciais.sql
-- Descrição:
-- Dados mínimos necessários para inicializar o sistema.
-- Execute após o 02_create_tables.sql
-- =====================================================

BEGIN;

----------------------------------------------------------
-- EMPRESA PADRÃO
----------------------------------------------------------

INSERT INTO empresa (
    nome_empresa,
    telefone_empresa,
    email_empresa,
    endereco_empresa
)
VALUES (
    'Let''s Salon Studio Piloto',
    '(49) 99999-9999',
    'contato@letssalon.com',
    'Chapecó - SC'
);

----------------------------------------------------------
-- COLABORADOR ADMINISTRADOR
----------------------------------------------------------

INSERT INTO colaborador (
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
VALUES (
    '529.982.247-25',
    'Administrador',
    'admin@letssalon.com',
    'Administrador',
    '(49) 99999-9999',
    NULL,
    '$2a$10$hSj./pmtfcgdzT.fwbSvF.BYr/G1mJlJ6n5MGVPor6ecdhgDJCSu.',
    'colaborador',
    1
);

COMMIT;