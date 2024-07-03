<?php
include 'webPage.php';

$page = new WebPage("");

$content = <<<HTML
    <div id="catalog-goods-container">
        
    </div>
HTML;


$page->set_content($content);
$page->display_content();
