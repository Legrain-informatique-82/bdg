; ----------------------------------------
; - Programme    : GestionCommerciale    -
; - Version      : 1.0                   -
; - Copyright    : Le Grain S.A. 2005    -
; - NSIS version : 2.12                  -
; - Auteur       : Nicolas               -
; ----------------------------------------

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "GestionCommerciale"
!define PRODUCT_VERSION "1.0"
!define PRODUCT_PUBLISHER "Le Grain"
!define PRODUCT_WEB_SITE "http://www.legrain.fr"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"

; MUI 1.67 compatible ------
;!include "MUI.nsh"
!include "XPUI.nsh"


; MUI Settings
;!define MUI_HEADERIMAGE                                                                          
;  ;!define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\win.bmp"
;  !define MUI_HEADERIMAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Header\nsis.bmp" ; optional
;;!define MUI_HEADERIMAGE_BITMAP "modern-header 2.bmp"
;!define MUI_COMPONENTSPAGE_CHECKBITMAP "${NSISDIR}\Contrib\Graphics\Checks\simple-round2.bmp"
;  ;!define MUI_WELCOMEFINISHPAGE_BITMAP "${NSISDIR}\Contrib\Graphics\Wizard\orange.bmp"

;!define MUI_ABORTWARNING
;!define XPUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
;!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; ----------------------------- Liste des pages ----------------------------------                       


!define XPUI_TEXT_BGCOLOR 0xBBBBBB
!define XPUI_TEXT_COLOR 0x000000
;!define XPUI_TEXT_LIGHTCOLOR 0xFFFFFF
!define XPUI_BOTTOMIMAGE true

!define XPUI_BRANDINGTEXT_COLOR_FG 0x000000

!define XPUI_BRANDINGTEXT_COLOR_BG 0x000005


; Welcome page
!define XPUI_WELCOMEPAGE_TITLE "GestionCommerciale"
!define XPUI_WELCOMEPAGE_SUBTITLE ""
!insertmacro XPUI_PAGE_WELCOME
; License page                     
!define XPUI_LICENSEPAGE_CHECKBOX "1"
!insertmacro XPUI_PAGE_LICENSE "licence.txt"
;Page de selection des composants
!insertmacro XPUI_PAGE_COMPONENTS
; Directory page
!insertmacro XPUI_PAGE_DIRECTORY
; Instfiles page
!insertmacro XPUI_PAGE_INSTFILES
; Finish page
!define XPUI_FINISHPAGE_RUN "$INSTDIR\GestionCommerciale.exe"
!insertmacro XPUI_PAGE_FINISH

; Uninstaller pages
!insertmacro XPUI_UNPAGE_INSTFILES

; Language files
!insertmacro XPUI_LANGUAGE "French"
;!insertmacro XPUI_LANGUAGE "English"

; MUI end ------

;------------------------------- Description des sections ---------------------------
LangString DESC_SEC_TRANSDOC ${LANG_FRENCH} "Gestion Commerciale."
LangString DESC_SEC_FIREBIRD ${LANG_FRENCH} "Installation de la base de données Firebird."
LangString DESC_SEC_JAVA ${LANG_FRENCH} "Installation de la machine virtuelle java."

;-------------------------------- Global ---------------------------------------------
Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
XPStyle on
OutFile "setup_XPUI.exe"
InstallDir "$PROGRAMFILES\GestCom"
ShowInstDetails show
ShowUnInstDetails show
Brandingtext "Le Grain Informatique (NSIS v2.12)"
;"NullSoft Install System v2.12"

;------------------------------- Sections -------------------------------------------
Section "GestCom" SEC_TRANSDOC
  SectionIn RO ;toujours coché
  SetOutPath "$INSTDIR"
  SetOverwrite ifnewer
  ;CreateDirectory "$INSTDIR\Divers\importations"
  SetOutPath "$INSTDIR"
  File /r "C:\Projet\Java\Eclipse\GestionCommerciale\GestionCommerciale\dist\win32.win32.x86\"
  ;SetOutPath "$INSTDIR\bin"
  ;File "..\Sources\TransDocW.exe"   
