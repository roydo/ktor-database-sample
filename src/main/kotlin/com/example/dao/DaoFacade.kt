package com.example.dao

import com.example.models.AppAnalytics

interface DaoFacade {
    suspend fun getAllData(): List<AppAnalytics>
    suspend fun appAnalytics(id: Int): AppAnalytics?
    suspend fun addNewAppAnalytics(name: String, version: String): AppAnalytics?
    suspend fun editAppAnalytics(id: Int, name: String, version: String, downloads: Int): Boolean
    suspend fun deleteAppAnalytics(id: Int): Boolean
    
    suspend fun incrementDownloads(id: Int): Boolean
}