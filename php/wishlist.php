<?php
session_start();

// Ensure the user is authenticated
if (!isset($_SESSION['user_id'])) {
    die("Користувач не автентифікований. Будь ласка, увійдіть.");
}
// Оновлення кошика перед відображенням
include('good/update_cart.php');
$user_id = $_SESSION['user_id'];

// Handle actions from the wishlist
if ($_SERVER['REQUEST_METHOD'] == 'POST' && !empty($_POST)) {
    $action = $_POST['action'];

    if ($action == 'add' && isset($_POST['item_id']) && isset($_POST['image'])) {
        include('good/add_to_wishlist.php');
    } elseif ($action == 'remove') {
        include('good/remove_from_wishlist.php');
    } elseif ($action == 'clear') {
        include('good/clear_wishlist.php');
    } elseif ($action == 'add_all') {
        include('good/from_wishlist_to_cart.php');
    }
    header("Location: " . $_SERVER['PHP_SELF']);
    exit;
}

include 'header.php';
include 'db.php';

// Fetch wishlist items from the database
$sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image,
        (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist
        FROM products p
        JOIN wishlist w ON p.product_id = w.product_id
        WHERE w.user_id = $user_id";
$result = $conn->query($sql);
?>
<main class="wish_m">
<div class="container-fluid">

        <h1 class="wish_h1">Ваше обране</h1>
        <div class="row">
            <div class="col-8">
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
                                        <p class="price" style="display: inline;"><?= number_format($row['prise'], 2) ?> ₴</p>
                                        <div>
                                            <form method="post" action="wishlist.php" style="display: inline;">
                                                <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>"> <!-- Унікальний ID товару -->
                                                <input type="hidden" name="image" value="<?= $row['image'] ?>"> <!-- Шлях до зображення -->
                                                <input type="hidden" name="prise" value="<?= $row['prise'] ?>"> <!-- Ціна товару -->
                                                <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                                                    <img class="love" src="../img/<?= $in_wishlist ? 'fav_love.svg' : 'love.svg' ?>" alt="Add to wishlist">
                                                </button>
                                            </form>
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
                        echo "Немає товарів в обраному.";
                    }
                    ?>
                </div>
            </div>
            <div class="col-4">
                <div id="wish">
                    <form method="post" action="">
                        <button type="submit" name="action" value="clear" class="wish_btn">Очистити</button>
                    </form>
                    <form method="post" action="">
                        <button type="submit" name="action" value="add_all" class="wish_btn">Додати все в кошик</button>
                    </form>
                </div>
                <div id="summary" style="margin-top: 20px;">
                    <h1 class="h1_text" style="margin-left: 50px;">Суммарно</h1>
                    <div class="cash_style">
                        Загальна
                        <span id="cash">
                            <?php
                            $total = 0;
                            if (!empty($_SESSION['wishlist'])) {
                                foreach ($_SESSION['wishlist'] as $item_id => $item) {
                                    $total += $item['prise'];
                                }
                            }
                            echo $total;
                            ?>
                        </span>
                    </div>
                    <div class="cash_style">
                        <label>Знижка
                            <input type="checkbox" id="checkbox1" class=""></label>
                        <span id="sale">0%</span>
                    </div>
                    <div class="summ">
                        Разом
                        <span><?php echo $total; ?></span>
                    </div>
                </div>
            </div>
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

