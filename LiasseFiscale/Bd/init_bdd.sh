#!/bin/bash
# 1 : repertoire de base (temporaire)
# 2 : action => dossier:creation/droit dossier tmp *** copie:copie de tmp vers final

# - repertoire des dossier
# - fichier avec les erreurs
# + de parametre
case $1 in
	dossier)
		#modification des droits du repertoire temporaire pour permettre la creation d'une base de donnees par firebird
#		mkdir $2/data
#		chmod 777 $2/data
		chmod 777 $2
		;;
	copie)
		#copie de la base de donnees du repertoire temporaire vers le dossier final
#		cp -p $2/data/Gest_Com.fdb $2/data/aa.fdb 2> $2/data/aaa.txt
		cp -p $2 $3
		;;
	*)
		echo "Valeur invalide."
		;;
esac