SectionEnd

Section "Firebird" SEC_INTERBASE
  ;SectionIn RO ;toujours coché
  AddSize 9532 ;package WISE -qABCDEF
  DetailPrint "Installation de la base de données Firebird : "
  DetailPrint "Veuillez patienter ..."
  SetDetailsPrint none
  ExecWait '$PLUGINSDIR\Firebird-1.5.2.4731-Win32.exe /verysilent' $0 ;
  SetDetailsPrint both
  DetailPrint "Installation de la base de données Interbase terminée."
  ;MessageBox MB_OK "retour interbase $0"
  ;Exec "'$PROGRAMFILES\Borland\Interbase\BIN\isql -i $INSTDIR\transfact_vide.sql'"
SectionEnd

Section "Java" SEC_JAVA
  ;SectionIn RO ;toujours coché
  AddSize 131000 ;jre 1.5 update 6
  DetailPrint "Installation de la machine virtuelle Java : "
  DetailPrint "Veuillez patienter ..."
  SetDetailsPrint none
  ExecWait "$PLUGINSDIR\jre-1_5_0_06-windows-i586-p.exe /S /v/qn" $1
  SetDetailsPrint both
  DetailPrint "Installation de la machine virtuelle Java terminée."
  ;MessageBox MB_OK "retour java $1"           ; meme version deja installé = 1603
SectionEnd

;Insertion des description des sections
!insertmacro XPUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro XPUI_DESCRIPTION_TEXT ${SEC_TRANSDOC} $(DESC_SEC_TRANSDOC)
  !insertmacro XPUI_DESCRIPTION_TEXT ${SEC_INTERBASE} $(DESC_SEC_FIREBIRD)
  !insertmacro XPUI_DESCRIPTION_TEXT ${SEC_JAVA} $(DESC_SEC_JAVA)
!insertmacro XPUI_FUNCTION_DESCRIPTION_END

Section -AdditionalIcons
  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  CreateDirectory "$SMPROGRAMS\GestCom"
  CreateShortCut "$SMPROGRAMS\GestCom\Website.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\GestCom\Uninstall.lnk" "$INSTDIR\uninst.exe"
  SetOutPath "$INSTDIR"
  CreateShortCut "$SMPROGRAMS\GestCom\GestCom.lnk" "$INSTDIR\GestionCommerciale.exe"
  CreateShortCut "$DESKTOP\GestCom.lnk" "$INSTDIR\bin\GestionCommerciale.exe"     
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
  File /oname=$PLUGINSDIR\jre-1_5_0_06-windows-i586-p.exe "jre-1_5_0_06-windows-i586-p.exe"
  File /oname=$PLUGINSDIR\Firebird-1.5.2.4731-Win32.exe "Firebird-1.5.2.4731-Win32.exe"
FunctionEnd
               
Function un.onUninstSuccess
  HideWindow
  MessageBox MB_ICONINFORMATION|MB_OK "$(^Name) a été désinstallé avec succès de votre ordinateur."
FunctionEnd

Function un.onInit
  MessageBox MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2 "Êtes-vous certains de vouloir désinstaller totalement $(^Name) et tous ses composants ?" IDYES +2
  Abort
FunctionEnd

;---------------------------------------- UnInstall ------------------------------------------------------------
Section Uninstall
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
  Delete "$INSTDIR\uninst.exe"

  Delete "$SMPROGRAMS\GestCom\Uninstall.lnk"
  Delete "$SMPROGRAMS\GestCom\Website.lnk"
  Delete "$SMPROGRAMS\GestCom\GestCom.lnk"
  Delete "$DESKTOP\GestCom.lnk"

  RMDir "$SMPROGRAMS\GestCom"
  RMDir /r "$INSTDIR"
  ;RMDir "$INSTDIR"

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  SetAutoClose true
SectionEnd