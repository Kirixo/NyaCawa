<?php
include 'header.php';
include 'db.php'; // Підключення до бази даних

session_start();
$user_id = isset($_SESSION['user_id']) ? $_SESSION['user_id'] : null;

// Запит до бази даних для отримання товарів
$sql = "SELECT p.product_id, p.name, p.description, p.price, p.image";
if ($user_id !== null) {
    $sql .= ", (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist";
} else {
    $sql .= ", 0 AS in_wishlist"; // Встановлюємо 0 для незареєстрованих користувачів
}
$sql .= " FROM products p";
$result = $conn->query($sql);

?>

<!-- Решта коду залишається без змін -->

<div class="container-fluid">
    <main>
        <a href="ourapp.php" id="ad"><img src="../img/item 1.svg" alt="" class="ad"></a>
        <span class="montserrat rec">Рекомендації</span>
        <div class="row" style="padding: 50px; margin-right: 0px">
            <?php
            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()) {
                    // Обчислення старої ціни
                    $old_price = $row['price'] * 1.25;
                    $in_wishlist = $row['in_wishlist'] > 0;
                    ?>
                    <div class="col">
                        <div class="good">
                            <a href="product.php?product_id=<?= $row['product_id'] ?>" class="cat_pr">
                            <div class="img-container">
                                <img class="img_good" src="<?= $row['image'] ?>" alt="">
                            </div>
                            <p class="figure_name" title="<?= $row['name'] ?>"><?= $row['name'] ?></p>
                                <p class="description"><?= $row['description'] ?></p>
                            <div class="in_stock">
                                <span>В наявності</span>
                                <br>
                                <span class="old_price"><?= number_format($old_price, 2) ?>₴</span>
                                <br>
                            </div>
                            <div class="price">
                                <p class="price" style="display: inline;"><?= $row['price'] ?> ₴</p>
                            </a>
                                <div>
                                    <button class="wishlist-btn" data-product-id="<?= $row['product_id'] ?>" style="background: none; border: none; padding: 0;">
                                        <img class="love" src="../img/<?= $in_wishlist ? 'fav_love.svg' : 'love.svg' ?>" alt="Add to wishlist">
                                    </button>
                                    <form method="post" action="cart.php" style="display: inline;">
                                        <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>"> <!-- Унікальний ID товару -->
                                        <input type="hidden" name="image" value="<?= $row['image'] ?>"> <!-- Шлях до зображення -->
                                        <input type="hidden" name="price" value="<?= $row['price'] ?>"> <!-- Ціна товару -->
                                        <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                                            <img class="cart-icon" src="../img/ShoppingCart.svg" alt="Add to cart">
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <?php
                }
            } else {
                echo "Немає товарів для відображення.";
            }
            ?>
        </div>
    </main>
</div>
<?php include 'footer.php'; ?>

<script>
    document.querySelectorAll('.wishlist-btn').forEach(button => {
        button.addEventListener('click', function() {
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
