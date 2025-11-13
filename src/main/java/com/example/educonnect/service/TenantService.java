package com.example.educonnect.service;

import com.example.educonnect.dto.TenantRequestDto;
import com.example.educonnect.dto.TenantResponseDto;
import com.example.educonnect.entity.Tenant;
import com.example.educonnect.entity.enums.PlanType;
import com.example.educonnect.mapper.TenantMapper;
import com.example.educonnect.repository.TenantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TenantService {
    private final TenantMapper tenantMapper;
    private final TenantRepository tenantRepository;
    private final TenantManagementService tenantManagementService;
    @Transactional
    public TenantResponseDto createTenant(TenantRequestDto tenantRequestDto){
        Tenant tenant = tenantMapper.toTenant(tenantRequestDto);
        String s = generateIdentifierFromName(tenantRequestDto.getName());
        tenant.setSubdomain(s);
        tenant.setPlan(PlanType.FREE);
        Tenant saved = tenantRepository.save(tenant);
        tenantManagementService.createTenantSchema(s);
        return tenantMapper.toDto(saved);
    }
    public String generateIdentifierFromName(String name){
        return name.toLowerCase().replaceAll("\\s", "-" )
                .replaceAll("[^a-z0-9-]", "");
    }

    public Page<TenantResponseDto> getAllTenant(Pageable pageable){
        Page<Tenant> tenants = tenantRepository.findAll(pageable);
        return tenants.map(tenantMapper::toDto);
    }
    public TenantResponseDto updateTenant(Long id, TenantRequestDto tenantRequestDto){
        Tenant tenant = tenantRepository.findById(id).orElseThrow();
        tenantMapper.updateTenantFromDto(tenantRequestDto, tenant);
        Tenant saved = tenantRepository.save(tenant);
        return tenantMapper.toDto(saved);
    }
}