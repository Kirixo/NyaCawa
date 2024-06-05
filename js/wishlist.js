function addWishlistButtonListeners() {
    document.querySelectorAll('.wishlist-btn').forEach(button => {
        button.addEventListener('click', function (event) {
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