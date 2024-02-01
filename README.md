# Description
It is a desktop application which has a single window where user chooses 1 of 5 databases (transactions1 – transactions5) and enters the minimum support and confidence values. There are 2 text areas containing results: the left one shows the number of items and input transactions, while the right one generates association rules in the format X => Y, where X and Y are the itemsets.
Graphical User Interface is implemented using Java Swing library.


![image](https://github.com/MovieTone/AprioriAlgorithmGUI/assets/15722914/fd1ada5a-e7cf-4f4b-a07e-4c080c896889)

![image](https://github.com/MovieTone/AprioriAlgorithmGUI/assets/15722914/73a98396-a788-4f9d-9120-50393b64ae8e)

# Built with
- Java
- Gradle

# Structure

## Class Diagram
![image](https://github.com/MovieTone/AprioriAlgorithmGUI/assets/15722914/2908439a-e212-476f-9dbc-b10064cfffda)

## ERD diagram
RDBMS used to create a database of transactions is Apache Derby, which does not require to create servers or make any complex connections. On the contrary, it just creates and keeps files containing data.
If you run the application for the first time, it automatically creates 5 databases and inserts data.

![image](https://github.com/MovieTone/AprioriAlgorithmGUI/assets/15722914/31f63cd9-bb45-49fb-a8c7-3908553c4df6)


