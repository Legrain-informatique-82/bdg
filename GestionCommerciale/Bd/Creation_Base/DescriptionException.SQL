
CREATE EXCEPTION E_XAKADRESSE_EMAIL 'l''adresse mail doit être unique !';

CREATE EXCEPTION E_XAKADRESSE_WEB 'l''adresse du site web doit être unique !';

CREATE EXCEPTION E_XAKCODE_ARTICLE 'Le code article doit être unique';

CREATE EXCEPTION E_XAKCODE_BONLIV 'Le bon de livraison doit être unique';

CREATE EXCEPTION E_XAKCODE_C_PAIEMENT 'Le code de condition de paiement doit être unique';

CREATE EXCEPTION E_XAKCODE_DEVIS 'Le code devis doit être unique';

CREATE EXCEPTION E_XAKCODE_ENTREPRISE 'Le nom de l''entreprise doit être unique !';

CREATE EXCEPTION E_XAKCODE_FACTURE 'Le code facture doit être unique';

CREATE EXCEPTION E_XAKCODE_FAMILLE 'Le code famille doit être unique';

CREATE EXCEPTION E_XAKCODE_TIERS 'Le code Tiers doit être unique';

CREATE EXCEPTION E_XAKCODE_TVA 'Le code tva doit être unique';

CREATE EXCEPTION E_XAKCODE_T_ADR 'Le code de type adresse doit être unique !';

CREATE EXCEPTION E_XAKCODE_T_BANQUE 'Le type de compte bancaire doit être unique';

CREATE EXCEPTION E_XAKCODE_T_CIVILITE 'La civilité doit être unique';

CREATE EXCEPTION E_XAKCODE_T_DOC 'Le code de type de document doit être unique';

CREATE EXCEPTION E_XAKCODE_T_EMAIL 'Le type d''email doit être unique';

CREATE EXCEPTION E_XAKCODE_T_ENTITE 'Le code type entité doit être unique !';

CREATE EXCEPTION E_XAKCODE_T_LIENS 'le type de liens doit être unique !';

CREATE EXCEPTION E_XAKCODE_T_LIGNE 'Le code type de ligne doit être unique';

CREATE EXCEPTION E_XAKCODE_T_PAIEMENT 'Le code type de paiement doit être unique';

CREATE EXCEPTION E_XAKCODE_T_TARIF 'le type de tarif doit être unique !';

CREATE EXCEPTION E_XAKCODE_T_TEL 'Ce code de type téléphone doit être unique !';

CREATE EXCEPTION E_XAKCODE_T_TIERS 'Le code Type de tiers doit être unique';

CREATE EXCEPTION E_XAKCODE_T_WEB 'Le type de site web doit être unique';

CREATE EXCEPTION E_XAKCODE_UNITE 'Le code unité doit être unique';

CREATE EXCEPTION E_XAKCOMPTE_BANQUE 'Le compte doit être unique';

CREATE EXCEPTION E_XAKID_ADRESSE 'Cette adresse n''existe pas dans la liste des adresses disponibles !';

CREATE EXCEPTION E_XAKID_ADRESSE_L 'Cette adresse de livraison n''existe pas !';

CREATE EXCEPTION E_XAKID_ARTICLE 'Ce code article n''existe pas dans la liste des codes article disponibles !';

CREATE EXCEPTION E_XAKID_AVOIR 'Cet avoir n''existe pas !';

CREATE EXCEPTION E_XAKID_BONCDE 'Ce bon de commande n''existe pas !';

CREATE EXCEPTION E_XAKID_BONLIV 'Ce bon de livraison n''existe pas !';

CREATE EXCEPTION E_XAKID_COMMERCIAL 'Ce commercial n''existe pas dans la liste des tiers disponibles !';

CREATE EXCEPTION E_XAKID_C_PAIEMENT 'Cette condition de paiement n''existe pas !';

CREATE EXCEPTION E_XAKID_DEVIS 'Ce devis n''existe pas !';

CREATE EXCEPTION E_XAKID_EMAIL 'Cette adresse mail n''existe pas !';

CREATE EXCEPTION E_XAKID_ENTREPRISE 'Cette entreprise n''existe pas !';

