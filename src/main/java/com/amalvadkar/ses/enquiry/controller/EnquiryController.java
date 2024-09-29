package com.amalvadkar.ses.enquiry.controller;

import com.amalvadkar.ses.common.models.response.CustomResponse;
import com.amalvadkar.ses.enquiry.models.request.EnquiryCreateRequest;
import com.amalvadkar.ses.enquiry.models.request.EnquiryUpdateRequest;
import com.amalvadkar.ses.enquiry.services.EnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ses/enquiries")
public class EnquiryController {

    public static final String ENDPOINT_CREATE_ENQUIRY = "/create-enquiry";
    public static final String ENDPOINT_UPDATE_ENQUIRY = "/update-enquiry";
    public static final String ENDPOINT_ADD_ENQUIRY_LOAD_META_DATA = "/add-enquiry-load-metaData";
    private final EnquiryService enquiryService;

    @PostMapping(ENDPOINT_CREATE_ENQUIRY)
    public ResponseEntity<CustomResponse> createEnquiry(@Valid @RequestBody EnquiryCreateRequest enquiryCreateRequest){
        return ResponseEntity.ok(this.enquiryService.createEnquiry(enquiryCreateRequest));
    }

    @PutMapping(ENDPOINT_UPDATE_ENQUIRY)
    public ResponseEntity<CustomResponse> updateEnquiry(@Valid @RequestBody EnquiryUpdateRequest enquiryUpdateRequest){
        return ResponseEntity.ok(this.enquiryService.updateEnquiry(enquiryUpdateRequest));
    }

    @PostMapping(ENDPOINT_ADD_ENQUIRY_LOAD_META_DATA)
    public ResponseEntity<CustomResponse> fetchAddEnquiryMetaData(){
       return   ResponseEntity.ok(this.enquiryService.fetchAddEnquiryMetaData());
    }


}
