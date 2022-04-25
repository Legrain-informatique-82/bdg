<?php

// Debug mode
define('DEBUG_MOD',false);

$importdir = dirname(__FILE__)."/../import";
define('IMPORT_DIRECTORY',$importdir);

$ini_array = parse_ini_file("param.ini");
foreach( $ini_array as $key => $value ) {
	//echo "cle : ".$key." --- valeur : ".$value."<br>";
	//A l'écriture dans un fichier properties, Java protège le caractère ":" par un anti slash
	$value = str_replace("http\://","http://",$value);
	define($key,$value);
}

?>