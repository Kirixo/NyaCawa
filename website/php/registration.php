<?php
require_once('db.php');

header('Content-Type: application/json'); // Добавляем заголовок для JSON

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    if (empty($name) || empty($email) || empty($password)) {
        echo json_encode(['success' => false, 'message' => "Please fill in all fields."]);
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode(['success' => false, 'message' => "Invalid email format."]);
    } elseif (!preg_match('/^[a-zA-Z0-9_]{1,25}$/', $password) || !preg_match('/[a-zA-Z]/', $password)) {
        echo json_encode(['success' => false, 'message' => "Password must contain at least one letter and consist of letters, numbers, and underscores (1-25 characters)."]);
    } else {
        $check_query = "SELECT * FROM users WHERE name='$name' OR email='$email'";
        $result = $conn->query($check_query);
        if ($result->num_rows > 0) {
            echo json_encode(['success' => false, 'message' => "Username or email already exists."]);
        } else {
            $status_query = "SHOW COLUMNS FROM users WHERE Field = 'status'";
            $status_result = $conn->query($status_query);
            $row = $status_result->fetch_assoc();
            $status_values = explode("','", substr($row['Type'], 6, -2));
            $first_status = $status_values[0];

            $hashed_password = md5($password);

            $insert_query = "INSERT INTO users (name, email, password, status) VALUES ('$name', '$email', '$hashed_password', '$first_status')";
            if ($conn->query($insert_query) === TRUE) {
                echo json_encode(['success' => true]);
            } else {
                echo json_encode(['success' => false, 'message' => "Error: " . $insert_query . "<br>" . $conn->error]);
            }
        }

        $conn->close();
    }
}
?>
