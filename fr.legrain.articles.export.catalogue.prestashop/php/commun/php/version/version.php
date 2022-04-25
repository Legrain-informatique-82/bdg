<?php
require_once(dirname(__FILE__).'/../../../config/config.inc.php');
require './ftp.class.php';
require '../configuration/configuration.php';
require_once '../const_lgr.php';

// Code de sécurité requis
//if($_POST['code'] == SECURITY_KEY ){

	/*************************************************************************
	 * 			Récupération de la version du module PHP
	 *************************************************************************/
	$jsonUsers = array();
	$jsonUsers['version_presta'] = _PS_VERSION_;
	$jsonUsers['version_module_php'] = '0';
	
	/****************************************
	 * 			JSON + envoie FTP
	 ***************************************/
	$json =  json_encode($jsonUsers);
	
	//print_r($json);

	// creation du fichier
	$file_path = './json/version.json';
	$file = fopen($file_path, 'w');
	echo $file_path;
	if(!fwrite($file, $json)) {
		echo "raté";
		lgr_log_info("erreur ecriture ".$file_path);
	}
	echo "ok";
	fclose($file);
	changeDroitsFichier($file_path);
	// envoie dudit fichier via ftp
	$oFtp = new ftp(FTP_HOST, FTP_USER, FTP_PASSWORD);
	$oFtp->connexion();
	$oFtp->mkdir('json');
	$oFtp->cd('json');
	$oFtp->upload('version.json', $file_path,FTP_BINARY);
	unset($oFtp);

//}
?>