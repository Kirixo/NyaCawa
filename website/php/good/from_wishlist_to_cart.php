<?php
session_start();
include('../db.php');

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'add_all') {
    $user_id = $_SESSION['user_id'];

    if (!empty($_SESSION['wishlist'])) {
        foreach ($_SESSION['wishlist'] as $item_id => $item) {
            $item_id = intval($item_id);
            $sql = "SELECT * FROM cart WHERE user_id = $user_id AND product_id = $item_id";
            $result = $conn->query($sql);

            if ($result->num_rows > 0) {
                $sql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = $user_id AND product_id = $item_id";
            } else {
                $sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES ($user_id, $item_id, 1)";
            }
            $conn->query($sql);
        }
        // Clear the wishlist after transferring items to cart
        $sql = "DELETE FROM wishlist WHERE user_id = $user_id";
        $conn->query($sql);
        unset($_SESSION['wishlist']);
    }
}
?>
