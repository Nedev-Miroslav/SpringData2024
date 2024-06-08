package com.example.w14exercisespringdataautomappingobjects.service;

import com.example.w14exercisespringdataautomappingobjects.service.dtos.CartItemDTO;

public interface ShoppingCartService {

    String addItem(CartItemDTO item);
    String deleteItem(CartItemDTO item);
    String buyItem();
}
