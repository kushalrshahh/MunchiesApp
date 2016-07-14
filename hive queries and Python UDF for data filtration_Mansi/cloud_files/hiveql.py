add jar /home/cloudera/Desktop/csv-serde.jar;

CREATE TABLE review_info(user_id string, review_id string , stars float, date timestamp, review string, type string, business_id string) ROW FORMAT DELIMITED FIELDS TERMINATED BY ",";

load data local inpath '/home/cloudera/Desktop/final_reviews.csv' into table review_info;



CREATE TABLE check_menu(business_id string, menu array<string>)ROW FORMAT DELIMITED FIELDS TERMINATED BY ",";
load data local inpath '/home/cloudera/Desktop/temp/test.csv' into table check_menu;

select 
transform(business_id, menu)
using 'python /user/cloudera/split.py' as dish
from menu_info;



CREATE TABLE user_friend(user_id string, user_name string friend_id string)ROW FORMAT DELIMITED FIELDS TERMINATED BY ",";
load data local inpath '/home/cloudera/Desktop/temp/test.csv' into table user_friend;

CREATE TABLE review_split(business_id string, dish_name string, review string)ROW FORMAT DELIMITED FIELDS TERMINATED BY ",";
load data local inpath '/home/cloudera/Desktop/temp/target_duplicate_removed.csv' into table review_split;


select a.user_id, a.business_id, b.user_name
from review_info a RIGHT OUTER JOIN user_friend b
GROUP BY a.business_id
HAVING

select business_id, dish_name, count(dish_name) from review_split group by business_id, dish_name;

