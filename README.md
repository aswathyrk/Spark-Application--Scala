# Spark-Application--Scala


******************************************************************************* README *****************************************************************************************************************

------------------------------------------------Project Structure---------------------------------------------------------------------------------------------------------------------------------------

Root Folder
|--Readme
|--SparkJob.scala
|--Output
   |--part-00000
|--Spark_Scala
   |-- src
       |-- main
       |-- test
   |-- target (run spark-submit command from this directory)
       |-- Spark_Scala-0.0.1-SNAPSHOT-jar-with-dependencies.jar
       |-- classes
   |-- pom.xml
   |-- output (will be generated after spark-submit command below)   
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1. The job was created by first installing SCALA and SPARK on the local system. The SCALA IDE for Eclipse was installed and the application was developed and run on eclipse.

2. After coding the standalone job, the pom.xml is updated to be compiled and packaged through maven. Navigated to the eclipse workspace folder, and packaged the project using maven by using this command in the root structure: 
===>  mvn package

3. After the build is successful, a folder named "target" will be formed in the root directory.

4. The "target" folder contains the executable Jar "Spark_Scala-0.0.1-SNAPSHOT-jar-with-dependencies.jar", which contains all the dependencies. The contents of the "target" folder are as per below:

		drwxrwxr-x 2 aswathy aswathy     4096 Mar 31 14:11 test-classes
		drwxrwxr-x 3 aswathy aswathy     4096 Mar 31 14:12 classes
		-rw-rw-r-- 1 aswathy aswathy        1 Mar 31 14:12 classes.timestamp
		drwxrwxr-x 3 aswathy aswathy     4096 Mar 31 14:12 maven-status
		drwxrwxr-x 2 aswathy aswathy     4096 Mar 31 14:12 maven-archiver
		-rw-rw-r-- 1 aswathy aswathy     8826 Mar 31 14:12 Spark_Scala-0.0.1-SNAPSHOT.jar
		drwxrwxr-x 2 aswathy aswathy     4096 Mar 31 14:12 archive-tmp
		-rw-rw-r-- 1 aswathy aswathy 94537152 Mar 31 14:13 Spark_Scala-0.0.1-SNAPSHOT-jar-with-dependencies.jar


5. The following command is used while within the "target" folder to execute the Spark Job:
===>  spark-submit --class <packagename.classname> --master local --deploy-mode client --executor-memory 1g \<JarInTargetFolder> <Absolute file path to ad-events file> <Absolute file path to assets file> 

Example on my local machine:
===>  spark-submit --class aswathy.SparkJob --master local --deploy-mode client --executor-memory 1g \Spark_Scala-0.0.1-SNAPSHOT-jar-with-dependencies.jar /home/aswathy/Desktop/Spark_Inputs/ad-events_2014-01-20_00_domU-12-31-39-01-A1-34 /home/aswathy/Desktop/Spark_Inputs/assets_2014-01-20_00_domU-12-31-39-01-A1-34


6. After running the above command, a folder named "output" will be created in the root directory.

7. The output generated is stored in a file called part-00000, which is also submitted in the same folder as this README, for your reference.
