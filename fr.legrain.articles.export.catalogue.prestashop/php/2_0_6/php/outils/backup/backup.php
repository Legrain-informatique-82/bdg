<?php
	//require '../../configuration/configuration.php';
	//require '../../class/system/ftp/ftp.class.php';
	//require_once '../../const_lgr.php';

	//if($_POST['code'] == SECURITY_KEY ){
    //$host = "localhost";
    //$user = "root";
    //$pass = "";
    //$db = "prestashop";

    $host = "localhost";
    $user = "lgr2";
    $pass = "legrain";
    $db = "dev2_pageweb";
    
    //$host = HOSTNAME_BDD_BOUTIQUE;
    //$user = LOGIN_BDD_BOUTIQUE;
    //$pass = PASSWORD_BDD_BOUTIQUE;
    //$db = NAME_BDD_BOUTIQUE;

    $date = date("Ymd-H\hi" );
    //$backup = $date."_".$db.".sql";
    $backup_bdd = $date."_".$db.".sql";
    $rep_dest_sql = "bdd";
    $rep_dest_archives = "archives";
    $backupModuleTAR = $date."_module_".$db.".tar.gz";
    $backupToutTAR = $date."_all_".$db.".tar.gz";
    
    $prefixe = "";
	if(isset($_POST['prefixe'])) {
		$prefixe = $_POST['prefixe'];
		$filename = glob($rep_dest_archives."/".$prefixe."*");
		$filename2 = glob($rep_dest_sql."/".$prefixe."*");
		
		for($i=0;$i<count($filename);$i++) {
			unlink($filename[$i]);
		}
		
		for($i=0;$i<count($filename2);$i++) {
			unlink($filename2[$i]);
		}
	}
	
    $command = "mysqldump --host=$host --user=$user --password=$pass $db > $rep_dest_sql/$prefixe"."_"."$backup_bdd";
    echo "Votre base est en cours de sauvegarde.......";
    system($command);
    echo "C'est fini. Vous pouvez recuperer la base par FTP";
    
    //$command ="tar cfz $rep_dest_archives/".$prefixe."_".$backupModuleTAR." ../../.. --exclude '$rep_dest_archives'";
    //system($command);
    

    //$command ="tar cfz $rep_dest_archives/".$prefixe."_".$backupToutTAR." ../../../.. --exclude '$rep_dest_archives'";
    $command ="tar cfz $rep_dest_archives/".$prefixe."_".$backupToutTAR." .. --exclude '$rep_dest_archives'";
    system($command);

    /*
    //Pas de transfert des sauvegardes sur le compte FTP pour l'instant, elles restent en local
    $oFtp = new ftp(FTP_HOST, FTP_USER, FTP_PASSWORD);
	$oFtp->connexion();
	$oFtp->mkdir('backup');
	$oFtp->cd('backup');
	$oFtp->upload($backupModuleTAR, $backupModuleTAR,FTP_BINARY);
	$oFtp->upload($backupToutTAR, $backupToutTAR,FTP_BINARY);
	unset($oFtp);
	
	unlink($backupModuleTAR);
	unlink($backupToutTAR);
	*/
    
    //}
?>
