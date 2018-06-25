# isvowel-idl
This is the Proteus service contract exposed by [isvowel-service](../isvowel-service).

## How does this work?
Proteus services are created by first defining their contract as a proto file. Once you have defined the contract, Proteus 
does the hard work of creating services and clients for you by automatically generating all of the boilerplate communication
code for you, in whatever supported language you like.

It's then as easy as importing the generated package. That's it!

## What does a contract look like?
Let's take a look at this service's contract.

    syntax = "proto3";
    
    package com.netifi.proteus.demo.isvowel.service;
    
    option java_package = "com.netifi.proteus.demo.isvowel.service";
    option java_outer_classname = "IsVowelServiceProto";
    option java_multiple_files = true;
    
    service IsVowelService {
      rpc IsVowel (IsVowelRequest) returns (IsVowelResponse) {}
    }
    
    message IsVowelRequest {
      string character = 1;
    }
    
    message IsVowelResponse {
      bool isVowel = 1;
    }
    
The isvowel-service is responsible for taking in a character and then returning a boolean value indicating whether or not the 
character is a vowel.

### Service Metadata
In the contract above we first see some metadata about the java packaging we would like for the generated services and clients.

    syntax = "proto3";
    
    package com.netifi.proteus.demo.isvowel.service;
    
    option java_package = "com.netifi.proteus.demo.isvowel.service";
    option java_outer_classname = "IsVowelServiceProto";
    option java_multiple_files = true;

### Service Definition
Next, we see the most important part. The definition of the service.

    service IsVowelService {
      rpc IsVowel (IsVowelRequest) returns (IsVowelResponse) {}
    }
    
As you can see, `IsVowelService` exposes a single method `IsVowel` that takes an `IsVowelRequest` and returns an `IsVowelResponse`.

### Message Definitions
The definitions of `IsVowelRequest` and `IsVowelResponse` are defined directly below the service.

    message IsVowelRequest {
      string character = 1;
    }
    
    message IsVowelResponse {
      bool isVowel = 1;
    }