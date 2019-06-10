<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();


	$member_id = $_POST['member_id'];
	$loan_id = $_POST['loan_id'];
	$amount = $_POST['amount'];
	$date_requested=date("Y-m-d H:i:s");
	

	$row = $db->insertGuarantors($loan_id,$member_id,$amount,$date_requested);
	if($row)
	{
		
		echo json_encode($row);
	}else
	{
		$response["error_msg"] = "unknown error occured during the process of insertion";
		echo json_encode($response);
	}

 


?>