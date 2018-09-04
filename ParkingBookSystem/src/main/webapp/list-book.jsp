<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- 
    Document   : list-book.jsp
    Created on : 2018-07-01, 15:30:41
    Author     : lukas
--%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet" href="style.css" type="text/css" />
</head>
<body>
	<div class="container">
		<div id="header">
			<h3>
				<c:out value="${USER_RESPONSE}" />
			</h3>
			<h2>
				<b>List of Reservations</b>
			</h2>
		</div>
		<table>
			<tr>
				<th>Number</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Name Surname</th>
			</tr>

			<c:forEach var="tempBook" items="${BOOKED_LIST}">

				<tr>
					<td>${tempBook.number}</td>
					<td>${tempBook.start}</td>
					<td>${tempBook.end}</td>
					<td>${tempBook.userName}</td>
				</tr>

			</c:forEach>
		</table>
		<br> <br> <br> <a href="index.html"><button
				class="btn btn-warning">Go Back</button></a>
	</div>
</body>
</html>