#!/bin/bash

# Define variables
APP_NAME="BigDataAnalysis"
JAR_NAME="${APP_NAME}-assembly-1.0.jar"
MAIN_CLASS="org.bigdataanalysis.Main"
QUEUE="default" # Specifying the default queue; change as needed

# Assembly the project into a fat JAR
echo "Assembling the project into a fat JAR..."
sbt assembly

# Check if the assembly was successful
if [ ! -f "target/scala-2.12/${JAR_NAME}" ]; then
    echo "Assembly failed."
    exit 1
fi

echo "Assembly successful."

# Submit the application to Spark
echo "Submitting the application to Spark..."
spark-submit --deploy-mode cluster \
             --class ${MAIN_CLASS} \
             --queue ${QUEUE} \
             --name ${APP_NAME} \
             "target/scala-2.12/${JAR_NAME}"

echo "Spark job submitted."

