#!/bin/bash
# On va chercher par ftp le fichier json.
HOST=$1
USER=$2
PASSWD=$3
code=$4
url=$5
# Récupération par ftp.
touch /var/www/legrain.biz/web/prestashop/lgr/php/import/test.txt
cd json
ftp -ni $HOST <<END_SCRIPT
user $USER $PASSWD
binary
cd json
get catalogue_web.json
quit
END_SCRIPT
# récupération de toute les images
chmod 777 catalogue_web.json
cd ../images

ftp -niv $HOST <<END_SCRIPT
user $USER $PASSWD
binary
cd images
mget *
quit
END_SCRIPT
#exit 0
chmod 777 -R ../images
cd ..
# fichier mettant le site en maintenance durant la mise à jour de la bdd 
#touch maintenance.txt
#lancement de la commande php lisant le json et metant la bdd à jour
#echo $code > /tmp/testzz.txt
curl -X POST $url -d code=$code 

# deplacement des images au bon endroit.
#suppression du contenu de old
#rm -f ../images/old/categories/* ../images/old/labels/* ../images/old/produits/*
# Anciennes images dans old
#mv ../images/categories/* ../images/old/categories/
#mv ../images/labels/* ../images/old/labels/
#mv ../images/produits/* ../images/old/produits/
# déplacement des images dans le dossier définitif
#mv images/categories/* ../images/categories/
#mv images/labels/* ../images/labels/
#mv images/produits/* ../images/produits/
# suppression du fichier de maintenance
# rm -f maintenance.txt
