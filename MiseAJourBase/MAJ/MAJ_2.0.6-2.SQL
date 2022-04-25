


CREATE or ALTER  PROCEDURE GESTION_INSERTION_2
as    
DECLARE VARIABLE QUERY VARCHAR(1000);
BEGIN
    QUERY='';
    update TA_T_DOC c set c.code_t_doc='Facture' where c.code_t_doc like 'F';
    update TA_T_DOC c set c.code_t_doc='Devis' where c.code_t_doc like 'D';
    update TA_T_DOC c set c.code_t_doc='Boncde' where c.code_t_doc like 'BC';
    update TA_T_DOC c set c.code_t_doc='Avoir' where c.code_t_doc like 'A';
    update TA_T_DOC c set c.code_t_doc='Apporteur' where c.code_t_doc like 'AP';
    update TA_T_DOC c set c.code_t_doc='Acompte' where c.code_t_doc like 'AC';
    update TA_T_DOC c set c.code_t_doc='Proforma' where c.code_t_doc like 'P';
    update TA_T_DOC c set c.code_t_doc='Bonliv' where c.code_t_doc like 'BL';
    if(not exists(select * from ta_t_doc c  where c.code_t_doc like 'Prelevement'))
    then INSERT INTO TA_T_DOC (ID_T_DOC, CODE_T_DOC, LIB_T_DOC, QUI_CREE_T_DOC, QUAND_CREE_T_DOC, QUI_MODIF_T_DOC, QUAND_MODIF_T_DOC, "VERSION", IP_ACCES, VERSION_OBJ) VALUES (NULL, 'Prelevement', 'Prelevement', '', 'NOW', '', 'NOW', '', '0', '0');

END
^
EXECUTE PROCEDURE GESTION_INSERTION_2
^
drop procedure GESTION_INSERTION_2
^


CREATE OR ALTER TRIGGER TA_R_REGLEMENT_BD0 FOR TA_R_REGLEMENT
ACTIVE BEFORE DELETE POSITION 0
AS
begin
  if(not exists(select * from ta_r_reglement rr where rr.id_reglement = old.id_reglement))then
  delete from ta_reglement r where r.id_document=old.id_reglement;
end
^
