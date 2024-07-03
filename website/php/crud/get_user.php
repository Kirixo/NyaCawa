<?php
require_once('../db.php');

$user_id = $_GET['user_id'];

$stmt = $conn->prepare("SELECT * FROM users WHERE user_id = ?");
$stmt->bind_param("i", $user_id);
$stmt->execute();
$result = $stmt->get_result();

if ($user = $result->fetch_assoc()) {
    echo json_encode($user);
} else {
    http_response_code(404);
    echo "User not found.";
}

$stmt->close();
$conn->close();
?>
