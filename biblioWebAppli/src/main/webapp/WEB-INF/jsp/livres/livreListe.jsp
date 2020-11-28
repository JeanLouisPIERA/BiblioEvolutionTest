<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Livres</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="container col-md-8 col-md-offset-1">

<div class="wrapper">


 </div>
 </div>

<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-8 col-md-offset-2 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">
    <h3>Recherche des livres enregistrés</h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/livres" method="GET" modelAttribute="livre">
				 
				 <fieldset class="form-row">
				 
				 <fieldset class="form-group">
				 <label>Référence du Livre :</label>
				 <input type="text" name="numLivre" value="${livreCriteria.numLivre}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Titre du Livre :</label>
				 <input type="text" name="titre" value="${livreCriteria.titre}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Auteur du Livre :</label>
				 <input type="text" name="auteur" value="${livreCriteria.auteur}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Nom de la catégorie :</label>
				 <input type="text" name="nomCategorie" value="${livreCriteria.nomCategorie}"/>
				 </fieldset>
				
				
				<fieldset class="form-group">
				 <label>Nombre d'exemplaires disponibles :</label>
				 <input type="text" name="nbExemplairesDisponibles" value="${livreCriteria.nbExemplairesDisponibles}"/>
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
		<div class="container col-md-8 col-md-offset-2">
			<div class="wrapper">
				 <div class="panel panel-primary">
				  <div class="panel-heading">
				   <h3>Informations sur les livres</h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered">
					   		 <thead>		  	  	 
					    			 <tr>
									      <th>Référence</th>
									      <th>Titre</th>
									      <th>Auteur</th>
									      <th>Nombre d'exemplaires</th>
									      <th>Exemplaires disponibles</th>
									      <th>Catégorie</th>
									      <th>Date de retour la plus proche</th>
									      <th>Réservations en cours : nombre d'utilisateurs inscrits</th>
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="livre" items="${livres}">
					   				 <tr>
								          <td>${livre.numLivre}</td>
								          <td>${livre.titre}</td>
								          <td>${livre.auteur}</td>
								          <td>${livre.nbExemplaires}</td>
								          <td>${livre.nbExemplairesDisponibles}</td>
								          <td>${livre.categorie.getNomCategorie()}</td>
								          <td>
								          <c:if test="${livre.nbExemplairesDisponibles==0}"> 
								          ${livre.dateRetourPrevuePlusProche}
								          </c:if>
								          </td>
								          <td>
								          <c:if test="${livre.nbExemplairesDisponibles==0}"> 
								          ${livre.nbReservataires}
								          </c:if>
								          </td>
								          
								          <c:if test="${livre.nbExemplairesDisponibles==0}">
								          <td>
								          	<a type="button"  class="btn btn-success" 
								        	href="/reservations/creation/${livre.numLivre}">Réserver</a>
								          </td>
								          </c:if>
								          
								          
								          <c:if test="${livre.nbExemplairesDisponibles!=0}">
								          <td>
								          	<a type="button"  class="btn btn-danger" 
								        	href="/reservations/creation/${livre.numLivre}">Réserver DEMO</a>
								           </td>	
								          </c:if>
								         
					        	      	  
					    				 </tr>
				   					</c:forEach>
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${livres.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="livres?page=${page}&size=${size}" class="page-target">${page+1}</a>
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