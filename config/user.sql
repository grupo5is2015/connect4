
/* Logearse como root y ejecutar este comando */


CREATE USER franco@localhost IDENTIFIED BY 'franco';
GRANT ALL PRIVILEGES ON connect4_development.* TO franco@localhost;
GRANT ALL PRIVILEGES ON connect4_test.* TO franco@localhost;


