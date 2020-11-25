<%@ page isErrorPage="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>


<head>

<title>Erreur</title>
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
   <h3>Le dernier traitement n'a pas été realisé</h3>
  </div>
 

  
  
  <div class="panel-body">
   

	
<table width="100%" border="1">
<tr valign="top">
<td width="40%"><b>Libellé de l'erreur:</b></td>
<td>${error}</td>
</tr>

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







