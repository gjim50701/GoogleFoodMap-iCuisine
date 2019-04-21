<?php

$db = mysqli_connect($dbhost, $dbuser, $dbpass);
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, dbname) or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id =  $_POST["User_Id"];
   $Friend_Id = $_POST["Friend_Id"];
   
  
	$sql = "INSERT INTO `friend` (`User_Id`, `Friend_Id`) VALUES ('$User_Id', '$Friend_Id')";
    mysqli_query($db, $sql);    
	$sql2 = "INSERT INTO `friend` (`User_Id`, `Friend_Id`) VALUES ('$Friend_Id', '$User_Id')";	
	mysqli_query($db, $sql2);    
	
	$response = array();
   $response["success"] = true;  
    
    echo json_encode($response);

mysqli_close($db); // 關閉伺服器連接
?>



