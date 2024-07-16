package gitbal.backend.global.util;


public interface ScheduleUpdater<T>{

    void update(T t, String username, Long oldScore, Long newScore);
}
