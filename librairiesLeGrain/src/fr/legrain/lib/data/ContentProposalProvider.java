package fr.legrain.lib.data;

import java.util.ArrayList;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class ContentProposalProvider implements IContentProposalProvider {

	/*
	 * The proposals provided.
	 */
	private String[] proposals;
	private String[] descriptions;
	private String[] labels;
	/*
	 * Boolean that tracks whether filtering is used.
	 */
	private boolean filterProposals = true;
	/*
	 * The proposals mapped to IContentProposal.
	 */
	private IContentProposal[] contentProposals;

	/**
	 * Construct a SimpleContentProposalProvider whose content proposals are
	 * always the specified array of Objects.
	 * 
	 * @param proposals
	 *            the array of Strings to be returned whenever proposals are
	 *            requested.
	 */
	public ContentProposalProvider(String[] proposals,String[] descriptions,String[] labels) {
		super();
		this.proposals = proposals;
		this.descriptions =descriptions; 
		this.labels =labels;
	}


	public ContentProposalProvider(String[] proposals,String[] descriptions) {
		super();
		this.proposals = proposals;
		this.descriptions =descriptions; 
	}

	public ContentProposalProvider(String[] proposals) {
		super();
		this.proposals = proposals;
	}

	/**
	 * Return an array of Objects representing the valid content proposals for a
	 * field. Ignore the current contents of the field.
	 * 
	 * @param contents
	 *            the current contents of the field (ignored)
	 * @param position
	 *            the current cursor position within the field (ignored)
	 * @return the array of Objects that represent valid proposals for the field
	 *         given its current content.
	 */
	public IContentProposal [] getProposals(String contents, final int position) {
		if (filterProposals) {
			ArrayList list = new ArrayList();
			for (int i = 0; i < proposals.length; i++) {
				if (proposals[i].length() >= position
						&& proposals[i].substring(0, position)
						.equalsIgnoreCase(contents.substring(0,position))) {
					if(labels==null)labels = new String[proposals.length] ;
					list.add(makeContentProposal(proposals[i],descriptions[i],labels[i]));
				}
			}
			return (IContentProposal[]) list.toArray(new IContentProposal[list
			                                                              .size()]);
		}
		if (contentProposals == null) {
			contentProposals = new IContentProposal[proposals.length];
			for (int i=0; i<proposals.length; i++) {
				final String proposal = proposals[i];
				contentProposals[i] = makeContentProposal(proposals[i],descriptions[i],labels[i]);
			}
		}
		return contentProposals;
	}
	/**
	 * Set the Strings to be used as content proposals.
	 * 
	 * @param items
	 *            the array of Strings to be used as proposals.
	 */
	public void setProposals(String[] items) {
		this.proposals = items;
		contentProposals = null;
	}

	public void setDescription(String[] description) {
		this.descriptions = description;
	}

	public void setLabel(String[] label) {
		this.labels = label;
	}

	/*
	 * Make an IContentProposal for showing the specified String.
	 */
	private IContentProposal makeContentProposal(final String proposal,
			final String description,final String label) {
		return new IContentProposal() {
			public String getContent() {
				return proposal;
			}
			public String getDescription() {
				if(descriptions!=null && description!=null)
					return description;
				else
					return null;
			}
			public String getLabel() {
				if(labels!=null && label!=null)
					return label;
				else
					return null;
			}
			public int getCursorPosition() {
				return proposal.length();
			}
		};
	}


	public boolean isFilterProposals() {
		return filterProposals;
	}


	/**
	 * Set the boolean that controls whether proposals are filtered according to
	 * the current field content.
	 * 
	 * @param filterProposals
	 *            <code>true</code> if the proposals should be filtered to
	 *            show only those that match the current contents of the field,
	 *            and <code>false</code> if the proposals should remain the
	 *            same, ignoring the field content.
	 * @since 3.3
	 */
	public void setFiltering(boolean filterProposals) {
		this.filterProposals = filterProposals;
		// Clear any cached proposals.
		contentProposals = null;
	}
}
