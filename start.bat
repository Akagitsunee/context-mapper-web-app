if [%1]==[] (
    call docker-compose --project-directory docker up web gui --build -d
)

if %1==test (
    call docker-compose --project-directory docker up test --build -d
)

if %1==web (
    call docker-compose --project-directory docker up gui --build -d
)

if %1==gui (
    call docker-compose --project-directory docker up gui --build -d
)

if %1==local (
    call ./backend/gradlew.bat bootJar -p ./backend
    call java -jar ./backend/build/web-1.0.0.jar
)