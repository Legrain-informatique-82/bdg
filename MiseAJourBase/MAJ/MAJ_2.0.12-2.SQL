CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR1
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    if(not exists(select * from TA_T_BANQUE tb where tb.CODE_T_BANQUE like 'PREL'))then
    begin
    	query = 'INSERT INTO TA_T_BANQUE (ID_T_BANQUE, CODE_T_BANQUE, LIBL_T_BANQUE,  VERSION_OBJ) VALUES (Null, ''PREL'', ''Prélèvement'', 0)';
   	 	EXECUTE STATEMENT :query;
    end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR1
^
drop procedure GESTION_INSERTION_VALEUR1
^

update ta_info_entreprise cb set cb.id_tiers= -1 where cb.id_tiers is null;
^

update ta_compte_banque cb set cb.nom_compte= 'compte : '||cb.compte where cb.nom_compte is null
^


CREATE or ALTER  PROCEDURE GESTION_INSERTION_ETAT
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    if(not exists(select * from TA_ETAT tb where tb.CODE_ETAT like 'ANNULE'))then
    begin
    	query = 'INSERT INTO TA_ETAT (CODE_ETAT, LIBELLE_ETAT) VALUES (''ANNULE'', ''Annulé'')';
   	 	EXECUTE STATEMENT :query;
    end
    if(not exists(select * from TA_ETAT tb where tb.CODE_ETAT like 'IMPAYE'))then
    begin
    	query = 'INSERT INTO TA_ETAT (CODE_ETAT, LIBELLE_ETAT) VALUES (''IMPAYE'', ''Impayé'')';
   	 	EXECUTE STATEMENT :query;
    end
    if(not exists(select * from TA_ETAT tb where tb.CODE_ETAT like 'REJETE'))then
    begin
    	query = 'INSERT INTO TA_ETAT (CODE_ETAT, LIBELLE_ETAT) VALUES (''REJETE'', ''Rejeté'')';
   	 	EXECUTE STATEMENT :query;
    end
    if(not exists(select * from TA_ETAT tb where tb.CODE_ETAT like 'REPORTE'))then
    begin
    	query = 'INSERT INTO TA_ETAT (CODE_ETAT, LIBELLE_ETAT) VALUES (''REPORTE'', ''Reporté'')';
   	 	EXECUTE STATEMENT :query;
    end    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_ETAT
^
drop procedure GESTION_INSERTION_ETAT
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_1
as    
DECLARE VARIABLE QUERY VARCHAR(1000);
BEGIN
    QUERY='';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='T'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Tiers' where CODE_T_C_PAIEMENT='T';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='F'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Facture' where CODE_T_C_PAIEMENT='F';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='D'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Devis' where CODE_T_C_PAIEMENT='D';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='BC'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Boncde' where CODE_T_C_PAIEMENT='BC';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='A'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Avoir' where CODE_T_C_PAIEMENT='A';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='AP'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Apporteur' where CODE_T_C_PAIEMENT='AP';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='AC'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Acompte' where CODE_T_C_PAIEMENT='AC';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='P'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Proforma' where CODE_T_C_PAIEMENT='P';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='PR'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Prelevement' where CODE_T_C_PAIEMENT='PR';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='BL'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='Bonliv' where CODE_T_C_PAIEMENT='BL';
    if (exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='AE'))then
      update TA_T_C_PAIEMENT set CODE_T_C_PAIEMENT='AvisEcheance' where CODE_T_C_PAIEMENT='AE';
    
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Tiers'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Tiers', 'Tiers', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Facture'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Facture', 'Facture', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Devis'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Devis', 'Devis', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Boncde'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Boncde', 'Commande', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Avoir'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Avoir', 'Avoir', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Apporteur'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Apporteur', 'Aporteur', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Acompte'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Acompte', 'Acompte', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Proforma'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Proforma', 'Proforma', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Prelevement'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Prelevement', 'Prelevement', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='Bonliv'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Bonliv', 'Livraison', '', 'NOW', '', 'NOW', '', '0', '0');
    if (not exists(select ID_C_PAIEMENT from TA_T_C_PAIEMENT where CODE_T_C_PAIEMENT='AvisEcheance'))then
      INSERT INTO TA_T_C_PAIEMENT (ID_C_PAIEMENT, CODE_T_C_PAIEMENT, LIBL_T_C_PAIEMENT, QUI_CREE_T_C_PAIEMENT, QUAND_CREE_T_C_PAIEMENT, QUI_MODIF_T_C_PAIEMENT, QUAND_MODIF_T_C_PAIEMENT, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'AvisEcheance', 'Avis Echeance', '', 'NOW', '', 'NOW', '', '0', '0');
      
END
^
EXECUTE PROCEDURE GESTION_INSERTION_1
^
drop procedure GESTION_INSERTION_1
^