# Lab Journal: Exercise 1

Authors: Pouyan Rezakhani,  Dhivyabharathi Ramasamy

# Lab Journal: Exercise 1



By calculating the first order condition of the given utility function $u(x)$ we can easily find the interior maximum. Since the utility function is quasiconcave and continuous and is differentiable with respect to $x$.

If $x^*$ solves the following first-order condition then it would solve the consumer's maximization problem subject to given constraints. That is,

$ \frac{\partial u}{\partial h_{work}}
   = \frac{-1}{24-h_{work}} + \frac{0.6 (h_{work}-6)^{-0.6}}{(h_{work}-6)^{0.6}} = 0$
   
   Solution of the above FOC would be $h_{work} = 12.75$ and  $ h_{leisure} = 11.25$.
   The only part needed to change in the Hermit class is the following part in produce method.
   
   ``` java
   
   double plannedLeisureTime = currentManhours.getAmount() *11.25/24;
   
  ```

*Other methods such as trying binary search within an appropriate interval was also possible but not really efficient. Since it is vain to do with more what could be done with fewer.
* We could also use gradient descent methods to solve the above first-order condition in Java. 


 
