<html>
    <body>
<?php
    $morada = $_REQUEST['morada'];
    $codigo = $_REQUEST['codigo'];
    $data_inicio = $_REQUEST['data_inicio'];
    try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;
        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $db->query("start transaction;");

        $sql = "delete from oferta where morada = '$morada' and codigo = '$codigo' and data_inicio = '$data_inicio';";

        echo("<p>Oferta removida!</p>");

        $db->query($sql);

        $db->query("commit;");

        echo("<p><a href='http://web.ist.utl.pt/ist179779/mainPage.php'> Paginal inicial </a></p>");

        $db = null;
    }
    catch (PDOException $e)
    {
        $db->query("rollback;");
        echo("<p>ERROR: {$e->getMessage()}</p>");
    }
?>
    </body>
</html>
