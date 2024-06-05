<?php
function getProductPrice($conn, $item_id) {
    $stmt = $conn->prepare("SELECT prise FROM products WHERE product_id = ?");
    $stmt->bind_param("i", $item_id);
    $stmt->execute();
    $result = $stmt->get_result();
    return $result->fetch_assoc()['prise'] ?? null;
}

function addToWishlist($conn, $user_id, $item_id) {
    $stmt = $conn->prepare("INSERT INTO wishlist (user_id, product_id) VALUES (?, ?)");
    $stmt->bind_param("ii", $user_id, $item_id);
    return $stmt->execute();
}

function removeFromWishlist($conn, $user_id, $item_id) {
    $stmt = $conn->prepare("DELETE FROM wishlist WHERE user_id = ? AND product_id = ?");
    $stmt->bind_param("ii", $user_id, $item_id);
    return $stmt->execute();
}

function isItemInWishlist($conn, $user_id, $item_id) {
    $stmt = $conn->prepare("SELECT * FROM wishlist WHERE user_id = ? AND product_id = ?");
    $stmt->bind_param("ii", $user_id, $item_id);
    $stmt->execute();
    return $stmt->get_result()->num_rows > 0;
}
?>
