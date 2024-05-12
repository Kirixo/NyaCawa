<?php

$host="localhost";
$user="root";
$password="";
$dbname="nyacawa";

$conn = new mysqli($host, $user, $password, $dbname)
	or die ('Could not connect to the database server' );

//$con->close();


// $conn = mysqli_connect($host, $user, $password,$dbname);

// if(!$conn){
//   die("Помилка підключення");
// } else{
// echo "Успіх";
// }
?>