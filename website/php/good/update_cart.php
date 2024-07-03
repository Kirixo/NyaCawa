<?php
session_start();
include('db.php');

$user_id = $_SESSION['user_id'];

$sql = "SELECT cart.product_id, cart.quantity, products.name, products.price, products.image 
        FROM cart 
        JOIN products ON cart.product_id = products.product_id 
        WHERE cart.user_id = $user_id";
$result = $conn->query($sql);

$_SESSION['cart'] = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $_SESSION['cart'][$row['product_id']] = [
            'quantity' => $row['quantity'],
            'name' => $row['name'],
            'price' => $row['price'],
            'image' => $row['image'] // Додано посилання на фото товару у сесію
        ];
    }
}
?>
