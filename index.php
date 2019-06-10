<?php

     try
		{
		$conn = new PDO("sqlsrv:Server=IBSKE00223\MSSQLSERVER1;Database = Gate_15","sa","password@1");
		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);


		//$this->conn->exec("set names utf8");
	    }catch(Exception $ex)
	      {
		die(print_r($ex->getMessage()));

	      }
	      $query = "SELECT TOP 4 * FROM Supplier";
  		$stmt =$conn->prepare($query);
  		$stmt->execute();
  		$results=$stmt->fetchAll(PDO::FETCH_BOTH);

  		$Supplier=array();

  		foreach ($results as $rows) {
  			echo json_encode($rows['ID'].' '.$rows['SupplierName']) ;
  			echo '<br>';


  			
  		}
  			//return $results;


?>