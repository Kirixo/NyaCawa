<?php

class WebPage {
    protected $title;
    protected $content;
    private array $menuItems = [
        'catalog' => 'Каталог',
        'favorites' => 'Обране',
        'app' => 'Наш застосунок'
    ];

    public function __construct($title, $menuItems = '', $content = '') {
        $this->title = $title;
        if($menuItems !== '') {
            $this->menuItems = $menuItems;
        }
        $this->content = $content;
    }

    public function display_content()
    {
        echo '<!DOCTYPE html><html lang="ua"><head><meta charset="UTF-8">';
        echo <<<HTML
        <link rel="stylesheet" href="..\css\bootstrap.min.css">
        <link rel="stylesheet" href="..\css\bootstrap-theme.min.css">
        <link rel="stylesheet" href="..\css\style.css">
        <script src="js/jquery.js"></script></head><body>
        HTML;


        $this->displayTitle();
        $this->displayHeader();
        $this->displayNavBar($this->title, $this->menuItems);
        echo $this->content;
        $this->displayFooter();
    }

    protected function displayTitle() {
        echo "<title>{$this->title}</title>";
    }

    protected function displayHeader() {
        echo <<<HTML
            <header>
                <img src="/img/827438-Neon-Genesis-Evangelion-anime-city-Evangelion1.0.jpg" alt="" class="head_img">
            </header>
         HTML;
    }

    public function displayNavbar($currentPage, $menuItems): void
    {
        echo <<<HTML
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container" id="bs-menu">
                <div class="logo">
                    <h1><a href="main.php" class="without_link">NyaCawa</a></h1>
                </div>
                
                <ul class="nav navbar-nav">
        HTML;

        foreach ($menuItems as $page => $title) {
            $activeClass = $page === $currentPage ? 'class="active"' : '';
            echo "<li {$activeClass}><a id=\"{$page}\" href=\"{$page}.php\">{$title}</a></li>";
        }

        echo <<<HTML
                </ul>
                
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#" class=""><img class="shopcart" src="/img/cart.png" alt=""></a>
                    </li>
                    <li>
                        <a class="profile_btn" id="profile_btn"><img class="profile" src="/img/profile.png" alt=""></a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right" style="padding-right: 15px;">
                    <li>
                        <div class="name status_st" style="padding-bottom: 10px;">Имя</div>
                        <div class="status status_st">Статус</div>
                    </li>
                </ul>
            </div>
        </nav>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                function setActiveClass(event) {
                    var items = document.querySelectorAll('.nav.navbar-nav li a');
                    items.forEach(function(item) {
                        item.classList.remove('active');
                    });
                    event.target.classList.add('active');
                }

                var menuItems = document.querySelectorAll('.nav.navbar-nav li a');
                menuItems.forEach(function(item) {
                    item.addEventListener('click', setActiveClass);
                });
            });
        </script>
    HTML;
    }

    public function set_content($content)
    {

        $this->content = "<main>" . $content . "</main>";
    }

    public function displayFooter(): void
    {
        echo <<<HTML
        <footer>
            <div class="foot">
                "NyaCawa Inc." ©<br>
                Все права своровал таджик ^_^
            </div>
        </footer>
    HTML;
    }
}
?>



