package org.izouir.product_service.service;

import org.izouir.product_service.dto.FilterRequestDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SpecificationService<T> {
    Specification<T> getSearchSpecification(final List<FilterRequestDto> filters);
}
