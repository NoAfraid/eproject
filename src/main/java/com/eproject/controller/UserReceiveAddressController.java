package com.eproject.controller;

import com.eproject.common.R;
import com.eproject.entity.UserReceiveAddress;
import com.eproject.service.UserReceiveAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/address",produces = "application/json;charset=UTF-8")
public class UserReceiveAddressController {

    @Resource
    private UserReceiveAddressService userReceiveAddressService;

    /**
     * 添加收货地址
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/add",produces = "application/json;charset=UTF-8")
    public R add(@RequestBody UserReceiveAddress address){
//        address.setRegion(address.);
        int count = userReceiveAddressService.add(address);
        if (count > 0){
            return R.ok().put("date","添加成功");
        }
        return R.error(-1,"添加出错");
    }

    /**
     * 删除收货地址
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/delete",produces = "application/json;charset=UTF-8")
    public R delete(@RequestBody Integer id){
        int num = userReceiveAddressService.delete(id);
        if (num > 0){
            return R.ok().put("0","删除成功");
        }
        return R.error(-1,"删除出错");
    }

    /**
     * 修改收货地址
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/update",produces = "application/json;charset=UTF-8")
    public R update(@RequestBody UserReceiveAddress address){
        int num = userReceiveAddressService.update(address.getId(),address);
        if (num > 0){
            return R.ok().put("data","修改成功");
        }
        return R.error(-1,"修改出错");
    }

    /**
     * 根据id获取用户地址
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectById",produces = "application/json;charset=UTF-8")
    public R selectById(@RequestBody Integer id){
        return R.ok().put("data",userReceiveAddressService.selectById(id));
    }
    /**
     * 获取收货地址列表
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/addressList",produces = "application/json;charset=UTF-8")
    public R<List<UserReceiveAddress>> list(@RequestBody UserReceiveAddress address){
        List<UserReceiveAddress> addressList = userReceiveAddressService.list(address);
        if (addressList.size() >0){
            return R.ok().put("data",addressList);
        }
        return R.error(-1,"获取出错");
    }

    /**
     * 获取收货地址列表
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getItem",produces = "application/json;charset=UTF-8")
   public R<UserReceiveAddress> getItem(@RequestParam("userId") Integer userId){
        UserReceiveAddress address = userReceiveAddressService.getItem(userId);
        if (address != null){
            return R.ok().put("addressList",address);
        }
        return R.error(-1,"获取出错");
    }

    /**
     * 设置为默认地址
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateDefaultStatus",produces = "application/json;charset=UTF-8")
    public R updateDefaultStatus(@RequestBody UserReceiveAddress address){
        userReceiveAddressService.updateDefaultStatus(address);
        return R.ok();
    }
}
