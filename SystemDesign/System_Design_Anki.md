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

## System Design Peer Interviews

1. https://www.pramp.com/
1. https://discord.com/channels/806944316941402133/816211520480084009
1. https://discord.com/channels/821903927565615145/829905305671303170
1. https://discord.com/channels/477997689867468800/714846876624748676


## System Design Resources Recommended
1. https://www.educative.io/courses/grokking-the-system-design-interview
1. https://github.com/donnemartin/system-design-primer
1. https://www.youtube.com/playlist?list=PLCfguwhZH5DnHl2yldI781yR6FAgky0Np
1. https://drive.google.com/file/d/1m6UgeWa0ttIdAIJ5Y1NljGE3_nOPlSe9/view
1. https://www.youtube.com/channel/UC9vLsnF6QPYuH51njmIooCQ

## System Design Peer Interviews - Others

1. http://highscalability.com/all-time-favorites/
1. http://highscalability.com/blog/category/example  
1. http://www.hiredintech.com/system-design
1. https://courses.systeminterview.com/courses/system-design-interview-an-insider-s-guide?ref=ec9cc5
1. https://docs.google.com/document/d/1iKk6vJbWtI02AllnIEZTrKWQb4dT2QthJdRt05vq6Hw/edit
1. https://engineering.fb.com/2014/08/08/production-engineering/making-facebook-s-software-infrastructure-more-energy-efficient-with-autoscale/
1. https://gist.github.com/vasanthk/485d1c25737e8e72759f
1. https://github.com/shashank88/system_design
1. https://hackernoon.com/10-tips-for-using-diagrams-to-ace-the-system-design-interview-906p3609
1. https://lethain.com/introduction-to-architecting-systems-for-scale/
1. https://matthewdbill.medium.com/back-of-envelope-calculations-cheat-sheet-d6758d276b05#:~:text=Back%20of%20envelope%20calculations%20is,expected%20in%20system%20design%20interviews
1. https://medium.com/@anilkkurmi/top-20-infoq-presentation-to-prepare-for-system-design-interview-ad218fab80dd
1. https://www.hiredintech.com/classrooms/system-design/lesson/52
1. https://www.youtube.com/channel/UCJ8590hU1VY8YcZb7k5-IhQ
1. https://www.youtube.com/channel/UCkQX1tChV7Z7l1LFF4L9j_g
1. https://www.youtube.com/playlist?list=PLA8lYuzFlBqAy6dkZHj5VxUAaqr4vwrka
1. https://www.youtube.com/watch?v=3loACSxowRU&list=PLhgw50vUymycJPN6ZbGTpVKAJ0cL4OEH3&ab_channel=codeKarle
1. https://www.youtube.com/watch?v=ZgdS0EUmn70


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

## System Design - Reference (from comments - https://www.amazon.com/dp/B08B3FWYBX/ref=cm_cr_dp_d_vote_lft?ie=UTF8&voteInstanceId=R4JVS78AO38EX&voteValue=1&csrfT=gliD0sT3FjR1bD94shUAM1JjKtkKracQ9MuvL0gAAAABAAAAAF%2B2IIFyYXcAAAAA%2B4kUEk%2F7iMGR3xPcX6iU#R4JVS78AO38EX&)

-- designing data-intensive applications. Highly recommended.
-- system design primer github repo. Highly recommended free resource.
-- Leetcode discussion forum about system designs.
-- Grokking the system interview course. This is an ok resource but not very deep.
-- Various youtube channels. I like channels like Tushar Roy, System Design Interview, Success in Tech, etc. There are a lot more but I found them most useful for senior engineer positions.
-- Various tech blogs: Facebook, Netflix, Uber, AirBnb, etc. Those tech blogs are extremely valuable to help us understand real-life systems.
-- highscalability website. The website contains lots of real world systems.
-- InfoQ youtube channel. Many tech companies talk about how they scale their systems at infoQ. I find sometimes it’s quite hard to find useful videos on google but when I narrow down it to a specific channel, it’s much easier to find. For example, I found a lot of useful tech talks about uber there. It’s invaluable when I interviewed there.

## How to create anki from this boot mock question file

```
mdanki System_Design_Anki.md System_Design_Anki.apkg --deck "System_Design_Anki::MdAnki::WebSecurity"
```
