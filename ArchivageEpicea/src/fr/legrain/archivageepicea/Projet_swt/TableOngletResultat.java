package fr.legrain.archivageepicea.Projet_swt;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.core.commands.common.HandleObjectManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.lib.gui.grille.LgrTableViewer;

public class TableOngletResultat extends CTabItem {
	
	private static Double debitEtotal=new Double(0);
	private static Double creditEtotal=new Double(0);
//	private Table table = null;
//	private Table tableDownPiece = null;
	private LgrTableViewer tableViewer = null;
	private LgrTableViewer tableViewer1 = null;
	private Composite compositeOnglet = null;
	private String nomOnglet = null;
	private String cheminFichierNomColonne = null;
	private String devise = "€";


	private Map<Table, List<Object[]>> MapTableAll = null;
	
	private String tablePieces = "Pieces";
	private int ID_PIECE = 0;
	private Connection conn = null;
	private Statement stmt = null;
	
	private String pathFile = null;

	private String querySql = "SELECT * FROM ECRITURES WHERE ID_PIECE=";
	private static Double debit= 0.00;
	private static Double credit= 0.00;
	
	
	/*
	 * TotalCredit TotalDebit MontantPiece sont dans onglet de Pieces 
	 */
	private Double TotalCredit = new Double(0);
	private Double TotalDebit = new Double(0);
	private Double MontantPiece= new Double(0);
	
	/*
	 * 
	 */
	private Double TotalCreditCompte = new Double(0);
	private Double TotalDebitCompte = new Double(0);
	private Double SoldeCompte = new Double(0);
	private Double TotalCreditEcriture = new Double(0);
	private Double TotalDebitEcriture = new Double(0);
	/*
	 * 
	 */
	private String TauxTva = "";
	//private String TauxTva = null;
	protected String CodeTva = "";//"null"
	private String MontantTva = "";
	private String TypeTva = "";
	private String DateLivraison = "";
	
	/*
	 * 
	 */
	private CompositeOneTable OngelEcriture;
	private CompositeTwoTable OngelPiece;
	private CompositeOneTableCompt OngelCompte;
	//static public makeCompositeLeft test = new makeCompositeLeft();
	
