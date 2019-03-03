mvn clean install
export CONFIG_SERVER_VERSION=1.0.0-SNAPSHOT
export SECURITY_SERVICE_VERSION=1.0.0-SNAPSHOT
export ACCOUNT_SERVICE_VERSION=1.0.0-SNAPSHOT
export PRODUCT_SERVICE_VERSION=1.0.0-SNAPSHOT
export CART_SERVICE_VERSION=1.0.0-SNAPSHOT
export WEB_BACKEND_VERSION=1.0.0-SNAPSHOT
export profile=dev
#start configuration server: default port = 8888
java -jar ./cart4j-configuration-server/target/cart4j-configuration-$CONFIG_SERVER_VERSION.jar
#Start security service: default port = 8082
java -jar ./cart4j-security-server/target/security-server-$SECURITY_SERVICE_VERSION.jar
#Start account service: default port = 8083.
java -jar ./cart4j-account-service/target/cart4j-account-service-$ACCOUNT_SERVICE_VERSION.jar
#Start product service: default port = 8081
java -jar ./cart4j-product-service/target/cart4j-product-service-$PRODUCT_SERVICE_VERSION.jar
#Start cart service: default port = 8085
java -jar ./cart4j-cart-service/target/cart4j-cart-service-$CART_SERVICE_VERSION.jar
#Start admin backend: default port = 8088
java -jar ./cart4j-web-admin-backend/target/cart4j-web-backend-$WEB_BACKEND_VERSION.jar