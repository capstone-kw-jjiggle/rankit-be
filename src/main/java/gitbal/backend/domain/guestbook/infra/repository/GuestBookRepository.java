package gitbal.backend.domain.guestbook.infra.repository;

import gitbal.backend.domain.guestbook.GuestBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>{

    @Query(value = "SELECT * FROM guest_book g ORDER BY g.created_at DESC LIMIT 30", nativeQuery = true)
    List<GuestBook> findGuestBookTop30();
}
