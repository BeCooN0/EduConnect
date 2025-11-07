package com.example.educonnect.mapper;

import com.example.educonnect.dto.TenantRequestDto;
import com.example.educonnect.dto.TenantResponseDto;
import com.example.educonnect.entity.Tenant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantResponseDto toDto(Tenant tenant);
    Tenant toTenant(TenantRequestDto tenantRequestDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTenantFromDto(TenantRequestDto tenantRequestDto, @MappingTarget Tenant tenant);
}