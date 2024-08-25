package org.izouir.order_service.service;

import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.shared_lib.entity.Order;
import org.izouir.shared_lib.entity.OrderPosition;

import java.util.List;

public interface OrderPositionService {
    void place(Order order, List<OrderPositionDto> orderPositionDtoList);

    void decline(List<OrderPosition> orderPositions);
}
