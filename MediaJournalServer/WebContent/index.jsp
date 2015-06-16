<html>
    <head>
        <title>Login Medientagebuch</title>
    </head>
    <body>
        <form method="post" action="servlet">
        Name:<input type="text" name="name" /><br/>
        Password:<input type="text" name="password" /><br/>
                <input type="hidden" name="usecase"	value="login" />
        <input type="submit" value="login" />
        </form>
    </body>
</html>