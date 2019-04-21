<?php
$error = ""; $result = ""; 


$db = mysqli_connect($dbhost, $dbuser, $dbpass);
         mysqli_select_db($db, dbname); // 選擇資料庫
		 mysqli_set_charset($db, "UTF8");
		 
$search = "SELECT * FROM map";
	$rows = mysqli_query($db, $search);
	$num = mysqli_num_rows($rows);

if (isset($_POST["send"])) {  // 是否是表單送回
   
  
	$search = "SELECT * FROM map";
	$rows = mysqli_query($db, $search);
	$num = mysqli_num_rows($rows);

	
	

         mysqli_close($db); // 關閉連接         

}  
?>
<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Check_Map</title>
</head>
<body>

<h1>Check_Map</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    
    
    
    <input type="submit" name="send" value="Check"/>
</form>
<?php echo $result ?>

<table border="1">
    <thead>
       <tr>
           <th>Id</th>
           <th>Name</th>
           <th>Amenity</th>
           <th>Lat</th>
		   <th>Lon</th>
		   <th>Address</th>
		   <th>Phone</th>
		   <th>Star</th>
		   <th>Pic</th>
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
		   echo "<td>" . $row[7] . "</td>";
           echo "<td>" . $row[8] . "</td>";
           echo "</tr>";
        }
     }
     mysqli_free_result($rows);
     ?>
     </tbody>
</table>
</body>
</html>