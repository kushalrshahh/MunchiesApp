# MunchiesApp

#Menu collection and Alchemy processing folder:

- The final application output can be seen in Output folder

- Src folder contains:

- Menu collection program (CollectMenus.java): here, we are calling Google MAp and

Foursquare APIs to get restaurant specific menus.

- Mapper reducer program to process reviews and update final average scores of the

dishes (munchies.java - driver program, munchiesMapper.java, munchiesReducer.java)

- sentianalysis.java file contain Alchemy API calls: Here, we are calling alchemy API to analyse

tone of the dish specific restaurant comment. This class is being called in munchiesMapper.java

program.

#Hive Queries and Python UDF folder:

this folder contains hive queries and Python scripts/UDFs that we wrote for initial data filtration,

conversion of json to csv format and searching dish specific comment line in the user review.

- udf.py file contains user defined functions that we wrote in python to compare actual dish

name from menu file to diah name in the user reviews.

- review_json_to_csv fille converts data from nested json format to easily readable csv

format.

- hiveql.py file loads the data in hive tables.

app-debug.apk file:

This .apk file contains executable Android application code.

#Munchies-Android- App

This folder contains all the Android activities, layouts, fragments, etc. that we designed to create

fully working Munchies application.
