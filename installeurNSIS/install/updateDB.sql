connect 'c:/lgrdoss/BureauDeGestion/dossier/Bd/GEST_COM.FDB' user 'ADMIN' password '###_PASSWORD_FB_BDG_###';
update TA_VERSION set old_version = (select (num_version) from TA_VERSION);
update TA_VERSION set num_version = '1.0.6';
commit;
