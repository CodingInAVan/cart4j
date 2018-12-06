# Security Server

## Authentication Server implemented with Spring OAuth2.
 
### Prerequisite
- You will need the lombok plugin to compile it.
Please refer this <a href='https://projectlombok.org/setup/overview'>page</a>
- Mysql 5.7+ 

### Step

1. Do maven install. <br/>
```mvn clean install```

2. For the first run, `install` profile needs to be included to generate database tables. <br/>
```java -Dspring.profiles.active=dev -Dspring.profiles.include=install -jar security-server-1.0.0-SNAPSHOT.jar```

3. With the install profile, it will ask to create your admin username and password.

4. After you created the admin user, it will ask to create client information for api accesses (managing resources, clients, users, roles and scopes) for this security service.

5. Shutdown the server and rerun it with `noninstall` profile <br/>
```java -Dspring.profiles.active=dev -Dspring.profiles.include=noninstall -jar security-server-1.0.0-SNAPSHOT.jar```
