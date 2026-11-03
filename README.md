# ⚔️ BotClash Cyber-Arena — Backend Engine

High-performance, low-memory Spring Boot combat simulation & mech management backend for the **BotClash Underground Robot Combat League**.

Optimized specifically for AWS `t3` instances (2GB RAM, 8GB disk space).

---

## 🚀 Key Features

- **Turn-based Battle Simulator**: Simulates high-stakes robot combat turn-by-turn with initiative rolls, speed-based evasion/dodges, 18% critical strike multiplier (1.6x damage), and armor mitigation.
- **Dynamic Combat Telemetry**: Generates play-by-play battle round logs with weapon-specific combat commentary.
- **Pre-seeded Arena Legends**: Built-in seeding service (`POST /api/bots/seed`) to spawn 6 championship gladiator mechs across all weight classes.
- **Scrapyard Soft-Deletion**: Destroyed or decommissioned bots are soft-deleted (`scrapped = true`) and can be nano-repaired individually or in batch.
- **Hall of Fame Leaderboard**: Live ranking system sorting active bots by total victories, combat rating, and win ratios.
- **AWS `t3` Memory Tuning**: Capped Hikari connection pool (5 connections) and Tomcat thread pool (40 threads), running with JVM heap limits under ~250MB RSS.

---

## 🛠️ How to Run Locally

### Prerequisites
- Java 17+ (JDK)
- MySQL 8.x running on port `3306` (or Docker MySQL)
- Database: `student_db` (or updated in `application.properties`)

### Step 1: Start Backend
```bash
cd Practice-2
./mvnw clean compile
./mvnw spring-boot:run
```
The API boots at `http://localhost:8080`. Hibernate will automatically generate or update the `combat_bots` table.

---

## 🐳 Run with Docker & Docker Compose

```bash
cd Practice-2
docker compose up --build      # Build and boot Spring Boot + MySQL 8.4
docker compose down            # Stop containers
docker compose down -v         # Stop and wipe database volume
```

- API: `http://localhost:8080`
- MySQL host port: `localhost:3307` (`root` / `root`)

---

## 📡 REST API Reference

All endpoints are mapped under `@CrossOrigin(origins = "*")` at `/api/bots`:

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/bots` | Retrieve all active hangar mechs (optional `?weightClass=` & `?weaponType=`) |
| `GET` | `/api/bots/{id}` | Get single bot specification by ID |
| `POST` | `/api/bots` | Forge/create a new custom combat mech |
| `PUT` | `/api/bots/{id}` | Re-tune / upgrade an existing combat mech |
| `DELETE` | `/api/bots/{id}` | Decommission / scrap mech (soft-delete to Scrapyard) |
| `POST` | `/api/bots/duel` | **Commence turn-based arena duel simulation** |
| `GET` | `/api/bots/leaderboard` | Hall of Fame leaderboard ranked by arena victories |
| `GET` | `/api/bots/scrapyard` | List all decommissioned / destroyed mechs |
| `PUT` | `/api/bots/{id}/restore` | Nano-repair single bot back to active service |
| `POST` | `/api/bots/scrapyard/restore-all` | Batch restore all scrapped bots |
| `POST` | `/api/bots/seed` | Auto-seed 6 preset gladiator bots into the arena |

---

## ⚔️ Combat Duel Payload Example

### `POST /api/bots/duel`
```json
{
  "bot1Id": 1,
  "bot2Id": 2,
  "arenaEnvironment": "MOLTEN_FOUNDRY"
}
```

### Duel Response Example
```json
{
  "winner": { "id": 1, "name": "VORTEX RIPPER", ... },
  "loser": { "id": 2, "name": "TITAN CRUSHER", ... },
  "isDraw": false,
  "totalRounds": 6,
  "victoryQuote": "VORTEX RIPPER obliterated TITAN CRUSHER in round 6!",
  "combatLog": [
    {
      "round": 1,
      "attackerId": 1,
      "defenderId": 2,
      "damageDealt": 34,
      "defenderHpRemaining": 146,
      "isCritical": true,
      "isDodge": false,
      "commentary": "💥 CRITICAL STRIKE! VORTEX RIPPER detonates plasma burst on TITAN CRUSHER for 34 damage! [146 HP left]"
    }
  ]
}
```

---

## 🏗️ Architecture

```
Practice-2/
├── src/main/java/com/thinkitive/practice2/
│   ├── controller/CombatBotController.java  # REST API Endpoints
│   ├── entity/CombatBot.java                # JPA Entity for Combat Mech
│   ├── repository/CombatBotRepository.java  # Spring Data JPA Repository
│   ├── service/CombatBotService.java        # Combat Simulation & Business Logic
│   └── dto/
│       ├── DuelRequest.java                 # Bot 1 vs Bot 2 Duel Request DTO
│       ├── DuelResult.java                  # Battle Results & Victory Stats
│       └── BattleRoundLog.java              # Turn-by-turn Combat Telemetry
└── src/main/resources/application.properties # t3 Micro Pool & JPA Configuration
```
