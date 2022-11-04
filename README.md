# Groceries tracker #


## Description ##
The application (script for now) that reads the json file of the receipt and writes 
data from it to the database.


## Database structure ##
The application runs on the [MariaDB 10.6.10](https://mariadb.org/) database.  
The `groceries` database consists of three tables:
 * Table `receipts`.
 * Table `products`.
 * Linking table `receipts_products`.
```
groceries
 ├─> receipts
 │    ├─> receipt_id [int(11) unsigned NOT NULL AUTO_INCREMENT]
 │    ├─> seller_name [varchar(255) NOT NULL]
 │    └─> date_time [datetime NOT NULL]
 │
 ├─> receipts_products
 │    ├─> receipt_product_id [int(11) unsigned NOT NULL AUTO_INCREMENT]
 │    ├─> receipt_id [int(11) unsigned NOT NULL]
 │    ├─> product_id [int(11) unsigned NOT NULL]
 │    └─> quantity [double unsigned NOT NULL]
 │
 └─> products
      ├─> product_id [int(11) unsigned NOT NULL AUTO_INCREMENT]
      ├─> name [varchar(255) NOT NULL]
      └─> price [double unsigned NOT NULL]
```
For more information, see [create_database.sql](./create_database.sql).


## JSON ##
The application reads only json files of receipts created in the mobile application 
of the Federal Taxation Service of the Russian Federation ([Google Play](https://play.google.com/store/apps/details?id=ru.fns.billchecker),
[AppStore](https://apps.apple.com/ru/app/%D0%BF%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%B0-%D0%BA%D0%B0%D1%81%D1%81%D0%BE%D0%B2%D0%BE%D0%B3%D0%BE-%D1%87%D0%B5%D0%BA%D0%B0-%D0%B2-%D1%84%D0%BD%D1%81-%D1%80%D0%BE%D1%81%D1%81%D0%B8%D0%B8/id1169353005)).


## Using ##
1. Download the json file of the receipt to the `Download` folder.
2. Launch the application (if the receipt already exists in the database, it will not be added again).
3. The data is recorded. Now you can do some data analysis!