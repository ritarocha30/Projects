<html>
    <body>
    <h3>Reservas disponiveis</h3>
<?php
  try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;

        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "select numero from reserva;";

        $result = $db->query($sql);

        echo("<table border=\"3\">\n");
        foreach($result as $row)
        {
            echo("<tr>\n");
            echo("<td>{$row['numero']}</td>\n");
            echo("<td><a href=\"PagarReserva.php?numero={$row['numero']}\"> Pagar reserva </a></td>\n");
            echo("</tr>\n");
        }
        echo("</table>\n");
        echo("<p><a href='http://web.ist.utl.pt/ist179779/mainPage.php'> Paginal inicial </a></p>");

       $db = null;
    }
    catch (PDOException $e)
    {
        echo("<p>ERROR: {$e->getMessage()}</p>");
    }
?>
    </body>
</html>