package com.example.demo.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.domain.dto.admin.order.OrderDetailDTO;
import com.example.demo.domain.entities.OrderDetail;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderDetailDTOMapper implements Function<OrderDetail, OrderDetailDTO> {

	@Override
	public OrderDetailDTO apply(OrderDetail t) {
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setNumberItems(t.getQuantity());
		dto.setSellingPrice(t.getProductPrice());
		dto.setUnitPrice(t.getUnitPrice());
		dto.setIdProduct(t.getProduct().getId());
		dto.setProductName(t.getProductName());
		return dto;
	}

}
