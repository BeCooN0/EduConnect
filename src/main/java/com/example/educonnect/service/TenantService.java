package com.example.educonnect.service;

import com.example.educonnect.dto.TenantRequestDto;
import com.example.educonnect.dto.TenantResponseDto;
import com.example.educonnect.entity.Tenant;
import com.example.educonnect.entity.enums.PlanType;
import com.example.educonnect.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TenantService {
    private final ModelMapper modelMapper;
    private final TenantRepository tenantRepository;
    private final TenantManagementService tenantManagementService;
    @Transactional
    public TenantResponseDto createTenant(TenantRequestDto tenantRequestDto){
        Tenant tenant = modelMapper.map(tenantRequestDto, Tenant.class);
        String s = generateIdentifierFromName(tenantRequestDto.getName());
        tenant.setSubdomain(s);
        tenant.setPlan(PlanType.FREE);
        Tenant saved = tenantRepository.save(tenant);
        tenantManagementService.createTenantSchema(s);
        return modelMapper.map(saved, TenantResponseDto.class);
    }
    public String generateIdentifierFromName(String name){
        return name.toLowerCase().replaceAll("\\s", "-" )
                .replaceAll("[^a-z0-9-]", "");
    }

    public Page<TenantResponseDto> getAllTenant(Pageable pageable){
        Page<Tenant> tenants = tenantRepository.findAll(pageable);
        return tenants.map(tenant -> modelMapper.map(tenant, TenantResponseDto.class));
    }
}