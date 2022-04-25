CREATE or ALTER  PROCEDURE GESTION_INSERTION_1
as
declare variable idcomptebancaire integer;
begin
  idcomptebancaire=null;
  select cb.ID_COMPTE_BANQUE from ta_compte_banque cb rows 1 into :idcomptebancaire;
  if(idcomptebancaire is not null)then
  begin
     update ta_acompte a set ID_COMPTE_BANQUE=:idcomptebancaire where ID_COMPTE_BANQUE is null;
  end
end
^
EXECUTE PROCEDURE GESTION_INSERTION_1
^
drop procedure GESTION_INSERTION_1
^