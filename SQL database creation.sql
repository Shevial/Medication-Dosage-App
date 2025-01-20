--
-- PostgreSQL database dump
--

-- Dumped from database version 15.10
-- Dumped by pg_dump version 15.10

-- Started on 2025-01-20 17:27:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16425)
-- Name: dosages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dosages (
    medication_id integer NOT NULL,
    maximum_factor numeric(10,2) NOT NULL,
    minimum_factor numeric(10,2) NOT NULL,
    dosage_frequency text
);


ALTER TABLE public.dosages OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16417)
-- Name: medications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medications (
    id integer NOT NULL,
    name text NOT NULL,
    details text
);


ALTER TABLE public.medications OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16416)
-- Name: medications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medications_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medications_id_seq OWNER TO postgres;

--
-- TOC entry 3333 (class 0 OID 0)
-- Dependencies: 214
-- Name: medications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medications_id_seq OWNED BY public.medications.id;


--
-- TOC entry 3177 (class 2604 OID 16420)
-- Name: medications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medications ALTER COLUMN id SET DEFAULT nextval('public.medications_id_seq'::regclass);


--
-- TOC entry 3327 (class 0 OID 16425)
-- Dependencies: 216
-- Data for Name: dosages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dosages (medication_id, maximum_factor, minimum_factor, dosage_frequency) FROM stdin;
\.


--
-- TOC entry 3326 (class 0 OID 16417)
-- Dependencies: 215
-- Data for Name: medications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medications (id, name, details) FROM stdin;
\.


--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 214
-- Name: medications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medications_id_seq', 1, false);


--
-- TOC entry 3181 (class 2606 OID 16431)
-- Name: dosages dosages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dosages
    ADD CONSTRAINT dosages_pkey PRIMARY KEY (medication_id);


--
-- TOC entry 3179 (class 2606 OID 16424)
-- Name: medications medications_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medications
    ADD CONSTRAINT medications_pkey PRIMARY KEY (id);


--
-- TOC entry 3182 (class 2606 OID 16432)
-- Name: dosages fk_medication_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dosages
    ADD CONSTRAINT fk_medication_id FOREIGN KEY (medication_id) REFERENCES public.medications(id) NOT VALID;


-- Completed on 2025-01-20 17:27:14

--
-- PostgreSQL database dump complete
--

