-- Re-seed regions and districts with Uzbek (Latin) names.
--
-- Replaces the English seed from V3. Existing employees keep their region and
-- district: their references are snapshotted by old name, cleared so the
-- reference tables can be emptied, and re-linked to the new Uzbek rows.

-- 1. old (English) name -> new (Uzbek) name
CREATE TEMP TABLE region_name_map (
    old_name VARCHAR(255) NOT NULL,
    new_name VARCHAR(255) NOT NULL
) ON COMMIT DROP;

CREATE TEMP TABLE district_name_map (
    old_region_name VARCHAR(255) NOT NULL,
    old_name        VARCHAR(255) NOT NULL,
    new_name        VARCHAR(255) NOT NULL
) ON COMMIT DROP;

INSERT INTO region_name_map (old_name, new_name) VALUES
                                                     ('Republic of Karakalpakstan', 'Qoraqalpogʻiston Respublikasi'),
                                                     ('Xorazm Region', 'Xorazm viloyati'),
                                                     ('Navoiy Region', 'Navoiy viloyati'),
                                                     ('Bukhara Region', 'Buxoro viloyati'),
                                                     ('Samarqand Region', 'Samarqand viloyati'),
                                                     ('Qashqadaryo Region', 'Qashqadaryo viloyati'),
                                                     ('Surxondaryo Region', 'Surxondaryo viloyati'),
                                                     ('Jizzakh Region', 'Jizzax viloyati'),
                                                     ('Sirdaryo Region', 'Sirdaryo viloyati'),
                                                     ('Tashkent Region', 'Toshkent viloyati'),
                                                     ('Namangan Region', 'Namangan viloyati'),
                                                     ('Fergana Region', 'Fargʻona viloyati'),
                                                     ('Andijan Region', 'Andijon viloyati'),
                                                     ('Tashkent City', 'Toshkent shahri');

