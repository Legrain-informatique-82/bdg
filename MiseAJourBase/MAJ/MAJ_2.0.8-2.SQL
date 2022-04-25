CREATE or ALTER  PROCEDURE GESTION_INSERTION_TABLE1
as
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
    if (exists(select * from TA_T_TVA_DOC where CODE_T_TVA_DOC ='F')) then
     begin
       query='UPDATE TA_T_TVA_DOC set LIBELLE_EDITION = '''' where CODE_T_TVA_DOC = ''F''';
       EXECUTE STATEMENT :query;
     end
     if (exists(select * from TA_T_TVA_DOC where CODE_T_TVA_DOC ='HUE')) then
     begin
       query='UPDATE TA_T_TVA_DOC set LIBELLE_EDITION = ''Exonération TVA, vente à l''''export'' where CODE_T_TVA_DOC = ''HUE''';
       EXECUTE STATEMENT :query;
     end
     if (exists(select * from TA_T_TVA_DOC where CODE_T_TVA_DOC ='UE')) then
     begin
       query='UPDATE TA_T_TVA_DOC set LIBELLE_EDITION = ''Exonération TVA, art. 262 ter-1 du CGI'' where CODE_T_TVA_DOC = ''UE''';
      EXECUTE STATEMENT :query;
     end
     if (exists(select * from TA_T_TVA_DOC where CODE_T_TVA_DOC ='N')) then
     begin
       query='UPDATE TA_T_TVA_DOC set LIBELLE_EDITION = ''En Franchise de TVA'' where CODE_T_TVA_DOC= ''N''';
      EXECUTE STATEMENT :query;
     end
END
^
EXECUTE PROCEDURE GESTION_INSERTION_TABLE1
^
drop procedure GESTION_INSERTION_TABLE1
^

CREATE or ALTER  PROCEDURE MODIF_CODE_T_TVA_DOC
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$FIELD_NAME, a.RDB$RELATION_NAME FROM RDB$RELATION_FIELDS a
where a.RDB$RELATION_NAME = 'TA_INFOS_FACTURE' and  a.RDB$FIELD_NAME ='CODE_T_TVA_DOC')) THEN
  begin
    query = ' ALTER TABLE TA_INFOS_FACTURE ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_ACOMPTE ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_APPORTEUR ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;

    query = ' ALTER TABLE TA_INFOS_AVOIR ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_BONCDE ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_BONLIV ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_DEVIS ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_PRELEVEMENT ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
    
    query = ' ALTER TABLE TA_INFOS_PROFORMA ADD CODE_T_TVA_DOC DLGR_CODEL;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE MODIF_CODE_T_TVA_DOC
^
drop procedure MODIF_CODE_T_TVA_DOC
^

CREATE or ALTER  PROCEDURE MAJ_CODE_T_TVA_DOC_TIERS
as
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';

       query='UPDATE TA_TIERS SET ID_T_TVA_DOC = (select ID_T_TVA_DOC from TA_T_TVA_DOC where CODE_T_TVA_DOC like ''N'') where (ID_T_TVA_DOC is null)';
       EXECUTE STATEMENT :query;

END
^
EXECUTE PROCEDURE MAJ_CODE_T_TVA_DOC_TIERS
^
drop procedure MAJ_CODE_T_TVA_DOC_TIERS
^