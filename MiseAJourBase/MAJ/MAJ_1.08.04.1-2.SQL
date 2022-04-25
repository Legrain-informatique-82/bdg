RECREATE VIEW V_R_DOCUMENT(
    ID_R_DOCUMENT,
    ID_FACTURE,
    ID_DEVIS,
    ID_BONCDE,
    ID_AVOIR,
    ID_BONLIV,
    IP_ACCES)
AS
Select

  RDocument.ID_R_DOCUMENT,
  RDocument.ID_FACTURE,
  RDocument.ID_DEVIS,
  RDocument.ID_BONCDE,
  RDocument.ID_AVOIR,
  RDocument.ID_BONLIV,
    RDocument.IP_ACCES
  
from   TA_R_DOCUMENT RDocument
^


CREATE OR ALTER PROCEDURE CALCUL_TOTAL_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER)
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUX_R_HT NUMERIC(15,2),
    TAUX_R_TTC NUMERIC(15,2),
    REGLE NUMERIC(15,2),
    REMISE_HT NUMERIC(15,2),
    REMISE_TTC NUMERIC(15,2),
    MTNETHT NUMERIC(15,2),
    MTNETTTC NUMERIC(15,2),
    MTNETTVA NUMERIC(15,2),
    NETAPAYER NUMERIC(15,2),
    TVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TXREMISEHT NUMERIC(15,4);
DECLARE VARIABLE TXREMISETTC NUMERIC(15,4);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module) = 'FACTURE') then
begin
select sum(ldoc.mt_ht_l_facture),sum(ldoc.mt_ttc_l_facture),doc.regle_facture,
  doc.tx_rem_ht_facture, doc.tx_rem_ttc_facture, doc.rem_ht_facture, doc.rem_ttc_facture,
  sum(ldoc.mt_ht_l_facture)-doc.rem_ht_facture,doc.ttc
  from ta_l_facture ldoc, ta_facture doc
  where ldoc.id_facture=:id_document and  doc.id_facture=:id_document
  and ldoc.id_t_ligne = :typeligne
  group by doc.regle_facture,
  doc.tx_rem_ht_facture, doc.tx_rem_ttc_facture, doc.rem_ht_facture, doc.rem_ttc_facture
  ,doc.ttc
   into :mt_ht ,:totalttc ,:regle,:taux_r_ht,:taux_r_ttc,:remise_ht,:remise_ttc,:mtnetht,:ttc;
end
if (upper(:module) = 'DEVIS') then
begin
select sum(ldoc.mt_ht_l_devis),sum(ldoc.mt_ttc_l_devis),(0)as Regle_Devis,
  doc.tx_rem_ht_devis, doc.tx_rem_ttc_devis, doc.rem_ht_devis, doc.rem_ttc_devis,
  sum(ldoc.mt_ht_l_devis)-doc.rem_ht_devis,doc.ttc
  from ta_l_devis ldoc, ta_devis doc
  where ldoc.id_devis=:id_document and  doc.id_devis=:id_document
  and ldoc.id_t_ligne = :typeligne
  group by Regle_Devis,
  doc.tx_rem_ht_devis, doc.tx_rem_ttc_devis, doc.rem_ht_devis, doc.rem_ttc_devis
  ,doc.ttc
   into :mt_ht ,:totalttc ,:regle,:taux_r_ht,:taux_r_ttc,:remise_ht,:remise_ttc,:mtnetht,:ttc;
end
if (upper(:module) = 'BONLIV') then
begin
select sum(ldoc.mt_ht_l_bonliv),sum(ldoc.mt_ttc_l_bonliv),(0)as Regle_BONLIV,
  doc.tx_rem_ht_bonliv, doc.tx_rem_ttc_bonliv, doc.rem_ht_bonliv, doc.rem_ttc_bonliv,
  sum(ldoc.mt_ht_l_bonliv)-doc.rem_ht_bonliv,doc.ttc
  from ta_l_bonliv ldoc, ta_bonliv doc
  where ldoc.id_bonliv=:id_document and  doc.id_bonliv=:id_document
  and ldoc.id_t_ligne = :typeligne
  group by Regle_BONLIV,
  doc.tx_rem_ht_bonliv, doc.tx_rem_ttc_bonliv, doc.rem_ht_bonliv, doc.rem_ttc_bonliv
  ,doc.ttc
   into :mt_ht ,:totalttc ,:regle,:taux_r_ht,:taux_r_ttc,:remise_ht,:remise_ttc,:mtnetht,:ttc;
end

select sum(f.mt_tva_recup) from calcul_tva_direct(:module,:id_document,:taux_r_ht,:ttc) f into :MTNETTVA;
tva=:mtnettva;
mt_ttc=:totalttc;
mt_tva=:mt_ttc-:mt_ht;

if (:ttc=1) then
    begin
       txremiseht = taux_r_ht;
       mtnetttc=:mt_ttc - (:mt_ttc*(:txremiseht/100));
       MTNETHT=:mtnetttc - :MTNETTVA;
       remise_ht =  :totalttc - :mtnetttc ;
    end
else
    begin
      txremiseht = taux_r_ht;
      MTNETHT=:mt_ht-(:mt_ht*(:txremiseht/100));
      mtnetttc=:MTNETHT + :MTNETTVA;
      remise_ht = mt_ht - mtnetht;
    end
  txremisettc = taux_r_ttc;
  remise_ttc = (:mtnetttc * (:txremisettc/100));
  mtnetttc = :mtnetttc -:remise_ttc;
  netapayer = :mtnetttc - :regle;

suspend;
end
^

CREATE OR ALTER PROCEDURE CALCUL_TVA_SUR_MT_HT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,4),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
/* je passe par une variable :tva en numeric(15,4) car autrement la valeur est tronqué sans arrondi*/
TYPELIGNE=0;
mt_tva_recup=0;
Query='';
VALEURTTC=0;
LIBELLE_RECUP='';
CODE_TVA_RECUP='';
TAUX_RECUP=0;
select typeL.id_t_ligne from  ta_t_ligne typeL  where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:MODULE) = 'FACTURE') Then
   begin
        if (:ttc=1) then
            begin
                query ='select sum(ldoc.mt_ttc_l_facture),
                sum(ldoc.mt_ttc_l_facture)-((sum(ldoc.mt_ttc_l_facture)*100)/(100+ldoc.TAUX_TVA_L_FACTURE)),';
            end
        else
            begin
                query ='select sum(ldoc.mt_ht_l_facture),
                 sum(ldoc.mt_ht_l_facture)*(ldoc.TAUX_TVA_L_FACTURE/100),' ;
            end
        query = :query || ' ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          from ta_l_facture_temp ldoc where ldoc.id_facture =' ||:id_document||
        'and ldoc.id_t_ligne = '||:typeligne||
          'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
   end
if (upper(:MODULE) = 'DEVIS') Then
   begin
        if (:ttc=1) then
            begin
                query ='select sum(ldoc.mt_ttc_l_devis),
                sum(ldoc.mt_ttc_l_devis)-((sum(ldoc.mt_ttc_l_devis)*100)/(100+ldoc.TAUX_TVA_L_devis)),';
            end
        else
            begin
                query ='select sum(ldoc.mt_ht_l_devis),
                 sum(ldoc.mt_ht_l_devis)*(ldoc.TAUX_TVA_L_devis/100),' ;
            end
        query = :query || ' ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          from ta_l_devis_temp ldoc where ldoc.id_devis =' ||:id_document||
        'and ldoc.id_t_ligne = '||:typeligne||
          'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
   end
if (upper(:MODULE) = 'BONLIV') Then
   begin
        if (:ttc=1) then
            begin
                query ='select sum(ldoc.mt_ttc_l_bonLiv),
                sum(ldoc.mt_ttc_l_bonLiv)-((sum(ldoc.mt_ttc_l_bonLiv)*100)/(100+ldoc.TAUX_TVA_L_bonLiv)),';
            end
        else
            begin
                query ='select sum(ldoc.mt_ht_l_bonLiv),
                 sum(ldoc.mt_ht_l_bonLiv)*(ldoc.TAUX_TVA_L_bonLiv/100),' ;
            end
        query = :query || ' ldoc.code_tva_l_bonLiv,ldoc.taux_tva_l_bonLiv
          from ta_l_bonLiv_temp ldoc where ldoc.id_bonLiv =' ||:id_document||
        'and ldoc.id_t_ligne = '||:typeligne||
          'group by ldoc.code_tva_l_bonLiv,ldoc.taux_tva_l_bonLiv
          order by ldoc.code_tva_l_bonLiv,ldoc.taux_tva_l_bonLiv';
   end

if (:ttc=1) then
    begin
        FOR execute statement :query
          into : sumttc,tva,code_tva_recup,taux_recup
          do
          begin
              if(:taux_recup is null or (:tva is null) ) then
              begin
                  taux_recup=100;
                  id_document_recup=:id_document;
                  code_tva_recup='';
                  tva=0;
                  valeurttc=:sumttc;
                  suspend;
              end
              else
                  begin
                  if (:tauxremise is not null and :tauxremise > 0 ) then
                    begin
                        sumttc=:sumttc-(:sumttc*(:tauxremise/100));
                        tva=cast( sumttc-(:sumttc*100) / (100+:taux_recup)as numeric (15,2));
                    end
                    select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
                    id_document_recup=:id_document;
                    valeurttc=:sumttc;
                    mt_tva_recup = :mt_tva_recup + :tva;
                    Suspend;
                    mt_tva_recup = 0;
                  end
          end
    end
else
    begin
        FOR execute statement :query
          into : sumht,tva,code_tva_recup,taux_recup
          do
          begin
              if(:taux_recup is null or (:tva is null) ) then
              begin
                  taux_recup=100;
                  id_document_recup=:id_document;
                  code_tva_recup='';
                  tva=0;
                  valeurttc=:sumht;
                  suspend;
              end
              else
                  begin
                  if (:tauxremise is not null and :tauxremise > 0 ) then
                    begin
                        sumht=:sumht-(:sumht*(:tauxremise/100));
                        tva= cast( :sumht * (:taux_recup/100)as numeric (15,2));
                    end
                    select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
                    id_document_recup=:id_document;
                    valeurttc=:sumht;
                    mt_tva_recup = :mt_tva_recup + :tva;
                    Suspend;
                    mt_tva_recup = 0;
                  end
          end
    end
end
^


CREATE OR ALTER PROCEDURE CALCUL_TOTAL_TEMP (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISEHT NUMERIC(15,4),
    TTC INTEGER,
    TAUXESCOMPTE NUMERIC(15,4))
RETURNS (
    MT_HT NUMERIC(15,2),
    MT_TVA NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    NETHT NUMERIC(15,2),
    NETTTC NUMERIC(15,2),
    NETTVA NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V1 NUMERIC(15,2);
DECLARE VARIABLE V2 NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module)='FACTURE') then
begin
  select sum(ldoc.mt_ht_l_facture), sum(ldoc.mt_ttc_l_facture)
  from ta_l_facture_temp ldoc
  where ldoc.id_facture=:id_document and ldoc.id_t_ligne = :typeligne
  /*and ldoc.ip_acces = current_connection*/
   into :mt_ht, :mt_ttc;
end

if (upper(:module)='DEVIS') then
begin
  select sum(ldoc.mt_ht_l_devis), sum(ldoc.mt_ttc_l_devis)
  from ta_l_devis_temp ldoc
  where ldoc.id_devis=:id_document and ldoc.id_t_ligne = :typeligne
  and ldoc.ip_acces = current_connection
   into :mt_ht, :mt_ttc;
end
if (upper(:module)='BONLIV') then
begin
  select sum(ldoc.mt_ht_l_bonliv), sum(ldoc.mt_ttc_l_bonliv)
  from ta_l_bonliv_temp ldoc
  where ldoc.id_bonliv=:id_document and ldoc.id_t_ligne = :typeligne
  and ldoc.ip_acces = current_connection
   into :mt_ht, :mt_ttc;
end
if (:mt_ttc<> 0 ) then
/* mt_ttc = :mt_ht;*/
mt_tva=0;
else
mt_tva=:mt_ttc -:mt_ht;
select sum(f.mt_tva_recup) from calcul_tva_sur_mt_ht(:module,:id_document,:tauxremiseht,:ttc) f into :nettva;


if (:ttc=1) then
    begin
      v1=(:mt_ttc*(:tauxremiseht/100));
       netttc=:mt_ttc -v1;
       netht=:netttc - :nettva;
    end
else
    begin
      v1=(:mt_ht*(:tauxremiseht/100));
      netht=:mt_ht-v1;
      netttc=:netht + :nettva;
    end
    v2= (:netttc*(:tauxescompte/100));
    netttc = :netttc -v2;
suspend;
end
^


CREATE OR ALTER PROCEDURE CALCUL_TVA_DIRECT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TAUXREMISE NUMERIC(15,4),
    TTC INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP INTEGER,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    VALEURTTC NUMERIC(15,2))
AS
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE SUMHT NUMERIC(15,2);
DECLARE VARIABLE SUMTTC NUMERIC(15,2);
DECLARE VARIABLE QUERY VARCHAR(1000);
DECLARE VARIABLE TVA NUMERIC(15,4);
begin
TYPELIGNE=0;
mt_tva_recup = 0;
query = '';
select typeL.id_t_ligne from  ta_t_ligne typeL  where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module) = 'FACTURE') then
begin
if (:ttc=1) then
    begin
     query = 'select sum(ldoc.mt_ttc_l_facture),
        sum(ldoc.mt_ttc_l_facture)-((sum(ldoc.mt_ttc_l_facture)*100)/(100+ldoc.TAUX_TVA_L_FACTURE)),
        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
    end
else
    begin
     query = 'select sum(ldoc.mt_ht_l_facture),
         sum(ldoc.mt_ht_l_facture)*(ldoc.TAUX_TVA_L_FACTURE/100),
        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
    end
query = :query || ' from ta_l_facture ldoc where ldoc.id_facture =' ||:id_document||
        'and ldoc.id_t_ligne =' ||:typeligne||
          'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
          order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
end

if (upper(:module) = 'DEVIS') then
begin
if (:ttc=1) then
    begin
     query = 'select sum(ldoc.mt_ttc_l_devis),
        sum(ldoc.mt_ttc_l_devis)-((sum(ldoc.mt_ttc_l_devis)*100)/(100+ldoc.TAUX_TVA_L_devis)),
        ldoc.CODE_TVA_L_devis,ldoc.TAUX_TVA_L_devis';
    end
else
    begin
     query = 'select sum(ldoc.mt_ht_l_devis),
         sum(ldoc.mt_ht_l_devis)*(ldoc.TAUX_TVA_L_devis/100),
        ldoc.CODE_TVA_L_devis,ldoc.TAUX_TVA_L_devis';
    end
query = :query || ' from ta_l_devis ldoc where ldoc.id_devis =' ||:id_document||
        'and ldoc.id_t_ligne =' ||:typeligne||
          'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
          order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
