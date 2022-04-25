#!/bin/bash

##############################################################################################
# faire un script en ruby (ou autre pour gestion du XML)
# boucle sur /Build/plugins_features/liste.txt ou sur les répertoire
# création du "répertoire maven" s'il n'existe pas sinon MAJ du XML
# si création, copie du pom.xml modèle puis modification
# la fin, vérifier que sonar est lancer
# puis lancer la commande : mvn sonar:sonar à la racine du projet SonarMaven
##############################################################################################

# ./reader.sh liste.txt /home/nicolas/.hudson/jobs/test\ trunk/workspace/svn /home/nicolas/eclipse.build

. ./svn/Build/build.conf

IFS="@"
PLUGIN="plugins"
FEATURE="features"

BASE_BUILD=$buildDirectory

rm -rf $buildDirectory/$PLUGIN
rm -rf $buildDirectory/$FEATURE
rm -rf $buildDirectory

mkdir $buildDirectory
mkdir $buildDirectory/$PLUGIN
mkdir $buildDirectory/$FEATURE

echo "***************************************************************"
echo "* Création des répertoire maven pour sonar pour chaque plugin *"
echo "***************************************************************"

sed -i s#"HUDSON"#"r$SVN_REVISION *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"#g $BASE_SCM/GestionCommerciale/plugin.properties

while read ligne
do
   
set -- $ligne
arr=( $ligne )
#if [[ -a $BASE_SCM/${arr[1]} ]]
#Le repertoire existe bien, on peut le deplacer
	rep=${arr[0]}
    fich=${arr[1]}
    echo "copie dans $rep de $fich"
	cp -r $BASE_SCM/${arr[1]} $buildDirectory/$rep/$fich
#fi

#done < $1
done < ../Build/plugins_features/liste.txt
