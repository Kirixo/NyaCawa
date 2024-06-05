<?php
session_start();
require_once('db.php');

if (!isset($_SESSION['is_admin']) || !$_SESSION['is_admin']) {
    header('Location: index.php');
    exit();
}

// Fetch users, products, and categories for management
$users = $conn->query("SELECT * FROM users");
$products = $conn->query("SELECT * FROM products");
$categories = $conn->query("SELECT * FROM product_categories");
?>

<!DOCTYPE html>
<html lang="ua">
<head>
    <meta charset="UTF-8">
    <title>Admin Management</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery.js"></script>
    <script src="../js/bootstrap.js"></script>
</head>
<body>
<header>
    <h1>Admin Management Page</h1>
</header>
<main class="container">
    <!-- Users Management -->
    <section>
        <h2>Users</h2>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">Add User</button>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Name</th>
                <th>Admin</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <?php while ($user = $users->fetch_assoc()): ?>
                <tr>
                    <td><?= $user['user_id'] ?></td>
                    <td><?= $user['email'] ?></td>
                    <td><?= $user['name'] ?></td>
                    <td><?= $user['is_admin'] ? 'Yes' : 'No' ?></td>
                    <td>
                        <button class="btn btn-warning edit-user" data-id="<?= $user['user_id'] ?>" data-bs-toggle="modal" data-bs-target="#editUserModal">Edit</button>
                        <button class="btn btn-danger delete-user" data-id="<?= $user['user_id'] ?>">Delete</button>
                    </td>
                </tr>
            <?php endwhile; ?>
            </tbody>
        </table>
    </section>

    <!-- Products Management -->
    <section>
        <h2>Products</h2>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addProductModal">Add Product</button>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Image</th>
                <th>Category ID</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <?php while ($product = $products->fetch_assoc()): ?>
                <tr>
                    <td><?= $product['product_id'] ?></td>
                    <td><?= $product['name'] ?></td>
                    <td><?= $product['description'] ?></td>
                    <td><?= $product['price'] ?></td>
                    <td><?= $product['image'] ?></td>
                    <td><?= $product['category_id'] ?></td>
                    <td>
                        <button class="btn btn-warning edit-product" data-id="<?= $product['product_id'] ?>" data-bs-toggle="modal" data-bs-target="#editProductModal">Edit</button>
                        <button class="btn btn-danger delete-product" data-id="<?= $product['product_id'] ?>">Delete</button>
                    </td>
                </tr>
            <?php endwhile; ?>
            </tbody>
        </table>
    </section>

    <!-- Categories Management -->
    <section>
        <h2>Categories</h2>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCategoryModal">Add Category</button>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <?php while ($category = $categories->fetch_assoc()): ?>
                <tr>
                    <td><?= $category['category_id'] ?></td>
                    <td><?= $category['name'] ?></td>
                    <td>
                        <button class="btn btn-warning edit-category" data-id="<?= $category['category_id'] ?>" data-bs-toggle="modal" data-bs-target="#editCategoryModal">Edit</button>
                        <button class="btn btn-danger delete-category" data-id="<?= $category['category_id'] ?>">Delete</button>
                    </td>
                </tr>
            <?php endwhile; ?>
            </tbody>
        </table>
    </section>

    <!-- Add User Modal -->
    <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addUserModalLabel">Add User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="add-user-form">
                        <div class="mb-3">
                            <label for="add-user-email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="add-user-email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-user-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="add-user-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-user-password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="add-user-password" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-user-admin" class="form-label">Admin</label>
                            <input type="checkbox" class="form-check-input" id="add-user-admin" name="is_admin">
                        </div>
                        <button type="submit" class="btn btn-primary">Add User</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit User Modal -->
    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">Edit User</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="edit-user-form">
                        <input type="hidden" id="edit-user-id" name="user_id">
                        <div class="mb-3">
                            <label for="edit-user-email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="edit-user-email" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-user-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="edit-user-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-user-admin" class="form-label">Admin</label>
                            <input type="checkbox" class="form-check-input" id="edit-user-admin" name="is_admin">
                        </div>
                        <button type="submit" class="btn btn-primary">Edit User</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Add Product Modal -->
    <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addProductModalLabel">Add Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="add-product-form">
                        <div class="mb-3">
                            <label for="add-product-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="add-product-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-product-description" class="form-label">Description</label>
                            <input type="text" class="form-control" id="add-product-description" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-product-price" class="form-label">Price</label>
                            <input type="number" class="form-control" id="add-product-price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-product-img" class="form-label">Image</label>
                            <input type="text" class="form-control" id="add-product-img" name="image" required>
                        </div>
                        <div class="mb-3">
                            <label for="add-product-category" class="form-label">Category ID</label>
                            <input type="number" class="form-control" id="add-product-category" name="category_id" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Product Modal -->
    <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editProductModalLabel">Edit Product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="edit-product-form">
                        <input type="hidden" id="edit-product-id" name="product_id">
                        <div class="mb-3">
                            <label for="edit-product-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="edit-product-name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-product-description" class="form-label">Description</label>
                            <input type="text" class="form-control" id="edit-product-description" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-product-price" class="form-label">Price</label>
                            <input type="number" class="form-control" id="edit-product-price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-product-img" class="form-label">Image</label>
                            <input type="text" class="form-control" id="edit-product-img" name="image" required>
                        </div>
                        <div class="mb-3">
                            <label for="edit-product-category" class="form-label">Category ID</label>
                            <input type="number" class="form-control" id="edit-product-category" name="category_id" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Edit Product</button>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <!-- Add Category Modal -->
    <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCategoryModalLabel">Add Category</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="add-category-form">
                        <div class="mb-3">
                            <label for="add-category-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="add-category-name" name="name" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Add Category</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Category Modal -->
    <div class="modal fade" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editCategoryModalLabel">Edit Category</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="edit-category-form">
                        <input type="hidden" id="edit-category-id" name="category_id">
                        <div class="mb-3">
                            <label for="edit-category-name" class="form-label">Name</label>
                            <input type="text" class="form-control" id="edit-category-name" name="name" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Edit Category</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</main>
