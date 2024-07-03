<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'remove' && isset($_POST['item_id'])) {
    $item_id = $_POST['item_id'];
    $user_id = $_SESSION['user_id'];
    $sql = "DELETE FROM wishlist WHERE user_id = $user_id AND product_id = $item_id";
    $conn->query($sql);
    unset($_SESSION['wishlist'][$item_id]);
}
?>
