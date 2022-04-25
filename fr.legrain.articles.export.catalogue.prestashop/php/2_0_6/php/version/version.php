<?php
require_once(dirname(__FILE__).'/../../../config/config.inc.php');
require '../configuration/configuration.php';
//require '../class/model/crud.php';
require '../lib/lib.system.php';
require '../class/system/ftp/ftp.class.php';

require_once '../const_lgr.php';

// Code de sécurité requis
//if($_POST['code'] == SECURITY_KEY ){

	/*************************************************************************
	 * 			Récupération de la version du module PHP
	 *************************************************************************/
	$filename = './version_module.json';
	$json = fread(fopen($filename,'r'),filesize($filename));
	//$$json =json_decode($json);
	$$json =( mb_detect_encoding($json, "UTF-8") =='UTF-8' )?json_decode($json):json_decode( utf8_encode($json) );
	$v = $$json->version;

	$jsonUsers = array();
	$jsonUsers['version_presta'] = lgr_version_presta();
	$jsonUsers['version_module_php'] = $v;
	
	/****************************************
	 * 			JSON + envoie FTP
	 ***************************************/
	$json =  json_encode($jsonUsers);
	
	//print_r($json);

	// creation du fichier
	$file_path = './json/version.json';
	$file = fopen($file_path, 'w');
	if(!fwrite($file, $json)) {
		lgr_log_info("erreur ecriture ".$file_path);
	}
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