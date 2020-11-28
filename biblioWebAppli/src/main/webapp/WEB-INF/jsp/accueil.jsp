<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>

<head>

<title>Accueil</title>
<%@ include file="common/header1.jspf"%>
</head>

<body> 

<div id= "header1">

<%@ include file="common/navigation.jspf"%>


<div class="container">

    <div class="container-fluid d-flex center-block">
    <div class="row d-flex justify-content-center">
  	<div class="col-md-6 col-md-offset-3">
  	<div class="wrapper">

 <div class="panel panel-primary">
     <div class="panel-heading">Bienvenue dans votre Espace Personnel</div>
        <div class="panel-body">
           Bonjour ${name}!!
           <br></br> <a href="/livres">Cliquer ici</a> pour consulter tous les livres. 
           <br></br> <a href="/prets">Cliquer ici</a> pour consulter vos prets en cours. 
           <br></br> <a href="/reservations">Cliquer ici</a> pour consulter vos reservations en cours.
                      
        </div>
     </div>
 </div>
 </div>
 </div>
 </div>
 </div>
 
<div id="footer">
<%@ include file="common/footer1.jspf"%>
</div>
</div>

</body>

</html>