package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import java.util.*
import javax.sql.DataSource
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

fun main() {
    val dataSource = createDataSource()

    val flyway = Flyway
        .configure()
        .locations("filesystem:src/main/sql")
        .dataSource(dataSource)
        .load()

    flyway.migrate()
}

private fun createDataSource(): DataSource {
    val start = System.currentTimeMillis()

    while (true) {
        try {
            return HikariDataSource(
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
        } catch (t: Throwable) {
            val isTimeout = (System.currentTimeMillis() - start).milliseconds > 1.minutes
            if (isTimeout) {
                throw t
            } else {
                println("Retry...")
            }
        }
    }
}