/*初始化Vue*/
new Vue({
    el:"#app",
    data:{
        brandList:[],
        brand:{  /*添加品牌*/
            name:'',
            firstChar:''
        },
        page:1,  /*当前页*/
        pageSize:5,  /*一页有多少条数据*/
        total:50,   /*总页数*/
        maxPage:9,   /*最大的页码数*/
        selectIds:[],  /*记录删除选中的*/
        searchBrand:{   /*品牌搜索实体*/
            name:'',
            firstChar:''
        }
    },
    methods:{

        //发送网络请求
        getAllBrands:function () {
            //因为axios也有一个this，所有不能直接用this
            let _this = this;
            axios.get("/brand/getAllBrand").then(function (response) {
                //把数据绑定到集合里面
                _this.brandList = response.data
            }).catch(function (reason) {
                console.log(reason);
            })
        },

        /*自定义分页方法*/
        pageHandler:function (page) {
            this.page = page;
            let _this = this;
            /*使用post发送请求的好处是可以单独传一个对象*/
            axios.post("/brand/findPage?page="+page+"&pageSize="+_this.pageSize,_this.searchBrand).then(function (response) {
                //把数据绑定到集合里面
                _this.brandList = response.data.rows
                _this.total = response.data.total
                console.log(response.data.rows);
            }).catch(function (reason) {
                console.log(reason);
            })
        },


        /*添加更新品牌*/
        brandSave:function () {
            //判断当前操作是添加还是更新
            let url = '';
            if (this.brand.id == null){
                //添加
                url = '/brand/addBrand'
            }else {
                //更新
                url = '/brand/update'
                console.log("id为---------------------" + this.brand.id)
            }

            let _this = this;
            axios.post(url,this.brand).then(function (response) {
                if (response.data.success){
                    alert(response.data.msg);
                    _this.brand.name = '';
                    _this.brand.firstChar = '';
                    _this.pageHandler(1);
                }else {
                    alert(response.data.msg)
                }
            }).catch(function (reason) {
                console.log(reason.data)
            })
        },

        /*根据id查找商品，并且展示到文本框*/
        updateById:function (id) {
            let _this = this;
            axios.get('/brand/updateById',{params:{id:id}}).then(function (response) {
                //做赋值
                _this.brand = response.data;
            }).catch(function (reason) {
                console.log(reason.data);
            })
        },

        /*删除记录选中的状态*/
        deleteSelection:function (event,id) {
            //复选框选中
            if (event.target.checked){
                //向数组中添加元素
                this.selectIds.push(id)
            }else {
                //取消选中  从数组当中移除
                let idx = this.selectIds.indexOf(id);   /*找到选中哪个的位置*/
                this.selectIds.splice(idx,1)   /*根据指定位置删除1个元素*/
            }
            console.log(this.selectIds)
        },

        /*删除记录*/
        deleteBrand:function () {
            let _this = this;
            /*indices参数是不让传角标  要加空格*/
            axios.post('/brand/delete',Qs.stringify({idx: _this.selectIds},{indices: false})).then(function (response) {
                if (response.data.success){
                    alert(response.data.msg);
                    _this.selectIds = [];
                    _this.pageHandler(1);
                }else {
                    alert(response.data.msg)
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        }

    },
    /*初始化vue后调用*/
    created:function () {
        // this.getAllBrands();
        this.pageHandler(1)
    }
})