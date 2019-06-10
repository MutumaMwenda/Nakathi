<?php
 require_once 'db_functions.php';
 $db = new Db_Functions();

 $response = array();

if (
	isset($_POST['assetName']) &&
	isset($_POST['barcode']) &&
	isset($_POST['department']) &&
	isset($_POST['location']) &&
	isset($_POST['sublocation']) &&
    isset($_POST['subsublocation']) &&
	isset($_POST['assettype']) &&
	isset($_POST['condition']) &&
	isset($_POST['assetuser']) &&
	isset($_POST['serial_no']) &&
	isset($_POST['user_id'])) 
	
	
{
	$assetName = $_POST['assetName'];
	$barcode = $_POST['barcode'];
	$department = $_POST['department'];
	$location = $_POST['location'];
	$sublocation = $_POST['sublocation'];
	$subsublocation = $_POST['subsublocation'];
	$assettype = $_POST['assettype'];
	$condition = $_POST['condition'];
	$assetuser = $_POST['assetuser'];
	$serial_no = $_POST['serial_no'];
	$user_id = $_POST['user_id'];


	$row = $db->insertAsset($assetName,$barcode,$department,$location,$sublocation,$subsublocation,$assettype,$condition,$assetuser,$serial_no,$user_id);
	if($row)
	{
		/*$response["assetName"] =$row[""];
		$response["barcode"] =$row["barcode"];
		$response["department"] =$row["department"];
		$response["location"] =$row["location"];
		$response["sublocation"] =$row["sub_location"];
		$response["subsublocation"] =$row["sub_sublocation"];
		$response["assettype"] =$row["asset_type"];
		$response["condition"] =$row["condition"];
		$response["assetuser"] =$row["asset_user"];
		$response["serial_no"] =$row["serial_no"];
		$response["user_id"] =$row["user_id"];
		echo json_encode($response);*/
		echo json_encode("true");
	}else
	{
		$response["error_msg"] = "Error occured during the process of insertion";
		echo json_encode($response);
	}
}
else
{
   $response["error_msg"] = "required parameter(assetName,barcode,department,location,sublocation,sslocation,assetType,condition,assetUser,serial,user_id) is missing";

   echo json_encode($response);
}


 

 
 



?>