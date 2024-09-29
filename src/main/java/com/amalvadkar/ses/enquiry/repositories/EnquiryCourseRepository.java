package com.amalvadkar.ses.enquiry.repositories;

import com.amalvadkar.ses.enquiry.entities.EnquiryCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EnquiryCourseRepository extends JpaRepository<EnquiryCourseEntity, Long> {


    @Modifying
    @Query("""
            UPDATE EnquiryCourseEntity ec
            SET ec.deleteFlag = true
            WHERE ec.enquiryEntity.id = :enquiryId
            """)
    int markAllEnquiryCourseToDeleteFlagTrueForEnquiry(@Param("enquiryId") Long enquiryId);

    @Query("""
            select ec from  EnquiryCourseEntity ec
            where ec.enquiryEntity.id = :enquiryId
            and ec.courseEntity.id = :courseId
            """)
    Optional<EnquiryCourseEntity> fetchEnquiryCourseEntityByCourseAndEnquiry(@Param("courseId") Long courseId,
                                                                             @Param("enquiryId") Long enquiryId);


}
