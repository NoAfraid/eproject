package com.eproject.controller.commonController;

import com.eproject.common.R;
import com.eproject.entity.TAddressCity;
import com.eproject.entity.TAddressProvince;
import com.eproject.entity.TAddressTown;
import com.eproject.service.CityService;
import com.eproject.service.ProvinceService;
import com.eproject.service.TownService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/Linkage",produces = "application/json;charset=UTF-8")
public class LinkageController {

    @Resource
    private ProvinceService provinceService;

    @Resource
    private CityService cityService;

    @Resource
    private TownService townService;

    /**
     * 获取三级联动
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getProvince",produces = "application/json;charset=UTF-8")
    public R getProvince(){
        List<TAddressProvince> provinceList = provinceService.selectAll();
        return R.ok().put("data",provinceList);
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getCity",produces = "application/json;charset=UTF-8")
    public R getCity(@RequestBody Map<String, Object> result){
        int provinceCode = Integer.parseInt(result.get("provinceCode") + "");
        List<TAddressCity> cityList = cityService.findByProvinceCode(provinceCode);
        return R.ok().put("data",cityList);
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getTown",produces = "application/json;charset=UTF-8")
    public R getTown(@RequestBody Map<String, Object> result){
        int cityCode = Integer.parseInt(result.get("cityCode") + "");
        List<TAddressTown> townList = townService.findByCityCode(cityCode);
        return R.ok().put("data",townList);
    }
}
