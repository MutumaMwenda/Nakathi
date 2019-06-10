<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();
 $row = $db->getMembers();

 echo json_encode($row);
 

?>