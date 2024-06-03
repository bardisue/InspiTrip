package com.ssafy.trip.model.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReadCountService {
    private final RedisTemplate<String, String> redisTemplate;
    
    public ReadCountService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementReadCount(int influencerId) {
        String key = "readcount:" + influencerId;
        redisTemplate.opsForValue().increment(key);
    }

    public int getReadCount(int influencerId) {
        String key = "readcount:" + influencerId;
        String count = redisTemplate.opsForValue().get(key);
        return count != null ? Integer.parseInt(count) : 0;
    }

    public void addToReadCountRank(int influencerId, double score) {
        redisTemplate.opsForZSet().add("readcount_rank", String.valueOf(influencerId), score);
    }

    public Set<String> getReadCountRank() {
        return redisTemplate.opsForZSet().reverseRange("readcount_rank", 0, -1);
    }
    
    public void resetReadCounts() {
        // Delete all keys related to read counts
        redisTemplate.delete(redisTemplate.keys("readcount:*"));
        redisTemplate.delete("readcount_rank");
    }
}
