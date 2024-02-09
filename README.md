# DDG User Feedback

### Config

Create an .env file in this folder, with these values:

```
DATABASE_NAME="<Your DB Name>"
DATABASE_URL="jdbc:postgresql://localhost:5432/${DATABASE_NAME}"
DATABASE_USER="<Your User>"
DATABASE_PASSWORD="<Your Password>"
ASANA_WORKSPACE="<Your Workspace GID>"
ASANA_PROJECT="<Your Project GID>"
ASANA_PAT="<Your PAT>"
ASANA_USER_ID="<Your User GID>"
```

### Local Deploy

1. Ensure postgres is running
1. (First time only) create the local database: `createdb <Your DB Name>`
1. (First time only) run the initial migration: `psql -d <Your DB Name> -f db/create_tables.sql`
1. Export the .env file as env vars:

```
eval $(cat .env)
```

Deploy the backend and frontend in two different shells:

##### Rest Service (Backend)

1. `./mvnw install`
2. `./mvnw spring-boot:run`

##### Client (Frontend)

Running Node Version >= 16

1. `cd client`
2. `npm install`
3. `npm start`

Runs on `localhost:3000` by default.

#### How to deploy (locally) using Docker Compose

```
docker compose up -d
```

### Deploy to Prod

Request provisioning script from the owners of this repo. Once provided, execute it on the desired server:

```
ssh username@ip-address 'bash -s' < provision.sh
```

### Troubleshoot in Prod

For now, there is just one server. You just ssh into it. 😨

View backend logs:

```
docker ps rest-service
```

Database access:

```
docker exec -u root -it ddg_app-db-1 /bin/bash
psql -U <Your User> -d <Your Database Name>
```

To restart the service, re-run the provisioning script