end
if (upper(:module) = 'BONLIV') then
begin
if (:ttc=1) then
    begin
     query = 'select sum(ldoc.mt_ttc_l_bonLiv),
        sum(ldoc.mt_ttc_l_bonLiv)-((sum(ldoc.mt_ttc_l_bonLiv)*100)/(100+ldoc.TAUX_TVA_L_bonLiv)),
        ldoc.CODE_TVA_L_bonLiv,ldoc.TAUX_TVA_L_bonLiv';
    end
else
    begin
     query = 'select sum(ldoc.mt_ht_l_bonLiv),
         sum(ldoc.mt_ht_l_bonLiv)*(ldoc.TAUX_TVA_L_bonLiv/100),
        ldoc.CODE_TVA_L_bonLiv,ldoc.TAUX_TVA_L_bonLiv';
    end
query = :query || ' from ta_l_bonLiv ldoc where ldoc.id_bonLiv =' ||:id_document||
        'and ldoc.id_t_ligne =' ||:typeligne||
          'group by ldoc.code_tva_l_bonLiv,ldoc.taux_tva_l_bonLiv
          order by ldoc.code_tva_l_bonLiv,ldoc.taux_tva_l_bonLiv';
end
if (:ttc=1) then
    begin
        FOR execute statement :query
          into : sumttc,tva,code_tva_recup,taux_recup
          do
          begin
             if(:taux_recup is null or (:tva is null) ) then
              begin
                  taux_recup=100;
                  id_document_recup=:id_document;
                  code_tva_recup='';
                  tva=0;
                  valeurttc=:sumttc;
                  suspend;
              end
              else
              begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumttc=:sumttc-(:sumttc*(:tauxremise/100));
                tva=cast( sumttc-(:sumttc*100) / (100+:taux_recup)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumttc;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup=0;
          end
          end
    end
else
    begin
        FOR execute statement :query
          into : sumht,tva,code_tva_recup,taux_recup
          do
          begin
             if(:taux_recup is null or (:tva is null) ) then
              begin
                  taux_recup=100;
                  id_document_recup=:id_document;
                  code_tva_recup='';
                  tva=0;
                  valeurttc=:sumht;
                  suspend;
              end
              else
              begin
          if (:tauxremise is not null and :tauxremise > 0 ) then
            begin
                sumht=:sumht-(:sumht*(:tauxremise/100));
                tva= cast( :sumht * (:taux_recup/100)as numeric (15,2));
            end
            select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup into :libelle_recup;
            id_document_recup=:id_document;
            valeurttc=:sumht;
            mt_tva_recup = :mt_tva_recup + :tva;
            Suspend;
            mt_tva_recup=0;
          end
          end
    end
end
^






CREATE OR ALTER PROCEDURE INVERSE_NUM_LIGNE (
    MODULE VARCHAR(255),
    NUM_LIGNE INTEGER,
    NEWLIGNE INTEGER,
    ID_LIGNE_DEPART INTEGER)
AS
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE OLDLIGNE INTEGER;
DECLARE VARIABLE DESCENDANT INTEGER;
begin
if (upper(:module)='FACTURE') then
    begin
    select NUM_LIGNE_L_FACTURE from ta_l_facture_temp where ID_L_facture = :ID_LIGNE_DEPART
    and ip_acces=current_connection  into :oldligne;
    if((:oldligne < :NEWLIGNE))then
        begin
        for select id_l_facture from ta_l_facture_temp where num_ligne_l_facture between :oldligne
        and :NEWLIGNE and ip_acces=current_connection
        order by num_ligne_l_facture desc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then
                     update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE-1) where ID_L_facture = :id_ligne;
                   else
                     update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:NEWLIGNE) where ID_L_facture = :id_ligne;
            end
        end
    else
        begin
        for select id_l_facture from ta_l_facture_temp where num_ligne_l_facture between :NEWLIGNE
        and :oldligne  and ip_acces=current_connection
        order by num_ligne_l_facture asc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then  /*  */
                     update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE+1) where ID_L_facture = :id_ligne;
                   else
                     update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:NEWLIGNE) where ID_L_facture = :id_ligne;
            end
        end
    end
if (upper(:module)='DEVIS') then
    begin
    select NUM_LIGNE_L_DEVIS from ta_l_devis_temp where ID_L_DEVIS = :ID_LIGNE_DEPART
    and ip_acces=current_connection  into :oldligne;
    if((:oldligne < :NEWLIGNE))then
        begin
        for select id_l_DEVIS from ta_l_DEVIS_temp where num_ligne_l_DEVIS between :oldligne
        and :NEWLIGNE and ip_acces=current_connection
        order by num_ligne_l_DEVIS desc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(NUM_LIGNE_L_DEVIS-1) where ID_L_DEVIS = :id_ligne;
                   else
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(:NEWLIGNE) where ID_L_DEVIS = :id_ligne;
            end
        end
    else
        begin
        for select id_l_DEVIS from ta_l_DEVIS_temp where num_ligne_l_DEVIS between :NEWLIGNE
        and :oldligne  and ip_acces=current_connection
        order by num_ligne_l_DEVIS asc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then  /*  */
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(NUM_LIGNE_L_DEVIS+1) where ID_L_DEVIS = :id_ligne;
                   else
                     update ta_l_DEVIS_temp set NUM_LIGNE_L_DEVIS=(:NEWLIGNE) where ID_L_DEVIS = :id_ligne;
            end
        end
    end
if (upper(:module)='BONLIV') then
    begin
    select NUM_LIGNE_L_bonLiv from ta_l_bonLiv_temp where ID_L_bonLiv = :ID_LIGNE_DEPART
    and ip_acces=current_connection  into :oldligne;
    if((:oldligne < :NEWLIGNE))then
        begin
        for select id_l_bonLiv from ta_l_bonLiv_temp where num_ligne_l_bonLiv between :oldligne
        and :NEWLIGNE and ip_acces=current_connection
        order by num_ligne_l_bonLiv desc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then
                     update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(NUM_LIGNE_L_bonLiv-1) where ID_L_bonLiv = :id_ligne;
                   else
                     update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(:NEWLIGNE) where ID_L_bonLiv = :id_ligne;
            end
        end
    else
        begin
        for select id_l_bonLiv from ta_l_bonLiv_temp where num_ligne_l_bonLiv between :NEWLIGNE
        and :oldligne  and ip_acces=current_connection
        order by num_ligne_l_bonLiv asc into :id_ligne  do
            begin
                   if(:id_ligne <> :ID_LIGNE_DEPART) then  /*  */
                     update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(NUM_LIGNE_L_bonLiv+1) where ID_L_bonLiv = :id_ligne;
                   else
                     update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(:NEWLIGNE) where ID_L_bonLiv = :id_ligne;
            end
        end
    end
suspend;
end
^

CREATE OR ALTER PROCEDURE MAJ_NUM_LIGNE (
    MODULE VARCHAR(255),
    NUM_LIGNE INTEGER,
    ID_LIGNE INTEGER,
    SUPPR INTEGER)
AS
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE NUM INTEGER;
DECLARE VARIABLE MAJ INTEGER;
DECLARE VARIABLE INC INTEGER;
begin
 maj=0;
 inc=0;
if (upper(:module)='FACTURE') then
    begin
     if(:suppr=0) then
         begin
         for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
         where ip_acces=current_connection
         order by lfacttemp.num_ligne_l_facture asc,
         lfacttemp.quand_cree_l_facture desc
         into :id, :num do
             begin
                if(:maj=1) then
                    begin
                        update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(NUM_LIGNE_L_FACTURE+1) where ID_L_FACTURE = :id;
                    end
                if(:num_ligne=:num) then
                    begin
                        maj= 1;
                    end
             end
         end 
     else
         begin
             for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
             where ip_acces=current_connection
             order by lfacttemp.num_ligne_l_facture asc,
         lfacttemp.quand_cree_l_facture desc
         into :id, :num do
            begin
                update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=(:inc) where ID_L_FACTURE = :id;
                inc=inc+1;
            end
         end
    
     maj=0;
     for select ID_L_FACTURE, NUM_LIGNE_L_FACTURE from ta_l_facture_temp lfacttemp 
     where ip_acces=current_connection
     order by lfacttemp.num_ligne_l_facture asc
     into :id, :num do
         begin
            update ta_l_facture_temp set NUM_LIGNE_L_FACTURE=:maj where ID_L_FACTURE = :id;
            maj=maj+1;
         end
    end
if (upper(:module)='DEVIS') then
    begin
     if(:suppr=0) then
         begin
         for select ID_L_DEVIS, NUM_LIGNE_L_DEVIS from ta_l_DEVIS_temp ldoc
         where ip_acces=current_connection
         order by ldoc.num_ligne_l_devis asc,
         ldoc.quand_cree_l_devis desc
         into :id, :num do
             begin
                if(:maj=1) then
                    begin
                        update ta_l_devis_temp set NUM_LIGNE_L_devis=(NUM_LIGNE_L_devis+1) where ID_L_devis = :id;
                    end
                if(:num_ligne=:num) then
                    begin
                        maj= 1;
                    end
             end
         end 
     else
         begin
             for select ID_L_devis, NUM_LIGNE_L_devis from ta_l_devis_temp ldoc
             where ip_acces=current_connection
             order by ldoc.num_ligne_l_devis asc,
         ldoc.quand_cree_l_devis desc
         into :id, :num do
            begin
                update ta_l_devis_temp set NUM_LIGNE_L_devis=(:inc) where ID_L_devis = :id;
                inc=inc+1;
            end
         end
    
     maj=0;
     for select ID_L_devis, NUM_LIGNE_L_devis from ta_l_devis_temp ldoc
     where ip_acces=current_connection
     order by ldoc.num_ligne_l_devis asc
     into :id, :num do
         begin
            update ta_l_devis_temp set NUM_LIGNE_L_devis=:maj where ID_L_devis = :id;
            maj=maj+1;
         end
    end
if (upper(:module)='BONLIV') then
    begin
     if(:suppr=0) then
         begin
         for select ID_L_bonLiv, NUM_LIGNE_L_bonLiv from ta_l_bonLiv_temp ldoc
         where ip_acces=current_connection
         order by ldoc.num_ligne_l_bonLiv asc,
         ldoc.quand_cree_l_bonLiv desc
         into :id, :num do
             begin
                if(:maj=1) then
                    begin
                        update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(NUM_LIGNE_L_bonLiv+1) where ID_L_bonLiv = :id;
                    end
                if(:num_ligne=:num) then
                    begin
                        maj= 1;
                    end
             end
         end 
     else
         begin
             for select ID_L_bonLiv, NUM_LIGNE_L_bonLiv from ta_l_bonLiv_temp ldoc
             where ip_acces=current_connection
             order by ldoc.num_ligne_l_bonLiv asc,
         ldoc.quand_cree_l_bonLiv desc
         into :id, :num do
            begin
                update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=(:inc) where ID_L_bonLiv = :id;
                inc=inc+1;
            end
         end
    
     maj=0;
     for select ID_L_bonLiv, NUM_LIGNE_L_bonLiv from ta_l_bonLiv_temp ldoc
     where ip_acces=current_connection
     order by ldoc.num_ligne_l_bonLiv asc
     into :id, :num do
         begin
            update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv=:maj where ID_L_bonLiv = :id;
            maj=maj+1;
         end
    end
suspend;
end
^

CREATE OR ALTER PROCEDURE NETTOYAGE 
AS
begin
delete from ta_modif modif where not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=modif.ip_acces);
delete from ta_l_facture_temp temp where  not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=temp.ip_acces);
delete from ta_l_devis_temp temp where  not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=temp.ip_acces);
delete from ta_l_bonliv_temp temp where  not exists
  (select ip_acces from ta_acces acces where acces.ip_acces=temp.ip_acces);
end
^

CREATE OR ALTER PROCEDURE RECUP_LIGNES_BONLIV (
    CODE_DOCUMENT_RECUP VARCHAR(50))
