-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION postgres;

-- DROP SEQUENCE public.agenda_id_agendamento_seq;

CREATE SEQUENCE public.agenda_id_agendamento_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.agenda_id_agendamento_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.agenda_id_agendamento_seq TO postgres;

-- DROP SEQUENCE public.atendimento_id_atendimento_seq;

CREATE SEQUENCE public.atendimento_id_atendimento_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.atendimento_id_atendimento_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.atendimento_id_atendimento_seq TO postgres;

-- DROP SEQUENCE public.colaborador_id_colaborador_seq;

CREATE SEQUENCE public.colaborador_id_colaborador_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.colaborador_id_colaborador_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.colaborador_id_colaborador_seq TO postgres;

-- DROP SEQUENCE public.empresa_codigo_empresa_seq;

CREATE SEQUENCE public.empresa_codigo_empresa_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.empresa_codigo_empresa_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.empresa_codigo_empresa_seq TO postgres;

-- DROP SEQUENCE public.insumo_codigo_insumo_seq;

CREATE SEQUENCE public.insumo_codigo_insumo_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.insumo_codigo_insumo_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.insumo_codigo_insumo_seq TO postgres;

-- DROP SEQUENCE public.servico_codigo_servico_seq;

CREATE SEQUENCE public.servico_codigo_servico_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE public.servico_codigo_servico_seq OWNER TO postgres;
GRANT ALL ON SEQUENCE public.servico_codigo_servico_seq TO postgres;
-- public.cliente definition

-- Drop table

-- DROP TABLE public.cliente;

CREATE TABLE public.cliente ( cpf_cliente varchar(14) NOT NULL, nome_cliente varchar(100) NOT NULL, endereco_cliente varchar(200) NULL, telefone_cliente varchar(20) NULL, email_cliente varchar(100) NOT NULL, data_nascimento date NULL, senha_cliente varchar(255) NOT NULL, perfil varchar(20) DEFAULT 'cliente'::character varying NOT NULL, CONSTRAINT cliente_email_cliente_key UNIQUE (email_cliente), CONSTRAINT cliente_pkey PRIMARY KEY (cpf_cliente), CONSTRAINT unique_cpf_cliente UNIQUE (cpf_cliente));

-- Permissions

ALTER TABLE public.cliente OWNER TO postgres;
GRANT ALL ON TABLE public.cliente TO postgres;


-- public.empresa definition

-- Drop table

-- DROP TABLE public.empresa;

CREATE TABLE public.empresa ( codigo_empresa serial4 NOT NULL, nome_empresa varchar(100) NOT NULL, telefone_empresa varchar(20) NULL, email_empresa varchar(100) NULL, endereco_empresa varchar(200) NULL, CONSTRAINT empresa_nome_empresa_key UNIQUE (nome_empresa), CONSTRAINT empresa_pkey PRIMARY KEY (codigo_empresa));

-- Permissions

ALTER TABLE public.empresa OWNER TO postgres;
GRANT ALL ON TABLE public.empresa TO postgres;


-- public.administrador definition

-- Drop table

-- DROP TABLE public.administrador;

CREATE TABLE public.administrador ( cpf_administrador varchar(14) NOT NULL, nome_administrador varchar(100) NOT NULL, email_administrador varchar(100) NOT NULL, senha_administrador varchar(255) NOT NULL, telefone_administrador varchar(20) NOT NULL, perfil varchar(20) DEFAULT 'administrador'::character varying NOT NULL, codigo_empresa int4 NULL, CONSTRAINT administrador_email_administrador_key UNIQUE (email_administrador), CONSTRAINT administrador_pkey PRIMARY KEY (cpf_administrador), CONSTRAINT administrador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa));

-- Permissions

ALTER TABLE public.administrador OWNER TO postgres;
GRANT ALL ON TABLE public.administrador TO postgres;


-- public.colaborador definition

-- Drop table

-- DROP TABLE public.colaborador;

CREATE TABLE public.colaborador ( id_colaborador serial4 NOT NULL, cpf_colaborador varchar(14) NOT NULL, nome_colaborador varchar(100) NOT NULL, email_colaborador varchar(100) NOT NULL, funcao_colaborador varchar(100) NULL, telefone_colaborador varchar(20) NULL, foto_colaborador varchar(255) NULL, senha_colaborador varchar(255) NOT NULL, perfil varchar(20) DEFAULT 'colaborador'::character varying NOT NULL, codigo_empresa int4 NULL, CONSTRAINT colaborador_cpf_colaborador_key UNIQUE (cpf_colaborador), CONSTRAINT colaborador_email_colaborador_key UNIQUE (email_colaborador), CONSTRAINT colaborador_pkey PRIMARY KEY (id_colaborador), CONSTRAINT unique_cpf_colaborador UNIQUE (cpf_colaborador), CONSTRAINT unique_email_colaborador UNIQUE (email_colaborador), CONSTRAINT colaborador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa));

