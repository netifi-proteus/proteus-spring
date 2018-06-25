# springboot-demo
An example of how easy it is to orchestrate interactions between multiple [Spring Boot](https://spring.io/projects/spring-boot) microservices using [Netifi Proteus](https://www.netifi.com).

The example works as follows:

1. The client generates random alphabetic strings.
2. The client streams the randomly generated strings to the vowelcount microservice over a bidirectional channel.
3. The vowelcount microservice splits the incoming strings into a stream of individual characters which it then sends one by one to the isvowel microservice using request/reply semantics.
4. The isvowel microservice determines if the character is a vowel and sends back an appropriate boolean response to the vowelcount microservice.
5. If a vowel was identified by the isvowel microservice, the vowelcount microservice increments its internal vowel counter and streams a message to the client with the latest vowel count.

![architectural diagram](diagram.png)

## Project Structure
This example project is structured as follows:

* [client](client) - Client that streams random strings to VowelCount service
* [isvowel-idl](isvowel-idl) - Service contract exposed by the IsVowel service
* [isvowel-service](isvowel-service) - IsVowel service implementation
* [vowelcount-idl](vowelcount-idl) - Service contract exposed by the VowelCount service
* [vowelcount-service](vowelcount-service) - VowelCount service implementation

## Prerequisites
This example requires a running Proteus Broker.

## Running the Example
TODO

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/netifi-proteus/proteus-spring/issues).