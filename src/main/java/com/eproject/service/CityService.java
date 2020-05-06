package com.eproject.service;

import com.eproject.entity.TAddressCity;

import java.util.List;

public interface CityService {

    List<TAddressCity> findByProvinceCode(int provinceCode);
}
