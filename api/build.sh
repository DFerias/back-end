#!/bin/sh
"/home/voronhuk/Development/TCC/back-end/api/mvnw" package -f "/home/voronhuk/Development/TCC/back-end/api/pom.xml"
echo "Finalizado Build"
cp /home/voronhuk/Development/TCC/back-end/api/target/api-0.0.1-SNAPSHOT.jar /home/voronhuk/Development/TCC/back-end
echo  "Arquivo copiado"