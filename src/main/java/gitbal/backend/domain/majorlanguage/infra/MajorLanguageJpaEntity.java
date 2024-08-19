package gitbal.backend.domain.majorlanguage.infra;

import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.BaseTimeEntity;
import gitbal.backend.domain.majorlanguage.MajorLanguage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "major_language")
public class MajorLanguageJpaEntity extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_language_id")
    private Long id;

    private String majorLanguage;

    private Long languageCount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public MajorLanguageJpaEntity(Long id, String majorLanguage, Long languageCount, User user) {
        this.id = id;
        this.majorLanguage = majorLanguage;
        this.languageCount = languageCount;
        this.user=user;
    }




    public void updateMajorLanguage(
        MajorLanguageDto updateLanguage){
        this.setMajorLanguage(updateLanguage.getLanguageName());
        this.setLanguageCount(updateLanguage.getLanguageUsageCount());
    }


    public static MajorLanguageJpaEntity from(MajorLanguage majorLanguage){
        return MajorLanguageJpaEntity.builder()
            .id(majorLanguage.getId())
            .majorLanguage(majorLanguage.getMajorLanguage())
            .languageCount(majorLanguage.getLanguageCount())
            .build();
    }

    public MajorLanguage toDomain(){
        return MajorLanguage.builder()
            .id(this.id)
            .majorLanguage(this.majorLanguage)
            .languageCount(this.languageCount)
            .build();
    }

    @Override
    public String toString() {
        return "MajorLanguage{" +
            "majorLanguage='" + majorLanguage + '\'' +
            ", languageCount=" + languageCount +
            '}';
    }
}
