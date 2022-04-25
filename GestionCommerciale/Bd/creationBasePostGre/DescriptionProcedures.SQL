CREATE OR REPLACE FUNCTION public."ANNULE_MODIFICATION" (
  "NOMTABLE" varchar,
  "NOMCHAMP" varchar,
  "VALEUR" varchar
)
RETURNS void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."AUTORISE_MODIF" (
    "NOMTABLE" VARCHAR,
    "NOMCHAMP" VARCHAR,
    "VALEUR" VARCHAR,
    out retour integer
)
RETURNS INTEGER AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."AUTORISE_MODIF_GENERE" (
    NOMTABLE VARCHAR,
    NOMCHAMP VARCHAR,
    VALEUR VARCHAR,
    VERIF_CONNECTION INTEGER,
    out retour integer
    )
RETURNS INTEGER AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."ENREGISTRE_ACCES"(
)
RETURNS void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';



CREATE OR REPLACE FUNCTION public."NETTOYAGE" (
    TEMP_MIN INTEGER
)
RETURNS void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RAZ_ACCES"(
)
RETURNS void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RECORD_MODIFIABLE" (
    NOM_TABLE VARCHAR,
    IDTABLE INTEGER,
    out NB INTEGER
    )
RETURNS INTEGER AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RECUP_IP_ACCES"(
IP_ACCES VARCHAR
)
RETURNS  VARCHAR AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';



CREATE OR REPLACE FUNCTION public."RENTRE_EN_MODIFICATION" (
    NOMTABLE VARCHAR,
    NOMCHAMP VARCHAR,
    VALEUR VARCHAR
    )
returns void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."SUPPRESSION_ACCES"(
)
returns void AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';

/******************************************************************************/
/*                             Stored Procedures                              */
/******************************************************************************/


CREATE OR REPLACE FUNCTION public."ANNULE_MODIFICATION" (
  "NOMTABLE" varchar,
  "NOMCHAMP" varchar,
  "VALEUR" varchar
)
RETURNS void AS
$body$
BEGIN
delete from ta_modif where
upper(table_modif) =upper(nomtable) and
upper(champ_modif) =upper(nomchamp) and
upper(valeur_modif) =upper(valeur)and
upper(ip_acces)=current_connection;
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."AUTORISE_MODIF" (
    "NOMTABLE" VARCHAR,
    "NOMCHAMP" VARCHAR,
    "VALEUR" VARCHAR,
    out retour integer
)
RETURNS INTEGER AS
$body$
BEGIN
  select count(*) from ta_modif modif where upper(modif.table_modif) = upper(nomtable)
  and upper(modif.champ_modif) =upper(nomchamp)
  and  upper(modif.valeur_modif) =upper(valeur)
  and upper(ip_acces)<>current_connection into retour;
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."AUTORISE_MODIF_GENERE" (
    NOMTABLE VARCHAR,
    NOMCHAMP VARCHAR,
    VALEUR VARCHAR,
    VERIF_CONNECTION INTEGER,
    out retour integer
    )
RETURNS INTEGER AS
$body$
DECLARE
  QUERY varchar(1000);
BEGIN
      query='select count(*) from '|| nomtable ||' where upper('|| nomchamp || ') = upper('''||valeur||''')';
      execute statement query into retour;

 if(retour <= 0)THEN
   	select into retour count(*) from ta_modif modif where upper(modif.table_modif) = upper(nomtable)
  	and upper(modif.champ_modif) =upper(nomchamp)
  	and  upper(modif.valeur_modif) =upper(valeur) ;
 	 if ((retour>0) and (verif_connection=1))THEN   
       select into retour count(*) from ta_modif modif where upper(modif.table_modif) = upper(nomtable)
        and upper(modif.champ_modif) =upper(nomchamp)
        and  upper(modif.valeur_modif) =upper(valeur) ;
        /*and modif.ip_acces<>current_connection*/ 
 	 end if;
  END if;
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."ENREGISTRE_ACCES"(
)
RETURNS void AS
$body$
BEGIN
  if (not exists
  (select acces2.ip_acces from ta_acces acces2 where acces2.ip_acces=current_connection
  and acces2.user_acces=user)) then
      insert into ta_acces (user_acces)
    values (user) ;
  end if;
END;
$body$
LANGUAGE 'plpgsql';



CREATE OR REPLACE FUNCTION public."NETTOYAGE" (
    TEMP_MIN INTEGER
)
RETURNS void AS
$body$
BEGIN
	delete from ta_modif modif where
	not exists  (select mon.mon$attachment_id from mon$attachments mon  where mon.mon$attachment_id=modif.ip_acces);
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RAZ_ACCES"(
)
RETURNS void AS
$body$
BEGIN
delete from ta_acces acces where
       not exists (select ip_acces from ta_acces acces, mon$attachments mon where acces.ip_acces=mon.mon$attachment_id);
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RECORD_MODIFIABLE" (
    NOM_TABLE VARCHAR,
    IDTABLE INTEGER,
    out NB INTEGER
    )
RETURNS INTEGER AS
$body$
BEGIN

END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."RECUP_IP_ACCES"(
IP_ACCES VARCHAR
)
RETURNS  VARCHAR AS
$body$
BEGIN
 ip_acces = current_connection;
END;
$body$
LANGUAGE 'plpgsql';



CREATE OR REPLACE FUNCTION public."RENTRE_EN_MODIFICATION" (
    NOMTABLE VARCHAR,
    NOMCHAMP VARCHAR,
    VALEUR VARCHAR
    )
returns void AS
$body$
BEGIN
if (valeur is not null) then
    if (not exists(select * from ta_modif modif where table_modif=upper(nomtable) and
    champ_modif=upper(nomchamp)and valeur_modif=upper(valeur))) then
        insert into ta_modif
         (table_modif,champ_modif,valeur_modif)
         values (upper(nomtable),upper(nomchamp),upper(valeur))  ;
    end if;
end if;         
END;
$body$
LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION public."SUPPRESSION_ACCES"(
)
returns void AS
$body$
BEGIN
  delete from ta_acces where ip_acces=current_connection;
END;
$body$
LANGUAGE 'plpgsql';