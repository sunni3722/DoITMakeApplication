<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('navercon.php');



//POST 값을 읽어온다.
$list_id=isset($_POST['list_id']) ? $_POST['list_id'] : '';
$list_contents=isset($_POST['list_contents']) ? $_POST['list_contents'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($list_id!= "" ){ 

    $sql="UPDATE naver_list SET list_contents='$list_contents' WHERE list_id='$list_id'";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "'";
        echo $list_id;
        echo "'은 찾을 수 없습니다.";
    }
}
else {
    echo "변경할 list_id를 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         list_id: <input type = "text" name = "list_id" />
         list_contents: <input type = "text" name = "list_contents" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>
