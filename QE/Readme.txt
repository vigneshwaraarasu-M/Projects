
This SQL Query Engine will execute selected SQL queries based on the requirements

Steps for executing it in Eclipse:
* Please import this folder into eclipse and then execute the driver class
* Please execute com.queryengine.driver.Driver class

Steps for executing it in command prompt

* If you are executing this class in command prompt. you need to change the file location details
* The com.queryengine.tabledefinition.Table_def.java file need to be edited at line number 30. That is "table_prop.load(new FileInputStream("./src/table_mapping.properties"));"
* Mapping.properties  file will contains table and file mapping details.
* After updating save it and execute it