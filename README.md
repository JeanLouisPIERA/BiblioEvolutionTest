
# BiblioTest (Duplicate de Biblio)

# Stratégie GitFlow :

Notre projet sera basé sur deux branches : master et develop. Ces deux branches sont strictement interdites en écriture aux développeurs.

La branche master est le miroir de la production. Il est donc logique que l'on ne puisse y pousser les modifications directement.

La branche develop centralise toutes les nouvelles fonctionnalités qui seront livrées dans la prochaine version. On ne doit pas y faire des livraisons directement

La branche ticket1 est une branche feature ("de fonctionnalité") créée à partir de sa branche parente develop à laquelle elle sera mergée lorsque le bug du ticket1 sera corrigé.  

# Présentation :

Projet 7 développé dans le cadre du parcours académique Développeur Java OPENCLASSROOMS : Système de suivi des prêts d’ouvrage d’une bibliothèque municipale incluant une application web ouverte aux usagers, un web service et un batch des relances par mail 

# Références :

- CONTACT : jeanlouispiera@yahoo.fr
- VERSION : 1.0
- Mai-Aout 2020

# Présentation Générale :

Cet projet multi-modulaires MAVEN est un projet académique dans le cadre du parcours développeur JAVA d'OPENCLASSROOMS.

Il doit permettre aux abonnés et aux employés d'une bibliothèque municipale de bénéficier de services en ligne.

Il se présente sous 3 modules :

- l'API "biblioWebServiceRest" qui gère l'ensemble des services de création, modification, consultation et/ou suppression des comptes utilisateurs, catégories de livres, livres et prêts. Elle n'est pas directement accessible abonnés et employés. Une documentation SWAGGER a été installée,

- une application Web "biblioWebAppli" permet aux abonnés de se connecter pour consulter l'ensemble des livres disponibles, faire des sélections, emprunter un ou plusieurs ouvrages, consulter ses prêts et prolonger la durée d'un prêt,

- une application batch "biblioBatch" qui permet de récupérer à intervalles réguliers la liste des prêts échus dont le livre n'a pas été restitué et d'adresser un mail de relance aux abonnés retardataires
 	
Les données ne sont accessibles aux modules Web et Batch qu'au travers de l'API.
 

# Technologies : 
Ce projet a été écrit en Java avec JEE et la version 2.3.0.M2 de Springboot. Toutes les méthodes sont commentées en Javadoc.

# Prérequis : 
L'environnement d'installation minimum est :
- Apache Maven version 3.6.2
- JRE 1.8.0_231
- Database Postgresql0

# Installation du package

- récupérer le package à installer sur le dépôt GitHub https://github.com/JeanLouisPIERA/Biblio

- installer le package de la suite applicative où vous le souhaitez sur votre disque 

- copier les fichiers mvnw et mvnw.cmd à l'intérieur de chacune des 3 directories applicatives à savoir : "biblioWebServiceRest", "biblioWebAppli" et "biblioBatch" (obligatoire pour le déploiement de chaque module en ligne de commande).


# Installation et lancement de l'API "biblioWebServiceRest"
 
L'API "biblioWebServiceRest" doit être lancée en premier. Avant son lancement, il faut :

- créer une base de données vide dans Postgresql : la structure des tables est crée par le framework Hibernate dans le container SpringBoot au lancement de l'API "biblioWebServiceRest" qui génère un premier jeu de données

- paramétrer le nom de votre base, son username et son password en modifiant les paramètres de ce nom précédés du préfixe spring.datasource dans le fichier src/main/resources/applications-properties

- l'API gère la durée des prêts à partir de 2 constantes (durée du prêt initial, durée de la prolongation) dont la valeur est modifiable dans le fichier application.properties de l'applicationr
- ouvrir la commande en ligne dans la directory où vous avez copié l'API "biblioWebServiceRest"n
- taper la commande "mvnw spring-boot:run" pour lancer la compilation de l'applicationn
- une fois l'application lancée, pour accéder à la documentation SWAGGER, taper http://localhost:8080/swagger-UI.html dans votre navigateur (si le port localhos:8080 n'est pas disponible, il peut être modifié dans le fichier application.properties)

- chaque endpoint commenté dans SWAGGER peut être consommé


# Installation et lancement de l'Application Web "biblioWebAppli".
L'Application Web biblioWebAppli est un client sécurisé à utiliser pour consommer l'API biblioWebServiceRest en tant qu'abonné de la bibliothèque..

Avant le lancement de l'application, il faut obtenir un nom d'utilisateur et un mot de passe validé dans l'API.

Vérifier dans le fichier application.properties où se trouvent les endpoints sécurisés de l'API que toutes les valeurs sont valides.

Ensuite, il faut :

- ouvrir la commande en ligne dans la directory où vous avez copié l'Appli "biblioWebAppli"

- taper la commande "mvnw spring-boot:run" pour lancer la compilation de l'application

- une fois l'application lancée, pour accéder à l'application, taper http://localhost:8081/login dans votre navigateur (si le port localhost:8081 n'est pas disponible, il peut être modifié dans le fichier application.properties)


# Installation et lancement du Batch "biblioBatch"

Le batch est un service sécurisé qui peut être lancé avec l'API pour identifier à intervalles réguliers l'ensemble des prêts de livres non retournés et générer des mails de relance adressés aux abonnés retardataires.

La durée de l'intervalle est paramétrée dans le fichier application.properties dans la variable application.cron où elle peut être modifiée.

Dans ce fichier se trouvent d'autres paramètres modifiables comme le nom et le mot de passe pour accéder aux endpoints autorisés de l'API et les informations nécessaires à l'envoi des mails (la variable "application.mail" gère l'adresse mail de l'expéditeur et toutes les variables dont le préfixe est "spring.mail" gèrent informations du seveur smtp : par défaut le serveur utilisé est mailtrap.). Il est recommandé à tout nouvel utilisateur d'ouvrir une adresse mail expéditeur et de mettre à jour les variables dans le fichier application.properties.

Ensuite, il faut :

- ouvrir la commande en ligne dans la directory où vous avez copié l'Appli "biblioBatch"

- taper la commande "mvnw spring-boot:run" pour lancer la compilation de l'application qui s'éxécutera automatiquement à intervalles réguliers jusqu'à sa mise en arrêt manuel par l'utilisateur. 













