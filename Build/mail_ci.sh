#!/bin/bash

PROGRAMME="Bureau de gestion"

NOM_LIEN=lastSuccessful
FICHIER_TIMESTAMP=last.txt

HUDSON_URL=http://lgrser.lgr:8080/hudson
HUDSON_JOB=Bureau_de_Gestion_b_1_8_9_1_stocks
HUDSON_HOME=/var/opt/hudson
URL_BUILD=$HUDSON_URL/job/$HUDSON_JOB/promotion/
HUDSON_JOB_WORKSPACE=$HUDSON_HOME/jobs/$HUDSON_JOB/workspace

EMAIL_LAST=nicolas@lgrser.lgr
EMAIL_ALPHA=nicolas@lgrser.lgr
EMAIL_BETA=nicolas@lgrser.lgr
EMAIL_RC=nicolas@lgrser.lgr
EMAIL_FINAL=nicolas@lgrser.lgr

IFS=";"

cd $HUDSON_JOB_WORKSPACE

# Ecrit la date de $1 a la fin du fichier $3
# format d'une ligne : $2(separateur)date de $1
# Exemple : alpha;21354685
function test_f
{
	if [ -e $1 ]
	then    
		echo "$2;"$(lgr_time $1) >> $3
	fi
}

# Date de l'entree $1
# Le resultat de stat peut etre different d'un systeme a l'autre
# ici pour Ubuntu Linux donne la date en seconde pour une entree
function lgr_time
{
	if [ -e $1 ]
	then    
		echo $(stat -c '%Y' $1)
	fi
}

#Envoi d'un mail a l'adresse $3
function lgr_mail 
{
	echo -e "$PROGRAMME - build $1 \n$2" | mail $3 -s "[$PROGRAMME] Nouveau build $1 disponible"
}


function alert_new_build
{
	time=$(lgr_time $1)
	if [ ! $time -eq $2 ]
	then
		echo "$3 a changé"
		lgr_mail $3 $4 $5
	fi
}

#Lecture du fichier, et pour chaque ligne (1 ligne par type de promotion)
#comparaison de la date dans le fichier avec la date reelle du fichier
if [ -e $FICHIER_TIMESTAMP ]
	then
		while read ligne
			do
   
				set -- $ligne
				arr=( $ligne )

				type_build=${arr[0]}
				date=${arr[1]}

				case $type_build in
					last)
					echo $type_build
					FICHIER=../$NOM_LIEN
					alert_new_build $FICHIER $date $type_build $URL_BUILD $EMAIL_LAST
				;;

				alpha)
					echo $type_build
					FICHIER=../promotions/alpha/$NOM_LIEN
					alert_new_build $FICHIER $date $type_build $URL_BUILD $EMAIL_ALPHA
				;;

				beta)
					echo $type_build
					FICHIER=../promotions/beta/$NOM_LIEN
					alert_new_build $FICHIER $date $type_build $URL_BUILD $EMAIL_BETA
				;;

				RC)
					echo $type_build
					FICHIER=../promotions/RC/$NOM_LIEN
					alert_new_build $FICHIER $date $type_build $URL_BUILD $EMAIL_RC
				;;

				finale)
					echo $type_build
					FICHIER=../promotions/finale/$NOM_LIEN
					alert_new_build $FICHIER $date $type_build $URL_BUILD $EMAIL_FINAL
				;;
 
			esac
    
		done < $FICHIER_TIMESTAMP
fi

#Ecrire les nouvelles dates dans le fichier
rm -f $FICHIER_TIMESTAMP
touch $FICHIER_TIMESTAMP

FICHIER=../$NOM_LIEN
test_f $FICHIER last $FICHIER_TIMESTAMP

FICHIER=../promotions/alpha/$NOM_LIEN
test_f $FICHIER alpha $FICHIER_TIMESTAMP

FICHIER=../promotions/beta/$NOM_LIEN
test_f $FICHIER beta $FICHIER_TIMESTAMP

FICHIER=../promotions/RC/$NOM_LIEN
test_f $FICHIER RC $FICHIER_TIMESTAMP

FICHIER=../promotions/finale/$NOM_LIEN
test_f $FICHIER finale $FICHIER_TIMESTAMP





