<?php

//Api Rest
header("Acces-Control-Allow-Origin: *");
header("Content-Type: application/json");

include_once './bd.php';
$con = new bd();

$method = $_SERVER['REQUEST_METHOD'];


// Metodo para peticiones tipo GET
if ($method == "GET") {
//    eliminar el token
    $token = $_GET['token'];
    $sql = "select u.documento, u.nombre_completo, h.nombre_dedo, h.huella, h.imgHuella, u.pc_serial "
            . "from usuarios u inner join huellas h on u.documento  = h. documento "
            . "where u.pc_serial = '" . base64_encode($token) . "'";

    $rs = $con->findAll($sql);
    echo json_encode($rs);
}

// Metodo para peticiones tipo POST
if ($method == "POST") {
    $jsonString = file_get_contents("php://input");
    $jsonOBJ = json_decode($jsonString, true);
    $query = "update huellas_temp set huella = '" . $jsonOBJ['huella'] . "', imgHuella = '" . $jsonOBJ['imageHuella'] . "',"
            . "update_time = NOW(), statusPlantilla = '" . $jsonOBJ['statusPlantilla'] . "',"
            . "texto = '" . $jsonOBJ['texto'] . "' "
            . "where pc_serial = '" . base64_encode($jsonOBJ['serial']) . "'";


//    echo $query;
    $row = $con->exec($query);
    $con->desconectar();
    echo json_encode("Filas Agregadas: " . $row);
}


// Metodo para peticiones tipo PUT
if ($method == "PUT") {
    $jsonString = stripslashes(file_get_contents("php://input"));
    $jsonOBJ = json_decode($jsonString);

    if ($jsonOBJ->option == "verificar") {
        $query = "update huellas_temp set imgHuella = '" . $jsonOBJ->imageHuella . "',"
                . "update_time = NOW(),"
                . "statusPlantilla = '" . $jsonOBJ->statusPlantilla . "',"
                . "texto = '" . $jsonOBJ->texto . "',"
                . "documento =  '" . $jsonOBJ->documento . "',"
                . "nombre = '" . $jsonOBJ->nombre . "',"
                . "dedo =  '" . $jsonOBJ->dedo . "' "
                . "where pc_serial = '" . base64_encode($jsonOBJ->serial) . "'";
    } else {
        $query = "update huellas_temp set imgHuella = '" . $jsonOBJ->imageHuella . "',"
                . "update_time = NOW(), statusPlantilla = '" . $jsonOBJ->statusPlantilla . "',"
                . " texto = '" . $jsonOBJ->texto . "', opc = 'stop' "
                . "where pc_serial = '" . base64_encode($jsonOBJ->serial) . "'";
    }
  

    $row = $con->exec($query);
    $con->desconectar();
    echo json_encode("Filas Actualizadas: " . $row);
}



// Metodo para peticiones tipo PATCH
if ($method == "PATCH") {
    $jsonString = file_get_contents("php://input");
    $jsonOBJ = json_decode($jsonString, true);
    $query = "update huellas_temp set imgHuella = '" . $jsonOBJ['imgHuella'] . "',"
            . "update_time = NOW(), statusPlantilla = '" . $jsonOBJ['statusPlantilla'] . "', texto = '" . $jsonOBJ['texto'] . "', "
            . "documento = '" . $jsonOBJ['documento'] . "', nombre = '" . $jsonOBJ['nombre'] . "',"
            . "dedo = '" . $jsonOBJ['dedo'] . "' where pc_serial = '" . $jsonOBJ['serial'] . "'";
    $row = $con->exec($query);
    $con->desconectar();
    echo json_encode("Filas Actualizadas: " . $row);
}