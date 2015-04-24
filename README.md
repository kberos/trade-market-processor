Trade Message Processor
===============

An exercise test for Currencyfair hiring.

This application is a sample of how a Market trade messaging processor could be. It is not as sophisticated and production ready as the real one would be,
but close. It mostly demonstrates the architecture, the aproach of solving the problem, the scalability considerations, and part of the testing methodology.


The system must be able to handle many requests and render in real time to the client the results. The technology choices were made considering the current choices,
and the available time I had. It could definitely be ever better.
 Spring is used extensively both in the security, API controllers, of course IoC and everywhere it could help with the integration of the technologies I used.
At the front there is a static page tha contains 4 charts. 3 of the same type that display the real time rate for EUR/USD, EUR/GBP and EUR/JPY rates, and also
a bar chart that is updated every second with the 3 pairs "buy" volume. The client is using sockJ.js and stomp.js for the web sockets connection management and communication,
and HighCharts/HighStocks for rendering the graphics.
An endpoint /processor/message (POST) is used for the data input from the user. The user is able to send TradeMessages. Validation is applied since nothing can be Null.
The processorService then converts the object to json and adds it to a RabbitMQ queue. The queue is a good choice for "message handling and processing later".
It acts as a buffer and allows the system to scale. In a more complex production system the queue could be part of a spring Integration configuration where hooks and filters
could continually handle and analyze the messages as they go through the pipeline.
A listener (only one in our case, but it could be many instances/processors) listens and receives the messages. It then persists them in a mongoDB database for later use,
and after in processes/converts the message, it sends it to the client through the initialized web socket. The client checks the type of message, since more than one type
can arrive through the same channel, and if it is type "R" (rate) it finds the corresponding chart by the pair Id and renders it.
 On a different timeline, using a Scheduler, every 1 second, the server will use the same channel to communicate to the client, aggregated information about the volume of the
 pairs. The server uses the Db to extract aggregated information and then, after sending it to the client, the client similarly to the way it handles the rates, it handles the
 volumes and updates the volumes chart.

 The system has no rate limit although it should have. The limit can be set in various levels, from inside the app using timers, caches and databases, inside the tomcat container, or before that.
 The best choice is as HIGH as POSSIBLE. Perfect solution is either hardware in front of the servers or at least in the apache/OS level or using a solution like nginx.
 It was not implemented due to limited time.

 The system has a basic test coverage mostly for demonstration. The unit testing is not far from complete but of course Acceptance, stress and resilience testing should be implemented.
 There are some TODOs in the code that explain what is missing.

![Alt text](http://MarketTradeProcessor.jpg "High Level Architecture")
 Bellow are the points that need improvement
 - Unit Test code coverage.
 - System, resilience, stress accepts tests.
 - More friendly API responses and more proper validation.
 - Rate limit in front of the API endpoint.
 - Extra security in the endpoint via OAuth or similar.
 - Management of the connections and the queues to allow singe communication with the client if needed instead of broadcasting.


=======
# trade-market-processor
