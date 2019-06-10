<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();
 $row = $db->getDepartments();

 echo json_encode($row);
 



?>