	public TableOngletResultat(CTabFolder parent, int style, String nomOnglet, String cheminFichierNomColonne,int weight,int height,int nombreTable ) {
		super(parent, style);
		/*
		 * nombreTable is 1 
		 */
		if(nombreTable==1){
			OngelEcriture = new CompositeOneTable(parent,SWT.NONE);
			this.conn = conn;

			this.nomOnglet = nomOnglet;
			this.cheminFichierNomColonne = cheminFichierNomColonne;
			setText(nomOnglet); 

			tableViewer  = new LgrTableViewer(OngelEcriture.getTable()); 
			

			
			/**
			 * creer les colonne du table 
			 * nomOnglet pour cherchcer quelle de morceau dans le fichier de  parametre/ListeChampRecherche.properties
			 * EX ==> si nomOnglet = PIECESE, on cherhce les ligne qui commencent a PIECESE
			 * 
			 */

			tableViewer.createTableCol(OngelEcriture.getTable(),nomOnglet,cheminFichierNomColonne);
			OngelEcriture.getTable().setHeaderVisible(true);
			OngelEcriture.getTable().setLinesVisible(true);
			tableViewer.tri(Object.class, nomOnglet,cheminFichierNomColonne);

			//OngelEcriture.
			
			setControl(OngelEcriture);//definir le parent de compositeOnglet
		}
		/**
		 * for ongelt of piece
		 */
		if(nombreTable==2){
			OngelPiece = new CompositeTwoTable(parent,SWT.NONE);
			this.conn = conn;
			this.nomOnglet = nomOnglet;
			this.cheminFichierNomColonne = cheminFichierNomColonne;
			setText(nomOnglet); 

			tableViewer1 = new LgrTableViewer(OngelPiece.getTableTopPiece());
			tableViewer  = new LgrTableViewer(OngelPiece.getTableDownPiece()); 
			
			/**
			 * creer les colonne du table 
			 * nomOnglet pour cherchcer quelle de morceau dans le fichier de  parametre/ListeChampRecherche.properties
			 * EX ==> si nomOnglet = PIECESE, on cherhce les ligne qui commencent a PIECESE
			 * 
			 */

			tableViewer.createTableCol(OngelPiece.getTableTopPiece(),nomOnglet,cheminFichierNomColonne);

			OngelPiece.getTableTopPiece().setHeaderVisible(true);
			OngelPiece.getTableTopPiece().setLinesVisible(true);
			
			OngelPiece.getTableDownPiece().setHeaderVisible(true);
			OngelPiece.getTableDownPiece().setLinesVisible(true);

			setControl(OngelPiece);//definir le parent de compositeOnglet
		}
		/**
		 * for onglet of compte
		 */
		if(nombreTable==3){
			OngelCompte = new CompositeOneTableCompt(parent,SWT.NONE);
			this.conn = conn;

			this.nomOnglet = nomOnglet;
			this.cheminFichierNomColonne = cheminFichierNomColonne;
			setText(nomOnglet); 

			tableViewer  = new LgrTableViewer(OngelCompte.getTable()); 
	
			/**
			 * creer les colonne du table 
			 * nomOnglet pour cherchcer quelle de morceau dans le fichier de  parametre/ListeChampRecherche.properties
			 * EX ==> si nomOnglet = PIECESE, on cherhce les ligne qui commencent a PIECESE
			 * 
			 */
			tableViewer.createTableCol(OngelCompte.getTable(),nomOnglet,cheminFichierNomColonne);

			OngelCompte.getTable().setHeaderVisible(true);
			OngelCompte.getTable().setLinesVisible(true);

			setControl(OngelCompte);//definir le parent de compositeOnglet
		}

	}
	/**
	 * 
	 * @param compositeOnglet est comme compositeOnglet
	 */
	public void BasEcritures(CompositeOneTable compositeOnglet,String debPeriode,
			String finPeriode,String debCompte,String finCompte,String debMontant,
			String finMontant,String debReference,String finReference,String libelle,
			String nameDb,Double debit,Double credit ){
		if(debPeriode==null)
			debPeriode="";
		if(finPeriode==null)
			finPeriode="";
	
		compositeOnglet.getLabelNameDB().setText(nameDb);
		DecimalFormat df = new DecimalFormat("########.00"); 
		df.setMaximumFractionDigits ( 2 );
		df.setDecimalSeparatorAlwaysShown ( true ) ; 
		if(debit !=0.00)
			compositeOnglet.getLabelTotauxDebit().setText(df.format ( debit)+" "+ devise);
		else
			compositeOnglet.getLabelTotauxDebit().setText("0.00 "+ devise);
		if(credit !=0.00)
			compositeOnglet.getLabelTotauxCredit().setText(df.format ( credit)+" "+ devise);
		else
			compositeOnglet.getLabelTotauxCredit().setText("0.00 "+ devise);



		credit = fr.legrain.lib.data.LibCalcul.arrondi(credit);
		debit = fr.legrain.lib.data.LibCalcul.arrondi(debit);
		Double solde = credit - debit;
	
		if(solde==0){
			compositeOnglet.getLabelSoldeDebit().setText("0.00 "+devise);
			compositeOnglet.getLabelSoldeCredit().setText("0.00 "+devise);
		}
		if(solde>0){
			compositeOnglet.getLabelSoldeDebit().setText("0.00 "+devise);
			compositeOnglet.getLabelSoldeCredit().setText(String.valueOf(df.format ( solde))+" "+devise);
		}
		if(solde<0){
			compositeOnglet.getLabelSoldeDebit().setText(String.valueOf(df.format (- solde))+" "+devise);
			compositeOnglet.getLabelSoldeCredit().setText("0.00 "+devise);
		}
		
		compositeOnglet.getLabelNombreDB().setText(String.valueOf(compositeOnglet.getTable().getItemCount()));
	
		// ligne 1
		if(!debPeriode.equals("") || !finPeriode.equals("")){
			compositeOnglet.getLabelDate1().setText(debPeriode);
			compositeOnglet.getLabelDate2().setText(finPeriode);
		}
		//ligne 2
		if(!debCompte.equals("") || !finCompte.equals("")){
			compositeOnglet.getLabelCompte1().setText(debCompte);
			compositeOnglet.getLabelCompte2().setText(finCompte);
		}
		//ligne 3
		if(!debMontant.equals("") || !finMontant.equals("")){
			compositeOnglet.getLabelMontant1().setText(debMontant);
			compositeOnglet.getLabelMontant2().setText(finMontant);
		}
		//ligne 4
		if(!debReference.equals("") || !finReference.equals("")){
			compositeOnglet.getLabelReference1().setText(debReference);
			compositeOnglet.getLabelReference2().setText(finReference);
		}
		//ligne 5
		if(!libelle.equals("")){
			compositeOnglet.getLabelContenuInfoLibelle().setText(libelle);
		}
		setControl(compositeOnglet);
		
	}
	public void DownTablePieces(Group groupComposite){ 
		
		setNomOnglet("PIECESC");
		tableViewer1  = new LgrTableViewer(groupComposite);
		tableViewer1.createTableCol(OngelPiece.getTableDownPiece(),getNomOnglet(),getCheminFichierNomColonne());
		OngelPiece.getTableDownPiece().setHeaderVisible(true);
		OngelPiece.getTableDownPiece().setLinesVisible(true);		
	}

