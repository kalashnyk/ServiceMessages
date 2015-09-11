--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.4
-- Started on 2015-09-05 18:13:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 2020 (class 1262 OID 12135)
-- Dependencies: 2019
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 179 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 179
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 178 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 178
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

--
-- TOC entry 176 (class 1259 OID 16622)
-- Name: addressbook_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE addressbook_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE addressbook_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 16672)
-- Name: addressbook; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE addressbook (
    addressbook_id bigint DEFAULT nextval('addressbook_id_seq'::regclass) NOT NULL,
    user_id bigint,
    contact_id bigint
);


ALTER TABLE addressbook OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 16595)
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE messages_id_seq OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 16592)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE messages (
    message_id bigint DEFAULT nextval('messages_id_seq'::regclass) NOT NULL,
    from_user bigint,
    to_user bigint,
    date_time timestamp without time zone,
    subject character varying,
    message character varying
);


ALTER TABLE messages OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16586)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16583)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_id bigint DEFAULT nextval('users_id_seq'::regclass) NOT NULL,
    fio character varying,
    email character varying,
    login character varying,
    password character varying,
    role character varying,
    enabled boolean DEFAULT true
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 2014 (class 0 OID 16672)
-- Dependencies: 177
-- Data for Name: addressbook; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO addressbook (addressbook_id, user_id, contact_id) VALUES (1050, 1, 1651);
INSERT INTO addressbook (addressbook_id, user_id, contact_id) VALUES (1100, 1651, 1653);
INSERT INTO addressbook (addressbook_id, user_id, contact_id) VALUES (1101, 1, 1653);
INSERT INTO addressbook (addressbook_id, user_id, contact_id) VALUES (1150, 1, 1950);
INSERT INTO addressbook (addressbook_id, user_id, contact_id) VALUES (1151, 1, 1654);


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 176
-- Name: addressbook_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('addressbook_id_seq', 23, true);


--
-- TOC entry 2011 (class 0 OID 16592)
-- Dependencies: 174
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO messages (message_id, from_user, to_user, date_time, subject, message) VALUES (900, 1, 1651, '2015-09-05 12:32:39.248', 'Привет', 'Как дела');
INSERT INTO messages (message_id, from_user, to_user, date_time, subject, message) VALUES (950, 1651, 1653, '2015-09-05 12:44:57.369', 'Hello', 'How are you?');


--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 175
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('messages_id_seq', 19, true);


--
-- TOC entry 2009 (class 0 OID 16583)
-- Dependencies: 172
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1, 'Калашнюк Олег Петрович', 'kao8@mail.ru', 'admin', 'admin', 'ROLE_ADMIN', true);
INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1652, 'Дестини Хоуп Сайрус', 'houp@mail.ru', 'houp', 'houp', 'ROLE_USER', true);
INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1653, 'Томас Мапотер Четвертый', 'tomas@mail.ru', 'tomas', 'tomas', 'ROLE_USER', true);
INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1654, 'Марк Винсент', 'mark@mail.ru', 'mark', 'mark', 'ROLE_USER', true);
INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1651, 'Алисия Бет Мур', 'bet@mail.ru', 'bet', 'bet', 'ROLE_USER', true);
INSERT INTO users (user_id, fio, email, login, password, role, enabled) VALUES (1950, 'Панова Валерия Александровна', 'bengalka@mail.ru', 'bengalka', 'bengalka', 'ROLE_ADMIN', true);


--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 173
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 39, true);


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2015-09-05 18:13:42

--
-- PostgreSQL database dump complete
--

