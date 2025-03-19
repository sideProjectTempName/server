package com.tripplannerai.init;

import com.tripplannerai.mapper.category.CategoryFactory;
import com.tripplannerai.service.address.AddressService;
import com.tripplannerai.service.category.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DbInit {
    private final AddressService addressService;
    private final CategoryService categoryService;


    @PostConstruct
    public void init() {
        try {
            initializeData();
        } catch (Exception e) {
            log.error("데이터베이스 초기화 실패: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public void initializeData() throws Exception {
        log.info("주소 데이터 저장 시작...");
        addressService.saveAddressFromApi();
        log.info("주소 데이터 저장 완료");

        log.info("카테고리 데이터 저장 시작...");
        categoryService.saveCategoryFromApi();
        log.info("카테고리 데이터 저장 완료");
    }
}
