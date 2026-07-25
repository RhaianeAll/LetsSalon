--
-- PostgreSQL database dump
--

\restrict FTkqWlbpAe2ap48BgkQIqdidQTs3V9sEbjZx8o8DgjMIAe0n72stI8JJQzZWSKT

-- Dumped from database version 17.10 (Homebrew)
-- Dumped by pg_dump version 17.10 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: unaccent; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA public;


--
-- Name: EXTENSION unaccent; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION unaccent IS 'text search dictionary that removes accents';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: administrador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.administrador (
    cpf_administrador character varying(14) NOT NULL,
    nome_administrador character varying(100) NOT NULL,
    email_administrador character varying(100) NOT NULL,
    senha_administrador character varying(255) NOT NULL,
    telefone_administrador character varying(20) NOT NULL,
    perfil character varying(20) DEFAULT 'administrador'::character varying NOT NULL,
    codigo_empresa integer
);


ALTER TABLE public.administrador OWNER TO postgres;

--
-- Name: agenda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agenda (
    id_agendamento integer NOT NULL,
    cpf_cliente character varying(14),
    data date NOT NULL,
    horario time without time zone NOT NULL,
    id_colaborador integer,
    codigo_servico integer,
    codigo_empresa integer,
    status character varying(20) DEFAULT 'AGENDADO'::character varying
);


ALTER TABLE public.agenda OWNER TO postgres;

--
-- Name: agenda_id_agendamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agenda_id_agendamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.agenda_id_agendamento_seq OWNER TO postgres;

--
-- Name: agenda_id_agendamento_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.agenda_id_agendamento_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.agenda_id_agendamento_seq1 OWNER TO postgres;

--
-- Name: agenda_id_agendamento_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.agenda_id_agendamento_seq1 OWNED BY public.agenda.id_agendamento;


