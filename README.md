# amount2text project

This project is implemented using https://quarkus.io/ with Kotlin. I wanted to investigate this framework.
It's known for small images, that starts up very fast and leaves small memory footprint.
For some reason exception mapping is not working correct in native mode - Its clear that it's still a young framework

## Build and run the entire application in Docker

```shell script
 docker build -t lrn/amount2text . && docker run -i --rm -p 8080:8080 lrn/amount2text
```

Then test it by go to a browser at http://localhost:8080 and follow swagger link. Or you can just curl it with e.g:

```shell script
curl -X POST "http://localhost:8080/" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"input\":420.42}"
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

## Testing the application

The application can be tested using:

```shell script
./gradlew test
```

A test report is generated in the build/reports folder



