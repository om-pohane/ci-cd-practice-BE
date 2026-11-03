package com.thinkitive.practice2.service;

import com.thinkitive.practice2.dto.CreateWishRequest;
import com.thinkitive.practice2.dto.SubmitScoreRequest;
import com.thinkitive.practice2.entity.DiwaliWish;
import com.thinkitive.practice2.entity.SafaiScore;
import com.thinkitive.practice2.repository.DiwaliWishRepository;
import com.thinkitive.practice2.repository.SafaiScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiwaliService {

    private final DiwaliWishRepository wishRepository;
    private final SafaiScoreRepository scoreRepository;

    public DiwaliService(DiwaliWishRepository wishRepository, SafaiScoreRepository scoreRepository) {
        this.wishRepository = wishRepository;
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public DiwaliWish createWish(CreateWishRequest req) {
        String from = (req.getFromName() != null && !req.getFromName().trim().isEmpty())
                ? req.getFromName().trim()
                : "Om";
        String to = (req.getToName() != null && !req.getToName().trim().isEmpty())
                ? req.getToName().trim()
                : "Dost";

        // Generate clean friendly slug
        String cleanFrom = from.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String cleanTo = to.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        if (cleanFrom.isEmpty()) cleanFrom = "from";
        if (cleanTo.isEmpty()) cleanTo = "to";
        String randomSuffix = UUID.randomUUID().toString().substring(0, 5);
        String code = cleanFrom + "-" + cleanTo + "-" + randomSuffix;

        String msg = req.getCustomMessage();
        if (msg == null || msg.trim().isEmpty()) {
            msg = "May this Diwali bring endless Kaju Katli, maximum joy, and zero flying chappals into your life! Wishing you and your family a dazzling and Shubh Deepavali!";
        }

        Integer target = (req.getTargetScore() != null && req.getTargetScore() > 0) ? req.getTargetScore() : 1200;

        DiwaliWish wish = new DiwaliWish(code, from, to, msg, target);
        return wishRepository.save(wish);
    }

    @Transactional
    public Optional<DiwaliWish> getWishByCode(String code) {
        Optional<DiwaliWish> wishOpt = wishRepository.findByWishCode(code);
        wishOpt.ifPresent(wish -> {
            wish.setViewsCount(wish.getViewsCount() + 1);
            wishRepository.save(wish);
        });
        return wishOpt;
    }

    @Transactional
    public SafaiScore submitScore(SubmitScoreRequest req) {
        String player = (req.getPlayerName() != null && !req.getPlayerName().trim().isEmpty())
                ? req.getPlayerName().trim()
                : "Anonymous Sweeper";
        int score = req.getScore() != null ? req.getScore() : 0;
        int kaju = req.getKajuKatlis() != null ? req.getKajuKatlis() : 0;
        int diyas = req.getDiyas() != null ? req.getDiyas() : 0;
        int chappals = req.getChappalsDodged() != null ? req.getChappalsDodged() : 0;

        String rankTitle;
        if (score >= 2000) {
            rankTitle = "🏆 Supreme Safai Lord & Mithai King";
        } else if (score >= 1400) {
            rankTitle = "🪔 Mummy Ki Shaan & Chappal Dodger";
        } else if (score >= 800) {
            rankTitle = "🧹 Certified Safai Sevak";
        } else {
            rankTitle = "📦 Soan Papdi Collector";
        }

        SafaiScore safaiScore = new SafaiScore(player, req.getWishCode(), score, kaju, diyas, chappals, rankTitle);
        return scoreRepository.save(safaiScore);
    }

    public List<SafaiScore> getLeaderboard() {
        return scoreRepository.findTop20ByOrderByScoreDesc();
    }

    public List<SafaiScore> getScoresForWish(String wishCode) {
        return scoreRepository.findByWishCodeOrderByScoreDesc(wishCode);
    }
}
