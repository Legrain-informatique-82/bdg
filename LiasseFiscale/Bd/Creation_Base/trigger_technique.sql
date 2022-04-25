/******************************************************************************/
/****                         Triggers for tables                          ****/
/******************************************************************************/

/*************************************************/
/*              TA_ACCES                         */
/*************************************************/
/* Trigger: TBI_TA_ACCES */
CREATE TRIGGER TBI_TA_ACCES FOR TA_ACCES
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_ACCES is null) Then
      New.ID_ACCES = GEN_ID(NUM_ID_ACCES,1);
   New.QUI_CREE_ACCES = USER;
   new.QUAND_CREE_ACCES = 'NOW';
   new.QUI_MODIF_ACCES = USER;
   new.QUAND_MODIF_ACCES = 'NOW';
   new.IP_ACCES = current_connection;
   new.password_acces =  'password';
   select num_version from ta_version into new."VERSION";
end
^

/* Trigger: TBU_TA_ACCES */
CREATE TRIGGER TBU_TA_ACCES FOR TA_ACCES
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_ACCES = USER;
   new.QUAND_MODIF_ACCES = 'NOW';
   new.IP_ACCES = current_connection;
   new.password_acces =  'password';
   select num_version from ta_version into new."VERSION";
end
^
/*************************************************/
/*                TA_VERSION                     */
/*************************************************/
/* Trigger: TBI_TA_VERSION */
CREATE TRIGGER TBI_TA_VERSION FOR TA_VERSION
ACTIVE BEFORE INSERT POSITION 0
as
begin
   If (New.ID_VERSION is null) Then
      New.ID_VERSION = GEN_ID(NUM_ID_VERSION,1);
   New.QUI_CREE_VERSION = USER;
   new.QUAND_CREE_VERSION = 'NOW';
   new.QUI_MODIF_VERSION = USER;
   new.QUAND_MODIF_VERSION = 'NOW';
   new.IP_ACCES = current_connection;
end
^


/* Trigger: TBU_TA_VERSION */
CREATE TRIGGER TBU_TA_VERSION FOR TA_VERSION
ACTIVE BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_VERSION = USER;
   new.QUAND_MODIF_VERSION = 'NOW';
   new.IP_ACCES = current_connection;
end
^
