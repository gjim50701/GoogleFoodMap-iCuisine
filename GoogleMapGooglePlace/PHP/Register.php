<?php

$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, "db_test") or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id = $_POST["username"];
   $User_Name = $_POST["name"];
   $User_Password = $_POST["password"];
   
   //$sql = "INSERT INTO `user1` (`User_Id`, `User_Password`, `User_name`) VALUES ( '$User_Id' , '$User_Password', '$User_name')";
   //mysqli_query($db, $sql);
   
   
   $statement = mysqli_prepare($db, "INSERT INTO `user` (`User_Id`, `User_Password`, `User_Name`) VALUES (?, ?, ?)");
   mysqli_stmt_bind_param($statement, "sss", $User_Id, $User_Password, $User_Name);
   mysqli_stmt_execute($statement);
   $sql = "UPDATE user SET User_Pic = 'Default.jpg' WHERE User_Id = '$User_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
   $response = array();
   $response["success"] = true;  

    echo json_encode($response);

mysqli_close($db); // 關閉伺服器連接
?>




