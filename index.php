<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
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
						<a href="<?php echo $pillow_ref; ?>" class="image-block">
							<img src="<?php echo $pillow_img; ?>" alt="Подушки">
						</a>
						<div class="column-info">
							<a href="<?php echo $pillow_ref; ?>" class="category-title">Подушки</a>
						</div>
					</div>
					<div class="category-column">
						<a href="<?php echo $cup_ref; ?>" class="image-block">
    						<img src="<?php echo $cup_img; ?>" alt="Чашки">
						</a>
						<div class="column-info">
							<a href=<?php echo $cup_ref; ?> class="category-title">Чашки</a>
						</div>
					</div>
					<div class="category-column">
						<a href="<?php echo $figure_ref; ?>" class="image-block">
							<img src="<?php echo $figure_img; ?>" alt="Фігурки">
						</a>
						<div class="column-info">
							<a href="<?php echo $figure_ref; ?>" class="category-title">Фігурки</a>
						</div>
					</div>
					<div class="category-column">
						<a href="<?php echo $figure_ref; ?>" class="image-block">
							<img src="<?php echo $bag_img ?>" alt="Сумки">
						</a>
						<div class="column-info">
							<a href="<?php echo $figure_ref; ?>" class="category-title">Сумки</a>
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