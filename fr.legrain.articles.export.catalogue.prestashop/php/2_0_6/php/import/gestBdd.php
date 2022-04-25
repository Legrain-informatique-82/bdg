<?php
require_once(dirname(__FILE__).'/../../../config/config.inc.php');
require_once(dirname(__FILE__).'/../../../images.inc.php');
require_once '../configuration/configuration.php';
//require '../class/model/crud.php';
require_once '../lib/lib.system.php';

require_once '../const_lgr.php';

$utilise_declinaison = true;
$utilise_second_prix = true;
if(MODE_AFFICHAGE_PRIX=="declinaison") {
	$utilise_declinaison = true;
} else if(MODE_AFFICHAGE_PRIX=="special") {
	$utilise_declinaison = false;
} else {
	$utilise_second_prix = false;
}

/**
* Ajoute une image à un article
* @param int $idArticle
* @param string $nomImageArticle
* @param boolean $defaut
*/
function addImageArticle($idArticle, $nomImageArticle, $defaut) {
	$map_image_bdg_presta = array();
	$map_image_bdg_presta = chargeId(FICHIER_ID_IMAGE_ART);
	
	//$extension =  substr( $nomImageArticle, strpos($nomImageArticle,'.'));
	$extension =  '.jpg';

	//echo $fichierIdImageArt;
	if($map_image_bdg_presta[$nomImageArticle]) {
		//l'image existe deja, on recupére son id presta pour la modifier
		$image = new ImageCore($map_image_bdg_presta[$nomImageArticle]);
	} else {
		//nouvelle image
		$image = new ImageCore();
	}
	
	$image->id_product = $idArticle;
	$image->position = Image::getHighestPosition($idArticle) + 1;
	//$image->image_format = substr($extension,1);
	if(ImageCore::getCover($idArticle)==null) {
		$image->cover = true;
	} else {
		if($defaut) {
			$coverImage = ImageCore::getCover($idArticle);
			//lgr_var_dump($coverImage);
			$coverImage = new ImageCore($coverImage['id_image']);
			$coverImage->cover = false;
			$coverImage->save();
			$image->cover = $defaut;
		}
	}
	$image->legend = createMultiLangField('image produit '.$idArticle);
	
	//lgr_var_dump($image);

	$image->save();
	$map_image_bdg_presta[$nomImageArticle] = $image->id;

	$repImportImage = dirname(__FILE__).'/images/produits';
	$repImageProduct = _PS_PROD_IMG_DIR_;

	$cheminRepertoireImage = "";
	for ($i = 0; $i < strlen($image->id); $i++) {
		$s = strval($image->id);
		$cheminRepertoireImage = $cheminRepertoireImage.$s[$i].'/';

	}

	if(!file_exists($repImageProduct.$cheminRepertoireImage)) {
		mkdir($repImageProduct.$cheminRepertoireImage,0777,true);
	}

	$cheminImage = $repImageProduct.$cheminRepertoireImage.'/'.$image->id.$extension;
	copy($repImportImage.'/'.$nomImageArticle, $cheminImage);

	$cheminImageSansExtension = substr( $cheminImage, 0, strlen($cheminImage)-strlen($extension) );
	$imagesTypes = ImageTypeCore::getImagesTypes('products');
	foreach($imagesTypes as $type){
		//imageResize($cheminImage, $cheminImageSansExtension.'-'.$type['name'].$extension,$type['width'],$type['height'],$image->image_format);
		imageResize($cheminImage, $cheminImageSansExtension.'-'.$type['name'].$extension,$type['width'],$type['height']);
	}
	
	enregistreId(FICHIER_ID_IMAGE_ART, $map_image_bdg_presta);
}

/**
* Ajoute une image à une categorie
* @param int $idCategorie
* @param string $nomImageCatagorie
*/
function addImageCategorie($idCategorie, $nomImageCatagorie) {
	$repImportImage = dirname(__FILE__).'/images/categories';
	$repImageCategorie = _PS_CAT_IMG_DIR_;

	$extension =  substr( $nomImageCatagorie, strpos($nomImageCatagorie,'.'));
	$cheminImage = $repImageCategorie.$cheminRepertoireImage.'/'.$idCategorie.$extension;
	copy($repImportImage.'/'.$nomImageCatagorie, $cheminImage);

	$cheminImageSansExtension = substr( $cheminImage, 0, strlen($cheminImage)-strlen($extension) );
	$imagesTypes = ImageTypeCore::getImagesTypes('categories');
	foreach($imagesTypes as $type){
		imageResize($cheminImage, $cheminImageSansExtension.'-'.$type['name'].$extension,$type['width'],$type['height']);
	}
}

