package com.example.dao

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.AppAnalytics
import com.example.models.AppAnalyticsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class DaoFacadeImpl: DaoFacade {
    private fun resultRowToAppAnalytics(row: ResultRow) = AppAnalytics(
        id = row[AppAnalyticsData.id],
        name = row[AppAnalyticsData.name],
        version = row[AppAnalyticsData.version],
        downloads = row[AppAnalyticsData.downloads]
    )
    override suspend fun getAllData(): List<AppAnalytics> = dbQuery {
        AppAnalyticsData.selectAll().map(::resultRowToAppAnalytics)
    }

    override suspend fun appAnalytics(id: Int): AppAnalytics? = dbQuery {
        AppAnalyticsData
            .select { AppAnalyticsData.id eq id }
            .map(::resultRowToAppAnalytics)
            .singleOrNull()
    }

    override suspend fun addNewAppAnalytics(name: String, version: String): AppAnalytics? = dbQuery {
        val insertStatement = AppAnalyticsData.insert {
            it[AppAnalyticsData.name] = name
            it[AppAnalyticsData.version] = version
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAppAnalytics)
    }

    override suspend fun editAppAnalytics(id: Int, name: String, version: String, downloads: Int): Boolean = dbQuery {
        AppAnalyticsData.update({ AppAnalyticsData.id eq id }) {
            it[AppAnalyticsData.name] = name
            it[AppAnalyticsData.version] = version
            it[AppAnalyticsData.downloads] = downloads
        } > 0
    }

    override suspend fun deleteAppAnalytics(id: Int): Boolean = dbQuery {
        AppAnalyticsData.deleteWhere { AppAnalyticsData.id eq  id } > 0
    }

    override suspend fun incrementDownloads(id: Int): Boolean = dbQuery {
        // ここでトランザクション内での読み取りと書き込みを行います
        val currentCount = AppAnalyticsData.select { AppAnalyticsData.id eq id }
            .singleOrNull()?.get(AppAnalyticsData.downloads) ?: 0
        AppAnalyticsData.update({ AppAnalyticsData.id eq id }) {
            it[downloads] = currentCount + 1
        } > 0
    }
}

val dao: DaoFacade = DaoFacadeImpl().apply {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        if(getAllData().isEmpty()) {
            addNewAppAnalytics(
                "No apps yet",
                "1.0"
            )
        }
    }
}