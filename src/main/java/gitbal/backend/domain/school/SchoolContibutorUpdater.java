package gitbal.backend.domain.school;

public interface SchoolContibutorUpdater<T> {
    void updateContributor(T school, String username, Long newScore);
}
