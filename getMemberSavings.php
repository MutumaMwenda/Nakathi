<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();


	$id_number = $_POST['id_number'];
	

	$row = $db->getMemberSavings($id_number);
	if($row)
	{
		/*$response["loan_type_id"] =$row["loan_type_id"];
		$response["member_id"] =$row["member_id"];
		$response["amount"] =$row["amount"];
		
		
	echo json_encode($response);*/
		
		echo json_encode($row);
	}else
	{
		$response["error_msg"] = "unknown error occured during the process of insertion";
		echo json_encode($response);
	}

 


?>