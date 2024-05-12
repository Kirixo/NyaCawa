
<?php 
require_once('db.php');

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // Validate form data
    if (empty($name) || empty($email) || empty($password)) {
        echo "Please fill in all fields.";
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo "Invalid email format.";
    } elseif (!preg_match('/^[a-zA-Z0-9_]{1,25}$/', $password) || !preg_match('/[a-zA-Z]/', $password)) {
        echo "Password must contain at least one letter and consist of letters, numbers, and underscores (1-25 characters).";
    } else {
        // Check if username or email already exists
        $check_query = "SELECT * FROM users WHERE name='$name' OR email='$email'";
        $result = $conn->query($check_query);
        if ($result->num_rows > 0) {
            echo "Username or email already exists.";
        } else {
            // Get the first status from the enum list
            $status_query = "SHOW COLUMNS FROM users WHERE Field = 'status'";
            $status_result = $conn->query($status_query);
            $row = $status_result->fetch_assoc();
            $status_values = explode("','", substr($row['Type'], 6, -2)); // Extract enum values
            $first_status = $status_values[0];

           // Hash the password using MD5
           $hashed_password = md5($password);

            // Insert user into the database with the first status
            $insert_query = "INSERT INTO users (name, email, password, status) VALUES ('$name', '$email', '$hashed_password', '$first_status')";
            if ($conn->query($insert_query) === TRUE) {
                // Redirect to a new page after successful registration
                header("Location: web.php");
                exit(); // Stop execution after redirection
            } else {
                echo "Error: " . $insert_query . "<br>" . $conn->error;
            }
        }

        $conn->close();
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
    .login-link {
      display: block;
      text-align: center;
      margin-top: 10px;
    }
  </style>
</head>
<body>

<div class="container">
  <h2>Реєстрація клієнта</h2>
  <form action="" method="post">
    <label for="email">Електронна пошта</label>
    <input type="text" id="email" name="email" required>
    <label for="username">Ім'я користувача:</label>
    <input type="text" id="username" name="name" required>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <input type="submit" value="Зареєструватися">
    <a href="google-oauth.php">Вхід через Google</a>
    <a href="authorization.php" class="login-link">Увійти</a>
  </form>
</div>

</body>
</html>


