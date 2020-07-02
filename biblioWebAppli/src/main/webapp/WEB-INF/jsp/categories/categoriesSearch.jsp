<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>


<head>

<title>Page Categories</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>

<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>


<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-8 col-md-offset-3 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">Recherche des catégories</div>
    
	    <div class="panel-body">
	
				 <form:form class="form-inline d-flex bd-highlight" action="/categories" method="get" modelAttribute="categorieCriteria">
				 
				 <fieldset class="form-row">
				 <fieldset class="form-group">
				 <label>Nom de la catégorie :</label>
				 <input type="text" name="nomCategorie" value="${nomCategorie}"/>
				 </fieldset>
				 </fieldset>
		
		
				<fieldset class="form-row">
				 <fieldset class="form-group">
				 <label>Numéro de la page à afficher:</label>
				 <input type="text" name="page" value="${page}"/>
				 </fieldset>
				 </fieldset>
	
				 <fieldset class="form-row">
				 <fieldset class="form-group">
				 <label>Nombre de résultats par page:</label>
				 <input type="text" name="size" value="${size}"/>
				 </fieldset>
				 
				 <button class="btn-sm btn-primary" href="/categories/displayCategories">Valider</button>
				 </fieldset> 
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





