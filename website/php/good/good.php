<?php

class good{

    private $id;
    private $name;
    private $price;
    private $inStock;
    private $image;

    public function __construct($id, $name='', $price='', $inStock = 0, $image=''){
         $this->id = $id;

         //TODO(СДЕЛАТЬ ПОДГРЗКУ ИЗ БД ТОВАРА)
         $this->name = $name != '' ? $name: "xd" ;
         $this->price = $price != '' ? $price: "xd" ;
         $this->inStock = $inStock != 0 ? $inStock: 0 ;
         $this->image = $image != '' ? $image: "xd" ;

    }

    public function getGood(){
        $class = "in_stock";
        $str = "В наявності";
        if($this->inStock > 0){
            $class = "out_of_stock";
            $str = "Не в наявності";
        }
        $stock = "<p class=". "{$class}". ">{$str}</p>";

        $content = <<<HTML
        <div class="col-xs-3">
            <div class="good">
                <img class="img_good" src="{$this->image}" alt="">
                <p class="about">{$this->name}</p>
                {$stock}
                <p class="price">{$this->price} ₴<img class="love" src="/img/iloveparis.png" alt=""></p>
            </div>
        </div>
HTML;
    }

    private function updateGood ()
    {
        //TODO(СДЕЛАТЬ ПОДГРЗКУ ИЗ БД ТОВАРА)
    }
}