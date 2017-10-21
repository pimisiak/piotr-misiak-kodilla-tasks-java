#!/usr/bin/env bash

export CATALINA_HOME=/opt/apache-tomcat-9.0.1
PROJECT_PATH=~/Development/Java/Projects/kodilla-tasks

stop_tomcat() {
    sudo ${CATALINA_HOME}/bin/shutdown.sh
}

start_tomcat() {
    sudo ${CATALINA_HOME}/bin/startup.sh
}

rename() {
    rm ${PROJECT_PATH}/build/libs/crud.war
    if mv ${PROJECT_PATH}/build/libs/kodilla-tasks-0.0.1-SNAPSHOT.war ${PROJECT_PATH}/build/libs/crud.war; then
        echo "Successfuly renamed file"
    else
        echo "Cannot rename file"
        fail
    fi
}

copy_file() {
    if sudo cp ${PROJECT_PATH}/build/libs/crud.war ${CATALINA_HOME}/webapps; then
        start_tomcat
    else
        fail
    fi
}

fail() {
    echo "There were errors"
}

end() {
    echo "Work is finished"
}

if ./gradlew build; then
    rename
    copy_file
    end
else
    stop_tomcat
    fail
fi