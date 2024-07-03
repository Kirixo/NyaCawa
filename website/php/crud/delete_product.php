<?php
require_once('../db.php');

$product_id = $_POST['product_id'];

$stmt = $conn->prepare("DELETE FROM products WHERE product_id = ?");
$stmt->bind_param("i", $product_id);

if ($stmt->execute()) {
    echo "Product deleted successfully.";
} else {
    http_response_code(500);
    echo "Error deleting product.";
}

$stmt->close();
$conn->close();
?>
