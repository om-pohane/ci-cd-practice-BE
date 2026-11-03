package com.thinkitive.practice2.service;

import com.thinkitive.practice2.dto.BattleRoundLog;
import com.thinkitive.practice2.dto.DuelRequest;
import com.thinkitive.practice2.dto.DuelResult;
import com.thinkitive.practice2.entity.CombatBot;
import com.thinkitive.practice2.repository.CombatBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CombatBotService {

    @Autowired
    private CombatBotRepository combatBotRepository;

    private final Random random = new Random();

    public CombatBot createBot(CombatBot bot) {
        bot.setId(null);
        bot.setScrapped(false);
        bot.setStatus("READY");
        bot.setWins(0);
        bot.setLosses(0);
        bot.setDraws(0);
        bot.setCreatedAt(LocalDateTime.now());

        if (bot.getMaxHealth() == null || bot.getMaxHealth() < 80) bot.setMaxHealth(150);
        bot.setCurrentHealth(bot.getMaxHealth());

        if (bot.getAttack() == null) bot.setAttack(50);
        if (bot.getDefense() == null) bot.setDefense(30);
        if (bot.getSpeed() == null) bot.setSpeed(40);
        if (bot.getWeightClass() == null) bot.setWeightClass("MIDDLEWEIGHT");
        if (bot.getWeaponType() == null) bot.setWeaponType("PLASMA_SAW");
        if (bot.getAvatarColor() == null) bot.setAvatarColor("#4f46e5");

        bot.recalculatePowerRating();
        return combatBotRepository.save(bot);
    }

    public List<CombatBot> getAllActiveBots() {
        return combatBotRepository.findByScrappedFalseOrderByIdDesc();
    }

    public CombatBot getBotById(Long id) {
        CombatBot bot = combatBotRepository.findByIdAndScrappedFalse(id);
        if (bot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Combat bot not found with id " + id);
        }
        return bot;
    }

    public List<CombatBot> getBotsByWeightClass(String weightClass) {
        return combatBotRepository.findByWeightClassAndScrappedFalse(weightClass);
    }

    public List<CombatBot> getBotsByWeaponType(String weaponType) {
        return combatBotRepository.findByWeaponTypeAndScrappedFalse(weaponType);
    }

    public CombatBot updateBot(Long id, CombatBot updateData) {
        CombatBot bot = getBotById(id);

        if (updateData.getName() != null) bot.setName(updateData.getName());
        if (updateData.getWeightClass() != null) bot.setWeightClass(updateData.getWeightClass());
        if (updateData.getWeaponType() != null) bot.setWeaponType(updateData.getWeaponType());
        if (updateData.getMaxHealth() != null) {
            bot.setMaxHealth(updateData.getMaxHealth());
            bot.setCurrentHealth(updateData.getMaxHealth());
        }
        if (updateData.getAttack() != null) bot.setAttack(updateData.getAttack());
        if (updateData.getDefense() != null) bot.setDefense(updateData.getDefense());
        if (updateData.getSpeed() != null) bot.setSpeed(updateData.getSpeed());
        if (updateData.getAvatarColor() != null) bot.setAvatarColor(updateData.getAvatarColor());

        bot.recalculatePowerRating();
        return combatBotRepository.save(bot);
    }

    // Soft delete: sends bot to the Scrapyard
    public void scrapBot(Long id) {
        CombatBot bot = getBotById(id);
        bot.setScrapped(true);
        bot.setStatus("SCRAPPED");
        combatBotRepository.save(bot);
    }

    public List<CombatBot> getScrappedBots() {
        return combatBotRepository.findByScrappedTrueOrderByIdDesc();
    }

    // Restore bot from Scrapyard (rebuild chassis and restore health)
    public CombatBot repairBot(Long id) {
        CombatBot bot = combatBotRepository.findByIdAndScrappedTrue(id);
        if (bot == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scrapped bot not found with id " + id);
        }
        bot.setScrapped(false);
        bot.setStatus("READY");
        bot.setCurrentHealth(bot.getMaxHealth());
        return combatBotRepository.save(bot);
    }

    public List<CombatBot> getLeaderboard() {
        return combatBotRepository.findTop10ByScrappedFalseOrderByWinsDesc();
    }

    /**
     * Seeds initial gladiator champions into the arena database
     */
    public List<CombatBot> seedPresetChampions() {
        List<CombatBot> champions = new ArrayList<>();

        champions.add(createPreset("TITAN_PRIME", "TITAN", "HYDRAULIC_CRUSHER", 240, 75, 45, 20, "#dc2626"));
        champions.add(createPreset("VOLT_VIPER", "LIGHTWEIGHT", "TESLA_COIL", 130, 65, 20, 65, "#0891b2"));
        champions.add(createPreset("RUST_REAPER", "HEAVYWEIGHT", "SPINNING_BLADE", 190, 58, 38, 35, "#d97706"));
        champions.add(createPreset("PLASMA_SPECTER", "MIDDLEWEIGHT", "PLASMA_SAW", 160, 70, 28, 45, "#7c3aed"));
        champions.add(createPreset("INFERNO_COLOSSUS", "TITAN", "FLAMETHROWER", 220, 68, 40, 25, "#ea580c"));
        champions.add(createPreset("RAIL_WARLOCK", "MIDDLEWEIGHT", "RAILGUN", 150, 80, 22, 50, "#2563eb"));

        return champions;
    }

    private CombatBot createPreset(String name, String weight, String weapon, int hp, int atk, int def, int spd, String color) {
        CombatBot bot = new CombatBot();
        bot.setName(name);
        bot.setWeightClass(weight);
        bot.setWeaponType(weapon);
        bot.setMaxHealth(hp);
        bot.setCurrentHealth(hp);
        bot.setAttack(atk);
        bot.setDefense(def);
        bot.setSpeed(spd);
        bot.setAvatarColor(color);
        return createBot(bot);
    }

    /**
     * Executes a turn-based robot duel with critical hits, dodges, and battle logs
     */
    public DuelResult executeDuel(DuelRequest request) {
        if (request.getBot1Id() == null || request.getBot2Id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both fighter IDs are required for a duel");
        }
        if (request.getBot1Id().equals(request.getBot2Id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A bot cannot battle itself");
        }

        CombatBot bot1 = getBotById(request.getBot1Id());
        CombatBot bot2 = getBotById(request.getBot2Id());

        List<BattleRoundLog> combatLogs = new ArrayList<>();
        int hp1 = bot1.getMaxHealth();
        int hp2 = bot2.getMaxHealth();

        int roundNumber = 1;
        int maxRounds = 14;

        while (hp1 > 0 && hp2 > 0 && roundNumber <= maxRounds) {
            // Check turn order by speed + d20 roll
            int initiative1 = bot1.getSpeed() + random.nextInt(20);
            int initiative2 = bot2.getSpeed() + random.nextInt(20);

            CombatBot first = initiative1 >= initiative2 ? bot1 : bot2;
            CombatBot second = initiative1 >= initiative2 ? bot2 : bot1;

            int firstHp = first.getId().equals(bot1.getId()) ? hp1 : hp2;
            int secondHp = first.getId().equals(bot1.getId()) ? hp2 : hp1;

            // Attack 1: First strikes Second
            BattleRoundLog log1 = simulateStrike(roundNumber, first, second, secondHp);
            secondHp = log1.getDefenderHpRemaining();
            combatLogs.add(log1);

            if (first.getId().equals(bot1.getId())) {
                hp2 = secondHp;
            } else {
                hp1 = secondHp;
            }

            // If defender still alive, they counter-attack
            if (secondHp > 0) {
                BattleRoundLog log2 = simulateStrike(roundNumber, second, first, firstHp);
                firstHp = log2.getDefenderHpRemaining();
                combatLogs.add(log2);

                if (first.getId().equals(bot1.getId())) {
                    hp1 = firstHp;
                } else {
                    hp2 = firstHp;
                }
            }

            roundNumber++;
        }

        DuelResult result = new DuelResult();
        result.setTotalRounds(roundNumber - 1);
        result.setArenaEnvironment(request.getArenaEnvironment());
        result.setCombatLog(combatLogs);

        if (hp1 > 0 && hp2 <= 0) {
            result.setWinner(bot1);
            result.setLoser(bot2);
            result.setDraw(false);
            result.setVictoryQuote(bot1.getName() + " completely dismantled " + bot2.getName() + " in the " + request.getArenaEnvironment() + "!");
            bot1.setWins(bot1.getWins() + 1);
            bot2.setLosses(bot2.getLosses() + 1);
        } else if (hp2 > 0 && hp1 <= 0) {
            result.setWinner(bot2);
            result.setLoser(bot1);
            result.setDraw(false);
            result.setVictoryQuote(bot2.getName() + " pulverized " + bot1.getName() + " into scrap metal!");
            bot2.setWins(bot2.getWins() + 1);
            bot1.setLosses(bot1.getLosses() + 1);
        } else {
            // Draw
            result.setDraw(true);
            result.setWinner(null);
            result.setLoser(null);
            result.setVictoryQuote("Stalemate! Both bots endured the maximum combat duration without falling.");
            bot1.setDraws(bot1.getDraws() + 1);
            bot2.setDraws(bot2.getDraws() + 1);
        }

        combatBotRepository.save(bot1);
        combatBotRepository.save(bot2);

        return result;
    }

    private BattleRoundLog simulateStrike(int round, CombatBot attacker, CombatBot defender, int currentDefenderHp) {
        BattleRoundLog log = new BattleRoundLog();
        log.setRound(round);
        log.setAttackerId(attacker.getId());
        log.setAttackerName(attacker.getName());
        log.setDefenderId(defender.getId());
        log.setDefenderName(defender.getName());

        // Dodge chance: speed differential
        int speedDiff = defender.getSpeed() - attacker.getSpeed();
        int dodgeThreshold = Math.max(5, Math.min(30, 10 + speedDiff / 3));
        boolean isDodge = random.nextInt(100) < dodgeThreshold;

        if (isDodge) {
            log.setDodge(true);
            log.setCritical(false);
            log.setDamageDealt(0);
            log.setDefenderHpRemaining(currentDefenderHp);
            log.setCommentary(defender.getName() + " agilely dodged " + attacker.getName() + "'s " + formatWeapon(attacker.getWeaponType()) + " strike!");
            return log;
        }

        // Critical strike check: 18% base chance
        boolean isCrit = random.nextInt(100) < 18;

        // Base damage calculation
        double variance = 0.85 + (random.nextDouble() * 0.35); // 0.85 to 1.20
        double rawDamage = (attacker.getAttack() * variance);
        double armorMitigation = (defender.getDefense() * 0.35);
        int finalDamage = (int) Math.round(Math.max(10, rawDamage - armorMitigation));

        if (isCrit) {
            finalDamage = (int) Math.round(finalDamage * 1.6);
        }

        int remainingHp = Math.max(0, currentDefenderHp - finalDamage);

        log.setDodge(false);
        log.setCritical(isCrit);
        log.setDamageDealt(finalDamage);
        log.setDefenderHpRemaining(remainingHp);

        String flavor = getWeaponFlavorText(attacker.getWeaponType(), attacker.getName(), defender.getName(), finalDamage, isCrit);
        log.setCommentary(flavor);

        return log;
    }

    private String formatWeapon(String weaponType) {
        return weaponType.replace("_", " ").toLowerCase();
    }

    private String getWeaponFlavorText(String weaponType, String atk, String def, int dmg, boolean isCrit) {
        String critPrefix = isCrit ? "💥 CRITICAL IMPACT! " : "";
        switch (weaponType) {
            case "PLASMA_SAW":
                return critPrefix + atk + " tore into " + def + "'s chassis with high-frequency PLASMA SAW for " + dmg + " DMG!";
            case "TESLA_COIL":
                return critPrefix + atk + " discharged a blinding lightning arc via TESLA COIL shocking " + def + " for " + dmg + " DMG!";
            case "HYDRAULIC_CRUSHER":
                return critPrefix + atk + " crushed " + def + "'s titanium plating with HYDRAULIC CRUSHER for " + dmg + " DMG!";
            case "FLAMETHROWER":
                return critPrefix + atk + " engulfed " + def + " in napalm flames for " + dmg + " DMG!";
            case "RAILGUN":
                return critPrefix + atk + " fired a hypersonic tungsten slug from RAILGUN piercing " + def + " for " + dmg + " DMG!";
            case "SPINNING_BLADE":
                return critPrefix + atk + " shredded armor plates using SPINNING BLADE dealing " + dmg + " DMG!";
            default:
                return critPrefix + atk + " struck " + def + " for " + dmg + " DMG!";
        }
    }
}
