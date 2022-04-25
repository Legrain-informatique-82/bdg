package fr.legrain.archivageepicea.importation_DB;

public class ClassQuerySql {
	
	public String annee;
	
	
public ClassQuerySql(String annee){
	this.annee = annee;
}
public ClassQuerySql(){
}
	



	/**
	 * pour firebird
	 * CREATE generator and CREATE TRIGGER are for augement automatic
	 * ID of table historique!!
	 * 
	 * if firebird is version 2.0, we can apply 
	 * CREATE SEQUENCE name_sequence;
	 * 
	 *  insert   into   yourTable   (yourId,   f1,   f2)   
	 *  values   (next   value   for   sq_test,   ValueOfF1,   ValueOfF2);
	 */
	public String makeTableHistorique1 ="CREATE generator foo;";
	public String makeTableHistorique3="CREATE TRIGGER historiqueBeforeInsert FOR historique ACTIVE BEFORE INSERT POSITION 0 AS BEGIN IF( New.ID IS NULL)THEN begin New.ID = GEN_ID(Foo,1); end end";	
	public String makeTableHistorique2="CREATE TABLE historique(ID integer,histRqt varchar(500), perD varchar(255),perF varchar(255),comD varchar(255),comF varchar(255),monD varchar(255),monF varchar(255), refD varchar(255), refF varchar(255),lib varchar(255));";
	
	/*
	 * remplacer ID_Ligne smallint par ID_Ligne integer  
	 */
	public String makeTableEcriture = "create table Ecritures(Supprime varchar(1), ID integer ," +
										"ID_Piece integer," +
										"TypeLigne char(1) ," +
										"ID_Ligne integer ," +
										"\"DATE\" date ," +
										"Compte varchar(8) ," +
										"Tiers varchar(8) ," +
										"Libelle varchar(100) ," +
										"Qt1 float ," +
										"Qt2 float ," +
										"DebitSaisie numeric(15,3) ," +
										"CreditSaisie numeric(15,3) ," +
										"ID_Devise integer ," +
										"Debit numeric(15,3) ," +
										"Credit numeric(15,3) ," +
										"TvaCode varchar(2) ," +
										"TvaType char(1) ," +
										"TvaTaux float ," +
										"TvaDate date ," +
										"Rapprochement varchar(8) ," +
										"Validation date ," +
										"Qui varchar(10) ," +
										"Quand timestamp ," +
										"Num_Cheque varchar(50) ," +
										"Table_Gen varchar(50) ," +
										"Champ_Gen varchar(50) ," +
										"Val_Champ_Gen varchar(100) ," +
										"ID_GESTCO float ," +
										"CODE_GESTCO varchar(50) ," +
										"Montant_Tva numeric(15,3) ," +
										"ID_GESTANAL integer ," +
										"CODE_GESTANAL varchar(50));";
	
	public String makeTableHtva = "create table HTva(ID integer ," +
									"ID_Ecriture integer ," +
									"ID_Piece integer," +
									"ID_Declaration integer ," +
									"Part_Declaree float ," +
									"Debut_Periode timestamp ," +
									"Fin_Periode timestamp ," +
									"Verrouillee BOOL ," +
									"Deverrouillable BOOL," +
									"En_Attente BOOL," +
									"Date_Tva timestamp ," +
									"Reference_OD varchar(9) ," +
									"MontantTVA numeric(15,3) ," +
									"HTDeclare numeric(15,3) ," +
									"Divers varchar(100) ," +
									"CodeCompensation varchar(2) ," +
									"Taux float );";
	
	/*
	 * remplacer TpyePiece smallint par TpyePiece integer  
	 */
	public String makeTablePieces= "create table Pieces(Supprime varchar(1),ID integer ," +
									"Journal integer ," +
									"Reference varchar(9) ," +
									"\"DATE\" date  ," +
									"Libelle varchar(100) ," +
									"TypePiece integer ," +
									"Compte varchar(8) ," +
									"ID_Devise integer ," +
									"Validation date ," +
									"Qui varchar(10) ," +
									"Quand timestamp ," +
									"Num_Cheque varchar(50) ," +
									"ID_GESTCO float ," +
									"CODE_GESTCO varchar(50) ," +
									"Table_Gen varchar(50) ," +
									"Champ_Gen varchar(50) ," +
									"Val_Champ_Gen varchar(100)," +
									"Echeance date );"; 
	

	
	
