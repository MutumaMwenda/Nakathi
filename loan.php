<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();

 $response = array();

if (
	isset($_POST['loan_type_id']) &&
	isset($_POST['member_id']) &&
	isset($_POST['amount']))

	
	
{
	$loan_type_id = $_POST['loan_type_id'];
	$member_id = $_POST['member_id'];
	$amount = $_POST['amount'];
	$date_requested=date("Y-m-d H:i:s");
	


	$row = $db->insertLoan($loan_type_id,$member_id,$amount,$date_requested);
	if($row)
	{
		/*$response["loan_type_id"] =$row["loan_type_id"];
		$response["member_id"] =$row["member_id"];
		$response["amount"] =$row["amount"];
		
		
	echo json_encode($response);*/
		
		$response["message"]= $row;
		echo json_encode($response);
	}else
	{
		$response["error_msg"] = "unknown error occured during the process of insertion";
		echo json_encode($response);
	}
}
else
{
   $response["error_msg"] = "required parameter(loan_type_id,member_id,amount,date_requested) is missing";

   echo json_encode($response);
}


 

 
 



?>