<?php
$error = ""; $result = ""; 


$db = mysqli_connect("localhost", "Mike", "m5130410");
         mysqli_select_db($db, "db_test"); // 選擇資料庫
		 $sql2 = "SELECT * FROM user";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   $User_Id = $_POST["User_Id"];   // 取得表單欄位值
   $User_Password = $_POST["User_Password"];
   $User_Name = $_POST["User_Name"];
   if (!empty($User_Password))
   {
	$sql = "UPDATE user SET User_Password = '$User_Password' WHERE user.User_Id = '$User_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
   }
   if (!empty($User_Name))
   {
	$sql = "UPDATE user SET User_Name = '$User_Name' WHERE user.User_Id = '$User_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
   }
        

         mysqli_close($db); // 關閉連接         
      }
else {  // 初始表單欄位值
   $User_Id = ""; $User_Password = ""; 
   $User_Name = "";
}  
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Post_test.php</title>
</head>
<body>
<h1>更新使用者</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    <div>
        <label for="id">User_Id:</label>
        <input type="text" name="User_Id" id="id"
               value="<?php echo $User_Id ?>"/>       
    </div>
    <div>
        <label for="title">User_Password:</label>
        <input type="text" name="User_Password" id="title"
               value="<?php echo $User_Password ?>"/>
    </div>
	 <div>
        <label for="title">User_Name:</label>
        <input type="text" name="User_Name" id="title"
               value="<?php echo $User_Name ?>"/>
    </div>
    
    
    <input type="submit" name="send" value="更新使用者"/>
</form>
<?php echo $result ?>

<table border="1">
    <thead>
       <tr>
           <th>User_Id</th>
           <th>User_Password</th>
           <th>User_Name</th>
           <th>Map_Id</th>
		   <th>User_Pic</th>
		   
		  
    </thead>
    <tbody>
    <?php
	if ($num > 0) { // 有記錄
        // 顯示每一筆記錄  
        for ($i = 0;$i < $num; $i++ ) {
           // 取出記錄資料
           $row = mysqli_fetch_row($rows);
           echo "<tr>";
           echo "<td>" . $row[0] . "</td>";
           echo "<td>" . $row[1] . "</td>";
           echo "<td>" . $row[2] . "</td>";
           echo "<td>" . $row[3] . "</td>";
		   echo "<td>" . $row[4] . "</td>";
          
           echo "</tr>";
        }
     }
     mysqli_free_result($rows);
     ?>
     </tbody>
</table>
</body>
</html>