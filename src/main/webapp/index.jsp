<%@include file="head.jsp"%>
<html>
<body>
<div class="container">
<h2>User Display Exercise - Week 1</h2>
<a href = "searchUser">Go to the User Search</a>
<form action="/searchUser" method="get">
    <h5>Enter your search terms and click search: </h5>
    <div class="form-group">
        <label>First Name:</label>
        <input type="text" name="firstName">
    </div>
    <div class="form-group">
        <label>Last Name:</label>
        <input type="text" name="lastName">
    </div>
    <div class="form-group">
        <label>Age:</label>
        <input type="text" name="personAge">
    </div>
    <div class="form-group">
        <label>User Id:</label>
        <input type="text" name="userId">
    </div>
    <input type="submit" class="button" value="Search">
</form>
</div>
</body>
</html>