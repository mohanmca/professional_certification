## Planning for AWS

* Choosing instance type based on general recommendations can result in signficant overspend and performance penalty
  * We should choose based on specific requirement of the application
  * Use on-premise footprint metrics on your existing environment
* Optimize for performance and cost (high performance might also cost more)
* Use automation to get it right from the begining
* Model entire application instead of instance-type
* Cloud requires automation (it shouldn't be considered as extension to existing legacy replacement)

## AWS Cost optimization

* Continuously measure
* Pay for what you need than "Pay for what you use"
* Use consolidated billing
* Use AWS Organization to centrally manage governance policies
* Prefer convertible RI if not standard RI

## AWS Governance

* AWS native tools vs CMP (cloud management platform)
* Procure RI centrally
* Use master account for centrally procure, manage the lifecycle of RI
* Use resource tagging
  * Useful in many ways
* AWS Budgets for quotas and entitlements

## Think beyond EC2

* Poorly architected lambda would cost more than EC2 equivalent
  * Lambda cost is based number of invocation and memory allocation

## Reference
* [Top 10 Strategies to Manage Cost and Continuously Optimize AWS](https://pages.hypergrid.com/hubfs/EBOOK-10%20Strategies%20to%20Continuously%20Optimize%20AWS.pdf)  
# [Badri Venkatachari](https://www.youtube.com/watch?v=p9dnG663n9Q)
