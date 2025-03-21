package com.tripplannerai.repository.category;

import com.tripplannerai.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long>,CategoryCustomRepository {
}
