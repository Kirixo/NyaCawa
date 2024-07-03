<?php

class ContentHandler
{
    private $content;

    public function set_content($content)
    {
        // Определите разрешенный класс
        $allowedClass = 'allowed-class';

        // Проверка, содержит ли контент разрешенный класс
        if (preg_match('/class=["\'][^"\']*' . preg_quote($allowedClass, '/') . '[^"\']*["\']/', $content)) {
            $this->content = "<main>" . $content . "</main>";
        } else {
            throw new Exception('Content must contain the allowed class: ' . $allowedClass);
        }
    }

    public function get_content()
    {
        return $this->content;
    }
}

// Пример использования
try {
    $handler = new ContentHandler();
    $handler->set_content('<div class="allowed-class">This is content</div>');
    echo $handler->get_content();
} catch (Exception $e) {
    echo 'Error: ' . $e->getMessage();
}
