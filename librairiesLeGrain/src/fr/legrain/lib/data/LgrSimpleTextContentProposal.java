package fr.legrain.lib.data;

import org.apache.log4j.Logger;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class LgrSimpleTextContentProposal{
	
	static Logger logger = Logger.getLogger(LgrSimpleTextContentProposal.class.getName());
	
	/**
	 * 
	 * @param control - le composant texte
	 * @param keyStroke - raccourci devant  déclancher l'apparition des possibilites (voif org.eclipse.jface.bindings.keys.KeyStroke)
	 * @param proposalAndDetail - les choix possibles et leurs description ( [choix][description du choix] )
	 * @param autoActivation - caracteres qui déclenchent automatiquement l'apparition des possibilites 
	 * @return
	 */
	static public ContentProposalAdapter defaultTextContentProposalKey(Text control, String keyStroke, String[][] proposalAndDetail, char[] autoActivation) {
		ContentProposalAdapter retour = null;
		try {
			KeyStroke ks = KeyStroke.getInstance(keyStroke);

			String[] listeCodeFacture = null;
			String[] listeLibelleFacture = null;
			
			if(proposalAndDetail!=null){
				listeCodeFacture=new String[proposalAndDetail.length];
				listeLibelleFacture=new String[proposalAndDetail.length];
				for (int i=0; i<proposalAndDetail.length; i++) {
					listeCodeFacture [i]=proposalAndDetail[i][0];
					listeLibelleFacture [i]=proposalAndDetail[i][1];
				}
			}

			ContentProposalAdapter contentProposal = new ContentProposalAdapter(
					control, 
					new TextContentAdapter(), 
					new ContentProposalProvider(listeCodeFacture,listeLibelleFacture),
					ks, 
					autoActivation
			);
			
			retour = contentProposal;

			return retour;
		} catch (ParseException e) {
			logger.error("",e);
		}
		return retour;
	}
	
	/**
	 * 
	 * @param control - le composant texte
	 * @param commandId - l' ID de la commande devant  déclancher l'apparition des possibilites
	 * @param proposalAndDetail - les choix possibles et leurs description ( [choix][description du choix] )
	 * @param autoActivation - caracteres qui déclenchent automatiquement l'apparition des possibilites 
	 * @return
	 */
	static public ContentAssistCommandAdapter defaultTextContentProposalCommand(Text control, String commandId, String[][] proposalAndDetail, char[] autoActivation) {
		ContentAssistCommandAdapter retour = null;

		String[] listeCodeFacture = null;
		String[] listeLibelleFacture = null;

		if(proposalAndDetail!=null){
			listeCodeFacture=new String[proposalAndDetail.length];
			listeLibelleFacture=new String[proposalAndDetail.length];
			for (int i=0; i<proposalAndDetail.length; i++) {
				listeCodeFacture [i]=proposalAndDetail[i][0];
				listeLibelleFacture [i]=proposalAndDetail[i][1];
			}
		}

		ContentAssistCommandAdapter contentProposal = new ContentAssistCommandAdapter(
				control, 
				new TextContentAdapter(), 
				new ContentProposalProvider(listeCodeFacture,listeLibelleFacture),
				commandId, 
				autoActivation
		);
		
		retour = contentProposal;

		return retour;
	}
	
	static public void defaultOptions(ContentProposalAdapter contentProposal) {
		contentProposal.setFilterStyle(ContentProposalAdapter.FILTER_NONE);
		contentProposal.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}
}
