if [ $# -eq 0 ]; then
    docker-compose --project-directory docker up web gui --build -d
fi

if [ $1 = "test" ]; then
    docker-compose --project-directory docker up test --build
fi

if [ $1 = "web"  ]; then
    docker-compose --project-directory docker up web --build -d
fi

if [ $1 = "gui"  ]; then
    docker-compose --project-directory docker up gui --build -d
fi