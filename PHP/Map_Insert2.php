<?php

$db = mysqli_connect($dbhost, $dbuser, $dbpass);
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, dbname) or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id = $_POST["User_Id"];
   $Node_Name = $_POST["Restaurant_Name"];
   $Node_Lat = $_POST["Lat"];
   $Node_Lon = $_POST["Lon"];
   $Node_Phone = $_POST["Restaurant_Phone"];
   $Node_Star = $_POST["Star"];
   
  
   
   
   $statement = mysqli_prepare($db, "INSERT INTO `map` (`User_Id`, `Node_Name`, `Node_Lat` ,`Node_Lon` , `Node_Phone`,`Node_Star`) VALUES (?, ?, ?, ?, ?, ?)");
   mysqli_stmt_bind_param($statement, "ssddsd", $User_Id, $Node_Name, $Node_Lat, $Node_Lon, $Node_Phone, $Node_Star);
   mysqli_stmt_execute($statement);
    
   $response = array();
   $response["success"] = true;  
    
    echo json_encode($response);

mysqli_close($db); // 關閉伺服器連接
?>