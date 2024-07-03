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
<script src="../js/update_catalog.js?v=<?php echo time();?>" ></script>
