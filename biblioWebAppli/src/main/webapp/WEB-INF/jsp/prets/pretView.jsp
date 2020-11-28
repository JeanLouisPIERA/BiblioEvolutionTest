<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>


<head>

<title>Pret Vue</title>
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
   <h5>Fiche d'information du pret N° ${pret.numPret}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Numéro du pêt: ${pret.numPret}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Livre emprunté</td>
            <td>${pret.livre.getTitre()}</td>
        </tr>
        <tr>
            <td>Auteur</td>
            <td>${pret.livre.getAuteur()}</td>
        </tr>
        <tr>
            <td>Référence du livre</td>
            <td>${pret.livre.getNumLivre()}</td>
        </tr>
        <tr>
            <td>Statut du prêt</td>
            <td>${pret.pretStatut.getText()}</td>
        </tr>
          <tr>
            <td>Date du prêt</td>
            <td>${pret.datePret}</td>
        </tr>
         <tr>
            <td>Date prévue de retour</td>
            <td>${pret.dateRetourPrevue}</td>
        </tr>       
        <tr>
            <td>Date de retour effectif</td>
            <td>${pret.dateRetourEffectif}</td>
        </tr>
       
    </tbody>
</table>
  
</div>
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
