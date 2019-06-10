<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();
 $row = $db->getLoanType();

 echo json_encode($row);
 


?>