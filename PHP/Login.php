<?php
// 開啟伺服器連接
$db = mysqli_connect($dbhost, $dbuser, $dbpass);
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, dbname) or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
    $User_Id = $_POST["username"];
    $User_Password = $_POST["password"];
    
    $statement = mysqli_prepare($db, "SELECT `User_Name` , `User_Id`, `User_Password` FROM `user` WHERE `User_Id` = ? AND `User_Password` = ?");
    mysqli_stmt_bind_param($statement, "ss", $User_Id, $User_Password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $User_Name,  $User_Id, $User_Password);
    
	$query =  "SELECT `User_Pic` FROM user WHERE User_Id = '$User_Id' ";
	
	$json = 'http://';
	$json .= '163.13.201.88/php/User_Pic/';
	$rows = mysqli_query($db, $query);
	$row = mysqli_fetch_row($rows);

	$json .= $row[0];
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["name"] = $User_Name;
        $response["username"] = $User_Id;
        $response["password"] = $User_Password;
		$response["userimage"] = $json;
    }
    
    echo json_encode($response);
	mysqli_close($db);
?>