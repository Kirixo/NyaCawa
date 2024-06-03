<?php
include 'header.php'; // Підключення заголовка
include 'db.php'; // Підключення до бази даних

// Запит до бази даних для отримання товарів
$sql = "SELECT product_id, name, description, price, image FROM products";
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
                            $old_price = $row['price'] * 1.25;
                            ?>
                            <div class="col">
                                <div class="cat">
                                    <a href="product.php?product_id=<?= $row['product_id'] ?>" class="cat_pr">
                                        <img src="<?= $row['image'] ?>" alt="" class="cat_img">
                                        <span class="cat_name"><?= $row['name'] ?></span>
                                        <br>
                                        <span class="cat_on_storage">В наявності</span>
                                        <br>
                                        <span class="old_price"><?= number_format($old_price, 2) ?>₴</span>
                                        <br>
                                        <span class="new_price"><?= number_format($row['price'], 2) ?>₴</span>
                                        <form method="post" action="cart.php" style="display: inline;">
                                            <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>"> <!-- Унікальний ID товару -->
                                            <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                                                <img src="../img/ShoppingCart.svg" alt="Add to cart">
                                            </button>
                                        </form>
                                        <img src="../img/love.svg" alt="">
                                    </a>
                                </div>
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
<script src="../js/cart.js"></script>
