<?php
// Підключення до бази даних
include 'db.php';

// Запит до бази даних для отримання імені користувача
$query = "SELECT `name` FROM `nyacawa`.`users` WHERE `user_id` = ?";

// Підготовка та виконання запиту
$stmt = $pdo->prepare($query);
$stmt->execute([$_SESSION['user_id']]); // Припускаючи, що ви зберігаєте ідентифікатор користувача у сесії

// Отримання результату
$user = $stmt->fetch(PDO::FETCH_ASSOC);

// Формування відповіді
$response = [];
if ($user) {
    $response['success'] = true;
    $response['username'] = $user['name'];
} else {
    $response['success'] = false;
}

// Повернення відповіді у форматі JSON
header('Content-Type: application/json');
echo json_encode($response);
?>
