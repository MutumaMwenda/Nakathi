<?php
 
  class Db_Functions  
  {
  	private $conn;
  	function __construct()
  	{
  		require_once 'db_connect.php';
  		$db = new DB_Connect();
  		$this->conn = $db->connect();

  	}

  	function __destruct()
  	{

  		
  	}

  	public function getLoans()
  	{
  		$query = "SELECT  loan_type_id,member_id,amount FROM Loans";
  		
  		$stmt = $this->conn->prepare($query);
  		$stmt->execute();

  		$loan = array();

  		if($stmt->execute())
  		{
  		while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) 

  			
  				$loan[] = $row;
  				return $loan;
  		

  		}
}
   
 public function getMemberSavings($id_number)
    {

 /*$q= $conn->query("SELECT name FROM `login_users` WHERE username='$userid'");
$username = $q->fetchColumn();*/

 $stmt = $this->conn->prepare("SELECT amount FROM Daily_Savings_collection WHERE id_number=?");
 $stmt->execute([$id_number]);

while ($savings=$stmt->fetch(PDO::FETCH_ASSOC)) {
    
    return $savings;
  } 
} 

 



 public function checkId($id_number)
    {

  $response = array();

 $id_number = $_POST['id_number'];
 $stmt = $this->conn->prepare("SELECT * FROM Members WHERE id_number=?");
 $stmt->execute([$id_number]);
 $user = $stmt->fetch();
if ($user) {
    
              return true;
          } 
       else 
        {
             return false;
        } 

 
}

 public function checkPassword($id_number,$password)
    {
 $stmt = $this->conn->prepare("SELECT * FROM Members WHERE id_number=? and password=?");
 $stmt->bindValue(1,$id_number);
 $stmt->bindValue(2,$password);
 $stmt->execute();
 $result=$stmt->fetch();
if ($result) {
    
    return true;
} 
else 
{
   return false;
} 

 
}

 public function createPassword($password, $id_number)
    {
$stmt = $this->conn->prepare("SELECT * FROM Members WHERE id_number=?");
 $stmt->execute([$id_number]);
 $user = $stmt->fetch();

 if ($user) {
    
 $stmt = $this->conn->prepare("UPDATE Members SET password=? WHERE id_number=?");
$stmt->bindValue(1,$password);
$stmt->bindValue(2,$id_number);

$result=$stmt->execute();

return true;
} 
else 
{
   return false;
} 


 
}
    public function insertGuarantors($loan_id,$member_id,$amount,$date_requested)
      {
        
        $stmt = $this->conn->prepare("INSERT INTO Guarantors(loan_id,member_id,amount,date_requested)VALUES(?,?,?,?)");
        $stmt->bindValue(1,$loan_id);
        $stmt->bindValue(2,$member_id);
        $stmt->bindValue(3,$amount);
        $stmt->bindValue(4,$date_requested);

        $result = $stmt->execute();

        if ($result) {  

           return true;

        }else{
          return false;
        }


      }
    

      public function getLoanType()
    {
      $query = "SELECT  id,name FROM Loan_Type";
      
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      $loantype = array();

      if($stmt->execute())
      {
      while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) 

        
          $loantype[] = $row;
          return $loantype;
      
      }
    }

        public function getMembers()
    {
      $query = "SELECT id_number,first_name+ ' ' + last_name AS name FROM Members";
      
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      $members = array();

      if($stmt->execute())
      {
      while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) 

        
         $members[] = $row;
          return $members;
      
      }
    }
  		
  	

  	public function insertAsset($assetName,$barcode,$department,$location,$sublocation,$sslocation,$assetType,$condition,$assetUser,$serial,$user_id)
  	{
  			$query = "INSERT INTO Loans(assetName,barcode,department,location,sub_location,sub_sublocation
        ,asset_type,condition,asset_user,serial_no,user_id)
  			VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		  	$stmt = $this->conn->prepare($query);
		  	$stmt->bindValue(1, $assetName);
		    $stmt->bindValue(2, $barcode);
		    $stmt->bindValue(3, $department);
		    $stmt->bindValue(4, $location);
        $stmt->bindValue(5, $sublocation);
        $stmt->bindValue(6, $sslocation);
        $stmt->bindValue(7, $assetType);
        $stmt->bindValue(8, $condition);
		    $stmt->bindValue(9, $assetUser);
		    $stmt->bindValue(10, $serial);
		    $stmt->bindValue(11, $user_id);
		    
		    $result = $stmt->execute();
  		    
		  		if($result)
		  		{
		  			return $result;
		  		}
		  		else{
		  			return false;
		  		}

  	}


 
		  public function insertLoan($loan_type_id,$member_id,$amount,$date_requested)
		  {
        $id = -1;
		  	$stmt = $this->conn->prepare("INSERT INTO Loans(loan_type_id,member_id,amount,date_requested)VALUES(?,?,?,?)");
		  	$stmt->bindValue(1,$loan_type_id);
		  	$stmt->bindValue(2,$member_id);
		  	$stmt->bindValue(3,$amount);
        $stmt->bindValue(4,$date_requested);

		  	$result = $stmt->execute();

		  	if ($result) {  

           $id = $this->conn->lastInsertId();
		  		 return $id;

		  	}else{
		  		return $id;
		  	}


		  }



}

?>