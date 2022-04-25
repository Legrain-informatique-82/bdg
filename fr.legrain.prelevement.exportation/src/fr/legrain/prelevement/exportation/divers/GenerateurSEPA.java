package fr.legrain.prelevement.exportation.divers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityTransaction;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import fr.legrain.documents.dao.TaEndToEnd;
import fr.legrain.documents.dao.TaEndToEndDAO;
import fr.legrain.documents.dao.TaMandat;
import fr.legrain.documents.dao.TaMandatDAO;
import fr.legrain.documents.dao.TaPrelevement;
import fr.legrain.dossier.dao.TaInfoEntreprise;
import fr.legrain.dossier.dao.TaInfoEntrepriseDAO;
import fr.legrain.lib.data.LibDate;
import fr.legrain.prelevement.exportation.controllers.reponse.PaCriterePrelevementReponseController;
import fr.legrain.prelevement.exportation.divers.pt.sibace.sepa.DirectDebit;
import fr.legrain.prelevement.exportation.divers.pt.sibace.sepa.DirectDebitPaymentGroup;
import fr.legrain.tiers.dao.TaCompteBanque;


		


/**## Direct Debit Usage Example
---
Create a new Direct Debit message:

        DirectDebit dd = new DirectDebit("message id", "company name", "company id");        

Create a new Payment Group with FRST transactions:

        DirectDebitPaymentGroup pg = new DirectDebitPaymentGroup("payment group id", (java.util.Date)executionDate, 
                                                                 "creditor Name", "creditor id",
                                                                 "creditor Iban", "creditor Bic",
                                                                 "FRST");

Add a transaction without adc amendment to the payment group:

        pg.gaddTransactionWithoutAmendment("end to end id", (BigDecimal)value,
                                            "mandate id", (java.util.Date)mandateDate,
                                            "debtor name", "debtor IBAN", "debtor BIC");

Add a transaction with adc amendment (new iban) to the payment group:

        pg.gaddTransactionWithAmendedDebtorAccount("end to end id", (BigDecimal)value,
                                                    "mandate id", (java.util.Date)mandateDate,
                                                    "debtor name", "debtor IBAN", "debtor BIC", "original Iban");

Attach the payment group to the direct debit message:

        dd.addPaymentGroup(pg);

Create a new Payment Group with RCUR transactions:

        pg = new DirectDebitPaymentGroup("payment group id", (java.util.Date)executionDate, 
                                            "creditor Name", "creditor id",
                                            "creditor Iban", "creditor Bic",
                                            "RCUR");

Add a transaction with adc amendment (new bank) to the payment group:

        pg.gaddTransactionWithAmendedDebtorBank("end to end id", (BigDecimal)value,
                                                "mandate id", (java.util.Date)mandateDate,
                                                "debtor name", "debtor IBAN", "debtor BIC");

Attach the payment group to the direct debit message:

        dd.addPaymentGroup(pg);

Write the DirectDebit in a XML ISO-20022 compliant file:
        
        dd.write("/full/path/filename.sepa.xml");

*/

public class GenerateurSEPA {
	static Logger logger = Logger.getLogger(GenerateurSEPA.class.getName());	
	//les objet SEPA
//	private static CreditTransfer creditTransfer;
//	private static CreditTransferPaymentGroup creditTransferPaymentGroup;
//	private static DirectDebit directDebit;
//	private static DirectDebitPaymentGroup directDebitPaymentGroup;
//	private static SepaUtils sepaUtils;

