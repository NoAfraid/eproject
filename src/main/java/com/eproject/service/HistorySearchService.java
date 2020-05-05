package com.eproject.service;

import com.eproject.entity.HistorySearch;

import java.util.List;

public interface HistorySearchService {

    /**
     * 历史搜索（首页调用）
     */
    List<HistorySearch> getProductForHistorySearch(int number, int userId);
}
