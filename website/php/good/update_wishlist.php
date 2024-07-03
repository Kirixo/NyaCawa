<?php
session_start();

$user_id = $_SESSION['user_id'];

$sql = "SELECT wishlist.product_id, products.name, products.price, products.image 
        FROM wishlist 
        JOIN products ON wishlist.product_id = products.product_id 
        WHERE wishlist.user_id = $user_id";
$result = $conn->query($sql);

$_SESSION['wishlist'] = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $_SESSION['wishlist'][$row['product_id']] = [
            'name' => $row['name'],
            'price' => $row['price'],
            'image' => $row['image']
        ];
    }
}
?>
