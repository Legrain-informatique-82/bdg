#!/bin/bash

## Serveur lgrser.lgr
. ./svn/Build/build.conf

#Install "compilation a la main" dans /usr/local/nsis
#MAKENSIS_PATH=/usr/local/nsis/nsis-2.12/makensis

#Install depot apt-get
#dll des plugins dans /usr/share/nsis/Plugins/*.dll
#nsh des plugins dans /usr/share/nsis/Include/*.nsh
MAKENSIS_PATH=/usr/bin/makensis

#====================================================================================================================
# Variables devant etre identique a celle du fichier build.properties ou a celles passees dans la ligne de commande
#====================================================================================================================
buildType=I
buildId=BureauDeGestion
buildLabel=$buildId
archivePrefix=BureauDeGestion
#====================================================================================================================

#emplacement du script NSI
FICHIER_NSIS=$BASE_SCM/installeurNSIS/install/setup_GestionCommerciale.nsi
#repertoire de decompression des builds pour la generation du setup automatique
REP_UNZIP_NSIS_AUTO=$buildDirectory/auto
#repertoire de decompression des builds pour la generation du setup sur le site
REP_UNZIP_NSIS_SITE=/var/www/bdg/setup/bdg
#repertoire du site de gestion des mises a jour
REP_SITE_GESTION_UPDATE=/var/www/bdg/update
#repertoire de decompression des builds pour le site de mise a jour
REP_UNZIP_UPDATE_SITE=/home/distrib/bdg/updates
#emplacement des fichiers necessaire a la compilation du setup : pluginsNSIS, install jre, intall firebird, ...
REP_BASE_NSIS=$BASE_SCM/installeurNSIS/install
#Chemin programme d'analyse des dependance
ANALYSE_DEP=$BASE_SCM/LireXmlPlugin/lireXmlPlugin.jar

buildsZip=$buildLabel
zipLinux="$buildId-linux.gtk.x86.zip"
zipWin32="$buildId-win32.win32.x86.zip"
zipAll="$buildId-group.zip"
zipUpdateSie="$buildId$varUpdateSite-group.zip"

nomSetupAuto="setup_BdG_r$SVN_REVISION.exe"

#Mise à jour du lien symbolique pointant vers la jvm windows qui sera embarquee dans le setup windows
echo "*****************************************"
echo "* MAJ liens vers JRE Windows pour setup *"
echo "*****************************************"
echo "Ce setup Windows utilisera : $REPERTOIRE_EMBEDDED_JRE_SETUP_WINDOWS$NOM_EMBEDDED_JRE_SETUP_WINDOWS_COURANT"
rm $LIEN_EMBEDDED_JRE_SETUP_WINDOWS
ln -s $REPERTOIRE_EMBEDDED_JRE_SETUP_WINDOWS$NOM_EMBEDDED_JRE_SETUP_WINDOWS_COURANT $LIEN_EMBEDDED_JRE_SETUP_WINDOWS

#Setup automatique
echo "**************************************************"
echo "* Generation automatique du setup windows (NSIS) *"
echo "**************************************************"
CHEMIN_DEFAUT=$(pwd)
rm -rf $REP_UNZIP_NSIS_AUTO
unzip -d $REP_UNZIP_NSIS_AUTO $buildDirectory/$buildsZip/$zipWin32

#Importation des JRE (tant qu'on arrive pas a les exporter avec le PDE)
# -H pour suivre les liens symboliques
cp -rH /var/opt/java/windows/jre $REP_UNZIP_NSIS_AUTO/$archivePrefix/

cd $REP_BASE_NSIS
$MAKENSIS_PATH -DOUT_FILE=$buildDirectory/$buildsZip/$nomSetupAuto -DREPERTOIRE_EXPORT_RCP=$REP_UNZIP_NSIS_AUTO/$archivePrefix/ -DREPERTOIRE_EXPORT_RCP_UTIL=$REP_UNZIP_NSIS_AUTO/ $FICHIER_NSIS
cd $CHEMIN_DEFAUT

echo "***************************************************"
echo "* Generation des jars pour le site de mise a jour *"
echo "***************************************************"
#Preparation des jars pour le site de mise a jour
rm -rf $REP_UNZIP_UPDATE_SITE/*
unzip -d $REP_UNZIP_UPDATE_SITE $buildDirectory/$buildsZip$varUpdateSite/$zipUpdateSie
cp -a $REP_SITE_GESTION_UPDATE/updates/site_xml.php $REP_UNZIP_UPDATE_SITE/
cp -a $REP_SITE_GESTION_UPDATE/updates/test_xml.php $REP_UNZIP_UPDATE_SITE/

mv $REP_UNZIP_UPDATE_SITE/$buildLabel/** $REP_UNZIP_UPDATE_SITE/
mv $REP_UNZIP_UPDATE_SITE/win32.win32.x86/$buildLabel/** $REP_UNZIP_UPDATE_SITE/win32.win32.x86/
mv $REP_UNZIP_UPDATE_SITE/linux.gtk.x86/$buildLabel/** $REP_UNZIP_UPDATE_SITE/linux.gtk.x86/

rm -rf $REP_UNZIP_UPDATE_SITE/$buildLabel
rm -rf $REP_UNZIP_UPDATE_SITE/win32.win32.x86/$buildLabel
rm -rf $REP_UNZIP_UPDATE_SITE/linux.gtk.x86/$buildLabel

echo "*******************************************************************"
echo "* Preparation des repertoires pour le site de generation de setup *"
echo "*******************************************************************"
#Suppression des repertoires precedents
rm -rf $REP_UNZIP_NSIS_SITE/win32.win32.x86
rm -rf $REP_UNZIP_NSIS_SITE/linux.gtk.x86

#Creation des repertoires
mkdir $REP_UNZIP_NSIS_SITE/win32.win32.x86
mkdir $REP_UNZIP_NSIS_SITE/linux.gtk.x86

#Importation des JRE (tant qu'on arrive pas a les exporter avec le PDE)
# -H pour suivre les liens symboliques
cp -rH /var/opt/java/windows/jre $REP_UNZIP_NSIS_SITE/win32.win32.x86/

#Decompression win32.win32.x86
rm -rf $REP_UNZIP_NSIS_SITE/$buildLabel
unzip -d $REP_UNZIP_NSIS_SITE $buildDirectory/$buildsZip/$zipWin32 
mv $REP_UNZIP_NSIS_SITE/$buildLabel/** $REP_UNZIP_NSIS_SITE/win32.win32.x86/

#Decompression linux.gtk.x86
rm -rf $REP_UNZIP_NSIS_SITE/$buildLabel
unzip -d $REP_UNZIP_NSIS_SITE $buildDirectory/$buildsZip/$zipLinux
mv $REP_UNZIP_NSIS_SITE/$buildLabel/** $REP_UNZIP_NSIS_SITE/linux.gtk.x86/

#Nettoyage
rm -rf $REP_UNZIP_NSIS_SITE/$buildLabel

#Lecture des dependances et remplissage de la base de donnees
echo "--------------------------------------------------------------------------------"
echo "$JAVA_HOME/bin/java -jar $ANALYSE_DEP $eclipseInstall/features $BASE_SCM $forceContextQualifier"
echo "--------------------------------------------------------------------------------"
$JAVA_HOME/bin/java -jar $ANALYSE_DEP $eclipseInstall/features $BASE_SCM $forceContextQualifier

#Transfert des dependances vers le site de mise a jour interne
. ./svn/Build/transfert_mysql.sh



