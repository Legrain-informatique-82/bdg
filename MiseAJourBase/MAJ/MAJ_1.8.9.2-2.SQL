set term ^;
RECREATE VIEW V_C_PAIEMENT(
    ID_C_PAIEMENT,
    LIB_C_PAIEMENT,
    ID_TIERS,
    REPORT_C_PAIEMENT,
    FIN_MOIS_C_PAIEMENT,
    IP_ACCES)
AS
select
  CPaiement.ID_C_PAIEMENT,
  CPaiement.LIB_C_PAIEMENT ,
  CPaiement.ID_TIERS,
  CPaiement.REPORT_C_PAIEMENT,
  CPaiement.FIN_MOIS_C_PAIEMENT,
    CPaiement.IP_ACCES

from TA_C_PAIEMENT CPaiement
^

drop trigger TA_C_PAIEMENT_AIU0
^

CREATE OR ALTER PROCEDURE INFOSCPAIEMENT (
    id_facture_recup integer,
    id_tiers_recup integer)
returns (
    id_infos_facture integer,
    id_c_paiement integer,
    id_facture integer,
    id_tiers integer,
    lib_c_paiement varchar(255),
    report_c_paiement integer,
    fin_mois_c_paiement integer)
as
begin
for select

  infos.ID_INFOS_FACTURE,
  cast (null as INTEGER),
  infos.ID_FACTURE,
  cast (null as INTEGER),
  infos.LIB_C_PAIEMENT ,
  infos.REPORT_C_PAIEMENT ,
  infos.FIN_MOIS_C_PAIEMENT 
  
   
  from TA_INFOS_FACTURE infos where infos.ID_FACTURE = :id_facture_recup into :ID_INFOS_FACTURE ,:ID_C_PAIEMENT,:ID_FACTURE,
  :ID_TIERS,   :LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
do begin
  suspend;   
end
  for select
  
  cast (null as INTEGER),
  cPaiement.id_c_paiement,
  cast (null as INTEGER),
  cPaiement.id_tiers,
  cPaiement.LIB_C_PAIEMENT,
  cPaiement.REPORT_C_PAIEMENT,
  cPaiement.FIN_MOIS_C_PAIEMENT 
  
  from ta_c_paiement cPaiement  
  where cPaiement.id_tiers = :id_tiers_recup
  into :ID_INFOS_FACTURE ,:ID_C_PAIEMENT,:ID_FACTURE,:ID_TIERS , :LIB_C_PAIEMENT,:REPORT_C_PAIEMENT,:FIN_MOIS_C_PAIEMENT
  do begin
      suspend;
     end
     
end
^

CREATE OR ALTER TRIGGER TBI_C_PAIEMENT FOR TA_C_PAIEMENT
ACTIVE BEFORE INSERT POSITION 2
as
begin
   If (New.ID_C_PAIEMENT  is null) Then
      New.ID_C_PAIEMENT  = GEN_ID(NUM_ID_C_PAIEMENT,1);
   New.QUI_CREE_C_PAIEMENT  = USER;
   New.QUAND_CREE_C_PAIEMENT = 'NOW';
   New.QUI_MODIF_C_PAIEMENT = USER;
   New.QUAND_MODIF_C_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

drop trigger TBI_XAKCODE_C_PAIEMENT
^
drop trigger TBU_XAKCODE_C_PAIEMENT
^


CREATE OR ALTER TRIGGER TBU_C_PAIEMENT FOR TA_C_PAIEMENT
ACTIVE BEFORE UPDATE POSITION 2
as
begin
   New.QUI_MODIF_C_PAIEMENT = USER;
   New.QUAND_MODIF_C_PAIEMENT = 'NOW';
   new.IP_ACCES=current_connection;
   select num_version from ta_version into new."VERSION";
end
^

ALTER TABLE TA_C_PAIEMENT DROP CODE_C_PAIEMENT
^

CREATE GENERATOR NUM_ID_COMPTE_BANQUE
^

CREATE TABLE TA_COMPTE_BANQUE (
    ID_COMPTE_BANQUE           DID3 NOT NULL /* DID3 = INTEGER NOT NULL */,
    NOM_BANQUE                 DLGR_CODE /* DLGR_CODE = VARCHAR(20) NOT NULL */,
    COMPTE                     DLGR_CODE /* DLGR_CODE = VARCHAR(20) NOT NULL */,
    CODE_BANQUE                DLGR_CODE /* DLGR_CODE = VARCHAR(20) NOT NULL */,
    CODE_GUICHET               DLGR_CODEL /* DLGR_CODEL = VARCHAR(20) */,
    CLE_RIB                    DLGR_2 /* DLGR_2 = VARCHAR(2) NOT NULL */,
    ADRESSE1_BANQUE            DLIB100 /* DLIB100 = VARCHAR(100) */,
    ADRESSE2_BANQUE            DLIB100 /* DLIB100 = VARCHAR(100) */,
    CP_BANQUE                  DCODPOS /* DCODPOS = VARCHAR(25) */,
    VILLE_BANQUE               DLIB100 /* DLIB100 = VARCHAR(100) */,
    IBAN                       DLIB100 /* DLIB100 = VARCHAR(100) */,
    CODE_B_I_C                 DLGR_CODEL /* DLGR_CODEL = VARCHAR(20) */,
    TITULAIRE                  DLIB100 /* DLIB100 = VARCHAR(100) */,
    TYPE_COMPTE                DID9FACULT /* DID9FACULT = NUMERIC(15,2) */,
    QUI_CREE_COMPTE_BANQUE     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE_COMPTE_BANQUE   TIMESTAMP DEFAULT 'NOW',
    QUI_MODIF_COMPTE_BANQUE    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF_COMPTE_BANQUE  TIMESTAMP DEFAULT 'NOW',
    "VERSION"                  NUM_VERSION /* NUM_VERSION = VARCHAR(20) */,
    IP_ACCES                   DLIB50NN DEFAULT 0 /* DLIB50NN = VARCHAR(50) NOT NULL */,
    VERSION_OBJ                INTEGER
)
^
ALTER TABLE TA_COMPTE_BANQUE ADD PRIMARY KEY (ID_COMPTE_BANQUE)
^

