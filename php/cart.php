<?php
session_start();

// Переконайтеся, що користувач автентифікований
if (!isset($_SESSION['user_id'])) {
    die("Користувач не автентифікований. Будь ласка, увійдіть.");
}

$user_id = $_SESSION['user_id'];

// Оновлення кошика перед відображенням
include('good/update_cart.php');

// Handle quantity updates from existing cart items
if ($_SERVER['REQUEST_METHOD'] == 'POST' && !empty($_POST)) {
    $action = $_POST['action'];

    if ($action == 'add' && isset($_POST['item_id']) && isset($_POST['image'])) {
        include('good/add_to_cart.php');
    } elseif ($action == 'remove') {
        include('good/remove_from_cart.php');
    } elseif ($action == 'clear') {
        include('good/clear_cart.php');
    } elseif ($action == 'increase') {
        include('good/plus_quantity.php');
    } elseif ($action == 'decrease') {
        include('good/minus_quantity.php');
    }
    // Include other action handlers similarly
    header("Location: " . $_SERVER['PHP_SELF']);
    exit;
}
include('header.php');
include('db.php');

// Clean up session cart if items are removed from the database
if (!empty($_SESSION['cart'])) {
    foreach (array_keys($_SESSION['cart']) as $item_id) {
        $sql = "SELECT * FROM products WHERE product_id = $item_id";
        $result = $conn->query($sql);
        if ($result->num_rows == 0) {
            // If product is not found in the database, remove it from the session cart
            unset($_SESSION['cart'][$item_id]);
        }
    }
}

?>
<main class="car_m">
    <div class="row">
        <h1 class="h1_text">Ваш кошик</h1>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-8" id="cart">
            <?php
            if (!empty($_SESSION['cart'])) {
                foreach ($_SESSION['cart'] as $item_id => $item) {
                    $sql = "SELECT name, prise FROM products WHERE product_id = $item_id";
                    $result = $conn->query($sql);

                    if ($result->num_rows > 0) {
                        $product = $result->fetch_assoc();
                        ?>
                        <div class="good_cart">
                            <img src="<?= $item['image'] ?>" alt="">
                            <div class="info">
                                <span class="figure_name"><?= $product['name'] ?></span>
                                <span class="price"><?= $product['prise'] ?> ₴</span>
                            </div>
                            <form method="post" action="">
                                <input type="hidden" name="item_id" value="<?= $item_id ?>">
                                <div class="amount">
                                    <button type="submit" name="action" value="decrease" class="minus">-</button>
                                    <div class="amount_num"><?= $item['quantity'] ?></div>
                                    <button type="submit" name="action" value="increase" class="plus">+</button>
                                </div>
                                <button type="submit" name="action" value="remove" class="btn_close"></button>
                            </form>
                        </div>
                        <?php
                    }
                }
            } else {
                echo "<p>Ваш кошик порожній</p>";
            }
            ?>
        </div>
        <div class="col-md-4">
            <div id="summary">
                <h1 class="h1_text" style="margin-left: 50px;">Суммарно</h1>
                <div class="cash_style">
                    Замовлення
                    <span id="cash">
                    <?php
                    $total = 0;
                    $sql = "SELECT cart.*, products.prise 
                            FROM cart 
                            JOIN products ON cart.product_id = products.product_id 
                            WHERE cart.user_id = $user_id";
                    $result = $conn->query($sql);
                    if ($result->num_rows > 0) {
                        while ($row = $result->fetch_assoc()) {
                            $total += $row['prise'] * $row['quantity'];
                        }
                    }
                    echo $total;
                    ?>
                </span>
                </div>
                <div class="cash_style">
                    <label>Знижка бонусами
                        <input type="checkbox" id="checkbox1" class=""></label>
                    <span id="sale">0%</span>
                </div>
                <div class="summ">
                    Разом
                    <span><?php echo $total; ?></span>
                </div>
                <button class="summ_btn1" style="margin-bottom: 20px;">Купити</button>
                <form method="post" action="">
                    <button type="submit" name="action" value="clear" class="summ_btn2">Очистити</button>
                </form>
            </div>
            <div id="payment" style="margin-top: 10px;">
                <div class="credit">
                    <img src="/img/credit-card.svg" alt="">Почка1
                    <br>
                    <img src="/img/credit-card.svg" alt="">Почка2
                    <br>
                    <img src="/img/credit-card.svg" alt="">Почка3
                </div>
            </div>
        </div>
    </div>
</main>

<?php include('footer.php'); ?>
