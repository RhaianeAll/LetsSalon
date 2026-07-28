-- =====================================================
-- Let's Salon
-- Script 01 - Criação do Banco
-- =====================================================

CREATE DATABASE lets_salon_dev
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    LOCALE_PROVIDER = 'libc'
    TEMPLATE = template0;

COMMENT ON DATABASE lets_salon_dev IS
'Banco de dados do sistema Let''s Salon.';