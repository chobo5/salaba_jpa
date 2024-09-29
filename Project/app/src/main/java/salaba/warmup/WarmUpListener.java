package salaba.warmup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
@Slf4j
public class WarmUpListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private ServerProperties serverProperties;
    @Value("${management.endpoint.warm-up.enable:true}")
    private boolean enable;
    @Value("${management.endpoint.warm-up.times:5}")
    private int times;

    //요청 바디 초기화
    private static WarmUpRequest body() {
        final WarmUpRequest warmUpRequest = new WarmUpRequest();
        warmUpRequest.setValidTrue(true);
        warmUpRequest.setValidFalse(false);
        warmUpRequest.setValidString("warm up");
        warmUpRequest.setValidNumber(15);
        warmUpRequest.setValidBigDecimal(BigDecimal.TEN);
        return warmUpRequest;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (enable && event.getApplicationContext() instanceof ServletWebServerApplicationContext) {
            AnnotationConfigServletWebServerApplicationContext context =
                    (AnnotationConfigServletWebServerApplicationContext) event.getApplicationContext();

            String contextPath = serverProperties.getServlet().getContextPath();
            int port = serverProperties.getPort() != null && serverProperties.getPort() != 0 ?
                    serverProperties.getPort() : context.getWebServer().getPort();

            if (contextPath == null) {
                contextPath = "";
            }

            final String url = "http://localhost:" + port + contextPath + "/warm-up";
            log.info("Starting warm up application. Endpoint: {}, {} times", url, times);

            RestTemplate restTemplate = new RestTemplate();

            for (int i = 0; i < times; i++) {
                ResponseEntity<String> response = restTemplate.postForEntity(url, body(), String.class);
                log.debug("Warm up response:{}", response);
            }

            log.info("Completed warm up application");
        }
    }
}