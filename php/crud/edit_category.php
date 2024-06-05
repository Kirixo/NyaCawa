<?php
require_once('../db.php');

$category_id = $_POST['category_id'];
$name = $_POST['name'];

$stmt = $conn->prepare("UPDATE product_categories SET name = ? WHERE category_id = ?");
$stmt->bind_param("si", $name, $category_id);

if ($stmt->execute()) {
    echo "Category updated successfully.";
} else {
    http_response_code(500);
    echo "Error updating category.";
}

$stmt->close();
$conn->close();
?>
