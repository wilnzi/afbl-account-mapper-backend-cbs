CREATE OR REPLACE
PROCEDURE ps_getAccountsOpenedInPeriod(
    p_beginDate IN COMPTES.DTE_OUVERTURE%TYPE,
    p_endDate IN COMPTES.DTE_OUVERTURE%TYPE,
    p_results OUT SYS_REFCURSOR
)
AS
BEGIN
	OPEN p_results FOR

    SELECT
	c.CODMUT AS CustomerId,
	c.CODMUT AS UniqueBankingIdentifier,
	c.NUMCPTE AS AccountNumber,
	'010' || c.CODAGE || c.NUMCPTE AS BBAN,
	'010' || c.CODAGE || c.NUMCPTE AS IBAN,
	c.NUMCPTE AS OldAccountNumber,
	c.LIBCPTE AS AccountTitle,
	NULL AS AccountTitle2,
	NULL AS JointHolderName,
	(CASE
		WHEN m.TYPEMUT = 'P' THEN 'Personal'
		WHEN m.TYPEMUT = 'M' THEN 'Moral'
	END)AS AccountCategory,
	nc.LIBNATCPTE AS AccountType,
	d.CODISODEV AS AccountCurrency,
	'No_Tier_defined' AS AccountTier,
	pm.NUMREGCOM AS BusinessRegNo,
	TO_CHAR(c.DTE_OUVERTURE, 'YYYY-MM-dd') AS DateAccountCreated,
	COALESCE(p.PRENOMPER, '--') AS FirstName,
	NULL AS OtherName,
	COALESCE(p.NOMPER, '--') AS LastName,
	COALESCE(
        NULLIF(TRIM(p.PRENOMPER || ' ' || p.NOMPER), ''),
        'NO-EXISTING-NAME'
    ) AS CustomerName,
	m.PHONE1CLT AS CustomerPhoneNumber1,
	m.PHONE2CLT AS CustomerPhoneNumber2,
	NULL AS CustomerPhoneNumber3,
	CASE
        WHEN m.E_MAILCLT IS NULL THEN 'no-customer-email@no-customer-email.com'
        WHEN REGEXP_LIKE(m.E_MAILCLT, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
        THEN m.E_MAILCLT
        ELSE 'no-customer-email@no-customer-email.com'
    END AS CustomerEmail,
	REGEXP_REPLACE(m.ADRESSECLT, '[^[:alnum:][:space:]]', '') AS CustomerAddress,
	a.NOMACT AS IndustryName,
	CASE
		WHEN c.ETATCPTE = 'O' THEN 'Active'
		WHEN EXISTS (
		SELECT
			1
		FROM
			OPPO_CPTE oc
		WHERE
			oc.NUMCPTE = c.NUMCPTE
			AND oc.CODDEV = c.CODDEV
			AND oc.CODAGE = c.CODAGE
			AND oc.CODTYPEOPP = 'DORM') THEN 'Dormant'
		WHEN c.ETATCPTE = 'F' THEN 'Closed'
		ELSE NULL
	END AS AccountStatus,
	NULL AS AlternativeAccountId,
	TO_CHAR(COALESCE(p.DTEDELIVPCLT, pm.DTEETAB_RCCM), 'YYYY-MM-dd') AS LegalIssueDate,
	COALESCE(p.NUMPIECECLT, pm.NUMPATENTE) AS LegalId,
	TO_CHAR(COALESCE(p.DTEVALPIECECLT, pm.DTEVALPATPRM), 'YYYY-MM-dd') AS ExpiryDate,
	pi2.NATPID AS LegalDocumentName,
	pm.RAISONSOCIALE AS LegalHolderName,
	NULL AS ProxyType,
	NULL AS ProxyIdentifier,
	NULL AS ProxyScheme,
	NULL AS ProxyIdentifier,
	CASE
		WHEN m.REFNATCLIENT IN('3', '4', '5') THEN 'true'
		ELSE 'false'
	END AS PEP
	FROM
		COMPTES c
	INNER JOIN MUTUALISTES m ON
		m.CODMUT = c.CODMUT
	INNER JOIN NATURE_COMPTE nc ON
		nc.CODNATCPTE = c.CODNATCPTE
	INNER JOIN DEVISE d ON
		d.CODDEV = c.CODDEV
	INNER JOIN ACTIVITE a ON
		a.CODACT = m.CODACT
	LEFT JOIN PERSONNE_MORALE pm ON
		pm.CODMUT = m.CODMUT
	LEFT JOIN PERSONNE p ON
		p.CODMUT = m.CODMUT
	LEFT JOIN PIECE_IDENTITE pi2 ON
		pi2.CODPID = P.CODPID
	WHERE
		c.CODNATCPTE IN ('001', '002', '003', '004', '005', '006', '007', '033')
		AND (c.NUMCPTE LIKE '01%' OR c.NUMCPTE LIKE '02%')
	AND c.DTE_OUVERTURE BETWEEN p_beginDate AND p_endDate
	ORDER BY
		DateAccountCreated DESC ,
		CustomerId DESC;
EXCEPTION
	WHEN OTHERS THEN
        RAISE;
END ps_getAccountsOpenedInPeriod;


CREATE OR REPLACE
PROCEDURE ps_getAccountInformation(
 	p_branchCode IN COMPTES.CODAGE%TYPE,
    p_currencyCode IN COMPTES.CODDEV%TYPE,
    p_accountNumber IN COMPTES.NUMCPTE%TYPE,
    p_results OUT SYS_REFCURSOR
)
AS
BEGIN
	OPEN p_results FOR
        SELECT
	c.CODMUT AS CustomerId,
	c.CODMUT AS UniqueBankingIdentifier,
	c.NUMCPTE AS AccountNumber,
	'010' || c.CODAGE || c.NUMCPTE AS BBAN,
	'010' || c.CODAGE || c.NUMCPTE AS IBAN,
	c.NUMCPTE AS OldAccountNumber,
	c.LIBCPTE AS AccountTitle,
	NULL AS AccountTitle2,
	NULL AS JointHolderName,
	(CASE
		WHEN m.TYPEMUT = 'P' THEN 'Personal'
		WHEN m.TYPEMUT = 'M' THEN 'Moral'
	END)AS AccountCategory,
	nc.LIBNATCPTE AS AccountType,
	d.CODISODEV AS AccountCurrency,
	'No_Tier_defined' AS AccountTier,
	pm.NUMREGCOM AS BusinessRegNo,
	TO_CHAR(c.DTE_OUVERTURE, 'YYYY-MM-dd') AS DateAccountCreated,
	COALESCE(p.PRENOMPER, '--') AS FirstName,
	NULL AS OtherName,
	COALESCE(p.NOMPER, '--') AS LastName,
	COALESCE(
        NULLIF(TRIM(p.PRENOMPER || ' ' || p.NOMPER), ''),
        'NO-EXISTING-NAME'
    ) AS CustomerName,
	m.PHONE1CLT AS CustomerPhoneNumber1,
	m.PHONE2CLT AS CustomerPhoneNumber2,
	NULL AS CustomerPhoneNumber3,
	CASE
        WHEN m.E_MAILCLT IS NULL THEN 'no-customer-email@no-customer-email.com'
        WHEN REGEXP_LIKE(m.E_MAILCLT, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
        THEN m.E_MAILCLT
        ELSE 'no-customer-email@no-customer-email.com'
    END AS CustomerEmail,
	REGEXP_REPLACE(m.ADRESSECLT, '[^[:alnum:][:space:]]', '') AS CustomerAddress,
	a.NOMACT AS IndustryName,
	CASE
		WHEN c.ETATCPTE = 'O' THEN 'Active'
		WHEN EXISTS (
		SELECT
			1
		FROM
			OPPO_CPTE oc
		WHERE
			oc.NUMCPTE = c.NUMCPTE
			AND oc.CODDEV = c.CODDEV
			AND oc.CODAGE = c.CODAGE
			AND oc.CODTYPEOPP = 'DORM') THEN 'Dormant'
		WHEN c.ETATCPTE = 'F' THEN 'Closed'
		ELSE NULL
	END AS AccountStatus,
	NULL AS AlternativeAccountId,
	TO_CHAR(COALESCE(p.DTEDELIVPCLT, pm.DTEETAB_RCCM), 'YYYY-MM-dd') AS LegalIssueDate,
	COALESCE(p.NUMPIECECLT, pm.NUMPATENTE) AS LegalId,
	TO_CHAR(COALESCE(p.DTEVALPIECECLT, pm.DTEVALPATPRM), 'YYYY-MM-dd') AS ExpiryDate,
	pi2.NATPID AS LegalDocumentName,
	pm.RAISONSOCIALE AS LegalHolderName,
	NULL AS ProxyType,
	NULL AS ProxyIdentifier,
	NULL AS ProxyScheme,
	NULL AS ProxyIdentifier,
	CASE
		WHEN m.REFNATCLIENT IN('3', '4', '5') THEN 'true'
		ELSE 'false'
	END AS PEP
	FROM
		COMPTES c
	INNER JOIN MUTUALISTES m ON
		m.CODMUT = c.CODMUT
	INNER JOIN NATURE_COMPTE nc ON
		nc.CODNATCPTE = c.CODNATCPTE
	INNER JOIN DEVISE d ON
		d.CODDEV = c.CODDEV
	INNER JOIN ACTIVITE a ON
		a.CODACT = m.CODACT
	LEFT JOIN PERSONNE_MORALE pm ON
		pm.CODMUT = m.CODMUT
	LEFT JOIN PERSONNE p ON
		p.CODMUT = m.CODMUT
	LEFT JOIN PIECE_IDENTITE pi2 ON
		pi2.CODPID = P.CODPID
	WHERE
		c.CODNATCPTE IN ('001', '002', '003', '004', '005', '006', '007', '033')
	AND (c.NUMCPTE LIKE '01%' OR c.NUMCPTE LIKE '02%')
	AND c.NUMCPTE = p_accountNumber
	AND c.CODAGE = p_branchCode
	AND c.CODDEV = p_currencyCode;
EXCEPTION
	WHEN OTHERS THEN
        RAISE;
END ps_getAccountInformation;


CREATE OR REPLACE VIEW active_customers AS
WITH list_of_customers AS (
SELECT
	c.CODMUT AS CustomerId,
	c.CODMUT AS UniqueBankingIdentifier,
	c.NUMCPTE AS AccountNumber,
	'010 - ' || c.CODAGE || ' - ' || c.NUMCPTE AS BBAN,
	'010 - ' || c.CODAGE || ' - ' || c.NUMCPTE AS IBAN,
	c.NUMCPTE AS OldAccountNumber,
	c.LIBCPTE AS AccountTitle,
	NULL AS AccountTitle2,
	NULL AS JointHolderName,
	(CASE
		WHEN m.TYPEMUT = 'P' THEN 'Personal'
		WHEN m.TYPEMUT = 'M' THEN 'Moral'
	END)AS AccountCategory,
	nc.LIBNATCPTE AS AccountType,
	d.CODISODEV AS AccountCurrency,
	NULL AS AccountTier,
	pm.NUMREGCOM AS BusinessRegNo,
	TO_CHAR(c.DTE_OUVERTURE, 'YYYY-MM-dd') AS DateAccountCreated,
	p.PRENOMPER AS FirstName,
	NULL AS OtherName,
	p.NOMPER AS LastName,
	p.PRENOMPER || ' ' || p.NOMPER AS CustomerName,
	m.PHONE1CLT AS CustomerPhoneNumber1,
	m.PHONE2CLT AS CustomerPhoneNumber2,
	NULL AS CustomerPhoneNumber3,
	m.E_MAILCLT AS CustomerEmail,
	m.ADRESSECLT AS CustomerAddress,
	a.NOMACT AS IndustryName,
	CASE
		WHEN c.ETATCPTE = 'O' THEN 'Active'
		WHEN EXISTS (
		SELECT
			1
		FROM
			OPPO_CPTE oc
		WHERE
			oc.NUMCPTE = c.NUMCPTE
			AND oc.CODDEV = c.CODDEV
			AND oc.CODAGE = c.CODAGE
			AND oc.CODTYPEOPP = 'DORM') THEN 'Dormant'
		WHEN c.ETATCPTE = 'F' THEN 'Closed'
		ELSE NULL
	END AS AccountStatus,
	NULL AS AlternativeAccountId,
	TO_CHAR(COALESCE(p.DTEDELIVPCLT, pm.DTEETAB_RCCM), 'YYYY-MM-dd') AS LegalIssueDate,
	COALESCE(p.NUMPIECECLT, pm.NUMPATENTE) AS LegalId,
	TO_CHAR(COALESCE(p.DTEVALPIECECLT, pm.DTEVALPATPRM), 'YYYY-MM-dd') AS ExpiryDate,
	pi2.NATPID AS LegalDocumentName,
	pm.RAISONSOCIALE AS LegalHolderName,
	NULL AS ProxyType,
	NULL AS ProxyIdentifier,
	NULL AS ProxyScheme,
	CASE
		WHEN m.REFNATCLIENT IN('3', '4', '5') THEN 'true'
		ELSE 'false'
	END AS PEP,
	ag.NOMAGE AS branchName
FROM
		COMPTES c
INNER JOIN AGENCE ag ON
	ag.CODAGE = c.CODAGE
INNER JOIN MUTUALISTES m ON
		m.CODMUT = c.CODMUT
INNER JOIN NATURE_COMPTE nc ON
		nc.CODNATCPTE = c.CODNATCPTE
INNER JOIN DEVISE d ON
		d.CODDEV = c.CODDEV
INNER JOIN ACTIVITE a ON
		a.CODACT = m.CODACT
LEFT JOIN PERSONNE_MORALE pm ON
		pm.CODMUT = m.CODMUT
LEFT JOIN PERSONNE p ON
		p.CODMUT = m.CODMUT
LEFT JOIN PIECE_IDENTITE pi2 ON
		pi2.CODPID = P.CODPID
WHERE
		c.CODNATCPTE IN ('001', '002', '003', '004', '005', '006', '007', '033')
ORDER BY
	DateAccountCreated ASC
),
list_of_numered_customers AS (
SELECT
	ROWNUM AS ligneNumber,
	loc.*
FROM
	list_of_customers loc
)
SELECT
	*
FROM
	list_of_numered_customers lonc