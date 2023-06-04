## CDN and DNS

* AWS route 53 - DNS service
  * Hosted Zones
  * Domain Types
  * Record Types
  * Routing policies
* AWS CloudFront - CDN Network


## DNS Service

* Hierarchical distributed naming system
* Phonebook of the internet
  * google.com === 172.217.194.113
* TLD
  * Generic Top level domain
  * Domain name could end with (.watch, .clothing)
* GD
  * Geographic Domains (.au, .uk)

## AWS route 53 - DNS service

* DNS Service
* Woks inside and outside the AWS
* secure and reliable routing of requests

## AWS route 53 - Hosted Zone

* information about how to route a domain xyz.com
* Collection of resource record sets hosted by route 53
* Public hosted zone - route traffic in internet
* Private hosted zone - route traffic within VPC
  * We can use any domain we wish


## AWS route 53 - record types

* A   - IPV4
* AAA - IPV6
* CAA
* CNAME - Used as one name as alias for another
* MX - names of our mail server
* NPATR
* NS - Name server to hosted zones
* PTR - map IP to domain name
* SOA
* SPF - No longer recommended, identify of the sender email messages
* SRV
* TXT

## AWS route 53 - routing policy

* Simple
* Failover
* Geo-location
  * Continent, country or state in country
  * When overlap, it would choose smallest (Continent+Country matches, chooses country)
  * Restrict access to only few country
* Geo-proximity
  * Policy based on location + users  
* Latency
  * Chooses lowest latency resources
* Multi-value answer
  * DNS request upto 8 records, and randomly picked
* Weighted
  * Weight of the individual resource record dividey by the sum of the total value in resource record
* Abbreviation - SFGGLMW

## AWS CloudFront - CDN

* CloudFront is AWS's fault-tolerant and globally scalable content delivery network service.
* Speeds up distribution of your static and dynamic content through its worldwide network of edge locations.
* Data is cached, after a set period, this cached data will expire and so AWS CloudFront doesn't provide durability of your data.


## AWS CloudFront Distribution

* Web Distribution
  * Static and dynamic content (html, image, javascript)
  * MediaFiles using HTTP/HTTPS
  * ADD/Upate/Delete/Submit data from web-forms
  * Live-streaming to stream an event real time
* RTMP Distribution
  * Streaming distribution using Adobe Flash Media Service RTMP protocol
  * RTMP doesn't require all media to be downloaded to view
  * It should be served from S3, can't be served from webserver


## AWS - OAI (Origin access identity)

* Works only with S3 based content
* Prevents direct access, can't circumvent


## AWS - Edge distribution

* Which edge locations
* Different caching behaviour
* WAF (ACL) can be enabled