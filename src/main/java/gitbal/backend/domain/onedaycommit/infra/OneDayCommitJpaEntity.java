package gitbal.backend.domain.onedaycommit.infra;

import gitbal.backend.domain.onedaycommit.application.OneDayCommit;
import gitbal.backend.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayCommitJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_day_commit_id")
    private Long id;


    @ColumnDefault(value = "0")
    private Long steadyCount;


    @Builder
    public OneDayCommitJpaEntity(Long steadyCount) {
        this.steadyCount = steadyCount;
    }


    @Builder
    public OneDayCommitJpaEntity(Long id,Long steadyCount) {
        this.id=id;
        this.steadyCount = steadyCount;
    }

    public static OneDayCommitJpaEntity from(OneDayCommit oneDayCommit){
        return OneDayCommitJpaEntity.builder()
            .steadyCount(oneDayCommit.getSteadyCount())
            .build();
    }

    public OneDayCommit toDomain(){
        return OneDayCommit.builder()
            .steadyCount(this.steadyCount)
            .build();
    }


    // 초기 가입할 때 넣기위한 값
    public static OneDayCommitJpaEntity of(boolean hasRecent) {
        if (hasRecent) {
            return new OneDayCommitJpaEntity(1L);
        }
        return new OneDayCommitJpaEntity(0L);
    }


    public void updateOneDayCommitSteadyCount(boolean hasRecent){
        if(hasRecent) {
            this.steadyCount += 1L;
            return;
        }
        this.steadyCount=0L;
    }

}
