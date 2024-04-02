package com.example.data

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*

@Serializable
data class MyAppData(
    val id: Int,
    val appName: String = "",
    val version: String = "1.0",
    val clickedTimes: Int = 0
)
class MyAppDataSchema(private val database: Database) {
    object MyAppDataTable : Table() {
        val id = integer("id").autoIncrement()
        val appName = varchar("app_name", length = 50)
        val version = varchar("app_name", length = 10)
        val clickedTimes = integer("clicked_times")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(MyAppDataTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun createNewRow(myAppData: MyAppData) = dbQuery {
        MyAppDataTable.insert {
            it[appName] = myAppData.appName
            it[version] = myAppData.version
            it[clickedTimes] = myAppData.clickedTimes
        }
    }
    
    suspend fun getAll(): List<MyAppData> {
        return dbQuery {
            MyAppDataTable.selectAll().map {
                MyAppData(
                    it[MyAppDataTable.id],
                    it[MyAppDataTable.appName],
                    it[MyAppDataTable.version],
                    it[MyAppDataTable.clickedTimes]
                )
            }
        }
    }
    
    /*suspend fun incrementClickedTimes(id: Int) {
        val prevCount = dbQuery {
            MyAppDataTable.select(MyAppDataTable.id eq id)
                .map {
                    MyAppData(
                        it[MyAppDataTable.id],
                        it[MyAppDataTable.appName],
                        it[MyAppDataTable.version],
                        it[MyAppDataTable.clickedTimes]
                    )
                }
                .singleOrNull()
        }?.clickedTimes ?: 0
        MyAppDataTable.update({ MyAppDataTable.id eq id }) {
            it[clickedTimes] = prevCount + 1
        }
    }*/
    
    suspend fun incrementClickedTimes(id: Int) {
        transaction {
            // ここでトランザクション内での読み取りと書き込みを行います
            val currentCount = MyAppDataTable.select { MyAppDataTable.id eq id }
                .singleOrNull()?.get(MyAppDataTable.clickedTimes) ?: return@transaction

            MyAppDataTable.update({ MyAppDataTable.id eq id }) {
                it[clickedTimes] = currentCount + 1
            }
        }
    }
    
}

