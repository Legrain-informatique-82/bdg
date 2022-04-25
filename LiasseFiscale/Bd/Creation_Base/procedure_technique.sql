/***********************************************/
CREATE PROCEDURE RECUP_IP_ACCES 
RETURNS (
    IP_ACCES VARCHAR(50))
AS
begin
  ip_acces = current_connection;
  suspend;
end
^

/***********************************************/
CREATE PROCEDURE RAZ_ACCES 
AS
begin
/*delete from ta_acces acces ;*/
suspend;
end
^

/***********************************************/
CREATE PROCEDURE NETTOYAGE 
AS
begin
/*delete from ta_modif modif where not exists*/
/*  (select ip_acces from ta_acces acces where acces.ip_acces=modif.ip_acces);*/
end
^


/***********************************************/
CREATE PROCEDURE MAJ_GENERATEUR 
AS
DECLARE VARIABLE V_GEN INTEGER;
DECLARE VARIABLE V_MAX INTEGER;
begin
/*select max(alias.ID_TIERS) from TA_TIERS alias into :V_MAX;if (v_max is null)*/
/*then v_max = 0; V_GEN = gen_id(NUM_ID_TIERS, 0); V_GEN = gen_id(NUM_ID_TIERS, V_MAX - V_GEN);*/
end
^

/***********************************************/
CREATE PROCEDURE ENREGISTRE_ACCES 
AS
begin
/*  if (not exists*/
/*  (select acces2.ip_acces from ta_acces acces2 where acces2.ip_acces=current_connection*/
/*  and acces2.user_acces=user)) then*/
/*  begin*/
/*    insert into ta_acces (user_acces)*/
/*    values (user) ;*/
/*  end*/
end
^
