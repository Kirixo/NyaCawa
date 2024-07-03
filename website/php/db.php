
<?php
$host = "localhost";
$user = "root";
$password = "";
$dbname = "nyacawa";

$conn = new mysqli($host, $user, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}



//$con->close();


// $conn = mysqli_connect($host, $user, $password,$dbname);

/*if(!$conn){
   die("Помилка підключення");
 } else{
 echo "Успіх";
 }*/
?>