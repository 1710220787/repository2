new Vue({
    //初始化vue
    el:"#app",
    data:{
        specPageList:[],  /*分页列表数据数组集合*/
        page:1,
        pageSize:5,
        total:50,
        searchSpec:{  /*搜索实体*/
            specName:''
        },
        /*新增规格选项
        * 如果以这种形式的时候就不用初始化数据
        * */
        specEntity:{
            spec:{},
            specOption:[]
        },
        selectIds:[]  /*记录多选*/

    },
    methods:{
        pageHandler:function (page) {
            /*必不可少*/
            this.page = page;
            let _this = this;
            //发送网络请求
            axios.post('/spec/getAllSpec?page='+page+'&pageSize='+this.pageSize,this.searchSpec).then(function (response) {
                //分页总记录数
                _this.total = response.data.total;
                _this.specPageList = response.data.rows;
                console.log(response.data.rows);
            }).catch(function (reason) {
                console.log(reason);
            })
        },

        /*添加和更新数据*/
        save:function () {
            let url = '';
            if (this.specEntity.spec.id != null){
                //更新
                url = '/spec/update'
            }else {
                //添加
                url = '/spec/save'
            }

            let _this = this;
            //发送请求  把规格的实体传给服务器
            axios.post(url,this.specEntity).then(function (response) {
                if (response.data.success){
                    alert(response.data.msg);
                    //清空文本框内容
                    _this.specEntity.spec = {};
                    _this.specEntity.specOption = [];
                    _this.pageHandler(1);
                }else {
                    alert(response.data.msg);
                }
                console.log(response.data);
            }).catch(function (reason) {
                console.log(reason);
            })
        },

        /*删除记录选中的状态*/
        deleteSelect:function(event,id){
            //复选框选中
            if (event.target.checked){
                //向数组添加元素
                this.selectIds.push(id)
            }else {
                //取消选中，移除
                /*找到选中哪个位置*/
                let ind = this.selectIds.indexOf(id);
                /*根据位置取消选中*/
                this.selectIds.splice(ind,1);
            }
            console.log(this.selectIds)
        },

        /*删除*/
        deleteSpec:function(){
            let _this = this;
            axios.post('/spec/deleteSpec',Qs.stringify({idx: _this.selectIds},{indices: false})).then(function (response) {
                if (response.data.success){
                    alert(response.data.msg);
                    _this.pageHandler(1);
                }else {
                    alert(response.data.msg);
                }
                console.log(response.data);
            }).catch(function (reason) {
                console.log(reason);
            })
        },



        /*根据规格id查询对应的规格和规格选项*/
        getSpecById:function(id){
            let _this = this;
            //发送请求  把规格的实体传给服务器
            axios.post('/spec/getSpecById?id='+id,).then(function (response) {
                //把值设置到实体上面去
                _this.specEntity = response.data;
                console.log(response.data);
            }).catch(function (reason) {
                console.log(reason);
            })
        },



        /*新增规格*/
        addOption:function(){
            this.specEntity.specOption.push({})
        },
        /*删除规格*/
        deleteOption:function (index) {
            this.specEntity.specOption.splice(index,1);
        },

    },
    created:function () {
        this.pageHandler(1)
    }
})