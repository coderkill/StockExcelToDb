Schema Design:

 ![image](https://user-images.githubusercontent.com/26936240/151702115-bdc6bcf7-6b96-4f34-9a39-a267b8c23217.png)

The above is the basic schema diagram for the data for which is presented as follow:
 ![image](https://user-images.githubusercontent.com/26936240/151702235-12281c63-2e23-4af9-8bc7-67df8a220ef3.png)

Symbol is considered to be the primary key of the table Stocks and the foreign key of the table StockInfo. Symbol, StockName and Industry are repeated data and can come several times and are considered to be prime attributes which don’t change overtime. Since they are repeated overtime we can see that this data would cause redundancy if put together with the table stock_info. In order to remove the redundancy we have created another table Stocks which contains the basic details of the stock. 
In the table StockInfo we see that the data is dependent on the Report date and the Symbol
And the rest all are non prime attributes. So in this case Symbol and Report columns together make a composite key. This is because they both together uniquely identify a row in the table. The above table is in 2NF but has a transitive dependency with Industry. The above Stocks table can be further split into 2 tables, Stocks and IndustryType and will be linked with each other using IndustryId (IndustryId is a self generated auto_incremented value) as part of the final design shown below

 ![image](https://user-images.githubusercontent.com/26936240/151702118-4775cfe9-e6db-4d36-b703-3fce953f746b.png)


The above diagram would be in 4NF, this is assuming the StockName is not always unique(Not a candidate key). If the StockName is always unique then it would be a 3NF structure as BCNF(4NF) requires the table to not contain 2 keys which can identify the data (Symbol and StockName). But splitting the table up more does not make sense in terms of efficiency and space. So the above would be considered the highest form of normalization possible.
The 1st query gets the shortest duration and the second gets for longest duration.

Coding task:
 ![image](https://user-images.githubusercontent.com/26936240/151702095-92911667-aa28-4953-a58f-2dbd2773725a.png)


The above is the flow diagram of the code. For clearer perception I have added a HTML file as well:
 

There are 2 approaches which can be taken:
•	Insert every row one by one into the DB
•	Insert all queries in batches.
I have both the approaches implemented. But it’s better to follow the 2nd one as it reduces I/O with DB. Database used is MYSQL.
Scalability:
Another Excel sheet with same Columns can be added into the DB and Recovery of data takes place every time a new run starts. We load all the Industry as well as the Industryid and the stocks into a map, this is done in order to prevent any duplicate data from entering the table.
The objects which are created are the maps to store the unique industry and stocks. This is done in order to reduce the redundancy when inserting into the DB.