<script>
    $(document).ready(function() {
        // Add User
        $('#add-user-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: '../php/crud/edit_user.php',
                data: $(this).serialize(),
                success: function(response) {
                    location.reload();
                },
                error: function() {
                    alert('Error adding user.');
                }
            });
        });

        // Edit User
        $('.edit-user').click(function() {
            var userId = $(this).data('id');
            $.ajax({
                type: 'GET',
                url: '../php/crud/edit_user.php',
                data: { user_id: userId },
                success: function(data) {
                    var user = JSON.parse(data);
                    $('#edit-user-id').val(user.user_id);
                    $('#edit-user-email').val(user.email);
                    $('#edit-user-name').val(user.name);
                    $('#edit-user-admin').prop('checked', user.is_admin);
                }
            });
        });

        $('#edit-user-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: '../php/crud/edit_user.php',
                data: $(this).serialize(),
                success: function(response) {
                    location.reload();
                },
                error: function() {
                    alert('Error editing user.');
                }
            });
        });

        // Delete User
        $('.delete-user').click(function() {
            var userId = $(this).data('id');
            if (confirm('Are you sure you want to delete this user?')) {
                $.ajax({
                    type: 'POST',
                    url: '../php/crud/delete_user.php',
                    data: { user_id: userId },
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert('Error deleting user.');
                    }
                });
            }
        });

        $(document).ready(function() {
            // Add Product
            $('#add-product-form').submit(function(event) {
                event.preventDefault();
                $.ajax({
                    type: 'POST',
                    url: '../php/crud/add_product.php',
                    data: $(this).serialize(),
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert('Error adding product.');
                    }
                });
            });

            // Edit Product
            $('.edit-product').click(function() {
                var productId = $(this).data('id');
                $.ajax({
                    type: 'GET',
                    url: '../php/crud/get_product.php',
                    data: { product_id: productId },
                    success: function(data) {
                        var product = JSON.parse(data);
                        $('#edit-product-id').val(product.product_id);
                        $('#edit-product-name').val(product.name);
                        $('#edit-product-description').val(product.description);
                        $('#edit-product-price').val(product.price);
                        $('#edit-product-img').val(product.image);
                        $('#edit-product-category').val(product.category_id);
                    }
                });
            });

            $('#edit-product-form').submit(function(event) {
                event.preventDefault();
                $.ajax({
                    type: 'POST',
                    url: '../php/crud/edit_product.php',
                    data: $(this).serialize(),
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert('Error updating product.');
                    }
                });
            });

            // Delete Product
            $('.delete-product').click(function() {
                if (confirm('Are you sure you want to delete this product?')) {
                    var productId = $(this).data('id');
                    $.ajax({
                        type: 'POST',
                        url: '../php/crud/delete_product.php',
                        data: { product_id: productId },
                        success: function(response) {
                            location.reload();
                        },
                        error: function() {
                            alert('Error deleting product.');
                        }
                    });
                }
            });
        });


        // Add Category
        $('#add-category-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: '../php/crud/add_category.php',
                data: $(this).serialize(),
                success: function(response) {
                    location.reload();
                },
                error: function() {
                    alert('Error adding category.');
                }
            });
        });

        // Edit Category
        $('.edit-category').click(function() {
            var categoryId = $(this).data('id');
            $.ajax({
                type: 'GET',
                url: '../php/crud/get_category.php',
                data: { category_id: categoryId },
                success: function(data) {
                    var category = JSON.parse(data);
                    $('#edit-category-id').val(category.category_id);
                    $('#edit-category-name').val(category.name);
                }
            });
        });

        $('#edit-category-form').submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: 'POST',
                url: '../php/crud/edit_category.php',
                data: $(this).serialize(),
                success: function(response) {
                    location.reload();
                },
                error: function() {
                    alert('Error editing category.');
                }
            });
        });

        // Delete Category
        $('.delete-category').click(function() {
            var categoryId = $(this).data('id');
            if (confirm('Are you sure you want to delete this category?')) {
                $.ajax({
                    type: 'POST',
                    url: '../php/crud/delete_category.php',
                    data: { category_id: categoryId },
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert('Error deleting category.');
                    }
                });
            }
        });
    });
</script>
</body>
</html>
