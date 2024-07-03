<?php
session_start();
include('../db.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'add' && isset($_POST['item_id']) && isset($_POST['image'])) {
    $item_id = intval($_POST['item_id']);
    $image = $_POST['image'];
    $user_id = $_SESSION['user_id'];

    // Initialize wishlist in session if not already set
    if (!isset($_SESSION['wishlist'])) {
        $_SESSION['wishlist'] = [];
    }

    // Add item to wishlist
    if (!isset($_SESSION['wishlist'][$item_id])) {
        // Get product price from database
        $sql = "SELECT price FROM products WHERE product_id = $item_id";
        $result = $conn->query($sql);
        if ($result && $row = $result->fetch_assoc()) {
            $price = $row['price'];
            $_SESSION['wishlist'][$item_id] = ['image' => $image, 'price' => $price];
        }
    }

    // Update database
    $sql = "SELECT * FROM wishlist WHERE user_id = $user_id AND product_id = $item_id";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $sql = "DELETE FROM wishlist WHERE user_id = $user_id AND product_id = $item_id";
    } else {
        $sql = "INSERT INTO wishlist (user_id, product_id) VALUES ($user_id, $item_id)";
    }

    if ($conn->query($sql) === TRUE) {
        // Update the wishlist in session
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
    }
}
?>
