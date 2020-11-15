## How to create anki from this boot mock question file

```
mdanki System_Design_Anki.md System_Design_Anki.apkg --deck "System_Design_Anki::MdAnki::WebSecurity"
```


## What kind of question would improve learning System Design kowledge

* Know about things that scale extreemly well
  * IP/TCP/DNS/EMAIL/HTTP/HTTPS
* How they deliver larger and smaller packet with loss
* How to effectively use UDP and use-cases
* Why DNS is reliable, what are all the trade-off

## What kind of question would improve System Design kowledge

* Design Google Search Engine
* Design Youtube
* Design Uber
* Design Dropbox
* Design Uber
* Design AirBnb
* Secure Storage system, secure network systemdddd


## What component knowledge would help to learn

* Core-NoSQL
* NGNix, MapReduce, SSTable, Btree
* Client-Server, Leader Election, Peer-to_Peert, Availability, Scalability, Hashing, Replication, Proxies
* Load Balancer, Polling, Pushing

## IP Protocol notes

* What does IP header contains
  * Version, Total Length
  * TTL, Protocol
  * Sender Address, Destination Address
* What is the size of IP header length including src/dst address - 20 bytes (without options)

## TCP Protocol notes

* What does TCP header contains
  * Source/Destination port number - 4 bytes
  * Sequence number - 4 bytes
  * Acknowledgement Number - 4 bytes
  * Window Size - 65,535 bytes (usual)
  * Window Size - larger for (LFN large fat network)
  * Urgent pointer 


## Latency Comparison Numbers (~2012)

* L1 cache reference                           0.5 ns
* Branch mispredict                            5   ns
* L2 cache reference                           7   ns                      14x L1 cache
* Mutex lock/unlock                           25   ns
* Main memory reference                      100   ns                      20x L2 cache, 200x L1 cache
* Compress 1K bytes with Zippy             3,000   ns        3 us
* Send 1K bytes over 1 Gbps network       10,000   ns       10 us
* Read 4K randomly from SSD*             150,000   ns      150 us          ~1GB/sec SSD
* Read 1 MB sequentially from memory     250,000   ns      250 us
* Round trip within same datacenter      500,000   ns      500 us
* Read 1 MB sequentially from SSD*     1,000,000   ns    1,000 us    1 ms  ~1GB/sec SSD, 4X memory
* Disk seek                           10,000,000   ns   10,000 us   10 ms  20x datacenter roundtrip
* Read 1 MB sequentially from disk    20,000,000   ns   20,000 us   20 ms  80x memory, 20X SSD
* Send packet CA->Netherlands->CA    150,000,000   ns  150,000 us  150 ms

## Miscellaneous Questions

* How many ports are there - 2^16-1 - 65535 (256  * 256) TCP and 65535 UDP ports

## Availability

* Redundancy is key for availability
* What part of the system is extreemly critical and available is more important?
* How many 9s of availablity is required - 99% can accomadate 3.65 days per year
* Increasing every 9s in HA, would double/multiply cost of design/operate
* [Highly Availability](https://en.wikipedia.org/wiki/High_availability)
* Active Reduandancy might leads to problem of leader election


## Caching

* Decreases latency
  * Cache very complex calculation
  * Cache data from higer-latency database / (reduce io operation)
  * 
d

## System Design - Reference

* [System Design Git-books](https://aaronice.gitbook.io/system-design/)
* [Reference for latency](https://gist.github.com/jboner/2841832)
* [System Design Cheatsheet](https://gist.github.com/vasanthk/485d1c25737e8e72759f)
* [System Design Interview Tips](https://github.com/checkcheckzz/system-design-interview)
* [System Design Primer](https://github.com/donnemartin/system-design-primer)