/**
 * 
 * Copier à partir de la classe AdminImport.php
 * infos : http://stackoverflow.com/questions/6385695/create-product-from-a-module-in-prestashop
 * @param String $field
 * @return multitype:unknown
 */
function createMultiLangField($field) {
	//$defaultLanguage = new Language((int)(Configuration::get('PS_LANG_DEFAULT')));
	$languages = Language::getLanguages(false);
	$res = array();
	foreach ($languages AS $lang)
	$res[$lang['id_lang']] = $field;
	return $res;
}

function getLanguageId() {
	$lang = 'fr';
	$id_lang = Language::getIdByIso($lang);
	return $id_lang;
}

function initialiseDeclinaison() {
	//Teste l'existance du groupe d'attributs pour un langage particulier
	$attrib = AttributeGroup::getAttributesGroups(getLanguageId());
	$trouve = false;
	foreach ($attrib AS $a) {
		if($a['name']=='Conditionnement') {
			$trouve = true;
		}
	}
	$retour = 0;
	if(!$trouve) {
		//Le groupe d'attributs n'exite pas, création d'un groupe d'attribut
		$attributeGroup = new AttributeGroup();
		$attributeGroup->name = createMultiLangField("Conditionnement");
		$attributeGroup->is_color_group = 0;
		$attributeGroup->public_name = createMultiLangField("Conditionnement");
		$attributeGroup->save();
		$retour = $attributeGroup->id;
	}
	return $retour;
	//return $attributeGroup->id;
}

function initialiseLabelFeature() {
	//Teste l'existance de la feature pour un langage particulier
	$attrib = FeatureCore::getFeatures(getLanguageId());
	$trouve = false;
	$retour = 0;
	foreach ($attrib AS $a) {
		if($a['name']=='Label') {
			$trouve = true;
			$retour = $a['id_feature'];
		}
	}
	if(!$trouve) {
		//La feature Label n'exite pas, création de la feature
		$feature = new FeatureCore();
		$feature->name = createMultiLangField('Label');
		$feature->save();
		$retour = $feature->id;
	}
	return $retour;
}

function addAttributeUnite($attributeGroupId) {
	$att = Attribute::getAttributes(getLanguageId());
	$trouve = false;
	$id = 0;
	foreach ($att AS $a) {
		if($a['name']=='Unite') {
			$trouve = true;
			$id = $a['id_attribute'];
		}
	}
	$retour = $id;
	if(!$trouve && $attributeGroupId) {
		$attributeUnite = new Attribute();
		$attributeUnite->id_attribute_group = $attributeGroupId;
		$attributeUnite->name = createMultiLangField("Unite");
		$attributeUnite->save();
		$retour = $attributeUnite->id;
	}
	return $retour;
}

function addAttributeColis($attributeGroupId) {
	$att = Attribute::getAttributes(getLanguageId());
	$trouve = false;
	$id = 0;
	foreach ($att AS $a) {
		if($a['name']=='Colis') {
			$trouve = true;
			$id = $a['id_attribute'];
		}
	}
	$retour = $id;
	if(!$trouve && $attributeGroupId) {
		$attributeColis = new Attribute();
		$attributeColis->id_attribute_group = $attributeGroupId;
		$attributeColis->name = createMultiLangField("Colis");
		$attributeColis->save();
		$retour = $attributeColis->id;
	}
	return $retour;
}

