<?php
include 'webPage.php';

$page = new WebPage("app");
$page->set_content("<div><h1>Добро пожаловать на наш сайт!</h1><p>Здесь вы найдете много интересного.</p></div>");
$page->display_content();
