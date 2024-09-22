package com.amalvadkar.ses.account.mapper;

import com.amalvadkar.ses.account.entities.UserEntity;
import com.amalvadkar.ses.account.models.request.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    UserEntity toEntity(SignUpRequest signUpRequest);
}
