#!/bin/sh
"./mvnw" package -f "./pom.xml"
echo "Finalizado Build"
cp ./target/api-0.0.1-SNAPSHOT.jar ./../
cp -r ./config/ ./../config/
echo  "Arquivo copiado"