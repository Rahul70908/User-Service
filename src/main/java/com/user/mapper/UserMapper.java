package com.user.mapper;

import com.user.entity.User;
import com.user.model.request.SignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "userName", target = "name")
    @Mapping(source = "email", target = "email")
    User formUser(SignUpDto signUpDto);
}
