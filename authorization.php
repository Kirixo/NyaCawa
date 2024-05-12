<?php
session_start(); // Start the session for user authentication

require_once('db.php'); // Include the database connection file

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['username']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Validate form data
    if (empty($username) || empty($password)) {
        echo "Please fill in all fields.";
    } else {
        // Check if the user exists in the database
        $check_query = "SELECT * FROM users WHERE name='$username'";
        $result = $conn->query($check_query);
        if ($result->num_rows == 1) {
            // User exists, verify the password
            $user_data = $result->fetch_assoc();
            $hashed_password = md5($password); // Hash the entered password
            if ($hashed_password === $user_data['password']) {
                // Passwords match, user is authenticated
                $_SESSION['username'] = $username; // Store the username in the session
                header("Location: web.php"); // Redirect to the welcome page
                exit(); // Stop execution after redirection
            } else {
                echo "Incorrect password.";
            }
        } else {
            echo "User does not exist.";
        }
    }
}
?>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Вхід</title>
  <style>
    .container {
      width: 50%;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }
    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 10px;
      margin: 5px 0;
      border-radius: 5px;
      border: 1px solid #ccc;
      box-sizing: border-box;
    }
    input[type="submit"] {
      width: 100%;
      padding: 10px;
      margin-top: 10px;
      border-radius: 5px;
      border: none;
      background-color: #007bff;
      color: #fff;
      cursor: pointer;
    }
    input[type="submit"]:hover {
      background-color: #0056b3;
    }
    .registration-link{
      display: block;
      text-align: center;
      margin-top: 10px;
    }
  </style>
</head>
<body>

<div class="container">
  <h2>Аутентифікація клієнта</h2>
  <form action="" method="post">
    <label for="username">Ім'я користувача:</label>
    <input type="text" id="username" name="username" required>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <input type="submit" value="Вхід">
    <a href="registration.php" class="registration-link">Зареєструватися</a>
  </form>
</div>

</body>
</html>
