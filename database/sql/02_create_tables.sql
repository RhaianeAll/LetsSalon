-- =====================================================
-- Let's Salon
-- Script 02 - Estrutura das Tabelas
-- Gerado a partir do backup oficial (limpo)
-- =====================================================

CREATE TABLE administrador (
    cpf_administrador character varying(14) NOT NULL,
    nome_administrador character varying(100) NOT NULL,
    email_administrador character varying(100) NOT NULL,
    senha_administrador character varying(255) NOT NULL,
    telefone_administrador character varying(20) NOT NULL,
    perfil character varying(20) DEFAULT 'administrador'::character varying NOT NULL,
    codigo_empresa integer
);
CREATE TABLE agenda (
    id_agendamento integer NOT NULL,
    cpf_cliente character varying(14),
    data date NOT NULL,
    horario time without time zone NOT NULL,
    id_colaborador integer,
    codigo_servico integer,
    codigo_empresa integer,
    status character varying(20) DEFAULT 'AGENDADO'::character varying
);
CREATE SEQUENCE agenda_id_agendamento_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE agenda_id_agendamento_seq1 OWNED BY agenda.id_agendamento;



CREATE TABLE atendimento (
    id_atendimento integer NOT NULL,
    id_colaborador integer,
    codigo_servico integer,
    cpf_cliente character varying(14),
    data_hora timestamp without time zone NOT NULL,
    valor_arrecadado numeric(10,2),
    codigo_insumo integer,
    qtd_aplicacoes integer,
    custo_insumo numeric(10,2),
    codigo_empresa integer
);
CREATE SEQUENCE atendimento_id_atendimento_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE atendimento_id_atendimento_seq1 OWNED BY atendimento.id_atendimento;



CREATE TABLE cadastro_insumo (
    codigo_insumo integer NOT NULL,
    rende_aplicacoes integer,
    codigo_servico integer NOT NULL
);
CREATE TABLE cliente (
    cpf_cliente character varying(14) NOT NULL,
    nome_cliente character varying(100) NOT NULL,
    endereco_cliente character varying(200),
    telefone_cliente character varying(20),
    email_cliente character varying(100) NOT NULL,
    data_nascimento date,
    senha_cliente character varying(255) NOT NULL,
    perfil character varying(20) DEFAULT 'cliente'::character varying NOT NULL,
    codigo_empresa integer NOT NULL
);
CREATE TABLE colaborador (
    id_colaborador integer NOT NULL,
    cpf_colaborador character varying(14) NOT NULL,
    nome_colaborador character varying(100) NOT NULL,
    email_colaborador character varying(100) NOT NULL,
    funcao_colaborador character varying(100),
    telefone_colaborador character varying(20),
    foto_colaborador character varying(255),
    senha_colaborador character varying(255) NOT NULL,
    perfil character varying(20) DEFAULT 'colaborador'::character varying NOT NULL,
    codigo_empresa integer
);
CREATE SEQUENCE colaborador_id_colaborador_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE colaborador_id_colaborador_seq1 OWNED BY colaborador.id_colaborador;



CREATE TABLE empresa (
    codigo_empresa integer NOT NULL,
    nome_empresa character varying(100) NOT NULL,
    telefone_empresa character varying(20),
    email_empresa character varying(100),
    endereco_empresa character varying(200)
);
CREATE SEQUENCE empresa_codigo_empresa_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE empresa_codigo_empresa_seq1 OWNED BY empresa.codigo_empresa;



CREATE TABLE insumo (
    codigo_insumo integer NOT NULL,
    nome_insumo character varying(100) NOT NULL,
    valor_insumo numeric(10,2),
    quantidade_insumo integer,
    capacidade character varying(50),
    codigo_empresa integer
);
CREATE SEQUENCE insumo_codigo_insumo_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE insumo_codigo_insumo_seq1 OWNED BY insumo.codigo_insumo;



CREATE TABLE servico (
    codigo_servico integer NOT NULL,
    nome_servico character varying(100) NOT NULL,
    valor_servico numeric(10,2),
    duracao_aproximada character varying(20),
    id_colaborador integer,
    codigo_insumo integer,
    quantidade_insumo integer,
    codigo_empresa integer
);
CREATE SEQUENCE servico_codigo_servico_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE servico_codigo_servico_seq1 OWNED BY servico.codigo_servico;



