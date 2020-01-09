cd /tmp/touche-shared-task/
./gradlew build --info
java -Xmx8g -Xms7g -jar /tmp/touche-shared-task/build/libs/touche-shared-task-1.0-SNAPSHOT-all.jar Main /tmp/input-dir/ /tmp/output-dir/
