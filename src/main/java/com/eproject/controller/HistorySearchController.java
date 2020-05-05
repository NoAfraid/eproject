package com.eproject.controller;

import com.eproject.service.HistorySearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/history",produces = "application/json;charset=UTF-8")
public class HistorySearchController {

    @Resource
    private HistorySearchService historySearchService;

    /**
     *
     */
}