	public String makeTablePlanCpt = "create table PlanCpt(Compte varchar(8) ," +
									"Libelle varchar(100) " +
									",Debit_Deb float ," +
									"Credit_Deb float ," +
									"Un1 varchar(2) ," +
									"Un2 varchar(2) ," +
									"Qt1_Deb float ," +
									"Qt2_Deb float ," +
									"PlanSaisie BOOL ," +
									"TvaCode varchar(2) ," +
									"TvaType varchar(1) ," +
									"TvaDebit BOOL," +
									"SensCredit BOOL," +
									"Collectif BOOL," +
									"Centraliser BOOL," +
									"Tiers BOOL," +
									"Rapprochement BOOL," +
									"Immobilisation BOOL," +
									"ImmoAmort BOOL," +
									"ImmoDegressif BOOL," +
									"Emprunt BOOL," +
									"Abandonne BOOL," +
									"Qui varchar(10) ," +
									"Quand timestamp ," +
									"ID_Affichage integer ," +
									"JBanque varchar(15) ," +
									"pointable BOOL," +
									"taux float ," +
									"DIV varchar(255) ," +
									"ID_GESTANAL integer ," +
									"CODE_GESTANAL varchar(50) ," +
									"UtilOuvAuto BOOL);";
	
	public String makeTablePointage = "create table Pointage(ID float ," +
										"ID_Debit integer ," +
										"ID_Credit integer ," +
										"\"DATE\" date ," +
										"Montant numeric(15,3) ," +
										"TvaDate date ," +
										"Validation date ," +
										"Qui varchar(10) ," +
										"Quand timestamp);";

	public String makeTableResteDC = "create table ResteDC(ID integer ," +
									"ID_Ecriture integer ," +
									"ID_Piece integer ," +
									"\"DATE\" date ," +
									"Reference varchar(9) ," +
									"Tiers varchar(8) ," +
									"Libelle varchar(100) ," +
									"Debit numeric(15,3) ," +
									"Credit numeric(15,3) ," +
									"Sens varchar(1) ," +
									"Reste numeric(15,3) ," +
									"Lettre varchar(1) ," +
									"Montant numeric(15,3) ," +
									"Ligne integer );";
	
	public String makeTablePlanAux = "create table PlanAux(Tiers varchar(8) ," +
										"Nom varchar(100) ," +
										"Compte varchar(8) ," +
										"\"TYPE\" varchar(1) ," +
										"SensCredit BOOL," +
										"Rapprochement BOOL," +
										"TvaDebit BOOL," +
										"Abandonne Bool," +
										"Debit_Deb numeric(15,3) ," +
										"Credit_Deb numeric(15,3) ," +
										"Adresse1 varchar(100) ," +
										"Adresse2 varchar(100) ," +
										"Poste varchar(5) ," +
										"Ville varchar(5) ," +
										"Pays varchar(20) ," +
										"Livr_Adr1 varchar(100) ," +
										"Livr_Adr2 varchar(100) ," +
										"Livr_Poste varchar(5) ," +
										"Livr_Ville varchar(25) ," +
										"Livr_Pays varchar(20) ," +
										"Telephone1 varchar(15) ," +
										"Telephone2 varchar(15)," +
										"Telecopie varchar(15)," +
										"Mel varchar(30)," +
										"Qui varchar(10)," +
										"Quand timestamp," +
										"Actif BOOL," +
										"ID_GESTCO float," +
										"ID_COMMUNICATION float," +
										"CODE_GESTCO varchar(50)," +
										"ID_Affichage integer," +
										"JBanque varchar(15));";
	
