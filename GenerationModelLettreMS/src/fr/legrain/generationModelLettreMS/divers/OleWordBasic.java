package fr.legrain.generationModelLettreMS.divers;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.ole.win32.*;

public class OleWordBasic {
	// Generated from typelib filename: wb70en32.tlb
	private OleAutomation oleObject;
	public static final int wdOpenFormatAuto = 0;
	public static final int wdOpenFormatDocument = 1;
	public static final int wdOpenFormatTemplate = 2;
	public static final int wdOpenFormatRTF = 3;
	public static final int wdOpenFormatText = 4;
	public static final int wdOpenFormatUnicodeText = 5;
	
	/*** zhaolin ***/
	private static final int WD_FIND_CONTINUE      = 1;
	private static final int WD_REPLACE_ALL        = 2;
	/*** zhaolin ***/
	public OleWordBasic(OleAutomation automationObject) {
		oleObject = automationObject;
	}
	public void AppShow(){
		// dispid = 33057 name = AppShow
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 33057;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = getIDsOfNames(new String[]{"AppShow"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}
	public void dispose() {
		if (oleObject != null)
			oleObject.dispose();
		oleObject = null;
	}
	//Removes the selection without putting it on the Clipboard
	public void EditClear(){
		// dispid = 32897 name = EditClear
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 32897;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = getIDsOfNames(new String[]{"EditClear"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}
	// Copies the selection and puts it on the Clipboard
	public void EditCopy(){
		// dispid = 109 name = EditCopy
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 109;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = getIDsOfNames(new String[]{"EditCopy"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}
	//Selects the entire document"
	public void EditSelectAll(){
		// dispid = 237
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 237;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = getIDsOfNames(new String[]{"EditSelectAll"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}
	// Moves the Insertion point or, if Select is non-zero, the active end
	// of the selection to the end of the document
	// This method return 0 if the insertion point or the active end of the
	// selection was not moved (ie it was already at the end of the document).
	// This method returns -1 if the insertion point or the active end of the selection has moved.
	public short EndOfDocument(int select){
		// dispid=49169, type=METHOD, name="EndOfDocument"
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 49169;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"EndOfDocument"});
		//int dispIdMember = rgdispid[0];
		Variant[] rgvarg = null;
		if (select >= 0 ){
			rgvarg = new Variant[1];
			rgvarg[0] = new Variant(select);
		}
		Variant pVarResult = oleObject.invoke(dispIdMember, rgvarg);
		if (pVarResult == null) return -1;
		return pVarResult.getShort();
	}
	// Opens an existing document or template
	public void FileOpen(String name)
	{
		// dispid = 80 name = FileOpen
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		//int dispIdMember = 80;
		// Alternatively, you can look up the DISPID dynamically
		int[] rgdispid = oleObject.getIDsOfNames(new String[]{"FileOpen"});
		int dispIdMember = rgdispid[0];
		Variant[] rgvarg = new Variant[1];
		rgvarg[0] = new Variant(name);
		oleObject.invokeNoReply(dispIdMember, rgvarg);
	}
	// Saves the active document or template
	public void FileSave(String name,OleAutomation oleAutomation){
		// dispid = 83 name = FileSave
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		//int dispIdMember = 83;
		// Alternatively, you can look up the DISPID dynamically
		int[] rgdispid = oleObject.getIDsOfNames(new String[]{"FileSave"});
		int dispIdMember = rgdispid[0];
		
		Variant[] rgvarg = new Variant[1];
		rgvarg[0] = new Variant(name);
		
		oleAutomation.invokeNoReply(dispIdMember,rgvarg);
	}
	public void FormatFont(int points, boolean bold, boolean italic){
		// dispid=174, type=METHOD, name=FormatFont
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		//int dispIdMember = 174;
		// Alternatively, you can look up the DISPID dynamically
		int[] rgdispid = oleObject.getIDsOfNames(new String[]{"FormatFont", 
				"Points", "Color", "Font", "Bold", "Italic"});
		int dispIdMember = rgdispid[0];
		int index = 0;
		Variant[] rgvarg = new Variant[3];
		int[] rgdispidNamedArgs = new int[3];
		rgvarg[0] = new Variant(points);
		rgdispidNamedArgs[0] = rgdispid[1];
		rgvarg[1] = new Variant(bold?1:0);
		rgdispidNamedArgs[1] = rgdispid[4];
		rgvarg[2] = new Variant(italic?1:0);
		rgdispidNamedArgs[2] = rgdispid[5];
		oleObject.invokeNoReply(dispIdMember, rgvarg, rgdispidNamedArgs);
	}
	public void Insert(String text){
		//dispid=32786, type=METHOD, name="Insert"
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 32786;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"Insert"});
		//int dispIdMember = rgdispid[0];
		Variant[] rgvarg = new Variant[1];
		rgvarg[0] = new Variant(text);
		oleObject.invokeNoReply(dispIdMember, rgvarg);
	}
	public void InsertFile(String name){
		//dispid=164, type=METHOD, name="InsertFile"
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		//int dispIdMember = 164;
		// Alternatively, you can look up the DISPID dynamically
		int[] rgdispid = oleObject.getIDsOfNames(new String[]{"InsertFile", "Name"});
		int dispIdMember = rgdispid[0];
		Variant[] rgvarg = new Variant[1];
		rgvarg[0] = new Variant(name);
		oleObject.invokeNoReply(dispIdMember, rgvarg);
	}
	public void InsertFile(String name,	//Variant Range,
			boolean confirmConversions
			//boolean Link
	)
	{
		//dispid=164, type=METHOD, name="InsertFile"
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		//int dispIdMember = 164;
		// Alternatively, you can look up the DISPID dynamically
		int[] rgdispid = oleObject.getIDsOfNames(new String[]{"InsertFile", "Name", 
		"ConfirmConversions"});
		int dispIdMember = rgdispid[0];
		Variant[] rgvarg = new Variant[2];
		int[] rgdispidNamedArgs = new int[2];
		rgvarg[0] = new Variant(name);
		rgdispidNamedArgs[0] = rgdispid[1];
		rgvarg[1] = new Variant(confirmConversions?1:0);
		rgdispidNamedArgs[1] = rgdispid[2];
		oleObject.invokeNoReply(dispIdMember, rgvarg, rgdispidNamedArgs);
	}
	public void InsertPara(){
		//dispid=32787, type=METHOD, name="InsertPara"
		// You can hard code the DISPID if you know it before hand - this is of 	course the fastest way
		int dispIdMember = 32787;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"InsertPara"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}
	public short SelType(){
		//dispid=32922, type=METHOD, name="SelType"
		// You can hard code the DISPID if you know it before hand - this is of 	course the fastest way
		int dispIdMember = 32922;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"SelType"});
		//int dispIdMember = rgdispid[0];
		Variant pVarResult = oleObject.invoke(dispIdMember);
		if (pVarResult == null) return -1;
		return pVarResult.getShort();
	}
	// Moves the insertion point to the beginning of the first line of the 	document
	public short StartOfDocument(int select){
		// dispid= 49168, type=METHOD, name="StartOfDocument"
		// You can hard code the DISPID if you know it before hand - this is of 	course the fastest way
		int dispIdMember = 49168;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"StartOfDocument"});
		//int dispIdMember = rgdispid[0];
		Variant[] rgvarg = null;
		if (select >= 0 ){
			rgvarg = new Variant[1];
			rgvarg[0] = new Variant(select);
		}
		Variant pVarResult = oleObject.invoke(dispIdMember, rgvarg);
		if (pVarResult == null) return -1;
		return pVarResult.getShort();
	}
	public void ToolsSpelling(){
		//dispid=191, type=METHOD, name="ToolsSpelling"
		// You can hard code the DISPID if you know it before hand - this is of course the fastest way
		int dispIdMember = 191;
		// Alternatively, you can look up the DISPID dynamically
		//int[] rgdispid = oleObject.getIDsOfNames(new String[]{"ToolsSpelling"});
		//int dispIdMember = rgdispid[0];
		oleObject.invokeNoReply(dispIdMember);
	}