	protected static String 	msgId;
//	protected static Date		creDtTm;
	protected static String		creDtTm; //type modifie
	protected static Integer 	nbOfTxs;
	protected static BigDecimal	ctrlSum;
	protected static String		initgPtyNm;
	protected static String		initgPtyOrgId;
	protected static String		initgPtyPrvtId;
	protected static String 	pmtInfId;
	protected static String 	pmtMtd = "DD"; // DD pour SDD Core
	protected static Boolean	btchBookg; // "true" implique une comptabilisation globale, "false" une comptabilisation unitaire
	protected static Integer	pmtNbOfTxs;
	protected static BigDecimal pmtCtrlSum;
	protected static String 	pmtTpInf;
	protected static String		svcLvl;
	protected static String		svcLvlCd = "SEPA"; // valeur "SEPA" est OBLIGATOIRE!
	protected static String		lclInstrmCd = "CORE"; // "CORE" pour un prélèvement SEPA (SDD Core), "B2B" pour un prélèvement interentreprise
	protected static String		seqTp; /* 	"FRST" (1er d'une serie),
											"RCUR" (récurrent-série en cours),
											"FNAL" (dernier d'une série) ou
											"OOFF" (ponctuel)
											Si "Amendement Indicator" est "true" et "Original
											DebtorAgent" est "SMNDA", alors la valeur
											"FRST" est obligatoire. */
											/** Source: www.bred.fr
											 * Chaque prélèvement SEPA émis dans le cadre d’un mandat doit indiquer une valeur indiquant sa 
											 * séquence de présentation.
											 * 	4 valeurs sont possibles :
											 * 	
											 * 	First : c’est le 1er prélèvement SEPA émis et traité pour ce mandat, même si il y a déjà eu 
											 * 	des prélèvements nationaux émis et accepté pour ce mandat (dans le cadre d’une migration de 
											 * 	prélèvement national en prélèvement SEPA).
											 * 	Si un 1er prélèvement SEPA a déjà été émis mais rejeté par la banque du débiteur avant sa d
											 * 	ate d’échéance, le prélèvement SEPA suivant sera à nouveau considéré comme « first ».
											 * 	
											 * 	Récurrent : ce n’est ni le 1er ni le dernier prélèvement SEPA émis et traité pour ce mandat.
											 * 	
											 * 	Final (optionnel) : c’est le dernier prélèvement SEPA émis pour ce mandat.
											 * 	
											 * 	One off : ce prélèvement est ponctuel et ne donnera pas lieu à une série. Il n’y aura donc 
											 * 	plus d’autres prélèvements possibles avec le même mandat.
											 * 	
											 * 	NB : Une remise de prélèvements SEPA ne peut contenir des prélèvements qu’avec une même valeur 
											 * 	de séquence. 
											 * */
	protected static String 	ctgyPurpCd;
	protected static Date 		reqdColIntDt; // date d'échéance
//	protected static String 	reqdColIntDt; // date d'échéance //type modifie
	protected static String 	cdtrNm; //le titulaire du compte à créditer (max 70 chars)
	protected static String 	cdtrAcctIdIBAN; // IBAN de Créancier
	protected static String 	cdtrAcctCcy = "EUR";
	protected static String 	cdtrAgtFinInstnIdBIC; // BIC de Créancier //composé de 8 à 11 caractères
	protected static String 	ultmtCdtrNm; // max 70 chars
	protected static String 	ultmtCdtrIdOrgId;
	protected static String 	chrgBr = "SLEV"; //Seule la valeur "SLEV" est autorisée
	protected static String 	cdtrSchmeId;
	protected static String 	cdtrSchmeIdId;
	protected static String 	cdtrSchmeIdPrvtId;
	protected static String 	cdtrSchmeIdOthrId;
	protected static String 	cdtrSchmeIdOthrSchmeNm;
	protected static String 	cdtrSchmeIdOthrSchmeNmPrty;
	//Direct Debit Transaction Information
	protected static String 	pmtId;
	protected static String 	instrId;
	protected static String 	endToEndId;
	protected static BigDecimal	instdAmt = new BigDecimal("0"); // Amount must be 0.01 or more and 999999999.99 or less 
	protected static String 	drctDbtTxMndtRltdInfMndtId;
	protected static Date 	 	drctDbtTxMndtRltdInfDtOfSgntr;
	protected static Boolean	drctDbtTxMndtRltdInfAmdmntInd = false; // "true" (si il y a des modifications), "false" (pas de modification). Valeur par défaut : "false"
	protected static String 	drctDbtTxMndtRltdInfAmdmntInfDtls; // si AmdmntInd est "true"
	protected static String 	drctDbtTxMndtRltdInfAmdmntInfDtlsOrgnlMndtId; // si AmdmntInd est "true"
	protected static String 	drctDbtTxMndtRltdInfAmdmntInfDtlsOrgnlCdtrSchmeId; // si AmdmntInd est "true"
	protected static String 	drctDbtTxMndtRltdInfAmdmntInfDtlsOrgnlCdtrSchmeIdIdPrvtIdOthrId; // si AmdmntInd est "true"
	protected static String 	drctDbtTxMndtRltdInfAmdmntInfDtlsOrgnlCdtrSchmeIdIdPrvtIdOthrIdSchmeNmPrtry= "SEPA"; // si AmdmntInd est "true"
	protected static String	 	orgnlDbtrAcctId;
	protected static String 	orgnlDbtrAcctIBAN; // IBAN de Débteur
	protected static String 	orgnlDbtrAgtFinInstnIdOthrId = "SMNDA"; /*Utilisé pour indiquer un changement
																		d'établissement bancaire du débiteur. Dans ce
																		cas cette donnée est obligatoire et la valeur
																		"SMNDA" doit être indiqué dans l'élément
																		"Identification" ainsi que la valeur 'FRST' dans
																		l'index 2.14 'Sequence Type'.
																		Le BIC de l'ancien établissement bancaire ne
																		doit pas être indiqué*/
	protected static String 	drctDbtTxInfUltmtCdtrNm;
	protected static String 	drctDbtTxInfUltmtCdtrIdOrgId; 
	protected static String 	dbtrAgt;
	protected static String 	dbtrAgtFinInstnIdBIC; // BIC de Debteur
	protected static String 	dbtr;
	protected static String 	dbtrNm;
	protected static String 	dbtrNmIdOrgId;
	protected static String 	dbtrAcct;
	protected static String 	dbtrAcctIdIBAN; // IBAN de Debteur
	protected static String 	ultmtDbtr;
	protected static String 	ultmtDbtrNm;
	protected static String 	ultmtDbtrIdOrgId;
	protected static String 	purp; //nature de prelevement
	protected static String 	purpCd; 
	protected static String 	rgltrRptgDtlsCd; // max 10 chars
	protected static String 	rmtInfUsrtrd;
	protected static String 	rmtInfSrtrd;
	protected static String 	rmtInfSrtrdCdtrRefInf;
	protected static String 	rmtInfSrtrdCdtrRefInfTp;
	protected static String 	rmtInfSrtrdCdtrRefInfTpCdOrPrtry;
	protected static String 	rmtInfSrtrdCdtrRefInfTpCdOrPrtryCd = "SCOR"; // La valeur 'SCOR' est obligatoire
	protected static String 	rmtInfSrtrdCdtrRefInfRef;
	