	// Creation de la table balance_$annee
	/*Compte;Tiers;Debit_Report;Credit_Report;
	 * Debit_Base;Credit_Base;Debit_Total;
	 * Credit_Total;Qt1_Report;Qt2_Report;
	 * Qt1_Base;Qt2_Base;Qt1_Total;Qt2_Total;ID_Affichage */
	public String makeTableBalance(String annee) { 
		return "create table balance_"+annee+"(Compte varchar(8),Tiers varchar(50)," +
			" Debit_Report numeric(15,3), Credit_Report numeric(15,3), Debit_base numeric(15,3),Credit_Base numeric(15,3), Debit_Total numeric (15,3),Credit_Total numeric (15,3)," +
			" Qt1_Report varchar(50), Qt2_Report varchar(50),Qt1_Base varchar(50), Qt2_Base varchar(50), Qt1_Total varchar(50), Qt2_Total varchar(50), ID_Affichage integer);";
	}
	/*Compte;Tiers;Debit_Report;
	 * Credit_Report;Debit_Base;Credit_Base;
	 * Debit_Total;Credit_Total;Qt1_Report;
	 * Qt2_Report;Qt1_Base;Qt2_Base;Qt1_Total;
	 * Qt2_Total;ID_Affichage*/
//	public String makeTableBalanceTiers = "create table balanceTiers_"+annee+"(Compte varchar(8),Tiers varchar(50)," +
//	" Debit_Report numeric(15,3), Credit_Report numeric(15,3), Debit_base numeric(15,3), Debit_Total numeric (15,3)" +
//	" Qt1_Base varchar(50), Qt2_Base varchar(50), Qt1_Total varchar(50), Qt2_Total varchar(50), ID_Affichage integer(11));";
	
	public String makeTableBalanceTiers(String annee){
		return "create table balanceTiers_"+annee+"(Compte varchar(8),Tiers varchar(50)," +
		" Debit_Report numeric(15,3), Credit_Report numeric(15,3), Debit_base numeric(15,3),Credit_base numeric(15,3),  Debit_Total numeric (15,3), Credit_Total numeric(15,3),Qt1_Report varchar(50),Qt2_Report varchar(50)," +
		" Qt1_Base varchar(50), Qt2_Base varchar(50), Qt1_Total varchar(50), Qt2_Total varchar(50), ID_Affichage integer);";
	}

	
	// Procedures
	public String makeProcedureEcriture = "CREATE PROCEDURE procedure_onglet_ecriture(query VARCHAR(5000))"+
										  "\n RETURNS (LIBELLE VARCHAR(255), REFERENCE VARCHAR(9)," +
										  "\n CREDIT NUMERIC(15,2),DEBIT NUMERIC(15,2),DATE_EC DATE," +
										  "\n TIERS VARCHAR(20),COMPTE VARCHAR(9))" +
										  "\n AS " +
										  "\n begin " +
										  "\n if (query is not null) then " +
										  "\n begin " +
										  "\n  for  execute statement :query into COMPTE,TIERS,LIBELLE," +
										  "\n	 date_ec,DEBIT,CREDIT,REFERENCE do" +
										  "\n	 suspend;" +
										  "\n end" +
										  "\n else" +
										  "\n begin" +
										  "\n for select E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE from ECRITURES E join PIECES P on (E.ID_PIECE=P.ID) WHERE cast(E.COMPTE  AS integer)>= '431' into" +
										  "\n  COMPTE,TIERS,LIBELLE,date_ec,DEBIT,CREDIT,REFERENCE do" +
										  "\n  suspend;" +
										  "\n  end " +
										  "\n end";	
//	public String makeProcedureEcriture = "CREATE PROCEDURE PROCEDURE_ONGLET_ECRITURE (" +
//	"\n query varchar(5000))returns (" +
//	"\n libelle varchar(255), reference varchar(9),id integer," +
//	"\n credit numeric(15,2),debit numeric(15,2),date_ec date," +
//	"\n tiers varchar(20),compte varchar(9))" +
//	"\n as begin" +
//	"\n if (query is not null) then" +
//	"\n begin for  execute statement :query into ID,COMPTE,TIERS,LIBELLE," +
//	"\n date_ec,DEBIT,CREDIT,REFERENCE do suspend;" +
//	"\n end" +
//	"\n else" +
//	"\n begin" +
//	"\n for select distinct E.ID,E.COMPTE,E.TIERS,E.LIBELLE,E.\"DATE\",E.DEBIT,E.CREDIT,P.REFERENCE from ECRITURES E join PIECES P on (E.ID_PIECE=P.ID) WHERE cast(E.COMPTE  AS integer)>= '431' into" +
//	"\n ID,COMPTE,TIERS,LIBELLE,date_ec,DEBIT,CREDIT,REFERENCE do" +
//	"\n suspend;" +
//	"\n end" +
//	"\n	 end";
	
