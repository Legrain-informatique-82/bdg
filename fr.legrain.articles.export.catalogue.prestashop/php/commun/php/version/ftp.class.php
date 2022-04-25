<?php
/*********************************************************************************************************************************
	**
	**		Classe FTP
	**
	**		Créée par XwZ
	**		E-mail : webmaster[@]htmlearn[dot]net
	**		Créé le : 15-08-2007
	**		Dernière mise à jour : 25-05-2008
	**
	**		La classe contient :
	**			- Les constructeurs et destructeurs :
	**				__construct ( entrée $host <string>, entrée $user <string>, entrée $pass <string>, entrée $port <integer> )
	**					Initialise les valeurs utilisées pour se connecter au serveur
	**
	**				__destruct ( void )
	**					Ferme la connexion au serveur ftp
	**
	**			- Les methodes de bases :
	**				connexion ( )
	**					Créer une connexion au serveur ftp, et renvois :
	**					- 1 si la connexion au serveur à échoué
	**					- 2 si l'identification à échoué
	**					- 3 si tout est ok
	**
	**				pwd
	**					retourne le chemin du repertoire courant
	**
	**				ls
	**					retourne un tableau avec la liste des fichiers présents dans le dossier courant
	**
	**				upload ( entrée fichier_distant <string>, entrée fichier_local <string>, entrée mode <string> )
	**					Upload le _fichier_local_ dans le répertoire courant sous le nom _fichier_distant_ avec le _mode_ choisi,
	**					_mode_ ne peut avoir que ces deux valeur : FTP_ASCII et FTP_BINARY
	**
	**				download ( entrée fichier_local <string>, entrée fichier_distant <string>, entrée mode <constante> )
	**					Download le _fichier_distant_ dans le répertoir courant sous le nom _fichier_local_ avec le _mode_ choisi
	**					_mode_ ne peut avoir que ces deux valeur : FTP_ASCII et FTP_BINARY
	**
	**				chmod ( entrée fichier <string>, entrée mode <string> )
	**					Change les droits d'accès du _fichier_ pour les droits indiqué dans _mode_
	**
	**				mkdir ( entrée dossier1 <string>,  entrée dossier2 <string>,  entrée dossier3 <string>, ... )
	**					Créé des répertoires dans le répertoire courant sous les noms de _dossier1_, _dossier2_, _dossier3_, ...
	**
	**				rm ( entrée fichier <string> )
	**					Supprime le _fichier_ du répertoire courant
	**
	**				rmdir ( entrée dossier <string> )
	**					Supprime le _dossier_ du répertoire courant
	**
	**				set_mode ( entrée mode <constante> )
	**					Définit le mode de transmission par défaut
	**					_mode_ ne peut avoir que ces deux valeur : FTP_ASCII et FTP_BINARY
	**
	*********************************************************************************************************************************/


class ftp {
    private $host ;
    private $user ;
    private $pass ;
    private $port ;
    private $flux_connexion ;
    private $flux_identification ;
    private $mode_transmission ;

    function __construct ( $host, $user, $pass, $port = 21 ) {
        $this -> host = $host ;
        $this -> user = $user ;
        $this -> pass = $pass ;
        $this -> port = $port ;
    }

    function __destruct ( ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            ftp_close ( $this -> flux_connexion ) ;
        }
    }

    function connexion () {
        $this -> flux_connexion = ftp_connect ( $this -> host, $this -> port ) ; // or die ( "erreur de connexion" ) ;

        if ( !$this -> flux_connexion === true ) {
            return 1 ;
        }
        else {
            $this -> flux_identification = ftp_login ( $this -> flux_connexion, $this -> user, $this -> pass ) ;
            if ( !$this -> flux_identification === true ) {
                return 2 ;
            }
            else {
                return 3 ;
            }
        }
    }

    function pwd () {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_pwd ( $this -> flux_connexion ) ;
        }
        else {
            return false ;
        }
    }

    function cd ( $repertoire ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_chdir ( $this -> flux_connexion, $repertoire ) ;
        }
        else {
            return false ;
        }
    }

    function ls ( $dossier = '.' ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            $sortie = array ( ) ;
            $i = 0 ;
            $tab_ls = ftp_nlist ( $this -> flux_connexion, $dossier ) ;
            if ( $tab_ls !== FALSE ) {
                foreach ( $tab_ls AS $id => $fichier ) {
                    if ( $fichier != '.' AND $fichier != '..' ) {
                        $sortie [ $i ] = $fichier ;
                        $i++;
                    }
                }
                return $sortie ;
            } else {
                return false ;
            }
        } else {
            return false ;
        }
    }

    function upload($fichier_distant, $fichier_local, $mode = '') {
        if ( true === empty ( $mode ) ) {
            $mode = $this -> mode_transmission ;
        }

        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_put ( $this -> flux_connexion, $fichier_distant, $fichier_local, $mode ) ;
        }
        else {
            return false ;
        }
    }

    function download ( $fichier_local, $fichier_distant, $mode = '' ) {
        if ( true === empty ( $mode ) ) {
            $mode = $this -> mode_transmission ;
        }

        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_get ( $this -> flux_connexion, $fichier_local, $fichier_distant, $mode ) ;
        }
        else {
            return false ;
        }
    }

    function chmod ( $fichier, $mode ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_chmod ( $this -> flux_connexion, $mode, $fichier ) ;
        }
        else {
            return false ;
        }
    }

    function mkdir() {
        $dossiers = func_get_args();
        $result = true;
        if(!$this -> flux_connexion === false && $this -> flux_identification === true ) {
            foreach($dossiers AS $dossier) {
                $result = ($result && ftp_mkdir($this->flux_connexion, $dossier));
            }
            return $result;
        }else {
            return false ;
        }
    }

    function rm ( $fichier ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_delete ( $this -> flux_connexion, $fichier ) ;
        }
        else {
            return false ;
        }
    }

    function rmdir ( $repertoire ) {
        if ( !$this -> flux_connexion === false && $this -> flux_identification === true ) {
            return ftp_rmdir ( $this -> flux_connexion, $repertoire ) ;
        }
        else {
            return false ;
        }
    }

    function set_mode ( $mode ) {
        $this -> mode_transmission = $mode ;
    }
}