RETURNS (
    ID_DOCUMENT_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
execute procedure vide_document_temp('bonLiv');

select ID_bonLiv from ta_bonLiv where CODE_bonLiv = :code_document_recup into :id_document_recup;

FOR select ldoc.id_l_bonLiv,
   ldoc.id_bonLiv,
    ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_bonLiv,
ldoc.lib_l_bonLiv, ldoc.qte_l_bonLiv, ldoc.u1_l_bonLiv, ldoc.u2_l_bonLiv,
ldoc.prix_u_l_bonLiv, ldoc.taux_tva_l_bonLiv,ldoc.compte_l_bonLiv, ldoc.code_tva_l_bonLiv,
ldoc.code_t_tva_l_bonLiv, ldoc.mt_ht_l_bonLiv,ldoc.mt_ttc_l_bonLiv, ldoc.rem_tx_l_bonLiv,
ldoc.rem_ht_l_bonLiv from ta_l_bonLiv ldoc
where ldoc.id_bonLiv = :id_document_recup
into
    :v_id_l_document,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT
do
  begin

     insert into ta_l_bonLiv_temp (
    ID_L_bonLiv,
    ID_bonLiv,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_bonLiv,
    LIB_L_bonLiv,
    QTE_L_bonLiv,
    U1_L_bonLiv,
    U2_L_bonLiv,
    PRIX_U_L_bonLiv,
    TAUX_TVA_L_bonLiv,
    compte_l_bonLiv,
    CODE_TVA_L_bonLiv,
    CODE_T_TVA_L_bonLiv,
    MT_HT_L_bonLiv,
    MT_TTC_L_bonLiv,
    REM_TX_L_bonLiv,
    REM_HT_L_bonLiv,
    IP_ACCES
    ) values(
    :V_ID_L_DOCUMENT,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT,
    Current_Connection) ;
    /*suspend; */
  end
  suspend;
end
^


CREATE OR ALTER PROCEDURE RECUP_LIGNES_DOCUMENT (
    MODULE_SOURCE VARCHAR(255),
    MODULE_DEST VARCHAR(255),
    VIDE_TABLE INTEGER,
    CODE_DOCUMENT_RECUP VARCHAR(50))
RETURNS (
    ID_DOCUMENT_RECUP INTEGER)
AS
DECLARE VARIABLE QUERY VARCHAR(1000);
begin
query='';
if (:vide_table = 1) then execute procedure vide_document_temp(:module_dest);
if (upper(:module_source)='FACTURE') then
    begin
        select id_facture from ta_facture f where f.code_facture = :code_document_recup into :id_document_recup;
    end
if (upper(:module_source)='DEVIS') then
    begin
        select id_devis from ta_devis f where f.code_devis = :code_document_recup into :id_document_recup;
    end
if (upper(:module_source)='BONLIV') then
    begin
        select id_bonLiv from ta_bonLiv f where f.code_bonLiv = :code_document_recup into :id_document_recup;
    end
if (upper(:module_dest)='FACTURE') then
    begin
        query = 'insert into ta_l_facture_temp ';
    end
if (upper(:module_dest)='DEVIS') then
    begin
        query = 'insert into ta_l_devis_temp ';
    end
if (upper(:module_dest)='BONLIV') then
    begin
        query = 'insert into ta_l_bonliv_temp ';
    end
       if (upper(:module_source)='FACTURE') then  query = :query ||'select 0,
        ldoc.id_facture,
        ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_facture,
        ldoc.lib_l_facture, ldoc.qte_l_facture, ldoc.u1_l_facture, ldoc.u2_l_facture,
        ldoc.prix_u_l_facture, ldoc.taux_tva_l_facture,ldoc.compte_l_facture,
        ldoc.code_tva_l_facture,ldoc.code_t_tva_l_facture,
        ldoc.mt_ht_l_facture,
        ldoc.mt_ttc_l_facture, ldoc.rem_tx_l_facture, ldoc.rem_ht_l_facture,
        null,null,null,null, null,null from ta_l_facture ldoc where ldoc.id_facture ='|| :id_document_recup;
       if (upper(:module_source)='DEVIS') then  query = :query ||'select 0,
        ldoc.id_devis,
        ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_devis,
        ldoc.lib_l_devis, ldoc.qte_l_devis, ldoc.u1_l_devis, ldoc.u2_l_devis,
        ldoc.prix_u_l_devis, ldoc.taux_tva_l_devis,ldoc.compte_l_devis,
        ldoc.code_tva_l_devis,ldoc.code_t_tva_l_devis,
        ldoc.mt_ht_l_devis,
        ldoc.mt_ttc_l_devis, ldoc.rem_tx_l_devis, ldoc.rem_ht_l_devis,
        null,null,null,null, null,null from ta_l_devis ldoc where ldoc.id_devis ='|| :id_document_recup;
       if (upper(:module_source)='BONLIV') then  query = :query ||'select 0,
        ldoc.id_bonLiv,
        ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_bonLiv,
        ldoc.lib_l_bonLiv, ldoc.qte_l_bonLiv, ldoc.u1_l_bonLiv, ldoc.u2_l_bonLiv,
        ldoc.prix_u_l_bonLiv, ldoc.taux_tva_l_bonLiv,ldoc.compte_l_bonLiv,
        ldoc.code_tva_l_bonLiv,ldoc.code_t_tva_l_bonLiv,
        ldoc.mt_ht_l_bonLiv,
        ldoc.mt_ttc_l_bonLiv, ldoc.rem_tx_l_bonLiv, ldoc.rem_ht_l_bonLiv,
        null,null,null,null, null,null from ta_l_bonLiv ldoc where ldoc.id_bonLiv ='|| :id_document_recup;

  if (:query<>'') then execute statement :query;
end
^


CREATE OR ALTER PROCEDURE CREE_DOCUMENT_SUR_DOCUMENT (
    CODEFIXE VARCHAR(9),
    MODULE_SOURCE VARCHAR(255),
    MODULE_DEST VARCHAR(255),
    QUERY_IN VARCHAR(1000))
RETURNS (
    CODE_DOCUMENT_TMP VARCHAR(9))
AS
DECLARE VARIABLE ID_DOCUMENT INTEGER;
DECLARE VARIABLE NB_LIGNE INTEGER;
DECLARE VARIABLE CODE_DOCUMENT VARCHAR(9);
DECLARE VARIABLE ID_DOC_NEW INTEGER;
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE QUERY_LOC1 VARCHAR(1000);
DECLARE VARIABLE QUERY_LOC2 VARCHAR(1000);
begin
execute procedure maj_generateur;
execute procedure vide_document_temp(:module_source);
code_document_tmp='';
    /*query_in ='select code_facture,id_facture from ta_facture where  id_facture =5 or id_facture =6 or id_facture =7 ';*/
    if(upper(:module_dest)='FACTURE')then
        begin
            id_doc_new=GEN_ID(num_id_facture,1) ;
        end
    if(upper(:module_dest)='DEVIS')then
        begin
            id_doc_new=GEN_ID(num_id_devis,1) ;
        end
    if(upper(:module_dest)='BONLIV')then
        begin
            id_doc_new=GEN_ID(num_id_bonliv,1) ;
        end
    code_document_tmp= CODEFIXE;
    for execute statement :query_in
     into :code_document,id_document do
     begin
         execute procedure recup_lignes_document(:module_source,:module_dest,0,:code_document) returning_values :id_document ;
     end
    nb_ligne=0;
    if(upper(:module_dest)='FACTURE')then
        begin
            update ta_l_facture_temp set id_l_facture =GEN_ID(num_id_l_facture,1) where id_facture = :id_document;
            update ta_l_facture_temp set id_facture =:id_doc_new where id_facture = :id_document;

           /* Récupérer les infos à partir du 1er document*/
            /*Créer le document dest*/
            query_loc1 = ' insert into ta_facture (id_facture,code_facture,date_facture,date_ech_facture,
            date_liv_facture,libelle_facture,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
            ,id_c_paiement,rem_ht_facture,tx_rem_ht_facture,rem_ttc_facture,tx_rem_ttc_facture
            ,nb_e_facture,ttc,export)';

            query_loc2 = ' insert into ta_infos_facture  (id_infos_facture,id_facture,adresse1,adresse2
            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
             select GEN_ID(num_id_infos_facture,1),'||:id_doc_new;
        end

    if(upper(:module_dest)='DEVIS')then
        begin
            for select id_l_devis from ta_l_devis_temp where id_devis = :id_document into :id_ligne
            do
            begin
               update ta_l_devis_temp set NUM_LIGNE_L_devis =:nb_ligne where id_l_devis = :id_ligne ;
               nb_ligne = :nb_ligne +1;
            end
            update ta_l_devis_temp set id_l_devis =GEN_ID(num_id_l_devis,1) where id_devis = :id_document;
            update ta_l_devis_temp set id_devis =:id_doc_new where id_devis = :id_document;
        
           /* Récupérer les infos à partir du 1er document*/
            /*Créer le document dest*/
            query_loc1 = ' insert into ta_devis (id_devis,code_devis,date_devis,date_ech_devis,
            date_liv_devis,libelle_devis,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
            ,id_c_paiement,rem_ht_devis,tx_rem_ht_devis,rem_ttc_devis,tx_rem_ttc_devis
            ,nb_e_devis,ttc,export)';


            query_loc2 = ' insert into ta_infos_devis  (id_infos_devis,id_devis,adresse1,adresse2
            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
            select GEN_ID(num_id_infos_devis,1) ,'||:id_doc_new ;
        end
    if(upper(:module_dest)='BONLIV')then
        begin
            for select id_l_bonLiv from ta_l_bonLiv_temp where id_bonliv = :id_document into :id_ligne
            do
            begin
               update ta_l_bonLiv_temp set NUM_LIGNE_L_bonLiv =:nb_ligne where id_l_bonLiv = :id_ligne ;
               nb_ligne = :nb_ligne +1;
            end
            update ta_l_bonLiv_temp set id_l_bonLiv =GEN_ID(num_id_l_bonLiv,1) where id_bonliv = :id_document;
            update ta_l_bonLiv_temp set id_bonLiv =:id_doc_new where id_bonliv = :id_document;
        
           /* Récupérer les infos à partir du 1er document*/
            /*Créer le document dest*/
            query_loc1 = ' insert into ta_bonLiv (id_bonLiv,code_bonLiv,date_bonLiv,date_ech_bonLiv,
            date_liv_bonLiv,libelle_bonLiv,id_adresse,id_adresse_liv,id_tiers,id_t_paiement
            ,id_c_paiement,rem_ht_bonLiv,tx_rem_ht_bonLiv,rem_ttc_bonLiv,tx_rem_ttc_bonLiv
            ,nb_e_bonLiv,ttc,export)';


            query_loc2 = ' insert into ta_infos_bonLiv  (id_infos_bonLiv,id_bonLiv,adresse1,adresse2
            ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
            ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
            code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement)
            select GEN_ID(num_id_infos_bonLiv,1) ,'||:id_doc_new ;
        end           /* Récupérer les infos à partir du 1er document*/
            /*Créer le document dest*/
            if (upper(:module_source)='FACTURE') then
                begin
                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_facture,doc2.date_ech_facture,
                    doc2.date_liv_facture,doc2.libelle_facture,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
                    ,doc2.id_c_paiement,doc2.rem_ht_facture,doc2.tx_rem_ht_facture,doc2.rem_ttc_facture,doc2.tx_rem_ttc_facture
                    ,doc2.nb_e_facture,doc2.ttc,doc2.export from ta_facture doc2 where doc2.id_facture ='|| :id_document;
        
                    query_loc2 = query_loc2 ||',adresse1,adresse2
                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
                    from ta_infos_facture where id_facture ='|| :id_document;
                end

            if (upper(:module_source)='DEVIS') then
                begin
                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_devis,doc2.date_ech_devis,
                    doc2.date_liv_devis,doc2.libelle_devis,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
                    ,doc2.id_c_paiement,doc2.rem_ht_devis,doc2.tx_rem_ht_devis,doc2.rem_ttc_devis,doc2.tx_rem_ttc_devis
                    ,doc2.nb_e_devis,doc2.ttc,doc2.export from ta_devis doc2 where doc2.id_devis ='|| :id_document;

                    query_loc2 = query_loc2 ||',adresse1,adresse2
                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
                    from ta_infos_devis where id_devis ='|| :id_document;
                end
            if (upper(:module_source)='BONLIV') then
                begin
                    query_loc1 = query_loc1 ||'select '||:id_doc_new||','''||:code_document_tmp||''',doc2.date_bonLiv,doc2.date_bonLiv,
                    doc2.date_liv_bonLiv,doc2.libelle_bonLiv,doc2.id_adresse,doc2.id_adresse_liv,doc2.id_tiers,doc2.id_t_paiement
                    ,doc2.id_c_paiement,doc2.rem_ht_bonLiv,doc2.tx_rem_ht_bonLiv,doc2.rem_ttc_bonLiv,doc2.tx_rem_ttc_bonLiv
                    ,doc2.nb_e_bonLiv,doc2.ttc,0 from ta_bonLiv doc2 where doc2.id_bonLiv ='|| :id_document;

                    query_loc2 = query_loc2 ||',adresse1,adresse2
                    ,adresse3,codepostal,ville,pays,adresse1_liv,adresse2_liv,adresse3_liv,codepostal_liv,
                    ville_liv,pays_liv,code_compta,compte,nom_tiers,prenom_tiers,surnom_tiers,code_t_civilite,
                    code_t_entite,tva_i_com_compl,code_c_paiement,lib_c_paiement,report_c_paiement,fin_mois_c_paiement
                    from ta_infos_bonLiv where id_bonLiv ='|| :id_document;
                end
     if (:query_loc1<>'') then execute statement :query_loc1;
     if (:query_loc2<>'') then execute statement :query_loc2;

    for execute statement :query_in
     into :code_document,id_document do
     begin
              if (upper(:module_dest)='FACTURE') then
                  begin
                     if (upper(:module_source)='DEVIS') then
                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_FACTURE)
                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                     if (upper(:module_source)='BONLIV') then
                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_FACTURE)
                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                  end
              if (upper(:module_dest)='DEVIS') then
                  begin
                     if (upper(:module_source)='FACTURE') then
                        insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_DEVIS)
                        values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                     if (upper(:module_source)='BONLIV') then
                       insert into ta_r_document  (ID_R_DOCUMENT,ID_bonLiv,ID_DEVIS)
                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                  end
              if (upper(:module_dest)='BONLIV') then
                  begin
                     if (upper(:module_source)='FACTURE') then
                        insert into ta_r_document  (ID_R_DOCUMENT,ID_FACTURE,ID_bonLiv)
                        values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                     if (upper(:module_source)='DEVIS') then
                       insert into ta_r_document  (ID_R_DOCUMENT,ID_DEVIS,ID_bonLiv)
                          values(gen_id(num_id_r_document,1),:id_document,:id_doc_new);
                  end
     end
execute procedure vide_document_temp(:module_source);
  suspend;
end
^



CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_BONLIV (
    NEW_ID_BONLIV INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_BONLIV from ta_BONLIV f where f.id_BONLIV=:new_id_BONLIV into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('BONLIV',0,:ttc,:v_rem_ht_l_document);
FOR select lBONLIV.id_l_BONLIV,
   lBONLIV.id_BONLIV,
    lBONLIV.id_t_ligne, lBONLIV.id_article, lBONLIV.num_ligne_l_BONLIV,
lBONLIV.lib_l_BONLIV, lBONLIV.qte_l_BONLIV, lBONLIV.u1_l_BONLIV, lBONLIV.u2_l_BONLIV,
lBONLIV.prix_u_l_BONLIV, lBONLIV.taux_tva_l_BONLIV,lBONLIV.compte_l_BONLIV, lBONLIV.code_tva_l_BONLIV,
lBONLIV.code_t_tva_l_BONLIV, lBONLIV.mt_ht_l_BONLIV,
lBONLIV.mt_ttc_l_BONLIV, lBONLIV.rem_tx_l_BONLIV, lBONLIV.rem_ht_l_BONLIV
from ta_l_BONLIV_temp lBONLIV
where lBONLIV.ip_acces = current_connection
into
    :v_id_l_document,
    :v_id_document,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document
do
  begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
    select art.numcpt_article from ta_article art where art.id_article = :v_id_article
    into :v_compte_l_document;
     insert into ta_l_BONLIV (
    ID_L_BONLIV,
    ID_BONLIV,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_BONLIV,
    LIB_L_BONLIV,
    QTE_L_BONLIV,
    U1_L_BONLIV,
    U2_L_BONLIV,
    PRIX_U_L_BONLIV,
    TAUX_TVA_L_BONLIV,
    compte_l_BONLIV,
    CODE_TVA_L_BONLIV,
    CODE_T_TVA_L_BONLIV,
    MT_HT_L_BONLIV,
    MT_TTC_L_BONLIV,
    REM_TX_L_BONLIV,
    REM_HT_L_BONLIV
    ) values(
    :v_id_l_document,
    :new_id_BONLIV,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document) ;
    delete from ta_l_BONLIV_temp where ID_L_BONLIV = :v_id_l_document and
    ip_acces=current_connection;
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE MAJ_LIGNES_BONLIV (
    ID_DOCUMENT_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_DOCUMENT_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
    delete from ta_l_BONLIV lBONLIV where lBONLIV.id_BONLIV=:id_document_recup  and
    not (lBONLIV.id_l_BONLIV in (
    select id_l_BONLIV from ta_l_BONLIV_temp where (id_BONLIV=:id_document_recup or (id_BONLIV=-1))and
    ip_acces=current_connection ));
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select D.tx_rem_ht_BONLIV from ta_BONLIV D where d.id_BONLIV=:id_document_recup into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('BONLIV',:id_document_recup,:ttc,:v_rem_ht_l_document);
  FOR select lBONLIV.id_l_BONLIV,
    lBONLIV.id_BONLIV,
    lBONLIV.id_t_ligne, lBONLIV.id_article, lBONLIV.num_ligne_l_BONLIV,
    lBONLIV.lib_l_BONLIV, lBONLIV.qte_l_BONLIV, lBONLIV.u1_l_BONLIV, lBONLIV.u2_l_BONLIV,
    lBONLIV.prix_u_l_BONLIV, lBONLIV.taux_tva_l_BONLIV,lBONLIV.compte_l_BONLIV, lBONLIV.code_tva_l_BONLIV,
    lBONLIV.code_t_tva_l_BONLIV, lBONLIV.mt_ht_l_BONLIV,
    lBONLIV.mt_ttc_l_BONLIV, lBONLIV.rem_tx_l_BONLIV, lBONLIV.rem_ht_l_BONLIV from ta_l_BONLIV_temp lBONLIV
    where lBONLIV.id_BONLIV = :id_document_recup and lBONLIV.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document = null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select lBONLIV.id_l_BONLIV from ta_l_BONLIV lBONLIV
        where lBONLIV.id_l_BONLIV = :v_id_l_document into :id_l_document_courant;
        if (:id_l_document_courant is not null) then
        begin
            update ta_l_BONLIV doc set
            doc.id_l_BONLIV = :v_id_l_document,
            doc.id_BONLIV = :v_id_document,
            doc.ID_T_LIGNE = :V_ID_T_LIGNE,
            doc.ID_ARTICLE = :V_ID_ARTICLE,
            doc.num_ligne_l_BONLIV = :v_num_ligne_l_document,
            doc.lib_l_BONLIV = :v_lib_l_document,
            doc.qte_l_BONLIV = :v_qte_l_document,
            doc.u1_l_BONLIV = :v_u1_l_document,
            doc.u2_l_BONLIV = :v_u2_l_document,
            doc.prix_u_l_BONLIV = :v_prix_u_l_document,
            doc.taux_tva_l_BONLIV = :v_taux_tva_l_document,
            doc.compte_l_BONLIV = :v_compte_l_document,
            doc.code_tva_l_BONLIV = :v_code_tva_l_document,
            doc.code_t_tva_l_BONLIV = :v_code_t_tva_l_document,
            doc.mt_ht_l_BONLIV = :v_mt_ht_l_document,
            doc.mt_ttc_l_BONLIV = :v_mt_ttc_l_document,
            doc.rem_tx_l_BONLIV = :v_rem_tx_l_document,
            doc.rem_ht_l_BONLIV = :v_rem_ht_l_document
            where doc.id_l_BONLIV = :v_id_l_document;
            update ta_l_BONLIV_temp set id_BONLIV = -1 where  id_l_BONLIV=:id_l_document_courant ;
        end
       end
  FOR select lBONLIV.id_l_BONLIV,
    lBONLIV.id_BONLIV,
    lBONLIV.id_t_ligne, lBONLIV.id_article, lBONLIV.num_ligne_l_BONLIV,
    lBONLIV.lib_l_BONLIV, lBONLIV.qte_l_BONLIV, lBONLIV.u1_l_BONLIV, lBONLIV.u2_l_BONLIV,
    lBONLIV.prix_u_l_BONLIV, lBONLIV.taux_tva_l_BONLIV,lBONLIV.compte_l_BONLIV, lBONLIV.code_tva_l_BONLIV,
    lBONLIV.code_t_tva_l_BONLIV, lBONLIV.mt_ht_l_BONLIV,
    lBONLIV.mt_ttc_l_BONLIV, lBONLIV.rem_tx_l_BONLIV, lBONLIV.rem_ht_l_BONLIV from ta_l_BONLIV_temp lBONLIV
    where lBONLIV.id_BONLIV = :id_document_recup  and lBONLIV.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_document;
            insert into ta_l_BONLIV  (
            id_l_BONLIV,
            id_BONLIV,
            ID_T_LIGNE,
            ID_ARTICLE,
            num_ligne_l_BONLIV,
            lib_l_BONLIV,
            qte_l_BONLIV,
            u1_l_BONLIV,
            u2_l_BONLIV,
            prix_u_l_BONLIV,
            taux_tva_l_BONLIV,
            compte_l_BONLIV,
            code_tva_l_BONLIV,
            code_t_tva_l_BONLIV,
            mt_ht_l_BONLIV,
            mt_ttc_l_BONLIV,
            rem_tx_l_BONLIV,
            rem_ht_l_BONLIV
            ) values(
            :v_id_l_document,
            :v_id_document,
            :V_ID_T_LIGNE,
            :V_ID_ARTICLE,
            :v_num_ligne_l_document,
            :v_lib_l_document,
            :v_qte_l_document,
            :v_u1_l_document,
            :v_u2_l_document,
            :v_prix_u_l_document,
            :v_taux_tva_l_document,
            :v_compte_l_document,
            :v_code_tva_l_document,
            :v_code_t_tva_l_document,
            :v_mt_ht_l_document,
            :v_mt_ttc_l_document,
            :v_rem_tx_l_document,
            :v_rem_ht_l_document);
       end 

   execute procedure vide_document_temp('BONLIV');
    suspend;
end
^


CREATE OR ALTER PROCEDURE MAJ_GENERATEUR
AS
DECLARE VARIABLE V_GEN INTEGER;
DECLARE VARIABLE V_MAX INTEGER;
begin
select max(alias.ID_TIERS) from TA_TIERS alias into :V_MAX;if (v_max is null)
then v_max = 0; V_GEN = gen_id(NUM_ID_TIERS, 0); V_GEN = gen_id(NUM_ID_TIERS, V_MAX - V_GEN);
select max(alias.id_adresse) from ta_adresse alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_adresse, 0); V_GEN = gen_id(num_id_adresse, V_MAX - V_GEN);
select max(alias.id_article) from ta_article alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_article, 0); V_GEN = gen_id(num_id_article, V_MAX - V_GEN);
select max(alias.id_avoir) from ta_avoir alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_avoir, 0); V_GEN = gen_id(num_id_avoir, V_MAX - V_GEN);
select max(alias.id_banque) from ta_banque alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_banque, 0); V_GEN = gen_id(num_id_banque, V_MAX - V_GEN);
select max(alias.id_boncde) from ta_boncde alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_boncde, 0); V_GEN = gen_id(num_id_boncde, V_MAX - V_GEN);
select max(alias.id_bonliv) from ta_bonliv alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_bonliv, 0); V_GEN = gen_id(num_id_bonliv, V_MAX - V_GEN);
select max(alias.id_c_paiement) from ta_c_paiement alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_c_paiement, 0); V_GEN = gen_id(num_id_c_paiement, V_MAX - V_GEN);
select max(alias.id_com_doc) from ta_com_doc alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_com_doc, 0); V_GEN = gen_id(num_id_com_doc, V_MAX - V_GEN);
select max(alias.id_commentaire) from ta_commentaire alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_commentaire, 0); V_GEN = gen_id(num_id_commentaire, V_MAX - V_GEN);
select max(alias.id_compl) from ta_compl alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_compl, 0); V_GEN = gen_id(num_id_compl, V_MAX - V_GEN);
select max(alias.id_devis) from ta_devis alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_devis, 0); V_GEN = gen_id(num_id_devis, V_MAX - V_GEN);
select max(alias.id_email) from ta_email alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_email, 0); V_GEN = gen_id(num_id_email, V_MAX - V_GEN);
select max(alias.id_entreprise) from ta_entreprise alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_entreprise, 0); V_GEN = gen_id(num_id_entreprise, V_MAX - V_GEN);
select max(alias.id_facture) from ta_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_facture, 0); V_GEN = gen_id(num_id_facture, V_MAX - V_GEN);
select max(alias.id_famille) from ta_famille alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_famille, 0); V_GEN = gen_id(num_id_famille, V_MAX - V_GEN);
select max(alias.id_l_avoir) from ta_l_avoir alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_avoir, 0); V_GEN = gen_id(num_id_l_avoir, V_MAX - V_GEN);
select max(alias.id_l_boncde) from ta_l_boncde alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_boncde, 0); V_GEN = gen_id(num_id_l_boncde, V_MAX - V_GEN);
select max(alias.id_l_bonliv) from ta_l_bonliv alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_bonliv, 0); V_GEN = gen_id(num_id_l_bonliv, V_MAX - V_GEN);
select max(alias.id_l_devis) from ta_l_devis alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_devis, 0); V_GEN = gen_id(num_id_l_devis, V_MAX - V_GEN);
select max(alias.id_l_facture) from ta_l_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_facture, 0); V_GEN = gen_id(num_id_l_facture, V_MAX - V_GEN);
select max(alias.id_l_modele) from ta_l_modele alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_l_modele, 0); V_GEN = gen_id(num_id_l_modele, V_MAX - V_GEN);
select max(alias.id_prix) from ta_prix alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_prix, 0); V_GEN = gen_id(num_id_prix, V_MAX - V_GEN);
select max(alias.id_r_adr) from ta_r_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_adr, 0); V_GEN = gen_id(num_id_r_adr, V_MAX - V_GEN);
select max(alias.id_r_adr_t_adr) from ta_r_adr_t_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_adr_t_adr, 0); V_GEN = gen_id(num_id_r_adr_t_adr, V_MAX - V_GEN);
select max(alias.id_r_document) from ta_r_document alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_document, 0); V_GEN = gen_id(num_id_r_document, V_MAX - V_GEN);
select max(alias.id_r_email) from ta_r_email alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_email, 0); V_GEN = gen_id(num_id_r_email, V_MAX - V_GEN);
select max(alias.id_r_tel) from ta_r_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_tel, 0); V_GEN = gen_id(num_id_r_tel, V_MAX - V_GEN);
select max(alias.id_r_tel_t_tel) from ta_r_tel_t_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_tel_t_tel, 0); V_GEN = gen_id(num_id_r_tel_t_tel, V_MAX - V_GEN);
select max(alias.id_r_web) from ta_r_web alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_r_web, 0); V_GEN = gen_id(num_id_r_web, V_MAX - V_GEN);
select max(alias.id_ref_prix) from ta_ref_prix alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_ref_prix, 0); V_GEN = gen_id(num_id_ref_prix, V_MAX - V_GEN);
select max(alias.id_t_adr) from ta_t_adr alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_adr, 0); V_GEN = gen_id(num_id_t_adr, V_MAX - V_GEN);
select max(alias.id_t_civilite) from ta_t_civilite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_civilite, 0); V_GEN = gen_id(num_id_t_civilite, V_MAX - V_GEN);
select max(alias.id_t_doc) from ta_t_doc alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_doc, 0); V_GEN = gen_id(num_id_t_doc, V_MAX - V_GEN);
select max(alias.id_t_entite) from ta_t_entite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_entite, 0); V_GEN = gen_id(num_id_t_entite, V_MAX - V_GEN);
select max(alias.id_t_ligne) from ta_t_ligne alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_ligne, 0); V_GEN = gen_id(num_id_t_ligne, V_MAX - V_GEN);
select max(alias.id_t_paiement) from ta_t_paiement alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_paiement, 0); V_GEN = gen_id(num_id_t_paiement, V_MAX - V_GEN);
select max(alias.id_t_tel) from ta_t_tel alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tel, 0); V_GEN = gen_id(num_id_t_tel, V_MAX - V_GEN);
select max(alias.id_t_tiers) from ta_t_tiers alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tiers, 0); V_GEN = gen_id(num_id_t_tiers, V_MAX - V_GEN);
select max(alias.id_t_tva) from ta_t_tva alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_t_tva, 0); V_GEN = gen_id(num_id_t_tva, V_MAX - V_GEN);
select max(alias.id_telephone) from ta_telephone alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_telephone, 0); V_GEN = gen_id(num_id_telephone, V_MAX - V_GEN);
select max(alias.id_tva) from ta_tva alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_tva, 0); V_GEN = gen_id(num_id_tva, V_MAX - V_GEN);
select max(alias.id_acces) from ta_acces alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_acces, 0); V_GEN = gen_id(num_id_acces, V_MAX - V_GEN);
select max(alias.id_infos_devis) from ta_infos_devis alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_infos_devis, 0); V_GEN = gen_id(num_id_infos_devis, V_MAX - V_GEN);
select max(alias.id_infos_facture) from ta_infos_facture alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_infos_facture, 0); V_GEN = gen_id(num_id_infos_facture, V_MAX - V_GEN);
select max(alias.id_mail_maj) from ta_mail_maj alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_mail_maj, 0); V_GEN = gen_id(num_id_mail_maj, V_MAX - V_GEN);
select max(alias.id_modele) from ta_modele alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_modele, 0); V_GEN = gen_id(num_id_modele, V_MAX - V_GEN);
select max(alias.id_modif) from ta_modif alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_modif, 0); V_GEN = gen_id(num_id_modif, V_MAX - V_GEN);
select max(alias.id_unite) from ta_unite alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_unite, 0); V_GEN = gen_id(num_id_unite, V_MAX - V_GEN);
select max(alias.id_web) from ta_web alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_web, 0); V_GEN = gen_id(num_id_web, V_MAX - V_GEN);
select max(alias.id_R_COMMERCIAL) from ta_R_COMMERCIAL alias into :V_MAX; if (v_max is null)
then v_max = 0; V_GEN = gen_id(num_id_R_COMMERCIAL, 0); V_GEN = gen_id(num_ID_R_COMMERCIAL, V_MAX - V_GEN);
end
^


CREATE OR ALTER PROCEDURE INFOSBONLIV (
    ID_DOCUMENT_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER,
    LIVRAISON INTEGER)
RETURNS (
    ID_INFOS_BONLIV INTEGER,
    ID_ADRESSE INTEGER,
    ID_BONLIV INTEGER,
    ADRESSE1 VARCHAR(100),
    ADRESSE2 VARCHAR(100),
    ADRESSE3 VARCHAR(100),
    CODEPOSTAL VARCHAR(5),
    VILLE VARCHAR(100),
    PAYS VARCHAR(100),
    ADRESSE1_LIV VARCHAR(100),
    ADRESSE2_LIV VARCHAR(100),
    ADRESSE3_LIV VARCHAR(100),
    CODEPOSTAL_LIV VARCHAR(5),
    VILLE_LIV VARCHAR(100),
    PAYS_LIV VARCHAR(100),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    ID_TIERS INTEGER,
    NOM_TIERS VARCHAR(100),
    PRENOM_TIERS VARCHAR(100),
    SURNOM_TIERS VARCHAR(20),
    CODE_T_CIVILITE VARCHAR(20),
    CODE_T_ENTITE VARCHAR(20),
    TVA_I_COM_COMPL VARCHAR(50),
    CODE_C_PAIEMENT VARCHAR(50),
    LIB_C_PAIEMENT VARCHAR(255),
    REPORT_C_PAIEMENT INTEGER,
    FIN_MOIS_C_PAIEMENT INTEGER)
AS
begin

for select

  infos.ID_INFOS_BONLIV,
  cast (null as INTEGER),
  infos.ID_BONLIV,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,         
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,         
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.CODE_C_PAIEMENT,
  infos.LIB_C_PAIEMENT ,
  infos.REPORT_C_PAIEMENT ,
  infos.FIN_MOIS_C_PAIEMENT 
  
   
  from ta_infos_BONLIV infos where infos.id_BONLIV = :id_DOCUMENT_recup into :ID_INFOS_BONLIV ,:id_adresse,
  :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
  :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
  :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE, :CODE_T_ENTITE,:TVA_I_COM_COMPL,
  :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin

  suspend;   
end
if (livraison=0) then
    begin
      for select
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'FACT')
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'LIV'))
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end

if (livraison=1) then
    begin
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'LIV')
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'FACT'))
      into :ID_INFOS_BONLIV ,:id_adresse,
      :id_BONLIV ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end
end
^

CREATE OR ALTER PROCEDURE CALCUL_TVA_TA_L_FACTURE (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    EXPORT INTEGER)
RETURNS (
    ID_DOCUMENT_RECUP SMALLINT,
    TAUX_RECUP NUMERIC(15,4),
    CODE_TVA_RECUP VARCHAR(20),
    MT_TVA_RECUP NUMERIC(15,2),
    LIBELLE_RECUP VARCHAR(100),
    MT_HT NUMERIC(15,2),
    MT_TTC NUMERIC(15,2),
    TAUXREMISE NUMERIC(15,4),
    MT_HT_REMISE NUMERIC(15,2),
    MT_TTC_REMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
DECLARE VARIABLE QUERY2 VARCHAR(1000);
DECLARE VARIABLE ID INTEGER;
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TTC SMALLINT;
begin TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
if (upper(:module)='FACTURE') then
begin
query1 = 'select doc.id_facture,doc.tx_rem_ht_facture,doc.ttc from ta_facture doc
         where doc.id_facture='||:id_document;
end
if (upper(:module)='DEVIS') then
begin
query1 = 'select doc.id_devis,doc.tx_rem_ht_devis,doc.ttc from ta_devis doc
         where doc.id_devis='||:id_document;
end
if (upper(:module)='BONLIV') then
begin
query1 = 'select doc.id_bonliv,doc.tx_rem_ht_bonliv,doc.ttc from ta_bonliv doc
         where doc.id_bonliv='||:id_document;
end
    for  execute statement :query1
    into : id,tauxremise,ttc do
        begin
            if (upper(:module)='FACTURE') then
            begin
            query2 = 'select sum(ldoc.mt_ht_l_facture),sum(ldoc.mt_ttc_l_facture),
            ((sum(ldoc.mt_ht_l_facture)*ldoc.TAUX_TVA_L_FACTURE) /100),
                        ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
                        from ta_l_facture ldoc  where ldoc.id_facture ='|| :id||
                        'and ldoc.id_t_ligne = '||:typeligne||
                        'group by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE
                        order by ldoc.CODE_TVA_L_FACTURE,ldoc.TAUX_TVA_L_FACTURE';
            end
            if (upper(:module)='DEVIS') then
            begin
            query2 = 'select sum(ldoc.mt_ht_l_devis),sum(ldoc.mt_ttc_l_devis),
            ((sum(ldoc.mt_ht_l_devis)*ldoc.taux_tva_l_devis) /100),
                        ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
                        from ta_l_devis ldoc  where ldoc.id_devis ='|| :id||
                        'and ldoc.id_t_ligne = '||:typeligne||
                        'group by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis
                        order by ldoc.code_tva_l_devis,ldoc.taux_tva_l_devis';
            end
            if (upper(:module)='BONLIV') then
            begin
            query2 = 'select sum(ldoc.mt_ht_l_bonliv),sum(ldoc.mt_ttc_l_bonliv),
            ((sum(ldoc.mt_ht_l_bonliv)*ldoc.taux_tva_l_bonliv) /100),
                        ldoc.code_tva_l_bonliv,ldoc.taux_tva_l_bonliv
                        from ta_l_bonliv ldoc  where ldoc.id_bonliv ='|| :id||
                        'and ldoc.id_t_ligne = '||:typeligne||
                        'group by ldoc.code_tva_l_bonliv,ldoc.taux_tva_l_bonliv
                        order by ldoc.code_tva_l_bonliv,ldoc.taux_tva_l_bonliv';
            end
            FOR execute statement :query2
            into : mt_ht,mt_ttc,mt_tva_recup,code_tva_recup,taux_recup
            do begin
            if(:code_tva_recup is not null and :code_tva_recup<>'')then
            begin
            mt_tva_recup = cast(:mt_tva_recup as numeric(15,2));
            if (:ttc=1) then
                begin

                    mt_ttc_remise=cast(:mt_ttc-(:mt_ttc*(:tauxremise/100)) as numeric(15,2));
                   /* if (:export = 1) then*/
                      mt_tva_recup=cast( :mt_ttc_remise-((:mt_ttc_remise*100)/(100+:taux_recup))as numeric (15,2));
                    mt_ht_remise=:mt_ttc_remise - :mt_tva_recup ;
                end
            else
                begin
                    mt_ht_remise=cast(:mt_ht-(:mt_ht*(:tauxremise/100)) as numeric(15,2));
                   /* if (:export = 1) then*/
                      mt_tva_recup=:mt_ht_remise * :taux_recup / 100;
                    mt_ttc_remise = :mt_ht_remise+ :mt_tva_recup ;
                end
        select tva.LIBELLE_TVA from TA_TVA tva where tva.CODE_TVA = :code_tva_recup
        into :libelle_recup; id_document_recup=:id;
        suspend;
        end
        end
        end
end
^


CREATE OR ALTER PROCEDURE DISPATCHER_TVA_DOCUMENT (
    MODULE VARCHAR(255),
    ID_DOCUMENT INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
AS
DECLARE VARIABLE QUERY1 VARCHAR(1000);
DECLARE VARIABLE CODE_TVA VARCHAR(20);
DECLARE VARIABLE TAUX NUMERIC(15,4);
DECLARE VARIABLE TOTAL_TVA NUMERIC(15,2);
DECLARE VARIABLE TOTALHT NUMERIC(15,2);
DECLARE VARIABLE NBLIGNE INTEGER;
DECLARE VARIABLE HT_LIGNE NUMERIC(15,2);
DECLARE VARIABLE ID_LIGNE INTEGER;
DECLARE VARIABLE LIGNEPASSE INTEGER;
DECLARE VARIABLE TVA_LIGNE NUMERIC(15,2);
DECLARE VARIABLE TVATMP NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TTC_LIGNE NUMERIC(15,2);
begin
TYPELIGNE=0;

select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
  if (:ttc=1)then
    begin
      FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
           calcul_tva_sur_mt_ht(:module,:id_document,:tauxremise,:ttc)
           into :code_tva,taux,total_tva
      do
      begin
      if (:code_tva is not null and :taux is not null and :total_tva is not null) then
          begin
          if (upper(:module)='FACTURE') then
          begin
              select (sum(ldoc.mt_ttc_l_facture)),
              count(*) from ta_l_facture_temp ldoc where
              ldoc.code_tva_l_facture = :CODE_TVA and ldoc.taux_tva_l_facture = :TAUX
              and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
              into :totalttc, :NbLigne;
          end
          if (upper(:module)='DEVIS') then
          begin
              select (sum(ldoc.mt_ttc_l_devis)),
              count(*) from ta_l_devis_temp ldoc where
              ldoc.code_tva_l_devis = :CODE_TVA and ldoc.taux_tva_l_devis = :TAUX
              and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
              into :totalttc, :NbLigne;
          end
          if (upper(:module)='BONLIV') then
          begin
              select (sum(ldoc.mt_ttc_l_bonLiv)),
              count(*) from ta_l_bonLiv_temp ldoc where
              ldoc.code_tva_l_bonLiv = :CODE_TVA and ldoc.taux_tva_l_bonLiv = :TAUX
              and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
              into :totalttc, :NbLigne;
          end
            lignepasse=1;
            TVATMP=0;
            TVATMP=:total_tva;
              if (upper(:module)='FACTURE') then
              begin
                  query1 = 'select ldoc2.mt_ttc_l_facture,
                           ldoc2.id_l_facture from ta_l_facture_temp ldoc2 where
                           ldoc2.code_tva_l_facture = '''||:CODE_TVA||''' and ldoc2.taux_tva_l_facture ='|| :TAUX;
              end
              if (upper(:module)='DEVIS') then
              begin
                  query1 = 'select ldoc2.mt_ttc_l_devis,
                           ldoc2.id_l_devis from ta_l_devis_temp ldoc2 where
                           ldoc2.code_tva_l_devis = '''||:CODE_TVA||''' and ldoc2.taux_tva_l_devis ='|| :TAUX;
              end
              if (upper(:module)='BONLIV') then
              begin
                  query1 = 'select ldoc2.mt_ttc_l_bonLiv,
                           ldoc2.id_l_bonLiv from ta_l_bonLiv_temp ldoc2 where
                           ldoc2.code_tva_l_bonLiv = '''||:CODE_TVA||''' and ldoc2.taux_tva_l_bonLiv ='|| :TAUX;
              end
              query1 = :query1 || ' and ldoc2.id_t_ligne =' ||:typeligne||' and ldoc2.ip_acces = current_connection';
            For  execute statement :query1
                 into :ttc_ligne, :id_ligne
            do 
                begin
                  if (:totalttc=0) then tva_ligne=(:ttc_ligne * :taux)/100;
                  else
                      begin
                           if  (lignepasse>= :NbLigne) then
                           tva_ligne = :TVATMP;
                           else
                           tva_ligne = (:total_tva * :ttc_ligne) / :totalttc;
                      end
                   TVATMP =  :TVATMP -  :tva_ligne;
                   if (upper(:module)='FACTURE') then
                   begin
                      Update ta_l_facture_temp ldoc3 set ldoc3.mt_ht_l_facture = (:ttc_ligne - :tva_ligne)
                      where ldoc3.id_l_facture=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
                   end
                   if (upper(:module)='DEVIS') then
                   begin
                      Update ta_l_devis_temp ldoc3 set ldoc3.mt_ht_l_devis = (:ttc_ligne - :tva_ligne)
                      where ldoc3.id_l_devis=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
                   end
                   if (upper(:module)='BONLIV') then
                   begin
                      Update ta_l_bonLiv_temp ldoc3 set ldoc3.mt_ht_l_bonLiv = (:ttc_ligne - :tva_ligne)
                      where ldoc3.id_l_bonLiv=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
                   end
                   lignepasse = :lignepasse + 1;
                end
          end
      end
    end
  else
    begin
      FOR select CODE_TVA_RECUP, TAUX_RECUP, MT_TVA_RECUP FROM
            calcul_tva_sur_mt_ht(:module,:id_document,:tauxremise,:ttc)
             into :code_tva,taux,total_tva
      do
      begin
      if (:code_tva is not null and :taux is not null and :total_tva is not null) then
          begin
          if (upper(:module)='FACTURE') then
          begin
            select (sum(ldoc.mt_ht_l_facture)), count(*) from ta_l_facture_temp ldoc where
            ldoc.code_tva_l_facture = :CODE_TVA and ldoc.taux_tva_l_facture = :TAUX
            and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
            into :TotalHT, :NbLigne;
          end
          if (upper(:module)='DEVIS') then
          begin
            select (sum(ldoc.mt_ht_l_devis)), count(*) from ta_l_devis_temp ldoc where
            ldoc.code_tva_l_devis = :CODE_TVA and ldoc.taux_tva_l_devis = :TAUX
            and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
            into :TotalHT, :NbLigne;
          end
          if (upper(:module)='BONLIV') then
          begin
            select (sum(ldoc.mt_ht_l_bonLiv)), count(*) from ta_l_bonLiv_temp ldoc where
            ldoc.code_tva_l_bonLiv = :CODE_TVA and ldoc.taux_tva_l_bonLiv = :TAUX
            and ldoc.id_t_ligne = :typeligne  and ldoc.ip_acces = current_connection
            into :TotalHT, :NbLigne;
          end
            lignepasse=1;
            TVATMP=0;
            TVATMP=:total_tva;
          if (upper(:module)='FACTURE') then
          begin
            query1 = 'select ldoc2.mt_ht_l_facture,
                      ldoc2.id_l_facture from ta_l_facture_temp ldoc2 where
                      ldoc2.code_tva_l_facture = '''||:CODE_TVA ||''' and ldoc2.taux_tva_l_facture = '|| :TAUX;
          end
          if (upper(:module)='DEVIS') then
          begin
            query1 = 'select ldoc2.mt_ht_l_devis,
                      ldoc2.id_l_devis from ta_l_devis_temp ldoc2 where
                      ldoc2.code_tva_l_devis = '''||:CODE_TVA ||''' and ldoc2.taux_tva_l_devis = '|| :TAUX;
          end
          if (upper(:module)='BONLIV') then
          begin
            query1 = 'select ldoc2.mt_ht_l_bonLiv,
                      ldoc2.id_l_bonLiv from ta_l_bonLiv_temp ldoc2 where
                      ldoc2.code_tva_l_bonLiv = '''||:CODE_TVA ||''' and ldoc2.taux_tva_l_bonLiv = '|| :TAUX;
          end
            query1 = :query1 || ' and ldoc2.id_t_ligne = '||:typeligne||' and ldoc2.ip_acces = current_connection';
            For execute statement :query1
            into :ht_ligne, :id_ligne
            do begin
               if (:totalht=0) then tva_ligne=(:ht_ligne * :taux)/100;
               else
                   begin
                       if  (lignepasse>= :NbLigne) then
                       tva_ligne = :TVATMP;
                    else
                       tva_ligne = (:total_tva * :ht_ligne) / :totalht;
                   end
               TVATMP =  :TVATMP -  :tva_ligne;
              if (upper(:module)='FACTURE') then
              begin
               Update ta_l_facture_temp ldoc3 set ldoc3.mt_ttc_l_facture = (:ht_ligne + :tva_ligne)
                  where ldoc3.id_l_facture=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
    
              end
              if (upper(:module)='DEVIS') then
              begin
               Update ta_l_devis_temp ldoc3 set ldoc3.mt_ttc_l_devis = (:ht_ligne + :tva_ligne)
                  where ldoc3.id_l_devis=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
              end
              if (upper(:module)='BONLIV') then
              begin
               Update ta_l_bonLiv_temp ldoc3 set ldoc3.mt_ttc_l_bonLiv = (:ht_ligne + :tva_ligne)
                  where ldoc3.id_l_bonLiv=:id_ligne and ldoc3.ip_acces = current_connection and ldoc3.id_t_ligne = :typeligne;
              end
               lignepasse = :lignepasse + 1;
            end
          end
      end
    end
  suspend;
end
^

CREATE OR ALTER PROCEDURE VIDE_DOCUMENT_TEMP (
    MODULE VARCHAR(255))
AS
begin
if (upper(:module) = 'FACTURE') then
  delete from ta_l_facture_temp ta where ta.ip_acces = current_connection;
if (upper(:module) = 'DEVIS') then
  delete from ta_l_devis_temp ta where ta.ip_acces = current_connection ;
  if (upper(:module) = 'BONLIV') then
  delete from ta_l_bonliv_temp ta where ta.ip_acces = current_connection ;
end
^

CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_DEVIS (
    NEW_ID_DEVIS INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_devis from ta_devis f where f.id_devis=:new_id_devis into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('devis',0,:ttc,:v_rem_ht_l_document);
FOR select ldevis.id_l_devis,
   ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis
from ta_l_devis_temp ldevis
where ldevis.ip_acces = current_connection
into
    :v_id_l_document,
    :v_id_document,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document
do
  begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
    select art.numcpt_article from ta_article art where art.id_article = :v_id_article
    into :v_compte_l_document;
     insert into ta_l_devis (
    ID_L_devis,
    ID_devis,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_devis,
    LIB_L_devis,
    QTE_L_devis,
    U1_L_devis,
    U2_L_devis,
    PRIX_U_L_devis,
    TAUX_TVA_L_devis,
    compte_l_devis,
    CODE_TVA_L_devis,
    CODE_T_TVA_L_devis,
    MT_HT_L_devis,
    MT_TTC_L_devis,
    REM_TX_L_devis,
    REM_HT_L_devis
    ) values(
    :v_id_l_document,
    :new_id_devis,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :v_num_ligne_l_document,
    :v_lib_l_document,
    :v_qte_l_document,
    :v_u1_l_document,
    :v_u2_l_document,
    :v_prix_u_l_document,
    :v_taux_tva_l_document,
    :v_compte_l_document,
    :v_code_tva_l_document,
    :v_code_t_tva_l_document,
    :v_mt_ht_l_document,
    :v_mt_ttc_l_document,
    :v_rem_tx_l_document,
    :v_rem_ht_l_document) ;
    delete from ta_l_devis_temp where ID_L_devis = :v_id_l_document and
    ip_acces=current_connection;
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE MAJ_LIGNES_DEVIS (
    ID_DOCUMENT_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_DOCUMENT_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
TYPELIGNE=0;
    delete from ta_l_devis ldevis where ldevis.id_devis=:id_document_recup  and
    not (ldevis.id_l_devis in (
    select id_l_devis from ta_l_devis_temp where (id_devis=:id_document_recup or (id_devis=-1))and
    ip_acces=current_connection ));
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select D.tx_rem_ht_devis from ta_devis D where d.id_devis=:id_document_recup into :v_rem_ht_l_document;
execute procedure dispatcher_tva_document('devis',:id_document_recup,:ttc,:v_rem_ht_l_document);
  FOR select ldevis.id_l_devis,
    ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
    ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
    ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
    ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
    ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis from ta_l_devis_temp ldevis
    where ldevis.id_devis = :id_document_recup and ldevis.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document = null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select ldevis.id_l_devis from ta_l_devis ldevis
        where ldevis.id_l_devis = :v_id_l_document into :id_l_document_courant;
        if (:id_l_document_courant is not null) then
        begin
            update ta_l_devis doc set
            doc.id_l_devis = :v_id_l_document,
            doc.id_devis = :v_id_document,
            doc.ID_T_LIGNE = :V_ID_T_LIGNE,
            doc.ID_ARTICLE = :V_ID_ARTICLE,
            doc.num_ligne_l_devis = :v_num_ligne_l_document,
            doc.lib_l_devis = :v_lib_l_document,
            doc.qte_l_devis = :v_qte_l_document,
            doc.u1_l_devis = :v_u1_l_document,
            doc.u2_l_devis = :v_u2_l_document,
            doc.prix_u_l_devis = :v_prix_u_l_document,
            doc.taux_tva_l_devis = :v_taux_tva_l_document,
            doc.compte_l_devis = :v_compte_l_document,
            doc.code_tva_l_devis = :v_code_tva_l_document,
            doc.code_t_tva_l_devis = :v_code_t_tva_l_document,
            doc.mt_ht_l_devis = :v_mt_ht_l_document,
            doc.mt_ttc_l_devis = :v_mt_ttc_l_document,
            doc.rem_tx_l_devis = :v_rem_tx_l_document,
            doc.rem_ht_l_devis = :v_rem_ht_l_document
            where doc.id_l_devis = :v_id_l_document;
            update ta_l_devis_temp set id_devis = -1 where  id_l_devis=:id_l_document_courant ;
        end
       end
  FOR select ldevis.id_l_devis,
    ldevis.id_devis,
    ldevis.id_t_ligne, ldevis.id_article, ldevis.num_ligne_l_devis,
    ldevis.lib_l_devis, ldevis.qte_l_devis, ldevis.u1_l_devis, ldevis.u2_l_devis,
    ldevis.prix_u_l_devis, ldevis.taux_tva_l_devis,ldevis.compte_l_devis, ldevis.code_tva_l_devis,
    ldevis.code_t_tva_l_devis, ldevis.mt_ht_l_devis,
    ldevis.mt_ttc_l_devis, ldevis.rem_tx_l_devis, ldevis.rem_ht_l_devis from ta_l_devis_temp ldevis
    where ldevis.id_devis = :id_document_recup  and ldevis.ip_acces = current_connection into
        :v_id_l_document,
        :v_id_document,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :v_num_ligne_l_document,
        :v_lib_l_document,
        :v_qte_l_document,
        :v_u1_l_document,
        :v_u2_l_document,
        :v_prix_u_l_document,
        :v_taux_tva_l_document,
        :v_compte_l_document,
        :v_code_tva_l_document,
        :v_code_t_tva_l_document,
        :v_mt_ht_l_document,
        :v_mt_ttc_l_document,
        :v_rem_tx_l_document,
        :v_rem_ht_l_document
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            v_qte_l_document=null;
            v_prix_u_l_document=null;
            v_taux_tva_l_document=null;
            v_compte_l_document=null;
            v_code_tva_l_document=null;
            v_code_t_tva_l_document=null;
            v_mt_ht_l_document=null;
            v_mt_ttc_l_document=null;
            v_rem_tx_l_document=null;
            v_rem_ht_l_document=null;
        end
        select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_document;
            insert into ta_l_devis  (
            id_l_devis,
            id_devis,
            ID_T_LIGNE,
            ID_ARTICLE,
            num_ligne_l_devis,
            lib_l_devis,
            qte_l_devis,
            u1_l_devis,
            u2_l_devis,
            prix_u_l_devis,
            taux_tva_l_devis,
            compte_l_devis,
            code_tva_l_devis,
            code_t_tva_l_devis,
            mt_ht_l_devis,
            mt_ttc_l_devis,
            rem_tx_l_devis,
            rem_ht_l_devis
            ) values(
            :v_id_l_document,
            :v_id_document,
            :V_ID_T_LIGNE,
            :V_ID_ARTICLE,
            :v_num_ligne_l_document,
            :v_lib_l_document,
            :v_qte_l_document,
            :v_u1_l_document,
            :v_u2_l_document,
            :v_prix_u_l_document,
            :v_taux_tva_l_document,
            :v_compte_l_document,
            :v_code_tva_l_document,
            :v_code_t_tva_l_document,
            :v_mt_ht_l_document,
            :v_mt_ttc_l_document,
            :v_rem_tx_l_document,
            :v_rem_ht_l_document);
       end 

   execute procedure vide_document_temp('devis');
    suspend;
end
^


CREATE OR ALTER PROCEDURE RECUP_LIGNES_FACTURE (
    CODE_FACTURE_RECUP VARCHAR(50))
RETURNS (
    ID_FACTURE_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
execute procedure vide_document_temp('facture');

select ID_FACTURE from ta_facture where CODE_FACTURE = :CODE_FACTURE_RECUP into :id_facture_recup;

FOR select lfacture.id_l_facture,
   lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture,
 lfacture.mt_ht_l_facture,
lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture lfacture
where lfacture.id_facture = :id_facture_recup
into
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE
do
  begin

     insert into ta_l_facture_temp (
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    compte_l_facture,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE,
    IP_ACCES
    ) values(
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE,
    Current_Connection) ;
    /*suspend; */
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE RECUP_LIGNES_EXPORTATION (
    ID_FACTURE_RECUP INTEGER,
    TTC INTEGER,
    TAUXREMISE NUMERIC(15,2))
RETURNS (
    ID_L_FACTURE INTEGER,
    ID_FACTURE INTEGER,
    ID_T_LIGNE INTEGER,
    ID_ARTICLE INTEGER,
    NUM_LIGNE_L_FACTURE INTEGER,
    LIB_L_FACTURE VARCHAR(255),
    QTE_L_FACTURE NUMERIC(15,2),
    U1_L_FACTURE VARCHAR(20),
    U2_L_FACTURE VARCHAR(20),
    PRIX_U_L_FACTURE NUMERIC(15,2),
    TAUX_TVA_L_FACTURE NUMERIC(15,4),
    CODE_TVA_L_FACTURE VARCHAR(20),
    CODE_T_TVA_L_FACTURE VARCHAR(1),
    MT_HT_L_FACTURE NUMERIC(15,2),
    MT_TTC_L_FACTURE NUMERIC(15,2),
    REM_TX_L_FACTURE NUMERIC(15,2),
    REM_HT_L_FACTURE NUMERIC(15,2),
    TVALIGNE NUMERIC(15,2),
    COMPTE_L_FACTURE VARCHAR(8))
AS
DECLARE VARIABLE CODE_TVA VARCHAR(20);
DECLARE VARIABLE TAUX NUMERIC(15,4);
DECLARE VARIABLE TOTAL_TVA NUMERIC(15,2);
DECLARE VARIABLE TOTALHT NUMERIC(15,2);
DECLARE VARIABLE NBLIGNE INTEGER;
DECLARE VARIABLE LIGNEPASSE INTEGER;
DECLARE VARIABLE TVA_LIGNE NUMERIC(15,2);
DECLARE VARIABLE TVATMP NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE TOTALTTC NUMERIC(15,2);
DECLARE VARIABLE TOTAL_TTC_REMISE NUMERIC(15,2);
DECLARE VARIABLE TOTAL_HT_REMISE NUMERIC(15,2);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
  FOR select calc.code_tva_recup, calc.taux_recup, calc.mt_tva_recup,calc.mt_ht,
  calc.mt_ttc,calc.mt_ht_remise,calc.mt_ttc_remise
  FROM  calcul_tva_ta_l_facture('facture',:ID_FACTURE_RECUP,1)calc into
    :code_tva,:taux,:total_tva,:totalht,
    :totalttc,:total_ht_remise,:total_ttc_remise
  do
  begin
    select  count(*) from ta_l_facture lfacture where
    lfacture.id_facture = :id_facture_recup and
    lfacture.code_tva_l_facture = :CODE_TVA and lfacture.taux_tva_l_facture = :TAUX
    and lfacture.id_t_ligne = :typeligne
    into :NbLigne;

 lignepasse=1;
 TVATMP=0;
 TVATMP=:total_tva;

    FOR select lfacture2.id_l_facture,
    lfacture2.id_facture,
    lfacture2.id_t_ligne, lfacture2.id_article, lfacture2.num_ligne_l_facture,
    lfacture2.lib_l_facture, lfacture2.qte_l_facture, lfacture2.u1_l_facture, lfacture2.u2_l_facture,
    lfacture2.prix_u_l_facture, lfacture2.taux_tva_l_facture,lfacture2.compte_l_facture, lfacture2.code_tva_l_facture,lfacture2.code_t_tva_l_facture,
    lfacture2.mt_ht_l_facture,lfacture2.mt_ttc_l_facture, lfacture2.rem_tx_l_facture, lfacture2.rem_ht_l_facture
    from ta_l_facture lfacture2 where
        lfacture2.id_facture = :id_facture_recup and
        lfacture2.code_tva_l_facture = :CODE_TVA and lfacture2.taux_tva_l_facture = :TAUX
        and lfacture2.id_t_ligne = :typeligne 
         into
        :ID_L_FACTURE,
        :ID_FACTURE,
        :ID_T_LIGNE,
        :ID_ARTICLE,
        :NUM_LIGNE_L_FACTURE,
        :LIB_L_FACTURE,
        :QTE_L_FACTURE,
        :U1_L_FACTURE,
        :U2_L_FACTURE,
        :PRIX_U_L_FACTURE,
        :TAUX_TVA_L_FACTURE,
        :compte_l_facture,
        :CODE_TVA_L_FACTURE,
        :CODE_T_TVA_L_FACTURE,
        :MT_HT_L_FACTURE,
        :MT_TTC_L_FACTURE,
        :REM_TX_L_FACTURE,
        :REM_HT_L_FACTURE
    do
            begin
                 if (:ttc=1)then
                    begin
                       if (:totalttc=0 or (total_ttc_remise=0) ) then
                           begin
                             if (:total_ttc_remise=0 and :totalttc<>0) then
                             begin
                                 mt_ttc_l_facture=0;
                                 tva_ligne =0;
                             end
                             else
                               tva_ligne = (:mt_ttc_l_facture * :taux)/100 ;
                           end
                       else
                           begin
                               mt_ttc_l_facture = (:total_ttc_remise * :mt_ttc_l_facture) / :totalttc ;
                               if  (lignepasse>= :NbLigne) then
                                 tva_ligne = :TVATMP;
                               else
                                 tva_ligne = (:total_tva * :mt_ttc_l_facture) / :total_ttc_remise;
                           end
                       TVATMP =  :TVATMP -  :tva_ligne;
                       mt_ht_l_facture = :mt_ttc_l_facture - :tva_ligne ;
                       tvaligne = :tva_ligne;
                       lignepasse = :lignepasse + 1;
                       suspend;
                    end
                  else
                    begin
                       if (:totalht=0 or (:total_ht_remise=0) ) then
                         begin
                             if (:total_ht_remise=0 and :totalht<>0) then
                             begin
                                 mt_ht_l_facture=0;
                                 tva_ligne =0;
                             end
                             else
                               tva_ligne = (:mt_ht_l_facture * :taux)/100;
                         end
                       else
                         begin
                            mt_ht_l_facture = (:total_ht_remise * :mt_ht_l_facture) / :totalht ;
                            if  (lignepasse>= :NbLigne) then
                              tva_ligne = :TVATMP;
                            else
                              tva_ligne = (:total_tva * :mt_ht_l_facture) / :total_ht_remise;
                         end
                       TVATMP =  :TVATMP -  :tva_ligne;

                       mt_ttc_l_facture = :mt_ht_l_facture + :tva_ligne;
                       tvaligne = :tva_ligne;
                       lignepasse = :lignepasse + 1;
                       suspend;
                    end
            end
  end
    FOR select lfacture2.id_l_facture,
    lfacture2.id_facture,
    lfacture2.id_t_ligne, lfacture2.id_article, lfacture2.num_ligne_l_facture,
    lfacture2.lib_l_facture, lfacture2.qte_l_facture, lfacture2.u1_l_facture, lfacture2.u2_l_facture,
    lfacture2.prix_u_l_facture, lfacture2.taux_tva_l_facture,lfacture2.compte_l_facture, lfacture2.code_tva_l_facture,lfacture2.code_t_tva_l_facture,
    lfacture2.mt_ht_l_facture,lfacture2.mt_ttc_l_facture, lfacture2.rem_tx_l_facture, lfacture2.rem_ht_l_facture
    from ta_l_facture lfacture2 where
        lfacture2.id_facture = :id_facture_recup and
        (lfacture2.code_tva_l_facture is null or lfacture2.code_tva_l_facture='' )
        and lfacture2.id_t_ligne = :typeligne 
         into
        :ID_L_FACTURE,
        :ID_FACTURE,
        :ID_T_LIGNE,
        :ID_ARTICLE,
        :NUM_LIGNE_L_FACTURE,
        :LIB_L_FACTURE,
        :QTE_L_FACTURE,
        :U1_L_FACTURE,
        :U2_L_FACTURE,
        :PRIX_U_L_FACTURE,
        :TAUX_TVA_L_FACTURE,
        :compte_l_facture,
        :CODE_TVA_L_FACTURE,
        :CODE_T_TVA_L_FACTURE,
        :MT_HT_L_FACTURE,
        :MT_TTC_L_FACTURE,
        :REM_TX_L_FACTURE,
        :REM_HT_L_FACTURE
    do
            begin
            if (:mt_ht_l_facture=0 or (:mt_ht_l_facture is null) ) then
              mt_ht_l_facture = :mt_ttc_l_facture;
            if (:mt_ttc_l_facture=0 or (:mt_ttc_l_facture is null) ) then
              mt_ttc_l_facture= :mt_ht_l_facture;
            suspend;
            end
end
^

CREATE OR ALTER PROCEDURE ENREGISTRE_LIGNES_FACTURE (
    NEW_ID_FACTURE INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
TYPELIGNE=0;
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_facture from ta_facture f where f.id_facture=:new_id_facture into :V_REM_HT_L_FACTURE;
execute procedure dispatcher_tva_document('facture',0,:ttc,:V_REM_HT_L_FACTURE);
FOR select lfacture.id_l_facture,
   lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture
from ta_l_facture_temp lfacture
where lfacture.ip_acces = current_connection
into
    :V_ID_L_FACTURE,
    :V_ID_FACTURE,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE
do
  begin
     if (:V_ID_T_LIGNE <> typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture=null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
    select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_facture;
     insert into ta_l_facture (
    ID_L_FACTURE,
    ID_FACTURE,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_FACTURE,
    LIB_L_FACTURE,
    QTE_L_FACTURE,
    U1_L_FACTURE,
    U2_L_FACTURE,
    PRIX_U_L_FACTURE,
    TAUX_TVA_L_FACTURE,
    compte_l_facture,
    CODE_TVA_L_FACTURE,
    CODE_T_TVA_L_FACTURE,
    MT_HT_L_FACTURE,
    MT_TTC_L_FACTURE,
    REM_TX_L_FACTURE,
    REM_HT_L_FACTURE
    ) values(
    :V_ID_L_FACTURE,
    :new_id_facture,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_FACTURE,
    :V_LIB_L_FACTURE,
    :V_QTE_L_FACTURE,
    :V_U1_L_FACTURE,
    :V_U2_L_FACTURE,
    :V_PRIX_U_L_FACTURE,
    :V_TAUX_TVA_L_FACTURE,
    :v_compte_l_facture,
    :V_CODE_TVA_L_FACTURE,
    :V_CODE_T_TVA_L_FACTURE,
    :V_MT_HT_L_FACTURE,
    :V_MT_TTC_L_FACTURE,
    :V_REM_TX_L_FACTURE,
    :V_REM_HT_L_FACTURE) ;
    delete from ta_l_facture_temp where ID_L_FACTURE = :V_ID_L_FACTURE and
    ip_acces=current_connection;
  end
  suspend;
end
^

CREATE OR ALTER PROCEDURE MAJ_LIGNES_FACTURE (
    ID_FACTURE_RECUP INTEGER,
    TTC INTEGER)
AS
DECLARE VARIABLE ID_L_FACTURE_COURANT INTEGER;
DECLARE VARIABLE V_ID_L_FACTURE INTEGER;
DECLARE VARIABLE V_ID_FACTURE INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_FACTURE INTEGER;
DECLARE VARIABLE V_LIB_L_FACTURE VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_FACTURE NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_FACTURE VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_FACTURE NUMERIC(15,2);
DECLARE VARIABLE TYPELIGNE INTEGER;
DECLARE VARIABLE V_CODE_T_TVA_L_FACTURE VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_FACTURE VARCHAR(8);
begin
TYPELIGNE=0;
    delete from ta_l_facture lfacture where lfacture.id_facture=:id_facture_recup  and
    not (lfacture.id_l_facture in (
    select id_l_facture from ta_l_facture_temp where (id_facture=:id_facture_recup or (id_facture=-1))and
    ip_acces=current_connection ));
select typeL.id_t_ligne from  ta_t_ligne typeL where typeL.code_t_ligne = 'H' into :typeligne;
select f.tx_rem_ht_facture from ta_facture f where f.id_facture=:ID_FACTURE_RECUP into :V_REM_HT_L_FACTURE;
execute procedure dispatcher_tva_document('facture',:id_facture_recup,:ttc,:V_REM_HT_L_FACTURE);
  FOR select lfacture.id_l_facture,
    lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
    lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
    lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
    lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture_temp lfacture
    where lfacture. id_facture = :id_facture_recup and lfacture.ip_acces = current_connection into
        :V_ID_L_FACTURE,
        :V_ID_FACTURE,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :V_NUM_LIGNE_L_FACTURE,
        :V_LIB_L_FACTURE,
        :V_QTE_L_FACTURE,
        :V_U1_L_FACTURE,
        :V_U2_L_FACTURE,
        :V_PRIX_U_L_FACTURE,
        :V_TAUX_TVA_L_FACTURE,
        :v_compte_l_facture,
        :V_CODE_TVA_L_FACTURE,
        :V_CODE_T_TVA_L_FACTURE,
        :V_MT_HT_L_FACTURE,
        :V_MT_TTC_L_FACTURE,
        :V_REM_TX_L_FACTURE,
        :V_REM_HT_L_FACTURE
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture = null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
        select lfacture.id_l_facture from ta_l_facture lfacture
        where lfacture.id_l_facture = :V_ID_L_FACTURE into :id_l_facture_courant;
        if (:id_l_facture_courant is not null) then
        begin
            update ta_l_facture set
            ID_L_FACTURE = :V_ID_L_FACTURE,
            ID_FACTURE = :V_ID_FACTURE,
            ID_T_LIGNE = :V_ID_T_LIGNE,
            ID_ARTICLE = :V_ID_ARTICLE,
            NUM_LIGNE_L_FACTURE = :V_NUM_LIGNE_L_FACTURE,
            LIB_L_FACTURE = :V_LIB_L_FACTURE,
            QTE_L_FACTURE = :V_QTE_L_FACTURE,
            U1_L_FACTURE = :V_U1_L_FACTURE,
            U2_L_FACTURE = :V_U2_L_FACTURE,
            PRIX_U_L_FACTURE = :V_PRIX_U_L_FACTURE,
            TAUX_TVA_L_FACTURE = :V_TAUX_TVA_L_FACTURE,
            compte_l_facture = :v_compte_l_facture,
            CODE_TVA_L_FACTURE = :V_CODE_TVA_L_FACTURE,
            CODE_T_TVA_L_FACTURE = :V_CODE_T_TVA_L_FACTURE,
            MT_HT_L_FACTURE = :V_MT_HT_L_FACTURE,
            MT_TTC_L_FACTURE = :V_MT_TTC_L_FACTURE,
            REM_TX_L_FACTURE = :V_REM_TX_L_FACTURE,
            REM_HT_L_FACTURE = :V_REM_HT_L_FACTURE
            where id_l_facture = :V_ID_L_FACTURE;
            update ta_l_facture_temp set id_facture = -1 where  id_l_facture=:id_l_facture_courant ;
        end
       end
  FOR select lfacture.id_l_facture,
    lfacture.id_facture,
    lfacture.id_t_ligne, lfacture.id_article, lfacture.num_ligne_l_facture,
    lfacture.lib_l_facture, lfacture.qte_l_facture, lfacture.u1_l_facture, lfacture.u2_l_facture,
    lfacture.prix_u_l_facture, lfacture.taux_tva_l_facture,lfacture.compte_l_facture, lfacture.code_tva_l_facture,lfacture.code_t_tva_l_facture, lfacture.mt_ht_l_facture,
    lfacture.mt_ttc_l_facture, lfacture.rem_tx_l_facture, lfacture.rem_ht_l_facture from ta_l_facture_temp lfacture
    where lfacture.id_facture = :id_facture_recup  and lfacture.ip_acces = current_connection into
        :V_ID_L_FACTURE,
        :V_ID_FACTURE,
        :V_ID_T_LIGNE,
        :V_ID_ARTICLE,
        :V_NUM_LIGNE_L_FACTURE,
        :V_LIB_L_FACTURE,
        :V_QTE_L_FACTURE,
        :V_U1_L_FACTURE,
        :V_U2_L_FACTURE,
        :V_PRIX_U_L_FACTURE,
        :V_TAUX_TVA_L_FACTURE,
        :v_compte_l_facture,
        :V_CODE_TVA_L_FACTURE,
        :V_CODE_T_TVA_L_FACTURE,
        :V_MT_HT_L_FACTURE,
        :V_MT_TTC_L_FACTURE,
        :V_REM_TX_L_FACTURE,
        :V_REM_HT_L_FACTURE
    do begin
     if (:V_ID_T_LIGNE <> :typeligne) then
        begin
            V_QTE_L_FACTURE=null;
            V_PRIX_U_L_FACTURE=null;
            V_TAUX_TVA_L_FACTURE=null;
            v_compte_l_facture=null;
            V_CODE_TVA_L_FACTURE=null;
            V_CODE_T_TVA_L_FACTURE=null;
            V_MT_HT_L_FACTURE=null;
            V_MT_TTC_L_FACTURE=null;
            V_REM_TX_L_FACTURE=null;
            V_REM_HT_L_FACTURE=null;
        end
        select art.numcpt_article from ta_article art where art.id_article = :v_id_article into :v_compte_l_facture;
            insert into ta_l_facture (
            ID_L_FACTURE,
            ID_FACTURE,
            ID_T_LIGNE,
            ID_ARTICLE,
            NUM_LIGNE_L_FACTURE,
            LIB_L_FACTURE,
            QTE_L_FACTURE,
            U1_L_FACTURE,
            U2_L_FACTURE,
            PRIX_U_L_FACTURE,
            TAUX_TVA_L_FACTURE,
            compte_l_facture,
            CODE_TVA_L_FACTURE,
            CODE_T_TVA_L_FACTURE,
            MT_HT_L_FACTURE,
            MT_TTC_L_FACTURE,
            REM_TX_L_FACTURE,
            REM_HT_L_FACTURE
            ) values(
            :V_ID_L_FACTURE,
            :V_ID_FACTURE,
            :V_ID_T_LIGNE,
            :V_ID_ARTICLE,
            :V_NUM_LIGNE_L_FACTURE,
            :V_LIB_L_FACTURE,
            :V_QTE_L_FACTURE,
            :V_U1_L_FACTURE,
            :V_U2_L_FACTURE,
            :V_PRIX_U_L_FACTURE,
            :V_TAUX_TVA_L_FACTURE,
            :v_compte_l_facture,
            :V_CODE_TVA_L_FACTURE,
            :V_CODE_T_TVA_L_FACTURE,
            :V_MT_HT_L_FACTURE,
            :V_MT_TTC_L_FACTURE,
            :V_REM_TX_L_FACTURE,
            :V_REM_HT_L_FACTURE);
       end 

   execute procedure vide_document_temp('facture');
    suspend;
end
^

CREATE OR ALTER PROCEDURE RECUP_LIGNES_DEVIS (
    CODE_DOCUMENT_RECUP VARCHAR(50))
RETURNS (
    ID_DOCUMENT_RECUP INTEGER)
AS
DECLARE VARIABLE V_ID_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_DOCUMENT INTEGER;
DECLARE VARIABLE V_ID_T_LIGNE INTEGER;
DECLARE VARIABLE V_ID_ARTICLE INTEGER;
DECLARE VARIABLE V_NUM_LIGNE_L_DOCUMENT INTEGER;
DECLARE VARIABLE V_LIB_L_DOCUMENT VARCHAR(255) CHARACTER SET NONE;
DECLARE VARIABLE V_QTE_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_U1_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_U2_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_PRIX_U_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_TAUX_TVA_L_DOCUMENT NUMERIC(15,4);
DECLARE VARIABLE V_CODE_TVA_L_DOCUMENT VARCHAR(20) CHARACTER SET NONE;
DECLARE VARIABLE V_MT_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_MT_TTC_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_TX_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_REM_HT_L_DOCUMENT NUMERIC(15,2);
DECLARE VARIABLE V_CODE_T_TVA_L_DOCUMENT VARCHAR(1);
DECLARE VARIABLE V_COMPTE_L_DOCUMENT VARCHAR(8);
begin
execute procedure vide_document_temp('DEVIS');

select ID_DEVIS from ta_DEVIS where CODE_DEVIS = :code_document_recup into :id_document_recup;

FOR select ldoc.id_l_devis,
   ldoc.id_devis,
    ldoc.id_t_ligne, ldoc.id_article, ldoc.num_ligne_l_devis,
ldoc.lib_l_devis, ldoc.qte_l_devis, ldoc.u1_l_devis, ldoc.u2_l_devis,
ldoc.prix_u_l_devis, ldoc.taux_tva_l_devis,ldoc.compte_l_devis, ldoc.code_tva_l_devis,
ldoc.code_t_tva_l_devis, ldoc.mt_ht_l_devis,ldoc.mt_ttc_l_devis, ldoc.rem_tx_l_devis,
ldoc.rem_ht_l_devis from ta_l_DEVIS ldoc
where ldoc.id_devis = :id_document_recup
into
    :v_id_l_document,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT
do
  begin

     insert into ta_l_DEVIS_temp (
    ID_L_DEVIS,
    ID_DEVIS,
    ID_T_LIGNE,
    ID_ARTICLE,
    NUM_LIGNE_L_DEVIS,
    LIB_L_DEVIS,
    QTE_L_DEVIS,
    U1_L_DEVIS,
    U2_L_DEVIS,
    PRIX_U_L_DEVIS,
    TAUX_TVA_L_DEVIS,
    compte_l_DEVIS,
    CODE_TVA_L_DEVIS,
    CODE_T_TVA_L_DEVIS,
    MT_HT_L_DEVIS,
    MT_TTC_L_DEVIS,
    REM_TX_L_DEVIS,
    REM_HT_L_DEVIS,
    IP_ACCES
    ) values(
    :V_ID_L_DOCUMENT,
    :V_ID_DOCUMENT,
    :V_ID_T_LIGNE,
    :V_ID_ARTICLE,
    :V_NUM_LIGNE_L_DOCUMENT,
    :V_LIB_L_DOCUMENT,
    :V_QTE_L_DOCUMENT,
    :V_U1_L_DOCUMENT,
    :V_U2_L_DOCUMENT,
    :V_PRIX_U_L_DOCUMENT,
    :V_TAUX_TVA_L_DOCUMENT,
    :v_compte_l_DOCUMENT,
    :V_CODE_TVA_L_DOCUMENT,
    :V_CODE_T_TVA_L_DOCUMENT,
    :V_MT_HT_L_DOCUMENT,
    :V_MT_TTC_L_DOCUMENT,
    :V_REM_TX_L_DOCUMENT,
    :V_REM_HT_L_DOCUMENT,
    Current_Connection) ;
    /*suspend; */
  end
  suspend;
end
^


CREATE OR ALTER PROCEDURE EXPORT_EPICEA (
    CPT_ESCOMPTE_DEBIT VARCHAR(8),
    CPT_ESCOMPTE_CREDIT VARCHAR(8),
    FACTURE INTEGER,
    REEXPORT INTEGER)
RETURNS (
    NUM_PIECE INTEGER,
    NUM_LIGNE_PIECE INTEGER,
    TYPE_PIECE VARCHAR(1),
    CODE_PIECE VARCHAR(20),
    DATE_PIECE TIMESTAMP,
    NUM_CPT_LIGNE VARCHAR(8),
    LIBELLE_LIGNE VARCHAR(255),
    MT_DEBIT_LIGNE NUMERIC(18,2),
    MT_CREDIT_LIGNE NUMERIC(18,2),
    QTE_1 NUMERIC(18,2),
    QTE_2 NUMERIC(18,2),
    CODE_TVA VARCHAR(20),
    TAUX_TVA NUMERIC(18,2),
    MT_DEBIT_TVA NUMERIC(18,2),
    MT_CREDIT_TVA NUMERIC(18,2),
    DATE_ECHEANCE TIMESTAMP,
    CPT_COLLECTIF VARCHAR(8),
    NOM_TIERS VARCHAR(100),
    ADRESSE_1_TIERS VARCHAR(100),
    ADRESSE_2_TIERS VARCHAR(100),
    CODE_POSTAL_TIERS VARCHAR(5),
    VILLE_TIERS VARCHAR(100),
    EXIGIBILITE_TVA VARCHAR(1),
    DATE_LIVRAISON_LIGNE TIMESTAMP,
    TTC INTEGER,
    TX_REM_HT_FACTURE NUMERIC(15,2))
AS
DECLARE VARIABLE ID_FACT_COURANT INTEGER;
DECLARE VARIABLE ID_ART INTEGER;
DECLARE VARIABLE ESCOMPTE NUMERIC(15,2);
DECLARE VARIABLE EXPORT_COMPTA INTEGER;
begin
 num_piece = 0 ;
 for select
   f.id_facture,f.code_facture, f.date_facture, f.date_ech_facture, f.date_liv_facture, f.libelle_facture,
   infosF.code_compta,infosF.compte,  infosF.nom_tiers, infosF.adresse1, infosF.adresse2, infosF.codepostal,
   infosF.ville,
   f.ttc,f.tx_rem_ht_facture, f.export
   from v_facture f, v_infos_facture infosF
   where /*f.id_facture between :id_deb and :id_fin*/ f.id_facture = :facture  and infosF.id_facture=f.id_facture
   into  :id_fact_courant, :code_piece ,:date_piece ,:date_echeance ,:date_livraison_ligne ,:libelle_ligne ,
   :num_cpt_ligne ,:cpt_collectif,
   :nom_tiers ,:adresse_1_tiers ,:adresse_2_tiers ,:code_postal_tiers ,:ville_tiers,:ttc,:tx_rem_ht_facture, :export_compta
 do begin
   if (export_compta!=1 or reexport=1 ) then begin /* facture pas deja exportee ou reexportation */
     execute procedure recup_lignes_facture(:code_piece) returning_values :id_fact_courant;
     num_piece = num_piece+1;
/*     select calc.mt_ttc-calc.mtnetttc,calc.mtnetttc from CALCUL_TOTAL_DIRECT('facture',:id_fact_courant) calc*/
     select calc.remise_ttc,calc.mtnetttc from CALCUL_TOTAL_DIRECT('facture',:id_fact_courant) calc
     into :escompte,:mt_debit_ligne;
     num_cpt_ligne = '+'||num_cpt_ligne;
     num_ligne_piece = 1;
     type_piece = 'V';
     mt_credit_ligne = 0;
     qte_1 = 0;
     qte_2 = 0;
     code_tva ='';
     taux_tva = 0;
     mt_debit_tva = 0;
     mt_credit_tva = 0;
     exigibilite_tva = '';
     suspend;
     cpt_collectif ='';
     nom_tiers ='';
     adresse_1_tiers ='';
     adresse_2_tiers ='';
     code_postal_tiers ='';
     ville_tiers='';
     
     if (:escompte > 0) then
       begin
           num_cpt_ligne = :cpt_escompte_debit;
           num_ligne_piece = num_ligne_piece + 1;
           type_piece = 'V';
           libelle_ligne = 'Escompte '||libelle_ligne;
           mt_debit_ligne = :escompte;
           mt_credit_ligne = 0;
           qte_1 = 0;
           qte_2 = 0;
           code_tva = '';
           taux_tva = 0;
           mt_debit_tva = 0;
           mt_credit_tva = 0;
           exigibilite_tva = '';
           suspend;
       end
     /*Récupération des lignes*/
     for select
       lf.id_article,lf.lib_l_facture, lf.qte_l_facture , lf.qte_l_facture ,lf.compte_l_facture, lf.code_tva_l_facture , lf.code_t_tva_l_facture ,
       lf.taux_tva_l_facture , lf.mt_ttc_l_facture-lf.mt_ht_l_facture,  lf.mt_ht_l_facture
       from  recup_lignes_exportation(:id_fact_courant,:ttc,:tx_rem_ht_facture) lf
       where lf.id_facture = :id_fact_courant
       into :id_art,:libelle_ligne, :qte_1, :qte_2 ,:num_cpt_ligne,:code_tva, :exigibilite_tva, :taux_tva, :mt_credit_tva, :mt_credit_ligne
     do begin

       num_ligne_piece = num_ligne_piece + 1;
       mt_debit_ligne = 0;
       mt_debit_tva = 0;
       cpt_collectif = '';
       nom_tiers = '';
       adresse_1_tiers = '';
       adresse_2_tiers = '';
       code_postal_tiers = '';
       ville_tiers = '';
       /*select art.numcpt_article from v_article art where art.id_article = :id_art into :num_cpt_ligne;  */
       suspend;
     end /*fin boucle sur lignes facture*/
     update ta_facture f set f.export=1 where f.id_facture=:id_fact_courant;
     execute procedure vide_document_temp('facture');
   end /* fin test deja exportee */
 end /* fin boucle sur facture */
end
^


CREATE OR ALTER PROCEDURE INFOSDEVIS (
    ID_DOCUMENT_RECUP INTEGER,
    ID_TIERS_RECUP INTEGER,
    LIVRAISON INTEGER)
RETURNS (
    ID_INFOS_DEVIS INTEGER,
    ID_ADRESSE INTEGER,
    ID_DEVIS INTEGER,
    ADRESSE1 VARCHAR(100),
    ADRESSE2 VARCHAR(100),
    ADRESSE3 VARCHAR(100),
    CODEPOSTAL VARCHAR(5),
    VILLE VARCHAR(100),
    PAYS VARCHAR(100),
    ADRESSE1_LIV VARCHAR(100),
    ADRESSE2_LIV VARCHAR(100),
    ADRESSE3_LIV VARCHAR(100),
    CODEPOSTAL_LIV VARCHAR(5),
    VILLE_LIV VARCHAR(100),
    PAYS_LIV VARCHAR(100),
    CODE_COMPTA VARCHAR(7),
    COMPTE VARCHAR(8),
    ID_TIERS INTEGER,
    NOM_TIERS VARCHAR(100),
    PRENOM_TIERS VARCHAR(100),
    SURNOM_TIERS VARCHAR(20),
    CODE_T_CIVILITE VARCHAR(20),
    CODE_T_ENTITE VARCHAR(20),
    TVA_I_COM_COMPL VARCHAR(50),
    CODE_C_PAIEMENT VARCHAR(50),
    LIB_C_PAIEMENT VARCHAR(255),
    REPORT_C_PAIEMENT INTEGER,
    FIN_MOIS_C_PAIEMENT INTEGER)
AS
begin

for select

  infos.ID_INFOS_DEVIS,
  cast (null as INTEGER),
  infos.ID_DEVIS,
  infos.ADRESSE1,
  infos.ADRESSE2,
  infos.ADRESSE3,
  infos.CODEPOSTAL,
  infos.VILLE,
  infos.PAYS,         
  infos.ADRESSE1_LIV,
  infos.ADRESSE2_LIV,
  infos.ADRESSE3_LIV,
  infos.CODEPOSTAL_LIV,
  infos.VILLE_LIV,
  infos.PAYS_LIV,         
  infos.CODE_COMPTA,
  infos.COMPTE,
  cast (null as INTEGER),
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_T_CIVILITE,
  infos.CODE_T_ENTITE,
  infos.TVA_I_COM_COMPL,
  infos.CODE_C_PAIEMENT,
  infos.LIB_C_PAIEMENT ,
  infos.REPORT_C_PAIEMENT ,
  infos.FIN_MOIS_C_PAIEMENT 
  
   
  from ta_infos_devis infos where infos.id_devis = :id_DOCUMENT_recup into :ID_INFOS_DEVIS ,:id_adresse,
  :id_devis ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
  :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
  :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE, :CODE_T_ENTITE,:TVA_I_COM_COMPL,
  :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin

  suspend;   
end
if (livraison=0) then
    begin
      for select
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'FACT')
      into :ID_INFOS_DEVIS ,:id_adresse,
      :id_devis ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'LIV'))
      into :ID_INFOS_DEVIS ,:id_adresse,
      :id_devis ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end

if (livraison=1) then
    begin
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and (tadr.code_t_adr = 'LIV')
      into :ID_INFOS_DEVIS ,:id_adresse,
      :id_devis ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
      for select
      
      cast (null as INTEGER),
      adresse.id_adresse,
      cast (null as INTEGER),
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      adresse.ADRESSE1_ADRESSE,
      adresse.ADRESSE2_ADRESSE,
      adresse.ADRESSE3_ADRESSE,
      adresse.CODEPOSTAL_ADRESSE,
      adresse.VILLE_ADRESSE,
      adresse.PAYS_ADRESSE,         
      cast(null as varchar(8)),
      cast(null as varchar(8)),
      tiers.id_tiers,
      cast(null as varchar(100)),
      cast(null as varchar(100)),
      cast(null as varchar(20)),
      civilite.CODE_T_CIVILITE,
      entite.CODE_T_ENTITE,
      cast (null as varchar(50)),
      cast (null as varchar(50)),
      cast (null as varchar(255)),
      cast (null as INTEGER) ,
      cast (null as INTEGER) 
      
      from ta_adresse adresse  
      left join ta_r_adr rAdr on rAdr.ID_ADRESSE = adresse.ID_ADRESSE
      left join ta_r_adr_t_adr rAdrTAdr on rAdrTAdr.id_adresse = adresse.ID_ADRESSE
      left join ta_t_adr tAdr on tAdr.id_t_adr = rAdrTAdr.id_t_adr
      left join Ta_tiers tiers on tiers.ID_TIERS = rAdr.ID_TIERS
      left join TA_T_CIVILITE civilite on civilite.id_T_civilite = tiers.id_T_civilite
      left join ta_t_entite entite on entite.id_T_entite = tiers.id_t_entite
      where tiers.id_tiers = :id_tiers_recup  and ((tadr.code_t_adr is null) or (tadr.code_t_adr = 'FACT'))
      into :ID_INFOS_DEVIS ,:id_adresse,
      :id_devis ,:ADRESSE1 ,:ADRESSE2 ,:ADRESSE3 ,:CODEPOSTAL ,:VILLE ,:PAYS ,:ADRESSE1_LIV ,
      :ADRESSE2_LIV ,:ADRESSE3_LIV ,:CODEPOSTAL_LIV ,:VILLE_LIV ,:PAYS_LIV ,:CODE_COMPTA ,
      :COMPTE ,:ID_TIERS ,:NOM_TIERS ,:PRENOM_TIERS ,:SURNOM_TIERS, :CODE_T_CIVILITE,:CODE_T_ENTITE,:TVA_I_COM_COMPL,
      :CODE_C_PAIEMENT,:LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
      do begin
          suspend;
         end
    end
end
^
RECREATE VIEW V_FACTURE(
    ID_FACTURE,
    CODE_FACTURE,
    DATE_FACTURE,
    DATE_ECH_FACTURE,
    DATE_LIV_FACTURE,
    LIBELLE_FACTURE,
    ID_ADRESSE,
    ADRESSE1_ADRESSE,
    ADRESSE2_ADRESSE,
    ADRESSE3_ADRESSE,
    CODEPOSTAL_ADRESSE,
    VILLE_ADRESSE,
    PAYS_ADRESSE,
    ID_ADRESSE_LIV,
    ADRESSE1_ADRESSELIV,
    ADRESSE2_ADRESSELIV,
    ADRESSE3_ADRESSELIV,
    CODEPOSTAL_ADRESSELIV,
    VILLE_ADRESSELIV,
    PAYS_LIV,
    ID_TIERS,
    CODE_TIERS,
    NOM_TIERS,
    PRENOM_TIERS,
    SURNOM_TIERS,
    CODE_COMPTA,
    COMPTE,
    ID_T_PAIEMENT,
    CODE_T_PAIEMENT,
    ID_C_PAIEMENT,
    REGLE_FACTURE,
    REM_HT_FACTURE,
    TX_REM_HT_FACTURE,
    REM_TTC_FACTURE,
    TX_REM_TTC_FACTURE,
    NB_E_FACTURE,
    MT_TTC_CALC,
    MT_HT_CALC,
    MT_TVA_CALC,
    NET_TTC_CALC,
    NET_HT_CALC,
    NET_TVA_CALC,
    IP_ACCES,
    TTC,
    EXPORT,
    COMMENTAIRE)
AS
select
  Facture.ID_FACTURE,
  Facture.CODE_FACTURE,
  Facture.DATE_FACTURE,
  Facture.DATE_ECH_FACTURE,
  Facture.DATE_LIV_FACTURE,
  Facture.LIBELLE_FACTURE,
  Facture.ID_ADRESSE,
  infos.adresse1,
  infos.adresse2,
  infos.adresse3,
  infos.codepostal,
  infos.ville,
  infos.pays,
  Facture.ID_ADRESSE_LIV,
  infos.adresse1_liv,
  infos.adresse2_liv,
  infos.adresse3_liv,
  infos.codepostal_liv,
  infos.ville_liv,
  infos.PAYS_LIV,
  Facture.ID_TIERS,
  Tiers.code_tiers,
  infos.NOM_TIERS,
  infos.PRENOM_TIERS,
  infos.SURNOM_TIERS,
  infos.CODE_COMPTA,
  infos.COMPTE,
  Facture.ID_T_PAIEMENT,
  TPaiement.code_t_paiement,
  CPaiement.id_c_paiement,
  Facture.REGLE_FACTURE,
  Facture.REM_HT_FACTURE,
  Facture.TX_REM_HT_FACTURE,
  Facture.REM_TTC_FACTURE,
  Facture.TX_REM_TTC_FACTURE,
  Facture.NB_E_FACTURE,
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  cast(0 as double precision),
  Facture.IP_ACCES,
  Facture.TTC,
  Facture.export,
  Facture.COMMENTAIRE
  from TA_FACTURE Facture
  left join ta_infos_facture infos on (infos.ID_INFOS_FACTURE = Facture.id_adresse )
  left join ta_tiers Tiers on (Tiers.id_tiers = Facture.id_tiers)
  left join ta_t_paiement TPaiement on (TPaiement.id_t_paiement = Facture.id_t_paiement)
  left join ta_c_paiement CPaiement on (CPaiement.id_c_paiement = Facture.id_c_paiement)
^
