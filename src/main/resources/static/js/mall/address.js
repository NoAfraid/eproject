var vm = new Vue({
    el: "#app",
    data:{
        province:'',
        sheng: '',
        shi: '',
        qu: '',
        provinceList:{
            provinceCode:"",
        },
        city:{
            cityCode:"",
        },
        town:[],
    },
    methods: {
        linkage: function (event) {

            var t={
                provinceCode:this.provinceList.provinceCode,
                cityCode: this.city.cityCode
            }
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
            this.$forceUpdate();
            var t={
                provinceCode:this.province,
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
            this.$forceUpdate();
            console.log(this.city.cityCode)
            var t={
                cityCode: this.shi
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
    },
    beforeMount: function () {
        this.linkage();
    },
})