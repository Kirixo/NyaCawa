<?php
require_once('../db.php');

$name = $_POST['name'];
$description = $_POST['description'];
$price = $_POST['price'];
$image = $_POST['image'];
$category_id = $_POST['category_id'];

$stmt = $conn->prepare("INSERT INTO products (name, description, price, image, category_id) VALUES (?, ?, ?, ?, ?)");
$stmt->bind_param("ssdsi", $name, $description, $price, $image, $category_id);

if ($stmt->execute()) {
    echo "Product added successfully.";
} else {
    http_response_code(500);
    echo "Error adding product.";
}

$stmt->close();
$conn->close();
?>
