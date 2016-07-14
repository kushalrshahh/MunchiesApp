import os
import csv


with open("./final_reviews.csv", "rU") as menu_reader:
    rows = csv.reader(menu_reader, delimiter = ",")
	for row in rows:
		query = "SELECT * FROM review_info WHERE(user_id ="+ row[1] +"AND business_id = "+ row[-1] + "OR (user_id IN (SELECT friend_id FROM user_friend WHERE user_id =" +row[1] + "AND business_id =" + row[-1]
		
		os.system("hive -e query > temp/3_final.csv")
		print "done"
		break