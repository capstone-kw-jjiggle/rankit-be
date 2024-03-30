package gitbal.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorLanguage {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_language_id")
    private Long id;

    private String majorLanguage;

    private Long languageCount;


    @Builder
    public MajorLanguage(String majorLanguage, Long languageCount) {
        this.majorLanguage = majorLanguage;
        this.languageCount = languageCount;
    }

    // TODO: test 용도여서 나중에 실제로 값 넣으면 변경해야함.



}
