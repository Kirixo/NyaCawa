<?php
include 'header.php';
include 'db.php'; // Підключення до бази даних

// Запит до бази даних для отримання товарів
$sql = "SELECT product_id, name, description, prise, image FROM products";
$result = $conn->query($sql);
?>

<div class="container-fluid">
    <main>
        <a href="ourapp.php" id="ad"><img src="../img/item 1.svg" alt="" class="ad"></a>
        <span class="montserrat rec">Рекомендації</span>
        <div class="row" style="padding: 50px;">
            <?php
            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()) {
                    ?>
                    <div class="col" >
                        <div class="good">
                            <div class="img-container">
                                <img class="img_good" src="<?= $row['image'] ?>" alt="">
                            </div>
                            <p class="figure_name"><?= $row['name'] ?></p>
                            <p><?= $row['description'] ?></p>
                            <p class="in_stock">В наявності</p>
                            <p class="price" style="display: inline;"><?= $row['prise'] ?> ₴</p>
                            <img class="love" src="../img/iloveparis.png" alt="">
                            <form method="post" action="cart.php" style="display: inline;">
                                <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>"> <!-- Унікальний ID товару -->
                                <input type="hidden" name="image" value="<?= $row['image'] ?>"> <!-- Шлях до зображення -->
                                <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                                    <img class="cart-icon" src="../img/ShoppingCart.svg" alt="Add to cart">
                                </button>
                            </form>
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
<script src="../js/cart.js"></script>
