<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>


<head>

<title>Categorie Creation</title>
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
</head>


<body> 

<div id= "header1">
<%@ include file="/WEB-INF/jsp/common/header1.jspf"%>
<%@ include file="/WEB-INF/jsp/common/navigation.jspf"%>

<div class="container-fluid">
 <div class="row justify-content-center mb-3">
  <div class="col-md-6 col-md-offset-3 ">
  
   <div class="panel panel-primary">
    <div class="panel-heading">Fiche de création d'une categorie</div>
    
    <div class="panel-body">
    
     <form:form class="form-inline d-flex bd-highlight" method="POST" modelAttribute="categorie">
        
        
      
       
       <fieldset class="form-group">
	       <form:label path="nomCategorie" class="col-auto col-form-label">Nom de la categorie</form:label>
	       <form:input path="nomCategorie" type="text" class="form-control"
	        placeholder="e.g Roman" required="required" />
	       <form:errors path="nomCategorie" cssClass="text-warning" />
      	</fieldset>
      	
      	
      	      	
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