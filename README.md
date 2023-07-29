# Title: Machine Learning in Big Data - Hive Server Setup and Data Insights

## Laboratory work Overview:
In this laboratory work, we focus on leveraging the power of Hive, a data warehousing infrastructure built on top of Hadoop, to interact with big data through SQL-like queries. By setting up a Hive server locally using Docker and connecting to it via user-friendly tools like Hue and Beeline, we simplify the process of extracting meaningful insights from massive datasets.

## Repository Contents:
The repository includes the following components:
- docker/: This folder contains all the necessary Docker configuration files to set up the Hive server locally on your machine. Docker allows for a convenient and scalable environment for running big data tools.
- scripts/: Within this directory, you'll find the scripts for hive access and query
- screenshots/: screenshots with hive accessing via hue
- data/: Empty folder,it is used to data storing
- hm02.pdf: document with laboratory works intrustions from MADE big data academy.

## Getting Started:
 - Navigate to the docker/ folder to build and set up the Hive server locally using Docker use command "docker-compose up" for that
 - Connect to hive:
   - Via hue open the site: https://demo.gethue.com/hue/accounts/login?next=/
   - Via beeline using bash script scripts/beeline.bash
- Download data file atrists.csv  to folder data from site: https://www.kaggle.com/pieca111/music-artists-popularity
- Copy data to docker using command: docker cp data/artists.csv docker-hadoop-hive-parquet_hive-server_1:opt/
- To do queries use script in Hive sql located in scripts/commands.sql

I hope this repository assists you in understanding Hive and its capabilities for big data analysis. By providing easy-to-follow setup instructions and examples of Hive SQL queries, I aim to empower you in your journey of data exploration and insights within big data environments. Happy learning and exploring the fascinating world of Machine Learning in Big Data! 🚀
