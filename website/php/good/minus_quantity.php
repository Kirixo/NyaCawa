<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'decrease' && isset($_POST['item_id'])) {
    $item_id = $_POST['item_id'];

    if (isset($_SESSION['cart'][$item_id])) {
        if ($_SESSION['cart'][$item_id]['quantity'] > 1) {
            $_SESSION['cart'][$item_id]['quantity']--;

            // Зменшення кількості в базі даних
            $sql = "UPDATE cart SET quantity = quantity - 1 WHERE user_id = $user_id AND product_id = $item_id";
            $conn->query($sql);
        } else {
            // Видалення товару з кошика, якщо кількість стає меншою за 1
            unset($_SESSION['cart'][$item_id]);
            $sql = "DELETE FROM cart WHERE user_id = $user_id AND product_id = $item_id";
            $conn->query($sql);
        }
    }
}
?>
