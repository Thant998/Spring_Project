<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <spring:url value="/resource/css/test.css" var="testCss"/>
        
        <link rel="stylesheet" href="${testCss}">
        
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Course Registration</title>
</head>

<body>
    <div id="testheader">
        <div class="container">
            <div class=row>        
                <div class="col-md-5 ">
            <a href="MNU001.jsp"><h3>Student Registration</h3></a>
        </div>  
        <div class="col-md-6">
            <p>User:${userInfo.userName}</p>
            <p>Current Date :
            	<%@page import="java.util.Date" %>
            		<%=new Date() %></p>
        </div>  
        <div class="col-md-1" >
            <input type="button" class="btn-basic" id="lgnout-button" value="Log Out" onclick="location.href='/acetest/logout'">
        </div>        
    </div>
</div>

</div>
    <!-- <div id="testsidebar">Hello World </div> -->
    <div class="container">
    <div class="sidenav">
        
        <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>
        
            <div class="dropdown-container">
          <a href="/acetest/setupRegisterCourse">Course Registration </a>
          <a href="/acetest/setupRegisterStudent">Student Registration </a>
          <a href="/acetest/setupSearchStudent">Student Search </a>
        </div>
        <a href="/acetest/showuser">Users Management</a>
      </div>
      <div class="main_contents">
    <div id="sub_content">
        <form:form action="/acetest/registerStudent" method="post" modelAttribute="stuBean">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Registration</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studentID" class="col-md-2 col-form-label">Student ID</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" name="id" path="id"/>
                    <form:errors path="id" style="color: red;"></form:errors>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name" path="name"/>
                    <form:errors path="name" style="color: red;"></form:errors>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                    <form:input type="date" class="form-control" name="dob" path="dob"/>
                    <form:errors path="dob" style="color: red;"></form:errors>
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                <div class="col-md-4">
                    <div class="form-check-inline">
                        <form:radiobutton class="form-check-input" id="gridRadios1"
									path="gender" value="Male" />
								<label class="form-check-label" for="gridRadios1"> Male
								</label>
							</div>
							<div class="form-check-inline">
								<form:radiobutton class="form-check-input" id="gridRadios2"
									path="gender" value="Female" />
								<label class="form-check-label" for="gridRadios2">
									Female </label>
									<form:errors path="gender" style="color: red;"></form:errors>
                    </div>
    
                </div>
            </fieldset>
    
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label">Phone</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="phone" value="+95 " path="phone"/>
                    <form:errors path="phone" style="color: red;"></form:errors>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label">Education</label>
                <div class="col-md-4">
                    <form:select class="form-select" aria-label="Education" id="education" path="education">
                    	<form:option value="Bachelor of Information Technology">Bachelor
									of Information Technology</form:option>
								<form:option value="Diploma in IT">Diploma in IT</form:option>
								<form:option value="Bachelor of Computer Science">Bachelor
									of Computer Science</form:option>
									<form:errors path="education" style="color: red;"></form:errors>
    				</form:select>
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Attend</legend>
    
                <div class="col-md-4">
                    <c:forEach items="${stuBean.attend}" var="data">
					<div class="form-check-inline col-md-2">
						<form:checkbox class="form-check-input" path="stuCourse" value="${data.id}"/>
						 <label class="form-check-label" for="gridRadios1">
										${data.name} </label>
								</div>
							</c:forEach>
                </div>
    			
               </fieldset>
            
    
            <div class="row mb-4">
                <div class="col-md-4"></div>
    
                <div class="col-md-4">
                    <button type="button" class="btn btn-danger ">
    
                        Reset
                    </button>
                  <input type="submit"  value="Add" class="btn btn-secondary col-md-2"
                     data-bs-toggle="modal" data-bs-target="#exampleModal">
                   
            </div>
            </div>
          </form:form>
    </div>
</div>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2022</span>
        </div>
        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
</body>

</html>
    