<?php
$error = ""; $result = ""; 


$db = mysqli_connect($dbhost, $dbuser, $dbpass);
         mysqli_select_db($db, "db_test"); // 選擇資料庫
		mysqli_set_charset($db, "UTF8");
		 $sql2 = "SELECT * FROM post";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   $User_Name = $_POST["User_Name"];   // 取得表單欄位值
   $Node_Name = $_POST["Node_Name"];
   $Post_Content = $_POST["Post_Content"];
   $Post_Star = $_POST["Post_Star"];
   date_default_timezone_set('Asia/Taipei');
   $Post_Time= date("Y/m/d H:i:s");
   
         
		 
         $sql = "INSERT INTO Post " .
         "(User_Name, Node_Name, Post_Time,  Post_Content, Post_Star) " .
         "VALUES ('$User_Name', '$Node_Name', '$Post_Time', " . 
         "'$Post_Content', '$Post_Star')";
         if (!mysqli_query($db, $sql)) { // 執行SQL指令
            $result = "新增記錄失敗...<br/>" . mysqli_error($db);
         }
         else $result = "新增記錄成功...<br/>";
		 $search = "SELECT Post_Star FROM post WHERE Node_Name = '$Node_Name' ";
		 $rows2 = mysqli_query($db , $search); // 執行SQL查詢              
         $num2 = mysqli_num_rows($rows2); // 取得記錄數
		 $counter = 0;
		 if ($num2 > 0) { // 有記錄
        
        for ($i = 0;$i < $num2; $i++ ) 
		{
           // 取出記錄資料
           $row2 = mysqli_fetch_row($rows2);
		   $counter = $counter + $row2[0]; 
        }
		 }
		 $counter2 = round($counter/$num2,1);
		 $sql3 = "UPDATE node SET Node_Star = '$counter2' WHERE node.Node_Name = '$Node_Name'";
		 mysqli_query($db, $sql3);
		 
		 $sql2 = "SELECT * FROM post";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
		 

         mysqli_close($db); // 關閉連接         
      }
else {  // 初始表單欄位值
   $User_Name = ""; $Node_Name = ""; $Post_Pic = "";
   $Post_Content = ""; $Post_Star = ""; 
} 
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Post_test.php</title>
</head>
<body>
<h1>新增記錄</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    <div>
        <label for="id">User:</label>
        <input type="text" name="User_Name" id="id"
               value="<?php echo $User_Name ?>"/>       
    </div>
    <div>
        <label for="title">Node:</label>
        <input type="text" name="Node_Name" id="title"
               value="<?php echo $Node_Name ?>"/>
    </div>
    
    <div>
        <label for="price">Content:</label>
        <input type="text" name="Post_Content" id="price"
               value="<?php echo $Post_Content ?>"/>
    </div>
    <div>
        <label for="category">Star(int):</label>
        <input type="int" name="Post_Star" id="category"
               value="<?php echo $Post_Star ?>"/>
    </div>
    
    <input type="submit" name="send" value="新增記錄"/>
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
