<html>
    <body>
<?php
    $codigo = $_REQUEST['codigo'];
    $numero = $_REQUEST['numero'];
    date_default_timezone_set('Portugal/Lisbon');
    $date = date('Y-m-d G:i:s', time());
    try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;

        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $db->query("start transaction;");

        $sql = "insert into paga values('$numero','$date','$codigo');";

        echo("<p>Reserva paga! </p>");

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

