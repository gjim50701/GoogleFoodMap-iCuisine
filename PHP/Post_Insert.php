<?php
$error = ""; $result = ""; 


$db = mysqli_connect($dbhost, $dbuser, $dbpass);
         mysqli_select_db($db,dbname); // 選擇資料庫
		 mysqli_set_charset($db, "UTF8");
  // 是否是表單送回
   $User_Id = $_POST["User_Id"];   // 取得表單欄位值
   $Node_Lat = $_POST["Node_Lat"];
   $Node_Lon = $_POST["Node_Lon"];
   $Post_Content = $_POST["Post_desc"];
   $Post_Star = $_POST["Post_star"];
   date_default_timezone_set('Asia/Taipei');
   $Post_Time= date("Y/m/d H:i:s");
   $image = $_POST["Post_pic"]; 
   $fileName = $_SERVER['DOCUMENT_ROOT']."/php/Post_Pic/";
            if (!file_exists($fileName)) 
			{
                mkdir($fileName,0777,true);
            }
	
	$Post_Pic = rand()."_" . time() .".jpg";
    $fileName = $fileName . $Post_Pic;
	if(file_put_contents($fileName, base64_decode($image)))
	{
		$message = "GG";
	}
		 $search = "SELECT `Node_Name` FROM node WHERE Node_Lat = '$Node_Lat' AND Node_Lon = '$Node_Lon'";  
		 $rows0 = mysqli_query($db , $search);
		 $row0 =  mysqli_fetch_row($rows0);
		 
		 $Node_Name = $row0[0];
		 
		 
		 
		 
		 
         $sql = "INSERT INTO Post " .
         "(User_Id, Node_Name, Post_Time, Post_Pic, Post_Content, Post_Star) " .
         "VALUES ('$User_Id', '$Node_Name', '$Post_Time', " . 
         "'$Post_Pic', '$Post_Content', '$Post_Star')";
         if (!mysqli_query($db, $sql)) { // 執行SQL指令
            $result = "新增記錄失敗...<br/>" . mysqli_error($db);
         }
         else $result = "新增記錄成功...<br/>";
		 
		 $search = "SELECT Post_Star FROM post WHERE Node_Name = '$Node_Name' ";
		 $rows2 = mysqli_query($db , $search); // 執行SQL查詢              
         $num2 = mysqli_num_rows($rows2); // 取得記錄數
		 $counter = 0;
		 
		 $counter2= $Post_Star;
		 if ($num2 > 0) { // 有記錄
        
        for ($i = 0;$i < $num2; $i++ ) 
		{
           // 取出記錄資料
           $row2 = mysqli_fetch_row($rows2);
		   $counter = $counter + $row2[0]; 
        }
		 $counter2= round($counter/$num2,1);
		 }
		  
		 $sql3 = "UPDATE node SET Node_Star = '$counter2' WHERE Node_Name = '$Node_Name'";
		 mysqli_query($db, $sql3);
		 
		 $response = array();
         $response["success"] = true;  
    
         echo json_encode($response);
         mysqli_close($db); // 關閉連接         
     


?>
		 
         