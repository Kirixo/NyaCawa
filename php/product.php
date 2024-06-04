<?php
include 'header.php'; // Підключення заголовка
include 'db.php'; // Підключення до бази даних

session_start();
$user_id = $_SESSION['user_id'];

// Отримання product_id з URL
$product_id = isset($_GET['product_id']) ? (int)$_GET['product_id'] : 0;

// Запит до бази даних для отримання інформації про товар
$sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image,
        (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist
        FROM products p
        WHERE p.product_id = $product_id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $product = $result->fetch_assoc();
    $old_price = $product['prise'] * 1.25;
    $in_wishlist = $product['in_wishlist'] > 0;
} else {
    echo "Товар не знайдено.";
    exit;
}
?>

<div class="container-fluid">
    <main>
        <div class="container">
            <div class="product" style="margin-top: 50px;">
                <div class="row">
                    <div class="col"><img src="<?= $product['image'] ?>" alt="" class="product_img"></div>
                    <div class="col">
                        <span class="product_name"><?= $product['name'] ?></span>
                        <br>
                        <span class="old_price"><?= number_format($old_price, 2) ?>₴</span>
                        <br>
                        <span class="new_price"><?= number_format($product['prise'], 2) ?>₴</span>
                        <br>
                        <div class="share">
                            <hr><img src="../img/shared.svg" alt="">
                        </div>
                        <span class="on_storage">В наявності</span>
                        <br>
                        <div class="row">
                            <div class="col">
                                <form method="post" action="cart.php">
                                    <input type="hidden" name="item_id" value="<?= $product['product_id'] ?>"> <!-- Унікальний ID товару -->
                                    <input type="hidden" name="image" value="<?= $product['image'] ?>"> <!-- Шлях до зображення -->
                                    <input type="hidden" name="prise" value="<?= $product['prise'] ?>"> <!-- Ціна товару -->
                                    <button type="submit" name="action" value="add" class="in_cart">
                                        <img src="../img/ShoppingCart.svg" alt="">В кошик
                                    </button>
                                </form>
                            </div>
                            <div class="col">
                                <button class="in_fav" data-product-id="<?= $product['product_id'] ?>">
                                    <img class="heart-icon" src="../img/<?= $in_wishlist ? 'fav_love.svg' : 'love.svg' ?>" alt="Add to wishlist">В обране
                                </button>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="recom">
            <span class="rec" style="padding-left: 15px !important;">Рекомендації</span>
            <div class="row" style="margin-right: 0px">
                <?php
                // Запит до бази даних для отримання рекомендованих товарів, виключаючи поточний товар
                $sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image,
                        (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist
                        FROM products p
                        WHERE p.product_id != $product_id";
                $result = $conn->query($sql);

                if ($result->num_rows > 0) {
                    while ($row = $result->fetch_assoc()) {
                        // Обчислення старої ціни
                        $old_price = $row['prise'] * 1.25;
                        $in_wishlist = $row['in_wishlist'] > 0;
                        ?>
                        <div class="good" style="width: 300px; height: 450px;">
                            <a href="product.php?product_id=<?= $row['product_id'] ?>" class="cat_pr">
                                <div class="img-container" style="height: 250px; overflow: hidden;">
                                    <img class="img_good" src="<?= $row['image'] ?>" alt="" style="width: 100%; height: 100%; object-fit: contain;">
                                </div>
                                <p class="figure_name" title="<?= $row['name'] ?>"><?= $row['name'] ?></p>
                                <p><?= $row['description'] ?></p>
                                <div class="cat_on_storage">
                                    <span>В наявності</span>
                                    <br>
                                    <span class="old_price"><?= number_format($old_price, 2) ?>₴</span>
                                </div>
                                <div class="price">
                                    <p class="price" style="display: inline;"><?= number_format($row['prise'], 2) ?> ₴</p>
                                    <div>
                                        <button class="wishlist-btn" data-product-id="<?= $row['product_id'] ?>" style="background: none; border: none; padding: 0;">
                                            <img class="love" src="../img/<?= $in_wishlist ? 'fav_love.svg' : 'love.svg' ?>" alt="Add to wishlist">
                                        </button>
                                        <form method="post" action="cart.php" style="display: inline;">
                                            <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>"> <!-- Унікальний ID товару -->
                                            <input type="hidden" name="image" value="<?= $row['image'] ?>"> <!-- Шлях до зображення -->
                                            <input type="hidden" name="prise" value="<?= $row['prise'] ?>"> <!-- Ціна товару -->
                                            <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                                                <img class="cart-icon" src="../img/ShoppingCart.svg" alt="Add to cart">
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </a>
                        </div>

                        <?php
                    }
                } else {
                    echo "Немає товарів для відображення.";
                }
                ?>
                <!-- Можна додати більше рекомендацій тут -->
            </div>
        </div>
    </main>
</div>

<?php include 'footer.php'; ?>

<script>
    // Функція для зміни зображення сердечка при кліку
    function toggleHeartIcon(button) {
        const heartIcon = button.querySelector('.heart-icon');
        if (heartIcon.src.includes('fav_love.svg')) {
            heartIcon.src = '../img/love.svg';
        } else {
            heartIcon.src = '../img/fav_love.svg';
        }
    }

    document.querySelectorAll('.in_fav').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Запобігає перезавантаженню сторінки

            const productId = this.getAttribute('data-product-id');
            const img = this.querySelector('.heart-icon');
            const action = img.src.includes('fav_love.svg') ? 'remove_from_wishlist' : 'add_to_wishlist';

            fetch('ajax.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `action=${action}&product_id=${productId}`
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        toggleHeartIcon(button); // Зміна зображення сердечка
                    } else {
                        console.error('Помилка:', data.message);
                    }
                })
                .catch(error => console.error('Помилка:', error));
        });
    });

    // Додатковий обробник подій для кнопок у рекомендаціях
    document.querySelectorAll('.wishlist-btn').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Запобігає переходу по посиланню
            const productId = this.dataset.productId;
            const action = this.querySelector('img').src.includes('fav_love.svg') ? 'remove_from_wishlist' : 'add_to_wishlist';

            fetch('ajax.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `action=${action}&product_id=${productId}`
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        if (action === 'add_to_wishlist') {
                            this.querySelector('img').src = '../img/fav_love.svg';
                        } else {
                            this.querySelector('img').src = '../img/love.svg';
                        }
                    } else {
                        console.error('Помилка:', data.message);
                    }
                })
                .catch(error => console.error('Помилка:', error));
        });
    });
</script>


