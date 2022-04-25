package fr.legrain.liasseFiscale.wizards;

import java.awt.BorderLayout;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
//import org.pdfbox.pdfviewer.PDFPagePanel;
//import org.pdfbox.pdfviewer.ReaderBottomPanel;
//import org.pdfbox.pdmodel.PDDocument;
//import org.pdfbox.pdmodel.PDPage;
//import org.pdfbox.pdmodel.interactive.form.PDAcroForm;
//import org.pdfbox.pdmodel.interactive.form.PDField;

import com.lowagie.text.Document;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.FdfReader;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.AcroFields.Item;
import com.lowagie.tools.Executable;

public class TestPDF {
	
	public void testGenEcran(Composite parent, int style) {
		Composite c = new Composite(parent,style);
		
		Label l = new Label(c,SWT.NONE);
		l.setText("def");
		GridData lData = new GridData(GridData.FILL,GridData.FILL,true,false);
		l.setLayoutData(lData);
		
		Text t = new Text(c,SWT.NONE);
		t.setText("abc");
		GridData tData = new GridData(GridData.FILL,GridData.FILL,true,false);
		t.setLayoutData(tData);
		
		Label l2 = new Label(c,SWT.NONE);
		l2.setText("2");
		l2.setLayoutData(lData);
		
		Label l3 = new Label(c,SWT.NONE);
		l3.setText("3");
		l3.setLayoutData(lData);
		
		c.setSize(511, 483);
		c.setLayout(new GridLayout(2,true));
		c.layout();
	}
	
	public void testRectangle() {
		//pdfbox test du viewer
//		try {
//		PDDocument pdfdoc = PDDocument.load("C:/Liasse Fiscale/LIASSE BIC REEL 2050.pdf");
//		
//		PDAcroForm form = pdfdoc.getDocumentCatalog().getAcroForm();
//		
//		//List allPages = pdfdoc.getDocumentCatalog().getAllPages();
//		
//		List allFields = form.getFields();
//		FileOutputStream fos = new FileOutputStream("c:/bbb.txt");
//		String ligne;
//		String retourLigne = "\n";
//		
//		for (Object f : allFields) {
//			ligne = "getFullyQualifiedName "+((PDField)f).getFullyQualifiedName();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "getUpperRightX "+ ((PDField)f).getWidget().getRectangle().getUpperRightX();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "getUpperRightY "+ ((PDField)f).getWidget().getRectangle().getUpperRightY();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "getHeight "+ ((PDField)f).getWidget().getRectangle().getHeight();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "getWidth "+ ((PDField)f).getWidget().getRectangle().getWidth();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "getWidth "+ ((PDField)f).getWidget().getRectangle().getWidth();
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//			ligne = "--------------------------------";
//			fos.write(ligne.getBytes());
//			fos.write(retourLigne.getBytes());
//		}
//		fos.close();
//
////		PDPage page = (PDPage)allPages.get(2);
////		PDFPagePanel p = new PDFPagePanel();
////		JFrame f = new JFrame();
////		f.setLayout(new BorderLayout());
////		p.setPage(page);
////		p.setVisible(true);
////		f.add(p,BorderLayout.CENTER);
////		
////		ReaderBottomPanel rbp = new ReaderBottomPanel();
////		f.add(rbp,BorderLayout.SOUTH);
////		
////		f.setVisible(true);	
//		
//		} catch (Exception e){
//			 e.printStackTrace();
//		}
		
	}
	
	public void test() {
		try {
			//PdfReader pdfr = new PdfReader("");
			//pdfr.
			//FdfReader fdfr = new FdfReader("");
			//fdfr.
			//PdfObject pdfo = new PdfObject(0);
			//pdfo.
			//PRStream prs = new PRStream(null,0);
			//prs.
			//PdfDictionary dico = new PdfDictionary();
			//dico.
			
		
			// iText : dictionnaire
			PdfReader pdfr = new PdfReader("C:/Liasse Fiscale/LIASSE BIC REEL 2050.pdf");
			FileOutputStream fos = new FileOutputStream("c:/aaa.txt");
			AcroFields acroFields = pdfr.getAcroFields();
			
			HashMap m = acroFields.getFields();
			String ligne;
			String retourLigne = "\n";
			int i = 0;
			for (Object s : m.keySet()) {
				i++;
				
				AcroFields.Item item = (Item) m.get(s);
				ligne = i+" : "+((String)s) + " => page :"+ item.page;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => widget_refs :"+ item.widget_refs;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => widgets :"+ item.widgets;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => values :"+ item.values;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => merged :"+ item.merged;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => tabOrder :"+ item.tabOrder;
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => acroFields.getField :"+ acroFields.getField(((String)s));
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				ligne = i+" : "+((String)s) + " => acroFields.getFieldPositions :"
						+ acroFields.getFieldPositions(((String)s))[0] + " * "
						+ acroFields.getFieldPositions(((String)s))[1] + " * "
						+ acroFields.getFieldPositions(((String)s))[2] + " * "
						+ acroFields.getFieldPositions(((String)s))[3] + " * "
						+ acroFields.getFieldPositions(((String)s))[4];
				fos.write(ligne.getBytes());
				fos.write(retourLigne.getBytes());
				fos.write("-----------------------------------------------------------".getBytes());
				fos.write(retourLigne.getBytes());

			}
			fos.close();
			
			/* 
			// iText : javascript
			RandomAccessFileOrArray f = new RandomAccessFileOrArray("");
			//this.exportAsFDF(true,true,null,true);
			PdfReader pdfr = new PdfReader("C:/Liasse Fiscale/LIASSE BIC REEL 2050.pdf");
			DescriptionDocument document = new DescriptionDocument();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfCopy writer = new PdfCopy(document, baos);
			document.open();
			PdfAction act = new PdfAction(); 
			writer.addJavaScript("this.print(false);", false);
			document.close();
			String s = pdfr.getJavaScript();
			System.err.println(s);
			*/
			
			/*
			//pdfbox test du viewer
			PDDocument pdfdoc = PDDocument.load("C:/Liasse Fiscale/LIASSE BIC REEL 2050.pdf");
			List allPages = pdfdoc.getDocumentCatalog().getAllPages();

			PDPage page = (PDPage)allPages.get(2);
			PDFPagePanel p = new PDFPagePanel();
			JFrame f = new JFrame();
			f.setLayout(new BorderLayout());
			p.setPage(page);
			p.setVisible(true);
			f.add(p,BorderLayout.CENTER);
			
			ReaderBottomPanel rbp = new ReaderBottomPanel();
			f.add(rbp,BorderLayout.SOUTH);
			
			f.setVisible(true);	
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
