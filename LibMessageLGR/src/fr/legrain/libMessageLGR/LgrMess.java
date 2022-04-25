package fr.legrain.libMessageLGR;

import org.eclipse.ui.PlatformUI;

public class LgrMess {
	public static String C_SAUT_DE_LIGNE ="\n";
	public static String C_RETOURCHARIOT ="\r";
	public static String C_DOUBLE_SAUT_DE_LIGNE ="\n\n";
	public static String C_SOULIGNE ="...................................................................";
	public static String C_CONTACTER_SERVICE_MAINTENANCE = C_DOUBLE_SAUT_DE_LIGNE
		+C_SOULIGNE+C_SAUT_DE_LIGNE
		+"Contacter le service de maintenance au 05.63.30.31.44"
		+C_SAUT_DE_LIGNE+C_SOULIGNE;	
	public static String C_CONFIG_EMAIL = "L'envoie d'E-mail est mal configuré."
		+LgrMess.C_SAUT_DE_LIGNE+
		"Vous devez remplir vos paramètres de connexion dans le menu Outil/Préférences/E-mail!";
	private static boolean DEVELOPPEMENT = false;
	private static boolean DECORE = true;
	private static boolean TOUJOURS_MAJ = false;
	private static String UTILISATEUR = System.getProperty("user.name");
	private static boolean DOSSIER_EN_RESEAU = false;
	private static boolean AfficheAideRemplie = true;


	public static boolean isDEVELOPPEMENT() {
		return DEVELOPPEMENT;
	}
	
	public static void setDEVELOPPEMENT(boolean developpement,boolean majTitre) {
		if(developpement!=DEVELOPPEMENT && majTitre){
	    	if(developpement)				
	    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
	    		setText(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
	    				getText()+" - Attention !!! Version développement -");
	    	else
	    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().
	    		setText(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getText());
		}
		DEVELOPPEMENT = developpement;
	}

	public static boolean isDECORE() {
		return DECORE;
	}

	public static void setDECORE(boolean decore) {
		DECORE = decore;
	}

	public static boolean isTOUJOURS_MAJ() {
		return TOUJOURS_MAJ;
	}

	public static void setTOUJOURS_MAJ(boolean toujours_maj) {
		TOUJOURS_MAJ = toujours_maj;
	}

	public static String getUTILISATEUR() {
		return UTILISATEUR;
	}

	public static void setUTILISATEUR(String utilisateur) {
		UTILISATEUR = utilisateur;
	}

	public static boolean isDOSSIER_EN_RESEAU() {
		return DOSSIER_EN_RESEAU;
	}

	public static void setDOSSIER_EN_RESEAU(boolean dOSSIERENRESEAU) {
		DOSSIER_EN_RESEAU = dOSSIERENRESEAU;
	}

	public static boolean isAfficheAideRemplie() {
		return AfficheAideRemplie;
	}

	public static void setAfficheAideRemplie(boolean afficheAideRemplie) {
		AfficheAideRemplie = afficheAideRemplie;
	}

}
