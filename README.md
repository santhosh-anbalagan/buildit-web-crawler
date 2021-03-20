# _Web Crawler_

Problem Statement :-  Design a simple web crawler. The crawler should be limited to one domain. Given a starting URL – say http://wiprodigital.com  - it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.

### Prerequisite
|  | Version |
| ------ | ------ |
| JDK | 1.8 |

### Plugin
- Maven
- 
### Configure
- application.properties
  1. Change the port number
    ```
    server.port = <port_number>
    ```
## Build & Run

1) Clone this repository in local machine (git clone ..)
2) Build
    ```
    mvn clean
    mvn install
    ```
3) Run
    ```
    java -jar target/crawler-1.0-SNAPSHOT.jar
    ```
##### Alternatively
- To avoid mavent build commands there's a built jar in `jar` directory.
- If you dont want to run without commnad line, you can use any IDE and run as Crawler java class

## Improvement Areas

1) Implementing retry logc if the connection link timeout.
2) Add maximum depth for any link to process to control the crawling.
3) Can do better in Executr service
4) Adding test cases
5) Response in XML format

