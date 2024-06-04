<?php
session_start();
include 'db.php'; // Підключення до бази даних

// Отримання необхідних даних з AJAX запиту
$action = $_POST['action'] ?? '';

// Перевірка наявності авторизації користувача (якщо потрібно)
if (!isset($_SESSION['user_id'])) {
    echo json_encode(['success' => false, 'message' => 'Користувач не автентифікований. Будь ласка, увійдіть.']);
    exit;
}

// Обробка різних операцій
switch ($action) {
    case 'add_to_wishlist':
        // Отримання даних з AJAX запиту
        $productId = (int)$_POST['product_id'];
        $userId = $_SESSION['user_id'];

        // Додавання товару до обраного
        $sql = "INSERT INTO wishlist (user_id, product_id) VALUES ($userId, $productId)";
        if ($conn->query($sql) === TRUE) {
            echo json_encode(['success' => true]);
        } else {
            echo json_encode(['success' => false, 'message' => 'Помилка при додаванні до обраного.']);
        }
        break;

    case 'remove_from_wishlist':
        // Отримання даних з AJAX запиту
        $productId = (int)$_POST['product_id'];
        $userId = $_SESSION['user_id'];

        // Видалення товару з обраного
        $sql = "DELETE FROM wishlist WHERE user_id = $userId AND product_id = $productId";
        if ($conn->query($sql) === TRUE) {
            echo json_encode(['success' => true]);
        } else {
            echo json_encode(['success' => false, 'message' => 'Помилка при видаленні з обраного.']);
        }
        break;

    // Інші операції...

    default:
        echo json_encode(['success' => false, 'message' => 'Невідома операція.']);
}
?>
