package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val username: String,
    val email: String,
    val mobile: String,
    val password: String,
    val isAdmin: Boolean = false
)

@Entity(tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val game: String = "Free Fire",
    val entryFee: Int,
    val prizePool: Int,
    val date: String,
    val time: String,
    val totalSlots: Int,
    val slotsFilled: Int = 0,
    val status: String, // Upcoming, Registration Open, Full, Live, Completed, Cancelled
    val matchType: String, // Solo, Duo, Squad
    val bannerRes: String? = null,
    val rules: String,
    val description: String,
    val roomId: String? = null,
    val roomPassword: String? = null,
    val map: String = "Bermuda"
)

@Entity(tableName = "registrations")
data class Registration(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val tournamentId: Long,
    val playerName: String,
    val ffUid: String,
    val ign: String,
    val whatsapp: String,
    val teamName: String? = null,
    val teamMembers: String? = null,
    val paymentRef: String? = null,
    val paymentStatus: String = "Pending", // Pending, Approved, Rejected
    val regStatus: String = "Pending", // Pending, Approved, Rejected
    val regDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tournamentId: Long,
    val matchNumber: Int,
    val date: String,
    val time: String,
    val map: String,
    val roomId: String? = null,
    val roomPassword: String? = null,
    val status: String = "Upcoming"
)

@Entity(tableName = "results")
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tournamentId: Long,
    val matchId: Long? = null,
    val teamName: String,
    val kills: Int,
    val position: Int,
    val points: Int,
    val totalPoints: Int,
    val rank: Int
)

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long?, // Null for global notifications
    val title: String,
    val message: String,
    val date: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)
