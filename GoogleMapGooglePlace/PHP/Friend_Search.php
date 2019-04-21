<?php

$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, "db_test") or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   

   $Friend_Id = $_POST["Friend_Id"];
   
  
	$sql = "SELECT `User_Id` , `User_Name` FROM User WHERE User_Id = '$Friend_Id'";
	
	$rows = mysqli_query($db, $sql);
	$row = mysqli_fetch_row($rows);
	
	$response["name"] = $row[0];
    $response["username"] = $row[1];
	
	

mysqli_close($db); // 關閉伺服器連接
?>