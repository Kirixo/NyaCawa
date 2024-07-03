<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'remove' && isset($_POST['item_id'])) {
    $item_id = $_POST['item_id'];
    $sql = "DELETE FROM cart WHERE user_id = $user_id AND product_id = $item_id";
    $conn->query($sql);
    unset($_SESSION['cart'][$item_id]);
}
?>