-- Permissions

ALTER TABLE public.colaborador OWNER TO postgres;
GRANT ALL ON TABLE public.colaborador TO postgres;


-- public.insumo definition

-- Drop table

-- DROP TABLE public.insumo;

CREATE TABLE public.insumo ( codigo_insumo serial4 NOT NULL, nome_insumo varchar(100) NOT NULL, valor_insumo numeric(10, 2) NULL, quantidade_insumo int4 NULL, capacidade varchar(50) NULL, codigo_empresa int4 NULL, CONSTRAINT insumo_pkey PRIMARY KEY (codigo_insumo), CONSTRAINT insumo_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa));

-- Permissions

ALTER TABLE public.insumo OWNER TO postgres;
GRANT ALL ON TABLE public.insumo TO postgres;


-- public.servico definition

-- Drop table

-- DROP TABLE public.servico;

CREATE TABLE public.servico ( codigo_servico serial4 NOT NULL, nome_servico varchar(100) NOT NULL, valor_servico numeric(10, 2) NULL, duracao_aproximada varchar(20) NULL, id_colaborador int4 NULL, codigo_insumo int4 NULL, quantidade_insumo int4 NULL, codigo_empresa int4 NULL, CONSTRAINT servico_pkey PRIMARY KEY (codigo_servico), CONSTRAINT servico_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa), CONSTRAINT servico_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo), CONSTRAINT servico_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador));

-- Permissions

ALTER TABLE public.servico OWNER TO postgres;
GRANT ALL ON TABLE public.servico TO postgres;


-- public.agenda definition

-- Drop table

-- DROP TABLE public.agenda;

CREATE TABLE public.agenda ( id_agendamento serial4 NOT NULL, cpf_cliente varchar(14) NULL, "data" date NOT NULL, horario time NOT NULL, id_colaborador int4 NULL, codigo_servico int4 NULL, codigo_empresa int4 NULL, status varchar(20) DEFAULT 'AGENDADO'::character varying NULL, CONSTRAINT agenda_pkey PRIMARY KEY (id_agendamento), CONSTRAINT agenda_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa), CONSTRAINT agenda_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico), CONSTRAINT agenda_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES public.cliente(cpf_cliente), CONSTRAINT agenda_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador));

-- Permissions

ALTER TABLE public.agenda OWNER TO postgres;
GRANT ALL ON TABLE public.agenda TO postgres;


-- public.atendimento definition

-- Drop table

-- DROP TABLE public.atendimento;

CREATE TABLE public.atendimento ( id_atendimento serial4 NOT NULL, id_colaborador int4 NULL, codigo_servico int4 NULL, cpf_cliente varchar(14) NULL, data_hora timestamp NOT NULL, valor_arrecadado numeric(10, 2) NULL, codigo_insumo int4 NULL, qtd_aplicacoes int4 NULL, custo_insumo numeric(10, 2) NULL, codigo_empresa int4 NULL, CONSTRAINT atendimento_pkey PRIMARY KEY (id_atendimento), CONSTRAINT atendimento_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa), CONSTRAINT atendimento_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo), CONSTRAINT atendimento_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico), CONSTRAINT atendimento_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES public.cliente(cpf_cliente), CONSTRAINT atendimento_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador));

-- Permissions

ALTER TABLE public.atendimento OWNER TO postgres;
GRANT ALL ON TABLE public.atendimento TO postgres;


-- public.cadastro_insumo definition

-- Drop table

-- DROP TABLE public.cadastro_insumo;

CREATE TABLE public.cadastro_insumo ( codigo_insumo int4 NOT NULL, rende_aplicacoes int4 NULL, codigo_servico int4 NOT NULL, CONSTRAINT cadastro_insumo_pkey PRIMARY KEY (codigo_insumo, codigo_servico), CONSTRAINT cadastro_insumo_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo), CONSTRAINT cadastro_insumo_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico));

-- Permissions

ALTER TABLE public.cadastro_insumo OWNER TO postgres;
GRANT ALL ON TABLE public.cadastro_insumo TO postgres;




-- Permissions

GRANT ALL ON SCHEMA public TO postgres;