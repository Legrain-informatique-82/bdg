package fr.legrain.extensionprovider;

import java.util.Map;

import fr.legrain.edition.actions.AnalyseFileReport;

public interface IConnect {
	void connect();
	Map getInfosEdititon(String namePlugin,String nameEntity,AnalyseFileReport analyseFileReport);
	String  getPathFolderEdititonPay(String namePlugin,String nameEntity,AnalyseFileReport analyseFileReport);

}
