document.addEventListener('DOMContentLoaded', function() {
    const productList = document.querySelector('#product-list');
    const sortSelect = document.getElementById('sort-select');
    const categoryCheckboxes = document.querySelectorAll('#category-filter input[type="checkbox"]');
    const priceFilterBtn = document.getElementById('price-filter-btn');
    const priceMinInput = document.getElementById('price-min');
    const priceMaxInput = document.getElementById('price-max');

    let debounceTimer;

    function debounce(func, delay) {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(func, delay);
    }

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
                event.preventDefault();
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

    sortSelect.addEventListener('change', function() {
        debounce(updateProductList);
    });

    categoryCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            debounce(updateProductList);
        });
    });

    priceMinInput.addEventListener('input', function() {
        debounce(updateProductList);
    });

    priceMaxInput.addEventListener('input', function() {
        debounce(updateProductList);
    });
});
