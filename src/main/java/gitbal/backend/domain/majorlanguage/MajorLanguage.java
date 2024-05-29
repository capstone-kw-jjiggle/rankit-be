package gitbal.backend.domain.majorlanguage;

import gitbal.backend.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MajorLanguage extends BaseTimeEntity {


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




    public void updateMajorLanguage(MajorLanguage beforeLanguage, MajorLanguage updateLanguage){
        beforeLanguage.setMajorLanguage(updateLanguage.majorLanguage);
        beforeLanguage.setLanguageCount(updateLanguage.languageCount);
    }


    @Override
    public String toString() {
        return "MajorLanguage{" +
            "majorLanguage='" + majorLanguage + '\'' +
            ", languageCount=" + languageCount +
            '}';
    }
}
