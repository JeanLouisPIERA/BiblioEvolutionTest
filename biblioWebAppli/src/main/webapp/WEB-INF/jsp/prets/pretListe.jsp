<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Prets</title>
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
    <h3>Recherche des prets enregistrés</h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/prets" method="GET" modelAttribute="pret">
				 
				 <fieldset class="form-row">
							 
				 <fieldset class="form-group">
				 <label>Référence du Pret :</label>
				 <input type="text" name="numPret" value="${pretCriteria.numPret}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Nom de l'emprunteur :</label>
				 <input type="text" name="username" value="${pretCriteria.username}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Identifiant de l'emprunteur :</label>
				 <input type="text" name="userId" value="${pretCriteria.userId}"/>
				 </fieldset>
				 
				
				 
				 <fieldset class="form-group">
				 <label>Identifiant du Livre :</label>
				 <input type="text" name="numLivre" value="${pretCriteria.numLivre}"/>
				 </fieldset>
				 
				 <fieldset class="form-group">
				 <label>Titre du Livre :</label>
				 <input type="text" name="titre" value="${pretCriteria.titre}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Auteur du Livre :</label>
				 <input type="text" name="auteur" value="${pretCriteria.auteur}"/>
				 </fieldset>
				 
				 
				 <fieldset class="form-group">
				 <label>Nom de la catégorie :</label>
				 <input type="text" name="nomCategorieLivre" value="${pretCriteria.nomCategorieLivre}"/>
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
				   <h3>Informations sur les prets de livre</h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered">
					   		 <thead>		 
					   			  	 
					    			 <tr>
									      <th>Référence</th>
									      <th>Emprunteur</th>
									      <th>Livre</th>
									      <th>Date du Pret</th>
									      <th>Date de Retour Prévue</th>
									      <th>Date de Retour Effectif</th>
									      <th>Disponibilité</th>
									      
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="pret" items="${prets}">
					   				 <tr>
								          <td>${pret.numPret}</td>
							              <td>${pret.user.getUsername()}</td>
							              <td>${pret.livre.getTitre()}</td>
								          <td>${pret.datePret}</td>
								          <td>${pret.dateRetourPrevue}</td>
								          <td>${pret.dateRetourEffectif}</td>
								          <td>${pret.pretStatut.getText()}</td>
								          <td>
								          <c:if test="${pret.pretStatut.getCode() != 'PROLONGE' 
								          && pret.pretStatut.getCode() != 'CLOTURE' 
								          && pret.pretStatut.getCode() != 'ECHU'}">
								          	<a type="button"  class="btn btn-warning" 
								        	href="/prets/prolongation/${pret.numPret}">Prolonger</a>
								          </c:if>
								          </td>
								          <td>
								          <c:if test="${pret.pretStatut.getCode() != 'CLOTURE'}">
								          	<a type="button"  class="btn btn-danger" 
								        	href="/prets/cloture/${pret.numPret}">Cloturer</a>
								          </c:if>
					        	      	  </td>
					    				 </tr>
				   					</c:forEach>
								</tbody>
							</table>			
			   			<div class="container">
			    		<!-- div class="row-lg-2" -->
			    			<c:if test="${prets.size() > 0 }">
					            <!-- ul class="pagination-sm"-->
					            <ul class="nav nav-pills">
					                <c:forEach begin="0" end="${totalPages-1}" var="page">
					                    <li class="page-item">
					                        <a class="btn btn-info" href="prets?page=${page}&size=${size}" class="page-target">${page+1}</a>
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