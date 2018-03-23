# ajp-tester

## Purpose
Web application for sending simple AJP messages to a Server for testing purposes

## Description
This is a Spring Boot web-app, that can take HTTP requests and convert them to the AJP protocol and relay them to an AJP server.

Once you download and run the web-app, there is a usage page at: "http://localhost:9090"

The HTTP to AJP message conversion is done by the following library:
https://github.com/jrialland/ajp-client

## Usage Detail
This web-app currently supports 2 methods:
(see usage page for more details)

1. AJP CPing - Check that the AJP Server is Up

Example:
http://localhost:9090/ajpCPing?ip=10.10.10.1&port=8009
Description:
Send an AJP CPing request to 10.10.10.1:8009

2. AJP GET - Send GET message to AJP Server; browse a web-page

Example:
http://localhost:9090/ajpGet?ip=10.10.10.1&port=8009&query=/mypage/test
Description:
Send an AJP GET request to 10.10.10.1:8009//mypage/test

## How to Download and Run the Web-app with Java
1. Download the binaries from the latest Releases tag (e.g.: ajp-tester-1.0.0-SNAPSHOT.jar)
2. Make sure you have Java installed
3. Startup scripts are in the ./scripts/ folder.
4. run "java -jar ./ajp-tester-1.0.0-SNAPSHOT.jar --server.port=9090"
5. This will start the Web-app listening on port 9090.
6. To see usage page, in Web Browser navigate to "http://localhost:9090"

## How to Build Project with Maven
1. Download the Project from Github
2. Make sure you have Java and Maven installed
3. run "mvn install" in the root folder - this will create ./target/ajp-tester-1.0.0-SNAPSHOT.jar


