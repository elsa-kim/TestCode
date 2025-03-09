package sample.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendEmail(String fromEmail, String toEmail, String subject, String content) {
        // 메일 전송
        log.info("메일 전송");
        throw new IllegalArgumentException("메일전송");
    }

    // spy test: 어떤 것은 객체로 사용하고 어떤건 stub 사용하고 싶을 때
    // MailSendClient @Mock으로 설정 시 log 안뜸 / @Spy 설정 시 log 노출
    public void a(){
        log.info("a");
    }
    public void b(){
        log.info("b");
    }
    public void c(){
        log.info("c");
    }
}
