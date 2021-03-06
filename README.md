# Micronaut / Single Page App Demo

This project demonstrates use of a Single Page App (written in Vue.js) with a microservices backend (using Micronaut).

The project is a Gradle multi-project build, with subprojects for `inventory` ("business logic"), `auth` (user authentication), `gateway` (API gateway for the SPA), and `frontend` (Vue.js SPA).

## Loading in IntelliJ

~~You need to install the Lombok plugin for IDEA. Open the Settings panel (Ctrl + Alt + S). Search for "Plugins", then search for "Lombok" in the plugins. Find the plugin and install it.~~


## Usage

The `inventory`, `auth` and `gateway` services expect Consul to be running at `localhost:8500`. This can be most easily accomplished using Docker:

The `auth` application has been changed to DB authentication and requires mysql to be running, refer to `config.properties` in base folder which contains mysql configuration db host, name, username, password etc 
```
docker run -d -p8500:8500 consul
```

There is a killnode shortened to kn in my env or in my `~/.bashrc`
```
function kn {
 kill -9 $(netstat -pln 2>/dev/null |grep LISTEN|grep node|awk '{print $7}'|awk -F"/" '{print $1}');
}
export kn
```





Once Consul is running, the services can be started up using Gradle (order is suggested, not required, but all three services should be running prior to making requests against the Gateway).

Start ALL applications in one go. 
```
 kn; MICRONAUT_CONFIG_FILES=$PWD/config.properties ./gradlew gateway:run frontend:serve inventory:run auth:run --parallel
```

---


#### Authentication notes:

There is a slight delay in authenticating. Please refer to [DatabaseAuthenticationProvider.java](https://github.com/vahidhedayati/micronaut-spa-webinar/blob/master/auth/src/main/java/com/objectcomputing/auth/DatabaseAuthenticationProvider.java#L59). 
This commented out segment breaks down the optional process in which highlighted segment `passwordEncoder.matches` causes this.  


#### Vue 3 demo site 
Been watching videos on the latest (vue 3) and decided to write a front end using Vue 3 - it is similar to exising vue 2 front-end. 
To use vue 3 front-end :

`./gradlew frontend-vue3:serve`

alternatively  go into `frontend-vue3` folder and run `npm run serve` and site will appear on `http://localhost:8081/` 


 
---- 
####Otherwise

Start the `inventory` service:

```
MICRONAUT_CONFIG_FILES=$PWD/config.properties ./gradlew inventory:run
```

Start the `auth` service:

```
MICRONAUT_CONFIG_FILES=$PWD/config.properties ./gradlew auth:run
```

Start the `gateway` service:

```
MICRONAUT_CONFIG_FILES=$PWD/config.properties ./gradlew gateway:run
```

Start the `frontend` with either Gradle or NPM/Yarn:

```
./gradlew frontend:serve
```
...or
```
cd frontend; npm install; npm run serve
```

The Gateway will run at `http://localhost:8080`

Access the frontend UI at `http://localhost:3000`

Users (usernames case sensititve):

`acme`/`password`
`makers`/`password`
`admin`/`password`

## API Documentation

The API documentation for the Gateway can be found at `http://localhost:8080/swagger-ui/`
