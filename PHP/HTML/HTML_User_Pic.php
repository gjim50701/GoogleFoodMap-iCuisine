<?php
$msg = "";
if (isset($_FILES["file"])) {  // 是否有上傳檔案資料
   // 顯示上傳檔案資訊
   $fileName = $_SERVER['DOCUMENT_ROOT']."/php/User_Pic/";
 
            if (!file_exists($fileName)) {
 
                //进行文件创建
                mkdir($fileName,0777,true);
 
            }
 
            //进行名称的拼接
            $imgName = $fileName.$_FILES["file"]["name"];
 
            //获取上传数据并写入
            $result = move_uploaded_file($_FILES['file']['tmp_name'],$imgName);
   
$msg .= "檔案上傳成功<br/>";

$db = mysqli_connect("localhost", "Mike", "m5130410");
if (!$db) die("錯誤: 無法連接MySQL伺服器!" . mysqli_connect_error());

mysqli_select_db($db, dbname) or  // 選擇資料庫
   die("錯誤: 無法選擇資料庫!" . mysqli_error($db));
   mysqli_set_charset($db, "UTF8");
   
   $User_Id =  "wangdaming";
   $Pic_Name = $_FILES["file"]["name"];
	
	$sql = "UPDATE `user` SET User_Pic = '$Pic_Name' WHERE `User_Id` = '$User_Id'";
	if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
}
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>HTML_User_Pic</title>
</head>
<body>
<h1>上傳檔案</h1><hr/>
<form action="" enctype="multipart/form-data"
      method="post">
  <div>
    <input type="file" name="file"/>
  </div><br/>
  <input type="submit" value="上傳檔案" />
</form>
<?php 
if (!empty($msg)) {
    echo "<p>" . $msg . "</p>";
}
?>
</body>
</html>
