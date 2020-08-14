<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Login Page</title>
		
		<link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"
        rel="stylesheet">
		
  </head>

  <body>
  
  	<div id= "header">
	<%@ include file="common/header.jspf"%>
      
    <div class="container">

    <div class="container-fluid d-flex center-block">
    <div class="row d-flex justify-content-center">
  	<div class="col-md-8 col-md-offset-4">
  	<div class="wrapper">
    
      <!-- form method="POST" action="{contextPath}/login" class="form-signin" modelAttribute="user"-->
      <form:form class="form-inline d-flex bd-highlight" method="POST" action="/login">
        <h2 class="form-heading">Identifiez-vous</h2>

        
         <div class="form-group">
            
            
            <input name="username" type="text" class="form-control" placeholder="Votre nom d'utilisateur"
                   autofocus="true"/>
            
                   <br>
                  
            <input name="password" type="password" class="form-control" placeholder="Votre mot de passe"/>
            
            <br>
            
			
            <button class="btn btn-sm btn-primary btn-block" type="submit">Valider</button>
            
        </div>
        
        <c:if test="${error!=null}">
            ${error}
            </c:if>
            
            <br>
        
      </form:form>
      </div>
    </div>
    
 </div>
 </div>
 <div id="footer">
<%@ include file="common/footer1.jspf"%>
</div>
 </div>  
 
 <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
        <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </div>
  
  </body>
  
</html>