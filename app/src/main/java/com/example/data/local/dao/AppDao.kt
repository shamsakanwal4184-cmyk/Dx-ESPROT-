package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Users
    @Query("SELECT * FROM users WHERE email = :email OR mobile = :mobile LIMIT 1")
    suspend fun getUserByEmailOrMobile(email: String, mobile: String): User?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    // Tournaments
    @Query("SELECT * FROM tournaments ORDER BY id DESC")
    fun getAllTournaments(): Flow<List<Tournament>>

    @Query("SELECT * FROM tournaments WHERE id = :id")
    suspend fun getTournamentById(id: Long): Tournament?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: Tournament): Long

    @Update
    suspend fun updateTournament(tournament: Tournament)

    @Delete
    suspend fun deleteTournament(tournament: Tournament)

    // Registrations
    @Query("SELECT * FROM registrations ORDER BY id DESC")
    fun getAllRegistrations(): Flow<List<Registration>>

    @Query("SELECT * FROM registrations WHERE userId = :userId")
    fun getRegistrationsByUser(userId: Long): Flow<List<Registration>>

    @Query("SELECT * FROM registrations WHERE tournamentId = :tournamentId")
    fun getRegistrationsByTournament(tournamentId: Long): Flow<List<Registration>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistration(registration: Registration): Long

    @Update
    suspend fun updateRegistration(registration: Registration)

    // Matches
    @Query("SELECT * FROM matches WHERE tournamentId = :tournamentId")
    fun getMatchesByTournament(tournamentId: Long): Flow<List<Match>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Match): Long

    // Results
    @Query("SELECT * FROM results WHERE tournamentId = :tournamentId ORDER BY rank ASC")
    fun getResultsByTournament(tournamentId: Long): Flow<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: Result)

    // Notifications
    @Query("SELECT * FROM notifications WHERE userId IS NULL OR userId = :userId ORDER BY date DESC")
    fun getNotificationsByUser(userId: Long): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)
}
