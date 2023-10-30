<p align="center">
   <img align="center" src="./logo.svg" height="250px">
</p>
<h1 align="center">
   IPA-V-Backend
</h1>
<p align="center">
   Yannic Studer
</p>

## Introduction

IPA-V-Backend is a component of the IPA-V project. It serves as the intermediary between the message queue and the frontend, storing data received from the message queue for a specified period and providing an API for the frontend to retrieve and visualize the stored data. This README provides instructions on how to set up and use IPA-V-Backend in your development environment.

## Prerequisites

Before you can use IPA-V-Backend, ensure that you have the following prerequisites installed:

1. **Java**: IPA-V-Backend is a Java application, so you need to have Java installed. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/) or your preferred Java distribution.

2. **Maven**: IPA-V-Backend uses Maven as a build tool and dependency management system. Make sure you have Maven installed. You can download it from the [official Maven website](https://maven.apache.org/download.cgi).

3. **IntelliJ IDEA**: You can use IntelliJ IDEA as your Integrated Development Environment (IDE) to work with the project. If you don't have it installed, download it from the [JetBrains website](https://www.jetbrains.com/idea/download/).

4. **SQL Database**: IPA-V-Backend relies on a SQL database to store transaction data. You need a SQL database installed and configured. The provided SQL script can be used to create the necessary table.

## Setting Up the Database

IPA-V-Backend stores transaction data in a SQL database. To set up the database, follow these steps:

1. **Execute SQL Script**: Use a SQL workbench or client of your choice to execute the following SQL script. This will create the necessary "transaction" table.


```SQL
CREATE DATABASE transaction;

CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME,
    amount DOUBLE,
    sender_server_location VARCHAR(255),
    receiver_server_location VARCHAR(255),
    memory_usage BIGINT,
    processing_time BIGINT,
    sender_account_number VARCHAR(255),
    receiver_account_number VARCHAR(255)
);
```

## Repository Links

- [IPA-V-Backend](https://github.com/FireNick44/IPA-V-Backend): The backend part of the IPA-V application.
- [IPA-V-Frontend](https://github.com/FireNick44/IPA-V-Frontend): The frontend part of the IPA-V application.
- [IPA-V-MQ](https://github.com/FireNick44/IPA-V-MQ): The message queuing component for IPA-V.

### Contributors

- [Yannic Studer](https://github.com/FireNick44)


## License
Project is [MIT licensed](./LICENSE)