import csv

with open("./yelp_academic_dataset_user.csv", "rU") as reader:
	rows = csv.reader(reader, delimiter = ',')
   	for row in rows:
		friends = row[3].split(',')
		try:
                        for friend in friends:
                                friend = friend.split("'")
                            ###    print friend
                                print row[-7] + ", " + row[-8] + ", "+ friend[1]
                except:
                        pass
