<?php

$db = mysqli_connect("localhost", "Mike", "m5130410");
     mysqli_select_db($db, "db_test"); // 選擇資料庫
	  mysqli_set_charset($db, "UTF8");
	$User_Id = $_POST["User_Id"];   // 取得表單欄位值
    $User_Name = $_POST["User_New_Name"];

	$sql = "UPDATE user SET User_Name = '$User_Name' WHERE User_Id = '$User_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }	 
	$json = '{';
	$json .= '"User_New_Name":';
	$json .= '"'.$User_Name ;
	

	$json .= '"}';
   echo $json;
	
	mysqli_close($db);	 
		 
		 
		 
?>