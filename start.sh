if [[ $# -eq 0 ]]; then
    docker-compose --project-directory docker up web -d --build gui 
fi

if [[ $1 = "test" ]]; then
    docker-compose --project-directory docker up --build test 
fi

if [[ $1 = "web"  ]]; then
    docker-compose --project-directory docker up  -d --build web
fi

if [[ $1 = "gui"  ]]; then
    docker-compose --project-directory docker up  -d --build gui
fi
