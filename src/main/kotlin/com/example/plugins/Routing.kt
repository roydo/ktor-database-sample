package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.dao.dao
import com.example.data.JsonToKotlin
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.util.*


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/app-analytics") {
            call.respond(
                FreeMarkerContent(
                    "analytics.ftl",
                    mapOf("appanalyticsdata" to dao.getAllData())
                )
            )
        }
        post("/app-analytics") {
            val formParameters = call.receiveParameters()
            if(formParameters.contains("add")) {
                val name = formParameters.getOrFail("name")
                val version = formParameters.getOrFail("version")
                dao.addNewAppAnalytics(name,version)
                call.respondRedirect("/app-analytics")
            } else if(formParameters.contains("delete")) {
                val id = formParameters.getOrFail("delete")
                dao.deleteAppAnalytics(id.toInt())
                call.respondRedirect("/app-analytics")
            }
        }
        get("/app-analytics/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(
                FreeMarkerContent(
                    "edit.ftl",
                    mapOf("appanalyticsdata" to dao.appAnalytics(id))
                )
            )
        }
        post("/app-analytics/{id}"){
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            dao.editAppAnalytics(
                id = id,
                name = formParameters.getOrFail("name"),
                version = formParameters.getOrFail("version"),
                downloads = formParameters.getOrFail("downloads").toInt()
            )
            call.respondRedirect("/app-analytics")
        }
        
        get("/apps") {
            call.respond(FreeMarkerContent("apps.ftl", null))
        }
        post("/apps") {
            val data = call.receive<JsonToKotlin>()
            dao.incrementDownloads(data.id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
