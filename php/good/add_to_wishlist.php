<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'add' && isset($_POST['item_id']) && isset($_POST['image'])) {
    $item_id = $_POST['item_id'];
    $image = $_POST['image'];
    $user_id = $_SESSION['user_id'];

    // Initialize wishlist in session if not already set
    if (!isset($_SESSION['wishlist'])) {
        $_SESSION['wishlist'] = [];
    }

    // Add item from wishlist
    if (!isset($_SESSION['wishlist'][$item_id])) {
        // Get product price from database
        $sql = "SELECT prise FROM products WHERE product_id = $item_id";
        $result = $conn->query($sql);
        $row = $result->fetch_assoc();
        $prise = $row['prise'];

        $_SESSION['wishlist'][$item_id] = ['image' => $image, 'prise' => $prise];
    }

    // Update database
    $sql = "SELECT * FROM wishlist WHERE user_id = $user_id AND product_id = $item_id";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $sql = "DELETE FROM wishlist WHERE user_id = $user_id AND product_id = $item_id";
    } else {
        $sql = "INSERT INTO wishlist (user_id, product_id) VALUES ($user_id, $item_id)";
    }
    $conn->query($sql);
}
?>
