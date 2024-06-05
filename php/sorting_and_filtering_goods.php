<?php
include 'db.php';
session_start();
$user_id = isset($_SESSION['user_id']) ? $_SESSION['user_id'] : null;

$sort = $_POST['sort'] ?? 'prise_asc';
$categories = $_POST['categories'] ?? '';
$price_min = $_POST['price_min'] ?? 0;
$price_max = $_POST['price_max'] ?? PHP_INT_MAX;

$sql = "SELECT p.product_id, p.name, p.description, p.prise, p.image";
if ($user_id !== null) {
    $sql .= ", (SELECT COUNT(*) FROM wishlist w WHERE w.product_id = p.product_id AND w.user_id = $user_id) AS in_wishlist";
} else {
    $sql .= ", 0 AS in_wishlist";
}
$sql .= " FROM products p";

// Dynamic WHERE clause
$whereClauses = [];
$bind_params = [];

if (!empty($categories)) {
    $categoriesArray = explode(',', $categories);
    $categoriesPlaceholders = implode(',', array_fill(0, count($categoriesArray), '?'));
    $sql .= " JOIN product_categories pc ON p.category_id = pc.category_id";
    $whereClauses[] = "pc.name IN ($categoriesPlaceholders)";
    foreach ($categoriesArray as $category) {
        $bind_params[] = $category;
    }
}

if ($price_min || $price_max != PHP_INT_MAX) {
    $whereClauses[] = "p.prise BETWEEN ? AND ?";
    $bind_params[] = $price_min;
    $bind_params[] = $price_max;
}

if (!empty($whereClauses)) {
    $sql .= " WHERE " . implode(' AND ', $whereClauses);
}

// Sorting
switch ($sort) {
    case 'prise_asc':
        $sql .= " ORDER BY p.prise ASC";
        break;
    case 'prise_desc':
        $sql .= " ORDER BY p.prise DESC";
        break;
    case 'name_asc':
        $sql .= " ORDER BY p.name ASC";
        break;
    case 'name_desc':
        $sql .= " ORDER BY p.name DESC";
        break;
    case 'date_desc':
        $sql .= " ORDER BY p.product_id DESC";
        break;
    default:
        $sql .= " ORDER BY p.prise ASC";
}

$stmt = $conn->prepare($sql);

// Bind parameters
if (!empty($bind_params)) {
    $types = str_repeat('s', count($bind_params));
    $stmt->bind_param($types, ...$bind_params);
}

$stmt->execute();
$result = $stmt->get_result();

$products = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $old_prise = $row['prise'] * 1.25;
        $in_wishlist = $row['in_wishlist'] > 0;

        $product = [
            'product_id' => $row['product_id'],
            'name' => $row['name'],
            'description' => $row['description'],
            'prise' => $row['prise'],
            'image' => $row['image'],
            'old_prise' => $old_prise,
            'in_wishlist' => $in_wishlist
        ];

        $products[] = $product;
    }
}

// Generate the HTML for products
foreach ($products as $product) {
    ?>
    <div class="good" style="width: 300px; height: 450px;">
        <a href="product.php?product_id=<?= $product['product_id'] ?>" class="cat_pr">
            <div class="img-container" style="height: 250px; overflow: hidden;">
                <img class="img_good" src="<?= $product['image'] ?>" alt="" style="width: 100%; height: 100%; object-fit: contain;">
            </div>
            <p class="figure_name" title="<?= $product['name'] ?>"><?= $product['name'] ?></p>
            <p class="description"><?= $product['description'] ?></p>
            <div class="cat_on_storage">
                <span>В наявності</span>
                <br>
                <span class="old_price"><?= number_format($product['old_prise'], 2) ?>₴</span>
            </div>
        </a>
        <div class="price">
            <p class="price" style="display: inline;"><?= $product['prise'] ?> ₴</p>
            <div>
                <button class="wishlist-btn" data-product-id="<?= $product['product_id'] ?>" style="background: none; border: none; padding: 0;">
                    <img class="love" src="../img/<?= $product['in_wishlist'] ? 'fav_love.svg' : 'love.svg' ?>" alt="Add to wishlist">
                </button>
                <form method="post" action="cart.php" style="display: inline;">
                    <input type="hidden" name="item_id" value="<?= $product['product_id'] ?>">
                    <input type="hidden" name="image" value="<?= $product['image'] ?>">
                    <input type="hidden" name="prise" value="<?= $product['prise'] ?>">
                    <button type="submit" name="action" value="add" style="background: none; border: none; padding: 0;">
                        <img class="cart-icon" src="../img/ShoppingCart.svg" alt="Add to cart">
                    </button>
                </form>
            </div>
        </div>
    </div>
    <?php
}
?>
