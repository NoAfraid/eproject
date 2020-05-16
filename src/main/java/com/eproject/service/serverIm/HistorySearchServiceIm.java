package com.eproject.service.serverIm;

import com.eproject.dao.HistorySearchDao;
import com.eproject.entity.HistorySearch;
import com.eproject.entity.Product;
import com.eproject.service.HistorySearchService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistorySearchServiceIm implements HistorySearchService {

    @Resource
    private HistorySearchDao historySearchDao;


    @Override
    public List<HistorySearch> getProductForHistorySearch(int number, int userId){
        List<HistorySearch> carouseList = new ArrayList<>(number);
        List<HistorySearch> list = historySearchDao.getProductForHistorySearch(number,userId);
        HistorySearch h = new HistorySearch();
        Object[] ob = list.toArray();
        if (!CollectionUtils.isEmpty(list)) {
//            for (int i = 0; i < ob.length; i++) {
//                //转化为Product对象
//                HistorySearch historySearch = (HistorySearch) ob[i];
//                int count = historySearch.getSearchCount();
//                //每搜索一次，searchCount加一
//                count++;
//                h.setUserId(userId);
//                h.setUpdateTime(new Date());
//                h.setSearchCount(count);
//                historySearchDao.updateSearchCount(h);
//            }
            return list;
        }
        return null;
    }
}
