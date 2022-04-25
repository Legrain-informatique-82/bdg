<?php
require_once '../configuration/configuration.php';
require_once '../const_lgr.php';
require_once '../class/system/ftp/ftp.class.php';

lgr_log_info("Début importation.");
//if($_POST['code'] == SECURITY_KEY ){
	$host = FTP_HOST;
	$user = FTP_USER;
	$password = FTP_PASSWORD;
	$code = SECURITY_KEY;
	//$url = WEBSITE_ADRESS.'/import/gestBdd.php';
	$url = WEBSITE_ADRESS.'/lgr/php/import/gestBdd.php';
	
	/*************************************************/
	$oFtp = new ftp(FTP_HOST, FTP_USER, FTP_PASSWORD);
	$oFtp->connexion();
	$oFtp->cd('json');
	$oFtp->download('json/catalogue_web.json', 'catalogue_web.json',FTP_BINARY);
	//$oFtp->ftp_sync('images', 'images',FTP_BINARY);
	$oFtp->cd('..');
	$oFtp->ftp_sync('images', 'images');
	unset($oFtp);
	require_once './gestBdd.php';
	/*************************************************/
	
	
	
	
//	exec("./treatment.sh '$host' '$user' '$password' '$code' '$url'");
//}
lgr_log_info("Fin importation.");
?>