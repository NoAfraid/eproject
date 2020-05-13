package com.eproject.common;

public class Contants {

    public final static String FILE_UPLOAD_DIC = "D:\\Workspaces\\eproject\\src\\main\\resources\\static\\upload\\";//上传文件的默认url前缀，根据部署设置自行修改

    public final static String Image = "http://localhost/upload";

    public final static String MALL_VERIFY_CODE_KEY = "mallVerifyCode";//验证码key

    public final static int INDEX_CAROUSEL_NUMBER = 5;//首页轮播图数量(可根据自身需求修改)
    public final static int INDEX_GOODS_NEW_NUMBER = 5;//首页新品数量
    public final static int INDEX_GOODS_HOT_NUMBER = 10;//首页热卖商品数量
    public final static int INDEX_GOODS_RECOMMOND_NUMBER = 10;//首页推荐商品数量
    public final static int INDEX_HOTSEARCH_NUMBER = 5;//首页推荐商品数量

    public final static String MALL_USER_SESSION_KEY = "loginUser";//session中user的key

    public final static int GOODS_SEARCH_PAGE_LIMIT = 10;//搜索分页的默认条数(每页10条)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 3;//我的订单列表分页的默认条数(每页3条)

    public final static int SELL_STATUS_UP = 0;//商品上架状态
    public final static int SELL_STATUS_DOWN = 1;//商品下架状态

}
