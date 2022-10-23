if [[ $# -eq 0 ]]; then
    docker-compose --project-directory docker up -d --build web gui 
fi

if [[ $1 = "test" ]]; then
    docker-compose --project-directory docker up --build test 
fi

if [[ $1 = "web"  ]]; then
    docker-compose --project-directory docker up -d --build web
fi

if [[ $1 = "gui"  ]]; then
    docker-compose --project-directory docker up -d --build gui
fi

if [[ $1 = "local"  ]]; then
    chmod +x ./backend/gradlew
    ./backend/gradlew bootJar -p ./backend
    java -jar ./backend/build/web-1.0.0.jar
fi
