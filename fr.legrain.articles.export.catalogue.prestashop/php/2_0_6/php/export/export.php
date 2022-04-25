<?php
require_once(dirname(__FILE__).'/../../../config/config.inc.php');
require '../configuration/configuration.php';
//require '../class/model/crud.php';
require '../lib/lib.system.php';
require '../class/system/ftp/ftp.class.php';

require_once '../const_lgr.php';

// Code de sécurité requis
//if($_POST['code'] == SECURITY_KEY ){
	//$system =   System::load($pdo, 1);
	//$dateOfLastExport = $system->getDate_last_export();
	/************************
	 * 		USERS
	 ************************/
	// Liste de tous les membres qui se sont connectés depuis la dernière exportation.
	//$users =  User::loadAllConnectedAfterDate($pdo, $dateOfLastExport);
	$jsonUsers = array();
	$jsonUsers['tiers'] = array();
	$tmp = array( );
	foreach( CustomerCore::getCustomers() as $u ){
		$tmp['id'] = $u['id_customer'];
		$tmp['email'] = $u['email'];
		$tmp['name'] = $u['lastname'];
		$tmp['firstname'] = $u['firstname'];
		
		$customer = new CustomerCore($u['id_customer']);
		//if($customers != NULL && ($customers->getLastConnections())[0]>$dateOfLastExport) {
			
		//}
		$customerDefaultGroup = new GroupCore($customer->id_default_group);
		/*
		 * config/defines.inc.php
		 * define('_PS_PRICE_DISPLAY_PRECISION_', 2);
		 * define('PS_TAX_EXC', 1);
		 * define('PS_TAX_INC', 0);
		 */
		if($customerDefaultGroup->price_display_method == PS_TAX_EXC) {
			//client en affichage HT
			$tmp['ttc'] = 0;
		} else if($customerDefaultGroup->price_display_method == PS_TAX_INC){
			$tmp['ttc'] = 1;
		} else {
			$tmp['ttc'] = 1;
		}
		
		
// 		echo '<pre>';
// 		print_r($tmp);
// 		print_r($u);
// 		print_r($customer);
// 		echo '</pre>';

		//$tmp['phone-number'] = $customers->getPhone_number();
		list($year, $month, $day) = explode('-',$customer->birthday);
		$tmp['birthday-date-fr']= $customer->birthday=='0000-00-00'?null:$day.'/'.$month.'/'.$year;
		//$tmp['bdg-id'] = $u->getId_bdg()==0?null:$u->getId_bdg();
		$adress = array();
		foreach($customer->getAddresses((int)(Configuration::get('PS_LANG_DEFAULT'))) as $a){
			$tmpa['id-adress'] = $a['id_address'];
			$tmpa['name-adress'] = $a['alias'];
			$tmpa['address'] = $a['address1'].' '.$a['address2'];
			$tmpa['zip-code'] = $a['postcode'];
			$tmpa['city'] = $a['city'];
			array_push($adress, $tmpa );
		}
		$tmp['adress-fact'] = $adress;
		array_push( $jsonUsers['tiers'], $tmp );
	}
	/********************************************
	 * 			COMMANDES
	 ******************************************/
	$jsonUsers['commandes'] = array();
	
	// Liste des commandes qui ne ont id_bdg à 0.
	//$commands = Command::loadAllNotExportBdg( $pdo ) ;

	$map_commandes_bdg_presta = array();
	$map_commandes_bdg_presta = chargeId($fichierIdCommandes);
	$tmp = array();
	foreach(OrderCore::getOrdersIdByDate('1900-01-01', now) as $commandId){
	//foreach(OrderCore::getOrderIdsByStatus() as $commandId){
		//if(!array_search($commandId, $map_commandes_bdg_presta)) { 
		if($map_commandes_bdg_presta[$commandId]==null) {
			//la commande n'a pas deja été trasférée au bdg
			$command = new OrderCore($commandId);
			//echo '<pre>';
			//var_dump( $command );
			//echo '</pre>';

			$tmp['id-command'] = $command->id;
			$tmp['command-libel'] = "Commande boutique web : ".$command->id;

			$tmp['total-ht'] = $command->total_products_wt;
			$tmp['total-tva'] = $command->total_products - $command->total_products_wt;
			
			$tmp['carrier_tax_rate'] = $command->carrier_tax_rate;
			$tmp['total_shipping'] = $command->total_shipping;
			//$tmp['total_discounts'] = $command->total_discounts;

			$customer = new CustomerCore($command->id_customer);
			$tmp['user-name'] = $customer->lastname;
			$tmp['user-firstname'] = $customer->firstname;

			$billingAddress = new AddressCore($command->id_address_invoice);
			$tmp['billing-address'] = $billingAddress->address1.' '.$billingAddress->address2;
			$tmp['billing-city'] = $billingAddress->city;
			$tmp['billing-zip_code'] = $billingAddress->postcode;

			$deliveryAddress = new AddressCore($command->id_address_delivery);
			$tmp['delivery-address'] = $deliveryAddress->address1.' '.$deliveryAddress->address2;
			$tmp['delivery-city'] = $deliveryAddress->city;
			$tmp['delivery-zip_code'] = $deliveryAddress->postcode;

			$tmp['date-of-command'] = $command->date_add;
			list($year, $month, $day) = explode('-',date('Y-m-d',strtotime($command->date_add)));
			$tmp['date-of-command-fr']= $day.'/'.$month.'/'.$year;
			$tmp['id_user'] = $command->id_customer;
			$tmp['lines'] = array();
			$tmpa = array();

			// Liste des lignes
			$map_article_bdg_presta = array();
			$map_tva_bdg_presta = array();
			$map_article_bdg_presta = chargeId($fichierIdArticle);
			$map_tva_bdg_presta = chargeId($fichierIdTva);
			foreach ($command->getProductsDetail() as $line){
				$tmpa['quantity'] = $line['product_quantity'];
				//$tmpa['total-ht'] = $line['total_ht'];
				//$tmpa['total-tva'] = $line['total_tva'];
				//$tmpa['unit-product'] = $line['unit'];
				$tmpa['product-name'] = $line['product_name'];
				$tmpa['unit-price_ht'] = $line['product_price'];
				//$tmpa['id-TVA'] = array_search($line['tax_idTax'],$map_tva_bdg_presta);
				//echo $line['product_id'];
				//lgr_var_dump($map_article_bdg_presta);
				$tmpa['id-product'] = array_search($line['product_id'],$map_article_bdg_presta);
				array_push($tmp['lines'], $tmpa);
			}
			array_push($jsonUsers['commandes'], $tmp);
		}
	}
	
	/****************************************
	 * 			JSON + envoie FTP
	 ***************************************/
	$json =  json_encode($jsonUsers);

	// creation du fichier
	$file_path = './json/tiers_commandes.json';
	$file = fopen($file_path, 'w');
	fwrite($file, $json);
	fclose($file);
	changeDroitsFichier($file_path);
	// envoie dudit fichier via ftp
	$oFtp = new ftp(FTP_HOST, FTP_USER, FTP_PASSWORD);
	$oFtp->connexion();
	$oFtp->cd('json');
	$oFtp->upload('tiers_commandes.json', './json/tiers_commandes.json',FTP_BINARY);
	unset($oFtp);

	// Sauvegarde de la nouvelle date d'export( mettre à jour quand ok)
	//	$system->setDate_last_export(date('Y-m-d H:i:s'),true);
//}
?>