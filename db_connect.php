<?php

class DB_Connect
{
	
	private $conn;
	
	public function connect()
	{
		try
		{
			$this->conn = new PDO("sqlsrv:Server=IBSS00105\SQLEXPRESS;Database =NakathiDB","sa","pass@word1");
		   $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
	    }catch(Exception $ex)
	      {
		die(print_r($ex->getMessage()));

	      }
		return $this->conn;
	}
		
	
}

?>