<?php

$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, "db_test") or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id =  $_POST["User_Id"];
   $Node_Lat = $_POST["lat"];
   $Node_Lon = $_POST["lon"];
   $sql = "DELETE FROM map WHERE User_Id = '$User_Id' AND Node_Lat = '$Node_Lat' AND Node_Lon = '$Node_Lon'";
   mysqli_query($db, $sql);
   
   $response = array();
   $response["success"] = true;  
    
    echo json_encode($response);

mysqli_close($db); // 關閉伺服器連接
?>