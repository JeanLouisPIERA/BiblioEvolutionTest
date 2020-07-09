<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Livre Confirmation</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">

<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

<div class="container-fluid">

<div class="row d-flex justify-content-center">

 <div class="container col-md-4 col-md-offset-4">
 <div class="panel panel-primary ">
 
  <div class="panel-heading">
   <h3>L'enregistrement du livre a ete validé</h3>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Titre : ${livre.titre}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Auteur</td>
            <td>${livre.auteur}</td>
        </tr>
        <tr>
            <td>Nb Exemplaires</td>
            <td>${livre.nbExemplaires}</td>
        </tr>
        <tr>
            <td>Categorie</td>
            <td>${livre.categorie.getNumCategorie()}</td>
        </tr>
        <tr>
            <td>Identifiant</td>
            <td>${livre.numLivre}</td>
        </tr>
        
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/livres">Retour au menu Livre</a>
 </div>
 </div>
 
</div>
</div>

<div id="footer">
<%@ include file="/WEB-INF/jsp/common/footer1.jspf"%>
</div>

</div>

</body>


</html>
