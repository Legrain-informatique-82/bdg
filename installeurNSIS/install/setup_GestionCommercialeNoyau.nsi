; ----------------------------------------
; - Programme    : bureau de gestion     -
; - Version      : 1.0                   -
; - Copyright    : Le Grain S.A. 2006    -
; - NSIS version : 2.12                  -
; - Auteur       : Nicolas               -
; ----------------------------------------
!addplugindir "pluginsNSIS" ; Répertoire contenant les plugins NSIS non standards

!include LogicLib.nsh ; Instructions logiques
!include "Locate.nsh"

;"Constantes"
!define REPERTOIRE_EXPORT_RCP "C:\Projet\Java\Eclipse\GestionCommerciale\installeurNSIS\install\Build_GestionCommercialeNoyau\"
!define REPERTOIRE_EXPORT_RCP_UTIL "C:\Projet\Java\Eclipse\GestionCommerciale\installeurNSIS\install\utilitaires\"
!define NOM_REPERTOIRE_UTIL_RESTAURATION "restauration"
!define INSTALLEUR_JRE "jre-6u1-windows-i586-p.exe"
!define INSTALLEUR_FIREBIRD "Firebird-2.0.2.12855-1-Win32.exe"
!define SCRIPT_ADD_DB_USER "addDbUser.bat"
!define SCRIPT_EXEC_SQL "updateDB.sql"
!define FIREBIRD_SERVICE_NAME "FirebirdServerDefaultInstance"
!define FIREBIRD_GUARDIAN_SERVICE_NAME "FirebirdGuardianDefaultInstance"
!define FIREBIRD_PROCESS_NAME "fbserver.exe"
!define FIREBIRD_GUARDIAN_PROCESS_NAME "fbguard.exe"
!define FIREBIRD_BIN_DIRECTORY "C:\Program Files\Firebird\Firebird_2_0\bin"
!define NOM_REPERTOIRE_SORTIE_UTIL "util"

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "Bureau de gestion"
!define PRODUCT_VERSION "1.0"
!define PRODUCT_VERSION_DETAIL "1.0.6.0"
!define PRODUCT_PUBLISHER "Le Grain Informatique"
!define PUBLISHER_WEB_SITE "http://www.legrain.fr"
!define PRODUCT_WEB_SITE "http://www.logiciel-gestion.fr"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"

; MUI 1.67 compatible ------
!include "MUI.nsh"

; MUI Settings
!define MUI_HEADERIMAGE                                                                          
  ;!define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\win.bmp"
  !define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\nsis.bmp" ; optional
;!define MUI_HEADERIMAGE_BITMAP "modern-header 2.bmp"
!define MUI_COMPONENTSPAGE_CHECKBITMAP "${NSISDIR}\Contrib\Graphics\Checks\simple-round2.bmp"
  ;!define MUI_WELCOMEFINISHPAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Wizard\orange.bmp"

!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; ----------------------------- Liste des pages ----------------------------------                       

; Welcome page
!insertmacro MUI_PAGE_WELCOME
; License page                     
!define MUI_LICENSEPAGE_CHECKBOX "1"
!insertmacro MUI_PAGE_LICENSE "licence.txt"
;Page de selection des composants
!insertmacro MUI_PAGE_COMPONENTS
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
!define MUI_FINISHPAGE_RUN "$INSTDIR\GestionCommerciale.exe"
!insertmacro MUI_PAGE_FINISH

; Uninstaller pages
!insertmacro MUI_UNPAGE_INSTFILES

; Language files
!insertmacro MUI_LANGUAGE "French"

; MUI end ------

;------------------------------- Description des sections ---------------------------
LangString DESC_SEC_BUREAU_DE_GESTION ${LANG_FRENCH} "Bureau de gestion."
LangString DESC_SEC_FIREBIRD ${LANG_FRENCH} "Installation de la base de données Firebird."
LangString DESC_SEC_JAVA ${LANG_FRENCH} "Installation de la machine virtuelle java."

