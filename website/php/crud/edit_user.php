<?php
require_once('../db.php');

$user_id = $_POST['user_id'];
$email = $_POST['email'];
$name = $_POST['name'];
$is_admin = isset($_POST['is_admin']) ? 1 : 0;

$stmt = $conn->prepare("UPDATE users SET email = ?, name = ?, is_admin = ? WHERE user_id = ?");
$stmt->bind_param("ssii", $email, $name, $is_admin, $user_id);

if ($stmt->execute()) {
    echo "User updated successfully.";
} else {
    http_response_code(500);
    echo "Error updating user.";
}

$stmt->close();
$conn->close();
?>
