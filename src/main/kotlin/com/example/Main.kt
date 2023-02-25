package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import java.util.*

fun main() {
    val dataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:8090/example"
            connectionTimeout = 10_000
            dataSourceProperties = Properties().apply {
                setProperty("user", "postgres")
                setProperty("password", "test")

                setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
                setProperty("sslmode", "disable")
            }
        }
    )

    val flyway = Flyway
        .configure()
        .locations("filesystem:src/main/sql")
        .dataSource(dataSource)
        .load()

    flyway.migrate()
}
