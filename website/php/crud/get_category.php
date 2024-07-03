<?php
require_once('../db.php');

$category_id = $_GET['category_id'];

$stmt = $conn->prepare("SELECT * FROM product_categories WHERE category_id = ?");
$stmt->bind_param("i", $category_id);
$stmt->execute();
$result = $stmt->get_result();

if ($category = $result->fetch_assoc()) {
    echo json_encode($category);
} else {
    http_response_code(404);
    echo "Category not found.";
}

$stmt->close();
$conn->close();
?>
