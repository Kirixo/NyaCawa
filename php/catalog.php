<?php
include 'webPage.php';

$page = new WebPage("catalog");

$content = <<<HTML
    <
HTML;


$page->set_content($content);

$page->display_content();
