require 'json'

File.open("./yelp_academic_dataset_review.json").each_line do |line|
  j = JSON.parse(line)
  puts j["user_id"] + "," +
    j["review_id"] + "," +
    j["stars"].to_s + "," +
    j["date"] + "," +
    j["text"].gsub(/,/, ';').gsub(/\n/," ").gsub(/"/,"'") + "," +
    j["type"] + "," +
    j["business_id"]
end
