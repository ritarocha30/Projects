<html>
    <body>
    <h3> Ofertas </h3>
<?php
  try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;

        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "select morada, codigo, data_inicio, data_fim, tarifa from oferta;";

        $result = $db->query($sql);

        echo("<table border=\"3\">\n");
        foreach($result as $row)
        {
            echo("<tr>\n");
            echo("<td>{$row['morada']}</td>\n");
            echo("<td>{$row['codigo']}</td>\n");
            echo("<td>{$row['data_inicio']}</td>\n");
            echo("<td><a href=\"updateRemoverOferta.php?morada={$row['morada']}&codigo={$row['codigo']}&data_inicio={$row['data_inicio']}\"> Remover Oferta </a></td>\n");
            echo("<td><a href=\"CriarReserva.php?morada={$row['morada']}&codigo={$row['codigo']}&data_inicio={$row['data_inicio']}\"> Reservar </a></td>\n");

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