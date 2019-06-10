<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();

 $response = array();

if (isset($_POST['id_number']))
	
{
	$id_number = $_POST['id_number'];
	


	$row = $db->checkId($id_number);
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
   $response["error_msg"] = "required parameter(id_number) is missing";

   echo json_encode($response);
}

?>