<html>
    <body>
    <h3> Edificios </h3>
<?php
  try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;

        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = "select morada from edificio;";

        $result = $db->query($sql);

        echo("<table border=\"3\">\n");
        echo("<tr><td>morada</td></tr>\n");
        foreach($result as $row)
        {
            echo("<tr>\n");
            echo("<td>{$row['morada']}</td>\n");
            echo("<td><a href=\"updateRemoverEdificio.php?morada={$row['morada']}\"> Remover Edificio </a></td>\n");
            echo("<td><a href=\"updateTotalRealizado.php?morada={$row['morada']}\"> Total Realizado </a></td>\n");
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