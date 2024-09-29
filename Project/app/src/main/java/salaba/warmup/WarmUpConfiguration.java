package salaba.warmup;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "management.endpoint.warm-up", name = "enable",
        havingValue = "true", matchIfMissing = true)
@Configuration
public class WarmUpConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WarmUpListener warmUpListener() {
        return new WarmUpListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public WarmUpController warmUpController() {
        return new WarmUpController();
    }
}
