package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    Map<String, String> env = new HashMap<>();

    public EnvController(@Value("${PORT:NOT SET}") String port, @Value("${MEMORY_LIMIT:NOT SET}") String mem, @Value("${CF_INSTANCE_INDEX:NOT SET}") String inst, @Value("${CF_INSTANCE_ADDR:NOT SET}") String address) {
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", mem);
        env.put("CF_INSTANCE_INDEX", inst);
        env.put("CF_INSTANCE_ADDR", address);
    }

    public String get(String key) {
        return env.get(key);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return env;
    }
}