CREATE EXCEPTION E_XAKID_FACTURE 'Cette facture n''existe pas !';

CREATE EXCEPTION E_XAKID_FAMILLE 'Cette famille n''existe pas dans la liste des familles d''article disponibles !';

CREATE EXCEPTION E_XAKID_I_BANQUE 'Cette banque n''est pas décrite !';

CREATE EXCEPTION E_XAKID_I_COMMENTAIRE 'Ce commentaire n''existe pas !';

CREATE EXCEPTION E_XAKID_L_AVOIR 'Cette ligne d''avoir n''existe pas !';

CREATE EXCEPTION E_XAKID_L_BONCDE 'Cette ligne de bon de commande n''existe pas !';

CREATE EXCEPTION E_XAKID_L_BONLIV 'Cette ligne de bon de livraison n''existe pas !';

CREATE EXCEPTION E_XAKID_L_DEVIS 'Cette ligne de devis n''existe pas !';

CREATE EXCEPTION E_XAKID_L_FACTURE 'Cette ligne de facture n''existe pas !';

CREATE EXCEPTION E_XAKID_PRIX 'Ce prix n''existe pas dans la liste des prix disponibles !';

CREATE EXCEPTION E_XAKID_TELEPHONE 'Ce numéro de téléphone n''existe pas !';

CREATE EXCEPTION E_XAKID_TIERS 'Ce tiers n''existe pas dans la liste des tiers disponibles !';

CREATE EXCEPTION E_XAKID_TIERS_COMPL 'Le complément d''information de ce tiers existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_ID_ADRESSE 'La relation entre ce tiers et cette adresse existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_ID_EMAIL 'La relation entre ce tiers et cette adresse email existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_ID_TELEPHONE 'La relation entre ce tiers et ce numéro existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_ID_TIERS_COM 'La relation entre ce tiers et ce commercial existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_ID_WEB 'La relation entre ce tiers et ce site internet existe déjà';

CREATE EXCEPTION E_XAKID_TIERS_R_COMMERCIAL 'Le complément d''information de ce tiers existe déjà';

CREATE EXCEPTION E_XAKID_TVA 'Ce code tva n''existe pas dans la liste des codes tva disponibles !';

CREATE EXCEPTION E_XAKID_T_ADR 'Ce type adresse n''existe pas !';

CREATE EXCEPTION E_XAKID_T_ADR_ID_ADRESSE 'La relation entre ce type d''adresse et cette adresse existe déjà';

CREATE EXCEPTION E_XAKID_T_CIVILITE 'Cette civilité n''existe pas dans la liste des civilités disponibles !';

CREATE EXCEPTION E_XAKID_T_DOC 'Ce type de document n''existe pas !';

CREATE EXCEPTION E_XAKID_T_LIGNE 'Ce type de ligne n''existe pas dans la liste des types de ligne disponibles !';

CREATE EXCEPTION E_XAKID_T_PAIEMENT 'Ce type de paiement n''existe pas !';

CREATE EXCEPTION E_XAKID_T_TEL 'Ce type téléphone n''existe pas !';

CREATE EXCEPTION E_XAKID_T_TEL_ID_TELEPHONE 'La relation entre ce type de téléphone et ce numéro existe déjà';

CREATE EXCEPTION E_XAKID_T_TIERS 'Ce type de tiers n''existe pas dans la liste des types disponibles !';

CREATE EXCEPTION E_XAKID_UNITE 'Ce code unité n''existe pas dans la liste des codes unité disponibles !';

CREATE EXCEPTION E_XAKID_WEB 'Cette adresse de site web n''existe pas !';

CREATE EXCEPTION E_XAKNOM_ENTREPRISE 'Le nom de l''entreprise doit être unique !';

CREATE EXCEPTION NE_PAS_MODIFIER 'Vous ne pouvez pas modifier cet enregistrement !';

CREATE EXCEPTION NE_PAS_SUPPRIMER 'Vous ne pouvez pas supprimer cet enregistrement !';

CREATE EXCEPTION XAKCODE_T_TVA 'Le code type tva doit être unique';
