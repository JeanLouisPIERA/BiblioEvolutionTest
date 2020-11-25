

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

INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (10, 'Balzac', 1, 0, 'Le Pere Goriot', 1);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (11, 'Einstein', 3, 0, 'Comment je vois le monde', 2);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (12, 'Collectif', 2, 0, 'Mathematiques au college', 3);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (13, 'Boudard', 2, 0, 'Physiologie des cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (14, 'Jean Anouilh', 2, 0,'La guerre de Troie n''aura pas lieu', 4);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (15, 'Achille Zavatta', 2, 2, 'Memoire d''un clown', 5);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (16, 'Victor Hugo', 3, 3, 'La Legende des Siecles', 6);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (17, 'JRR Tolkien', 5, 5, 'Le Seigneur de l''Anneau', 7);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (18, 'Uderzo et Goscinny', 1, 1, 'Asterix Le Gaulois', 8);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (19, 'Alphonse Boudard', 2, 1, 'Metamorphose des cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (20, 'Frederic Dard', 3, 2, 'San Antonio a de la memoire', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (21, 'Frederic Dard', 1, 1, 'San Antonio fume les cloportes', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (22, 'Antoine Dominique', 2, 2, 'Le Gorille joue au clown', 9);
INSERT INTO public.livre (num_livre, auteur, nb_exemplaires, nb_exemplaires_disponibles, titre, num_categorie) VALUES (23, 'Herge', 1, 0, 'Tintin et le mystère de l''oreille cassée', 8);
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

INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (1, 'Jean-Charles@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Jean-Charles', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (2, 'Charlemagne@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Charlemagne', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (3, 'Alexandre@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Alexandre', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (6, 'Tintin@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Tintin', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (7, 'Emilou@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Emilou', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (8, 'Haddock@hotmail.com', '$2a$10$D5I01lR8Ku3kqUTFdXi9aeU4Q24OdIAeOSK.t4xC/vLIEL8tL0OSC', 'Haddock', 2);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (4, 'Admin10@hotmail.com', '$2a$10$q/RTq/ulI1SCnBZc3SHna.PmIa8psA9ZAH1KsbfB1daG5yVNcycx.', 'Admin10', 1);
INSERT INTO public.utilisateur (id_user, adresse_mail, password, username, role_id) VALUES (5, 'UserBatch@hotmail.com', '$2a$10$IT3thelqGJ4iB/pfcwX7cO2xPnhZPHBlLNvw49DqfTuLlWWuPuEN2', 'UserBatch', 2);



--
-- TOC entry 2856 (class 0 OID 331293)
-- Dependencies: 205
-- Data for Name: pret; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (21, '2020-09-10', NULL, '2020-11-10', 1, 14, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (22, '2020-09-30', NULL, '2020-12-30', 2, 14, 8);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (23, '2020-09-30', '2020-10-15', '2020-10-30', 3, 23, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (24, '2020-09-30', NULL, '2020-10-30', 1, 11, 6);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (25, '2020-10-04', NULL, '2020-12-04', 2, 20, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (26, '2020-10-05', NULL, '2020-11-05', 1, 10, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (27, '2020-10-20', NULL, '2020-11-20', 1, 13, 1);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (28, '2020-10-25', NULL, '2020-11-25', 0, 12, 8);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (29, '2020-10-25', NULL, '2020-12-25', 2, 12, 7);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (30, '2020-11-02', NULL, '2020-12-02', 0, 11, 1);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (31, '2020-11-10', NULL, '2020-12-10', 0, 13, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (32, '2020-11-12', NULL, '2020-12-12', 0, 11, 7);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (33, '2020-11-14', '2020-11-18', '2020-12-14', 3, 24, 6);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (34, '2020-11-14', '2020-11-18', '2020-12-14', 3, 19, 7);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (35, '2020-11-19', NULL, '2020-12-19', 0, 19, 3);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (36, '2020-11-19', '2020-11-21', '2020-12-19', 3, 18, 8);
INSERT INTO public.pret (num_pret, date_pret, date_retour_effectif, date_retour_prevue, pret_statut, livre_num_livre, user_id) VALUES (37, '2020-11-19', NULL, '2020-12-19', 0, 23, 6);

INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (1, '2020-11-02', NULL, NULL, '2020-11-03', 1, 1, 11, 6);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (2, '2020-11-08', NULL, NULL, '2020-11-09', 1, 2, 11, 7);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (3, '2020-11-10', NULL, NULL, '2020-11-11', 1, 3, 11, 8);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (4, '2020-11-02', NULL, NULL, NULL, 0, 1, 12, 6);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (5, '2020-11-08', NULL, NULL, NULL, 0, 2, 12, 7);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (6, '2020-11-10', NULL, NULL, NULL, 0, 3, 12, 8);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (7, '2020-11-15', '2020-11-18', '20-11-20', NULL, 2, 1, 24, 3);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (8, '2020-11-15', '2020-11-18', '20-11-20', '2020-11-19', 3, null, 19, 3);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (9, '2020-11-20', NULL, NULL, NULL, 0, 1, 18, 3);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (10, '2020-11-21', NULL, NULL, NULL, 0, 4, 12, 1);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (11, '2020-11-22', NULL, NULL, NULL, 0, 1, 14, 3);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (12, '2020-11-22', NULL, NULL, NULL, 0, 2, 18, 2);
INSERT INTO public.reservation (num_reservation, date_reservation, date_notification, date_deadline, date_suppression, reservation_statut, rang_reservation, livre_num_livre, user_id) VALUES (13, '2020-11-22', NULL, NULL, NULL, 0, 2, 24, 2);


--
-- TOC entry 2869 (class 0 OID 0)
-- Dependencies: 202
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 37, true);


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

   