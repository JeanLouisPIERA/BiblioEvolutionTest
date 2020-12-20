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
    <h3><center>Recherche des prets enregistrés</center></h3>
    </div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/prets" method="GET" modelAttribute="pret">
				 
				 <fieldset class="form-row">
							 
				 <fieldset class="form-group">
				 <label>Référence du Pret :</label>
				 <input type="text" name="numPret" value="${pretCriteria.numPret}"/>
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
				   <h3><center>Informations sur les prets de livre</center></h3>
				  </div>
  					<div class="panel-body">
					   <table class="table table-striped table-condensed table-bordered" style="text-align:center">
					   		 <thead>		 
					   			  	 
					    			 <tr>
									      <th style="text-align:center">Référence du Prêt</th>
									      <th style="text-align:center">Référence du Livre</th>
									      <th style="text-align:center">Titre</th>
									      <th style="text-align:center">Catégorie</th>
									      <th style="text-align:center">Date du Pret</th>
									      <th style="text-align:center">Date de Retour Prévue</th>
									      <th style="text-align:center">Date de Retour Effectif</th>
									      <th style="text-align:center">Statut du prêt</th>
									      
								     </tr>
								     </thead>
								     <tbody>
					   				 <c:forEach var="pret" items="${prets}">
					   				 <tr>
								          <td>${pret.numPret}</td>
							              <td>${pret.livre.getNumLivre()}</td>
							              <td>${pret.livre.getTitre()}</td>
							              <td>${pret.livre.categorie.getNomCategorie()}</td>
								          <td>${pret.datePret}</td>
								          <td>${pret.dateRetourPrevue}</td>
								          <td>${pret.dateRetourEffectif}</td>
								          <td>${pret.pretStatut.getText()}</td>
								          
								          <td>
								          	<a  type="button" class="btn btn-default" 
									        href="/prets/${pret.numPret}"> Fiche Info </a>
							        	  </td>
								         
								          <c:if test="${pret.pretStatut.getCode() != 'PROLONGE' 
								          && pret.pretStatut.getCode() != 'CLOTURE' 
								          && pret.pretStatut.getCode() != 'ECHU'}">
								          <td>
								          	<a type="button"  class="btn btn-warning" 
								        	href="/prets/prolongation/${pret.numPret}">Prolonger</a>
								          </td>
								          </c:if>
								          
								          <c:if test="${pret.pretStatut.getCode() == 'PROLONGE'
								          || pret.pretStatut.getCode() == 'CLOTURE' 
								          || pret.pretStatut.getCode() == 'ECHU'}">
								          <td>
								          	<a type="button"  class="btn btn-danger" 
								        	href="/prets/prolongation/${pret.numPret}">Prolonger DEMO</a>
								          </td>
								          </c:if>
								          
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