var vm = new Vue({
    el: "#app",
    data:{
        province:'',
        shi: '',
        qu: '',
        provinceList:{
            provinceCode:"",
        },
        city:{
            cityCode:"",
        },
        town:[],
        address:{
            userId:'',
            province:'',
            city: '',
            region: '',
            detailAddress:'',
            phoneNumber:'',
            name:'',
            postCode:'',
            defaultStatus:'',
        },
        addressList:[],
        vip: [],
        user: {nick: ''},
        cart: {count: 0},
    },
    mounted: function () {
        this.AddressList();
        this.getUserInfo();
        this.getCartInfo();
        // this.linkage()
    },
    methods: {
        linkage: function (event) {
            this.province = "110000"
            var formData = JSON.stringify();
            $.ajax({
                type: "post",
                url: "http://localhost:8080/Linkage/getProvince",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.provinceList = result.data;
                    } else {
                        alert(result.msg)
                    }
                },
            })
            this.getCity()
        },
        getCity: function(){
            if ($.trim(this.province) == ''|| $.trim(this.province) == null) {
                var t={
                    provinceCode:"110100",
                }
            }else {
                var t={
                    provinceCode:this.province,
                }
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/Linkage/getCity",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.city = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
            this.getTown()
        },
        getTown: function () {
            if ($.trim(this.shi) == ''|| $.trim(this.shi) == null) {
                var t={
                    cityCode:"110101",
                }
            }else {
                var t={
                    cityCode:this.shi,
                }
            }
            var formData = JSON.stringify(t);
            $.ajax({
                type: "post",
                url: "http://localhost:8080/Linkage/getTown",
                contentType: "application/json;charset=utf-8",
                dataType : "json",
                data: formData,
                success: function (result) {
                    if(result.code == 0) {
                        vm.town = result.data;
                    } else {
                        alert(result.msg)
                    }
                }
            })
        },

        /**
         * 添加地址
         */
        addAddress: function () {
            if ($.trim(this.address.detailAddress) == '') {
                alert("请输入详细地址");
                window.location.reload();
            }
            if ($.trim(this.address.phoneNumber) == '') {
                alert("请输入联系电话")
                window.location.reload();
            }
            if ($.trim(this.address.name) == '') {
                alert("请输入收货人姓名");
                window.location.reload();
            }
            var userId = getCookie("sessionId");
            this.address.userId = userId;
            this.address.province = this.province;
            if ($.trim(this.address.province) == '') {
                alert("请选择收货省份");
                window.location.reload();
            }
            this.address.city = this.shi;
            if ($.trim(this.address.city) == '') {
                alert("请选择收货城市");
                window.location.reload();
            }
            this.address.region =this.qu;
            if ($.trim(this.address.region) == '') {
                alert("请选择所在区域");
                window.location.reload();
            }
            var formData = JSON.stringify(this.address);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url:  "http://localhost:8080/address/add",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        alert(result.msg);
                    } else {
                        alert(result.msg);
                    }
                }
            })
        },

        /**
         * 收货地址列表
         */
        AddressList: function (page) {
            var userId = getCookie("sessionId");
            var t = {
                userId: userId
            };
            var formData = JSON.stringify(t);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url: "http://localhost:8080/address/addressList",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.addressList = result.data;
                    } else {
                        alert(result.pages);
                    }
                }
            });
        },

        /**
         * 删除收货地址
         */
        deleteAddress: function (id) {
            var formData = JSON.stringify(id);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url: "http://localhost:8080/address/delete",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                    } else {
                        alert(result.pages);
                    }
                }
            });
        },

        /**
         * 根据id用户地址
         */
        selectById: function (id) {
            var formData = JSON.stringify(id);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url: "http://localhost:8080/address/selectById",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        vm.address = result.data;
                        vm.province = vm.address.province;
                        vm.shi = vm.address.city;
                        vm.qu =vm.address.region;
                        this.province =vm.address.province;
                        this.shi = vm.address.city;
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },

        /**
         * 设为默认地址
         */
        updateDefaultStatus:function(id){
            var userId = getCookie("sessionId");
            var t = {
                userId: userId,
                defaultStatus:1,
                id:id
            };
            var formData = JSON.stringify(t);
            var now =  getNow("yyyyMMddHHmmss");
            var sign  = signString(formData,now);
            $.ajax({
                headers:{
                    client:client,
                    version:version,
                    requestTime:now,
                    sign:sign
                },
                type: "post",
                url: "http://localhost:8080/address/updateDefaultStatus",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: formData,
                success: function (result) {
                    if (result.code == 0) {
                        // return;
                        window.location.reload();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        /**
         * 获取用户信息
         */
        getUserInfo: function () {
            this.vip = getCookie("loginUser");
            if (this.vip == null){
                return;
            } else {
                var userId = getCookie("sessionId");

                if (userId == null || userId ==''){
                    return;
                } else {
                    var t = {
                        id: userId
                    };
                    var formData = JSON.stringify(t);
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/user/selectInfo",
                        contentType: "application/json;charset=utf-8",
                        dataType : "json",
                        data: formData,
                        success: function (result) {
                            if(result.code == 0) {
                                vm.user = result.data
                                console.log(vm.user.nick)
                            } else {
                                alert(result.msg)
                            }
                        }
                    })
                }
            }


        },

        /**
         * 获取购物车信息
         */
        getCartInfo: function () {
            this.vip = getCookie("loginUser");
            if (this.vip == null){
                return;
            } else {
                var userId = getCookie("sessionId");
                if (userId == null || userId ==''){
                    return;
                } else {
                    var t = {
                        userId: userId
                    };
                    var formData = JSON.stringify(t);
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/cart/count",
                        contentType: "application/json;charset=utf-8",
                        dataType: "json",
                        data: formData,
                        success: function (result) {
                            if (result.code == 0) {
                                vm.cart.count = result.data
                                console.log(vm.cart.count)
                            } else {
                                // alert(result.msg)
                            }
                        }
                    })
                }
            }

        },
    },
    beforeMount: function () {
        this.linkage();

    },
})