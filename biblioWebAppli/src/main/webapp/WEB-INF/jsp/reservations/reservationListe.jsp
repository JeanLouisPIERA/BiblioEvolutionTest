<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Reservations</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-10 col-md-offset-1 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">
    <h3>Recherche des réservations enregistrées</h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/reservations" method="GET" modelAttribute="reservation">
				 
				 <fieldset class="form-row">
							 
				 <fieldset class="form-group">
				 <label>Référence du Réservations :</label>
				 <input type="text" name="numReservation" value="${reservationCriteria.numReservation}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Identifiant du Livre :</label>
				 <input type="text" name="numLivre" value="${reservationCriteria.numLivre}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Titre du Livre :</label>
				 <input type="text" name="titre" value="${reservationCriteria.titre}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Auteur du Livre :</label>
				 <input type="text" name="auteur" value="${reservationCriteria.auteur}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Nom de la catégorie :</label>
				 <input type="text" name="nomCategorieLivre" value="${reservationCriteria.nomCategorieLivre}"/>
				 </fieldset>
				
				 
				 
				 <button class="btn-sm btn-primary">Valider</button>
				 </fieldset> 
				 </form:form>
	 			</div>
	 		</div> 
		 </div>
	 </div>
 </div>
 
 
<div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class="container col-md-10 col-md-offset-1">
			<div class="wrapper">
				 <div class="panel panel-primary">
				  <div class="panel-heading">
				   <h3>Informations sur les réservations de livre</h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered">
					   		 <thead>		 
					   			  	 
					    			 <tr>
									      <th>Référence Réservation</th>
									      <th>Date de Réservation</th>
									      <th>Référence du Livre</th>
									      <th>Titre</th>
									      <th>Catégorie</th>
									      <th>Disponibilité du Livre</th>
									      <th>Date de Retour Prévue</th>
									      <th>Rang dans liste d'attente</th>
									      
									      
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="reservation" items="${reservations}">
					   				 <tr>
								          <td>${reservation.numReservation}</td>
								          <td>${reservation.dateReservation}</td>
							              <td>${reservation.livre.getNumLivre()}</td>
							              <td>${reservation.livre.getTitre()}</td>
							              <td>${reservation.livre.categorie.getNomCategorie()}</td>
							              <td>${reservation.livre.getNbExemplairesDisponibles()}</td>
								          <td>${reservation.livre.getDateRetourPrevuePlusProche()}</td>
								          <td>${reservation.rangReservation}</td>
								          <td>
								          	<a type="button"  class="btn btn-warning" 
								        	href="/reservations/suppression/${reservation.numReservation}">Supprimer</a>
								          </td>
					    				 </tr>
				   					</c:forEach>
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${reservations.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="reservations?page=${page}&size=${size}" class="page-target">${page+1}</a>
					                    </li>
					                </c:forEach>
				           		 </ul>
					 		   </c:if>
					   		</div>
			    		</div>
		    		</div>	
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