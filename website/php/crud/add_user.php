<?php
require_once('db.php');

$email = $_POST['email'];
$name = $_POST['name'];
$password = password_hash($_POST['password'], PASSWORD_BCRYPT);
$is_admin = isset($_POST['is_admin']) ? 1 : 0;

$stmt = $conn->prepare("INSERT INTO users (email, name, password, is_admin) VALUES (?, ?, ?, ?)");
$stmt->bind_param("sssi", $email, $name, $password, $is_admin);

if ($stmt->execute()) {
    echo "User added successfully.";
} else {
    http_response_code(500);
    echo "Error adding user.";
}

$stmt->close();
$conn->close();
?>