//if($_POST['code'] == SECURITY_KEY ){
	// Lit le fichier json
	$filename = IMPORT_DIRECTORY.'/json/catalogue_web.json';
	$json = fread(fopen($filename,'r'),filesize($filename));
	//$$json =json_decode($json);
	$$json =( mb_detect_encoding($json, "UTF-8") =='UTF-8' )?json_decode($json):json_decode( utf8_encode($json) );
	if(DEBUG_MOD){
		echo '<pre>';
		print_r($$json);
		echo '</pre>';
	}
	/*******************************************
	 * 			Declinaison
	 *******************************************/
	//$attributeUniteId = NULL;
	//$attributeColisId = NULL;
	if($utilise_second_prix) {
		if($utilise_declinaison) {
			$attributeGroupId = initialiseDeclinaison();

			//Le groupe d'attributs vient d'être créé, ajout des attributs ou récupération des Id
			$attributeUniteId = addAttributeUnite($attributeGroupId);
			$attributeColisId = addAttributeColis($attributeGroupId);

		}
	}
	
	/*******************************************
	 * 			Categories
	 *******************************************/
	lgr_log_info("Importation des catégories");
	$map_categorie_bdg_presta = array();
	//var_dump($map_categorie_bdg_presta);
	$map_categorie_bdg_presta = chargeId($fichierIdCategorie);
	//var_dump($map_categorie_bdg_presta);
	foreach($$json->categories  as $a ){
		
		// Ajout des catégories
		//var_dump($map_categorie_bdg_presta);
		//$category = new Category();
		if($map_categorie_bdg_presta[$a->id]) { 
			//la categorie existe deja, on recupére son id presta pour la modifier
			$category = new CategoryCore($map_categorie_bdg_presta[$a->id]);
		} else {
			//nouvelle categorie
			$category = new CategoryCore();
		}
		//$category = new CategoryCore();
		$category->id_parent = 1;
		$category->active = true;
		$category->description = createMultiLangField($a->description);
		$category->link_rewrite = createMultiLangField($a->{'url-rewriting'});
		$category->name = createMultiLangField($a->libelle);
		$category->save();
		$map_categorie_bdg_presta[$a->id] = $category->id;
		//ajout de l'image
		addImageCategorie($category->id,$a->image);
	}
	enregistreId($fichierIdCategorie, $map_categorie_bdg_presta);
	foreach($$json->categories  as $a ){
		// Ajout de la hierarchie
		$category = new CategoryCore($map_categorie_bdg_presta[$a->id]);
		$idParent = $map_categorie_bdg_presta[$a->{'id-categorie-mere'}]==null?1:$map_categorie_bdg_presta[$a->{'id-categorie-mere'}];
		$category->id_parent = $idParent;
		$category->save();
	}
	/**********************************************
	 *  				labels
	 *********************************************/
	lgr_log_info("Importation des labels");
	$featureLabelId = initialiseLabelFeature();
	$map_label_bdg_presta = array();
	$map_label_bdg_presta = chargeId($fichierIdLabel);
 	foreach($$json->labels as $a) {
 		if($map_label_bdg_presta[$a->id]) { 
			//la valeur existe deja, on recupére son id presta pour la modifier
			$featureValue = new FeatureValueCore($map_label_bdg_presta[$a->id]);
		} else {
			//nouvelle valeur
			$featureValue = new FeatureValueCore();
		}
		/***********************************************/
		$featureValue->id_feature= $featureLabelId;
		$featureValue->value = createMultiLangField($a->libelle);
		$featureValue->save();
		/***********************************************/
		$map_label_bdg_presta[$a->id] = $featureValue->id;
		
		/*
		obj.put("id",taLabelArticle.getIdLabelArticle());
		obj.put("code",taLabelArticle.getCodeLabelArticle());
		obj.put("libelle",taLabelArticle.getLibelleLabelArticle());
		obj.put("description",taLabelArticle.getDesciptionLabelArticle());
		obj.put("image",taLabelArticle.getNomImageLabelArticle());
		*/
 	}
 	enregistreId($fichierIdLabel, $map_label_bdg_presta);
	/*************************************************
	 * 				TVA
	 ************************************************/
 	//Le mapping entre l'id de TVA BDG et l'id de la règle de taxe BDG est faite en dur dans le fichier
 	//JSON correspondant, il faut pour l'instant le maintenir à la main.
 	lgr_log_info("Importation des taux de TVA");
 	foreach( $$json->tva as $a ) {
// 	Tax::createWithSpecificId($pdo, $a->id, $a->libelle, $a->taux / 100);
//  		$tax = new TaxCore();
//  		$tax->name = $a->libelle;
//  		$tax->rate = $a->taux / 100;
//  		$tax->save();
 	}
	/************************************************
	 * 			Produit
	 ***********************************************/
 	$map_article_bdg_presta = array();
 	$map_tva_bdg_presta = array();
 	$map_article_bdg_presta = chargeId($fichierIdArticle);
 	$map_tva_bdg_presta = chargeId($fichierIdTva);
 	lgr_log_info("Importation des articles");
	foreach($$json->articles as $a){
		if( $a->catalogue){
			if($map_article_bdg_presta[$a->id]) {
				//l'article existe deja, on recupére son id presta pour la modifier
				$article = new ProductCore($map_article_bdg_presta[$a->id]);
			} else {
				//nouvel article
				$article = new ProductCore();
			}
			$article->active = 1;
			
			$article->id_category_default = 1;
			$article->out_of_stock = 1;
			$article->quantity = 1;
			$article->reference = $a->code;

			if($map_tva_bdg_presta[$a->tva]) { 
				//il y a une correspondance entre l'id du code TVA BDG, et l'id d'une regle de taxe presta dans le fichier de mapping
				$article->id_tax_rules_group = $map_tva_bdg_presta[$a->tva];
			} else {
				//pas de correspondance => pas de taxe dans presta
				$article->id_tax_rules_group = 0;
			}

			$article->description = createMultiLangField(($a->{'desc-html'}==null)?'':$a->{'desc-html'});
			$article->name = createMultiLangField(($a->{'libelle-court'}==null)?'':$a->{'libelle-court'});
			$article->description_short = createMultiLangField(($a->{'libelle-long'}==null)?'':$a->{'libelle-long'});
			$article->link_rewrite = createMultiLangField(($a->{'url-rewriting'}==null)?'a':$a->{'url-rewriting'});
			
			//$article->active = ($a->{'catalogue'}==null)?'1':$a->{'catalogue'};
			//$article->new = ($a->{'nouveaute'}==null)?'0':$a->{'nouveaute'};
			
			//prix par defaut, il faut toujours un prix par defaut, même les déclinaison utilise ce prix comme base de calcul
			$article->unit_price = ($a->{'prix-ht'}==null)?'0.00':$a->{'prix-ht'};
			$article->price = ($a->{'prix-ht'}==null)?'0.00':$a->{'prix-ht'};
			
			$article->save();
			
			if($utilise_second_prix) {
				if(!$utilise_declinaison) {
					//prix par defaut
					//$article->unit_price = ($a->{'prix-ht'}==null)?'0.00':$a->{'prix-ht'};
					//$article->price = ($a->{'prix-ht'}==null)?'0.00':$a->{'prix-ht'};
				} else {
					$attributes = array();
					$attributes[]=$attributeUniteId;
					if($article->productAttributeExists($attributes)) {
						//Il y a deja un attribut identique pour cet articles, recherche de l'$id_product_attribute pour le mettre à jour
						$att = $article->getAttributeCombinaisons(getLanguageId());
						$trouve = false;
						$id = 0;
						foreach ($att AS $aaa) {
							if($aaa['id_attribute']==$attributeUniteId) {
								$trouve = true;
								$id = $aaa['id_product_attribute'];
							}
						}
						if($trouve) {
							$article->updateProductAttribute($id, $a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0);
							$article->addAttributeCombinaison($id,$attributes);
						}
						/////////////////////
						$article->deleteDefaultAttributes();
						$article->setDefaultAttribute($id);
						/////////////////////
					} else {
						$id_product_attribute = $article->addProductAttribute(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
						$article->addAttributeCombinaison($id_product_attribute,$attributes);
						//$id_product_attribute = $article->addCombinationEntity($a->{'prix-ht'},0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
						
						/////////////////////
						$article->deleteDefaultAttributes();
						$article->setDefaultAttribute($id_product_attribute);
						/////////////////////
					}
					$article->save();
				}
			}
			
			$article->condition = 'new';
			
			$article->category=array(1);
			$article->addToCategories(array(1));
			$article->updateCategories($article->category, true);
			
			/*
			$article->weight = $a->{'poids-colis'};
			$article->height = $a->{'hauteur-colis'};
			$article->width = $a->{'longueur-colis'};
			$article->depth = $a->{'largeur-colis'};
			*/
				
			$article->save();
			$map_article_bdg_presta[$a->id] = $article->id;
			
			$article->category=array(1);
			$article->addToCategories(array(1));
			$article->updateCategories($article->category, true);
			$article->save();
			
			if($utilise_second_prix) {
				//reduction prix au colis
				if(!$utilise_declinaison) {
					//création d'un prix spécial pour gérer la réduction sur les colis
					if($a->{'prix-ht-colis'}) {
						//suppression des anciens prix, dans l'idéal il faudrait pourvoir faire un test
						SpecificPriceCore::deleteByProductId($article->id);

						$specificPrice = new SpecificPriceCore();
						$specificPrice->from_quantity = $a->{'qte-colis'};
						$specificPrice->price = $a->{'prix-ht-colis'};
						$specificPrice->reduction = 0;
						$specificPrice->reduction_type = 'amount';
						$specificPrice->from = '0000-00-00';
						$specificPrice->to = '0000-00-00';

						$specificPrice->id_product = $article->id;
						$specificPrice->id_shop = 0;
						$specificPrice->id_currency = 0;
						$specificPrice->id_country = 0;
						$specificPrice->id_group = 0;
						$specificPrice->save();
					}
				} else {
					//création d'une déclinaison pour gérer la réduction sur les colis
					//$id_product_attribute = $article->addProductAttribute($a->{'prix-ht-colis'}-$a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0);
					//$id_product_attribute = $article->addCombinationEntity($a->{'prix-ht-colis'},$a->{'prix-ht-colis'}-$a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0);
					if($a->{'prix-ht-colis'}) {
						$attributes = array();
						$attributes[] = $attributeColisId;
						if($article->productAttributeExists($attributes)) {
							//Il y a deja un attribut identique pour cet articles, recherche de l'$id_product_attribute pour le mettre à jour
							$att = $article->getAttributeCombinaisons(getLanguageId());
							$trouve = false;
							$id = 0;
							foreach ($att AS $aa) {
								if($aa['id_attribute']==$attributeColisId) {
									$trouve = true;
									$id = $aa['id_product_attribute'];
								}
							}
							if($trouve) {
								$article->updateProductAttribute($id, $a->{'prix-ht-colis'},$a->{'prix-ht-colis'}-$a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0);
								//$article->updateProductAttribute($id,$a->{'prix-ht-colis'}, $a->{'prix-ht-colis'}, 0, $a->{'prix-ht-colis'}-$a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0);
								$article->addAttributeCombinaison($id,$attributes);
							}
						} else {
							//$id_product_attribute = $article->addProductAttribute(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
							//$id_product_attribute = $article->addCombinationEntity($a->{'prix-ht-colis'},$a->{'prix-ht-colis'}-$a->{'prix-ht'},0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
							$id_product_attribute = $article->addProductAttribute($a->{'prix-ht-colis'}-$a->{'prix-ht'}, 0, 0, 0, 0, 0, 0, 0, 0, 0);
							$article->addAttributeCombinaison($id_product_attribute,$attributes);
						}
						$article->save();
					}
				}
			}
	
			if($a->promo) {
				//$unit_price_reduced = ($a->promo==null)?'0.00':$a->promo;
				//$package_price_reduced= ($a->{'unit_price_reduced'}==null)?'0.00':$a->{'unit_price_reduced'};
				//$article->
			}
			
			// On ajoute les images associés
			foreach($a->{'images-article'} as $b){
				// Ajout des images	
				addImageArticle((int)$article->id,$b->image,$b->defaut);
			}
			
			// On boucle sur les catégories (du json) pour ajouter les relations
			$map_categorie_bdg_presta = array();
			$map_categorie_bdg_presta = chargeId($fichierIdCategorie);
			$categoriesArticle = array(1);
			foreach($a->{'categories-article'} as $b){
				if($map_categorie_bdg_presta[$b->id]) {
					$categoriesArticle[] = $map_categorie_bdg_presta[$b->id];
				}

				$article->category=$categoriesArticle;
				$article->addToCategories($categoriesArticle);
				$article->updateCategories($article->category, true);
				$article->save();
			}
			
			//On boucle sur les labels (du json) pour ajouter les relations
			$map_label_bdg_presta = array();
			$map_label_bdg_presta = chargeId($fichierIdLabel);
			$value = "";
			$premier = true;
			foreach($a->{'labels-article'} as $b){
				//ajout des labels
 				//if($map_label_bdg_presta[$b->id]) { 
 					//ajout de valeur prédéfinis pour la features
					//$article->addFeaturesToDB($featureLabelId, $map_label_bdg_presta[$b->id]);
				//}
				if(!$premier) {
					$value = $value.",";
				} else {
					$premier = false;
				}
				$value = $value.$b->libelle;
			}
			//pour l'instant, le labels sont la seules features des articles provonant de bdg, on peu donc tout supprimer
			//et tout recréer pour faire une MAJ, attention s'il y a d'autres features plus tard
			$article->deleteFeatures();
			$idValue = $article->addFeaturesToDB($featureLabelId, 0, 1);
			$article->addFeaturesCustomToDB($idValue, getLanguageId(), $value);
			$article->save();
			
		} else {
			//l'article ne doit plus être dans le catalogue
			if($map_article_bdg_presta[$a->id]) {
				//l'article existe deja, on recupére son id presta pour la modifier
				$article = new ProductCore($map_article_bdg_presta[$a->id]);
				
				$article->active = 0;
				$article->save();
			}
		}
	}
	enregistreId($fichierIdArticle, $map_article_bdg_presta);
	
//}
?>
