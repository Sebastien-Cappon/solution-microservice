CREATE USER 'mls_t2d2_username'@'%' IDENTIFIED BY 'mlst2d2pass';
GRANT ALL ON *.* TO 'mls_t2d2_username'@'%';
FLUSH PRIVILEGES;