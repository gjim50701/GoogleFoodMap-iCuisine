<?php
$error = ""; $result = ""; 


$db = mysqli_connect("localhost", "Mike", "m5130410");
         mysqli_select_db($db, "db_test"); // 選擇資料庫
		 mysqli_set_charset($db, "UTF8");
		 $sql2 = "SELECT * FROM node";
         $rows = mysqli_query($db, $sql2); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數



if (isset($_POST["send"])) {  // 是否是表單送回
   $User_Id = $_POST["User_Id"];   // 取得表單欄位值
   $Node_Id = $_POST["Node_Id"];
   $Node_Name = $_POST["Node_Name"];
   $Node_Lat = $_POST["Node_Lat"];
   $Node_Lon = $_POST["Node_Lon"];
   if (!empty($Node_Id))
   {
	$search = "SELECT * FROM node WHERE Node_Id = '$Node_Id'";
	$rows = mysqli_query($db, $search);
	$row = mysqli_fetch_row($rows);
	
	$sql = "INSERT INTO map " .
         "(User_Id, Node_Name, Node_Amenity, Node_Lat, Node_Lon, Node_Address, Node_Phone, Node_Star, Node_Pic) " .
         "VALUES ('$User_Id', '$row[1]', '$row[2]', " . 
         "$row[3], '$row[4]', '$row[5]', '$row[6]', '$row[7]', '$row[8]')";
         
		 if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 $sql3 = "SELECT * FROM map";
         $rows = mysqli_query($db, $sql3); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
   }
   else
   {
	$sql = "INSERT INTO map " .
         "(User_Id, Node_Name,  Node_Lat, Node_Lon) " .
         "VALUES ('$User_Id', '$Node_Name', '$Node_Lat', " . 
         "$Node_Lon)";
         
		 if (!mysqli_query($db, $sql)) 
		 { // 執行SQL指令
            $result = "查詢記錄失敗...<br/>" . mysqli_error($db);  
		 }
		 
          $sql3 = "SELECT * FROM map";
         $rows = mysqli_query($db, $sql3); // 執行SQL查詢              
         $num = mysqli_num_rows($rows); // 取得記錄數
   }
        

         mysqli_close($db); // 關閉連接         
      }
else {  // 初始表單欄位值
   $User_Id = ""; $Node_Id = ""; 
   $Node_Name = ""; $Node_Lat = "";
   $Node_Lon = "";
   
}  
?>
<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>加入地圖</title>
</head>
<body>

<h1>加入地圖</h1><hr/>
<div style="color: red"><?php echo $error ?></div>
<form method="post" action="">
    <label for="id">Id與其他擇一填就好，Id不填則後三者必填</label>
	<div>
        <label for="id">(必填)User_Id:</label>
        <input type="text" name="User_Id" id="id"
               value="<?php echo $User_Id ?>"/>       
    </div>
    <div>
        <label for="title">Node_Id:</label>
        <input type="text" name="Node_Id" id="title"
               value="<?php echo $Node_Id ?>"/>
    </div>
	 <div>
        <label for="title">Node_Name:</label>
        <input type="text" name="Node_Name" id="title"
               value="<?php echo $Node_Name ?>"/>
    </div>
	<div>
        <label for="title">Node_Lat:</label>
        <input type="text" name="Node_Lat" id="title"
               value="<?php echo $Node_Lat ?>"/>
    </div>
	<div>
        <label for="title">Node_Lon:</label>
        <input type="text" name="Node_Lon" id="title"
               value="<?php echo $Node_Lon ?>"/>
    </div>
    
    
    <input type="submit" name="send" value="加入地圖"/>
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
		   $str = str_replace('02','(02)',$row[6]);
           echo "<td>" . $str . "</td>";
		   echo "<td>" . $row[7] . "</td>";
           echo "<td>" . urldecode($row[8]). "</td>";
           echo "</tr>";
        }
     }
     mysqli_free_result($rows);
     ?>
     </tbody>
</table>
</body>
</html>