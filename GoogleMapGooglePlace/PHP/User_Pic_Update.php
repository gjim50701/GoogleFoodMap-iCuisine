<?php
$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, "db_test") or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
 
   $fileName = $_SERVER['DOCUMENT_ROOT']."/php/User_Pic/";
   $image = $_POST["image"]; 
   $User_Id =  $_POST["User_Id"];
 
            if (!file_exists($fileName)) {
 
                //进行文件创建
                mkdir($fileName,0777,true);
 
            }
	$fileName2 = rand()."_" . time() .".jpg";
    $fileName  = $fileName . $fileName2;
	$sql = "UPDATE `user` SET User_Pic = '$fileName2' WHERE `User_Id` = '$User_Id'";
	
	if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }

 
	if(file_put_contents($fileName, base64_decode($image)))
	{
		echo json_encode([
		"Message" => "nice",
		"Status" => "OK"
		]);
	}else{
		echo json_encode([]);
	}
	
	

  
   
	
	
?>