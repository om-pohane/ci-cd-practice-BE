package com.thinkitive.practice2.controller;

import com.thinkitive.practice2.dto.DuelRequest;
import com.thinkitive.practice2.dto.DuelResult;
import com.thinkitive.practice2.entity.CombatBot;
import com.thinkitive.practice2.service.CombatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bots")
public class CombatBotController {

    @Autowired
    private CombatBotService combatBotService;

    // GET /api/bots - List all active battle bots
    @GetMapping
    public List<CombatBot> getAllBots() {
        return combatBotService.getAllActiveBots();
    }

    // GET /api/bots/1 - Get bot by ID
    @GetMapping("/{id}")
    public CombatBot getBotById(@PathVariable Long id) {
        return combatBotService.getBotById(id);
    }

    // GET /api/bots/weight?class=TITAN
    @GetMapping("/weight")
    public List<CombatBot> getBotsByWeightClass(@RequestParam("class") String weightClass) {
        return combatBotService.getBotsByWeightClass(weightClass);
    }

    // GET /api/bots/weapon?type=PLASMA_SAW
    @GetMapping("/weapon")
    public List<CombatBot> getBotsByWeaponType(@RequestParam("type") String weaponType) {
        return combatBotService.getBotsByWeaponType(weaponType);
    }

    // POST /api/bots - Build a new combat mech
    @PostMapping
    public CombatBot createBot(@RequestBody CombatBot bot) {
        return combatBotService.createBot(bot);
    }

    // PUT /api/bots/1 - Modify or upgrade combat mech
    @PutMapping("/{id}")
    public CombatBot updateBot(@PathVariable Long id, @RequestBody CombatBot bot) {
        return combatBotService.updateBot(id, bot);
    }

    // DELETE /api/bots/1 - Scrap robot into scrapyard (soft delete)
    @DeleteMapping("/{id}")
    public String scrapBot(@PathVariable Long id) {
        combatBotService.scrapBot(id);
        return "Mech #" + id + " was decommissioned into the Scrapyard.";
    }

    // GET /api/bots/scrapped - List all destroyed/scrapped robots
    @GetMapping("/scrapped")
    public List<CombatBot> getScrappedBots() {
        return combatBotService.getScrappedBots();
    }

    // PUT /api/bots/1/repair - Restore/rebuild robot from scrapyard
    @PutMapping("/{id}/repair")
    public CombatBot repairBot(@PathVariable Long id) {
        return combatBotService.repairBot(id);
    }

    // GET /api/bots/leaderboard - Hall of Fame / Top Wins
    @GetMapping("/leaderboard")
    public List<CombatBot> getLeaderboard() {
        return combatBotService.getLeaderboard();
    }

    // POST /api/bots/seed - Seed legendary starter champions
    @PostMapping("/seed")
    public List<CombatBot> seedChampions() {
        return combatBotService.seedPresetChampions();
    }

    // POST /api/bots/duel - Run live simulated robot battle
    @PostMapping("/duel")
    public DuelResult executeDuel(@RequestBody DuelRequest request) {
        return combatBotService.executeDuel(request);
    }
}
