<?php
class ItemCart extends AProduct {
	public function __construct($idProduct,$libel,$quantity,$price,$tva,$type){
		parent::__construct($idProduct.$type,$idProduct,$libel,$quantity,$price,$tva,$type);
	}
}

class EShop {

	const NAME = 'EShop';
	private $content;
	private $total;
	private $quantity;
	private $totalHT;

	public static function getInstance() {
		//		if (!Session::exists(EShop::NAME))
		//			Session::set(EShop::NAME,new EShop());
		//		return Session::get(Eshop::NAME);
		if (!$_SESSION[EShop::NAME])
		$_SESSION[EShop::NAME]=new EShop();
		return $_SESSION[EShop::NAME];
	}


	private function __construct() {
		$this->content = array();
	}

	public function add(AProduct $product) {

		$pos = $this->getPosById($product->getId());
		if ($pos >= 0)
		$this->content[$pos]->add($product->getQuantity());
		else
		$this->content[] = $product;
		$this->actualize();
	}

	public function delOneProduct($id) {
		$pos = $this->getPosById($id);
		if ($pos == -1)
		throw new Exception("Product ($id) does not exists in the EShop");
		$this->content[$pos]->delOneProduct();
		if($this->content[$pos]->getQuantity()==0)
		$this->delete($id);
		$this->actualize();
	}
	public function setQuantity($id,$newQuantity){
		$pos = $this->getPosById($id);
		if ($pos == -1)
		throw new Exception("Product ($id) does not exists in the EShop");
		$this->content[$pos]->setQuantity($newQuantity);
		$this->actualize();
	}
	public function delete($id) {
		$pos = $this->getPosById($id);
		if ($pos == -1)
		throw new Exception("Product ($id) does not exists in the EShop");
		array_splice($this->content,$pos,1);
		$this->actualize();
	}

	public function clear() {
		$this->content = array();
		$this->actualize();
	}

	public function getTotalTTC() {
		return $this->total;
	}
	public function getTotalHT() {
		return $this->totalHT;
	}

	public function getQuantity() {
		return $this->quantity;
	}

	public function getPosById($id) {
		return $this->getPos('Id',$id);
	}
	//	public function getPosSingleName($singleName) {
	//		return $this->getPos('singleName',$singleName);
	//	}

	public function getProductById($id) {
		//        return $this->content[$this->getPos('Id',$id)]['product'];
		return $this->content[$this->getPos('Id',$id)];
	}
	//	public function getProductBySingleName($singleName) {
	//		//        return $this->content[$this->getPos('Name',$name)]['product'];
	//		return $this->content[$this->getPos('singleName',$singleName)];
	//	}
	public function listProduct() {
		return $this->content;
	}

	private function getPos($by,$value) {
		if (!in_array($by,array('Id','singleName')))
		throw new Exception('Unknown search method');
		$pos = -1;
		foreach ($this->content as $key => $product)
		if ($product->{'get'.$by}() == $value)
		$pos = $key;
		return $pos;
	}

	private function actualize() {
		$this->total = 0;
		$this->quantity = 0;
		$this->totalHT =0;
		foreach ($this->content as $product) {
			$this->total += $product->getPriceTTC() * $product->getQuantity();
			$this->quantity += $product->getQuantity();
			$this->totalHT += $product->getPriceHT() * $product->getQuantity() ;
		}
	}

}

abstract class AProduct {

	private $id;
	private $idProduct;
	private $libel;
	private $price;
	private $quantity;
	private $tva;
	private $type; // unité ou colis (U ou C)

	public function __construct($id,$idProduct,$libel,$quantity,$price,$tva,$type) {
		$this->id = $id;
		$this->idProduct = $idProduct;
		$this->libel = $libel;
		$this->price = $price;
		$this->quantity = $quantity;
		$this->tva = ($tva > 1) ? ($tva / 100 ) : $tva;
		$this->type = $type;
	}

	public function add($quantity = null) {
		// Modifier la quantité à l'ajout
		if($quantity!=null)
		$this->quantity +=$quantity;
		else
		$this->quantity += 1;
	}
	public function setQuantity($newQuantity){
		$this->quantity = $newQuantity;
	}
	public function delOneProduct() {
		$this->quantity -= 1;
	}
	public function getId() {
		return $this->id;
	}
	public function getPriceHT() {
		return $this->price;
	}
	public function getPriceTTC() {
		return $this->price * $this->tva +$this->price;
	}
	public function getTotalPriceHt(){
		return $this->price*$this->getQuantity();
	}
	public function getTotalPriceTtc(){
		return ($this->price * $this->tva +$this->price)*$this->getQuantity();
	}
	public function getQuantity() {
		return $this->quantity;
	}
	public function getLibel() {
		return $this->libel;
	}
	public function getIdProduct() {
		return $this->idProduct;
	}
	public function getTva() {
		return $this->tva;
	}
	public function getType(){
		return $this->type;
	}

}
?>