;-------------------------------- Global ---------------------------------------------
Name "${PRODUCT_NAME}" ;"${PRODUCT_NAME} ${PRODUCT_VERSION}"
XPStyle on
OutFile "Setups\setup_BdG_Noyau.exe"
InstallDir "$PROGRAMFILES\Lgr\BureauDeGestion"
ShowInstDetails show
ShowUnInstDetails show
Brandingtext "Le Grain Informatique (NSIS v2.12)"
;"NullSoft Install System v2.12"

;Informations pour l'onglet "version" des propriétés du setup
VIProductVersion "${PRODUCT_VERSION_DETAIL}"
VIAddVersionKey /LANG=${LANG_FRENCH} ProductName "${PRODUCT_NAME}"
VIAddVersionKey ProductVersion "${PRODUCT_VERSION}"
VIAddVersionKey /LANG=${LANG_FRENCH} CompanyName "${PRODUCT_PUBLISHER}"
VIAddVersionKey /LANG=${LANG_FRENCH} CompanyWebsite "${PUBLISHER_WEB_SITE}"
VIAddVersionKey /LANG=${LANG_FRENCH} FileVersion ""
VIAddVersionKey /LANG=${LANG_FRENCH} FileDescription "${PRODUCT_NAME}"
VIAddVersionKey /LANG=${LANG_FRENCH} LegalCopyright "${PRODUCT_PUBLISHER}"

;------------------------------- Sections -------------------------------------------
Section "Bureau de gestion" SEC_BUREAU_DE_GESTION
  SectionIn RO ;toujours coché
  SetOutPath "$INSTDIR"
  SetOverwrite ifnewer
  ;CreateDirectory "$INSTDIR\Divers\importations"
  SetOutPath "$INSTDIR"
  File /r "${REPERTOIRE_EXPORT_RCP}"
  ;SetOutPath "$INSTDIR\bin"
  ;File "..\Sources\TransDocW.exe"   
SectionEnd

Section "-Utilitaire restauration"
  SectionIn RO ;toujours coché
  SetOutPath "$INSTDIR\${NOM_REPERTOIRE_SORTIE_UTIL}\${NOM_REPERTOIRE_UTIL_RESTAURATION}"
  SetOverwrite ifnewer
  ;CreateDirectory "$INSTDIR\{$NOM_REPERTOIRE_SORTIE_UTIL}\{$NOM_REPERTOIRE_UTIL_RESTAURATION}"
  File /r "${REPERTOIRE_EXPORT_RCP_UTIL}\${NOM_REPERTOIRE_UTIL_RESTAURATION}\"
  SetOutPath "$INSTDIR"
  ;SetOutPath "$INSTDIR\bin"
  ;File "..\Sources\TransDocW.exe"   
SectionEnd

Section /o "Java" SEC_JAVA
  ;SectionIn RO ;toujours coché
  AddSize 70000 ;jre 1.6 update 1
  DetailPrint "Installation de la machine virtuelle Java : "
  DetailPrint "Veuillez patienter ..."
  SetDetailsPrint none
  ExecWait "$PLUGINSDIR\install_jre.exe /passive /qn" $1
  SetDetailsPrint both
  DetailPrint "Installation de la machine virtuelle Java terminée."
  ;MessageBox MB_OK "retour java $1"           ; meme version deja installé = 1603
SectionEnd

Section "Firebird" SEC_INTERBASE
  ;SectionIn RO ;toujours coché
  Call supprFirebird  
  AddSize 13600 ;package WISE -qABCDEF
  DetailPrint "Installation de la base de données Firebird : "
  DetailPrint "Veuillez patienter ..."
  SetDetailsPrint none
  Call installFirebird
  SetDetailsPrint both
  DetailPrint "Installation de la base de données Interbase terminée."
  ;MessageBox MB_OK "retour interbase $0"
  ;Exec "'$PROGRAMFILES\Borland\Interbase\BIN\isql -i $INSTDIR\transfact_vide.sql'"
SectionEnd

Section "-addDatabaseUser" ;section cachée et toujours exécutée
    Call addAttenteLancementFirebird
    ;MessageBox MB_OK 'stop'
	DetailPrint "Ajout d'un utilisateur pour la base de données."
    SetDetailsPrint none
	Call addDatabaseUser
    SetDetailsPrint both
SectionEnd

