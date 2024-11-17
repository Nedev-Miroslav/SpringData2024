package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;

import java.util.Set;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query(value = "SELECT b FROM BorrowingRecord b WHERE b.book.genre = 'SCIENCE_FICTION' ORDER BY b.borrowDate DESC")
    Set<BorrowingRecord> findAllByGenre();
}
