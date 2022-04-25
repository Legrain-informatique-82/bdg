CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    query = 'update ta_tiers t set t.id_famille_tiers=(select rft.id_famille from ta_r_famille_tiers rft where rft.id_tiers=t.id_tiers rows 1) where t.id_famille_tiers is null';
    EXECUTE STATEMENT :query;
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR1
^
drop procedure GESTION_INSERTION_VALEUR1
^