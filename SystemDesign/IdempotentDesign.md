## Idempotent - Run multiple time, but same effect (Free rerun)

* A function f is idempotent if f(f(x)) = f(x).
* Query the database to find all dormant accounts with a balance vs Query the database to find all dormant accounts with a balance, which haven't been charged the fee this month
* "Need to send a notification email when x condition becomes true". 
  * Naive way: during processing, check the condition and call the SendEmail() function. 
  * Idempotent way: Run a query that finds all x conditions, join to a list of notifications based on email+id+time, and only if there's no entry, send the notification and save it to the list.
* "Send a report at midnight". 
  * Naive way: have a cron entry that runs at midnight or run a loop that checks the time, and if midnight, sends the report. 
  * Idempotent way: cross-check against a list of reports that should have been sent, and if missing, send it. Run this check every few minutes.

## What are the known idempotent methods?
* HTTP GET requests are meant to be idempotent
* Common idempotent methods: GET, HEAD, PUT, DELETE, OPTIONS, TRACE
* Common non-idempotent methods: POST,PATCH, CONNECT
* [MDN Idempotent](https://developer.mozilla.org/en-US/docs/Glossary/Idempotent)

## Payment industry
* Payment processor should explicitly state idempotency semantics.
* The operations <charge-the-customer> and <mark-the-customer-as-charged> have to be (to loosely use the term) atomic.
* [Payment API's should be idempotent](https://stripe.com/docs/api/idempotent_requests)
* [Google Idempotency Document](https://developers.google.com/standard-payments/reference/idempotency)

## Improvement over Idempotency

* We can constrain the date range that we are checking to just since the last time process checked, and store this date either in memory or persisted depending on your use case, technology, and how we want system failures/restarts handled.
* If system is down for few-days, don't send all the reports, send only that are latest

## Advantages of Idempotency

* Run any number of instance
* Run any number of times
* If a downstream system can assure idempotency then don't bother making your operation idempotent. Failed payment? Retry. 

## Dis-advantages of Idempotency

* The downside of Idempotency approach, we have to compute the full desired state every few minutes, it can be quite expensive depending on how/what/why you are doing it. 
* It can also be an O(n^2) problem that is fast enough to make it into production and slow enough down the line with more data to wreak havoc.


## Reference
* [Idempotence Now Prevents Pain Later](https://ericlathrop.com/2021/04/idempotence-now-prevents-pain-later/)
* [YCombinator - Idempotence Now Prevents Pain Later](https://news.ycombinator.com/item?id=26726449)