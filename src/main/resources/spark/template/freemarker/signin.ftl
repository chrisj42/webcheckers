<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title>Web Checkers | Sign In</title>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

	<h1>Web Checkers | Sign In</h1>

	<div class="body">
		<!-- Provide a message to the user, if supplied. -->
		<#include "message.ftl">
		
		<form action="/signin" method="post">
			<label for="name">Enter your username:</label>
			<input id="name" name="userName" autofocus/>
			<br/><br/>
			<button type="submit">Login</button>
		</form>

	</div>

</div>
</body>

</html>
