# SSL Guide
You can generate certificate from java, then convert to key + pem file for using in HipChat SSL configuration
See https://www.playframework.com/documentation/2.3.x/CertificateGeneration

Or, If you have key + pem files (follow [HipChat guide](https://confluence.atlassian.com/display/HC/Creating+or+Obtaining+an+SSL+Key+and+Certificate))
then you need convert to jks for running with Play. See bellow.

## Convert key + pem to jks
see http://java.akraievoy.org/2011/02/convert-key-pem-to-jks-java-keystore.html

1. convert key + pem to p12
openssl pkcs12 -export -in example.com.crt -inkey example.com.key -out example.com.p12

2. Download & install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

3. Download & run portecle
http://portecle.sourceforge.net/

a. New Keystore
b. Import key pair
c. save

## remote debug play with https support
see https://www.playframework.com/documentation/2.3.x/ConfiguringHttps

activator -Dhttp.port=disabled -Dhttps.port=443 -Dhttps.keyStore=conf/example.com.jks -Dhttps.keyStorePassword=<password> -jvm-debug 5005
