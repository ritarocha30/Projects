<html>
    <body>
<?php
    try
    {
    	$host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;
        
        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

      echo("<h1>InstantOffice</h1>");
		  echo("<p><a href=\"ListarEdificios.php\"> Edificios </a></p>");    
   		echo("<p> &nbsp  &nbsp  &nbsp <a href=\"InserirEdificio.php\"> Inserir Edificio </a></p>");
   		echo("<p><a href=\"ListarEspacos.php\"> Espacos </a></p>");
   		echo("<p> &nbsp  &nbsp  &nbsp <a href=\"InserirEspaco.php\"> Inserir Espaco </a></p>");
   		echo("<p><a href=\"ListarPostos.php\"> Postos </a></p>");
   		echo("<p> &nbsp  &nbsp  &nbsp <a href=\"InserirPosto.php\"> Inserir Posto </a></p>");
   		echo("<p><a href=\"ListarOfertas.php\"> Ofertas </a></p>");
   		echo("<p> &nbsp  &nbsp  &nbsp <a href=\"CriarOferta.php\"> Criar Oferta </a></p>");
    	echo("<p><a href=\"ListarReservas.php\"> Reservas </a></p>");

        $db = null;
    }
    catch (PDOException $e)
    {
        echo("<p>ERROR: {$e->getMessage()}</p>");
    }
?>
    </body>
</html>