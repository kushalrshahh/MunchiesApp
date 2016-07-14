import csv

count = 0
row1 = 0
with open("./yelp_academic_dataset_user.csv", "rU") as reader:
	rows = csv.reader(reader, delimiter = ',')
   	for row in rows:
		row1 +=1
		if(row[2] == "0"):
			print row
			count += 1
print count, row1
