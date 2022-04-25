<?php
/*
 * Si problème d'antislash ajouter automatiquement à l'enregistrrement
 * mettre "php_value magic_quotes_gpc off" dans un .htaccess
 */

/*
ini_set('display_errors',1);
require_once '../php/const_lgr.php';
*/

$filename = array();
//$filename[] = "dyn/id_article.txt";
//$filename[] = "dyn/id_categorie.txt";
$dyn  = glob("../../data/dyn/id_*");
$fixe = glob("../../data/fixe/id_*");
$filename = array_merge($filename, $dyn);
$filename = array_merge($filename, $fixe);

echo "<a href='?'>vide</a><br><br>";

for($i=0;$i<count($filename);$i++) {
	echo "<a href='?edit&file=$filename[$i]'>$filename[$i]</a><br>";
}

	if(isset($_GET['edit'])) { //on a bien cliqué sur un des liens précédent pour arriver ici
  		$f = $_GET[file]; //fichier à éditer
  		if(!isset($_POST['content'])) { //pas de contenu => on affiche celui du fichier
			$handle = fopen($f, "r");
			$contents = fread($handle, filesize($f));
		
			echo "<h1>".$f."</h1>";
			echo "<form method='post'>
     			<input type='hidden' name='edit'>
     			<input type='hidden' name='file' value='$_POST[file]'>
     			<textarea name='content' cols='120' rows='30'>";
			echo $contents;
 			echo "</textarea><br>
     			<input type='submit'>
    			</form>";
			fclose($handle);
		} else { //il y a un contenu, on l'enregistre
			$fp = fopen($f, 'w');
			fwrite($fp, $_POST['content']);
			fclose($fp);
		}
	}

?> 