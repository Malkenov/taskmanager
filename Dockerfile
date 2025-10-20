FROM ubuntu:latest
LABEL authors="asana"

ENTRYPOINT ["top", "-b"]

# 1. Базовый образ (где уже есть JDK)
FROM openjdk:17-jdk-slim

# 2. Указываем рабочую директорию внутри контейнера
WORKDIR /app

# 3. Копируем jar-файл из локальной папки target внутрь контейнера
COPY target/taskmanager.jar app.jar

# 4. Команда, которая запускает приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
