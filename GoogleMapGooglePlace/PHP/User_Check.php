<?php 
	
	$con = mysqli_connect('localhost', 'Mike', 'm5130410', 'db_test');
	if (mysqli_connect_errno()) {
		echo "Failed to connect MySQL: " . mysqli_connect_error();
	}
	mysqli_set_charset($con, "UTF8");

	$User_Id = $_POST["Content"];

	
		
    $query = mysqli_query($con, "SELECT `User_Id` , `User_Name` ,`User_Pic` FROM user WHERE User_Id = '$User_Id'");
	$json = '{ "Friend_Id":"無此用戶", "Friend_Name":"無此用戶", "Friend_Image":"無此用戶"}';
	while ($row = mysqli_fetch_array($query))
	{
	$Pic_title = "http://";
	$Pic_title .= "163.13.201.88/php/User_Pic/";
	$char ='"';
	$json = 
	'{
	"Friend_Id":"'.str_replace($char,'`',strip_tags($row['User_Id'])).'", 
	"Friend_Name":"'.str_replace($char,'`',strip_tags($row['User_Name'])).'",
	"Friend_Image":"'.$Pic_title .str_replace($char,'`',strip_tags($row['User_Pic'])).'"	
			
		}';
	$json = substr($json,0,strlen($json)-1);
	$json .='}';

	
	// omitted commas at the end of the array
	
	}
		
	
	// print json
	echo $json;
	
	mysqli_close($con);
	
?>