
# BiblioEvolutionTest  

# Présentation :

Projet 10 développé dans le cadre du parcours académique Développeur Java OPENCLASSROOMS qui fait suite au Projet 7 qui avait pour objectif de créer un système de suivi des prêts d’ouvrage d’une bibliothèque municipale incluant une application web ouverte aux usagers, un web service et un batch des relances par mail 

Le projet 10 a permis de corriger un bug du Projet 7 en empêchant la prolongation de prêts déjà échus et en doublant l'envoi de mails aux titulaires de prêts à échoir en plus des mails déjà envoyés aux titulaires de prêts échus (#Ticket 2 branche HOTFIX).

Le projet 10 a aussi permis de développer une nouvelle fonctionnalité de réservation de livres sans aucun exemplaire disponible en limitant le nombre de réservataires à 2 fois le nombre total d'exemplaires : l'utilisateur de l'appli WEB a la possibilité d'enregistrer ou de supprimer en ligne sa réservation et de connaitre pour chaque livre réservé la date de retour la plus proche, le nombre de réservataires inscrits et son rang dans la file d'attente. Dès qu'un exemplaire est disponible, la réservation en 1er rang est notifiée et son réservataire reçoit un mail l'informant qu'il dispose de 48 heures pour se rendre à la bibliothèque et emprunter le livre ; dans le cas contraire la réservation est annulée et le réservataire dans le rang immédiatement suivant reçoit un mail de notification. L'API prévoit un endpoint qui permet à l'agent de la bibliothèque de "livrer" la réservation notifiée en créant dans la même opération le prêt du livre. (#Ticket 1 branche Feature)

Le projet 10 a enfin permis de mettre en place des tests unitaires pour l'ensemble des classes de la couche SERVICE de l'API avec un taux de couverture de 100%. Les tests d'intégration de l'API ont été automatisés sur tous les endpoints de la couche CONTROLLER à l'aide de l'outil POSTMAN. (#Ticket 3 branche Release 1.1.2) 

# Références :

- CONTACT : jeanlouispiera@yahoo.fr
- VERSION : 1.1.2-SNAPSHOT0
- Novembre-Décembre 20200

# Présentation Générale :

Cet projet multi-modulaires MAVEN est un projet académique dans le cadre du parcours développeur JAVA d'OPENCLASSROOMS.

Il doit permettre aux abonnés et aux employés d'une bibliothèque municipale de bénéficier de services en ligne.

Il se présente sous 3 modules :

- l'API "biblioWebServiceRest" qui gère l'ensemble des services de création, modification, consultation et/ou suppression des  catégories de livres, livres, prêts et réservations. Elle permet de gérer la file d'attente des réservations, de notifier une réservation en accordant un droit prioritaire à emprunter pendant 48h et de livrer une réservation en créant dans la même opération le prêt correspondant. Elle n'est pas directement accessible aux abonnés.,

- une application Web "biblioWebAppli" permet à chaque abonné de se connecter de manière sécurisée à son espace personnel pour consulter l'ensemble des livres disponibles, faire des sélections, emprunter un ou plusieurs ouvrages disponibles, consulter ses prêts et prolonger la durée d'un prêt dans la limite d'un seul renouvellement. Il peut depuis la dernière Release réserver un exemplaire et supprimer sa réservation, consulter le nombre de réservataires et son rang dans la file d'attente. ,

- une application batch "biblioBatch" qui permet de récupérer à intervalles réguliers la liste des prêts échus dont le livre n'a pas été restitué et d'adresser un mail de relance aux abonnés retardataire. Depuis la dernière Release, le batch gère aussi l'envoi d'un mail d'avertissement aux utilisateurs dont le prêt vient bientôt à échéance pour qu'ils pensent à ramener leur livre à la Bibliothèque et l'envoi d'un mail de notification aux réservataires en 1er rang qui dispose de 48 heures à réception pour venir chercher leur livre à la bibliotèque.s
 
Les données ne sont accessibles aux modules Web et Batch qu'au travers de l'API.

# Technologies : 
Ce projet a été écrit en Java avec JEE et la version 2.3.0.M2 de Springboot. Toutes les méthodes sont commentées en Javadoc.



# Prérequis : 
L'environnement d'installation minimum est :
- Apache Maven version 3.6.2
- JRE 1.8.0_231
- Database Postgresql


# Installation du packagee
- récupérer le package à installer sur le dépôt GitHub https://github.com/JeanLouisPIERA/BiblioEvolutionTest

- installer le package de la suite applicative où vous le souhaitez sur votre disque 


# Installation et lancement de l'API "biblioWebServiceRest"
 
L'API "biblioWebServiceRest" doit être lancée en premier. Avant son lancement, il faut créer une base de données vide dans Postgresql : la structure des tables est crée par le framework Hibernate dans le container SpringBoot au lancement de l'API "biblioWebServiceRest" qui génère un premier jeu de données

- paramétrer le nom de votre base, son username et son password en modifiant les paramètres de ce nom précédés du préfixe spring.datasource dans le fichier src/main/resources/applications-properties

- l'API gère la durée des prêts à partir de 2 constantes (durée du prêt initial, durée de la prolongation) dont la valeur est modifiable dans le fichier application.properties de l'application. Elle gère les réservations à partir de 3 constantes (délai du prêt à échoir, durée de la notification et multiplicateur pour le nombre maximum de réservataires)

- ouvrir la commande en ligne dans la directory où vous avez copié l'API "biblioWebServiceRest"
- taper la commande "mvn spring-boot:run" pour lancer la compilation de l'applicationn


- pour lancer avec JUnit les tests unitaires développés avec SpringBootTest et Mockito taper la commande "mvn clean test". La dépendance Jacoco édite un fichier index.html accessible dans votre dépôt en suivant le path biblioWebServiceRest\target\site\jacoco qui permet la lecture du taux de couverture


# Installation et lancement de l'Application Web "biblioWebAppli".
L'Application Web biblioWebAppli est un client sécurisé à utiliser pour consommer l'API biblioWebServiceRest en tant qu'abonné de la bibliothèque..

Avant le lancement de l'application, il faut obtenir un nom d'utilisateur et un mot de passe validé dans l'API.

Ensuite, il faut :

- ouvrir la commande en ligne dans la directory où vous avez copié l'Appli "biblioWebAppli"

- taper la commande "mvn spring-boot:run" pour lancer la compilation de l'applicationn

- une fois l'application lancée, pour accéder à l'application, taper http://localhost:8089/login dans votre navigateur (si le port localhost:8089 n'est pas disponible, il peut être modifié dans le fichier application.properties)


# Installation et lancement du Batch "biblioBatch""

Le batch est un service sécurisé qui peut être lancé avec l'API pour identifier à intervalles réguliers l'ensemble des prêts de livres non retournés, l'ensemble des prêts à échoir dans un délai gérable dans les propriétés et l'ensemble des prêts notifiés pour lesquels les réservataires ont 48 h pour venir emprunter le livre qu'ils ont réservé et pour générer des mails de relance, d'avertissement ou de notifications adressés aux abonnés selon l'une des 3 situations précédentes..

La durée de l'intervalle est paramétrée dans le fichier application.properties dans la variable application.cron où elle peut être modifiée.

Dans ce fichier propriétés se trouvent d'autres paramètres modifiables comme le nom et le mot de passe pour accéder aux endpoints autorisés de l'API et les informations nécessaires à l'envoi des mails (la variable "application.mail" gère l'adresse mail de l'expéditeur et toutes les variables dont le préfixe est "spring.mail" gèrent informations du seveur smtp : par défaut le serveur utilisé est mailtrap.). Il est recommandé à tout nouvel utilisateur d'ouvrir une adresse mail expéditeur et de mettre à jour les variables dans le fichier application.properties.

Ensuite, il faut :

- ouvrir la commande en ligne dans la directory où vous avez copié l'Appli "biblioBatch""

- taper la commande "mvn spring-boot:run" pour lancer la compilation de l'application qui s'éxécutera automatiquement à intervalles réguliers jusqu'à sa mise en arrêt manuel par l'utilisateur. 













