<?php

include_once './bd.php';
$con = new bd();

$insert = "insert into usuarios (documento, nombre_completo, telefono, pc_serial, fecha_crecion) "
        . "values('" . $_POST['documento'] . "', '" . $_POST['nombre'] . "', '" . $_POST['telefono'] . "', '" . $_POST['token'] . "', NOW())";
$row = $con->exec($insert);

$insertHuella = "insert into huellas (documento, nombre_dedo, huella, imgHuella) "
        . "values ('" . $_POST['documento'] . "', 'Indice D',"
        . "(select huella from huellas_temp where pc_serial = '" . $_POST['token'] . "'), "
        . "(select imgHuella from huellas_temp where pc_serial = '" . $_POST['token'] . "'))";
$row = $con->exec($insertHuella);

$delete = "delete from huellas_temp where pc_serial = '" . $_POST['token'] . "'";

$row = $con->exec($delete);

$con->desconectar();
echo json_encode("{\"filas\":$row}");
