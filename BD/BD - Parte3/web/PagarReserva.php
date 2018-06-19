<html>
    <body>
        <form action="updatePagarReserva.php" method="post">
            <p><input type="hidden" name="numero" value="<?=$_REQUEST['numero']?>"/></p>
            <p>Método de pagamento: <input type="text" name="codigo"/></p>
            <p><input type="submit" value="Submit"/></p>
        </form>
    </body>
</html>
