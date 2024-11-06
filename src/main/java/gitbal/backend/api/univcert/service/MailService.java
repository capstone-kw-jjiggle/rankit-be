package gitbal.backend.api.univcert.service;

import gitbal.backend.domain.univcert.UnivCertEntity;
import gitbal.backend.domain.univcert.infra.UnivCertRepository;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final UnivCertRepository univCertRepository;


    @Transactional
    public void sendMail(String to) {
        String code = createCode(); // 인증코드 생성

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("랭깃 인증 코드 발급 안내");
            helper.setText(setContext(code), true);
            helper.addInline("rankitLogo", new ClassPathResource("img/rankit.png")); // 로고 파일 경로 수정 필요

            mailSender.send(message);
            univCertRepository.save(UnivCertEntity.of(code, to));
        } catch (Exception e) {
            throw new MailSendException("메일 전송에 실패했습니다.");
        }
    }


    private String setContext(String code) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
       String mail = templateEngine.process("mail", context);
        log.info("mail : " + mail);
        return mail; // mail.html
    }

    private String createCode() {
        Random random = new Random();
        int i = random.nextInt(1000, 9999);
        return String.valueOf(i);
    }

    public Boolean certCode(String mail, String code) {
        UnivCertEntity univCertEntity = univCertRepository.findByEmail(mail)
            .orElseThrow(IllegalAccessError::new);
        log.info("univCertEntity : " + univCertEntity.toString());
        log.info("code : " + code);
        return univCertEntity.getCode().equals(code);
    }


    public void clearEmail(String email) {
        log.info("clearEmail : " + email);
        univCertRepository.deleteByEmail(email);
    }
}
