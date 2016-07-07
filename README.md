Step 1:

#Create a my.ini file using any text editor such as notepad++ in the location C:/mysql-5.6.23-winx64/my.ini with the following contents.

[mysqld]
basedir = C:/mysql-5.6.23-winx64
datadir = C:/mysql-5.6.23-winx64/data
port = 3306
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES

# Open a command prompt at C:/mysql-5.6.23-winx64/bin and start server
mysqld.exe --console


#Open another command prompt at C:/mysql-5.6.23-winx64/bin and run the mysql command line tool using the root account by typing
mysql.exe -u root

#set a password
UPDATE mysql.user SET Password = PASSWORD('password') WHERE User = 'root'
FLUSH  PRIVLEGES

#Setting up our database
CREATE DATABASE walmart;
CREATE USER 'walmart'@'localhost' IDENTIFIED BY 'password';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,ALTER,DROP ON walmart.* TO 'walmart'@'localhost';

#create table
USE walmart;
CREATE TABLE Seathold (db_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, level_id BIGINT NOT NULL, level_name VARCHAR(255) NOT NULL, price VARCHAR(255),row_num BIGINT NOT NULL, seats_in_row BIGINT NOT NULL, status VARCHAR(255) NOT NULL, modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, phone_number VARCHAR(255),email_address VARCHAR(255),priority BIGINT NOT NULL);

Step 2:

pull the project code into eclipse.

for testing the codes there is the restfullTest Junit Test src\test\java and open the package there is a RestfulTest.java file in it, right click on it and Run as-> Junit test.

Thanks 
