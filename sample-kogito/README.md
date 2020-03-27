# org.kie.kogito.kogito-quarkus-archetype - 0.8.1 #

# Running

- Compile and Run

    ```
     mvn clean package quarkus:dev    
    ```

- Native Image (requires JAVA_HOME to point to a valid GraalVM)

    ```
    mvn clean package -Pnative
    ```
  
  native executable (and runnable jar) generated in `target/`

# Test your application

Generated application comes with sample test process that allows you to verify if the application is working as expected. Simply execute following command to try it out

```sh
curl -X POST "http://localhost:8080/WelcomeHome" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{}"
```

Once successfully invoked you should see feedback in the console of the running application.

# Swagger documentation

Point to [swagger docs](http://localhost:8080/swagger-ui/) to interactively query the generated endpoints.
