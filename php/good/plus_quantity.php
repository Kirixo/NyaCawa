<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'increase' && isset($_POST['item_id'])) {
    $item_id = $_POST['item_id'];

    if (isset($_SESSION['cart'][$item_id])) {
        $_SESSION['cart'][$item_id]['quantity']++;

        // Збільшення кількості в базі даних
        $sql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = $user_id AND product_id = $item_id";
        $conn->query($sql);
    }
}
?>
