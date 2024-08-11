package gitbal.backend.global.util;

public interface ContributorUpdater<T> {

    void updateContributor(T t, String username, Long newScore);
}
