<!DOCTYPE html>
<html lang="uk">

<head>  
  <meta charset="UTF-8">
  <title>Мій сайт - Головна</title>
  <link rel="stylesheet" href="style.css" />

</head>

<body class="site">
  <header class="header">
    <h1>Мій сайт</h1>
    <nav class="nav">
      <ul>
        <a href="index.html" class="nav__link">Головна</a></li>
        <a href="about.html" class="nav__link">Про нас</a></li>
        <a href="contact.html" class="nav__link">Контакти</a></li>
      </ul>
    </nav>
    
  </header>
  <main class="main">
    <section>
      <h2 class="section-title">Ласкаво просимо на мій сайт!</h2>
      <div>
        <p id="Clock"></p>
      </div>
      <article class="article">
        <h3 class="section-title">Останні новини</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse euismod luctus purus, vitae varius augue
          fermentum sit amet. Aliquam sed elit lacinia, tincidunt sapien vel, convallis magna.</p>
      </article>
      <article class="article">
        <h3 class="section-title">Popular Posts</h3>
        <ul class="list">
          <li><a href="#">Post 1</a></li>
          <li><a href="#">Post 2</a></li>
          <li><a href="#">Post 3</a></li>
        </ul>
      </article>
    </section>
  </main>
  <aside class="menu">
    <section>
      <h4 class="section-title">Here's an image:</h4>
      <article>
        <img src="C:\Users\Lenovo\Pictures\Saved Pictures\Обои\Живые обои\Cars\gtr\preview.jpg" alt="Placeholder image"
          class="image">
      </article>
    </section>
    <section>
      <h4 class="section-title">Here's a video:</h4>
      <article>
        <video controls
          src="C:\Users\Lenovo\Pictures\Saved Pictures\Обои\Живые обои\Other\Хакеры ведут атаку\Хакеры ведут атаку на пентагон.mp4"
          class="video"></video>
      </article>
    </section>
    <section>
      <h4 class="section-title">Here's a link to YouTube:</h4>
      <article>
        <a href="https://www.youtube.com/watch?v=f5uIm2Nba_8&list=OLAK5uy_mJOU6agoOUTRSZVTF-nfB4dvacD7XI2tE"
          target="_blank" rel="noopener">
          <span>
            <p id='variableText'>"Це північ"</p>
          </span>
        </a>
      </article>
    </section>
  </aside>
  <footer class="footer">
    <p>&copy; 2023 Мій сайт. Усі права захищені.</p>
    <?php echo 'While this is going to be parsed.'; ?>
  </footer>
  <script src="main.js"></script>

</body>

</html>