CREATE TABLE servico_colaborador (
    id integer NOT NULL,
    codigo_servico integer NOT NULL,
    id_colaborador integer NOT NULL
);
CREATE SEQUENCE servico_colaborador_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE servico_colaborador_id_seq OWNED BY servico_colaborador.id;



ALTER TABLE ONLY agenda ALTER COLUMN id_agendamento SET DEFAULT nextval('agenda_id_agendamento_seq1'::regclass);
ALTER TABLE ONLY atendimento ALTER COLUMN id_atendimento SET DEFAULT nextval('atendimento_id_atendimento_seq1'::regclass);



ALTER TABLE ONLY colaborador ALTER COLUMN id_colaborador SET DEFAULT nextval('colaborador_id_colaborador_seq1'::regclass);
ALTER TABLE ONLY empresa ALTER COLUMN codigo_empresa SET DEFAULT nextval('empresa_codigo_empresa_seq1'::regclass);



ALTER TABLE ONLY insumo ALTER COLUMN codigo_insumo SET DEFAULT nextval('insumo_codigo_insumo_seq1'::regclass);
ALTER TABLE ONLY servico ALTER COLUMN codigo_servico SET DEFAULT nextval('servico_codigo_servico_seq1'::regclass);



ALTER TABLE ONLY servico_colaborador ALTER COLUMN id SET DEFAULT nextval('servico_colaborador_id_seq'::regclass);
ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_email_administrador_key UNIQUE (email_administrador);
ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (cpf_administrador);
ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_pkey PRIMARY KEY (id_agendamento);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_pkey PRIMARY KEY (id_atendimento);
ALTER TABLE ONLY cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_pkey PRIMARY KEY (codigo_insumo, codigo_servico);
ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_email_cliente_key UNIQUE (email_cliente);
ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (cpf_cliente);
ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_cpf_colaborador_key UNIQUE (cpf_colaborador);
ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_email_colaborador_key UNIQUE (email_colaborador);
ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_pkey PRIMARY KEY (id_colaborador);
ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_nome_empresa_key UNIQUE (nome_empresa);
ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (codigo_empresa);
ALTER TABLE ONLY insumo
    ADD CONSTRAINT insumo_pkey PRIMARY KEY (codigo_insumo);
ALTER TABLE ONLY servico_colaborador
    ADD CONSTRAINT servico_colaborador_pkey PRIMARY KEY (id);
ALTER TABLE ONLY servico
    ADD CONSTRAINT servico_pkey PRIMARY KEY (codigo_servico);
ALTER TABLE ONLY servico_colaborador
    ADD CONSTRAINT uk_servico_profissional UNIQUE (codigo_servico, id_colaborador);
ALTER TABLE ONLY administrador
    ADD CONSTRAINT administrador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES servico(codigo_servico);
ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf_cliente);
ALTER TABLE ONLY agenda
    ADD CONSTRAINT agenda_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES colaborador(id_colaborador);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES insumo(codigo_insumo);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES servico(codigo_servico);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf_cliente);
ALTER TABLE ONLY atendimento
    ADD CONSTRAINT atendimento_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES colaborador(id_colaborador);
ALTER TABLE ONLY cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES insumo(codigo_insumo);
ALTER TABLE ONLY cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES servico(codigo_servico);
ALTER TABLE ONLY colaborador
    ADD CONSTRAINT colaborador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk_cliente_empresa FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY servico_colaborador
    ADD CONSTRAINT fk_sc_colaborador FOREIGN KEY (id_colaborador) REFERENCES colaborador(id_colaborador);
ALTER TABLE ONLY servico_colaborador
    ADD CONSTRAINT fk_sc_servico FOREIGN KEY (codigo_servico) REFERENCES servico(codigo_servico);
ALTER TABLE ONLY insumo
    ADD CONSTRAINT insumo_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY servico
    ADD CONSTRAINT servico_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES empresa(codigo_empresa);
ALTER TABLE ONLY servico
    ADD CONSTRAINT servico_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES insumo(codigo_insumo);
ALTER TABLE ONLY servico
    ADD CONSTRAINT servico_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES colaborador(id_colaborador);