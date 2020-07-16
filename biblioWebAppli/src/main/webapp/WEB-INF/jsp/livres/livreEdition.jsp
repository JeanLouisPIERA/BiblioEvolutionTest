<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>


<head>

<title>Livre Edition</title>
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
    <div class="panel-heading">Fiche de mise à jour d'un livre</div>
    
    <div class="panel-body">
    <br>
       <br>
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="livre">
       <form:hidden path="numLivre"/> 
      
       <fieldset class="form-row">
       <fieldset class="form-group">
	       <form:label path="titre" class="col-auto col-form-label">Titre :</form:label>
	       <form:input path="titre" type="text" class="form-control"
	        placeholder="e.g Le Pere Goriot" required="required" />
	       <form:errors path="titre" cssClass="text-warning" />
      	</fieldset>
      	
      	 <fieldset class="form-group">
	       <form:label path="auteur" class="col-auto col-form-label">Auteur :</form:label>
	       <form:input path="auteur" type="text" class="form-control"
	        placeholder="e.g Balzac" required="required" />
	       <form:errors path="auteur" cssClass="text-warning" />
      	</fieldset>
      	
      	<fieldset class="form-group">
	       <form:label path="nbExemplaires" class="col-auto col-form-label">Nb Exemplaires :</form:label>
	       <form:input path="nbExemplaires" type="text" class="form-control"
	        placeholder="e.g 2 " required="required" />
	       <form:errors path="nbExemplaires" cssClass="text-warning" />
      	</fieldset>
      	
      	<fieldset class="form-group">
	       <form:label path="categorie.numCategorie" class="col-auto col-form-label">Référence de la Catégorie :</form:label>
	       <form:input path="categorie.numCategorie" type="text" class="form-control"
	        placeholder="e.g 2 " required="required" />
	       <form:errors path="categorie.numCategorie" cssClass="text-warning" />
      	</fieldset>
      	
      	
      	
      	</fieldset>
       <br>
       <br>	      	
   	   <button type="submit" class="btn btn-success">Valider</button>
   	   
     </form:form>
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