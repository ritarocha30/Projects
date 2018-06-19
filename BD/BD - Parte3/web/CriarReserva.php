<html>
    <body>
        <form action="updateCriarReserva.php" method="post">
            <p><input type="hidden" name="morada" value="<?=$_REQUEST['morada']?>"/></p>
            <p><input type="hidden" name="codigo" value="<?=$_REQUEST['codigo']?>"/></p>
            <p><input type="hidden" name="data_inicio" value="<?=$_REQUEST['data_inicio']?>"/></p>
            <p>NIF do User: <input type="text" name="nif"/></p>
            <p>Numero da Reserva: <input type="text" name="nreserva"/></p>
            <p><input type="submit" value="Submit"/></p>
        </form>
    </body>
</html>