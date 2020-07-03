## AWS Elasticache Overview

* What is cache
* What is ElastiCache Service? Why we need them?
* What are ElastiCache features?

## AWS Elasticache

* WebService create,operate and scale in-memory datastore or cache in the cloud
* To make it more faster (works similar to L2 cache, for applications)
* Most of the applications reads more than write data
* We can horizontally scale web layer, but we can't do the same for DB layer
  * Cache can reduce the load on the DB layer, cache can horizontally scale
* Managed Service (no patching, update)

## AWS Elasticache - When to use?

* Requires scalaing
* To improve performance
* Compatible with MemCached and Redis
* It is located part of Database (under management console)

## AWS Elasicache usecase

 ```txt
 We have a website that provides support information about the range of motorcycles that we sell worldwide. We've sold five million motorcycles since 2010. Our support website usually receives around 100 thousand hits a day, generally from people looking for information about the specifications of our motorcycles and user guides. 
 
 One day, a fault is reported in a hose pipe, commonly used in motorcycle engines. Anyone who has a motorcycle wants to verify that their bike does not use this hose pipe. Luckily, our motorcycles do not use the faulty hose pipe, and we put out a press release stating that fact. 
 
 However, we fear the worst, since last year a similar fault was announced and our website crashed when two million customers checked our website for information about the fault. This time, our website received seven million views, however, the website was able to respond and deliver on those requests, because after the site crashed last year, we implemented Amazon ElastiCache between the web server and the MySQL database, to cache website-based content. 
 
 Now when the web server requests the press release page, the content of that page is delivered out of Amazon ElastiCache, reducing the amount of time it takes the web server to display the press release by removing the need for the web server to request the press release page content from the MySQL database.
 ```

 AWS recommends that you use an ElastiCache Redis (cluster mode disabled) cluster in which situation?

 To handle highly read intensive application

* Runs within VPC
* [Memcached vs Redis](https://aws.amazon.com/elasticache/redis-vs-memcached/)
