	<html>
    <body>
<?php
    $morada = $_REQUEST['morada'];
    try
    {
        $host = "db.ist.utl.pt";
        $user ="ist179779";
        $password = "yolovidaloka";
        $dbname = $user;
        $db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
        $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
        $db->query("start transaction;");

        $sql = "select morada, codigo_espaco ,tarifa, datediff(data_inicio,data_fim)
                from(
                    (select distinct p.morada, p.codigo_espaco
                     from posto p, aluga a, paga pa
                     where p.codigo_espaco = a.codigo and p.morada = a.morada and pa.numero=a.numero)
                     UNION
                    (select distinct e.morada, e.codigo
                     from espaco e, aluga a, paga pa 
                     where e.codigo = a.codigo and e.morada = a.morada and pa.numero = a.numero))  
                where(data_fim like '2016%') and (data_inicio like '2016%');";

        echo("<p>Edificio com a morada '$morada' contem o seguinte total produzido: '$total'.</p>");

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