	public String makeProcedurePiece = "CREATE PROCEDURE procedure_onglet_piece(query VARCHAR(5000))" +
									   "\n RETURNS (JOURNAL INTEGER,REFERENCE VARCHAR(9),COMPTE VARCHAR(8)," +
									   "\n DATE_PC DATE,LIBELLE VARCHAR(100))" +
									   "\n AS" +
									   "\n begin" +
									   "\n  if (query is not null) then" +
									   "\n  begin" +
									   "\n  for  execute statement :query into JOURNAL,REFERENCE,COMPTE," +
									   "\n  DATE_PC,LIBELLE do suspend;" +
									   "\n end" +
									   "\n else" +
									   "\n begin" +
									   "\n for select P.journal,P.reference,P.compte,P.\"DATE\",P.libelle from PIECES P into" +
									   "\n JOURNAL,REFERENCE,COMPTE,DATE_PC,LIBELLE do suspend;" +
									   "\n end" +
									   "\n end";
	
	public String makeProcedurePieceEcriture = "CREATE PROCEDURE procedure_onglet_pieceEcriture(QUERY VARCHAR(5000))" +
											   "\n RETURNS (COMPTE VARCHAR(8),TIERS VARCHAR(8),LIBELLE VARCHAR(100)," +
											   "\n QT1 FLOAT,QT2 FLOAT,DEBIT NUMERIC(15,2),CREDIT NUMERIC(15,2))" +
											   "\n AS " +
											   "\n begin" +
											   "\n if (query is not null) then" +
											   "\n begin" +
											   "\n for  execute statement :query into COMPTE,TIERS,LIBELLE," +
											   "\n QT1,QT2,DEBIT,CREDIT do suspend;" +
											   "\n end" +
											   "\n else" +
											   "\n begin" +
											   "\n for select E.COMPTE,E.TIERS,E.LIBELLE,E.QT1,E.QT2,E.DEBIT,E.CREDIT from ECRITURES E into" +
											   "\n  COMPTE,TIERS,LIBELLE,QT1,QT2,DEBIT,CREDIT do suspend;  " +
											   "\n end" +
											   "\n end";
	public String makeProcedureCompte = "CREATE OR ALTER PROCEDURE procedure_onglet_compte(query varchar(5000))" +
										"\n returns (reference varchar(9),libelle varchar(255),date_cp date," +
										"\n qt1 float,qt2 float,debit numeric(15,2),credit numeric(15,2))" +
										"\n as" +
										"\n begin" +
										"\n if (query is not null) then" +
										"\n begin" +
										"\n for  execute statement :query into REFERENCE,LIBELLE,DATE_CP,QT1,QT2,DEBIT,CREDIT do suspend;" +
										"\n end" +
										"\n else" +
										"\n begin for select P.REFERENCE,E.LIBELLE,E.\"DATE\",E.QT1,E.QT2,E.DEBIT,E.CREDIT FROM Ecritures E JOIN Pieces P on (E.ID_PIECE=P.ID) into" +
										" REFERENCE,LIBELLE,DATE_CP,QT1,QT2,DEBIT,CREDIT do suspend;" +
										"\n end" +
										"\n end";
	

	
	// Accesseurs
	
	public void set_annee(String annee){
		this.annee=annee;
	}
	public String get_annee(){
		return annee;
	}

}


