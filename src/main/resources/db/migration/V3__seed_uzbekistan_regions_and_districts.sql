INSERT INTO regions (name) VALUES
    ('Republic of Karakalpakstan'),
    ('Xorazm Region'),
    ('Navoiy Region'),
    ('Bukhara Region'),
    ('Samarqand Region'),
    ('Qashqadaryo Region'),
    ('Surxondaryo Region'),
    ('Jizzakh Region'),
    ('Sirdaryo Region'),
    ('Tashkent Region'),
    ('Namangan Region'),
    ('Fergana Region'),
    ('Andijan Region'),
    ('Tashkent City');

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Republic of Karakalpakstan') r
CROSS JOIN (VALUES
    ('Amudaryo District'),
    ('Beruniy District'),
    ('Chimboy District'),
    ('Ellikqala District'),
    ('Kegeyli District'),
    ('Moʻynoq District'),
    ('Nukus District'),
    ('Qanlikoʻl District'),
    ('Qoʻngʻirot District'),
    ('Qoraoʻzak District'),
    ('Shumanay District'),
    ('Taxtakoʻpir District'),
    ('Toʻrtkoʻl District'),
    ('Xoʻjayli District'),
    ('Taxiatosh District'),
    ('Boʻzatov District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Xorazm Region') r
CROSS JOIN (VALUES
    ('Bogʻot District'),
    ('Gurlan District'),
    ('Xonqa District'),
    ('Hazorasp District'),
    ('Khiva District'),
    ('Qoʻshkoʻpir District'),
    ('Shovot District'),
    ('Urganch District'),
    ('Yangiariq District'),
    ('Yangibozor District'),
    ('Tuproqqalʼa District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Navoiy Region') r
CROSS JOIN (VALUES
    ('Konimex District'),
    ('Qiziltepa District'),
    ('Xatirchi District'),
    ('Navbahor District'),
    ('Karmana District'),
    ('Nurota District'),
    ('Tomdi District'),
    ('Uchquduq District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Bukhara Region') r
CROSS JOIN (VALUES
    ('Olot District'),
    ('Bukhara District'),
    ('Gʻijduvon District'),
    ('Jondor District'),
    ('Kogon District'),
    ('Qorakoʻl District'),
    ('Qorovulbozor District'),
    ('Peshku District'),
    ('Romitan District'),
    ('Shofirkon District'),
    ('Vobkent District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Samarqand Region') r
CROSS JOIN (VALUES
    ('Bulungʻur District'),
    ('Ishtixon District'),
    ('Jomboy District'),
    ('Kattakurgan District'),
    ('Qoʻshrabot District'),
    ('Narpay District'),
    ('Nurobod District'),
    ('Oqdaryo District'),
    ('Paxtachi District'),
    ('Payariq District'),
    ('Pastdargʻom District'),
    ('Samarqand District'),
    ('Toyloq District'),
    ('Urgut District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Qashqadaryo Region') r
CROSS JOIN (VALUES
    ('Chiroqchi District'),
    ('Dehqonobod District'),
    ('Gʻuzor District'),
    ('Qamashi District'),
    ('Qarshi District'),
    ('Koson District'),
    ('Kasbi District'),
    ('Kitob District'),
    ('Mirishkor District'),
    ('Muborak District'),
    ('Nishon District'),
    ('Shakhrisabz District'),
    ('Yakkabogʻ District'),
    ('Kokdala District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Surxondaryo Region') r
CROSS JOIN (VALUES
    ('Angor District'),
    ('Bandixon District'),
    ('Boysun District'),
    ('Denov District'),
    ('Jarqoʻrgʻon District'),
    ('Qiziriq District'),
    ('Qumqoʻrgʻon District'),
    ('Muzrabot District'),
    ('Oltinsoy District'),
    ('Sariosiyo District'),
    ('Sherobod District'),
    ('Shoʻrchi District'),
    ('Termiz District'),
    ('Uzun District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Jizzakh Region') r
CROSS JOIN (VALUES
    ('Arnasoy District'),
    ('Baxmal District'),
    ('Doʻstlik District'),
    ('Forish District'),
    ('Gʻallaorol District'),
    ('Sharof Rashidov District'),
    ('Mirzachoʻl District'),
    ('Paxtakor District'),
    ('Yangiobod District'),
    ('Zomin District'),
    ('Zafarobod District'),
    ('Zarbdor District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Sirdaryo Region') r
CROSS JOIN (VALUES
    ('Oqoltin District'),
    ('Boyovut District'),
    ('Guliston District'),
    ('Xovos District'),
    ('Mirzaobod District'),
    ('Sardoba District'),
    ('Sayxunobod District'),
    ('Sirdaryo District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Tashkent Region') r
CROSS JOIN (VALUES
    ('Bekabad District'),
    ('Boʻstonliq District'),
    ('Boʻka District'),
    ('Chinoz District'),
    ('Qibray District'),
    ('Ohangaron District'),
    ('Oqqoʻrgʻon District'),
    ('Parkent District'),
    ('Piskent District'),
    ('Quyichirchiq District'),
    ('Zangiota District'),
    ('Oʻrtachirchiq District'),
    ('Yangiyoʻl District'),
    ('Yuqorichirchiq District'),
    ('Tashkent District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Namangan Region') r
CROSS JOIN (VALUES
    ('Chortoq District'),
    ('Chust District'),
    ('Kosonsoy District'),
    ('Mingbuloq District'),
    ('Namangan District'),
    ('Norin District'),
    ('Pop District'),
    ('Toʻraqoʻrgʻon District'),
    ('Uchqoʻrgʻon District'),
    ('Uychi District'),
    ('Yangiqoʻrgʻon District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Fergana Region') r
CROSS JOIN (VALUES
    ('Oltiariq District'),
    ('Bagʻdod District'),
    ('Beshariq District'),
    ('Buvayda District'),
    ('Dangʻara District'),
    ('Fergana District'),
    ('Furqat District'),
    ('Qoʻshtepa District'),
    ('Quva District'),
    ('Rishton District'),
    ('Soʻx District'),
    ('Toshloq District'),
    ('Uchkoʻprik District'),
    ('Uzbekistan District'),
    ('Yozyovon District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Andijan Region') r
CROSS JOIN (VALUES
    ('Andijan District'),
    ('Asaka District'),
    ('Baliqchi District'),
    ('Boʻston District'),
    ('Buloqboshi District'),
    ('Izboskan District'),
    ('Jalaquduq District'),
    ('Xoʻjaobod District'),
    ('Qoʻrgʻontepa District'),
    ('Marhamat District'),
    ('Oltinkoʻl District'),
    ('Paxtaobod District'),
    ('Shahrixon District'),
    ('Ulugʻnor District')
) AS d(name);

INSERT INTO districts (name, region_id)
SELECT d.name, r.id
FROM (SELECT id FROM regions WHERE name = 'Tashkent City') r
CROSS JOIN (VALUES
    ('Bektemir District'),
    ('Chilanzar District'),
    ('Yashnobod District'),
    ('Mirobod District'),
    ('Mirzo Ulugbek District'),
    ('Sergeli District'),
    ('Shayxontoxur District'),
    ('Olmazor District'),
    ('Uchtepa District'),
    ('Yakkasaray District'),
    ('Yunusabad District'),
    ('Yangihayot District')
) AS d(name);