INSERT INTO district_name_map (old_region_name, old_name, new_name) VALUES
                                                                        ('Republic of Karakalpakstan', 'Amudaryo District', 'Amudaryo tumani'),
                                                                        ('Republic of Karakalpakstan', 'Beruniy District', 'Beruniy tumani'),
                                                                        ('Republic of Karakalpakstan', 'Chimboy District', 'Chimboy tumani'),
                                                                        ('Republic of Karakalpakstan', 'Ellikqala District', 'Ellikqala tumani'),
                                                                        ('Republic of Karakalpakstan', 'Kegeyli District', 'Kegeyli tumani'),
                                                                        ('Republic of Karakalpakstan', 'Moʻynoq District', 'Moʻynoq tumani'),
                                                                        ('Republic of Karakalpakstan', 'Nukus District', 'Nukus tumani'),
                                                                        ('Republic of Karakalpakstan', 'Qanlikoʻl District', 'Qanlikoʻl tumani'),
                                                                        ('Republic of Karakalpakstan', 'Qoʻngʻirot District', 'Qoʻngʻirot tumani'),
                                                                        ('Republic of Karakalpakstan', 'Qoraoʻzak District', 'Qoraoʻzak tumani'),
                                                                        ('Republic of Karakalpakstan', 'Shumanay District', 'Shumanay tumani'),
                                                                        ('Republic of Karakalpakstan', 'Taxtakoʻpir District', 'Taxtakoʻpir tumani'),
                                                                        ('Republic of Karakalpakstan', 'Toʻrtkoʻl District', 'Toʻrtkoʻl tumani'),
                                                                        ('Republic of Karakalpakstan', 'Xoʻjayli District', 'Xoʻjayli tumani'),
                                                                        ('Republic of Karakalpakstan', 'Taxiatosh District', 'Taxiatosh tumani'),
                                                                        ('Republic of Karakalpakstan', 'Boʻzatov District', 'Boʻzatov tumani'),
                                                                        ('Xorazm Region', 'Bogʻot District', 'Bogʻot tumani'),
                                                                        ('Xorazm Region', 'Gurlan District', 'Gurlan tumani'),
                                                                        ('Xorazm Region', 'Xonqa District', 'Xonqa tumani'),
                                                                        ('Xorazm Region', 'Hazorasp District', 'Hazorasp tumani'),
                                                                        ('Xorazm Region', 'Khiva District', 'Xiva tumani'),
                                                                        ('Xorazm Region', 'Qoʻshkoʻpir District', 'Qoʻshkoʻpir tumani'),
                                                                        ('Xorazm Region', 'Shovot District', 'Shovot tumani'),
                                                                        ('Xorazm Region', 'Urganch District', 'Urganch tumani'),
                                                                        ('Xorazm Region', 'Yangiariq District', 'Yangiariq tumani'),
                                                                        ('Xorazm Region', 'Yangibozor District', 'Yangibozor tumani'),
                                                                        ('Xorazm Region', 'Tuproqqalʼa District', 'Tuproqqalʼa tumani'),
                                                                        ('Navoiy Region', 'Konimex District', 'Konimex tumani'),
                                                                        ('Navoiy Region', 'Qiziltepa District', 'Qiziltepa tumani'),
                                                                        ('Navoiy Region', 'Xatirchi District', 'Xatirchi tumani'),
                                                                        ('Navoiy Region', 'Navbahor District', 'Navbahor tumani'),
                                                                        ('Navoiy Region', 'Karmana District', 'Karmana tumani'),
                                                                        ('Navoiy Region', 'Nurota District', 'Nurota tumani'),
                                                                        ('Navoiy Region', 'Tomdi District', 'Tomdi tumani'),
                                                                        ('Navoiy Region', 'Uchquduq District', 'Uchquduq tumani'),
                                                                        ('Bukhara Region', 'Olot District', 'Olot tumani'),
                                                                        ('Bukhara Region', 'Bukhara District', 'Buxoro tumani'),
                                                                        ('Bukhara Region', 'Gʻijduvon District', 'Gʻijduvon tumani'),
                                                                        ('Bukhara Region', 'Jondor District', 'Jondor tumani'),
                                                                        ('Bukhara Region', 'Kogon District', 'Kogon tumani'),
                                                                        ('Bukhara Region', 'Qorakoʻl District', 'Qorakoʻl tumani'),
                                                                        ('Bukhara Region', 'Qorovulbozor District', 'Qorovulbozor tumani'),
                                                                        ('Bukhara Region', 'Peshku District', 'Peshku tumani'),
                                                                        ('Bukhara Region', 'Romitan District', 'Romitan tumani'),
                                                                        ('Bukhara Region', 'Shofirkon District', 'Shofirkon tumani'),
                                                                        ('Bukhara Region', 'Vobkent District', 'Vobkent tumani'),
                                                                        ('Samarqand Region', 'Bulungʻur District', 'Bulungʻur tumani'),
                                                                        ('Samarqand Region', 'Ishtixon District', 'Ishtixon tumani'),
                                                                        ('Samarqand Region', 'Jomboy District', 'Jomboy tumani'),
                                                                        ('Samarqand Region', 'Kattakurgan District', 'Kattaqoʻrgʻon tumani'),
                                                                        ('Samarqand Region', 'Qoʻshrabot District', 'Qoʻshrabot tumani'),
                                                                        ('Samarqand Region', 'Narpay District', 'Narpay tumani'),
                                                                        ('Samarqand Region', 'Nurobod District', 'Nurobod tumani'),
                                                                        ('Samarqand Region', 'Oqdaryo District', 'Oqdaryo tumani'),
                                                                        ('Samarqand Region', 'Paxtachi District', 'Paxtachi tumani'),
                                                                        ('Samarqand Region', 'Payariq District', 'Payariq tumani'),
                                                                        ('Samarqand Region', 'Pastdargʻom District', 'Pastdargʻom tumani'),
                                                                        ('Samarqand Region', 'Samarqand District', 'Samarqand tumani'),
                                                                        ('Samarqand Region', 'Toyloq District', 'Toyloq tumani'),
                                                                        ('Samarqand Region', 'Urgut District', 'Urgut tumani'),
                                                                        ('Qashqadaryo Region', 'Chiroqchi District', 'Chiroqchi tumani'),
                                                                        ('Qashqadaryo Region', 'Dehqonobod District', 'Dehqonobod tumani'),
                                                                        ('Qashqadaryo Region', 'Gʻuzor District', 'Gʻuzor tumani'),
                                                                        ('Qashqadaryo Region', 'Qamashi District', 'Qamashi tumani'),
                                                                        ('Qashqadaryo Region', 'Qarshi District', 'Qarshi tumani'),
                                                                        ('Qashqadaryo Region', 'Koson District', 'Koson tumani'),
                                                                        ('Qashqadaryo Region', 'Kasbi District', 'Kasbi tumani'),
                                                                        ('Qashqadaryo Region', 'Kitob District', 'Kitob tumani'),
                                                                        ('Qashqadaryo Region', 'Mirishkor District', 'Mirishkor tumani'),
                                                                        ('Qashqadaryo Region', 'Muborak District', 'Muborak tumani'),
                                                                        ('Qashqadaryo Region', 'Nishon District', 'Nishon tumani'),
                                                                        ('Qashqadaryo Region', 'Shakhrisabz District', 'Shahrisabz tumani'),
                                                                        ('Qashqadaryo Region', 'Yakkabogʻ District', 'Yakkabogʻ tumani'),
                                                                        ('Qashqadaryo Region', 'Kokdala District', 'Koʻkdala tumani'),
                                                                        ('Surxondaryo Region', 'Angor District', 'Angor tumani'),
                                                                        ('Surxondaryo Region', 'Bandixon District', 'Bandixon tumani'),
                                                                        ('Surxondaryo Region', 'Boysun District', 'Boysun tumani'),
                                                                        ('Surxondaryo Region', 'Denov District', 'Denov tumani'),
                                                                        ('Surxondaryo Region', 'Jarqoʻrgʻon District', 'Jarqoʻrgʻon tumani'),
                                                                        ('Surxondaryo Region', 'Qiziriq District', 'Qiziriq tumani'),
                                                                        ('Surxondaryo Region', 'Qumqoʻrgʻon District', 'Qumqoʻrgʻon tumani'),
                                                                        ('Surxondaryo Region', 'Muzrabot District', 'Muzrabot tumani'),
                                                                        ('Surxondaryo Region', 'Oltinsoy District', 'Oltinsoy tumani'),
                                                                        ('Surxondaryo Region', 'Sariosiyo District', 'Sariosiyo tumani'),
                                                                        ('Surxondaryo Region', 'Sherobod District', 'Sherobod tumani'),
                                                                        ('Surxondaryo Region', 'Shoʻrchi District', 'Shoʻrchi tumani'),
                                                                        ('Surxondaryo Region', 'Termiz District', 'Termiz tumani'),
                                                                        ('Surxondaryo Region', 'Uzun District', 'Uzun tumani'),
                                                                        ('Jizzakh Region', 'Arnasoy District', 'Arnasoy tumani'),
                                                                        ('Jizzakh Region', 'Baxmal District', 'Baxmal tumani'),
                                                                        ('Jizzakh Region', 'Doʻstlik District', 'Doʻstlik tumani'),
                                                                        ('Jizzakh Region', 'Forish District', 'Forish tumani'),
                                                                        ('Jizzakh Region', 'Gʻallaorol District', 'Gʻallaorol tumani'),
                                                                        ('Jizzakh Region', 'Sharof Rashidov District', 'Sharof Rashidov tumani'),
                                                                        ('Jizzakh Region', 'Mirzachoʻl District', 'Mirzachoʻl tumani'),
                                                                        ('Jizzakh Region', 'Paxtakor District', 'Paxtakor tumani'),
                                                                        ('Jizzakh Region', 'Yangiobod District', 'Yangiobod tumani'),
                                                                        ('Jizzakh Region', 'Zomin District', 'Zomin tumani'),
                                                                        ('Jizzakh Region', 'Zafarobod District', 'Zafarobod tumani'),
                                                                        ('Jizzakh Region', 'Zarbdor District', 'Zarbdor tumani'),
                                                                        ('Sirdaryo Region', 'Oqoltin District', 'Oqoltin tumani'),
                                                                        ('Sirdaryo Region', 'Boyovut District', 'Boyovut tumani'),
                                                                        ('Sirdaryo Region', 'Guliston District', 'Guliston tumani'),
                                                                        ('Sirdaryo Region', 'Xovos District', 'Xovos tumani'),
                                                                        ('Sirdaryo Region', 'Mirzaobod District', 'Mirzaobod tumani'),
                                                                        ('Sirdaryo Region', 'Sardoba District', 'Sardoba tumani'),
                                                                        ('Sirdaryo Region', 'Sayxunobod District', 'Sayxunobod tumani'),
                                                                        ('Sirdaryo Region', 'Sirdaryo District', 'Sirdaryo tumani'),
                                                                        ('Tashkent Region', 'Bekabad District', 'Bekobod tumani'),
                                                                        ('Tashkent Region', 'Boʻstonliq District', 'Boʻstonliq tumani'),
                                                                        ('Tashkent Region', 'Boʻka District', 'Boʻka tumani'),
                                                                        ('Tashkent Region', 'Chinoz District', 'Chinoz tumani'),
                                                                        ('Tashkent Region', 'Qibray District', 'Qibray tumani'),
                                                                        ('Tashkent Region', 'Ohangaron District', 'Ohangaron tumani'),
                                                                        ('Tashkent Region', 'Oqqoʻrgʻon District', 'Oqqoʻrgʻon tumani'),
                                                                        ('Tashkent Region', 'Parkent District', 'Parkent tumani'),
                                                                        ('Tashkent Region', 'Piskent District', 'Piskent tumani'),
                                                                        ('Tashkent Region', 'Quyichirchiq District', 'Quyichirchiq tumani'),
                                                                        ('Tashkent Region', 'Zangiota District', 'Zangiota tumani'),
                                                                        ('Tashkent Region', 'Oʻrtachirchiq District', 'Oʻrtachirchiq tumani'),
                                                                        ('Tashkent Region', 'Yangiyoʻl District', 'Yangiyoʻl tumani'),
                                                                        ('Tashkent Region', 'Yuqorichirchiq District', 'Yuqorichirchiq tumani'),
                                                                        ('Tashkent Region', 'Tashkent District', 'Toshkent tumani'),
                                                                        ('Namangan Region', 'Chortoq District', 'Chortoq tumani'),
                                                                        ('Namangan Region', 'Chust District', 'Chust tumani'),
                                                                        ('Namangan Region', 'Kosonsoy District', 'Kosonsoy tumani'),
                                                                        ('Namangan Region', 'Mingbuloq District', 'Mingbuloq tumani'),
                                                                        ('Namangan Region', 'Namangan District', 'Namangan tumani'),
                                                                        ('Namangan Region', 'Norin District', 'Norin tumani'),
                                                                        ('Namangan Region', 'Pop District', 'Pop tumani'),
                                                                        ('Namangan Region', 'Toʻraqoʻrgʻon District', 'Toʻraqoʻrgʻon tumani'),
                                                                        ('Namangan Region', 'Uchqoʻrgʻon District', 'Uchqoʻrgʻon tumani'),
                                                                        ('Namangan Region', 'Uychi District', 'Uychi tumani'),
                                                                        ('Namangan Region', 'Yangiqoʻrgʻon District', 'Yangiqoʻrgʻon tumani'),
                                                                        ('Fergana Region', 'Oltiariq District', 'Oltiariq tumani'),
                                                                        ('Fergana Region', 'Bagʻdod District', 'Bagʻdod tumani'),
                                                                        ('Fergana Region', 'Beshariq District', 'Beshariq tumani'),
                                                                        ('Fergana Region', 'Buvayda District', 'Buvayda tumani'),
                                                                        ('Fergana Region', 'Dangʻara District', 'Dangʻara tumani'),
                                                                        ('Fergana Region', 'Fergana District', 'Fargʻona tumani'),
                                                                        ('Fergana Region', 'Furqat District', 'Furqat tumani'),
                                                                        ('Fergana Region', 'Qoʻshtepa District', 'Qoʻshtepa tumani'),
                                                                        ('Fergana Region', 'Quva District', 'Quva tumani'),
                                                                        ('Fergana Region', 'Rishton District', 'Rishton tumani'),
                                                                        ('Fergana Region', 'Soʻx District', 'Soʻx tumani'),
                                                                        ('Fergana Region', 'Toshloq District', 'Toshloq tumani'),
                                                                        ('Fergana Region', 'Uchkoʻprik District', 'Uchkoʻprik tumani'),
                                                                        ('Fergana Region', 'Uzbekistan District', 'Oʻzbekiston tumani'),
                                                                        ('Fergana Region', 'Yozyovon District', 'Yozyovon tumani'),
                                                                        ('Andijan Region', 'Andijan District', 'Andijon tumani'),
                                                                        ('Andijan Region', 'Asaka District', 'Asaka tumani'),
                                                                        ('Andijan Region', 'Baliqchi District', 'Baliqchi tumani'),
                                                                        ('Andijan Region', 'Boʻston District', 'Boʻston tumani'),
                                                                        ('Andijan Region', 'Buloqboshi District', 'Buloqboshi tumani'),
                                                                        ('Andijan Region', 'Izboskan District', 'Izboskan tumani'),
                                                                        ('Andijan Region', 'Jalaquduq District', 'Jalaquduq tumani'),
                                                                        ('Andijan Region', 'Xoʻjaobod District', 'Xoʻjaobod tumani'),
                                                                        ('Andijan Region', 'Qoʻrgʻontepa District', 'Qoʻrgʻontepa tumani'),
                                                                        ('Andijan Region', 'Marhamat District', 'Marhamat tumani'),
                                                                        ('Andijan Region', 'Oltinkoʻl District', 'Oltinkoʻl tumani'),
                                                                        ('Andijan Region', 'Paxtaobod District', 'Paxtaobod tumani'),
                                                                        ('Andijan Region', 'Shahrixon District', 'Shahrixon tumani'),
                                                                        ('Andijan Region', 'Ulugʻnor District', 'Ulugʻnor tumani'),
                                                                        ('Tashkent City', 'Bektemir District', 'Bektemir tumani'),
                                                                        ('Tashkent City', 'Chilanzar District', 'Chilonzor tumani'),
                                                                        ('Tashkent City', 'Yashnobod District', 'Yashnobod tumani'),
                                                                        ('Tashkent City', 'Mirobod District', 'Mirobod tumani'),
                                                                        ('Tashkent City', 'Mirzo Ulugbek District', 'Mirzo Ulugʻbek tumani'),
                                                                        ('Tashkent City', 'Sergeli District', 'Sirgʻali tumani'),
                                                                        ('Tashkent City', 'Shayxontoxur District', 'Shayxontohur tumani'),
                                                                        ('Tashkent City', 'Olmazor District', 'Olmazor tumani'),
                                                                        ('Tashkent City', 'Uchtepa District', 'Uchtepa tumani'),
                                                                        ('Tashkent City', 'Yakkasaray District', 'Yakkasaroy tumani'),
                                                                        ('Tashkent City', 'Yunusabad District', 'Yunusobod tumani'),
                                                                        ('Tashkent City', 'Yangihayot District', 'Yangihayot tumani');