	public void StrucDownOngletCompte(CompositeOneTableCompt compositeOnglet,String dateDebut,String dateFin,String numCompte){

		compositeOnglet.getLabelNumeroCompte().setText(numCompte);
		compositeOnglet.getLabelTotauxMonvementDate().setText("Du "+dateDebut+" Au "+dateFin);
		compositeOnglet.getLabelSoldeFinDate().setText(dateFin);
		compositeOnglet.getLabelTotalEcritureCompte().setText(String.valueOf(compositeOnglet.getTable().getItemCount()));
		
		String totalDebitCompteString=formatChiffre(TotalDebitCompte);
		compositeOnglet.getLabelDebitCompte().setText(totalDebitCompteString+" "+devise);
		String totalCreditCompteString=formatChiffre(TotalCreditCompte);
		compositeOnglet.getLabelCreditCompte().setText(totalCreditCompteString+" "+devise);
		if(TotalCreditCompte>TotalDebitCompte){
			String soldeCompte=formatChiffre(SoldeCompte);
			compositeOnglet.getLabelDebitSoldeCompte().setText("0.00"+devise);
			compositeOnglet.getLabelCreditSoldeCompte().setText(soldeCompte+" "+devise);
		}
		if(TotalCreditCompte<TotalDebitCompte){
			String soldeCompte=formatChiffre(SoldeCompte);
			compositeOnglet.getLabelDebitSoldeCompte().setText(soldeCompte+" "+devise);
			compositeOnglet.getLabelCreditSoldeCompte().setText("0.00"+" "+devise);
		}
		if(TotalCreditCompte.equals(TotalDebitCompte)){
			String soldeCompte=formatChiffre(SoldeCompte);
			compositeOnglet.getLabelDebitSoldeCompte().setText(soldeCompte+" "+devise);
			compositeOnglet.getLabelCreditSoldeCompte().setText(soldeCompte+" "+devise);
		}
		
		setControl(compositeOnglet);
	
	}
	public void StrucDownTablePieces(CompositeTwoTable compositeOnglet){
		DownTablePieces(compositeOnglet.getGroupDownTablePieces());
		setControl(compositeOnglet);
		
	}
	/*
	 * en effect ajoutContenuCompositeBottom est compositeInfoPieceLeft dans le class CompositeTwoTable 
	 */
	public void ajoutContenuCompositeBottom(CompositeTwoTable compositeOnglet){
		
		if(TauxTva.contains(".")){
			TauxTva = TauxTva.substring(0,(TauxTva.indexOf(".")+2));
			
		}
		compositeOnglet.getLabelValueEcritureTauxTva().setText(TauxTva+" %");
//		else{
//			compositeOnglet.getLabelValueEcritureTauxTva().setText(TauxTva);
//		}
		
		compositeOnglet.getLabelValueEcritureCodeTva().setText(CodeTva);

		compositeOnglet.getLabelValueEcritureMontantTva().setText(MontantTva+" "+devise);
		compositeOnglet.getLabelValueEcritureTypeTva().setText(TypeTva);

		compositeOnglet.getLabelValueEcriutreDateLivraison().setText(DateLivraison);
		compositeOnglet.getLabelValueNombreEcriture().setText(String.valueOf(OngelPiece.getTableDownPiece().getItemCount()));

	}
	public String formatChiffre(Double chiffreDouble){
		String chiffre = chiffreDouble.toString();
		String formatChiffre = "";
		if(chiffre!=null){
			if(chiffre.contains(".")){
				int nombreChiffre = chiffre.substring(0, (chiffre.indexOf("."))).length();
				for(int i=0;i<nombreChiffre;i++){
					formatChiffre= formatChiffre.concat("#");
				}
				formatChiffre= formatChiffre+".##";//####.##
			}
			else{
				int nombreChiffre = chiffre.length();
				for(int i=0;i<nombreChiffre;i++){
					formatChiffre= formatChiffre.concat("#");
				}
				formatChiffre = formatChiffre+".00";
			}
			DecimalFormat df=new DecimalFormat(formatChiffre);
			chiffre = df.format(chiffreDouble);
		}
		else{
			chiffre = "0.00";
		}
		return chiffre;
	}
	public void ajoutContenuCompositeDownGroup(CompositeTwoTable compositeOnglet){

		String totalDebitString=formatChiffre(TotalDebit);
		compositeOnglet.getLabelPieceTotauxDebit().setText(totalDebitString+devise);

		String totalCreditString=formatChiffre(TotalCredit);
		compositeOnglet.getLabelPieceTotauxCredit().setText(totalCreditString+devise);

		if(TotalCredit>TotalDebit){
			compositeOnglet.getLabelPieceMontantDebit().setText("0.00"+devise);
			String montantPiece=formatChiffre(MontantPiece);
			compositeOnglet.getLabelPieceMontantCredit().setText(montantPiece+devise);

		}
		if(TotalCredit<TotalDebit){
			String montantPiece=formatChiffre(MontantPiece);
			compositeOnglet.getLabelPieceMontantDebit().setText(montantPiece+devise);
			compositeOnglet.getLabelPieceMontantCredit().setText("0.00"+devise);
		}
		if(TotalCredit.equals(TotalDebit)){
			String montantPiece=formatChiffre(MontantPiece);
			compositeOnglet.getLabelPieceMontantDebit().setText(montantPiece+".00"+devise);
			compositeOnglet.getLabelPieceMontantCredit().setText(montantPiece+".00"+devise);
		}
	}
	/*
	 * pour obtenir les titre de colonne en fonction de la fichier de ListeChampRecherche.properties
	 * 
	 * List<String> res ==> contient les nom dee colonne dont on a besoin
	 */
	
//	public Composite getCompositeDownGroup() {
//		return compositeDownGroup;
//	}
	public List<String> listeTitreChampBd(String section,String fileName) throws Exception{
		PropertiesConfiguration listeChamp = new PropertiesConfiguration();
		FileInputStream file = new FileInputStream(fileName);
		listeChamp.load(file);
		file.close();
		
		List<String> res =new ArrayList<String>();
		/*
		 * c'est un plugin qui appartien a JAVA
		 * la format de fechier doit comme ListeChampRecherche.properties
		 * xxxxxxx.xxxxxxxx=xxxxxxx;xxxxx
		 */
		org.apache.commons.configuration.SubsetConfiguration propertie = null;
		propertie = new org.apache.commons.configuration.SubsetConfiguration(listeChamp,section,".");
		if (!propertie.isEmpty()){	
			//String[] retour = new String[propertie.];
			for (Iterator iter = propertie.getKeys(); iter.hasNext();) {
				res.add(iter.next().toString());
			}
		}
		
		return res;
	}

	
	/*
	 * List<Object[]> data est les ligne de DB
	 * 
	 * List<Integer> entier est la position de colonne
	 * 
	 */
	public List<Object[]> analyseDonnee(ResultSet rs,List<Object[]> data,Object[] dataLigne){
		try {
			debitEtotal=0.00;
			creditEtotal=0.00;
			while(rs.next()){

				if(rs.getMetaData().getColumnName(3).equals("ID_PIECE")){
					debitEtotal = debitEtotal + rs.getDouble(12);
					creditEtotal = creditEtotal + rs.getDouble(13);
				}	

				//Object[] dataLigne = null;

				dataLigne = new Object[rs.getMetaData().getColumnCount()];

//				tabItem.getTableViewer().
				//TableItem ligne1 = new TableItem(tabItem.getTable(),SWT.NONE);
				/*
				 * 6 => E.compte
				 * 7 => E.Tiers
				 * 33 => P.Reference
				 * 8 => E.Libelle
				 * 5 => E.Date
				 * 14 => E.Debit
				 * 15 => E.Credit
				 * 
				 * Attention convertir : jj.mm.aaaa => jj/mm/aaaa
				 * convertir libelle : @apostrophe@ ==> ' 
				 */

//				ligne1.setText(new String[] {rs.getString(6),
//				rs.getString(7),
//				rs.getString(33),
//				rs.getString(8),
//				String.valueOf(dateFormat.format(rs.getDate(5)).replace("-", "/")),
//				String.valueOf(rs.getFloat(14)),
//				String.valueOf(rs.getFloat(15))});
				
				
//				for (int compt=1;compt<rs.getMetaData().getColumnCount();compt++)
//				System.out.println("Colonne "+compt+" : "+rs.getMetaData().getColumnName(compt));
//			
				try{
					
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					
						switch(rs.getMetaData().getColumnType(i+1)) {
						case Types.INTEGER :
							dataLigne[i] = rs.getInt(i+1);
							//System.out.println(rs.getInt(i+1));
							break;
						case Types.NUMERIC:
							dataLigne[i] = rs.getDouble(i+1);
							//System.out.println(rs.getDouble(i+1));
							break;
						case Types.FLOAT:
							dataLigne[i] = rs.getDouble(i+1);
							//System.out.println(rs.getDouble(i+1));
							break;
						case Types.VARCHAR :
							dataLigne[i] = rs.getString(i+1);
							//System.out.println(i+" "+rs.getString(i+1));
							break;

						case Types.DATE :
							dataLigne[i] = rs.getDate(i+1);
							//System.out.println(rs.getDate(i+1));
							break;
						case Types.TIMESTAMP :
							dataLigne[i] = rs.getTimestamp(i+1);
							//System.out.println(rs.getTimestamp(i+1));
							break;
						case Types.CHAR :
							dataLigne[i] = rs.getString(i+1);
							//System.out.println(rs.getString(i+1));
							break;
						case Types.SMALLINT :
							dataLigne[i] = rs.getShort(i+1);
							//System.out.println(rs.getShort(i+1));
							break;
						}

					}

					data.add(dataLigne);
				}
				catch(Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	/*
	 * pour onglet de Pieces
	 * pour calculer Debit et Credit et Montant de la piece
	 * 
	 * 14 est Debit
	 * 13 est Credit
	 */
	
	
	
	public void calculDebitCredit(List<Object[]> data , List<Integer> entier){
	
		for (Object[] ligne : data) {
			TotalCredit += (Double)ligne[15];
			TotalDebit += (Double)ligne[14];
		}
		if(TotalCredit>TotalDebit){
			MontantPiece = TotalCredit-TotalDebit;
		}
		if(TotalCredit<TotalDebit){
			MontantPiece = TotalDebit-TotalCredit;
		}
		if(TotalCredit==TotalDebit){
			MontantPiece=0.00;
		}	
	}
	/*
	 * pour onglet de Compte
	 * pour calculer Debit et Credit et Montant de la piece
	 * 
	 * 14 est Debit
	 * 13 est Credit
	 */
	public void calculDebitCreditCompte(List<Object[]> data , List<Integer> entier){
		
		for (Object[] ligne : data) {
			TotalCreditCompte += (Double)ligne[15];
			TotalDebitCompte += (Double)ligne[14];
		}
		if(TotalCreditCompte>TotalDebitCompte){
			SoldeCompte = TotalCreditCompte-TotalDebitCompte;
		}
		if(TotalCreditCompte<TotalDebitCompte){
			SoldeCompte = TotalDebitCompte-TotalCreditCompte;
		}
		if(TotalCreditCompte==TotalDebitCompte){
			SoldeCompte=0.00;
		}	
	}
	/*
	 * pour afficher la table
	 */
	public void setData(List<Object[]> data , List<Integer> entier ,Table nameTable) {
		
		try{
			for (Object[] ligne : data) {
				/* 
				 * ajouter un ligne
				 */
				//TableItem l = new TableItem(table,SWT.NONE);
				TableItem l = new TableItem(nameTable,SWT.NONE);
				int index = nameTable.indexOf(l);
				//System.out.println("-+-+-+-+-"+index+"-+-+-+-+-+-+");
				String[] texteLigne = new String[entier.size()];
				int compteur=0;
				/*
				 * pour comparer chaque data
				 * 
				 * ligne[i - 1] est un object de colonne
				 */
				for (Integer i : entier) {
					if (ligne[i - 1] instanceof Integer) {
						texteLigne[compteur] = LibConversion.integerToString((Integer) ligne[i - 1]);
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					} else if (ligne[i - 1] instanceof Float) {
						texteLigne[compteur] = String.valueOf((Float) ligne[i - 1]);
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					} else if (ligne[i - 1] instanceof Double) {
						texteLigne[compteur] = String.valueOf((Double) ligne[i - 1]);
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					} else if (ligne[i - 1] instanceof String) {
						/*
						 * convertir @apostrophe@ vers ' 
						 */
						texteLigne[compteur] = (String) ligne[i - 1].toString().replace("@apostrophe@", "'");
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					} else if (ligne[i - 1] instanceof Date) {
						texteLigne[compteur] = LibDate.dateToString((Date) ligne[i - 1]);
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					} else if (ligne[i - 1] instanceof Short) {
						texteLigne[compteur] = LibConversion.integerToString(LibConversion.shortToInteger((Short) ligne[i]));
						//System.out.println(texteLigne[compteur]+"+++++"+compteur+"---"+i.toString());
					}
					compteur++;
				}

				l.setText(texteLigne);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


		
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public int getID_PIECE() {
		return ID_PIECE;
	}

	public void setID_PIECE(int id_piece) {
		ID_PIECE = id_piece;
	}

	public String getNomOnglet() {
		return nomOnglet;
	}

	public void setNomOnglet(String nomOnglet) {
		this.nomOnglet = nomOnglet;
	}

	public String getCheminFichierNomColonne() {
		return cheminFichierNomColonne;
	}
	public void setCheminFichierNomColonne(String cheminFichierNomColonne) {
		this.cheminFichierNomColonne = cheminFichierNomColonne;
	}

	public void setDataTableAll(List<Object[]> dataTableAll, List<Integer> entier,Table table) {
		if(MapTableAll==null){
			MapTableAll = new HashMap<Table, List<Object[]>>();
		}
		MapTableAll.put(table, dataTableAll);
		//this.dataTableAll = dataTableAll;
		//setData( dataTable1 , entier ,table);
		setData( dataTableAll , entier ,table);
	}

	public LgrTableViewer getTableViewer1() {
		return tableViewer1;
	}
	public void setTableViewer1(LgrTableViewer tableViewer1) {
		this.tableViewer1 = tableViewer1;
	}

	public LgrTableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(LgrTableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	public Composite getCompositeOnglet() {
		return compositeOnglet;
	}

	public Double getTotalCredit() {
		return TotalCredit;
	}
	public void setTotalCredit(Double totalCredit) {
		TotalCredit = totalCredit;
	}
	public Double getTotalDebit() {
		return TotalDebit;
	}
	public void setTotalDebit(Double totalDebit) {
		TotalDebit = totalDebit;
	}
	public String getTauxTva() {
		return TauxTva;
	}
	public void setTauxTva(String tauxTva) {
		TauxTva = tauxTva;
	}
	public String getTypeTva() {
		return TypeTva;
	}
	public void setTypeTva(String typeTva) {
		TypeTva = typeTva;
	}
	public String getDateLivraison() {
		return DateLivraison;
	}
	public void setDateLivraison(String dateLivraison) {
		DateLivraison = dateLivraison;
	}
	public String getCodeTva() {
		return CodeTva;
	}
	public void setCodeTva(String codeTva) {
		CodeTva = codeTva;
	}
	public String getMontantTva() {
		return MontantTva;
	}
	public void setMontantTva(String montantTva) {
		MontantTva = montantTva;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public static Double getDebitEtotal() {
		return debitEtotal;
	}
	public static void setDebitEtotal(Double debitEtotal) {
		debitEtotal = debitEtotal;
	}
	public static Double getCreditEtotal() {
		return creditEtotal;
	}
	public static void setCreditEtotal(Double creditEtotal) {
		creditEtotal = creditEtotal;
	}
	public CompositeOneTable getOngelEcriture() {
		return OngelEcriture;
	}
	public void setOngelEcriture(CompositeOneTable ongelEcriture) {
		OngelEcriture = ongelEcriture;
	}
	public String getDevise() {
		return devise;
	}
	public void setDevise(String devise) {
		this.devise = devise;
	}

	public String getTablePieces() {
		return tablePieces;
	}
	public void setTablePieces(String tablePieces) {
		this.tablePieces = tablePieces;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public Double getMontantPiece() {
		return MontantPiece;
	}
	public void setMontantPiece(Double montantPiece) {
		MontantPiece = montantPiece;
	}
	public Double getTotalCreditCompte() {
		return TotalCreditCompte;
	}
	public void setTotalCreditCompte(Double totalCreditCompte) {
		TotalCreditCompte = totalCreditCompte;
	}
	public Double getTotalDebitCompte() {
		return TotalDebitCompte;
	}
	public void setTotalDebitCompte(Double totalDebitCompte) {
		TotalDebitCompte = totalDebitCompte;
	}
	public Double getSoldeCompte() {
		return SoldeCompte;
	}
	public void setSoldeCompte(Double soldeCompte) {
		SoldeCompte = soldeCompte;
	}
	public Double getTotalCreditEcriture() {
		return TotalCreditEcriture;
	}
	public void setTotalCreditEcriture(Double totalCreditEcriture) {
		TotalCreditEcriture = totalCreditEcriture;
	}
	public Double getTotalDebitEcriture() {
		return TotalDebitEcriture;
	}
	public void setTotalDebitEcriture(Double totalDebitEcriture) {
		TotalDebitEcriture = totalDebitEcriture;
	}
	public CompositeTwoTable getOngelPiece() {
		return OngelPiece;
	}
	public void setOngelPiece(CompositeTwoTable ongelPiece) {
		OngelPiece = ongelPiece;
	}

	public void setCompositeOnglet(Composite compositeOnglet) {
		this.compositeOnglet = compositeOnglet;
	}

	public CompositeOneTableCompt getOngelCompte() {
		return OngelCompte;
	}
	public void setOngelCompte(CompositeOneTableCompt ongelCompte) {
		OngelCompte = ongelCompte;
	}
	public Map<Table, List<Object[]>> getMapTableAll() {
		return MapTableAll;
	}
	public void setMapTableAll(Map<Table, List<Object[]>> mapTableAll) {
		MapTableAll = mapTableAll;
	}

}
