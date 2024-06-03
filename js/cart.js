function addToCart(element) {
    // Отримати дані про товар з батьківського елемента
    var goodElement = element.closest('.good');
    var name = goodElement.querySelector('.about').innerText;
    var price = goodElement.querySelector('.price').innerText;

    // Створити новий блок товару
    var newCartItem = document.createElement('div');
    newCartItem.classList.add('good_cart');

    var img = document.createElement('img');
    img.src = '../img/statue.jpg';
    img.alt = '';

    var info = document.createElement('div');
    info.classList.add('info');

    var nameSpan = document.createElement('span');
    nameSpan.classList.add('name');
    nameSpan.innerText = name;

    var priceSpan = document.createElement('span');
    priceSpan.classList.add('price');
    priceSpan.innerText = price;

    var button = document.createElement('button');
    button.classList.add('btn_close');

    var amountDiv = document.createElement('div');
    amountDiv.classList.add('amount');

    var minusLink = document.createElement('a');
    minusLink.classList.add('minus');

    var amountNumDiv = document.createElement('div');
    amountNumDiv.classList.add('amount_num');
    amountNumDiv.innerText = '1';

    var plusLink = document.createElement('a');
    plusLink.classList.add('plus');

    // Додати елементи до нового блоку товару
    info.appendChild(nameSpan);
    info.appendChild(priceSpan);
    newCartItem.appendChild(img);
    newCartItem.appendChild(info);
    newCartItem.appendChild(button);
    newCartItem.appendChild(amountDiv);
    amountDiv.appendChild(minusLink);
    amountDiv.appendChild(amountNumDiv);
    amountDiv.appendChild(plusLink);

    // Отримати елемент, що представляє ваш кошик
    var cart = document.getElementById('cart');

    // Додати новий блок товару до кошика
    cart.appendChild(newCartItem);
    console.log("хуйня")
}
