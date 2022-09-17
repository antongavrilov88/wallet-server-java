# RESTful authorization server template


## Prepare environment

Install PostgreSQL:
```bash
sudo apt update
sudo apt install postgresql
```

Create a new role `wallet_auth` to manage users database:
```bash
sudo -u postgres createuser --interactive
```
Type the name of a new role and delegate it superuser rights.

Create a new database named `wallet_auth` for `wallet_auth` user:
```bash
sudo -u postgres createdb wallet_auth
```

Create `wallet_auth` system user:
```bash
sudo adduser wallet_auth
```

Then enter to the Postgres's command interface:
```bash
sudo -u wallet_auth psql
```
and set password for the `wallet_auth` user:
```sql
alter user wallet_auth with password '...';
```

Modify the Postgres's config file `/etc/postgresql/11/main/postgresql.conf`:
- uncomment the `listen_addressess` line

## Create the server's database:

```bash
psql -h localhost -p 5432 -U wallet_auth
wallet_auth=# create database wallet_auth;
```
To edit user, password and DB name:
```bash
sudo nano <projectdirectory>/src/main/resources/application.properties
```
insert your user, password and DB name.

## Deploying on local machine

To build war and run app you need to install maven plugin:
```bash
sudo apt install maven
```
Now you need to run this command in project root directory, where pom.xml is placed:
```bash
mvn org.springframework.boot:spring-boot-maven-plugin:run
```
The application is running, to create request use port: 3003

You can find SWAGGER documentation at http://localhost:3003/swagger-ui/index.html#/

Hi