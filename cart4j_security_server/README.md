# Security Server

## Implemented with Spring OAuth2.
 
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
    ```
    Please input username for amdin:
    ADMIN
    Please input email for amdin:
    admin@cart4j.com
    Please input password for admin:
    12345
    
    Please input password for client:
    12345
    ```
4. After you created the admin user, it will ask to create client information for api accesses (managing resources, clients, users, roles and scopes) for this security service.

5. Shutdown the server and rerun it with `noninstall` profile <br/>
```java -Dspring.profiles.active=dev -Dspring.profiles.include=noninstall -jar security-server-1.0.0-SNAPSHOT.jar```

### Request Token

```
curl -X POST -u 'CLIENT-SECURITY-API:12345' -d 'username=ADMIN&password=12345&grant_type=password' http://localhost:8082/oauth/token
```
and you will get something like this: 
```
{"access_token":"7a6d895b-05ee-4351-902a-91f83ca0b0b3","token_type":"bearer","expires_in":43199,"scope":"SECURITY_API_ADMIN","roles":["USER_AUTH_ADMIN"]}
```

### API Access
To get a list of clients

```
curl -H "Authorization: Bearer 7a6d895b-05ee-4351-902a-91f83ca0b0b3" http://localhost:8082/api/auth/client
```
