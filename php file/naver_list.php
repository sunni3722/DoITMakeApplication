 <?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('navercon.php');



//POST 값을 읽어온다.
$list_id=isset($_POST['list_id']) ? $_POST['list_id'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($list_id != "" ){ 

    $sql="select * from naver_list where list_id='$list_id' order by list_id";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "null";
    }
	else{

   		$data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        	extract($row);

            array_push($data, 
                array('list_id'=>$row["list_id"],
	  'list_contents'=>$row["list_contents"]
            ));
        }


        if (!$android) {
            echo "<pre>"; 
            print_r($data); 
            echo '</pre>';
        }else
        {
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("naver_profile"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        }
    }
}
else {
    echo "검색할 list id를 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         List_ID: <input type = "text" name = "list_id" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>