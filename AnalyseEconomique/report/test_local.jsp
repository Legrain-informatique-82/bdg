<%@page import="java.net.URLEncoder"%>

<html>
	<head>
		<link rel="shortcut icon" type="image/gif" href="legrain.gif" />
	</head>
	<body>
		<!-- 
			<a href="http://localhost:8080/birt/frameset?__report=/donnees/apache-tomcat-5.5.23/webapps/birt/report/format_donnees_edition.xml&__format=pdf">test edition normal</a>
		-->

		<%! 
			String url = null; 
			String urlPdf = null;
			final String debutUrl = "http://localhost:8080/birt/report";
		%>

		<hr/>
		<ul>
			<li>
				<a href="<%= debutUrl %>/analyseEco.xml">Fichier venant de la comptabilité</a>
			</li>
			<li>
				<a href="<%= debutUrl %>/format_donnees_edition.xml">Fichier représentant l'analyse économique</a>
			</li>
			<li>
				<a href="<%= debutUrl %>/analyse.rptdesign">Modèle de l'édition</a>
			</li>
		</ul>

		<%
			String rptDesignPath = URLEncoder.encode("/report/analyse.rptdesign", "UTF-8");
			String xmlDataSourcePath = URLEncoder.encode("/donnees/apache-tomcat-5.5.23/webapps/birt/report/format_donnees_edition.xml", "UTF-8");
			url = "http://localhost:8080/birt/frameset?__report="+rptDesignPath+"&data="+xmlDataSourcePath;
			urlPdf = url+"&__format=pdf";
		%>
		<hr/>
		<ul>
			<li>
				<a href="<%= url %>">Exemple d'édition en HTML paginé (web viewer)</a>
			</li>
			<li>
				<a href="<%= urlPdf %>">Exemple d'édition en PDF</a>
			</li>
		</ul>
		<hr/>

	</body>
</html>
