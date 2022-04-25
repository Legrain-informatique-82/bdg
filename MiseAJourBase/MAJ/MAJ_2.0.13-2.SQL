CREATE or ALTER  PROCEDURE GESTION_ALTER_TA_L_ECHEANCE
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_L_ECHEANCE' and  a.RDB$FIELD_NAME ='ID_ETAT')) THEN
  begin
    query = 'ALTER TABLE TA_L_ECHEANCE ADD ID_ETAT DID_FACULTATIF';
    EXECUTE STATEMENT :query;
    
    query='ALTER TABLE TA_L_ECHEANCE ADD CONSTRAINT FK_TA_L_ECHEANCE_3 FOREIGN KEY (ID_ETAT) REFERENCES TA_ETAT (ID_ETAT)';
    EXECUTE STATEMENT :query;
    
  end
END
^
EXECUTE PROCEDURE GESTION_ALTER_TA_L_ECHEANCE
^
drop procedure GESTION_ALTER_TA_L_ECHEANCE
^



CREATE or ALTER  PROCEDURE GESTION_UPDATE_ta_t_tva_doc
as    
DECLARE VARIABLE QUERY VARCHAR(500);
BEGIN
query ='';

    query = 'update ta_t_tva_doc tt set tt.libelle_t_tva_doc=''Client sans TVA'',tt.libelle_edition=''Sans TVA'' where tt.code_t_tva_doc like ''N''';
    EXECUTE STATEMENT :query;
    
    if(not exists(select * from TA_T_TVA_DOC tb where tb.JOURNAL_T_TVA_DOC like 'FF'))then
    begin
    	query='INSERT INTO TA_T_TVA_DOC ( CODE_T_TVA_DOC, LIBELLE_T_TVA_DOC, JOURNAL_T_TVA_DOC,LIBELLE_EDITION, VERSION_OBJ) VALUES
 			( ''FRANCHISE'', ''Client en Franchise de TVA'', ''FF'',''Client en Franchise de TVA'', 0)';
    	EXECUTE STATEMENT :query;
    end

END
^
EXECUTE PROCEDURE GESTION_UPDATE_ta_t_tva_doc
^
drop procedure GESTION_UPDATE_ta_t_tva_doc
^



CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    if(not exists(select * from ta_tva tb where tb.CODE_Tva like 'F1'))then
    begin
    	query = 'insert into ta_tva (code_tva,libelle_tva,taux_tva,numcpt_tva,version_obj) values(''F1'',''Franchise de TVA'',0,'''',0)';
   	 	EXECUTE STATEMENT :query;
    end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR
^
drop procedure GESTION_INSERTION_VALEUR
^



CREATE OR ALTER PROCEDURE RECORD_MODIFIABLE (
    NOM_TABLE VARCHAR(100),
    IDTABLE INTEGER )
RETURNS (
    NB INTEGER )
AS
begin
if (upper(:NOM_TABLE) = upper('ta_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.id_tiers = :idtable into :nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_boncde doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_avoir doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_apporteur doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_proforma doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.id_tiers = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_acompte doc where doc.id_tiers = :idtable into nb  ;

        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_article'))   then
    begin
        nb=0;
        select count(*) from ta_L_facture Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;

        if (:nb<=0)then
            select count(*) from ta_l_boncde Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte Ldoc where Ldoc.id_ARTICLE = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_stock Ldoc where Ldoc.id_article_stock = :idtable into nb  ;

        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_T_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from ta_facture doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_devis doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_bonliv doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_boncde doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_avoir doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_apporteur doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_proforma doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_acompte doc where doc.ID_T_PAIEMENT = :idtable into nb  ;
        suspend;
    end
else
if (upper(:NOM_TABLE) = upper('ta_T_civilite'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_civilite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_Unite'))   then
    begin
        nb=0;
        select count(*) from ta_Prix prix where prix.id_unite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_Ligne'))   then
    begin
        nb=0;
        select count(*) from ta_l_facture ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_boncde ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte ldoc where ldoc.id_t_ligne = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_famille'))   then
    begin
        nb=0;
        select count(*) from ta_article article where article.id_famille = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_TVA'))   then
    begin
        nb=0;
        select count(*) from ta_ARTICLE article where article.id_tva = :idtable into nb  ;

        if (:nb<=0)then
            select count(*) from ta_l_facture ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_devis ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_bonliv ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_boncde ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_avoir ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_apporteur ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_proforma ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;
        if (:nb<=0)then
            select count(*) from ta_l_acompte ldoc,ta_TVA tva where ldoc.CODE_TVA_L_DOCUMENT = tva.code_tva and tva.id_tva = :idtable into nb  ;

        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_TVA'))   then
    begin
        nb=0;
        select count(*) from ta_Article article where article.id_T_Tva = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_T_Entite'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_entite = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_Entreprise'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_entreprise = :idtable into nb  ;
        suspend;
    end

if (upper(:NOM_TABLE) = upper('ta_t_tiers'))   then
    begin
        nb=0;
        select count(*) from ta_tiers tiers where tiers.id_t_tiers = :idtable into nb  ;
        suspend;
    end

if (upper(:NOM_TABLE) = upper('ta_t_adr'))   then
    begin
        nb=0;
        select count(*) from ta_adresse doc where doc.id_t_adr = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_email'))   then
    begin
        nb=0;
        select count(*) from ta_email doc where doc.id_t_email = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_web'))   then
    begin
        nb=0;
        select count(*) from ta_web doc where doc.id_t_web = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_tel'))   then
    begin
        nb=0;
        select count(*) from ta_telephone doc where doc.id_t_tel = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_tarif'))   then
    begin
        nb=0;
        select count(*) from ta_tiers doc where doc.id_t_tarif = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_banque'))   then
    begin
        nb=0;
        select count(*) from ta_compte_banque doc where doc.id_t_banque = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_t_liens'))   then
    begin
        nb=0;
        select count(*) from ta_liens doc where doc.id_t_liens = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('ta_etablissement'))   then
    begin
        nb=0;
        select count(*) from ta_operation doc where doc.id_etablissement = :idtable into nb  ;
        select count(*) from ta_depot doc where doc.id_etablissement = :idtable into nb  ;
        select count(*) from ta_report_t_paiement doc where doc.id_etablissement = :idtable into nb  ;
        suspend;
    end  
if (upper(:NOM_TABLE) = upper('TA_FAMILLE_TIERS'))   then
    begin
        nb=0;
        select count(*) from TA_R_FAMILLE_TIERS doc where doc.id_famille = :idtable into nb  ;
        suspend;
    end
if (upper(:NOM_TABLE) = upper('TA_FAMILLE_UNITE'))   then
    begin
        nb=0;
        select count(*) from TA_unite doc where doc.id_famille = :idtable into nb  ;
        suspend;
    end    
if (upper(:NOM_TABLE) = upper('TA_C_PAIEMENT'))   then
    begin
        nb=0;
        select count(*) from TA_TIERS doc where doc.ID_C_PAIEMENT = :idtable into nb  ;
        suspend;
    end    

if (upper(:NOM_TABLE) = upper('ta_acompte'))   then
    begin
        nb=0;
        select count(*) from ta_r_acompte r where r.id_acompte = :idtable
            and id_facture is not null into nb  ;
        suspend;
    end

    if (upper(:NOM_TABLE) = upper('ta_avoir'))   then
    begin
        nb=0;
        select count(*) from ta_r_avoir r where r.id_avoir = :idtable
            and id_facture is not null into nb  ;
        suspend;
    end
suspend;
end
^