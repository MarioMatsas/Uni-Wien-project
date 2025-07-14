## Instructions:
In the root directory run the following on cmd:
* javac Clieant_side\*.java Server_side\*.java Shared_elements\*.java
* And now on one terminal: java Clieant_side.Client
* And on another: java Server_side.Server

You will now be able to test all the use cases!

## Why I chose pattern X for use case Y:

* Use case 1 --> Fire and forget: We usually plan on sending many many log messages one by one, so even if some don't actually reach the destination, it's no big deal + we don't await a response, thus fire and forget is the best choice. 
* Use case 2 --> Sync with server: Here we want to be sure that the space will be released (the message reached the destination) + we don't wait for any response, thus sync with server fits in great.
* Use case 3 --> Polling: Here it makes sense to want some type of confirmation since we are sending a large amount of data at the same time + we don't need to get immediate confirmation, rather it's better if we check it whenever we want. Polling makes sense.
* Use case 4 --> Result callback: We want results to immediately appear on the client side when they are available, so result callback is the obvious choice.

## Why I chose UDP/TCP for asynchronous invocation pattern Y:

* Fire and forget: UDP because we want to send a message fast, don't want a response and we don't mind if the message gets lost/ doesn't always reach the destination.
* All the rest use TCP. For sync with server we use it because we need the server acknowledgement (something we can easily get by using TCP), while for polling and result callback we use it because we want to be sure that data reaches the destinaton (in the correct order) + the connection is persistent, which is a positive since we also want to receive a response.  