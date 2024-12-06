./gradlew clean build

rm -rf /opt/tomee/webapps/web_lab4*
cp ./build/libs/web_lab4.war /opt/tomee/webapps/
/opt/tomee/bin/shutdown.sh
/opt/tomee/bin/startup.sh