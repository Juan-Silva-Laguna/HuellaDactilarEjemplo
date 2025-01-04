<?php

include_once './bd.php';
set_time_limit(0);
date_default_timezone_set("America/Bogota");
$token = $_GET['token'];
$fecha_actual = 0;
$fecha_bd = 0;
if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $fecha_actual = (isset($_POST['timestamp']) && $_POST['timestamp'] != 'null') ? $_POST['timestamp'] : 0;
} else {
    if (isset($_GET['timestamp']) && $_GET['timestamp'] != 'null') {
        $fecha_actual = $_GET['timestamp'];
    }
}

$con = new bd();

while ($fecha_bd <= $fecha_actual) {
    $query = "Select fecha_creacion from huellas_temp where pc_serial = '" . $token . "' ORDER BY id DESC LIMIT 1";
    $rs = $con->findAll($query);
    usleep(100000);
    clearstatcache();
    if (count($rs) > 0) {
        $fecha_bd = strtotime($rs[0]['fecha_creacion']);
    }
}

$query = "Select fecha_creacion, opc from huellas_temp where pc_serial = '" . $token . "' ORDER BY id DESC LIMIT 1";
$datos_query = $con->findAll($query);

$array = array();
for ($i = 0; $i < count($datos_query); $i++) {
    $array['fecha_creacion'] = strtotime($datos_query[$i]['fecha_creacion']);
    $array['opc'] = $datos_query[$i]['opc'];
}
$con->desconectar();
$response = json_encode($array);
echo $response;


