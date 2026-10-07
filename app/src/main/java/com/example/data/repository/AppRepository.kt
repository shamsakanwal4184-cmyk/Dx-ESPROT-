package com.example.data.repository

import com.example.data.local.dao.AppDao
import com.example.data.local.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AppRepository(private val dao: AppDao) {

    // Users
    suspend fun login(emailOrMobile: String, password: String): User? {
        val user = dao.getUserByEmailOrMobile(emailOrMobile, emailOrMobile)
        return if (user?.password == password) user else null
    }

    suspend fun register(user: User): Long = dao.insertUser(user)

    suspend fun getUserById(id: Long): User? = dao.getUserById(id)

    fun getAllUsers(): Flow<List<User>> = dao.getAllUsers()

    // Tournaments
    fun getAllTournaments(): Flow<List<Tournament>> = dao.getAllTournaments()

    suspend fun getTournamentById(id: Long): Tournament? = dao.getTournamentById(id)

    suspend fun createTournament(tournament: Tournament): Long = dao.insertTournament(tournament)

    suspend fun updateTournament(tournament: Tournament) = dao.updateTournament(tournament)

    suspend fun deleteTournament(tournament: Tournament) = dao.deleteTournament(tournament)

    // Registrations
    fun getAllRegistrations(): Flow<List<Registration>> = dao.getAllRegistrations()

    fun getRegistrationsByUser(userId: Long): Flow<List<Registration>> = dao.getRegistrationsByUser(userId)

    fun getRegistrationsByTournament(tournamentId: Long): Flow<List<Registration>> = dao.getRegistrationsByTournament(tournamentId)

    suspend fun registerForTournament(registration: Registration): Long {
        val regId = dao.insertRegistration(registration)
        // Update tournament slot count
        val tournament = dao.getTournamentById(registration.tournamentId)
        if (tournament != null) {
            dao.updateTournament(tournament.copy(slotsFilled = tournament.slotsFilled + 1))
        }
        return regId
    }

    suspend fun updateRegistration(registration: Registration) = dao.updateRegistration(registration)

    // Matches
    fun getMatchesByTournament(tournamentId: Long): Flow<List<Match>> = dao.getMatchesByTournament(tournamentId)

    suspend fun createMatch(match: Match): Long = dao.insertMatch(match)

    // Results
    fun getResultsByTournament(tournamentId: Long): Flow<List<Result>> = dao.getResultsByTournament(tournamentId)

    suspend fun addResult(result: Result) = dao.insertResult(result)

    // Notifications
    fun getNotificationsByUser(userId: Long): Flow<List<Notification>> = dao.getNotificationsByUser(userId)

    suspend fun addNotification(notification: Notification) = dao.insertNotification(notification)

    // Sample Data Initialization
    suspend fun initSampleData() {
        val tournaments = listOf(
            Tournament(
                name = "DX ESPORTS Free Fire Battle",
                game = "Free Fire",
                entryFee = 10,
                prizePool = 1000,
                date = "2026-10-10",
                time = "18:00",
                totalSlots = 48,
                matchType = "Squad",
                status = "Registration Open",
                rules = "1. Respect all players.\n2. No hacking or third-party tools.\n3. Be on time.",
                description = "The ultimate battle for Free Fire squads."
            ),
            Tournament(
                name = "Pro League Solo",
                game = "Free Fire",
                entryFee = 5,
                prizePool = 500,
                date = "2026-10-12",
                time = "20:00",
                totalSlots = 50,
                matchType = "Solo",
                status = "Upcoming",
                rules = "1. Solo match rules apply.\n2. All players must be at least level 10.",
                description = "Test your solo skills in this high-stakes tournament."
            )
        )
        // Check if tournaments exist, if not insert
        val existing = dao.getAllTournaments().first()
        if (existing.isEmpty()) {
            tournaments.forEach { dao.insertTournament(it) }
        }
    }
}
