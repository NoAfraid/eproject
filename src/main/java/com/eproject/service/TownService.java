package com.eproject.service;

import com.eproject.entity.TAddressTown;

import java.util.List;

public interface TownService {

    List<TAddressTown> findByCityCode(int cityCode);

}
