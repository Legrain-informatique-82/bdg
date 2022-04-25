#/bin/bash

USER=root
PASSWORD=xxxxxxx
DB_SRC=plugins_features
DB_DEST=bdg_update

TABLE1=TA_DEP_FEATURE
TABLE2=TA_DEP_FEATURES_PLUGINS
TABLE3=TA_LIST_FEATURES
TABLE4=TA_LIST_PLUGINS
TABLE_CATEGORIE=TA_CATE_FEATURE

ID_CAT_TOUT=1


echo "***************************************************************************************************"
echo "* Transfert de la liste des dependences plugins/features vers la base de gestion des mises a jour *"
echo "* BD : $DB_SRC => $DB_DEST , Table : $TABLE1, $TABLE2, $TABLE3, $TABLE4                           *"
echo "***************************************************************************************************"

rm -f *.sql
mysqldump -u $USER -p$PASSWORD $DB_SRC $TABLE1 > $TABLE1.sql
mysqldump -u $USER -p$PASSWORD $DB_SRC $TABLE2 > $TABLE2.sql
mysqldump -u $USER -p$PASSWORD $DB_SRC $TABLE3 > $TABLE3.sql
mysqldump -u $USER -p$PASSWORD $DB_SRC $TABLE4 > $TABLE4.sql

echo "************************************************************************"
echo "* Nettoyage des tables $TABLE1, $TABLE2, $TABLE3, $TABLE4 dans $DB_DEST*"
echo "* Nettoyage de la description de la categorie 'Tout'                   *"
echo "************************************************************************"
mysql -u $USER -p$PASSWORD $DB_DEST -e "delete from $TABLE1"
mysql -u $USER -p$PASSWORD $DB_DEST -e "delete from $TABLE2"
mysql -u $USER -p$PASSWORD $DB_DEST -e "delete from $TABLE3"
mysql -u $USER -p$PASSWORD $DB_DEST -e "delete from $TABLE4"
mysql -u $USER -p$PASSWORD $DB_DEST -e "delete from $TABLE_CATEGORIE where id_category=$ID_CAT_TOUT"

echo "**************************************"
echo "* Importation des nouvelles features *"
echo "**************************************"
mysql -u $USER -p$PASSWORD $DB_DEST < $WORKSPACE/$TABLE1.sql
mysql -u $USER -p$PASSWORD $DB_DEST < $WORKSPACE/$TABLE2.sql
mysql -u $USER -p$PASSWORD $DB_DEST < $WORKSPACE/$TABLE3.sql
mysql -u $USER -p$PASSWORD $DB_DEST < $WORKSPACE/$TABLE4.sql

echo "**************************************"
echo "* Re-creation de la categorie 'Tout' *"
echo "**************************************"
#mysqlimport -u $USER -p$PASSWORD $DB_DEST $WORKSPACE/$TABLE1.sql $WORKSPACE/$TABLE2.sql $WORKSPACE/$TABLE3.sql $WORKSPACE/$TABLE4.sql
#mysql -u $USER -p$PASSWORD $DB_DEST -e "INSERT INTO $TABLE_CATEGORIE(id, id_category, nom_feature ) SELECT max(id)+1,$ID_CAT_TOUT, nom_feature FROM $TABLE3 WHERE type_feature = 'BG'"

mysql -u $USER -p$PASSWORD $DB_DEST -e "INSERT INTO $TABLE_CATEGORIE( id_category, nom_feature ) SELECT $ID_CAT_TOUT, nom_feature FROM $TABLE3 WHERE type_feature = 'BG'"

