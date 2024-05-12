<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css?v=<?= time(); ?>" type="text/css">
    <title>Aniworld</title>
    <?php 
    	include_once 'include/variables.php';
    ?>
</head>
<body>
	<div id="main-page">
	<?php
		include_once 'include/header.php';
	?>
	<main class="content-block container clearfix">
		<div class="search-block-wrapper main-search clearfix">
			<form action="/search" method="get" class="search-block">
				<input type="text" class="search" placeholder="Пошук">
				<div class="search-submit">
					<span class="fal fa-search"></span>
					<input type="submit" value>
				</div> 
			</form>
		</div>
		<div class="content">
			
			<aside class="sidebar">
				<nav class="sidebar-block">
					<div class="block-header">
						Категорії
					</div>
					<ul>
						<li><a href="<?php echo $pillow_ref; ?>">Подушки</a></li>
						<li><a href="<?php echo $cup_ref; ?>">Чашки</a></li>
						<li><a href="<?php echo $figure_ref; ?>">Фігурки</a></li>
						<li><a href="<?php echo $bag_ref; ?>">Сумки</a></li>
					</ul>
				</nav>
			</aside>
			<div class="content-page categories-page">
				<div class="grid-container">
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/figure_00001_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Фігурка Люмін</a>
							<span>3450 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/figure_00002_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Фігурка Ху Тао</a>
							<span>3500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/figure_00003_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Фігурка Люмін нендроїд</a>
							<span>1500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00001_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Подушка Ке Цин 45х45</a>
							<span>500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00002_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Подушка Райден 45х45</a>
							<span>500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00003_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Подушка Ху Тао 45х45</a>
							<span>500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00004_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Дакімакура Аяка 150х50</a>
							<span>2000 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00005_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Дакімакура Лайла 150х50</a>
							<span>2000 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00006_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Подушка Еула 45х45</a>
							<span>500 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00007_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Дакімакура Ху Тао 150х50</a>
							<span>2000 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/pillow_00008_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Дакімакура Кірара 150х50</a>
							<span>3450 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00001_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Кокомі</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00002_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Нін Гуан</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00003_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Яе Міко</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00004_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Ке Цин</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00005_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Гань Юй</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/cup_00006_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Чашка Гань Юй & Сяо</a>
							<span>300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/bag_00001_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Рюкзак Ху Тао</a>
							<span>1800 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/bag_00002_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Рюкзак Нахіда</a>
							<span>1300 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/bag_00003_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Шопер Аль Хайтам</a>
							<span>250 грн</span>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="img/bag_00004_01.png" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Шопер Йоімія</a>
							<span>3450 грн</span>
						</div>
					</div>

					<gap></gap>
					<gap></gap>
					<gap></gap>
				</div>

			</div>

		</div>
		<div class="popular">
			
		</div>
	</main>
	<footer>
		
	</footer>
	</div>
</body>
</html>