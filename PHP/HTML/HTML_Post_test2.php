<?php
$error = ""; $result = ""; 


$db = mysqli_connect($dbhost, $dbuser, $dbpass);
         mysqli_select_db($db, "db_test"); // 選擇資料庫
		 $sql2 = "SELECT * FROM post";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   $User_Id = $_POST["User_Id"];   // 取得表單欄位值
   $Node_Id = $_POST["Node_Id"];
   if (!empty($User_Id))
   {
	$sql = "SELECT * FROM Post WHERE User_Id = '$User_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 
         $rows = mysqli_query($db, $sql); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); 
   }
   if (!empty($Node_Id))
   {
	$sql = "SELECT * FROM Post WHERE Node_Id = '$Node_Id'";
         if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 
         $rows = mysqli_query($db, $sql); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); 
   }
        

         mysqli_close($db); // 關閉連接         
      }
else {  // 初始表單欄位值
   $User_Id = ""; $Node_Id = ""; 
} 
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Post_test.php</title>
</head>
<body>
<h1>查詢記錄</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    <div>
        <label for="id">User_Id=:</label>
        <input type="text" name="User_Id" id="id"
               value="<?php echo $User_Id ?>"/>       
    </div>
    <div>
        <label for="title">Node:</label>
        <input type="text" name="Node_Id" id="title"
               value="<?php echo $Node_Id ?>"/>
    </div>
    
    
    <input type="submit" name="send" value="查詢記錄"/>
</form>
<?php echo $result ?>

<table border="1">
    <thead>
       <tr>
           <th>Post_Id</th>
           <th>User_Id</th>
           <th>Node_Id</th>
           <th>Post_Time</th>
		   <th>Post_Pic</th>
		   <th>Post_Content</th>
		   <th>Post_Star</th>
		  
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
           echo "<td>" . $row[5] . "</td>";
		   echo "<td>" . $row[6] . "</td>";
           echo "</tr>";
        }
     }
     mysqli_free_result($rows);
     ?>
     </tbody>
</table>
</body>
</html>