<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();

 $response = array();

if (isset($_POST['id_number'])&&
	isset($_POST['password']))
	
{
	$id_number = $_POST['id_number'];
	$password=$_POST['password'];

	


	$row = $db->checkPassword($id_number,$password);
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
   $response["error_msg"] = "required parameter(id_number,password) is missing";

   echo json_encode($response);
}

?>