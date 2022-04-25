#!/bin/bash

#Conversion dans un repertoire de tous les fichiers texte d'un encoding vers un autre
#Commande de basse : iconv -f encodage_original -t utf-8 fichier_original > nouveau_fichier
#Pour connaitre les types d'encodage que l'on peut passer en parametre : iconv --list
#script d'origine sur http://docs.moodle.org/fr/Conversion_de_fichiers_en_UTF-8


#FROM=iso-8859-1
FROM=WINDOWS-1252
TO=UTF-8
FROM=UTF-8
TO=WINDOWS-1252
ICONV="iconv -f $FROM -t $TO"
#REPERTOIRE_A_CONVERTIR=ToUTF/
REPERTOIRE_A_CONVERTIR=$1

# Convert
find $REPERTOIRE_A_CONVERTIR -type f -name "*" | while read fn; do
cp ${fn} ${fn}.bak
echo ${fn}
$ICONV < ${fn}.bak > ${fn}
rm ${fn}.bak
done
