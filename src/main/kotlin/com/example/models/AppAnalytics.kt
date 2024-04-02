package com.example.models

import org.jetbrains.exposed.sql.Table

data class AppAnalytics(
    val id: Int,
    val name: String,
    val version: String,
    val downloads: Int = 0
)

object AppAnalyticsData: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val version = varchar("version", 10)
    val downloads = integer("downloads").default(0)

    override val primaryKey = PrimaryKey(id)
}