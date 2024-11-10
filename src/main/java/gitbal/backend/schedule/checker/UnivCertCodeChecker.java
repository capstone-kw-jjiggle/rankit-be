package gitbal.backend.schedule.checker;

import gitbal.backend.domain.univcert.infra.UnivCertRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnivCertCodeChecker {

    private final UnivCertRepository univCertRepository;


    public void checkExpireCertCodeAndClear(){
        univCertRepository.findAll().stream()
            .filter(univCert -> univCert.getExpireDate().isBefore(LocalDateTime.now()))
            .forEach(univCertRepository::delete);
    }




}
