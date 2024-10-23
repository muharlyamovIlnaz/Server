package com.ilnaz.server.config;

import org.flywaydb.core.Flyway;

public class FlywayConfiguration {
    public static void migrateDatabase() {
        // Настройки подключения к базе данных
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres").load();

        // Запуск миграций
        flyway.migrate();
    }
}
