package com.amalvadkar.ses.enquiry.repositories;

import com.amalvadkar.ses.common.models.response.KeyValueResponse;
import com.amalvadkar.ses.enquiry.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity,Long> {

    @Query("""
            select
                new com.amalvadkar.ses.common.models.response.KeyValueResponse
                (
                    ce.id,
                    ce.name
                )
            ce from CourseEntity ce
            where ce.deleteFlag = false
            """)
    List<KeyValueResponse<Long, String>> fetchAllActiveCourses();
}
