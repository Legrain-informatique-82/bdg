CREATE TABLE TA_CHAMP_SUPP_ART
(
  ID_CHAMP_SUPP_ART DID3 NOT NULL,
  NOM_CHAMP_SUPP_ART DLIB255NN,
  TYPE_VALEUR_CHAMP_SUPP_ART DLIB255NN,/*string, num, date, bool, (liste)*/
  DESCRIPTION_CHAMP_SUPP_ART DLIB255NN,
  DEFAUT_CHAMP_SUPP_ART DLIB255NN,
  /* VALEUR_LISTE DLIB255NN, */

  QUI_CREE_CHAMP_SUPP_ART DLIB50,
  QUAND_CREE_CHAMP_SUPP_ART Timestamp DEFAULT 'NOW',
  QUI_MODIF_CHAMP_SUPP_ART DLIB50,
  QUAND_MODIF_CHAMP_SUPP_ART Timestamp DEFAULT 'NOW',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ Integer,
  PRIMARY KEY (ID_CHAMP_SUPP_ART)
);

CREATE GENERATOR NUM_ID_CHAMP_SUPP_ART;
SET GENERATOR NUM_ID_CHAMP_SUPP_ART TO 0;


CREATE TABLE TA_R_CHAMP_SUPP_ART
(
  ID_R_CHAMP_SUPP DID3 NOT NULL,
  ID_ARTICLE DID3 NOT NULL,
  ID_CHAMP_SUPP_ART DID3 NOT NULL,
  VALEUR DLIB255NN,

  QUI_CREE_R_CHAMP_SUPP DLIB50,
  QUAND_CREE_R_CHAMP_SUPP Timestamp DEFAULT 'NOW',
  QUI_MODIF_R_CHAMP_SUPP DLIB50,
  QUAND_MODIF_R_CHAMP_SUPP Timestamp DEFAULT 'NOW',
  "VERSION" NUM_VERSION,
  IP_ACCES DLIB50NN DEFAULT 0,
  VERSION_OBJ Integer,
  PRIMARY KEY (ID_R_CHAMP_SUPP)
);

ALTER TABLE TA_R_CHAMP_SUPP_ART ADD CONSTRAINT FK_TA_R_CHAMP_SUPP_1
  FOREIGN KEY (ID_ARTICLE) REFERENCES TA_ARTICLE (ID_ARTICLE);

ALTER TABLE TA_R_CHAMP_SUPP_ART ADD CONSTRAINT FK_TA_R_CHAMP_SUPP_2
  FOREIGN KEY (ID_CHAMP_SUPP_ART) REFERENCES TA_CHAMP_SUPP_ART (ID_CHAMP_SUPP_ART);
  
CREATE GENERATOR NUM_ID_R_CHAMP_SUPP;
SET GENERATOR NUM_ID_R_CHAMP_SUPP TO 0;

/***********************************************************************************************************************************/


SET TERM ^ ;
create TRIGGER TBID_CHAMP_SUPP_ART FOR TA_CHAMP_SUPP_ART ACTIVE
BEFORE INSERT POSITION 1
as
begin
   If (New.ID_CHAMP_SUPP_ART  is null) Then
      New.ID_CHAMP_SUPP_ART  = GEN_ID(NUM_ID_CHAMP_SUPP_ART,1);
   New.QUI_CREE_CHAMP_SUPP_ART  = USER;
   New.QUAND_CREE_CHAMP_SUPP_ART = 'NOW';
   New.QUI_MODIF_CHAMP_SUPP_ART = USER;
   New.QUAND_MODIF_CHAMP_SUPP_ART = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end^
SET TERM ; ^

SET TERM ^ ;
create TRIGGER TBU_CHAMP_SUPP_ART FOR TA_CHAMP_SUPP_ART ACTIVE
BEFORE UPDATE POSITION 1
as
begin
   new.QUI_MODIF_CHAMP_SUPP_ART = USER;
   new.QUAND_MODIF_CHAMP_SUPP_ART = 'NOW';
   new.IP_ACCES = current_connection;
   select num_version from ta_version into new."VERSION";
end^
SET TERM ; ^


/***********************************************************************************************************************************/
IDBD.ini
====
TaChampSuppArt=idChampSuppArt
TaRChampSuppArt=idRChampSuppArt

CtrlBD.ini
====
T_CHAMP_SUPP_ART.TaChampSuppArt.nomChampSuppArt =100;112
T_CHAMP_SUPP_ART.TaChampSuppArt.typeValeurChampSuppArt =100;112
T_CHAMP_SUPP_ART.TaChampSuppArt.descriptionChampSuppArt =100;112
T_CHAMP_SUPP_ART.TaChampSuppArt.defautChampSuppArt =100;112

ListeChampGrille.properties
====
SWTPaTypeChampSuppArticleController.nomChampSuppArt=Nom;150
SWTPaTypeChampSuppArticleController.typeValeurChampSuppArt=Type;150
SWTPaTypeChampSuppArticleController.descriptionChampSuppArt=Description;150
SWTPaTypeChampSuppArticleController.defautChampSuppArt=Valeur par d\u00e9faut;150


TaArticle.java
====
private Set<TaRChampSuppArt> taRChampSuppArtes = new HashSet<TaRChampSuppArt>(0);


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taArticle")
	public Set<TaRChampSuppArt> getTaRChampSuppArtes(){
		return this.taRChampSuppArtes;
	}

	public void setTaRChampSuppArtes(Set<TaRChampSuppArt> taRChampSuppArtes) {
		this.taRChampSuppArtes = taRChampSuppArtes;
	}


