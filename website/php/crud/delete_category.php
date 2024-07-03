<?php
require_once('../db.php');

$category_id = $_POST['category_id'];

$stmt = $conn->prepare("DELETE FROM product_categories WHERE category_id = ?");
$stmt->bind_param("i", $category_id);

if ($stmt->execute()) {
    echo "Category deleted successfully.";
} else {
    http_response_code(500);
    echo "Error deleting category.";
}

$stmt->close();
$conn->close();
?>
