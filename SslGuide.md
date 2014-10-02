# SSL Guide
You can [generate certificate](https://www.playframework.com/documentation/2.3.x/CertificateGeneration) from java, then convert to key + pem file for using in HipChat SSL configuration.



Or, If you have key + pem files (follow [HipChat guide](https://confluence.atlassian.com/display/HC/Creating+or+Obtaining+an+SSL+Key+and+Certificate))
then you need convert to jks for running with Play. See bellow.

## Convert key + pem to jks

1. convert key + pem to p12
```
openssl pkcs12 -export -in example.com.crt -inkey example.com.key -out example.com.p12
```

2. Download & install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

3. Download & run [portecle](http://portecle.sourceforge.net/)
    1. New Keystore
    2. Import key pair
    3. save

Refs: [Convert key + pem to jks](http://java.akraievoy.org/2011/02/convert-key-pem-to-jks-java-keystore.html)

## remote debug play with https support
Refs: [ConfiguringHttps](https://www.playframework.com/documentation/2.3.x/ConfiguringHttps)

Example:
```
activator -jvm-debug 5005 -Dhttp.port=disabled -Dhttps.port=443 -Dhttps.keyStore=conf/chanpro2.jks -Dhttps.keyStorePassword=<password>
```
