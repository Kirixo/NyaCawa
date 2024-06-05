<?php
require_once('../db.php');

$product_id = $_POST['product_id'];
$name = $_POST['name'];
$description = $_POST['description'];
$price = $_POST['price'];
$image = $_POST['image'];
$category_id = $_POST['category_id'];

$stmt = $conn->prepare("UPDATE products SET name = ?, description = ?, price = ?, image = ?, category_id = ? WHERE product_id = ?");
$stmt->bind_param("ssdsii", $name, $description, $price, $image, $category_id, $product_id);

if ($stmt->execute()) {
    echo "Product updated successfully.";
} else {
    http_response_code(500);
    echo "Error updating product.";
}

$stmt->close();
$conn->close();
?>