	/*** zhaolin ***/

	public void replace(String searchTerm,
			String replacementTerm,OleAutomation oleAutomation) throws SWTException,
			NullPointerException {

		OleAutomation selectionFindAutomation = null;
		OleAutomation childAutomation         = null;
		Variant[] arguments                   = null;
		Variant invokeResult                  = null;
		int[] id                              = null;
		int[] namedArguments                  = null;
		boolean success                       = true;

		// Validate the searchTerm parameter and throw exception if
		// null value passed.
		if(searchTerm == null) {
			throw new NullPointerException("Null value passed to " +
			"searchTerm parameter of the replace() method.");
		}

		// Validate the replacementTerm parameter and throw exception if
		// null value passed.
		if(replacementTerm == null) {
			throw new NullPointerException("Null value passed to " +
			"replacementTerm parameter of the replace() method.");
		}

		// Most of the VBA instructions used to perform the search and
		// replace functionality and child automations of Selection.Find,
		// therefore, it is wise to cache that automation first.
		// From the application, get an automation for the Selection property
		childAutomation         = this.getChildAutomation(oleAutomation,
		"Selection");

		selectionFindAutomation = this.getChildAutomation(childAutomation,
		"Find");
		// Next, using the cached automation, invoke the 'ClearFormatting'
		// method, validate the returned value and invoke the method.
		//
		// Selection.Find.ClearFormatting
		//
		id = selectionFindAutomation.getIDsOfNames(new String[]{"ClearFormatting"});
		if(id == null) {
			throw new SWTException("It is not possible to recover an identifier " +
					"for the ClearFormatting method in WordSearchReplace.replace() " +
			"when clearing the formatting for the search string.");
		}
		invokeResult = selectionFindAutomation.invoke(id[0]);
		if(invokeResult == null) {
			throw new SWTException("A problem occurred invoking the " +
					"ClearFormatting method in WordSearchReplace.repace() " +
			"when clearing formatting for the search string.");
		}

		// Now, perform the same function but for the replacement string.
		//
		// Selection.Find.Replacement.ClearFormatting
		//
		childAutomation = this.getChildAutomation(selectionFindAutomation,
		"Replacement");
		id = childAutomation.getIDsOfNames(new String[]{"ClearFormatting"});
		if(id == null) {
			throw new SWTException("It is not possible to recover an identifier " +
					"for the ClearFormatting method in WordSearchReplace.replace() " +
			"when clearing the formatting for the replacement string.");
		}
		invokeResult = childAutomation.invoke(id[0]);
		if(invokeResult == null) {
			throw new SWTException("A problem occurred invoking the " +
					"ClearFormatting method in WordSearchReplace.repace() " +
			"when clearing formatting for the replacement string.");
		}

		// Firstly, set the search text.
		//
		// .Text = "search term" 
		//
		arguments    = new Variant[1];
		arguments[0] = new Variant(searchTerm);
		success = this.setPropertyValue(selectionFindAutomation, "Text", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the Text " +
			"property for the search string in WordSearchReplace.replace().");
		}

