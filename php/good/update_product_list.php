<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include 'db.php'; // Підключення до бази даних
session_start();
$user_id = isset($_SESSION['user_id']) ? $_SESSION['user_id'] : null;

// Отримання параметрів сортування і фільтрації з запиту
$sort = $_POST['sort'] ?? 'price_asc';
$categories = $_POST['categories'] ?? '';
$min_price = $_POST['min_price'] ?? 0;
$max_price = $_POST['max_price'] ?? PHP_INT_MAX;

// Створення запиту SQL для вибору товарів з урахуванням сортування і фільтрації
$sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image";
if ($user_id !== null) {
    $sql .= ", (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist";
} else {
    $sql .= ", 0 AS in_wishlist";
}
$sql .= " FROM products p";

$sql .= " FROM products p";

// Dynamic WHERE clause
$whereClauses = [];
$bind_params = [];

if (!empty($categories)) {
    $categoriesArray = explode(',', $categories);
    $categoriesPlaceholders = implode(',', array_fill(0, count($categoriesArray), '?'));
    $whereClauses[] = "p.product_id IN (SELECT pc.product_id FROM product_categories pc JOIN product_categories c ON pc.category_id = c.category_id AND c.name IN ($categoriesPlaceholders))";
    foreach ($categoriesArray as $category) {
        $bind_params[] = $category;
    }
}

// Додавання фільтра за ціною
$sql .= " AND p.prise BETWEEN $min_price AND $max_price";

// Додавання сортування
switch ($sort) {
    case 'price_asc':
        $sql .= " ORDER BY p.prise ASC";
        break;
    case 'price_desc':
        $sql .= " ORDER BY p.prise DESC";
        break;
    case 'name_asc':
        $sql .= " ORDER BY p.name ASC";
        break;
    case 'name_desc':
        $sql .= " ORDER BY p.name DESC";
        break;
    default:
        $sql .= " ORDER BY p.prise ASC";
}

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $old_price = $row['prise'] * 1.25;
        $in_wishlist = $row['in_wishlist'] > 0;
        ?>
        <div class="good" style="width: 300px; height: 450px;">
            <a href="product.php?product_id=<?= $row['product_id'] ?>" class="cat_pr">
                <div class="img-container" style="height: 250px; overflow: hidden;">
                    <img class="img_good" src="<?= $row['image'] ?>" alt="" style="width: 100%; height: 100%; object-fit: contain;">
                </div>
                <p class="figure_name" title="<?= $row['name'] ?>"><?= $row['name'] ?></p>
                <p class="description"><?= $row['description'] ?></p>
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
                            <input type="hidden" name="item_id" value="<?= $row['product_id'] ?>">
                            <input type="hidden" name="image" value="<?= $row['image'] ?>">
                            <input type="hidden" name="prise" value="<?= $row['prise'] ?>">
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
    echo 'Немає товарів для відображення.';
}
?>