Section "-updateDB" ;section cachée et toujours exécutée
    IfFileExists c:\lgrdoss\BureauDeGestion\dossier\Bd\GEST_COM.FDB Maj NotMaj
    Maj:
    DetailPrint "Mise à jour de la base de données."
    SetDetailsPrint none
    ;Exec "${FIREBIRD_BIN_DIRECTORY}\isql.exe -i ${PLUGINSDIR}\script_updateDB.sql"
    ;Exec '"C:\Program Files\Firebird\Firebird_2_0\bin\isql.exe" -i $PLUGINSDIR\script_updateDB.sql'
    SetDetailsPrint both
    Goto End
    NotMaj:
    DetailPrint "Pas de base de données a mettre à jour."
    End:
    ;MessageBox MB_OK 'stop'
SectionEnd


;Insertion des description des sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC_BUREAU_DE_GESTION} $(DESC_SEC_BUREAU_DE_GESTION)
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC_INTERBASE} $(DESC_SEC_FIREBIRD)
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC_JAVA} $(DESC_SEC_JAVA)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

Section -AdditionalIcons
  WriteIniStr "$INSTDIR\${PRODUCT_PUBLISHER}.url" "InternetShortcut" "URL" "${PUBLISHER_WEB_SITE}"
  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateDirectory "$SMPROGRAMS\Bureau de gestion"
  CreateShortCut "$SMPROGRAMS\Bureau de gestion\Le Grain.lnk" "$INSTDIR\${PRODUCT_PUBLISHER}.url"
  CreateShortCut "$SMPROGRAMS\Bureau de gestion\logiciel-gestion.fr.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\Bureau de gestion\Désinstallation.lnk" "$INSTDIR\uninst.exe"
  SetOutPath "$INSTDIR"
  CreateShortCut "$SMPROGRAMS\Bureau de gestion\Bureau de gestion.lnk" "$INSTDIR\GestionCommerciale.exe"
  CreateShortCut "$DESKTOP\Bureau de gestion.lnk" "$INSTDIR\GestionCommerciale.exe"     
 
  CreateShortCut "$SMPROGRAMS\Bureau de gestion\Restauration.lnk" "$INSTDIR\${NOM_REPERTOIRE_SORTIE_UTIL}\${NOM_REPERTOIRE_UTIL_RESTAURATION}\RestaurationSauvegarde.exe"
  CreateShortCut "$DESKTOP\Restauration.lnk" "$INSTDIR\${NOM_REPERTOIRE_SORTIE_UTIL}\${NOM_REPERTOIRE_UTIL_RESTAURATION}\RestaurationSauvegarde.exe"
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString" "$INSTDIR\uninst.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "Publisher" "${PRODUCT_PUBLISHER}"
SectionEnd

;----------------------------------------- Fonctions --------------------------------------------------------- 
Function .onInit
  InitPluginsDir 
  File /oname=$PLUGINSDIR\install_jre.exe "${INSTALLEUR_JRE}"
  File /oname=$PLUGINSDIR\install_firebird.exe "${INSTALLEUR_FIREBIRD}"
  File /oname=$PLUGINSDIR\script_AddBdUser.bat "${SCRIPT_ADD_DB_USER}"
  ;File /oname=$PLUGINSDIR\script_updateDB.sql "${SCRIPT_EXEC_SQL}"
FunctionEnd
               
Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) a été désinstallé avec succès de votre ordinateur."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Êtes-vous certains de vouloir désinstaller totalement $(^Name) et tous ses composants ?" IDYES +2
  Abort
FunctionEnd

Function addDatabaseUser
  Exec "$PLUGINSDIR\script_AddBdUser.bat"
FunctionEnd

Function supprFirebird
  ;MessageBox MB_OK 'stop1'
  ${locate::Open} "$PROGRAMFILES\Firebird" "/F=1 /D=0 /M=unins000.exe" $0
  ${If} $0 <> 0
    ;MessageBox MB_OK '$PROGRAMFILES\Firebird\Firebird_1_5'
    ;MessageBox MB_OK '$0'
    ${locate::Find} "$0" $1 $2 $3 $4 $5 $6
    ;MessageBox MB_OK '$1'
    ExecWait '$1 /VERYSILENT';
    ;ExecWait '$PROGRAMFILES\Firebird\Firebird_1_5\unins000.exe';
    RMDir /r "$PROGRAMFILES\Firebird\"
    ;MessageBox MB_OK 'stop2'
  ${EndIf}
