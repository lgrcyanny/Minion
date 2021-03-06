Minion
======

Minion is a personalized recommendation engine based on multi agent platform Jadex. It's kind of a creative way to build recommendation engine.
The Recommendation algorithm is the classic collobrative filtering algorithm based on Mahout.

Multi Agents
====
The Minion system consists of 8 agents, all of them are built with the BDIAgent, BDI is Belief, Desire(Goal) and Intention(Plan).<BR>
Multi Agent System(MAS) is different from object-oriented system, which is usually single Thread and based on passive call. MAS system is concurrent, and each Agent is intellent, sociality, autonomy and focus on its BDI. Based on Multi Agent, the recommendation engine can be more personalized.<Br> Meanwhile, Agent Oriented programming model is a creative method, the system can be more loose coupling.<BR>

The Agents in the Minion system:<BR>

+ UserBDI<BR>
Provide a simple UserInterface to get user input and behaviors, make request to RecommenderBDI to get Recommended items.
+ RecommenderBDI<BR>
Controll the whole process of generating recommendations. Make use of the services provided by DataMiningBDI, SearchBDI and RecommendationBuildBDI.
+ DataMiningBDI<BR>
Make Request to StrategyBDI to get algorithm strategy. If strategy is UserCF, then request UserCFBDI to do DataMining job, else if is ItemCF, request ItemCFBDI to do datamining job.
+ StrategyBDI<BR>
Make algorithm strategy based on the self similarity of the items which the user has rated. Now the self similarity is calcuated by stadard deviation.
+ ItemCFBDI<BR>
Data mining with ItemCF algorithm.
+ UserCFBDI<BR>
Data mining with UserCF algorithm.
+ SearchBDI<BR>
Since the Recommended Items generated by DataMiningBDI is not detail enough. The SearchBDI search Database to generated details about the recommended items.
+ RecommendationBuildBDI<BR>
Build recommendation results for Display.

BTW, these agents is communicated with active components using service interface with goal delegation.<BR>
See more about the mechanism on [Jadex Tutorial](http://www.activecomponents.org/bin/view/BDI+V3+Tutorial/07+Using+Services)

Related Technology
============
1. [Jadex BDI Version 3](http://www.activecomponents.org/bin/view/BDI+V3+Tutorial/01+Introduction)
2. [Apache Mahout](http://mahout.apache.org/)
3. [MySQL](http://www.mysql.com)
4. [Maven](http://maven.apache.org)
5. J2SE 1.6

The Data Set
========
The Minion system adopts the [MovieLens 1M dataset](http://grouplens.org/datasets/movielens/).


Installation
==============
The following steps is available for MacOS or Linux system.

1.git clone https://github.com/lgrcyanny/Minion<BR>

2.Install MySQL<BR>

3.Download [MovieLens 1M dataset](http://grouplens.org/datasets/movielens/)<BR>

4.Create database movielens<BR>
Import the movielens.sql into database:<BR>
```sh
$ mysql -u root -proot movielens < movielens.sql
```

5.Import the 1M data into database<BR>
```sh
$ mysql -u root -p
mysql>  load data local infile 'ratings.dat' into table tbl_ratings fields terminated by '::'  enclosed by '' lines terminated by '\n' (userid, movieid, rating, timestamp);
mysql>  load data local infile 'movies.dat' into table tbl_movies fields terminated by '::'  enclosed by '' lines terminated by '\n' (movieid, title, genres);
mysql>  load data local infile 'users.dat' into table tbl_users fields terminated by '::'  enclosed by '' lines terminated by '\n' (userid, gender, age, occupation, zipcode);
```
<BR>
6.Download [Eclipse IDE for Java EE DevelopersEclipse IDE for Java EE Developers](http://www.eclipse.org/downloads/)<BR>

7.Run maven build on Minion project, then all dependencies will be downloaded automatically.<BR>

8.Run Minion Application<BR>
Open Run configurations, choose project: mas-recomm-engine, Main class jadex.base.Starter<BR>

9.After the Jadex Control Center(JCC) opened, add the classpath which is like "mas-recomm-engine-0.0.1-SNAPSHOT.jar"<BR>

10.Start "RecommendationService.application.xml"<BR>

The installation is complicated, good luck and be careful.








