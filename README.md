# *Budget Control Application*

## Project Status: In Progress 🚧

### Overview:
The Budget Control Application helps users manage their finances by tracking revenue and expenses, and generating monthly summaries.

## Set up the project

1. We need Docker to run a container for Postgres 14.5; you can skip it if Postgres is installed on your computer. Run the command below to start the Docker container from the Postgres image:

```docker
docker run --name localhost_postgresql -d \
-e POSTGRES_DB=bcontrol \
-e POSTGRES_USER=test \
-e POSTGRES_PASSWORD=r00t \
-p 5432:5432 \
postgres:14.5