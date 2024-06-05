<?php
require_once('../db.php');

$name = $_POST['name'];

$stmt = $conn->prepare("INSERT INTO product_categories (name) VALUES (?)");
$stmt->bind_param("s", $name);

if ($stmt->execute()) {
    echo "Category added successfully.";
} else {
    http_response_code(500);
    echo "Error adding category.";
}

$stmt->close();
$conn->close();
?>