	//variables pour les tests de code 
	private static final String dirName	 = "C:\\LGRDOSS\\BureauDeGestion\\SEPA_Prelevement\\";
	private static final String fileName = ".sepa.xml";
	private static final String space	 = " ";
	private static final String textTMP	 = "TEST-MESSAGE";
	private static final String prelevValid = "PREL";
	
	
	//private String compteCourant;
	
	@SuppressWarnings("static-access")
//	public static void PrintOutSEPA(List<TaPrelevement> list ,TaInfoEntreprise compteEntreprise){
//		
//
//		PrintWriter xmlExportFile;
//				
//		try {
//			xmlExportFile = new PrintWriter(fileName);
//
//			nbOfTxs=0;
//			ctrlSum = new BigDecimal("0");
//			for (TaPrelevement counter : list){
//				nbOfTxs++;
//				ctrlSum = ctrlSum.add(counter.getNetTtcCalc());
//			}
//			
//			creDtTm = LibDate.dateToStringTimeStamp(new Date(), "/", " ", ":");
//			creDtTm = creDtTm.substring(0,creDtTm.indexOf(":", 16));
//			long var = 0; // Incrementeur pour Mandat
//			Long mandatId = (new Date().getTime());// TimeStamp pour Mandat
//			for (TaPrelevement client : list){
//				var ++;
//
//				msgId = "Teste message"; //<-- il faut generer
//				initgPtyNm = client.getTaInfosDocument().getNomEntreprise();
//				pmtInfId = "Prelevement "+client.getCodeDocument();
//				pmtMtd = "DD"; // DD pour SDD Core
//				btchBookg = false; // "true" implique une comptabilisation globale, "false" une comptabilisation unitaire
//				pmtNbOfTxs = nbOfTxs; //il faut refaire!!! <--
//				pmtCtrlSum = ctrlSum; // <--
//				svcLvlCd = "SEPA"; // valeur "SEPA" est OBLIGATOIRE!
//				lclInstrmCd = "CORE"; // "CORE" pour un prélèvement SEPA (SDD Core), "B2B" pour un prélèvement interentreprise
//				seqTp = "FRST"; //<--
////				reqdColIntDt = LibDate.dateToString(client.getDateDocument()); //client.getDateDocument(); // date d'échéance
//				//<--
//				Set<TaCompteBanque>listeCb= (Set<TaCompteBanque>)(client.getTaTiers()!=null ?client.getTaTiers().getTaCompteBanques():null);//extracted(client);
//
//				for (TaCompteBanque taCompteBanque : listeCb) {
//					if (taCompteBanque.getTaTBanque().getCodeTBanque().equals(prelevValid)){
//						dbtrAcctIdIBAN 			= taCompteBanque.getIban();
//						dbtrAgtFinInstnIdBIC 	= taCompteBanque.getCodeBIC();
//						break;
//					}
//				}
//				
//				if (compteEntreprise!=null && compteEntreprise.getTaTiers()!=null){
//					for (TaCompteBanque taCompteBanque : compteEntreprise.getTaTiers().getTaCompteBanques()) {
//						cdtrAcctIdIBAN 			= taCompteBanque.getIban(); // IBAN de Créancier
//						cdtrAgtFinInstnIdBIC 	= taCompteBanque.getCodeBIC(); // BIC de Créancier
//						break;
//					}
//					
//				} else {
//					cdtrAcctIdIBAN 			= "none"; // IBAN de Créancier
//					cdtrAgtFinInstnIdBIC 	= "none"; // BIC de Créancier
//				}
//				
//				cdtrAcctCcy = "EUR";
//				chrgBr = "SLEV"; //Seule la valeur "SLEV" est autorisée
//				cdtrSchmeIdPrvtId="FR00ZZZ123456"; //numerequ doit etre genere
//				cdtrSchmeIdOthrSchmeNmPrty = "SEPA";
//				//DD
//				instdAmt = client.getNetTtcCalc(); // Amount must be 0.01 or more and 999999999.99 or less 
//				drctDbtTxMndtRltdInfDtOfSgntr=client.getDateDocument(); //date de signature de Mandar SEPA
//				drctDbtTxMndtRltdInfAmdmntInd = false; // "true" (si il y a des modifications), "false" (pas de modification). Valeur par défaut : "false"
//				orgnlDbtrAcctIBAN = dbtrAcctIdIBAN; // IBAN de Débteur <-- Il faut changer
//				orgnlDbtrAgtFinInstnIdOthrId = "SMNDA"; // 2.58
//				drctDbtTxInfUltmtCdtrNm = client.getTaTiers().getNomTiers();
//				dbtrNm = client.getTaTiers().getNomTiers();
//				rmtInfUsrtrd = client.getCodeDocument();
//				rmtInfSrtrdCdtrRefInfTpCdOrPrtryCd = "SCOR"; // La valeur 'SCOR' est obligatoire
//				rmtInfSrtrdCdtrRefInfRef = client.getCodeDocument();
//				
//				xmlExportFile.write("*************************************************************\n");
//				xmlExportFile.write("Message: "+msgId+"\n");
//				xmlExportFile.write("Date of Today: "+creDtTm+"\n");
//				xmlExportFile.write("Nom d'entreprise créancier: "+initgPtyNm+"\n");
//				xmlExportFile.write("Payment Information ID: "+pmtInfId+"\n");
//				xmlExportFile.write("Methode de permutation(DD): "+pmtMtd+"\n");
//				xmlExportFile.write("Batch Booking: "+btchBookg.toString(btchBookg)+"\n");
//				xmlExportFile.write("Nb total des lots: "+pmtNbOfTxs+"\n");
//				xmlExportFile.write("Control Sum Total: "+pmtCtrlSum+"\n");
//				xmlExportFile.write("SEPA: "+svcLvlCd+"\n");
//				xmlExportFile.write("Core: "+lclInstrmCd+"\n");
//				xmlExportFile.write("Sequance: "+seqTp+"\n");
//				xmlExportFile.write("Date d'echeance: "+reqdColIntDt+"\n");
//				xmlExportFile.write("Creditor IBAN: "+cdtrAcctIdIBAN+"\n");
//				xmlExportFile.write("Creditor BIC: "+cdtrAgtFinInstnIdBIC+"\n");
//				xmlExportFile.write("Corency: "+cdtrAcctCcy+"\n");
//				xmlExportFile.write("SEPA: "+cdtrSchmeIdOthrSchmeNmPrty+"\n");
//				xmlExportFile.write("Creditor Identification: "+cdtrSchmeIdPrvtId+"\n");
//				xmlExportFile.write("Debtor Name: "+dbtrNm+"\n");
////				xmlExportFile.write("Debtor Mandat: "+drctDbtTxMndtRltdInfMndtId+"\n");
//				xmlExportFile.write("Mandat: MA-"+(mandatId+var)+"\n");
////				xmlExportFile.write("Date de signature de Mandat: "+drctDbtTxMndtRltdInfDtOfSgntr+"\n");
//				xmlExportFile.write("SLEV: "+chrgBr+"\n");
//				xmlExportFile.write("Instruction Identification: "+instrId+"\n");
//				xmlExportFile.write("End to End Identification: "+endToEndId+"\n");
//				xmlExportFile.write("Sum de  la facture: "+instdAmt+"\n");
//				xmlExportFile.write("Debtors IBAN: "+dbtrAcctIdIBAN+"\n");
//				xmlExportFile.write("Debtors BIC: "+dbtrAgtFinInstnIdBIC+"\n");
////				xmlExportFile.write(""++"\n");
//				
//			}
//		
//			xmlExportFile.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}
	


