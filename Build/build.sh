#!/bin/bash

## Serveur lgrser.lgr
. ./svn/Build/build.conf

#Variable specifique a la version d'eclipse utilise pour la compilation
#plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar
#plugins/org.eclipse.pde.build.source_3.7.0.v20110512-1320.jar
version_equinox_launcher=1.2.0.v20110502
pde_build_version=3.7.0.v20110512-1320

#Variable relative au repertoire de travail du serveur d'integration continue
build_configuration_folder=$BASE_SCM/Build/headless-build
product_file=$BASE_SCM/GestionCommerciale/GestionCommercialeCompletIC.product
product_file_test=$BASE_SCM/GestionCommerciale/GestionCommercialeCompletICTest.product

if [ $# -eq 3 ] 
then
#il y a 3 arguments, le 3eme correspond au fichier .product a compiler
#s'il n'y a que 2 arguments, on prend le fichier .product par defaut
product_file=$BASE_SCM/GestionCommerciale/$3
fi

#forceContextQualifier=$1
buildId=$2

echo "*************************************"
echo "* Compilation Product - Eclipse PDE *"
echo "*************************************"

$JAVA_HOME/bin/java -jar $eclipseInstall/plugins/org.eclipse.equinox.launcher_$version_equinox_launcher.jar \
-application org.eclipse.ant.core.antRunner \
-buildfile $eclipseInstall/plugins/org.eclipse.pde.build_$pde_build_version/scripts/productBuild/productBuild.xml \
-Dbuilder=$build_configuration_folder \
-Dproduct=$product_file \
-Dbase=$base_folder \
-DbuildDirectory=$buildDirectory \
-DforceContextQualifier=$forceContextQualifier \
-DbuildId=$buildId \
-Dp2.gathering=true

echo "*************************************************************"
echo "* Compilation Product pour les tests unitaire - Eclipse PDE *"
echo "*************************************************************"

#Suppression du depot p2 local, sinon les metadonnees ne sont pas regenere
#http://help.eclipse.org/galileo/index.jsp?topic=/org.eclipse.pde.doc.user/tasks/pde_p2_reusingmetadata.htm
#http://help.eclipse.org/galileo/index.jsp?topic=/org.eclipse.pde.doc.user/tasks/pde_p2_productbuilds.htm
#Si elles ne sont pas regenere, les features/plugins de test ne sont pas pris en compte
rm -rf $buildDirectory/buildRepo

$JAVA_HOME/bin/java -jar $eclipseInstall/plugins/org.eclipse.equinox.launcher_$version_equinox_launcher.jar \
-application org.eclipse.ant.core.antRunner \
-buildfile $eclipseInstall/plugins/org.eclipse.pde.build_$pde_build_version/scripts/productBuild/productBuild.xml \
-Dbuilder=$build_configuration_folder \
-Dproduct=$product_file_test \
-Dbase=$base_folder \
-DbuildDirectory=$buildDirectory \
-DforceContextQualifier=$forceContextQualifier \
-DbuildId=$buildId$varTestsUnitaire \
-Dp2.gathering=true \
-DrunTest=yes
#-Dp2.build.repo=$buildDirectory/buildRepo_test
#Pour remettre les tests JUnits automatique avec eclipse 3.4, il faut pouvoir ajouter les plugins avec p2 (dropins,...)
#http://rcpquickstart.com/2007/08/06/running-automated-tests-with-pde-build/
#et rajouter pour "Publish JUnit test result report" la valeur "test-results.xml" dans la configuration Hudson

echo "*********************************************************"
echo "* Compilation pour le site de mise a jour - Eclipse PDE *"
echo "*********************************************************"

$JAVA_HOME/bin/java -jar $eclipseInstall/plugins/org.eclipse.equinox.launcher_$version_equinox_launcher.jar \
-application org.eclipse.ant.core.antRunner \
-buildfile $eclipseInstall/plugins/org.eclipse.pde.build_$pde_build_version/scripts/productBuild/productBuild.xml \
-Dbuilder=$build_configuration_folder \
-Dproduct=$product_file \
-Dbase=$base_folder \
-DbuildDirectory=$buildDirectory \
-DforceContextQualifier=$forceContextQualifier \
-DbuildId=$buildId$varUpdateSite \
-DoutputUpdateJars=true \
-Dp2.gathering=false \
-DgroupConfigurations=true

