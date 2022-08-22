<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
 <spring:url value="/resource/css/test.css" var="testCss"/>
        
        <link rel="stylesheet" href="${testCss}">
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
            <!--<p>Please check your data again.</p>-->
          </div>
        </div>
        <form:form action="login" modelAttribute="login" method="post">
          <form:input type="text" placeholder="name" path="loginName"/>
          <form:input type="password" placeholder="Password" path="loginPassword"/>
          <button>login</button>
          <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form:form>
      </div>
    </div>
</body>

</html>