CREATE EXCEPTION E_XAKCOMPTE_BANQUE 'Le compte de banque doit être unique'
^

/* Trigger: TBI_TA_COMPTE_BANQUE */
CREATE OR ALTER TRIGGER TBI_TA_COMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_COMPTE_BANQUE is null) Then
      New.ID_COMPTE_BANQUE = GEN_ID(NUM_ID_COMPTE_BANQUE,1);
   New.QUI_CREE_COMPTE_BANQUE = USER;
   new.QUAND_CREE_COMPTE_BANQUE = 'NOW';
   new.QUI_MODIF_COMPTE_BANQUE = USER;
   new.QUAND_MODIF_COMPTE_BANQUE = 'NOW';
   select num_version from ta_version into new."VERSION";
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBI_XAKCOMPTE_BANQUE */
CREATE OR ALTER TRIGGER TBI_XAKCOMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCOmpte_BANQUE  */
   select count(*) from TA_COMPTE_BANQUE
   where COMPTE = NEW.COMPTE
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCOMPTE_BANQUE ;
end
^


/* Trigger: TBU_TA_COMPTE_BANQUE */
CREATE OR ALTER TRIGGER TBU_TA_COMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.COMPTE = Upper(New.COMPTE);
   new.QUI_MODIF_COMPTE_BANQUE = USER;
   select num_version from ta_version into new."VERSION";
   new.QUAND_MODIF_COMPTE_BANQUE = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_XAKCOMPTE_BANQUE */
CREATE OR ALTER TRIGGER TBU_XAKCOMPTE_BANQUE FOR TA_COMPTE_BANQUE
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCOMPTE_BANQUE  */
   If  ((OLD.COMPTE <> NEW.COMPTE)) then
   begin
     select count(*) from TA_COMPTE_BANQUE
     where COMPTE = NEW.COMPTE
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCOMPTE_BANQUE ;
   end
end
^


/******************/
ALTER TABLE TA_ADRESSE ADD ID_T_ADR did4 
^
ALTER TABLE TA_ADRESSE ADD ID_TIERS DID3
^
alter table TA_ADRESSE alter ID_TIERS position 8
^
alter table TA_ADRESSE alter ID_T_ADR position 9
^
alter table TA_ADRESSE add constraint FK_TA_ADRESSE_2 foreign key (ID_TIERS) references TA_TIERS(ID_TIERS)
^
ALTER TABLE TA_ADRESSE ADD CONSTRAINT FK_TA_ADRESSE_1 FOREIGN KEY (ID_T_ADR) REFERENCES TA_T_ADR (ID_T_ADR)
^

/******************/

ALTER TABLE TA_EMAIL ADD ID_TIERS DID3
^
alter table TA_EMAIL alter ID_TIERS position 3
^
ALTER TABLE TA_EMAIL ADD ID_T_EMAIL DID4
^
alter table TA_EMAIL alter ID_T_EMAIL position 4
^
alter table TA_EMAIL add constraint FK_TA_EMAIL_2 foreign key (ID_TIERS) references TA_TIERS(ID_TIERS)
^
alter table TA_EMAIL add constraint FK_TA_EMAIL_1 foreign key (ID_T_EMAIL) references TA_T_EMAIL(ID_T_EMAIL)
^

/******************/
ALTER TABLE TA_TELEPHONE ADD ID_T_TEL DID4
^
ALTER TABLE TA_TELEPHONE ADD ID_TIERS DID3
^
alter table TA_TELEPHONE alter ID_T_TEL position 4
^
alter table TA_TELEPHONE alter ID_T_TEL position 5
^
alter table TA_TELEPHONE add constraint FK_TA_TELEPHONE_2 foreign key (ID_TIERS) references TA_TIERS(ID_TIERS)
^
alter table TA_TELEPHONE add constraint FK_TA_TELEPHONE_1 foreign key (ID_T_TEL) references TA_T_TEL(ID_T_TEL)
^


/******************/
ALTER TABLE TA_WEB ADD ID_T_WEB DID4
^
ALTER TABLE TA_WEB ADD ID_TIERS DID3
^
alter table TA_WEB alter ID_TIERS position 3
^
alter table TA_WEB alter ID_T_WEB position 4
^
alter table TA_WEB add constraint FK_TA_WEB_1 foreign key (ID_T_WEB) references TA_T_WEB(ID_T_WEB)
^
alter table TA_WEB add constraint FK_TA_WEB_2 foreign key (ID_TIERS) references TA_TIERS(ID_TIERS)
^

set term ;^
