package com.codepilot.springbootbasics.repository;

import com.codepilot.springbootbasics.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);

    List<Student> findByNameContaining(String keyword);

    List<Student> findByNameStartingWith(String prefix);

    Page<Student> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    List<Student> findByNameAndEmail(String name, String email);

    @Query("SELECT s FROM Student s WHERE s.name = :name")
    List<Student> searchByName(@Param("name") String name);

    List<Student> findByNameContainingIgnoreCase(
            String keyword,
            Sort sort
    );
}
