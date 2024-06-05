<?php
include 'header.php'; // Підключення заголовка
include 'db.php'; // Підключення до бази даних

session_start();
$user_id = isset($_SESSION['user_id']) ? $_SESSION['user_id'] : null;
?>

<div class="container-fluid">
    <main class="cat_m">
        <div class="row">
            <div class="col-3">
                <aside>
                    <h4 class="sort_n">Сортування</h4>
                    <select id="sort-select">
                        <option value="date_desc">Дата додавання: новіші</option>
                        <option value="price_asc">Ціна: за зростанням</option>
                        <option value="price_desc">Ціна: за спаданням</option>
                        <option value="name_asc">Назва: за зростанням</option>
                        <option value="name_desc">Назва: за спаданням</option>
                    </select>


                    <hr class="sort_hr">

                    <h4 class="sort_n">Фільтри</h4>
                    <div class="category-filter">
                        <label class="sort_l">
                            <input type="checkbox" value="Фігурки">Фігурки
                        </label>
                        <br>
                        <label class="sort_l">
                            <input type="checkbox" value="Дакімакури">Дакімакури
                        </label>
                        <br>
                    </div>



                    <hr class="sort_hr">

                    <div class="sort_c">
                        <label>
                            <input type="number" id="price-min" placeholder="0">
                        </label>
                        <hr class="sort_hr2">
                        <label>
                            <input type="number" id="price-max" placeholder="10000">
                        </label>
                        <button class="sort_ok" id="price-filter-btn">Ок</button>
                    </div>
                </aside>
            </div>
            <div class="col-9">
                <div class="row" id="product-list">

                </div>
            </div>
        </div>
    </main>
</div>

<?php include 'footer.php'; ?>


<script>
    document.addEventListener('DOMContentLoaded', function() {
        const productList = document.querySelector('#product-list');
        const sortSelect = document.getElementById('sort-select');
        const categoryCheckboxes = document.querySelectorAll('.category-filter input[type="checkbox"]');
        const priceFilterBtn = document.getElementById('price-filter-btn');
        const priceMinInput = document.getElementById('price-min');
        const priceMaxInput = document.getElementById('price-max');

        function updateProductList() {
            const sortValue = sortSelect.value;
            const checkedCategories = Array.from(categoryCheckboxes)
                .filter(checkbox => checkbox.checked)
                .map(checkbox => checkbox.value)
                .join(',');

            const priceMin = priceMinInput.value || 0;
            const priceMax = priceMaxInput.value || Number.MAX_SAFE_INTEGER;

            fetch('sorting_and_filtering_goods.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `sort=${sortValue}&categories=${checkedCategories}&price_min=${priceMin}&price_max=${priceMax}`
            })
                .then(response => response.text())
                .then(html => {
                    productList.innerHTML = html;
                    addWishlistButtonListeners();
                })
                .catch(error => {
                    console.error('Помилка:', error);
                });
        }

        function addWishlistButtonListeners() {
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
        }

        sortSelect.addEventListener('change', updateProductList);
        categoryCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', updateProductList);
        });
        priceFilterBtn.addEventListener('click', updateProductList);

        // Initial load
        updateProductList();
    });


</script>
