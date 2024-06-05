<?php session_start();
include 'db.php'; ?>
<!DOCTYPE html>
<html lang="ua">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>NyaCawa</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../css/style.css?v=<?php echo time();?>">
    <script src="../js/jquery.js"></script>
    <script src="../js/bootstrap.js"></script>

    <style>
        /* Додаткові стилі для покращення вигляду форми */
        .modal-header {
            background-color: #1B1A1F;
            color: #BEBEBE;
        }
        .modal-footer {
            background-color: #F4F0F6;
            display: flex;
            justify-content: space-between;
        }
        .toggle-link {
            cursor: pointer;
            color: #1B1A1F;
        }
        .toggle-link:hover {
            text-decoration: underline;
        }

        .btn-primary{

            background-color: #535C91;
        }
        .btn-primary: hover{
            background-color: #666e9f;
        }

        .google-btn {
            background-color: #ffffff;
            color: #000000;
            border: none;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .google-btn:hover{
            background-color: #dddddd;
        }
        .google-btn img {
            margin-right: 10px;
        }

        .name{
            margin-top: 10px;
            color: #BEBEBE;
            font-size: 24px;
            font-family: Montserrat;
        }
    </style>
</head>
<body>
<header>
    <img src="../img/827438-Neon-Genesis-Evangelion-anime-city-Evangelion1.0.jpg" alt="" class="head_img">
</header>
<div id="fon"></div>

<nav class="navbar navbar-expand-lg">

        <a class="navbar-brand" href="index.php">NyaCawa</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="catalog.php">Каталог</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ourapp.php">Про нас</a>
                </li>
                <form class="d-flex search_f" role="search" style="margin-top: 7px;">
                    <img src="../img/search.svg" alt="" style="width: 27px; height: 27px;">
                    <div class="stick"></div>
                    <input class="input_s" type="search" placeholder="Пошук за назвою" aria-label="Search">
                </form>
            </ul>

            <a href="cart.php" class="d-flex" style="margin-right: 10px;">
                <img src="../img/ShoppingCart.svg" alt="" class="d-flex">
            </a>
            <a href="wishlist.php" class="d-flex" style="margin-right: 10px;">
                <img src="../img/love.svg" alt="" class="d-flex">
            </a>
            <?php

            if(isset($_SESSION['username'])) {
                $username = $_SESSION['username'];
                echo '<span class="name" style="margin-right: 10px;">' . $username . '</span>';
            }
            ?>
            <a class="d-flex" style="cursor: pointer;" data-bs-toggle="modal" data-bs-target="#authModal">
                <img src="../img/profile.svg" alt="" class="d-flex img_profile">
            </a>
        </div>

</nav>

<!-- Модальне вікно -->
<div class="modal fade" id="authModal" tabindex="-1" aria-labelledby="authModalLabel" aria-hidden="true" style="z-index: 10000000">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="authModalLabel">Аутентифікація клієнта</h5>
                <button type="button" class="btn-close" style="background-color: #BEBEBE" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="background-color: #F4F0F6">
                <div id="error-message" class="alert alert-danger" style="display: none;"></div>
                <form id="login-form" action="authorization.php" method="POST">
                    <div class="mb-3">
                        <label for="email" class="form-label">Електронна адреса:</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Пароль:</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 mb-2">Вхід</button>
                    <a href="google-oauth.php" class="google-btn w-100">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1200px-Google_%22G%22_logo.svg.png" alt="Google logo" width="20">
                        Увійти з Google
                    </a>
                </form>
                <form id="register-form" action="registration.php" method="POST" style="display: none;">
                    <div class="mb-3">
                        <label for="reg-email" class="form-label">Електронна пошта</label>
                        <input type="text" id="reg-email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="reg-username" class="form-label">Ім'я користувача:</label>
                        <input type="text" id="reg-username" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="reg-password" class="form-label">Пароль:</label>
                        <input type="password" id="reg-password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 mb-2">Зареєструватися</button>
                    <a href="google-oauth.php" class="google-btn w-100">
                        <img src="https://w7.pngwing.com/pngs/326/85/png-transparent-google-logo-google-text-trademark-logo-thumbnail.png" alt="Google logo" width="20">
                        Зареєструваися з Google
                    </a>
                </form>
            </div>
            <div class="modal-footer">
                <span id="toggle-auth" class="toggle-link">Зареєструватися</span>
            </div>
        </div>
    </div>
</div>
<!-- Повідомлення про успішну реєстрацію -->
<div id="success-message" class="alert alert-success" style="display: none;">
    Ви успішно зареєструвалися! Будь ласка увійдіть у аккаунт
</div>

<script>

    $(document).ready(function() {
        $('#toggle-auth').click(function() {
            if ($('#login-form').is(':visible')) {
                $('#login-form').hide();
                $('#register-form').show();
                $('#authModalLabel').text('Реєстрація клієнта');
                $(this).text('Увійти');
            } else {
                $('#register-form').hide();
                $('#login-form').show();
                $('#authModalLabel').text('Аутентифікація клієнта');
                $(this).text('Зареєструватися');
            }
        });

        // AJAX for login form
        $('#login-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: 'http://localhost/NyaCawa/php/authorization.php',
                data: $(this).serialize(),
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        window.location.href = 'index.php';
                    } else {
                        $('#error-message').text(response.message).show();
                    }
                },

            });
        });

        // AJAX for register form
        $('#register-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: 'http://localhost/NyaCawa/php/registration.php',
                data: $(this).serialize(),
                dataType: 'json',
                success: function(response) {
                    if (response.success) {
                        // Показати повідомлення про успішну реєстрацію
                        $('#success-message').show();
                        // Закрити модальне вікно
                        $('#authModal').modal('hide');
                        setTimeout(function() {
                            $('#success-message').hide();
                        }, 3000);
                    } else {
                        $('#error-message').text(response.message).show();
                    }
                },
                error: function() {
                    $('#error-message').text('Виникла помилка. Спробуйте ще раз.').show();
                }
            });
        });
    });

</script>
