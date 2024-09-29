package com.amalvadkar.ses.enquiry.services;


import com.amalvadkar.ses.ApplicationProperties;
import com.amalvadkar.ses.common.models.response.CustomResponse;
import com.amalvadkar.ses.enquiry.constants.EnquiryResConstants;
import com.amalvadkar.ses.enquiry.entities.CourseEntity;
import com.amalvadkar.ses.enquiry.entities.EnquiryCourseEntity;
import com.amalvadkar.ses.enquiry.entities.EnquiryEntity;
import com.amalvadkar.ses.enquiry.exceptions.EnquiryNotFoundException;
import com.amalvadkar.ses.enquiry.models.request.EnquiryCreateRequest;
import com.amalvadkar.ses.enquiry.models.request.EnquiryUpdateRequest;
import com.amalvadkar.ses.enquiry.models.request.response.AddEnquiryOnLoadResponse;
import com.amalvadkar.ses.enquiry.repositories.CourseRepository;
import com.amalvadkar.ses.enquiry.repositories.EnquiryCourseRepository;
import com.amalvadkar.ses.enquiry.repositories.EnquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.amalvadkar.ses.enquiry.constants.EnquiryConstants.CLASS_MODES_METADATA_KEY;
import static com.amalvadkar.ses.enquiry.constants.EnquiryConstants.COURSE_LIST_METADATA_KEY;
import static com.amalvadkar.ses.enquiry.constants.EnquiryResConstants.ENQUIRY_CREATED_SUCCESSFULLY_RES_MSG;
import static com.amalvadkar.ses.enquiry.constants.EnquiryResConstants.FETCHED_SUCCESSFULLY_RES_MSG;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;
    private final CourseRepository courseRepo;
    private final EnquiryCourseRepository enquiryCourseRepository;
    private final ApplicationProperties applicationProperties;

    @Transactional
    public CustomResponse createEnquiry(EnquiryCreateRequest enquiryCreateRequest) {

        // save enquiry
        EnquiryEntity enquiryEntity = new EnquiryEntity();
        enquiryEntity.setName(enquiryCreateRequest.name());
        enquiryEntity.setClassMode(enquiryCreateRequest.classMode());
        enquiryEntity.setContactNumber(enquiryCreateRequest.contactNumber());
        EnquiryEntity savedEnquiry = enquiryRepository.save(enquiryEntity);

        // save course enquiry mapping
        List<EnquiryCourseEntity> enquiryCourseEntities = new ArrayList<>();
        for (Long courseId : enquiryCreateRequest.courseIds()) {
            CourseEntity courseEntity = courseRepo.getReferenceById(courseId);
            EnquiryCourseEntity enquiryCourseEntity = new EnquiryCourseEntity();
            enquiryCourseEntity.setEnquiryEntity(savedEnquiry);
            enquiryCourseEntity.setCourseEntity(courseEntity);
            enquiryCourseEntities.add(enquiryCourseEntity);
        }
        enquiryCourseRepository.saveAll(enquiryCourseEntities);

        return CustomResponse.success(savedEnquiry.getId(), ENQUIRY_CREATED_SUCCESSFULLY_RES_MSG);
    }

    @Transactional
    public CustomResponse updateEnquiry(EnquiryUpdateRequest enquiryUpdateRequest) {
        //update Enquiry
        EnquiryEntity enquiry = makeEnquiryForUpdate(enquiryUpdateRequest);
        EnquiryEntity updatedEnquiry = enquiryRepository.save(enquiry);

        int markedDeletedCount = this.enquiryCourseRepository.markAllEnquiryCourseToDeleteFlagTrueForEnquiry(updatedEnquiry.getId());
        log.debug("made all course entities deleted flag true for enquiry = {}, count = {}", enquiry.getId(), markedDeletedCount);
        List<EnquiryCourseEntity> enquiryCourseEntities = new ArrayList<>();
        for (Long courseId : enquiryUpdateRequest.courseIds()) {
            EnquiryCourseEntity enquiryCourseEntity = fetchIfExistsElsePrepareNew(enquiry, courseId);
            enquiryCourseEntity.setCourseEntity(courseRepo.getReferenceById(courseId));
            enquiryCourseEntity.setEnquiryEntity(updatedEnquiry);
            enquiryCourseEntity.setDeleteFlag(false);
            enquiryCourseEntities.add(enquiryCourseEntity);
        }
        enquiryCourseRepository.saveAll(enquiryCourseEntities);
        return CustomResponse.success(updatedEnquiry.getId(), EnquiryResConstants.ENQUIRY_UPDATED_SUCCESSFULLY_RES_MSG);
    }

    public CustomResponse fetchAddEnquiryMetaData() {
        Map<String, Object> metadata = Map.of(
                CLASS_MODES_METADATA_KEY, applicationProperties.classModeDropDownOptions(),
                COURSE_LIST_METADATA_KEY, this.courseRepo.fetchAllActiveCourses());
        return CustomResponse.success(new AddEnquiryOnLoadResponse(metadata), FETCHED_SUCCESSFULLY_RES_MSG);
    }

    private EnquiryEntity makeEnquiryForUpdate(EnquiryUpdateRequest enquiryUpdateRequest) {
        EnquiryEntity enquiry = this.enquiryRepository.findEnquiry(enquiryUpdateRequest.id()).
                orElseThrow(() -> new EnquiryNotFoundException("Enquiry Not Found"));

        enquiry.setEnquiryStatus(enquiryUpdateRequest.enquiryStatus());
        enquiry.setContactNumber(enquiryUpdateRequest.contactNumber());
        enquiry.setName(enquiryUpdateRequest.name());
        enquiry.setClassMode(enquiryUpdateRequest.classMode());
        return enquiry;
    }
    private EnquiryCourseEntity fetchIfExistsElsePrepareNew(EnquiryEntity enquiry, Long courseId) {
        return this.enquiryCourseRepository.fetchEnquiryCourseEntityByCourseAndEnquiry(courseId, enquiry.getId())
                .orElseGet(EnquiryCourseEntity::new);
    }

}
