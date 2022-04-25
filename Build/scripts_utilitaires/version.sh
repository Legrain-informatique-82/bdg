#!/bin/bash
#base: find . \( -name "*.xml" -or -name "*.MF" -or -name "*.properties" \) | xargs grep -F -l 0.6.0 | xargs sed -i -e 's/0.6.0/0.7.0/g'
#http://wiki.eclipse.org/STP_version_update_script
#==========================================================================================================================================
# Verifier les fichiers NSI a la main
#==========================================================================================================================================

#REPERTOIRE="/donnees/Projet/Java/Eclipse/GestionCommerciale/"
REPERTOIRE="/donnees/Projet/Java/Eclipse/GestionCommerciale_branche_2_0_13/"
VERSION_AVANT="2.0.13"
VERSION_APRES="2.0.14"

find $REPERTOIRE \( -name "*.xml" \
 -or -name "*.MF" \
 -or -name "*.properties" \
 -or -name "*.product" \
 -or -name "*.nsi" \
 -or -name "*.ini" \) \
 | xargs grep -s -F -l $VERSION_AVANT \
 | xargs sed -i -e 's/'$VERSION_AVANT'/'$VERSION_APRES'/g'

echo "Changement de version: $VERSION_AVANT ==> $VERSION_APRES dans le répertoire $REPERTOIRE"
echo "Attention au fichier : MiseAJourBase/build/db.build_MAJ.xml qui a du etre modifié."
echo "Pensé a changer le numero de version dans MiseAJourBase/MAJ/MAJ_Version.SQL"
echo "Pensé a changer la date de version dans MiseAJourBase/MAJ/MAJ_Date_Version.SQL et MiseAJourBase/MAJ/MAJ_Version.SQL"
echo "Pensé a verifier qu'il n'y a pas de numero de version mineur dans le A Propos (2.0.14.xxx)"
