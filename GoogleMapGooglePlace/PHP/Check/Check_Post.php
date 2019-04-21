<?php
$error = ""; $result = ""; 


$db = mysqli_connect("localhost", "Mike", "m5130410");
         mysqli_select_db($db, "db_test"); // 選擇資料庫
		 mysqli_set_charset($db, "UTF8");
		 $sql2 = "SELECT * FROM post ORDER BY Post_Id";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   
		 $sql2 = "SELECT * FROM post ORDER BY Post_Id";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
		 

         mysqli_close($db); // 關閉連接         
      }

?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Check_Post</title>
</head>
<body>
<h1>Check_Post</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    
    
    <input type="submit" name="send" value="Check_Post"/>
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