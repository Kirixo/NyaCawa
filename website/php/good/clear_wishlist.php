<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['action']) && $_POST['action'] == 'clear') {
    $user_id = $_SESSION['user_id'];
    $sql = "DELETE FROM wishlist WHERE user_id = $user_id";
    $conn->query($sql);
    unset($_SESSION['wishlist']);
}
?>
