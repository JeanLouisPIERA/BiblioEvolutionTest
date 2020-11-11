

--
-- TOC entry 2854 (class 0 OID 331280)
-- Dependencies: 203
-- Data for Name: categorie; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (1, 'Roman');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (2, 'Essai scientifique');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (3, 'Manuel scolaire');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (4, 'Theatre');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (5, 'Memoire');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (6, 'Poesie');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (7, 'Heroic Fantasy');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (8, 'Bande Dessinee');
INSERT INTO public.categorie (num_categorie, nom_categorie) VALUES (9, 'Policier');


--
-- TOC entry 2855 (class 0 OID 331285)
-- Dependencies: 204
-- Data for Name: livre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (10, 'Balzac', 1, 1, 'Le Pere Goriot', 1);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (11, 'Einstein', 2, 0, 'Comment je vois le monde', 2);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (12, 'Collectif', 3, 2, 'Mathematiques au college', 3);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (13, 'Boudard', 1, 1, 'Physiologie des cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (14, 'Jean Anouilh', 1, 1, 'La guerre de Troie n''aura pas lieu', 4);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (15, 'Achille Zavatta', 2, 0, 'Memoire d''un clown', 5);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (16, 'Victor Hugo', 3, 2, 'La Legende des Siecles', 6);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (17, 'JRR Tolkien', 5, 5, 'Le Seigneur de l''Anneau', 7);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (18, 'Uderzo et Goscinny', 10, 10, 'Asterix Le Gaulois', 8);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (19, 'Alphonse Boudard', 1, 1, 'Metamorphose des cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (20, 'Frederic Dard', 3, 1, 'San Antonio a de la memoire', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (21, 'Frederic Dard', 1, 0, 'San Antonio fume les cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (22, 'Antoine Dominique', 2, 0, 'Le Gorille joue au clown', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (23, 'Herge', 6, 0, 'Tintin et le mystèrez de l''oreille cassée', 8);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (24, 'Apollinaire', 1, 1, 'Alcools', 6);



--
-- TOC entry 2858 (class 0 OID 331300)
-- Dependencies: 207
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role (id, name) VALUES (1, 1);
INSERT INTO public.role (id, name) VALUES (2, 0);


--
-- TOC entry 2860 (class 0 OID 331308)
-- Dependencies: 209
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (1, 'Jean-Charles@hotmail.com', '$2a$10$O8YsVymxoY8D7Hs2Eegjgenw9tM23XexaPwVXsAnmv6Mn/Iars7FG', 'Jean-Charles', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (2, 'Charlemagne@hotmail.com', '$2a$10$tm6MaYpRYfkNsW3lPSZB2.cFuJk6jwI2FJVrL3yfjffXNCEkeVLKC', 'Charlemagne', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (3, 'Alexandre@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Alexandre', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (4, 'Admin10@hotmail.com', '$2a$10$q/RTq/ulI1SCnBZc3SHna.PmIa8psA9ZAH1KsbfB1daG5yVNcycx.', 'Admin10', 1);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (5, 'UserBatch@hotmail.com', '$2a$10$IT3thelqGJ4iB/pfcwX7cO2xPnhZPHBlLNvw49DqfTuLlWWuPuEN2', 'UserBatch', 2);



--
-- TOC entry 2856 (class 0 OID 331293)
-- Dependencies: 205
-- Data for Name: pret; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (25, '2020-10-05', NULL, '2020-11-05', 1, 10, 1);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (26, '2020-09-15', NULL, '2020-11-15', 2, 11, 2);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (27, '2020-10-15', NULL, '2020-11-15', 0, 12, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (28, '2020-09-15','2020-09-30', '2020-10-15', 3, 13, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (29, '2020-10-15', NULL, '2020-12-15', 2, 12, 2);





--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 202
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 29, true);


--
-- TOC entry 2870 (class 0 OID 0)
-- Dependencies: 206
-- Name: role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_id_seq', 2, true);


--
-- TOC entry 2871 (class 0 OID 0)
-- Dependencies: 208
-- Name: utilisateur_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utilisateur_id_user_seq', 5, true);


-- Completed on 2020-08-09 16:14:05

--
-- PostgreSQL database dump complete
--

   