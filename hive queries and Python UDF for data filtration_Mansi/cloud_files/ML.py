#!/usr/bin/env python
import csv
import re

target = open('target.csv', 'wb')


with open('final_copy.csv', 'rU') as menu_reader:
	rows1 = csv.reader(menu_reader, delimiter = ',')
	for row1 in rows1:
		#print row1
		#line = row1.split('"')
		#dish_names = filter(None, row1[12].split(','))
                dish_names = row1[12].split(',')
                #print dish_names
		with open('final_reviews.csv', 'rU') as reviews_reader:
			rows2 = csv.reader(reviews_reader, delimiter = ',')
			for row2 in rows2:
                                #print row2[4]
				#review_line = row2[2].split('"')
				#print sentences
				#row1[0] = row1[0].strip(',')
				#print "menu bid-->" + row1[0] + "review bid-->" + row2[4]
                                try:
                                        if(row1[0] == row2[6]):
                                                for dish_name in dish_names:
                                                        if(dish_name != ""):
                                                                #print dish_name
                                                                sentences = row2[4].split('.')
                                                                for sentence in sentences:
                                                                        if(re.search(dish_name, sentence)):
                                                                                #print sentence
                                                                                if(re.search(dish_name, sentence)):
                                                                                #                print sentence
                                                                                #print row1[0]+','+dish_name+','+row2[1]+','+sentence+'\n'
                                                                                        target.write(row1[0] + ',' + dish_name + ',' + sentence +" end of comment" '\n')
                                        else:
                                                continue
                                except:
                                        continue

target.close()