--
-- Name: atendimento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.atendimento (
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


ALTER TABLE public.atendimento OWNER TO postgres;

--
-- Name: atendimento_id_atendimento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.atendimento_id_atendimento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.atendimento_id_atendimento_seq OWNER TO postgres;

--
-- Name: atendimento_id_atendimento_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.atendimento_id_atendimento_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.atendimento_id_atendimento_seq1 OWNER TO postgres;

--
-- Name: atendimento_id_atendimento_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.atendimento_id_atendimento_seq1 OWNED BY public.atendimento.id_atendimento;


--
-- Name: cadastro_insumo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cadastro_insumo (
    codigo_insumo integer NOT NULL,
    rende_aplicacoes integer,
    codigo_servico integer NOT NULL
);


ALTER TABLE public.cadastro_insumo OWNER TO postgres;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
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


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: colaborador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.colaborador (
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


ALTER TABLE public.colaborador OWNER TO postgres;

--
-- Name: colaborador_id_colaborador_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.colaborador_id_colaborador_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.colaborador_id_colaborador_seq OWNER TO postgres;

--
-- Name: colaborador_id_colaborador_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.colaborador_id_colaborador_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.colaborador_id_colaborador_seq1 OWNER TO postgres;

--
-- Name: colaborador_id_colaborador_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.colaborador_id_colaborador_seq1 OWNED BY public.colaborador.id_colaborador;


--
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    codigo_empresa integer NOT NULL,
    nome_empresa character varying(100) NOT NULL,
    telefone_empresa character varying(20),
    email_empresa character varying(100),
    endereco_empresa character varying(200)
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- Name: empresa_codigo_empresa_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_codigo_empresa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.empresa_codigo_empresa_seq OWNER TO postgres;

--
-- Name: empresa_codigo_empresa_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_codigo_empresa_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empresa_codigo_empresa_seq1 OWNER TO postgres;

--
-- Name: empresa_codigo_empresa_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_codigo_empresa_seq1 OWNED BY public.empresa.codigo_empresa;


--
-- Name: insumo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.insumo (
    codigo_insumo integer NOT NULL,
    nome_insumo character varying(100) NOT NULL,
    valor_insumo numeric(10,2),
    quantidade_insumo integer,
    capacidade character varying(50),
    codigo_empresa integer
);


ALTER TABLE public.insumo OWNER TO postgres;

--
-- Name: insumo_codigo_insumo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.insumo_codigo_insumo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.insumo_codigo_insumo_seq OWNER TO postgres;

--
-- Name: insumo_codigo_insumo_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.insumo_codigo_insumo_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.insumo_codigo_insumo_seq1 OWNER TO postgres;

--
-- Name: insumo_codigo_insumo_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.insumo_codigo_insumo_seq1 OWNED BY public.insumo.codigo_insumo;


--
-- Name: servico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servico (
    codigo_servico integer NOT NULL,
    nome_servico character varying(100) NOT NULL,
    valor_servico numeric(10,2),
    duracao_aproximada character varying(20),
    id_colaborador integer,
    codigo_insumo integer,
    quantidade_insumo integer,
    codigo_empresa integer
);


ALTER TABLE public.servico OWNER TO postgres;

--
-- Name: servico_codigo_servico_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servico_codigo_servico_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER SEQUENCE public.servico_codigo_servico_seq OWNER TO postgres;

--
-- Name: servico_codigo_servico_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servico_codigo_servico_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.servico_codigo_servico_seq1 OWNER TO postgres;

--
-- Name: servico_codigo_servico_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.servico_codigo_servico_seq1 OWNED BY public.servico.codigo_servico;


--
-- Name: servico_colaborador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servico_colaborador (
    id integer NOT NULL,
    codigo_servico integer NOT NULL,
    id_colaborador integer NOT NULL
);


ALTER TABLE public.servico_colaborador OWNER TO postgres;

--
-- Name: servico_colaborador_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servico_colaborador_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.servico_colaborador_id_seq OWNER TO postgres;

--
-- Name: servico_colaborador_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.servico_colaborador_id_seq OWNED BY public.servico_colaborador.id;


--
-- Name: agenda id_agendamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda ALTER COLUMN id_agendamento SET DEFAULT nextval('public.agenda_id_agendamento_seq1'::regclass);


--
-- Name: atendimento id_atendimento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento ALTER COLUMN id_atendimento SET DEFAULT nextval('public.atendimento_id_atendimento_seq1'::regclass);


--
-- Name: colaborador id_colaborador; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador ALTER COLUMN id_colaborador SET DEFAULT nextval('public.colaborador_id_colaborador_seq1'::regclass);


--
-- Name: empresa codigo_empresa; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa ALTER COLUMN codigo_empresa SET DEFAULT nextval('public.empresa_codigo_empresa_seq1'::regclass);


--
-- Name: insumo codigo_insumo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.insumo ALTER COLUMN codigo_insumo SET DEFAULT nextval('public.insumo_codigo_insumo_seq1'::regclass);


--
-- Name: servico codigo_servico; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico ALTER COLUMN codigo_servico SET DEFAULT nextval('public.servico_codigo_servico_seq1'::regclass);


--
-- Name: servico_colaborador id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico_colaborador ALTER COLUMN id SET DEFAULT nextval('public.servico_colaborador_id_seq'::regclass);


--
-- Data for Name: administrador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.administrador (cpf_administrador, nome_administrador, email_administrador, senha_administrador, telefone_administrador, perfil, codigo_empresa) FROM stdin;
\.


--
-- Data for Name: agenda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agenda (id_agendamento, cpf_cliente, data, horario, id_colaborador, codigo_servico, codigo_empresa, status) FROM stdin;
45	475.255.341-44	2026-07-17	09:30:00	7	11	1	CONFIRMADO
46	928.327.648-51	2026-07-10	11:00:00	4	6	1	FINALIZADO
47	350.305.641-60	2026-07-09	13:30:00	4	7	1	FINALIZADO
48	395.376.724-09	2026-07-08	16:00:00	5	10	1	FINALIZADO
49	238.849.696-92	2026-07-07	10:00:00	8	12	1	CANCELADO
50	532.871.012-69	2026-07-18	17:00:00	4	8	1	PENDENTE
41	026.542.351-14	2026-07-15	09:00:00	4	4	1	FINALIZADO
42	161.559.407-89	2026-07-15	10:30:00	4	5	1	CANCELADO
51	395.376.724-09	2026-07-20	10:00:00	8	13	1	CONFIRMADO
43	816.184.959-50	2026-07-16	14:00:00	5	9	1	FINALIZADO
52	026.542.351-14	2026-07-20	16:30:00	5	9	1	FINALIZADO
44	310.341.316-56	2026-07-16	15:30:00	8	13	1	CANCELADO
54	026.542.351-14	2026-07-20	11:30:00	5	9	1	FINALIZADO
55	816.184.959-50	2026-07-20	13:30:00	5	9	1	FINALIZADO
53	026.542.351-14	2026-07-20	10:30:00	5	10	1	FINALIZADO
\.


--
-- Data for Name: atendimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.atendimento (id_atendimento, id_colaborador, codigo_servico, cpf_cliente, data_hora, valor_arrecadado, codigo_insumo, qtd_aplicacoes, custo_insumo, codigo_empresa) FROM stdin;
1	4	6	928.327.648-51	2026-07-10 11:00:00	150.00	7	1	15.00	1
2	4	7	350.305.641-60	2026-07-09 13:30:00	250.00	6	1	15.00	1
3	5	10	395.376.724-09	2026-07-08 16:00:00	40.00	11	1	15.00	1
\.


--
-- Data for Name: cadastro_insumo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cadastro_insumo (codigo_insumo, rende_aplicacoes, codigo_servico) FROM stdin;
4	20	4
5	20	5
7	2	6
6	5	7
6	8	8
10	25	9
11	25	10
3	30	11
12	15	12
3	30	13
\.


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (cpf_cliente, nome_cliente, endereco_cliente, telefone_cliente, email_cliente, data_nascimento, senha_cliente, perfil, codigo_empresa) FROM stdin;
026.542.351-14	Mariana Alves Pereira	Rua das Flores, 123, Porto Alegre - RS	(51) 99876-5432	mariana.pereira@email.com	1992-03-14	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
161.559.407-89	João Vitor Santos	Av. Ipiranga, 450, Porto Alegre - RS	(51) 98765-1234	joao.santos@email.com	1988-07-22	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
816.184.959-50	Camila Ribeiro Souza	Rua Voluntários da Pátria, 890, Porto Alegre - RS	(51) 99654-8821	camila.souza@email.com	1995-11-05	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
310.341.316-56	Rafael Augusto Barbosa	Rua Padre Chagas, 210, Porto Alegre - RS	(51) 98543-9910	rafael.barbosa@email.com	1990-01-30	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
475.255.341-44	Juliana Cristina Nunes	Av. Protásio Alves, 3200, Porto Alegre - RS	(51) 99432-7766	juliana.nunes@email.com	1998-05-18	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
928.327.648-51	Pedro Henrique Almeida	Rua General Câmara, 77, Porto Alegre - RS	(51) 98321-6654	pedro.almeida@email.com	1985-09-09	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
350.305.641-60	Larissa Beatriz Cardoso	Rua Fernando Machado, 340, Porto Alegre - RS	(51) 99210-5543	larissa.cardoso@email.com	1993-12-25	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
395.376.724-09	Thiago Matheus Farias	Av. Assis Brasil, 1500, Porto Alegre - RS	(51) 98109-4432	thiago.farias@email.com	1991-04-11	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
238.849.696-92	Bruna Letícia Teixeira	Rua João Alfredo, 65, Porto Alegre - RS	(51) 99098-3321	bruna.teixeira@email.com	1997-08-03	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
532.871.012-69	Gustavo Henrique Moura	Rua Lima e Silva, 210, Porto Alegre - RS	(51) 98987-2210	gustavo.moura@email.com	1989-06-27	$2a$10$sDCbwxtsS9Iqmz9zNU21Lu9NN4tHvkG/vb3HcFFUNK9OrgRlfVJw6	cliente	1
\.


--
-- Data for Name: colaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.colaborador (id_colaborador, cpf_colaborador, nome_colaborador, email_colaborador, funcao_colaborador, telefone_colaborador, foto_colaborador, senha_colaborador, perfil, codigo_empresa) FROM stdin;
4	494.287.720-20	Ana Souza	ana@letssalon.com	Cabeleireiro	(47) 98936-0430	\N	$2a$10$XFj83nESydkxxlJEmLBh.uVb94yO8NOTkBi62sbVo8mwfovz1qOLq	colaborador	1
5	776.708.010-26	Clara	clara@letssalon.com	Manicure	(61) 97643-4057	\N	$2a$10$Vl1YHjxwdBovfOhKQdSwVeshrm9E740zBmOGniRsiATsusKbckOnS	colaborador	1
7	104.332.181-00	Fernanda Lima	fernanda@letssalon.com	Esteticista	(51) 98211-4432	\N	$2a$10$K8G0NQwGf4QzvN3kXw0L6eWkT0bQe7J4M8gAqR9u3dS6M5R4V8z1G	COLABORADOR	1
9	083.863.794-99	Bianca Oliveira	bianca@letssalon.com	Recepcionista	(51) 98456-2290	\N	$2a$10$K8G0NQwGf4QzvN3kXw0L6eWkT0bQe7J4M8gAqR9u3dS6M5R4V8z1G	COLABORADOR	1
1	090.834.929-74	Jéssica Casaril	jessica@letssalon.com	Administrador	(49) 99902-7241	\N	$2a$10$v.HA8XFUU/pi/gZPjg4b/uDN6kSflpdAvheETpSbZS0fDY6T/PvZy	administrador	1
8	960.013.389-14	Lucas Rocha	lucas@letssalon.com	Cabeleireiro	(51) 99123-7765	\N	$2a$10$K8G0NQwGf4QzvN3kXw0L6eWkT0bQe7J4M8gAqR9u3dS6M5R4V8z1G	COLABORADOR	1
11	777.741.020-27	maria	maria@letssalon.com	Cabeleireiro	(94) 98488-7175	\N	$2a$10$LmJ2NCRXUkhvQrnr.tEIxeNgP05OFkEN6mW3CSuCKCG2Oz1zyPwcS	colaborador	1
\.


--
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.empresa (codigo_empresa, nome_empresa, telefone_empresa, email_empresa, endereco_empresa) FROM stdin;
1	Let's Salon Studio Piloto	\N	\N	\N
\.


--
-- Data for Name: insumo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.insumo (codigo_insumo, nome_insumo, valor_insumo, quantidade_insumo, capacidade, codigo_empresa) FROM stdin;
4	Shampoo Profissional	45.90	20	1L	1
5	Condicionador Profissional	42.50	20	1L	1
6	Máscara Hidratante	65.00	15	500g	1
7	Tintura Castanho	28.00	25	60g	1
8	Tintura Loiro	30.00	20	60g	1
9	Oxidante 20 Vol	18.50	30	900ml	1
10	Esmalte Vermelho	8.90	40	8ml	1
11	Esmalte Nude	8.90	40	8ml	1
12	Cera Modeladora	35.00	15	100g	1
3	Álcool 70%	12.50	20	1L	1
14	Esmalte Azul	8.90	5	8ml	1
\.


--
-- Data for Name: servico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.servico (codigo_servico, nome_servico, valor_servico, duracao_aproximada, id_colaborador, codigo_insumo, quantidade_insumo, codigo_empresa) FROM stdin;
4	Corte Feminino	80.00	60 min	4	4	1	1
5	Escova	60.00	45 min	4	5	1	1
6	Coloração	150.00	120 min	4	7	1	1
7	Progressiva	250.00	180 min	4	6	1	1
8	Hidratação	90.00	60 min	4	6	1	1
10	Pedicure	40.00	50 min	5	11	1	1
11	Limpeza de Pele	120.00	60 min	7	3	1	1
12	Corte Masculino	45.00	30 min	8	12	1	1
13	Barba	35.00	20 min	8	3	1	1
9	Manicure	40.00	40 min	5	10	1	1
15	Design Sobrancelhas	70.00	30 min	11	7	1	1
\.


--
-- Data for Name: servico_colaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.servico_colaborador (id, codigo_servico, id_colaborador) FROM stdin;
1	4	4
2	5	4
3	6	4
4	7	4
5	8	4
6	9	5
7	10	5
8	11	7
9	12	8
10	13	8
11	15	11
\.


--
-- Name: agenda_id_agendamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agenda_id_agendamento_seq', 1, false);


--
-- Name: agenda_id_agendamento_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.agenda_id_agendamento_seq1', 55, true);


--
-- Name: atendimento_id_atendimento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.atendimento_id_atendimento_seq', 1, false);


--
-- Name: atendimento_id_atendimento_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.atendimento_id_atendimento_seq1', 3, true);


--
-- Name: colaborador_id_colaborador_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.colaborador_id_colaborador_seq', 1, false);


--
-- Name: colaborador_id_colaborador_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.colaborador_id_colaborador_seq1', 12, true);


--
-- Name: empresa_codigo_empresa_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empresa_codigo_empresa_seq', 1, false);


--
-- Name: empresa_codigo_empresa_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empresa_codigo_empresa_seq1', 1, true);


--
-- Name: insumo_codigo_insumo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.insumo_codigo_insumo_seq', 1, false);


--
-- Name: insumo_codigo_insumo_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.insumo_codigo_insumo_seq1', 14, true);


--
-- Name: servico_codigo_servico_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servico_codigo_servico_seq', 1, false);


--
-- Name: servico_codigo_servico_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servico_codigo_servico_seq1', 17, true);


--
-- Name: servico_colaborador_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servico_colaborador_id_seq', 11, true);


--
-- Name: administrador administrador_email_administrador_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_email_administrador_key UNIQUE (email_administrador);


--
-- Name: administrador administrador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_pkey PRIMARY KEY (cpf_administrador);


--
-- Name: agenda agenda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda
    ADD CONSTRAINT agenda_pkey PRIMARY KEY (id_agendamento);


--
-- Name: atendimento atendimento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_pkey PRIMARY KEY (id_atendimento);


--
-- Name: cadastro_insumo cadastro_insumo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_pkey PRIMARY KEY (codigo_insumo, codigo_servico);


--
-- Name: cliente cliente_email_cliente_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_email_cliente_key UNIQUE (email_cliente);


--
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (cpf_cliente);


--
-- Name: colaborador colaborador_cpf_colaborador_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_cpf_colaborador_key UNIQUE (cpf_colaborador);


--
-- Name: colaborador colaborador_email_colaborador_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_email_colaborador_key UNIQUE (email_colaborador);


--
-- Name: colaborador colaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_pkey PRIMARY KEY (id_colaborador);


--
-- Name: empresa empresa_nome_empresa_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_nome_empresa_key UNIQUE (nome_empresa);


--
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (codigo_empresa);


--
-- Name: insumo insumo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.insumo
    ADD CONSTRAINT insumo_pkey PRIMARY KEY (codigo_insumo);


--
-- Name: servico_colaborador servico_colaborador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico_colaborador
    ADD CONSTRAINT servico_colaborador_pkey PRIMARY KEY (id);


--
-- Name: servico servico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_pkey PRIMARY KEY (codigo_servico);


--
-- Name: servico_colaborador uk_servico_profissional; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico_colaborador
    ADD CONSTRAINT uk_servico_profissional UNIQUE (codigo_servico, id_colaborador);


--
-- Name: administrador administrador_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.administrador
    ADD CONSTRAINT administrador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: agenda agenda_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda
    ADD CONSTRAINT agenda_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: agenda agenda_codigo_servico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda
    ADD CONSTRAINT agenda_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico);


--
-- Name: agenda agenda_cpf_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda
    ADD CONSTRAINT agenda_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES public.cliente(cpf_cliente);


--
-- Name: agenda agenda_id_colaborador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.agenda
    ADD CONSTRAINT agenda_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador);


