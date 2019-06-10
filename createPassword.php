<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();

 $response = array();

if (isset($_POST['password']))
	
{
	$password = $_POST['password'];
	$id_number = $_POST['id_number'];
	

	$row = $db->createPassword($password,$id_number);
	if($row)
	{

		$response["message"]= "true";
		echo json_encode($response);
	}else
	{
		$response["message"] = "false";
		echo json_encode($response);
	}
}
else
{
   $response["error_msg"] = "required parameter(password,id_number) is missing";

   echo json_encode($response);
}

?>