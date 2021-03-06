/******************************************************************************/
/*                 Generated by IBExpert 23/03/2009 10:57:30                  */
/******************************************************************************/



/******************************************************************************/
/*                                   Tables                                   */
/******************************************************************************/


CREATE GENERATOR NUM_ID_T_EMAIL;

CREATE TABLE TA_T_EMAIL (
    ID_T_EMAIL           DID3 NOT NULL /* DID3 = INTEGER NOT NULL */,
    CODE_T_EMAIL         DLGR_CODE /* DLGR_CODE = VARCHAR(20) NOT NULL */,
    LIBL_T_EMAIL         DLGR_LIB /* DLGR_LIB = VARCHAR(100) NOT NULL */,
    QUI_CREE_T_EMAIL     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE_T_EMAIL   TIMESTAMP DEFAULT 'NOW',
    QUI_MODIF_T_EMAIL    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF_T_EMAIL  TIMESTAMP DEFAULT 'NOW',
    "VERSION"          NUM_VERSION /* NUM_VERSION = VARCHAR(20) */,
    IP_ACCES           DLIB50NN DEFAULT 0 /* DLIB50NN = VARCHAR(50) NOT NULL */,
    VERSION_OBJ        INTEGER
);




/******************************************************************************/
/*                                Primary Keys                                */
/******************************************************************************/

ALTER TABLE TA_T_EMAIL ADD PRIMARY KEY (ID_T_EMAIL);


/******************************************************************************/
/*                                  Triggers                                  */
/******************************************************************************/


SET TERM ^ ;



/******************************************************************************/
/*                            Triggers for tables                             */
/******************************************************************************/

CREATE exception E_XAKCODE_T_EMAIL 'Le type d''email doit �tre unique'
^

/* Trigger: TBI_TA_T_EMAIL */
CREATE OR ALTER TRIGGER TBI_TA_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_T_EMAIL is null) Then
      New.ID_T_EMAIL = GEN_ID(NUM_ID_T_EMAIL,1);
   New.CODE_T_EMAIL = Upper(New.CODE_T_EMAIL);
   New.QUI_CREE_T_EMAIL = USER;
   new.QUAND_CREE_T_EMAIL = 'NOW';
   new.QUI_MODIF_T_EMAIL = USER;
   new.QUAND_MODIF_T_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBI_XAKCODE_T_EMAIL */
CREATE OR ALTER TRIGGER TBI_XAKCODE_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE INSERT POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_EMAIL  */
   select count(*) from TA_T_EMAIL
   where CODE_T_EMAIL = NEW.CODE_T_EMAIL
   into :numrows;
   if (numrows > 0) then
      exception E_XAKCODE_T_EMAIL ;
end
^


/* Trigger: TBU_TA_T_EMAIL */
CREATE OR ALTER TRIGGER TBU_TA_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE UPDATE POSITION 0
as
begin
   New.CODE_T_EMAIL = Upper(New.CODE_T_EMAIL);
   new.QUI_MODIF_T_EMAIL = USER;
   new.QUAND_MODIF_T_EMAIL = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end
^


/* Trigger: TBU_XAKCODE_T_EMAIL */
CREATE OR ALTER TRIGGER TBU_XAKCODE_T_EMAIL FOR TA_T_EMAIL
ACTIVE BEFORE UPDATE POSITION 1
as
DECLARE variable numrows integer;
begin
  /* E_XAKCODE_T_EMAIL  */
   If  ((OLD.ID_T_EMAIL <> NEW.ID_T_EMAIL)) then
   begin
     select count(*) from TA_T_EMAIL
     where CODE_T_EMAIL = NEW.CODE_T_EMAIL
     into :numrows;
     if (numrows > 0) then
        exception E_XAKCODE_T_EMAIL ;
   end
end
^


SET TERM ; ^



/******************************************************************************/
/*                                 Privileges                                 */
/******************************************************************************/


/* Privileges of users */
GRANT ALL ON TA_T_EMAIL TO "ADMIN" WITH GRANT OPTION;
