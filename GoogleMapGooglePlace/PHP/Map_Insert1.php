<?php

$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, "db_test") or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id =  $_POST["User_Id"];
   $Node_Lat = $_POST["lat"];
   $Node_Lon = $_POST["lon"];
   
  $search = "SELECT * FROM node WHERE Node_Lat = '$Node_Lat' AND Node_Lon = '$Node_Lon'";
	$rows = mysqli_query($db, $search);
	$row = mysqli_fetch_row($rows);
	
	$sql = "INSERT INTO map " .
         "(User_Id, Node_Name, Node_Amenity, Node_Lat, Node_Lon, Node_Address, Node_Phone, Node_Star, Node_Pic) " .
         "VALUES ('$User_Id', '$row[1]', '$row[2]', " . 
         "$row[3], '$row[4]', '$row[5]', '$row[6]', '$row[7]', '$row[8]')";
         
		 if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
   
   $response = array();
   $response["success"] = true;  
    
    echo json_encode($response);

mysqli_close($db); // 關閉伺服器連接
?>