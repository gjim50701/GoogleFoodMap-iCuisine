<?php

    $db = mysqli_connect($dbhost, $dbuser, $dbpass);
     mysqli_select_db($db, dbname); // 選擇資料庫
	mysqli_set_charset($db, "UTF8");
	
	$User_Id = $_POST["User_Id"];   // 取得表單欄位值
	
	$search = " SELECT Friend_Id FROM friend WHERE User_Id = '$User_Id'";
	
	$rows = mysqli_query($db, $search);
	$num = mysqli_num_rows($rows);
	

	$json = '{"node": [';
	for ($i = 0;$i < $num; $i++ ) {
	$row = mysqli_fetch_row($rows);
	
	$User_Id = $row[0];

	$query = mysqli_query($db, "SELECT `User_Id`,`Node_Name`, `Node_Amenity`, `Node_Lat`, `Node_Lon`, `Node_Address`, `Node_Phone`, `Node_Star` FROM map WHERE User_Id = '$User_Id'");
	
	
	
	// create looping dech array in fetch
	while ($row = mysqli_fetch_array($query)){

	// quotation marks (") are not allowed by the json string, we will replace it with the` character
	// strip_tag serves to remove html tags on strings
		$char ='"';
		$json .= 
		'{
			"User_Id":"'.str_replace($char,'`',strip_tags($row['User_Id'])).'", 
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
	if($i < $num-1)
	{
		$json .=",";
	}
	}

	$json .= ']}';
	
	echo $json;
	
	mysqli_close($db);
	