	/**Generation de Payements OOFF (One off) "ponctuel" */
	public static boolean PrintOutOOFF(List<TaPrelevement> list ,TaInfoEntreprise compteEntreprise, String nomCompte) throws Exception{
		if(compteEntreprise==null||compteEntreprise.getTaTiers().getTaCompl()==null || compteEntreprise.getTaTiers().getTaCompl().getIcs()==null ){
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur sur le paramètrage du dossier",  
					"Il manque des informations pour traiter les prélèvements.\r\n" +
					"Vous devez vous assurer d'avoir rempli le numéro ICS dans identité entreprise, ainsi qu'un compte bancaire.\r\n" +
					"les clients prélevés doivent tous avoir un compte bancaire du type 'PREL'.");
			throw new Exception();
		}
		
		TaMandat mandat=null;
		TaMandatDAO daoMandat = new TaMandatDAO();
		TaEndToEnd endToEnd=null;
		TaEndToEndDAO daoEndToEnd = new TaEndToEndDAO(daoMandat.getEntityManager());
		
		
		
        EntityTransaction transaction = daoMandat.getEntityManager().getTransaction();
		try {
        daoMandat.begin(transaction);
        
		/** Variables de GroupHeader */
		//Nb avec Sum Total de tous les trancfaires  
		nbOfTxs=0; //1.6
		ctrlSum = new BigDecimal("0"); //1.7
		for (TaPrelevement counter : list){
			nbOfTxs++;
			ctrlSum = ctrlSum.add(counter.getNetTtcCalc());
		}
		creDtTm = LibDate.dateToStringTimeStamp(new Date(), "-", "T", ":");// ISO Date Time: YYYY-MM-DDThh:mm:ss //1.2
//		creDtTm = creDtTm.substring(0,creDtTm.indexOf(":", 17));// format "YYYY-MM-DDThh:mm:ss" 
		long var = 0; // Incrementeur pour Mandat
		Long mandatId = (new Date().getTime());// TimeStamp pour Mandat //2.48 partie CodeId
		msgId = "MSDID - "+mandatId.toString(); // Identifiant unique du message: XXXXXXXX-XXX-SDD-AAQQQ-XXX //1.1
		initgPtyNm = compteEntreprise.getNomInfoEntreprise(); //Dans le PI //1.8
		//Create a new Direct Debit message:
        DirectDebit dd = new DirectDebit(msgId, initgPtyNm, "-"); //<-- //il manque que le ID d'Entreprise
		for (TaPrelevement client : list){
			var ++;
			
			/**PaymentInformation*/
			pmtInfId = "Prelevement "+client.getCodeDocument(); //2.1
			pmtMtd = "DD"; // DD pour SDD Core //2.2
			btchBookg = false; // "true" implique une comptabilisation globale, "false" une comptabilisation unitaire //2.3
			pmtNbOfTxs = 1; //=1 pour OOFF //2.4
			pmtCtrlSum = client.getNetTtcCalc(); //2.5 
			svcLvlCd = "SEPA"; // valeur "SEPA" est OBLIGATOIRE! //2.9
			lclInstrmCd = "CORE"; // "CORE" pour un prélèvement SEPA (SDD Core), "B2B" pour un prélèvement interentreprise //2.12
			seqTp = "OOFF"; //2.14
//			reqdColIntDt = LibDate.incrementDate(new Date(), 6, 0, 0); //client.getDateDocument(); // date d'échéance //2.18
			reqdColIntDt = client.getDateEchDocument(); //client.getDateDocument(); // date d'échéance //2.18			
			reqdColIntDt = dateVefirication(reqdColIntDt);
//			reqdColIntDt = new Date(); //.setDate(6) <-- il faut ajouter + 7 jours
			if (compteEntreprise!=null && compteEntreprise.getTaTiers()!=null){
				for (TaCompteBanque taCompteBanque : compteEntreprise.getTaTiers().getTaCompteBanques()) {
					if(nomCompte!=null && nomCompte.equals(taCompteBanque.getNomCompte())) {
						cdtrAcctIdIBAN 			= taCompteBanque.getIban(); // IBAN de Créancier //2.20
						cdtrAgtFinInstnIdBIC 	= taCompteBanque.getCodeBIC(); // BIC de Créancier //2.21
	//					initgPtyNm 				= compteEntreprise.getNomInfoEntreprise(); //1.8
						break;
					}
				}
				
			} else {
				cdtrAcctIdIBAN 			= "none"; // IBAN de Créancier
				cdtrAgtFinInstnIdBIC 	= "none"; // BIC de Créancier
//				initgPtyNm 				= "none";
			}
			cdtrAcctCcy = "EUR"; //2.20
			chrgBr = "SLEV"; //Seule la valeur "SLEV" est autorisée //2.24
			TaInfoEntrepriseDAO entreprise = new TaInfoEntrepriseDAO();

			cdtrSchmeIdPrvtId=compteEntreprise.getTaTiers().getTaCompl().getIcs(); //numerequ doit etre genere <-- //2.27 "FR00ZZZ123456"
			cdtrSchmeIdOthrSchmeNmPrty = "SEPA"; //2.27
			//Create a new Payment Group with FRST transactions:
	        DirectDebitPaymentGroup pg = new DirectDebitPaymentGroup(pmtInfId, reqdColIntDt, initgPtyNm, cdtrSchmeIdPrvtId, 
	        																				cdtrAcctIdIBAN, cdtrAgtFinInstnIdBIC, "OOFF");
			
			/**DirectDebit*/
			instrId = "Istruction Id"; //<-- //2.30
//			endToEndId = "E2E-"+(mandatId+var); //<-- //2.31  
			endToEndId = "E2E-"+client.getIdDocument(); //<-- //2.31  //id du prelevement
			instdAmt = client.getNetTtcCalc(); // Amount must be 0.01 or more and 999999999.99 or less //<-- //2.44
/*numero mandat*/drctDbtTxMndtRltdInfMndtId = "MA-"+(mandatId+var); //2.48
			drctDbtTxMndtRltdInfDtOfSgntr=client.getDateDocument(); //date de signature de Mandar SEPA //2.49
			drctDbtTxMndtRltdInfAmdmntInd = true; // "true" (si il y a des modifications), "false" (pas de modification). Valeur: "true" pour OOFF et FRST //2.50
			orgnlDbtrAgtFinInstnIdOthrId = "SMNDA"; //2.58
			Set<TaCompteBanque>listeCb= (Set<TaCompteBanque>)(client.getTaTiers()!=null ?client.getTaTiers().getTaCompteBanques():null);//extracted(client);
			for (TaCompteBanque taCompteBanque : listeCb) {
				if (taCompteBanque.getTaTBanque().getCodeTBanque().equals(prelevValid)){
					dbtrAcctIdIBAN 			= taCompteBanque.getIban();// IBAN de Debteur //2.73
					dbtrAgtFinInstnIdBIC 	= taCompteBanque.getCodeBIC();// BIC de Debteur //2.70
					break;
				}
			}
			dbtrNm = client.getTaTiers().getNomTiers(); //2.72
			rmtInfUsrtrd = client.getCodeDocument(); //<-- //2.89
			//Add a transaction without adc amendment to the payment group:
	        pg.addTransactionWithoutAmendment(endToEndId, instdAmt, drctDbtTxMndtRltdInfMndtId, client.getDateDocument(),
	        														dbtrNm, dbtrAcctIdIBAN, dbtrAgtFinInstnIdBIC);
	        //Attach the payment group to the direct debit message:
	        dd.addPaymentGroup(pg);
	        
	        

	        
	        //marquer n° mandate
	        mandat = new TaMandat();
	        mandat.setCodeMandat(drctDbtTxMndtRltdInfMndtId);
	        mandat.setTaTiers(client.getTaTiers());
	        mandat=daoMandat.enregistrerMerge(mandat);
	        //marquer endToEndId
	        if(client.getExportBanque()==1 && daoEndToEnd.exist(endToEndId) ){	        	
	        	endToEnd=daoEndToEnd.findByCode(endToEndId);
	        }
	        else   endToEnd = new TaEndToEnd();
	        endToEnd.setCodeEndToEnd(endToEndId);
	        endToEnd.setTaMandat(mandat);
	        endToEnd=daoEndToEnd.enregistrerMerge(endToEnd);
	        

			
		}
		//Write the DirectDebit in a XML ISO-20022 compliant file:
        try {
        	 daoMandat.commit(transaction);
			dd.write(dirName+"PREL-"+mandatId.toString()+fileName);
			MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Fichier d'exportation en banque",  
					" Le fichier d'exportation a été généré avec succès."+"\r\n"+"" +
							"Il se trouve dans : "+dirName+"PREL-"+mandatId.toString()+fileName);
			 return true;
		} catch (JAXBException e) {
			logger.error("",e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur sur écriture dans le fichier",  
					" Le fichier d'exportation n'a pas pu être généré.");
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			return false;
		} catch (IOException e) {
			logger.error("",e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur sur le chemin du fichier",  
					" Le fichier d'exportation n'a pas pu être généré."+"\r\n"+" Le chemin spécifié est introuvable.");
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			return false;
		}
		} catch (Exception e) {
			logger.error("",e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Erreur",  
					"Erreur sur la gestion de la présentation en banque des prélèvements.");
			if(transaction!=null && transaction.isActive()) {
				transaction.rollback();
			}
			return false;
		}
       
	}

	/**Source: http://www.aveol.fr/?p=602 - Debit */
	public static GregorianCalendar calculLundiPacques(int annee) { //<--
		int a = annee / 100;
		int b = annee % 100;
		int c = (3 * (a + 25)) / 4;
		int d = (3 * (a + 25)) % 4;
		int e = (8 * (a + 11)) / 25;
		int f = (5 * a + b) % 19;
		int g = (19 * f + c - e) % 30;
		int h = (f + 11 * g) / 319;
		int j = (60 * (5 - d) + b) / 4;
		int k = (60 * (5 - d) + b) % 4;
		int m = (2 * j - k - g + h) % 7;
		int n = (g - h + m + 114) / 31;
		int p = (g - h + m + 114) % 31;
		int jour = p + 1;
		int mois = n;

		GregorianCalendar date = new GregorianCalendar(annee, mois - 1, jour);
		date.add(GregorianCalendar.DAY_OF_MONTH, 1);
		return date;
	}
	/** Les jours fériés français */
	public static List<Date> getJourFeries(int annee) {
		List<Date> datesFeries = new ArrayList<Date>();

		// Jour de l'an
		GregorianCalendar jourAn = new GregorianCalendar(annee, 0, 1);
		datesFeries.add(jourAn.getTime());

		// Lundi de pacques
		GregorianCalendar pacques = calculLundiPacques(annee);
		datesFeries.add(pacques.getTime());

		// Fete du travail
		GregorianCalendar premierMai = new GregorianCalendar(annee, 4, 1);
		datesFeries.add(premierMai.getTime());

		// 8 mai
		GregorianCalendar huitMai = new GregorianCalendar(annee, 4, 8);
		datesFeries.add(huitMai.getTime());

		// Ascension (= pâques + 38 jours)
		GregorianCalendar ascension = new GregorianCalendar(annee,
				pacques.get(GregorianCalendar.MONTH),
				pacques.get(GregorianCalendar.DAY_OF_MONTH));
		ascension.add(GregorianCalendar.DAY_OF_MONTH, 38);
		datesFeries.add(ascension.getTime());

		// Pentecôte (= pâques + 49 jours)
		GregorianCalendar pentecote = new GregorianCalendar(annee,
				pacques.get(GregorianCalendar.MONTH),
				pacques.get(GregorianCalendar.DAY_OF_MONTH));
		pentecote.add(GregorianCalendar.DAY_OF_MONTH, 49);
		datesFeries.add(pentecote.getTime());

		// Fête Nationale
		GregorianCalendar quatorzeJuillet = new GregorianCalendar(annee, 6, 14);
		datesFeries.add(quatorzeJuillet.getTime());

		// Assomption
		GregorianCalendar assomption = new GregorianCalendar(annee, 7, 15);
		datesFeries.add(assomption.getTime());

		// La Toussaint
		GregorianCalendar toussaint = new GregorianCalendar(annee, 10, 1);
		datesFeries.add(toussaint.getTime());

		// L'Armistice
		GregorianCalendar armistice = new GregorianCalendar(annee, 10, 11);
		datesFeries.add(armistice.getTime());

		// Noël
		GregorianCalendar noel = new GregorianCalendar(annee, 11, 25);
		datesFeries.add(noel.getTime());

		return datesFeries;
	}
	/**Source: http://www.aveol.fr/?p=602 -  Fin  */
	
	/**Verification de la date d'acusition d'un prelevement*/ //<--
	@SuppressWarnings("deprecation")
	public static Date dateVefirication(Date date){ //2.18

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		List<Date> fetesDAnnee = new ArrayList<Date>();
		fetesDAnnee = getJourFeries(date.getYear()+1900);
		boolean trigger = false;
		do {
			for (Date fetes : fetesDAnnee){
				if(fetes.equals(date)){ //calendar.getTime())
					date = LibDate.incrementDate(date, 1, 0, 0);
					dateVefirication(date);
				}

				if ((date.getDay()+1) == (Calendar.SATURDAY)){
					date = LibDate.incrementDate(date, 2, 0, 0);
					dateVefirication(date);
				}
				else if ((date.getDay()+1) == (Calendar.SUNDAY)){//calendar.get(Calendar.DAY_OF_WEEK)
					date = LibDate.incrementDate(date, 1, 0, 0);
					dateVefirication(date);
				}
			}
			trigger = true;
		}while (trigger == false);
		
		return date;
	}
	
}
