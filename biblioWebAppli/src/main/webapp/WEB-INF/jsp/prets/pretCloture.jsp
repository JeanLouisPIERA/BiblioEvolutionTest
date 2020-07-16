<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Pret Cloture</title>
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
   <h3>Le pret a ete cloturé</h3>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Référence Pret : ${pret.numPret}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Identifiant Emprunteur</td>
            <td>${pret.user.getIdUser()}</td>
        </tr>
        <tr>
            <td>Nom Emprunteur</td>
            <td>${pret.user.getUsername()}</td>
        </tr>
        <tr>
            <td>Référence du Livre</td>
            <td>${pret.livre.getNumLivre()}</td>
        </tr>
        <tr>
            <td>Nom du Livre</td>
            <td>${pret.livre.getTitre()}</td>
        </tr>
        <tr>
            <td>Date du Pret</td>
            <td>${pret.getDatePret()}</td>
        </tr>
        <tr>
            <td>Date prévue de Retour </td>
            <td>${pret.getDateRetourPrevue()}</td>
        </tr>
        <tr>
            <td>Date effective de Retour et clôture</td>
            <td>${pret.getDateRetourEffectif()}</td>
        </tr>
 		
    </tbody>
</table>
  
 </div>
 </div>
 <div>
 <a type="button" class="btn btn-primary btn-md" href="/prets">Retour au menu Pret</a>
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
