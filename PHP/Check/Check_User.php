<?php
$error = ""; $result = ""; 


$db = mysqli_connect($dbhost, $dbuser, $dbpass);
         mysqli_select_db($db, dbname); // 選擇資料庫
		 mysqli_set_charset($db, "UTF8");
		 $sql2 = "SELECT * FROM user";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   
		 $sql2 = "SELECT * FROM user";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
		 

         mysqli_close($db); // 關閉連接         
      }

?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Check_User</title>
</head>
<body>
<h1>Check_User</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    
    
    <input type="submit" name="send" value="Check_User"/>
</form>
<?php echo $result ?>

<table border="1">
    <thead>
       <tr>
           <th>User_Id</th>
           <th>User_Password</th>
           <th>User_Name</th>
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
	       $json = '<img src="http://';
	       $json .= '163.13.201.88/php/User_Pic/'.$row[3].'"';
		   echo "<td>" . $json . "</td>";
		   
           echo "</tr>";
        }
     }
     mysqli_free_result($rows);
     ?>
     </tbody>
</table>
</body>
</html>