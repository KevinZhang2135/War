* What is the point of the interface List? What does it mean for a Java class
* (e.g., LinkedList) to implement an interface?

An interface allows to specify requirements for a class. For the List interface,
it specified the methods needed to be implemented and their required parameters
and return types. When a class implements an interface, it has to satisfy all
the requirements list in the interface.


* Describe any serious problems you encountered.

Due to the nature of War, there was a chance of a infinite recursion error after
declaring "war," so the code had to check if each deck has less than 4 cards.