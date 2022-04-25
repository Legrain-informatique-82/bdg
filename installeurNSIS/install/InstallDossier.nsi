; ----------------------------------------
; - Programme    : bureau de gestion     -
; - Version      : 1.0                   -
; - Copyright    : Le Grain S.A. 2006    -
; - NSIS version : 2.12                  -
; - Auteur       : Nicolas               -
; ----------------------------------------

;"Constantes"
!define DOSSIER_A_SUPPRIMER "c:\lgrdoss\BureauDeGestion\dossier"
!define DOSSIER_A_COPIER "c:\lgrdoss\BureauDeGestion\dossier"

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "Dossier bureau de gestion"
!define PRODUCT_VERSION "1.0"
!define PRODUCT_VERSION_DETAIL "1.0.0.0"
!define PRODUCT_PUBLISHER "Le Grain Informatique"
!define PUBLISHER_WEB_SITE "http://www.legrain.fr"
!define PRODUCT_WEB_SITE "http://www.logiciel-gestion.fr"
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

; ----------------------------- Liste des pages ----------------------------------                       

; Welcome page
;!insertmacro MUI_PAGE_WELCOME
; License page                     
;!define MUI_LICENSEPAGE_CHECKBOX "1"
;!insertmacro MUI_PAGE_LICENSE "licence.txt"
;Page de selection des composants
!insertmacro MUI_PAGE_COMPONENTS
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
;!define MUI_FINISHPAGE_RUN "$INSTDIR\GestionCommerciale.exe"
!insertmacro MUI_PAGE_FINISH

; Language files
!insertmacro MUI_LANGUAGE "French"

; MUI end ------

;------------------------------- Description des sections ---------------------------
LangString DESC_SEC_BUREAU_DE_GESTION ${LANG_FRENCH} "Dossier"

;-------------------------------- Global ---------------------------------------------
Name "${PRODUCT_NAME}" ;"${PRODUCT_NAME} ${PRODUCT_VERSION}"
XPStyle on
OutFile "InstallDossier.exe"
InstallDir "c:\lgrdoss\BureauDeGestion"
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
Section "Dossier bureau de gestion" SEC_BUREAU_DE_GESTION
  SectionIn RO ;toujours coché
  SetOutPath "$INSTDIR"
  SetOverwrite ifnewer
  SetOutPath "$INSTDIR"
  ;Delete "fichier"
  RMDir /r "${DOSSIER_A_SUPPRIMER}"
  File /r "${DOSSIER_A_COPIER}"
SectionEnd

;Insertion des description des sections
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC_BUREAU_DE_GESTION} $(DESC_SEC_BUREAU_DE_GESTION)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

;----------------------------------------- Fonctions --------------------------------------------------------- 
Function .onInit
  InitPluginsDir 
FunctionEnd
               
