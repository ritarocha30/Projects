<html>
    <body>
<?php
    $morada = $_REQUEST['morada'];
    $codigo = $_REQUEST['codigo'];
    $codigo_espaco = $_REQUEST['codigo_espaco'];
    $foto = $_REQUEST['foto'];
    try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;
        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $db->query("start transaction;");

        $sql = "insert into alugavel values ('$morada','$codigo', '$foto');";
        $sql2 = "insert into posto values ('$morada', '$codigo', '$codigo_espaco');";

        echo("<p>Posto com a morada '$morada', codigo '$codigo', codigo do espaco '$codigo_espaco' e foto '$foto' inserido.</p>");

        $db->query($sql);
        $db->query($sql2);

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
