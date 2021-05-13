<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('navercon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if((($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $id=$_POST["id"];
        $list_id=$_POST["list_id"];


        if(empty($id)){
            $errMSG = "id를 입력하세요.";
        }
        else if(empty($list_id)){
            $errMSG = "list_id을 입력하세요.";
        }
        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO naver_list_id (id, list_id) VALUES(:id, :list_id)');
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':list_id', $list_id);

                if($stmt->execute())
                {
                    $successMSG = "새로운 리스트를 추가했습니다.";
                }
                else
                {
                    $errMSG = "리스트 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }
    }
?>
<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
<html>
   <body>
        
        <form action="<?php $_PHP_SELF ?>" method="POST">
            id: <input type = "text" name = "id" />
            list_id: <input type = "text" name = "list_id" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>
<?php 
    }
?>
