package org.iase24.nikolay.kirilyuk.repository;

import org.iase24.nikolay.kirilyuk.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