		// Next, the replacement text
		//
		// .Replacement.Text = "replacement term"
		//
		childAutomation = this.getChildAutomation(selectionFindAutomation,
		"Replacement");
		arguments[0] = new Variant(replacementTerm);
		success = this.setPropertyValue(childAutomation, "Text", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the Text property" +
			" for the replacement string in WordSearchReplace.replace().");
		}

		// Set the direction of the search - forward in this case.
		//
		// .Forward = True
		//
		arguments[0] = new Variant(true);
		success = this.setPropertyValue(selectionFindAutomation, "Forward", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the Forward " +
			"property in WordSearchReplace.replace().");
		}

		// Tell the search to wrap. Note the literal wdFindContinue relates to
		// a constant that is defined within Word. I have provided a static 
		// final to replace it called WD_FIND_CONTINUE
		//
		// .Wrap = wdFindContinue
		//
		arguments[0] = new Variant(WD_FIND_CONTINUE);
		success = this.setPropertyValue(selectionFindAutomation, "Wrap", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the Wrap " +
			"property in WordSearchReplace.replace().");
		}

		// Set the Format property to False.
		//
		// .Format = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "Format", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the Format " +
			"property in WordSearchReplace.replace().");
		}

		// Set the MatchCase property to false.
		//
		// .MatchCase = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "MatchCase", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the MatchCase " +
			"property in WordSearchReplace.replace().");
		}

		// Set the MatchWholeWord property to false.
		//
		// .MatchWholeWord = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "MatchWholeWord", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the " +
			"MatchWholeWord property in WordSearchReplace.replace().");
		}

		// Set the MatchWildCards property to false.
		//
		// .MatchWildcards = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "MatchWildCards", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the " +
			"MatchWildCards property in WordSearchReplace.replace().");
		}

		// Set the MatchSoundsLike property to false.
		//
		// .MatchSoundsLike = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "MatchSoundsLike", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the " +
			"MatchSoundsLike property in WordSearchReplace.replace().");
		}

		// Set the MatchAllWordForms property to false.
		//
		// .MatchAllWordForms = False
		//
		arguments[0] = new Variant(false);
		success = this.setPropertyValue(selectionFindAutomation, "MatchAllWordForms", arguments);
		if(!success) {
			throw new SWTException("A problem occurred setting the " +
			"MatchAllWordForms property in WordSearchReplace.replace().");
		}

		// Invoke the Execute command passing the correct value to the Replace
		// parameter. Again, wdReplaceAll is a constant that I have provided
		// a ststic final for called WD_REPLACE_ALL
		//
		// Selection.Find.Execute Replace:=wdReplaceAll
		//
		id = selectionFindAutomation.getIDsOfNames(new String[]{"Execute", "Replace"});
		if(id == null) {
			throw new SWTException("It was not possible to recover an identifier " +
			"for the Execute method in WordSearchReplace.replace().");
		}

		arguments         = new Variant[1];
		arguments[0]      = new Variant(WD_REPLACE_ALL);
		namedArguments    = new int[1];
		namedArguments[0] = id[1];

		// There was some indication that the invokeNoReply method should
		// be used when making this call but no, invoke SEEMS to work well
		//selectionFindAutomation.invokeNoReply(id[0], arguments, namedArguments);
		invokeResult = selectionFindAutomation.invoke(id[0], arguments, namedArguments);
		if(invokeResult == null) {
			throw new SWTException("A problem occurred trying to invoke the " +
			"Execute method in WordSearchReplace.replace().");
		}

	}
	private OleAutomation getChildAutomation(OleAutomation automation,
			String childName) throws SWTException {

		// Try to recove the unique identifier for the child automation
		int[] id = automation.getIDsOfNames(new String[]{childName});

		// If the identifier cannot be found then throw an exception to
		// terminate processing.      		
		if (id == null)  {
			throw new SWTException(
					"A problem occurred trying to obtain and id for: " +
					childName +
			"in the getChildAutomation() method.");
		}

		// SWT's implementation of OLE referes to all of Words objects, methods
		// and properties using the single term 'property'. The next stage 
		// therefore is to recover a refence to the 'property' that relates
		// to the child automation.
		Variant pVarResult = automation.getProperty(id[0]);

		// If it is not possible to recover a 'property' for the child 
		// automation, then throw an SWTException.
		if (pVarResult == null) {
			throw new SWTException(
					"A problem occurred trying to obtain an automation for property: " +
					id[0] +
			" in the getChildAutomation() method.");
		}

		// As we are after a child automation in this instance, call the
		// getAutomation() method on the 'property'.
		return(pVarResult.getAutomation());
	}

	private boolean setPropertyValue(OleAutomation automation,
			String propertyName,
			Variant[] arguments) throws SWTException,
			NullPointerException,
			IllegalArgumentException {
		// Validate the various parameters
		if(automation == null) {
			throw new NullPointerException(
					"A null value was passed to the automation parameter of " +
			"WordSearchReplace.setPropertyValue().");
		}
		if(propertyName == null) {
			throw new NullPointerException(
					"A null value was passed to the propertyName parameter of " +
			"WordSearchReplace.setPropertyValue().");
		}
		if(propertyName.length() == 0) {
			throw new IllegalArgumentException(
					"An empty - zero length - String was passed to the propertyName " +
			"parameter of WordSearchReplace.setPropertyValue().");
		}
		if(arguments == null) {
			throw new NullPointerException(
					"A null value was passed to the arguments parameter of " +
			"WordSearchReplace.setPropertyValue().");
		}
		if(arguments.length == 0) {
			throw new IllegalArgumentException(
					"An empty - zero length - array was passed to the arguments " +
			"parameter of WordSearchReplace.setPropertyValue().");
		}

		// Recover the identifier for the property
		int[] id = automation.getIDsOfNames(new String[]{propertyName});
		if(id == null) {
			throw new SWTException("Unable to obtain an identifier for the " +
					propertyName +
			" property in WordSearchReplace.setPropertyValue().");
		}

		// Try to set the properties value. If this fails, the boolean value
		// false will be returned to the calling code.
		return(automation.setProperty(id[0], arguments));
	}
	/*** zhaolin ***/ 
}
