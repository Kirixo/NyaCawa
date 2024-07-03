<?php
require_once('../db.php');

$user_id = $_POST['user_id'];

$stmt = $conn->prepare("DELETE FROM users WHERE user_id = ?");
$stmt->bind_param("i", $user_id);

if ($stmt->execute()) {
    echo "User deleted successfully.";
} else {
    http_response_code(500);
    echo "Error deleting user.";
}

$stmt->close();
$conn->close();
?>
