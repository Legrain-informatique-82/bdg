<?php
require_once(dirname(__FILE__).'/../../../config/config.inc.php');
require_once '../configuration/configuration.php';
//require '../class/model/crud.php';
require_once '../lib/lib.system.php';
require_once '../class/system/ftp/ftp.class.php';

require_once '../const_lgr.php';

// Code de sécurité requis
//if($_POST['code'] == SECURITY_KEY ){

	$map_tiers_bdg_presta = array();
	$map_commandes_bdg_presta = array();
	$map_tiers_bdg_presta = chargeId($fichierIdTiers);
	$map_commandes_bdg_presta = chargeId($fichierIdCommandes);

	// Récupération du fichier ftp
	$oFtp = new ftp(FTP_HOST, FTP_USER, FTP_PASSWORD);
	$oFtp->connexion();
	$oFtp->cd('json');
	$oFtp->download('json/retour_tiers_commandes.json', 'retour_tiers_commandes.json',FTP_BINARY );
	unset($oFtp);
	$filename = IMPORT_DIRECTORY.'/json/retour_tiers_commandes.json';
	if(is_file($filename)){
		$json = fread(fopen($filename,'r'),filesize($filename));
		$$json =json_decode($json);
		$commandes = $$json->{'retour-commande'};
		$tiers = $$json->{'retour-tiers'};
		if($commandes!=null){
			foreach( $commandes as $item ){
				//if( Command::countWithId($pdo,$item->id) ==1){
				//	$object = Command::load($pdo, $item->id);
				//	$object->setId_bdg($item->{'id-bdg'} ,true);
				//}
				$map_commandes_bdg_presta[$item->id] = $item->{'id-bdg'};
			}
		}
		if($tiers!=null){
			foreach( $tiers as $item ){
				//if( User::countWithId($pdo,$item->id) ==1){
				//	$object = User::load($pdo, $item->id);
				//	$object->setId_bdg($item->{'id-bdg'} ,true);
				//}
				$map_tiers_bdg_presta[$item->id] = $item->{'id-bdg'};
			}
		}
	}
	
	enregistreId($fichierIdTiers, $map_tiers_bdg_presta);
	enregistreId($fichierIdCommandes, $map_commandes_bdg_presta);
//}
?>