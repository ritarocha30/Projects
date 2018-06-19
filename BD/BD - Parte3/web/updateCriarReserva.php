<html>
    <body>
<?php
    $morada = $_REQUEST['morada'];
    $codigo = $_REQUEST['codigo'];
    $data_inicio = $_REQUEST['data_inicio'];
    $nif = $_REQUEST['nif'];
    $nreserva = $_REQUEST['nreserva'];
    try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;

        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $db->query("start transaction;");

        $sql = "insert into reserva values ('$nreserva');";
        $sql2 = "insert into aluga values ('$morada','$codigo','$data_inicio', '$nif', '$nreserva');";
        $sql3 = "insert into estado values ('$nreserva', '$data_inicio', 'Aceite');";

        echo("<p>Reserva criada com sucesso! </p>");

        $db->query($sql);
        $db->query($sql2);
        $db->query($sql3);

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

