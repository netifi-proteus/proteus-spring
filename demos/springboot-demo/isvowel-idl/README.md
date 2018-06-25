# isvowel-idl
This is the Proteus service contract exposed by isvowel-service.

## How does this work?
Proteus services are created by first defining their contract as a proto file. Once you have defined the contract, Proteus 
does the hard work of creating services and clients for you by automatically generating all of the boilerplate communication
code for you.

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