-- 2. snapshot which region/district each employee currently points to
CREATE TEMP TABLE employee_location_snapshot ON COMMIT DROP AS
SELECT e.id AS employee_id,
       r.name AS old_region_name,
       d.name AS old_district_name
FROM employees e
         LEFT JOIN regions r ON r.id = e.region_id
         LEFT JOIN districts d ON d.id = e.district_id
WHERE e.region_id IS NOT NULL OR e.district_id IS NOT NULL;

-- 3. detach employees, delete everything, restart ids from 1
UPDATE employees SET region_id = NULL, district_id = NULL
WHERE region_id IS NOT NULL OR district_id IS NOT NULL;

DELETE FROM districts;
DELETE FROM regions;

ALTER SEQUENCE regions_id_seq RESTART WITH 1;
ALTER SEQUENCE districts_id_seq RESTART WITH 1;

-- 4. seed regions (Uzbek names)
INSERT INTO regions (name)
SELECT new_name FROM region_name_map ORDER BY ctid;

-- 5. seed districts (Uzbek names)
INSERT INTO districts (name, region_id)
SELECT dm.new_name, r.id
FROM district_name_map dm
         JOIN region_name_map rm ON rm.old_name = dm.old_region_name
         JOIN regions r ON r.name = rm.new_name;

-- 6. re-link employees to the new rows
UPDATE employees e
SET region_id = r.id
    FROM employee_location_snapshot s
JOIN region_name_map rm ON rm.old_name = s.old_region_name
    JOIN regions r ON r.name = rm.new_name
WHERE e.id = s.employee_id;

UPDATE employees e
SET district_id = d.id
    FROM employee_location_snapshot s
JOIN district_name_map dm
ON dm.old_region_name = s.old_region_name
    AND dm.old_name = s.old_district_name
    JOIN region_name_map rm ON rm.old_name = dm.old_region_name
    JOIN regions r ON r.name = rm.new_name
    JOIN districts d ON d.name = dm.new_name AND d.region_id = r.id
WHERE e.id = s.employee_id;