package dev.matuszewski.decerto.operation.reposotory;

import dev.matuszewski.decerto.operation.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findTopByOrderByIdDesc();
}
