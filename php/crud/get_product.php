<?php
require_once('../db.php');

$product_id = $_GET['product_id'];

$stmt = $conn->prepare("SELECT * FROM products WHERE product_id = ?");
$stmt->bind_param("i", $product_id);
$stmt->execute();
$result = $stmt->get_result();

if ($product = $result->fetch_assoc()) {
    echo json_encode($product);
} else {
    http_response_code(404);
    echo "Product not found.";
}

$stmt->close();
$conn->close();
?>
