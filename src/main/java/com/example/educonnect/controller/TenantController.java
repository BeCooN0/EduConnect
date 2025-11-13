package com.example.educonnect.controller;

import com.example.educonnect.dto.TenantRequestDto;
import com.example.educonnect.dto.TenantResponseDto;
import com.example.educonnect.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RequestMapping("/api/tenants")
@RestController
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;
    @PostMapping
    public ResponseEntity<TenantResponseDto> createTenant(@RequestBody TenantRequestDto tenantRequestDto){
        TenantResponseDto tenant = tenantService.createTenant(tenantRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(tenant.getId())
                .toUri();
        return ResponseEntity.created(location).body(tenant);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TenantResponseDto>> getAllTenant(@PageableDefault(size = 10, page = 0) Pageable pageable){
        Page<TenantResponseDto> allTenant = tenantService.getAllTenant(pageable);
        return ResponseEntity.ok(allTenant);
    }

}