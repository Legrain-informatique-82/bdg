UPDATE [BERTRINE].[dbo].[Entreprise]
   SET [TypeClient] = replace(typeClient,'CLIENTS','CL')
 WHERE typeClient is not null 