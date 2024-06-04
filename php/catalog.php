<?php
include 'header.php'; // Підключення заголовка
include 'db.php'; // Підключення до бази даних

// Отримати поточного користувача
session_start();
$user_id = $_SESSION['user_id'];

// Запит до бази даних для отримання товарів з перевіркою наявності в обраному
$sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image, 
        (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist 
        FROM products p";
$result = $conn->query($sql);
?>

<div class="container-fluid">
    <main class="cat_m">
        <div class="row">
            <div class="col-3">
                <aside>
                    <!-- Можливий вміст бокової панелі -->
                </aside>
            </div>
            <div class="col-9">
                <div class="row">
                    <?php
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
                                        <p class="price" style="display: inline;"><?= $row['prise'] ?> ₴</p>
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
                </div>
            </div>
        </div>
    </main>
</div>

<?php include 'footer.php'; ?>

<script>
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