--
-- Name: atendimento atendimento_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: atendimento atendimento_codigo_insumo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo);


--
-- Name: atendimento atendimento_codigo_servico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico);


--
-- Name: atendimento atendimento_cpf_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_cpf_cliente_fkey FOREIGN KEY (cpf_cliente) REFERENCES public.cliente(cpf_cliente);


--
-- Name: atendimento atendimento_id_colaborador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.atendimento
    ADD CONSTRAINT atendimento_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador);


--
-- Name: cadastro_insumo cadastro_insumo_codigo_insumo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo);


--
-- Name: cadastro_insumo cadastro_insumo_codigo_servico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cadastro_insumo
    ADD CONSTRAINT cadastro_insumo_codigo_servico_fkey FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico);


--
-- Name: colaborador colaborador_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: cliente fk_cliente_empresa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT fk_cliente_empresa FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: servico_colaborador fk_sc_colaborador; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico_colaborador
    ADD CONSTRAINT fk_sc_colaborador FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador);


--
-- Name: servico_colaborador fk_sc_servico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico_colaborador
    ADD CONSTRAINT fk_sc_servico FOREIGN KEY (codigo_servico) REFERENCES public.servico(codigo_servico);


--
-- Name: insumo insumo_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.insumo
    ADD CONSTRAINT insumo_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: servico servico_codigo_empresa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_codigo_empresa_fkey FOREIGN KEY (codigo_empresa) REFERENCES public.empresa(codigo_empresa);


--
-- Name: servico servico_codigo_insumo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_codigo_insumo_fkey FOREIGN KEY (codigo_insumo) REFERENCES public.insumo(codigo_insumo);


--
-- Name: servico servico_id_colaborador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servico
    ADD CONSTRAINT servico_id_colaborador_fkey FOREIGN KEY (id_colaborador) REFERENCES public.colaborador(id_colaborador);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO postgres;


--
-- PostgreSQL database dump complete
--

\unrestrict FTkqWlbpAe2ap48BgkQIqdidQTs3V9sEbjZx8o8DgjMIAe0n72stI8JJQzZWSKT

