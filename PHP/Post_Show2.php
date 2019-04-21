<?php 
	
	$con = mysqli_connect($dbhost, $dbuser, $dbpass);
	if (mysqli_connect_errno()) {
		echo "Failed to connect MySQL: " . mysqli_connect_error();
	}
	mysqli_set_charset($con, "UTF8");
   
   $Node_Lat = $_POST["Node_Lat"];
   $Node_Lon = $_POST["Node_Lon"];
	
	$search = "SELECT `Node_Name` FROM node WHERE Node_Lat = '$Node_Lat' AND Node_Lon = '$Node_Lon'";  
		 $rows0 = mysqli_query($con , $search);
		 $row0 =  mysqli_fetch_row($rows0);
		 
		 $Node_Name = $row0[0];
	
	
	
	
	
	$query = mysqli_query($con, "SELECT * FROM post WHERE Node_Name = '$Node_Name' ORDER BY Post_Id DESC");
	$Pic_Title = "http://";
	$Pic_Title .= "163.13.201.88/php/Post_Pic/";
	$UPic_Title = "http://";
	$UPic_Title .= "163.13.201.88/php/User_Pic/";
	$json = '[';
    
	
	// create looping dech array in fetch
	while ($row = mysqli_fetch_array($query))
	{
		$User_Id = $row['User_Id'];

	$search = "SELECT `User_Name` , `User_Pic` FROM user WHERE User_Id = '$User_Id'";
		 $rows0 = mysqli_query($con , $search);
		 $row0 =  mysqli_fetch_row($rows0);
		 
		 $User_Name = $row0[0];
		 $User_Pic = $row0[1];
		$char ='"';
		$json .= 
		'{
			"User_Name":"'.$User_Name.'",
			"User_Image":"'.$UPic_Title.$User_Pic.'",
			"Node_Name":"'.str_replace($char,'`',strip_tags($row['Node_Name'])).'",
			
			"Post_Time":"'.str_replace($char,'`',strip_tags($row['Post_Time'])).'",
			"Post_Pic":"'.$Pic_Title.str_replace($char,'`',strip_tags($row['Post_Pic'])).'",
			"Post_Content":"'.str_replace($char,'`',strip_tags($row['Post_Content'])).'",
			"Post_Star":"'.str_replace($char,'`',strip_tags($row['Post_Star'])).'"
		    
			
		},';
	}
//"User_Pic":"'.$UPic_Title. $User_Pic.'",
	// omitted commas at the end of the array
	$json = substr($json,0,strlen($json)-1);

	$json .= ']';

	// print json
	echo $json;
	
	mysqli_close($con);
	
?>