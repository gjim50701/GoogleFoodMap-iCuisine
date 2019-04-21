<?php 
	
	$con = mysqli_connect($dbhost, $dbuser, $dbpass);
	if (mysqli_connect_errno()) {
		echo "Failed to connect MySQL: " . mysqli_connect_error();
	}
	mysqli_set_charset($con, "UTF8");
	
	$Node_Lat = $_POST["lat"];
    $Node_Lon = $_POST["lon"];

	$query =  "SELECT `Node_Pic` FROM node WHERE Node_Lat = '$Node_Lat' AND Node_Lon = '$Node_Lon'";//淡水可口魚丸
	
	$json = '{';
	$json .= '"Node_Pic":';
	$json .= '"http://';
	$json .= '163.13.201.88/php/Node_Pic/';
	

	
	// create looping dech array in fetch
	$rows = mysqli_query($con, $query);
	$row = mysqli_fetch_row($rows);

	$json .= $row[0];
		
		//$json .= $row[0]; 
			
	

	$json .= '"}';
   echo $json;
	
	mysqli_close($con);
	
?>
