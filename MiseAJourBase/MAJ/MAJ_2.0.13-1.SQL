CREATE or ALTER  PROCEDURE GESTION_Generateur
as
declare variable V_GEN integer;
declare variable V_MAX integer;
begin

select max(ID_T_TVA_DOC) from ta_t_tva_doc  into :v_max; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tva_doc, 0); V_GEN = gen_id(num_id_t_tva_doc, V_MAX - V_GEN);

    

END
^
EXECUTE PROCEDURE GESTION_Generateur
^
drop procedure GESTION_Generateur
^

