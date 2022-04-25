<?php
$fichierIdArticle = dirname(__FILE__).'/../data/dyn/id_article.txt';
$fichierIdCategorie = dirname(__FILE__).'/../data/dyn/id_categorie.txt';
$fichierIdImageArt = dirname(__FILE__).'/../data/dyn/id_image_art.txt';
define('FICHIER_ID_IMAGE_ART',dirname(__FILE__).'/../data/dyn/id_image_art.txt');
$fichierIdTiers = dirname(__FILE__).'/../data/dyn/id_tiers.txt';
$fichierIdCommandes = dirname(__FILE__).'/../data/dyn/id_commandes.txt';

$fichierIdTva = dirname(__FILE__).'/../data/fixe/id_tva.txt';

define('DROITS_FICHIER_GENERE',0666);

define('LOG_DEBUG',1);
define('LOG_INFOS',2);
define('LOG_WARNING',3);
define('LOG_ERROR',4);
$logLevel = LOG_DEBUG;

/*
 * Système de log utilisant Log4PHP => conflit avec la classe Logger de Prestashop.
 * En attendant qu'un des 2 framworks utilise les namesapces, on ne peut pas utiliser Log4PHP avec presta.
 * ==> En commentaire pour l'instant et remplacé par une fonction de log plus basique
 * 
 */
////////////////////////////////////////////////////////////////////////////////////////////////////////
$log_file = dirname(__FILE__)."/../log/bdg_presta.log";
// require_once('log4php/Logger.php');
// Logger::configure(dirname(__FILE__).'/log/log4php.properties');
// $logger = Logger::getLogger("main");

/*
 * Ajout de l'appender dynamique pour avoir un chemin de fichier dynamique
 * http://stackoverflow.com/questions/2658146/log4php-change-log-file-name-dynamically-in-log4php-properties
 */
// $appender = new LoggerAppenderRollingFile("MyAppender");
// $appender->setFile($log_file, true);
// $appender->setMaxBackupIndex(10);
// $appender->setMaxFileSize("100MB");
// $appenderlayout = new LoggerLayoutPattern();
////$pattern = '%d{Y-m-d H:i:s} [%p] %c: %m (at %F line %L)%n';
// $pattern = '%d{Y-m-d H:i:s} [%p] %c: %m %n';
// $appenderlayout->setConversionPattern($pattern);
// $appender->setLayout($appenderlayout);
// $appender->activateOptions();
// $logger->addAppender($appender);
////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * 
 * Ecrit la chaine de caractères $message dans un fichier
 * @param unknown_type $message
 * @see utilisé plutot lgr_log_info
 */
function lgr_log_private($message, $level = LOG_DEBUG){
	global $log_file, $logLevel;
	//if($level<=LOG_ERROR) { // pour l'instant on logge tout
		$fp = fopen($log_file, 'a');
		//$script_name = pathinfo($_SERVER['PHP_SELF'], PATHINFO_FILENAME);
		$time = date('Y-m-d H:i:s');
		//fwrite($fp, "$time ($script_name) $message\n");
		fwrite($fp, "$time *** $message\n");
		fclose($fp);
	//}
}

function lgr_var_dump($value) {
	echo '<pre>';
	var_dump($value);
	echo '</pre>';
}

function lgr_log_info($texte) {
//////////////////////////////////////////////////
//  //utilise le logger Log4PHP
// 	global $logger;
// 	$logger->info($texte);
//////////////////////////////////////////////////
	//$_SERVER["REMOTE_ADDR"]
	lgr_log_private($texte);
}

function enregistreId($fichier, $contenu) {
	
	$json =  json_encode($contenu);
	// creation du fichier
	//$fichier = $fichier;
	$file = fopen($fichier, 'w');
	fwrite($file, $json);
	fclose($file);
	changeDroitsFichier($fichier);
}

function changeDroitsFichier($file, $droits = DROITS_FICHIER_GENERE) {
	if(!chmod($file,$droits)) {
		lgr_log_info("Erreur pendant le changement des droits du fichier : ".$file);
	}
}

function chargeId($fichier) {
	return json_decode(file_get_contents($fichier),true);
}

function lgr_version_presta() {
	return _PS_VERSION_;
}

lgr_log_info("Version de prestashop : ".lgr_version_presta());
?>