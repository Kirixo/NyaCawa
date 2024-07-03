-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Час створення: Чрв 04 2024 р., 14:11
-- Версія сервера: 8.0.30
-- Версія PHP: 8.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `nyacawa`
--

-- --------------------------------------------------------

--
-- Структура таблиці `cart`
--

CREATE TABLE `cart` (
  `cart_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `product_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_ukrainian_ci;

--
-- Дамп даних таблиці `cart`
--

INSERT INTO `cart` (`cart_id`, `quantity`, `user_id`, `product_id`) VALUES
(19, 2, 3, 1),
(22, 3, 4, 1),
(29, 1, 4, 2),
(38, 2, 1, 5),
(39, 5, 1, 2),
(40, 4, 1, 1),
(41, 5, 1, 3),
(42, 3, 13, 1),
(45, 1, 13, 3);

-- --------------------------------------------------------

--
-- Структура таблиці `categories`
--

CREATE TABLE `categories` (
  `category_id` int NOT NULL,
  `name` varchar(40) CHARACTER SET cp1251 COLLATE cp1251_ukrainian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_ukrainian_ci;

--
-- Дамп даних таблиці `categories`
--

INSERT INTO `categories` (`category_id`, `name`) VALUES
(2, 'Дакімакури'),
(1, 'Фігурки');

-- --------------------------------------------------------

--
-- Структура таблиці `products`
--

CREATE TABLE `products` (
  `product_id` int NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` tinytext CHARACTER SET cp1251 COLLATE cp1251_ukrainian_ci,
  `prise` int NOT NULL,
  `category_id` int NOT NULL,
  `image` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_ukrainian_ci;

--
-- Дамп даних таблиці `products`
--

INSERT INTO `products` (`product_id`, `name`, `description`, `prise`, `category_id`, `image`) VALUES
(1, 'Фігурка Фрірен і Ферн', 'Персонажі з аніме “Sousou no Frieren', 15000, 1, '../img/friren-fern.jpg'),
(2, 'Yuji Itadori 1/7 Complete Figure', 'Персонаж з аніме \"Jujutsu Kaisen\"', 13400, 1, '../img/yuji-itadori.jpg'),
(3, 'Yuji Itadori VS ver. Complete Figure', 'Персонаж з аніме \"Jujutsu Kaisen\"', 14750, 1, '../img/yuji-itadori-vs-ver.jpg'),
(4, 'Suguru Geto', 'Персонаж з аніме \"Jujutsu Kaisen\"', 13400, 1, '../img/suguru-geto.jpg'),
(5, 'Asuka Shikinami', 'Персонаж з аніме \"Evangelion\"', 19400, 1, '../img/asuka.jpg');

-- --------------------------------------------------------

--
-- Структура таблиці `users`
--

CREATE TABLE `users` (
  `user_id` int NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(60) CHARACTER SET cp1251 COLLATE cp1251_ukrainian_ci NOT NULL,
  `password` varchar(32) CHARACTER SET cp1251 COLLATE cp1251_ukrainian_ci NOT NULL,
  `status` enum('Кадет','Ковбой','Мисливець за фігурками','Шиніґамі','Поціновувач чудового','Йонко','Накама') CHARACTER SET cp1251 COLLATE cp1251_ukrainian_ci NOT NULL,
  `bonus_points` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_ukrainian_ci;

--
-- Дамп даних таблиці `users`
--

INSERT INTO `users` (`user_id`, `name`, `email`, `password`, `status`, `bonus_points`) VALUES
(1, 'Cute_Moonlight', 'maksim.va2015@gmail.com', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(3, 'anton_molka', 'anton.molka@nure.ua', 'fe3d182206abf8d84ea3b4cd3f6cf7c4', 'Кадет', NULL),
(4, 'Максим Щербатюк', 'maksym.shcherbatiuk@nure.ua', '9961d3acde561bdfb45a4053f3798fc2', 'Кадет', NULL),
(7, 'denis.kostiuk', 'denis.kostiuk@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(8, 'ivan_kochevenko', 'ivan.kochevenko@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(9, 'dmytro_khyzhniak', 'dmytro.khyzhniak@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(11, 'mariia_kushnarenko', 'mariia.kushnarenko@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(12, 'denys_ivanishchev', 'denys.ivanishchev@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL),
(13, 'daniil_zabolotnyi', 'daniil.zabolotnyi@nure.ua', '1c0f8f3775f96433435fc05b30c3e252', 'Кадет', NULL);

-- --------------------------------------------------------

--
-- Структура таблиці `wishlist`
--

CREATE TABLE `wishlist` (
  `wishlist_id` int NOT NULL,
  `user_id` int NOT NULL,
  `product_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_ukrainian_ci;

--
-- Дамп даних таблиці `wishlist`
--

INSERT INTO `wishlist` (`wishlist_id`, `user_id`, `product_id`) VALUES
(54, 1, 3),
(56, 1, 4),
(64, 1, 1),
(68, 13, 2),
(69, 13, 3),
(70, 13, 4);

--
-- Індекси збережених таблиць
--

--
-- Індекси таблиці `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `product_id_idx` (`product_id`),
  ADD KEY `user_id_idx` (`user_id`);

--
-- Індекси таблиці `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`);

--
-- Індекси таблиці `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`),
  ADD KEY `category_id_idx` (`category_id`);

--
-- Індекси таблиці `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`);

--
-- Індекси таблиці `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`wishlist_id`),
  ADD KEY `product_id_idx` (`product_id`),
  ADD KEY `user_id_idx` (`user_id`);

--
-- AUTO_INCREMENT для збережених таблиць
--

--
-- AUTO_INCREMENT для таблиці `cart`
--
ALTER TABLE `cart`
  MODIFY `cart_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT для таблиці `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблиці `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблиці `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT для таблиці `wishlist`
--
ALTER TABLE `wishlist`
  MODIFY `wishlist_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `fk_cart_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `fk_cart_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Обмеження зовнішнього ключа таблиці `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Обмеження зовнішнього ключа таблиці `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `fk_wishlist_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `fk_wishlist_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
