<?php
/*
function generateMenuSousCat($pdo, $idCategoryMother,$array){
	$cat = Category::loadAllChildren($pdo,$idCategoryMother);
	foreach($cat as $$cat){
		$tmp = array();
		if($$cat->getParentCategory()->getIdCategory() == $idCategoryMother ){
				$tmp['url'] = $$cat->getUrl();
				$tmp['name'] = $$cat->getName();
				$tmp['id'] = $$cat->getIdCategory();
				$tmp['children'] = generateMenuSousCat($pdo,$$cat->getIdCategory(),array() );
				array_push($array, $tmp);
		}
	}
 return $array;
}

function generateUrlForThisCategory($pdo, $idcategory,$url){
	$cat = Category::load($pdo, $idcategory);
	$url = $cat->getUrl().'/'.$url; 
	if($cat->getParentCategory()!=null){
		$url= generateUrlForThisCategory($pdo, $cat->getParentCategory()->getidcategory(), $url);
	}
	return $url;
}
*/

?>