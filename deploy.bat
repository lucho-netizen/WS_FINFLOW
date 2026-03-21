docker build -t finflow-backend .
docker stop finflow-backend 2>nul
docker rm finflow-backend 2>nul
docker run -d -p 8080:8080 --name finflow-backend --env-file .env finflow-backend
echo Backend listo en http://localhost:8080