FunctionEnd

Function installFirebird
  Version::IsWindowsPlatformNT
  Pop $0
  StrCmp $0 "1" ItIsWindowsPlatformNT IsIsNotWindowsPlatformNT
 
  ItIsWindowsPlatformNT: 
  nsSCM::QueryStatus "${FIREBIRD_SERVICE_NAME}"
  Pop $0
  Pop $1 
  ${If} $1 <> 4
    Goto Install
  ${Else}
  ;MessageBox MB_OK "b"
    Goto End
  ${EndIf}

  IsIsNotWindowsPlatformNT: ; Win9x
  FindProcDLL::FindProc "${FIREBIRD_PROCESS_NAME}"
  Pop $R0
  ${If} $R0 <> 1
    Goto Install
  ${Else}
    Goto End
  ${EndIf}

  Install:
    ExecWait '$PLUGINSDIR\install_firebird.exe /verysilent /NOICONS' $0 ;
    
  End:
FunctionEnd

Function addAttenteLancementFirebird
  ; Cette fonction ne s'occupe pas de lancer firebird, elle attend que l'installeur de firebird le fasse
  ; inclure LogicLib.nsh pour les instructions logiques (if, while, ...)
  ; Utilise Version pour détecter la version de Windows (http://nsis.sourceforge.net/Detect_Windows_Version)
  ; Utilise NsSCM_plug-in pour le controle des services windows (http://nsis.sourceforge.net/NsSCM_plug-in)
  ; Utilise FindProcDLL pour savoir si un processus est actif (http://nsis.sourceforge.net/FindProcDLL_plug-in)
  
  Version::IsWindowsPlatformNT
  Pop $0
  StrCmp $0 "1" ItIsWindowsPlatformNT IsIsNotWindowsPlatformNT
 
  ItIsWindowsPlatformNT: ;Attente du lancement du service
  DetailPrint "Plateforme WinNT"
  
  DetailPrint "Lancement du service Firebird ..."
  nsSCM::Start "${FIREBIRD_GUARDIAN_SERVICE_NAME}"
  nsSCM::QueryStatus "${FIREBIRD_SERVICE_NAME}"
  Pop $0
  Pop $1 
  ${While} $1 <> 4
    Sleep 1000
    nsSCM::QueryStatus "${FIREBIRD_SERVICE_NAME}"
    Pop $0
    Pop $1 
  ${EndWhile}
  DetailPrint "Service Firebird OK"
  Goto End
  
  IsIsNotWindowsPlatformNT: ; Win9x
  DetailPrint "Plateforme Win9x"
  
  DetailPrint "Lancement du processus Firebird ..."
  Exec "${FIREBIRD_BIN_DIRECTORY}\${FIREBIRD_PROCESS_NAME}"
  FindProcDLL::FindProc "${FIREBIRD_PROCESS_NAME}"
  Pop $R0
  ${While} $R0 <> 1
    Sleep 1000
    FindProcDLL::FindProc "${FIREBIRD_PROCESS_NAME}"
    Pop $R0
  ${EndWhile}
  DetailPrint "Processus Firebird OK"
  
  End:
FunctionEnd

;---------------------------------------- UnInstall ------------------------------------------------------------
Section Uninstall
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
  Delete "$INSTDIR\uninst.exe"

  Delete "$SMPROGRAMS\Bureau de gestion\Désinstallation.lnk"
  Delete "$SMPROGRAMS\Bureau de gestion\Le Grain.lnk"
  Delete "$SMPROGRAMS\Bureau de gestion\logiciel-gestion.fr.lnk"
  Delete "$SMPROGRAMS\Bureau de gestion\Bureau de gestion.lnk"
  Delete "$DESKTOP\Bureau de gestion.lnk"
  
  Delete "$DESKTOP\Restauration.lnk"
  Delete "$SMPROGRAMS\Bureau de gestion\Restauration.lnk"
  

  RMDir "$SMPROGRAMS\Bureau de gestion"
  RMDir /r "$INSTDIR"
  ;RMDir "$INSTDIR"

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  SetAutoClose true
SectionEnd
