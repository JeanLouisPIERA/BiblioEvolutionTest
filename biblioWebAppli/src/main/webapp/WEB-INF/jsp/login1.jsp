<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>Login - Login Page </title>
<style type="text/css">
    body div {
        text-align: center;
    }
    label, input[type=text], input[type=password] {
        display: inline-block;
        width: 150px;
        margin: 5px;
    }
    input[type=submit] {
        width: 60px;
        margin: 10px;
        padding: 10px;
        text-align: center;
    }
</style>
</head>
<body>
<div>
    <div>
        <h2>Login Page</h2>
    </div>
    <div th:if="${param.error}">
        <h3>Invalid username and password.</h3>
    </div>
    <div th:if="${param.logout}">
        <h3>You have been logged out.</h3>
    </div>
    <div>
    <form th:action="@{/login}" method="post">
        <div><label>Utilisateur: </label> <input type="text" name="u" /></div>
        <div><label>Mot de Passe: </label><input type="password" name="p" /></div>
        <div><input type="submit" value="Valider" /></div>
    </form>
    </div>
</div>   
</body>
</html>