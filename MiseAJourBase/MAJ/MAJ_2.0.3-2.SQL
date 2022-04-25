

CREATE or ALTER  PROCEDURE GESTION_INSERTION_3
as    
DECLARE VARIABLE QUERY VARCHAR(1000);
BEGIN
    if (not exists(select ID_T_LIENS from TA_T_LIENS where CODE_T_LIENS='F'))then
    INSERT INTO TA_T_LIENS (CODE_T_LIENS, LIBL_T_LIENS, VERSION_OBJ) VALUES ('F', 'FICHIER', 0);
    if (not exists(select ID_T_LIENS from TA_T_LIENS where CODE_T_LIENS='R'))then
    INSERT INTO TA_T_LIENS (CODE_T_LIENS, LIBL_T_LIENS, VERSION_OBJ) VALUES ('R', 'REPERTOIRE', 0);
    if (not exists(select ID_T_LIENS from TA_T_LIENS where CODE_T_LIENS='RC'))then
    INSERT INTO TA_T_LIENS (CODE_T_LIENS, LIBL_T_LIENS, VERSION_OBJ) VALUES ('RC', 'REPERTOIRE COURRIER', 0);
END
^
EXECUTE PROCEDURE GESTION_INSERTION_3
^
drop procedure GESTION_INSERTION_3
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR4
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    if(not exists(select * from TA_VISUALISATION where lower(CODE_REQUETE) like 'extraction tiers - tous'))then
    begin
         query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, 
ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE) 
VALUES (gen_id(num_id_visualisation,1), ''Extraction tiers - Tous'', ''select distinct(t.nomTiers),t.prenomTiers,a.adresse1Adresse, a.adresse2Adresse,a.adresse3Adresse,  a.codepostalAdresse, a.villeAdresse, 
a.paysAdresse,substring(a.codepostalAdresse,1,2),tc.codeTCivilite,t.surnomTiers, t.codeTiers,e.nomEntreprise,t.actifTiers,t.ttcTiers,tt.codeTTiers,
  tt.libelleTTiers,c.commentaire,te.codeTEntite, co.tvaIComCompl,  co.siretCompl,tel.numeroTelephone,mail.adresseEmail,w.adresseWeb,t.quandCreeTiers
from fr.legrain.tiers.dao.TaTiers t  
left join t.taAdresse a 
left join t.taTCivilite tc 
left join t.taEntreprise e 
left join t.taTTiers tt 
left join t.taCommentaire c 
left join t.taTEntite te 
left join t.taCompl co 
left join t.taTelephone tel 
left join t.taEmail mail 
left join t.taWeb w    '', ''t.nomTiers;t.prenomTiers;a.adresse1Adresse;a.adresse2Adresse;a.adresse3Adresse;a.codepostalAdresse;a.villeAdresse;a.paysAdresse;
substring(a.codepostalAdresse,1,2);tc.codeTCivilite;t.surnomTiers;t.codeTiers;e.nomEntreprise;t.actifTiers;t.ttcTiers;tt.codeTTiers;tt.libelleTTiers;
c.commentaire;te.codeTEntite;co.tvaIComCompl;co.siretCompl;tel.numeroTelephone;mail.adresseEmail;w.adresseWeb;t.quandCreeTiers'', 
''Nom;Prénom;Adresse 1;Adresse 2;Adresse 3;Code poastal;Ville;Pays;Département;Civilité;Surnom;Code tiers;Nom entreprise;Actif;TTC;
Type tiers;Libellé type;Commentaire;Type entité;Tva intra;Siret;Tel;Email;Web'', ''50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50;50'', ''tiers'', 
''/report/extraction_Tiers.rptdesign'', ''t.codeTiers'', ''fr.legrain.tiers.editor.EditorTiers'', ''Tiers'', NULL, '''', NULL, 0, NULL, 
''s;s;s;s;s;s;s;s;i;s;s;s;s;b;b;s;s;s;s;s;s;s;s;s'', NULL, ''JPQL'', NULL)';
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR4
^
drop procedure GESTION_INSERTION_VALEUR4
^


delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles prelevement'
^
    
CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR5
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    --if(not exists(select * from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles prelevement'))then
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN,
CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles prelevement'', ''select pf.dateDocument,pf.taTiers.codeTiers,i.nomTiers,pf.codeDocument,lpf.taArticle.codeArticle,lpf.u1LDocument,lpf.qteLDocument,
lpf.mtHtLApresRemiseGlobaleDocument,lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument,
lpf.mtTtcLApresRemiseGlobaleDocument from TaPrelevement pf left join pf.lignes lpf join pf.taInfosDocument i order by pf.codeDocument'', ''pf.dateDocument;
pf.taTiers.codeTiers;i.nomTiers;pf.codeDocument;lpf.taArticle.codeArticle;lpf.u1LDocument;lpf.qteLDocument;
lpf.mtHtLApresRemiseGlobaleDocument;lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument;
lpf.mtTtcLApresRemiseGlobaleDocument'', ''Date document;Code tiers;nom Tiers;Code document;Code article;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100'',
 ''Prelevement'', ''aa'', ''pf.codeDocument'', ''fr.legrain.editor.prelevement.swt.multi'', ''Prelevement'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR5
^
drop procedure GESTION_INSERTION_VALEUR5
^



    
CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR6
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles acompte';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, 
ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles acompte'', ''select pf.dateDocument,pf.taTiers.codeTiers,i.nomTiers,pf.codeDocument,
lpf.taArticle.codeArticle,lpf.u1LDocument,lpf.qteLDocument,lpf.mtHtLApresRemiseGlobaleDocument,lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument,
lpf.mtTtcLApresRemiseGlobaleDocument from TaAcompte pf left join pf.lignes lpf join pf.taInfosDocument i order by pf.codeDocument'', ''pf.dateDocument;pf.taTiers.codeTiers;i.nomTiers;pf.codeDocument;lpf.taArticle.codeArticle;
lpf.u1LDocument;lpf.qteLDocument;lpf.mtHtLApresRemiseGlobaleDocument;lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument;
lpf.mtTtcLApresRemiseGlobaleDocument'', ''Date Acompte;Code tiers;Nom tiers;Code Acompte;Code article;Unités;Qtés;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100'',
 ''acompte'', ''aa'', ''pf.codeDocument'', ''fr.legrain.editor.acompte.swt.multi'', ''Acompte'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR6
^
drop procedure GESTION_INSERTION_VALEUR6
^


CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR7
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles proforma';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles proforma'', ''select pf.dateDocument,pf.taTiers.codeTiers,i.nomTiers,pf.codeDocument,lpf.taArticle.codeArticle,lpf.u1LDocument,lpf.qteLDocument,
lpf.mtHtLApresRemiseGlobaleDocument,lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument,
lpf.mtTtcLApresRemiseGlobaleDocument from TaProforma pf left join pf.lignes lpf join pf.taInfosDocument i order by pf.codeDocument'', ''pf.dateDocument;
pf.taTiers.codeTiers;i.nomTiers;pf.codeDocument;lpf.taArticle.codeArticle;lpf.u1LDocument;lpf.qteLDocument;
lpf.mtHtLApresRemiseGlobaleDocument;lpf.mtTtcLApresRemiseGlobaleDocument-lpf.mtHtLApresRemiseGlobaleDocument;
lpf.mtTtcLApresRemiseGlobaleDocument'', ''Date proforma;Code tiers;Nom tiers;Code proforma;Code article;Unité;Qté;HT;TVA;TTC'',
 ''100;100;100;100;100;100;100;100;100;100'', ''proforma'', ''aa'', ''pf.codeDocument'', ''fr.legrain.editor.proforma.swt.multi'', 
''Proforma'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)
' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR7
^
drop procedure GESTION_INSERTION_VALEUR7
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR8
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles bon de commande';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1),''Liste des articles Bon de commande'', ''select bcd.dateDocument,bcd.taTiers.codeTiers,i.nomTiers,bcd.codeDocument,lbcd.taArticle.codeArticle,lbcd.u1LDocument,lbcd.qteLDocument,
lbcd.mtHtLApresRemiseGlobaleDocument,lbcd.mtTtcLApresRemiseGlobaleDocument-lbcd.mtHtLApresRemiseGlobaleDocument,
lbcd.mtTtcLApresRemiseGlobaleDocument from TaBoncde bcd left join bcd.lignes lbcd join bcd.taInfosDocument i order by bcd.codeDocument'', ''bcd.dateDocument;
bcd.taTiers.codeTiers;i.nomTiers;bcd.codeDocument;lbcd.taArticle.codeArticle;lbcd.u1LDocument;lbcd.qteLDocument;
lbcd.mtHtLApresRemiseGlobaleDocument;lbcd.mtTtcLApresRemiseGlobaleDocument-lbcd.mtHtLApresRemiseGlobaleDocument;
lbcd.mtTtcLApresRemiseGlobaleDocument'', ''Date bon de commande;Code tiers;Nom tiers;Code bon de commande;Code article;Unité;Qté;HT;TVA;TTC'', 
''100;100;100;100;100;100;100;100;100;100'', ''bonCde'', ''aa'', ''bcd.codeDocument'', ''fr.legrain.editor.boncde.swt.multi'', 
''Boncde'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)
' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR8
^
drop procedure GESTION_INSERTION_VALEUR8
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR9
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles avoir';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles avoir'', ''select av.dateDocument,av.taTiers.codeTiers,i.nomTiers,av.codeDocument,lav.taArticle.codeArticle,lav.u1LDocument,lav.qteLDocument,
lav.mtHtLApresRemiseGlobaleDocument,lav.mtTtcLApresRemiseGlobaleDocument-lav.mtHtLApresRemiseGlobaleDocument,
lav.mtTtcLApresRemiseGlobaleDocument from TaAvoir av left join av.lignes lav join av.taInfosDocument i order by av.codeDocument'', ''av.dateDocument;
av.taTiers.codeTiers;i.nomTiers;av.codeDocument;lav.taArticle.codeArticle;lav.u1LDocument;lav.qteLDocument;
lav.mtHtLApresRemiseGlobaleDocument;lav.mtTtcLApresRemiseGlobaleDocument-lav.mtHtLApresRemiseGlobaleDocument;
lav.mtTtcLApresRemiseGlobaleDocument'', ''Date avoir;Code tiers;Nom tiers;Code avoir;Code article;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100'',
''avoir'', ''aa'', ''av.codeDocument'', ''fr.legrain.editor.avoir.swt.multi'', ''Avoir'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR9
^
drop procedure GESTION_INSERTION_VALEUR9
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR10
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles apporteur';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles apporteur'', ''select app.dateDocument,app.taTiers.codeTiers,i.nomTiers,app.codeDocument,lapp.taArticle.codeArticle,lapp.u1LDocument,lapp.qteLDocument,
lapp.mtHtLApresRemiseGlobaleDocument,lapp.mtTtcLApresRemiseGlobaleDocument-lapp.mtHtLApresRemiseGlobaleDocument,
lapp.mtTtcLApresRemiseGlobaleDocument from TaApporteur app left join app.lignes lapp join app.taInfosDocument i order by app.codeDocument'', ''app.dateDocument;
app.taTiers.codeTiers;i.nomTiers;app.codeDocument;lapp.taArticle.codeArticle;lapp.u1LDocument;lapp.qteLDocument;
lapp.mtHtLApresRemiseGlobaleDocument;lapp.mtTtcLApresRemiseGlobaleDocument-lapp.mtHtLApresRemiseGlobaleDocument;
lapp.mtTtcLApresRemiseGlobaleDocument'', ''Date apporteur;Code tiers;Nom tiers;Code apporteur;Code article;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100'', 
''apporteur'', ''aa'', ''app.codeDocument'', ''fr.legrain.editor.apporteur.swt.multi'', ''Apporteur'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR10
^
drop procedure GESTION_INSERTION_VALEUR10
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR11
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles facture %';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles Facture (avec code facture)'', ''select f.dateDocument,f.taTiers.codeTiers,i.nomTiers,f.codeDocument,a.codeArticle,
fa.codeFamille,lf.u1LDocument,lf.qteLDocument,
lf.mtHtLApresRemiseGlobaleDocument,lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument,
lf.mtTtcLApresRemiseGlobaleDocument from TaFacture f 
left join f.lignes lf
join f.taInfosDocument i
left join lf.taArticle a
left join a.taFamille fa
where lf.taArticle is not null'', ''f.dateDocument;f.taTiers.codeTiers;i.nomTiers;f.codeDocument;a.codeArticle;fa.codeFamille;lf.u1LDocument;lf.qteLDocument;
lf.mtHtLApresRemiseGlobaleDocument;lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument;lf.mtTtcLApresRemiseGlobaleDocument'', 
''Date facture;Code tiers;NomTiers;Code facture;Code article;Famille;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100;100'', ''article'', ''aa'', 
''f.codeDocument'', ''fr.legrain.editor.facture.swt.multi'', ''Facture'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;s;f;f;f;f'', ''7;8;9;10'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR11
^
drop procedure GESTION_INSERTION_VALEUR11
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR12
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des articles facturés';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des articles facturés'', ''select f.dateDocument,f.taTiers.codeTiers,i.nomTiers,f.codeDocument,lf.taArticle.codeArticle,lf.u1LDocument,
lf.qteLDocument,lf.mtHtLApresRemiseGlobaleDocument,lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument,lf.mtTtcLApresRemiseGlobaleDocument 
from TaFacture f left join f.lignes lf join f.taInfosDocument i  order by f.dateDocument,f.taTiers.codeTiers,f.codeDocument,lf.taArticle.codeArticle,lf.u1LDocument'',
''f.dateDocument;f.taTiers.codeTiers;i.nomTiers;f.codeDocument;lf.taArticle.codeArticle;lf.u1LDocument;lf.qteLDocument;lf.mtHtLApresRemiseGlobaleDocument;
lf.mtTtcLApresRemiseGlobaleDocument-lf.mtHtLApresRemiseGlobaleDocument;lf.mtTtcLApresRemiseGlobaleDocument'', 
''Date facture;Code tiers;Nom tiers;Code facture;Code article;Unité;Qté;HT;TVA;TTC'', ''100;100;100;100;100;100;100;100;100;100'',
''tiers'', ''aa'', ''f.codeDocument'', ''fr.legrain.editor.facture.swt.multi'', ''Facture'', NULL, NULL, NULL, 0, NULL, ''d;s;s;s;s;s;f;f;f;f'', ''6;7;8;9'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR12
^
drop procedure GESTION_INSERTION_VALEUR12
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR13
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des devis';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des devis'', ''select d.codeDocument,d.dateDocument,d.taTiers.codeTiers,i.nomTiers,d.netTtcCalc from TaDevis d join d.taInfosDocument i order by d.codeDocument'', 
''d.codeDocument;d.dateDocument;d.taTiers.codeTiers;i.nomTiers;d.netTtcCalc'', ''Code;Date;Code tiers;Nom tiers;Montant TTC'',
''100;100;100;100;200;100;100'', ''DEVIS'', ''aa'', ''d.codeDocument'', ''fr.legrain.editor.devis.swt.multi'', ''Devis'', NULL, NULL, NULL, 0, NULL, ''s;d;s;s;f'', ''4'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR13
^
drop procedure GESTION_INSERTION_VALEUR13
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR14
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des bons de livraison';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des bons de livraison'', ''select bl.codeDocument,bl.dateDocument,bl.taTiers.codeTiers,i.nomTiers,bl.netTtcCalc 
from TaBonliv bl join bl.taInfosDocument i order by bl.codeDocument'', ''bl.codeDocument;bl.dateDocument;bl.taTiers.codeTiers;i.nomTiers;bl.netTtcCalc'', ''Code;Date;Code tiers;Nom tiers;Montant TTC'', 
''100;100;100;100;200'', ''BONLIV'', ''aa'', ''bl.codeDocument'', ''fr.legrain.editor.Bl.swt.multi'', ''BonLiv'', NULL, NULL, NULL, 0, NULL, ''s;d;s;s;f'', ''4'', ''JPQL'', null)' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR14
^
drop procedure GESTION_INSERTION_VALEUR14
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_VALEUR15
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
    query ='';
    delete from TA_VISUALISATION where lower(CODE_REQUETE) like 'liste des factures';
    begin
    query = 'INSERT INTO TA_VISUALISATION (ID, CODE_REQUETE, REQUETE, CHAMPS, TITRE_CHAMPS, TAILLE_CHAMPS, MODULE, IMPRESSION, IDENTIFIANT, ID_EDITEUR, ID_PLUGIN, CLAUSE_WHERE, GROUPBY, CLAUSE_HAVING, PROC, PARAM, TYPES_RETOUR, TOTAUX, QUERY_LANG, CLASSE_RECHERCHE)
VALUES (gen_id(num_id_visualisation,1), ''Liste des Factures'', ''select f.codeDocument,f.dateDocument,f.taTiers.codeTiers,i.nomTiers,f.netTtcCalc,f.export 
from TaFacture f join f.taInfosDocument i order by f.codeDocument'', ''f.codeDocument;f.dateDocument;f.taTiers.codeTiers;i.nomTiers;f.netTtcCalc;f.export'', 
''CODE_FACTURE;DATE_FACTURE;CODE_TIERS;NOM_TIERS;MTNETTTC;EXPORT'', ''100;100;100;100;200;100;100;100'', ''facture'', ''aa'', ''f.codeDocument'',
''fr.legrain.editor.facture.swt.multi'', ''Facture'', NULL, NULL, NULL, 0, NULL, ''s;d;s;s;f;b'', ''4'', ''JPQL'', ''fr.legrain.facture.recherche.TestFonctionRecherche'')' ;
        EXECUTE STATEMENT :query;
    end
    
END
^
EXECUTE PROCEDURE GESTION_INSERTION_VALEUR15
^
drop procedure GESTION_INSERTION_VALEUR15
^

CREATE or ALTER  PROCEDURE GESTION_INSERTION_16
as    
DECLARE VARIABLE QUERY VARCHAR(1000);
BEGIN
    QUERY='';
    if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-01'))then
    	INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('1', 'FR-01', 'Ain', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-02'))then
    	INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('2', 'FR-02', 'Aisne', 'FR-R', 'Picardie');
    if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-03'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('3', 'FR-03', 'Allier', 'FR-C', 'Auvergne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-04'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('4', 'FR-04', 'Alpes-de-Haute-Provence', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-05'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('5', 'FR-05', 'Hautes-Alpes', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-06'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('6', 'FR-06', 'Alpes-Maritimes', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-07'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('7', 'FR-07', 'Ardeche', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-08'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('8', 'FR-08', 'Ardennes', 'FR-G', 'Champagne-Ardenne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-09'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('9', 'FR-09', 'Ariege', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-10'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('10', 'FR-10', 'Aube', 'FR-G', 'Champagne-Ardenne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-11'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('11', 'FR-11', 'Aude', 'FR-K', 'Languedoc-Roussillon');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-12'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('12', 'FR-12', 'Aveyron', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-13'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('13', 'FR-13', 'Bouches-du-Rhone', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-14'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('14', 'FR-14', 'Calvados', 'FR-P', 'Basse-Normandie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-15'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('15', 'FR-15', 'Cantal', 'FR-C', 'Auvergne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-16'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('16', 'FR-16', 'Charente', 'FR-T', 'Poitou-Charentes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-17'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('17', 'FR-17', 'Charente-Maritime', 'FR-T', 'Poitou-Charentes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-18'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('18', 'FR-18', 'Cher', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-19'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('19', 'FR-19', 'Correze', 'FR-L', 'Limousin');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-20'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('20', 'FR-20', 'Corse', 'FR-H', 'Corse');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-21'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('21', 'FR-21', 'Cote-d''Or', 'FR-D', 'Bourgogne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-22'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('22', 'FR-22', 'Cotes-d''Armor', 'FR-E', 'Bretagne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-23'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('23', 'FR-23', 'Creuse', 'FR-L', 'Limousin');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-24'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('24', 'FR-24', 'Dordogne', 'FR-B', 'Aquitaine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-25'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('25', 'FR-25', 'Doubs', 'FR-I', 'Franche-Comte');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-26'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('26', 'FR-26', 'Drome', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-27'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('27', 'FR-27', 'Eure', 'FR-Q', 'Haute-Normandie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-28'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('28', 'FR-28', 'Eure-et-Loir', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-29'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('29', 'FR-29', 'Finistere', 'FR-E', 'Bretagne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-30'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('30', 'FR-30', 'Gard', 'FR-K', 'Languedoc-Roussillon');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-31'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('31', 'FR-31', 'Haute-Garonne', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-32'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('32', 'FR-32', 'Gers', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-33'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('33', 'FR-33', 'Gironde', 'FR-B', 'Aquitaine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-34'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('34', 'FR-34', 'Herault', 'FR-K', 'Languedoc-Roussillon');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-35'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('35', 'FR-35', 'Ille-et-Vilaine', 'FR-E', 'Bretagne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-36'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('36', 'FR-36', 'Indre', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-37'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('37', 'FR-37', 'Indre-et-Loire', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-38'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('38', 'FR-38', 'Isere', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-39'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('39', 'FR-39', 'Jura', 'FR-I', 'Franche-Comte');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-40'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('40', 'FR-40', 'Landes', 'FR-B', 'Aquitaine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-41'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('41', 'FR-41', 'Loir-et-Cher', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-42'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('42', 'FR-42', 'Loire', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-43'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('43', 'FR-43', 'Haute-Loire', 'FR-C', 'Auvergne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-44'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('44', 'FR-44', 'Loire-Atlantique', 'FR-R', 'Pays de la Loire');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-45'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('45', 'FR-45', 'Loiret', 'FR-F', 'Centre');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-46'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('46', 'FR-46', 'Lot', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-47'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('47', 'FR-47', 'Lot-et-Garonne', 'FR-B', 'Aquitaine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-48'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('48', 'FR-48', 'Lozere', 'FR-K', 'Languedoc-Roussillon');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-49'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('49', 'FR-49', 'Maine-et-Loire', 'FR-R', 'Pays de la Loire');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-50'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('50', 'FR-50', 'Manche', 'FR-P', 'Basse-Normandie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-51'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('51', 'FR-51', 'Marne', 'FR-G', 'Champagne-Ardenne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-52'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('52', 'FR-52', 'Haute-Marne', 'FR-G', 'Champagne-Ardenne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-53'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('53', 'FR-53', 'Mayenne', 'FR-R', 'Pays de la Loire');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-54'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('54', 'FR-54', 'Meurthe-et-Moselle', 'FR-M', 'Lorraine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-55'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('55', 'FR-55', 'Meuse', 'FR-M', 'Lorraine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-56'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('56', 'FR-56', 'Morbihan', 'FR-E', 'Bretagne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-57'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('57', 'FR-57', 'Moselle', 'FR-M', 'Lorraine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-58'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('58', 'FR-58', 'Nievre', 'FR-D', 'Bourgogne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-59'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('59', 'FR-59', 'Nord', 'FR-O', 'Nord-Pas-de-Calais');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-60'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('60', 'FR-60', 'Oise', 'FR-R', 'Picardie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-61'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('61', 'FR-61', 'Orne', 'FR-P', 'Basse-Normandie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-62'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('62', 'FR-62', 'Pas-de-Calais', 'FR-O', 'Nord-Pas-de-Calais');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-63'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('63', 'FR-63', 'Puy-de-Dome', 'FR-C', 'Auvergne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-64'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('64', 'FR-64', 'Pyrenees-Atlantiques', 'FR-B', 'Aquitaine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-65'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('65', 'FR-65', 'Hautes-Pyrenees', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-66'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('66', 'FR-66', 'Pyrenees-Orientales', 'FR-K', 'Languedoc-Roussillon');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-67'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('67', 'FR-67', 'Bas-Rhin', 'FR-A', 'Alsace');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-68'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('68', 'FR-68', 'Haut-Rhin', 'FR-A', 'Alsace');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-69'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('69', 'FR-69', 'Rhone', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-70'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('70', 'FR-70', 'Haute-Saone', 'FR-I', 'Franche-Comte');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-71'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('71', 'FR-71', 'Saone-et-Loire', 'FR-D', 'Bourgogne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-72'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('72', 'FR-72', 'Sarthe', 'FR-R', 'Pays de la Loire');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-73'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('73', 'FR-73', 'Savoie', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-74'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('74', 'FR-74', 'Haute-Savoie', 'FR-V', 'Rhone-Alpes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-75'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('75', 'FR-75', 'Paris', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-76'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('76', 'FR-76', 'Seine-Maritime', 'FR-Q', 'Haute-Normandie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-77'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('77', 'FR-77', 'Seine-et-Marne', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-78'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('78', 'FR-78', 'Yvelines', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-79'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('79', 'FR-79', 'Deux-Sevres', 'FR-T', 'Poitou-Charentes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-80'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('80', 'FR-80', 'Somme', 'FR-R', 'Picardie');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-81'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('81', 'FR-81', 'Tarn', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-82'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('82', 'FR-82', 'Tarn-et-Garonne', 'FR-N', 'Midi-Pyrenees');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-83'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('83', 'FR-83', 'Var', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-84'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('84', 'FR-84', 'Vaucluse', 'FR-U', 'Provence-Alpes-Cote d''Azur');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-85'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('85', 'FR-85', 'Vendee', 'FR-R', 'Pays de la Loire');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-86'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('86', 'FR-86', 'Vienne', 'FR-T', 'Poitou-Charentes');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-87'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('87', 'FR-87', 'Haute-Vienne', 'FR-L', 'Limousin');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-88'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('88', 'FR-88', 'Vosges', 'FR-M', 'Lorraine');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-89'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('89', 'FR-89', 'Yonne', 'FR-D', 'Bourgogne');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-90'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('90', 'FR-90', 'Territoire de Belfort', 'FR-I', 'Franche-Comte');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-91'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('91', 'FR-91', 'Essonne', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-92'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('92', 'FR-92', 'Hauts-de-Seine', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-93'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('93', 'FR-93', 'Seine-Saint-Denis', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-94'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('94', 'FR-94', 'Val-de-Marne', 'FR-J', 'Ile-de-France');
	if (not exists(select NUMDEP from TA_DEPARTEMENTS where CODEDEP='FR-95'))then
		INSERT INTO TA_DEPARTEMENTS (NUMDEP, CODEDEP, NOMDEP, CODEREGION, NOMREGION) VALUES ('95', 'FR-95', 'Val-d''Oise', 'FR-J', 'Ile-de-France');
END
^
EXECUTE PROCEDURE GESTION_INSERTION_16
^
drop procedure GESTION_INSERTION_16
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR10
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_R_PARAM_DOS_INTEL')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_R_PARAM_DOS_INTEL;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_R_PARAM_DOS_INTEL TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR10
^
drop procedure GESTION_CREATION_GENERATOR10
^


CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR11
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_PARAM_DOSS_INTEL')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_PARAM_DOSS_INTEL;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_PARAM_DOSS_INTEL TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR11
^
drop procedure GESTION_CREATION_GENERATOR11
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_GENERATOR12
as    
DECLARE VARIABLE QUERY VARCHAR(255);
BEGIN
query = '';
IF (not exists( SELECT a.RDB$GENERATOR_NAME FROM RDB$GENERATORS a
where a.RDB$GENERATOR_NAME='NUM_ID_TYPE_DONNEE')) THEN
  begin
    query = 'CREATE GENERATOR NUM_ID_TYPE_DONNEE;';
    EXECUTE STATEMENT :query;
    
    query = 'SET GENERATOR NUM_ID_TYPE_DONNEE TO 0;';
    EXECUTE STATEMENT :query;
  end
END
^
EXECUTE PROCEDURE GESTION_CREATION_GENERATOR12
^
drop procedure GESTION_CREATION_GENERATOR12
^





CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE16
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_TYPE_DONNEE')) THEN
  begin
    query='CREATE TABLE TA_TYPE_DONNEE (
    ID           DID3 NOT NULL /* DID3 = INTEGER NOT NULL */,
    TYPE_DONNEE  DLIB50NN /* DLIB50NN = VARCHAR(50) NOT NULL */,
    QUI_CREE     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF  TIMESTAMP DEFAULT ''NOW'',
    IP_ACCES     DID_FACULTATIF /* DID_FACULTATIF = INTEGER */,
    VERSION_OBJ  INTEGER,
    "VERSION"    NUM_VERSION /* NUM_VERSION = VARCHAR(20) */
)';
    EXECUTE STATEMENT :query;

    query='ALTER TABLE TA_TYPE_DONNEE ADD PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;
    
  end 
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE16
^
drop procedure GESTION_CREATION_TABLE16
^



CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE15
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_PARAM_DOSSIER_INTELLIGENT')) THEN
  begin
    query='CREATE TABLE TA_PARAM_DOSSIER_INTELLIGENT (
    ID           DID3 NOT NULL /* DID3 = INTEGER NOT NULL */,
    MOT          DLIB50NN NOT NULL /* DLIB50NN = VARCHAR(50) NOT NULL */,
    SQL          DLIB50 /* DLIB50 = VARCHAR(50) */,
    NB_ZONES     DID3 NOT NULL /* DID3 = INTEGER NOT NULL */,
    QUI_CREE     DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE   TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF    DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF  TIMESTAMP DEFAULT ''NOW'',
    IP_ACCES     DID_FACULTATIF /* DID_FACULTATIF = INTEGER */,
    VERSION_OBJ  INTEGER,
    "VERSION"    NUM_VERSION /* NUM_VERSION = VARCHAR(20) */
)';
    EXECUTE STATEMENT :query;

    query='ALTER TABLE TA_PARAM_DOSSIER_INTELLIGENT ADD PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;
    
  end 
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE15
^
drop procedure GESTION_CREATION_TABLE15
^

CREATE or ALTER  PROCEDURE GESTION_CREATION_TABLE14
as    
DECLARE VARIABLE QUERY VARCHAR(5000);
BEGIN
query ='';
IF (not exists( SELECT  a.RDB$RELATION_NAME
FROM RDB$RELATIONS a
where a.RDB$RELATION_NAME='TA_R_PARAM_DOS_INTEL')) THEN
  begin
    query='CREATE TABLE TA_R_PARAM_DOS_INTEL (
    ID              INTEGER NOT NULL,
    ID_PARAM        DID_FACULTATIF /* DID_FACULTATIF = INTEGER */,
    ID_TYPE_DONNEE  DID_FACULTATIF /* DID_FACULTATIF = INTEGER */,
    QUI_CREE        DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_CREE      TIMESTAMP DEFAULT ''NOW'',
    QUI_MODIF       DLIB50 /* DLIB50 = VARCHAR(50) */,
    QUAND_MODIF     TIMESTAMP DEFAULT ''NOW'',
    "VERSION"       NUM_VERSION /* NUM_VERSION = VARCHAR(20) */,
    IP_ACCES        DLIB50NN DEFAULT 0 /* DLIB50NN = VARCHAR(50) NOT NULL */,
    VERSION_OBJ     INTEGER
)';
    EXECUTE STATEMENT :query;

    query='ALTER TABLE TA_R_PARAM_DOS_INTEL ADD CONSTRAINT PK_TA_R_PARAM_DOS_INTEL PRIMARY KEY (ID)';
    EXECUTE STATEMENT :query;
    
    query='ALTER TABLE TA_R_PARAM_DOS_INTEL ADD CONSTRAINT FK_TA_R_PARAM_DOS_INTEL_1 FOREIGN KEY (ID_PARAM) REFERENCES TA_PARAM_DOSSIER_INTELLIGENT (ID)';
    EXECUTE STATEMENT :query;
    
    query='ALTER TABLE TA_R_PARAM_DOS_INTEL ADD CONSTRAINT FK_TA_R_PARAM_DOS_INTEL_2 FOREIGN KEY (ID_TYPE_DONNEE) REFERENCES TA_TYPE_DONNEE (ID)';
    EXECUTE STATEMENT :query;
    
  end 
END
^
EXECUTE PROCEDURE GESTION_CREATION_TABLE14
^
drop procedure GESTION_CREATION_TABLE14
^
