<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('navercon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if((($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $list_id=$_POST["list_id"];
        $list_contents=$_POST["list_contents"];


        if(empty($list_id)){
            $errMSG = "list_id를 입력하세요.";
        }
        else if(empty($list_contents)){
            $errMSG = "list_contents을 입력하세요.";
        }
        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO naver_list (list_id, list_contents) VALUES(:list_id, :list_contents)');
                $stmt->bindParam(':list_id', $list_id);
                $stmt->bindParam(':list_contents', $list_contents);

                if($stmt->execute())
                {
                    $successMSG = "새로운 사용자를 추가했습니다.";
                }
                else
                {
                    $errMSG = "사용자 추가 에러";
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
            list_id: <input type = "text" name = "list_id" />
            list_contents: <input type = "text" name = "list_contents" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>
<?php 
    }
?>
