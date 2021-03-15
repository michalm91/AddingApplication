package dev.matuszewski.decerto.source.reposotory;

import dev.matuszewski.decerto.source.model.RandomNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomNumberRepository extends JpaRepository<RandomNumber, Long> {
}
