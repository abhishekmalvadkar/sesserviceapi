package com.amalvadkar.ses.enquiry.repositories;

import com.amalvadkar.ses.enquiry.entities.EnquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EnquiryRepository extends JpaRepository<EnquiryEntity ,Long> {

    @Query("""
            select ee from EnquiryEntity ee where ee.id = :id and ee.deleteFlag=false 
            """)
    Optional<EnquiryEntity> findEnquiry(@Param("id") Long id);
}
