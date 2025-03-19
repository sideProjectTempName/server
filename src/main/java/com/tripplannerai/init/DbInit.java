package com.tripplannerai.init;

import com.tripplannerai.mapper.category.CategoryFactory;
import com.tripplannerai.service.address.AddressService;
import com.tripplannerai.service.category.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DbInit {
    private final AddressService addressService;
    private final CategoryService categoryService;

    @PostConstruct
    @Transactional
    public void dbInit() {
        try {
            addressService.saveAddressFromApi();
            categoryService.saveCategoryFromApi();
        } catch (Exception e) {
            throw new RuntimeException("데이터베이스 초기화 실패",e);
        }
    }
}
