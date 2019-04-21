<?php 
	
	$con = mysqli_connect('localhost', 'Mike', 'm5130410', 'db_test');
	if (mysqli_connect_errno()) {
		echo "Failed to connect MySQL: " . mysqli_connect_error();
	}
	mysqli_set_charset($con, "UTF8");

	$query = mysqli_query($con, "SELECT * FROM node");
	
	$json = '{"node": [';

	
	// create looping dech array in fetch
	while ($row = mysqli_fetch_array($query)){

	// quotation marks (") are not allowed by the json string, we will replace it with the` character
	// strip_tag serves to remove html tags on strings
		$char ='"';
		$json .= 
		'{
			"Node_Id":"'.str_replace($char,'`',strip_tags($row['Node_Id'])).'", 
			"Node_Name":"'.str_replace($char,'`',strip_tags($row['Node_Name'])).'",
			"Node_Lat":"'.str_replace($char,'`',strip_tags($row['Node_Lat'])).'",
			"Node_Lon":"'.str_replace($char,'`',strip_tags($row['Node_Lon'])).'",
			"Node_Address":"'.str_replace($char,'`',strip_tags($row['Node_Address'])).'",
			"Node_Phone":"'.str_replace($char,'`',strip_tags($row['Node_Phone'])).'",
		    "Node_Star":"'.str_replace($char,'`',strip_tags($row['Node_Star'])).'"
			
		},';
	}

	// omitted commas at the end of the array
	$json = substr($json,0,strlen($json)-1);

	$json .= ']}';

	// print json
	echo $json;
	
	mysqli_close($con);
	
?>