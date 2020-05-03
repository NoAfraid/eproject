// var id = getRequest();
// var orderNo = id['orderNo'];
// var payType = id['payType'];
// console.log(payType)
// function getRequest() {
//     var url = location.search; //获取url中"?"符后的字串
//     // alert(url)
//     var theRequest = new Object();
//     if (url.indexOf("?") != -1) {
//         var str = url.substr(1);
//         strs = str.split("&");
//         // alert(strs)
//         for (var i = 0; i < strs.length; i++) {
//             theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
//             // alert(theRequest)
//         }
//     }
//     // alert(theRequest)
//     return theRequest;
//
// };
//
// var vm = new Vue({
//     el:"#app",
//     data: {
//         current: 1,
//         limit: 10,
//         pages: 1,
//         orderList: [],
//         total: 0,
//         totalPrice:'',
//         productList:{
//             productId:'',
//             productName:'',
//             productImg:'',
//             price:'',
//             productCount:'',
//             userId:'',
//         },
//         addressList:{
//             name:'',
//             phoneNumber:'',
//             province:'',
//             city:'',
//             detailAddress:'',
//         },
//         order:[],
//         cartList:[],
//         orderItemList:[],
//         pay:{
//             orderNo:'',
//             orderStatus:'',
//             totalPrice:''
//         },
//     },
//     mounted: function () {
//         var accessToken = getCookie("accessToken");
//         if (isEmpty(accessToken)) {
//             alert("请登录");
//             window.location.href="login.html"
//         } else {
//             this.accessToken = accessToken;
//         }
//         this.getOrderInfo();
//         // this.updateStock(46)
//     },
//     create:{},
//     methods:{
//         /**
//          * 提交订单
//          * @param id
//          * @constructor
//          */
//         getOrderInfo:function () {
//             var userId = getCookie("sessionId");
//             var t={
//                 orderNo:orderNo
//             };
//             var formData = JSON.stringify(t);
//             $.ajax({
//                 type: "post",
//                 url: "http://localhost:8080/order/getOrderByOrderNo",
//                 contentType: "application/json;charset=utf-8",
//                 dataType: "json",
//                 data: formData,
//                 success: function (result) {
//                     if (result.code == 0) {
//                         vm.order = result.data.result;
//                         vm.cartList = result.data.orderItemList;
//                         // alert(vm.order.orderNo)
//                         console.log(vm.order)
//                         console.log(vm.cartList)
//                     } else {
//                         alert(result.msg);
//                     }
//                 }
//             });
//         },
//
//         // /**
//         //  * 支付成功后修改库存
//         //  */
//         // updateStock: function (product) {
//         //     var t={
//         //
//         //     }
//         //
//         //     var formData = JSON.stringify(product);
//         //     $.ajax({
//         //         type: "post",
//         //         url: "http://localhost:8080/product/update/stock",
//         //         contentType: "application/json;charset=utf-8",
//         //         dataType: "json",
//         //         data: formData,
//         //         success: function (result) {
//         //             if (result.code == 0) {
//         //                 vm.total = result.data;
//         //                 // vm.cartList = result.data.orderItemList;
//         //                 // alert(vm.order.orderNo)
//         //                 console.log(vm.total)
//         //             } else {
//         //                 alert(result.msg);
//         //             }
//         //         }
//         //     });
//         // },
//     }
// })