<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'add' && isset($_POST['item_id']) && isset($_POST['image'])) {
    $item_id = $_POST['item_id'];
    $image = $_POST['image'];
    $user_id = $_SESSION['user_id']; // додано

    // Store image path in session
    if (!isset($_SESSION['cart'])) {
        $_SESSION['cart'] = [];
    }

    if (!isset($_SESSION['cart'][$item_id])) {
        $_SESSION['cart'][$item_id] = ['quantity' => 0, 'image' => $image];
    }

    $_SESSION['cart'][$item_id]['quantity']++;

    // Add item to the database
    $sql = "SELECT * FROM products WHERE product_id = $item_id";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        // Check if the item is already in the cart
        $sql = "SELECT * FROM cart WHERE user_id = $user_id AND product_id = $item_id";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            // If item is already in the cart, increase the quantity
            $sql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = $user_id AND product_id = $item_id";
        } else {
            // If item is not in the cart, insert it with the image path
            $sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES ($user_id, $item_id, 1)";
        }
        $conn->query($sql);
    }
   /* header("Location: " . $_SERVER['PHP_SELF']);
    exit;*/
}

?>
