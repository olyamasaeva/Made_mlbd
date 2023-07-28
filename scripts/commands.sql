# create table from csv

CREATE TABLE artists 
    (mbid STRING, artist_mb STRING, 
    artist_lastfm STRING, country_mb STRING, 
    country_lastfm STRING, tags_mb STRING, 
    tags_lastfm STRING, listeners_lastfm BIGINT, 
    scrobbles_lastfm BIGINT, ambiguous_artist BOOLEAN) 
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
    TBLPROPERTIES("skip.header.line.count"="1");

LOAD DATA LOCAL INPATH '/opt/artists.csv' INTO TABLE artists;

# Task 1 - maximum scrobbles artist

#command
select artist_lastfm, scrobbles_lastfm
from artists
order by scrobbles_lastfm desc
limit 1;

# result
+----------------+-------------------+
| artist_lastfm  | scrobbles_lastfm  |
+----------------+-------------------+
| The Beatles    | 517126254         |
+----------------+-------------------+

# Task 2 - most popular lastfm tag

# commands

create table tags as select explode(split(tags_lastfm, ';')) as tag from artists;

select tag, count(tag) as count from tags
where tag <> ''
group by tag
order by count desc
limit 1;

#result

+-------------+---------+
|     tag     |  count  |
+-------------+---------+
|  seen live  | 325112  |
+-------------+---------+

#task 3 - 10 most popular artist who sing 10 most popular tags

create table tags_most_popular as (select tag, count(tag) as count from tags
where tag <> ''
group by tag
order by count desc
limit 10);

create table tags_artists as
select artists.listeners_lastfm, artists.artist_lastfm, one_tag as tag
from artists
lateral view explode(split(tags_lastfm, ";")) tmp_tag_table AS one_tag;


select distinct artist_lastfm, listeners_lastfm
from tags_artists
cross join tags_most_popular on tags_artists.tag=tags_most_popular.tag
order by listeners_lastfm desc
limit 10;


# result
+------------------------+-------------------+
|     artist_lastfm      | listeners_lastfm  |
+------------------------+-------------------+
| Coldplay               | 5381567           |
| Radiohead              | 4732528           |
| Red Hot Chili Peppers  | 4620835           |
| Rihanna                | 4558193           |
| Eminem                 | 4517997           |
| The Killers            | 4428868           |
| Kanye West             | 4390502           |
| Nirvana                | 4272894           |
| Muse                   | 4089612           |
| Queen                  | 4023379           |
+------------------------+-------------------+

