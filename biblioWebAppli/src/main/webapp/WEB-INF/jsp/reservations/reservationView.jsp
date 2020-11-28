<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>

<html>


<head>

<title>Reservation Vue</title>
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
   <h5>Fiche d'information de la réservation N° ${reservation.numReservation}</h5>
  </div>
  
  <div class="panel-body">
   <table class="table table-striped table-condensed table-bordered">
    <thead>
        <tr>
            <th colspan="2"> Numéro de la réservation : ${reservation.numReservation}</th>
        </tr>
    </thead>
    <tbody>
         <tr>
            <td>Livre reservé</td>
            <td>${reservation.livre.getTitre()}</td>
        </tr>
        <tr>
            <td>Auteur</td>
            <td>${reservation.livre.getAuteur()}</td>
        </tr>
        <tr>
            <td>Référence du livre</td>
            <td>${reservation.livre.getNumLivre()}</td>
        </tr>
        <tr>
            <td>Statut de la réservation</td>
            <td>${reservation.reservationStatut.getText()}</td>
        </tr>
          <tr>
            <td>Date de la réservation</td>
            <td>${reservation.dateReservation}</td>
        </tr>
         <tr>
            <td>Date de la notification</td>
            <td>${reservation.dateNotification}</td>
        </tr>       
        <tr>
            <td>Date limite pour emprunter le livre</td>
            <td>${reservation.dateDeadline}</td>
        </tr>
        <tr>
            <td>Date de suppression</td>
            <td>${reservation.dateSuppression}</td>
        </tr>
        <tr>
            <td>Rang de réservation</td>
            <td>${reservation.rangReservation}</td>
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
