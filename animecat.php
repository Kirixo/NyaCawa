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
						Жанри
					</div>
					<ul>
						<li><a href="<?php echo $pillow_ref; ?>">Екшн</a></li>
						<li><a href="<?php echo $cup_ref; ?>">Пригоди</a></li>
						<li><a href="<?php echo $figure_ref; ?>">Комедія</a></li>
						<li><a href="<?php echo $bag_ref; ?>">Драма</a></li>
						<li><a href="<?php echo $pillow_ref; ?>">Фентезі</a></li>
						<li><a href="<?php echo $cup_ref; ?>">Хорор</a></li>
						<li><a href="<?php echo $figure_ref; ?>">Романтика</a></li>
						<li><a href="<?php echo $bag_ref; ?>">Спорт</a></li>
					</ul>
				</nav>
			</aside>
			<div class="content-page categories-page">
				<div class="grid-container">
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/10/47347.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Атака титанів</a>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1015/138006.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Фрірен</a>
						
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1208/94745.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Сталевий алхімік</a>
							
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1935/127974.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Врата Штейна</a>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1053/129004.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Магічна революція принцеси</a>
						</div>
					</div>
					<div class="category-column">
						<a href="product.php?<?php ?>" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1815/110626.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="product.php?<?php ?>" class="category-title">Обіцяний неверленд</a>
							
						</div>
					</div>
					<div class="category-column">
						<a href="oshinoko.php" class="image-block">
							<img src="https://cdn.myanimelist.net/images/anime/1812/134736.jpg" alt="img">
						</a>
						<div class="column-info">
							<a href="oshinoko.php" class="category-title">Дитина айдола</a>
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