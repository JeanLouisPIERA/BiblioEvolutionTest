--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2020-08-09 18:21:34


DROP TABLE IF EXISTS public.reservation; 
DROP TABLE IF EXISTS public.pret; 
DROP TABLE IF EXISTS public.utilisateur; 
DROP TABLE IF EXISTS public.role;
DROP TABLE IF EXISTS public.livre; 
DROP TABLE IF EXISTS public.categorie;
DROP SEQUENCE IF EXISTS public.hibernate_sequence;
DROP TABLE IF EXISTS public.hibernate_sequences;


--
-- TOC entry 202 (class 1259 OID 339456)
-- Name: categorie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorie (
    num_categorie bigint NOT NULL,
    nom_categorie character varying(255)
);


ALTER TABLE public.categorie OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 339459)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 339461)
-- Name: hibernate_sequences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hibernate_sequences (
    sequence_name character varying(255) NOT NULL,
    next_val bigint
);


ALTER TABLE public.hibernate_sequences OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 339464)
-- Name: livre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livre (
    num_livre bigint NOT NULL,
    auteur character varying(255),
    nb_exemplaires integer,
    nb_exemplaires_disponibles integer,
    date_retour_prevue_plus_proche date,
    nb_reservations_en_cours integer,
    titre character varying(255),
    num_categorie bigint
);


ALTER TABLE public.livre OWNER TO postgres;


CREATE TABLE public.reservation (
    num_reservation bigint NOT NULL,
    date_reservation date,
    date_notification date,
    date_deadline date,
    date_suppression date,
    reservation_statut integer,
    livre_num_livre bigint,
    user_id bigint
);


ALTER TABLE public.reservation OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 339470)
-- Name: pret; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pret (
    num_pret bigint NOT NULL,
    date_pret date,
    date_retour_effectif date,
    date_retour_prevue date,
    pret_statut integer,
    livre_num_livre bigint,
    user_id bigint
);


ALTER TABLE public.pret OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 339473)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name integer
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 339476)
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_id_seq OWNER TO postgres;

--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 208
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- TOC entry 209 (class 1259 OID 339478)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utilisateur (
    id_user bigint NOT NULL,
    adresse_mail character varying(255),
    password character varying(255),
    username character varying(255),
    role_id bigint
);


ALTER TABLE public.utilisateur OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 339484)
-- Name: utilisateur_id_user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utilisateur_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utilisateur_id_user_seq OWNER TO postgres;

--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 210
-- Name: utilisateur_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utilisateur_id_user_seq OWNED BY public.utilisateur.id_user;


--
-- TOC entry 2713 (class 2604 OID 339486)
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- TOC entry 2714 (class 2604 OID 339487)
-- Name: utilisateur id_user; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur ALTER COLUMN id_user SET DEFAULT nextval('public.utilisateur_id_user_seq'::regclass);


--
-- TOC entry 2716 (class 2606 OID 339489)
-- Name: categorie categorie_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorie
    ADD CONSTRAINT categorie_pkey PRIMARY KEY (num_categorie);


--
-- TOC entry 2718 (class 2606 OID 339491)
-- Name: hibernate_sequences hibernate_sequences_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hibernate_sequences
    ADD CONSTRAINT hibernate_sequences_pkey PRIMARY KEY (sequence_name);


--
-- TOC entry 2720 (class 2606 OID 339493)
-- Name: livre livre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT livre_pkey PRIMARY KEY (num_livre);

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (num_reservation);
    
    
--
-- TOC entry 2724 (class 2606 OID 339495)
-- Name: pret pret_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pret
    ADD CONSTRAINT pret_pkey PRIMARY KEY (num_pret);


--
-- TOC entry 2726 (class 2606 OID 339497)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- TOC entry 2722 (class 2606 OID 339499)
-- Name: livre ukl1cjiaxckarx60ohbrja9dbo1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT ukl1cjiaxckarx60ohbrja9dbo1 UNIQUE (titre, auteur);


--
-- TOC entry 2728 (class 2606 OID 339501)
-- Name: utilisateur utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT utilisateur_pkey PRIMARY KEY (id_user);


ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk64sut2lvpx80t9we8mcui2tu6 FOREIGN KEY (user_id) REFERENCES public.utilisateur(id_user);
    
    
--
-- TOC entry 2730 (class 2606 OID 339502)
-- Name: pret fk64sut2lvpx80t9we8mcui2tu6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pret
    ADD CONSTRAINT fk64sut2lvpx80t9we8mcui2tu6 FOREIGN KEY (user_id) REFERENCES public.utilisateur(id_user);


ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk7ejoe8ji4sgypnthjfkdi3kpw FOREIGN KEY (livre_num_livre) REFERENCES public.livre(num_livre);    
--
-- TOC entry 2731 (class 2606 OID 339507)
-- Name: pret fk7ejoe8ji4sgypnthjfkdi3kpw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pret
    ADD CONSTRAINT fk7ejoe8ji4sgypnthjfkdi3kpw FOREIGN KEY (livre_num_livre) REFERENCES public.livre(num_livre);


--
-- TOC entry 2732 (class 2606 OID 339512)
-- Name: utilisateur fkaqe8xtajee4k0wlqrvh2pso4l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utilisateur
    ADD CONSTRAINT fkaqe8xtajee4k0wlqrvh2pso4l FOREIGN KEY (role_id) REFERENCES public.role(id);


--
-- TOC entry 2729 (class 2606 OID 339517)
-- Name: livre fkpqui4qliun7pqwb327v51rys7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livre
    ADD CONSTRAINT fkpqui4qliun7pqwb327v51rys7 FOREIGN KEY (num_categorie) REFERENCES public.categorie(num_categorie);


-- Completed on 2020-08-09 18:21:44

--
-- PostgreSQL database dump complete
--
