package gitbal.backend.domain.univcert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "univ_cert")
public class UnivCertEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    @Column(unique = true)
    private String email;

    private LocalDateTime expireDate;


    public UnivCertEntity(String code, String email, LocalDateTime expireDate) {
        this.code = code;
        this.email = email;
        this.expireDate=expireDate;
    }

    public void updateCode(String code, LocalDateTime expireDate) {
        this.code = code;
        this.expireDate=expireDate;
    }

    public static UnivCertEntity of(String code, String email, LocalDateTime expireDate) {
        return new UnivCertEntity(code, email, expireDate);
    }
}
