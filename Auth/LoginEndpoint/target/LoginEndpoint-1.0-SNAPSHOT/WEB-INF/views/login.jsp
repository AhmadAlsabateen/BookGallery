<html>
<head>
<title>login</title>
</head>
<body>
    <p><font color="red">${errorMessage}</font></p>
    <form action="./login" method="POST" align="center">
        Name : <input name="username" type="text" /> Password : <input name="password" type="password" /> <input type="submit" />
    </form>

    <p><font color="red">${signUpErrorMessage}</font></p>
        <form action="./signup" method="POST" align="center">
            Name : <input name="username" type="text" /> Password : <input name="password" type="password" />
             Password2 : <input name="password2" type="password" /><input type="submit" />
        </form>

</body>
</html>