<?php
session_start();
require_once('db.php');

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    if (empty($email) || empty($password)) {
        echo json_encode(['success' => false, 'message' => "Будь ласка, заповніть усі поля."]);
    } else {
        $check_query = "SELECT * FROM users WHERE email=?";
        $stmt = $conn->prepare($check_query);
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows == 1) {
            $user_data = $result->fetch_assoc();
            $hashed_password = md5($password);
            if ($hashed_password === $user_data['password']) {
                $_SESSION['username'] = $user_data['name'];
                $_SESSION['user_id'] = $user_data['user_id'];
                $_SESSION['is_admin'] = $user_data['is_admin']; // Store admin status in the session
                if ($user_data['is_admin']) {
                    echo json_encode(['success' => true, 'redirect' => 'management.php']); // Redirect to admin management page
                } else {
                    echo json_encode(['success' => true, 'username' => $user_data['name']]);
                }
            } else {
                echo json_encode(['success' => false, 'message' => "Неправильний пароль."]);
            }
        } else {
            echo json_encode(['success' => false, 'message' => "Користувач не існує."]);
        }
    }
}
?>
