package org.iase24.nikolay.kirilyuk.repository;

import org.iase24.nikolay.kirilyuk.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
