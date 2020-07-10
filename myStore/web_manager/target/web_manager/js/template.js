Vue.component('v-select', VueSelect.VueSelect);
new Vue({
    el:"#app",
    data:{
        tempList:[],
        temp:{},
        searchTemp:{
            name:''
        },
        page:1,  /*当前页*/
        pageSize:5,  /*一页有多少条数据*/
        total:50,   /*总页数*/
        maxPage:9,   /*最大的页码数*/
        selectIds:[], //记录选择了哪些记录的id

        addName:'',

        /*品牌下拉选项*/
        brandsOptions: [],   /*供用户查看的下拉栏*/
        placeholder: '选择品牌',
        selectBrands: [],
        sel_brand_obj: [],

        /*规格下拉选项*/
        placeholderSpec:'选择规格',
        specOptions: [],  /*规格数据下拉*/
        selectSpecs: [],
        sel_spec_obj: [],   /*选择的规格*/
    },
    methods:{
        pageHandler:function (page) {
            /*必不可少*/
            this.page = page;
            let _this = this;
            //发送网络请求
            axios.post('/template/getAllTemplate?page='+page+'&pageSize='+this.pageSize,this.searchTemp).then(function (response) {
                //分页总记录数
                _this.total = response.data.total;
                _this.tempList = response.data.rows;
                console.log(response.data.rows);
            }).catch(function (reason) {
                console.log(reason);
            })
        },

        /*保存*/
        save:function(){

            /*判断是保存还是更新操作*/
            let url = '';
            if (this.tempList.id != null){
                url = '/template/update'
            }else {
                url = '/template/save'
            }

            let _this = this;
            let entity = {
                id:this.tempList.id,
                name:this.addName,
                brandIds:_this.sel_brand_obj,
                specIds:_this.sel_spec_obj
            };
            axios.post(url,entity)
                .then(function (response) {
                    console.log(response);
                    if (response.data.success){
                        alert(response.data.msg)
                    }else {
                        alert(response.data.msg)
                    }
                    _this.pageHandler(1);
                }).catch(function (reason) {
                console.log(reason);
            });
        },


        /*查找一条数据*/
        updateUI:function(id){
            let _this = this;
            axios.post("/template/updateUI?id="+id).then(function (response) {
                _this.tempList.id = id;
                _this.addName = response.data.name;
                _this.sel_brand_obj = JSON.parse(response.data.brandIds);
                _this.sel_spec_obj = JSON.parse(response.data.specIds);
                console.log(JSON.parse(response.data.brandIds));
                console.log(JSON.parse(response.data.specIds));
            }).catch(function (reason) {
                console.log(reason);
            });
        },

        jsonObj:function (jsonStr,key) {
            let json = JSON.parse(jsonStr);
            let value = '';
            for (let i = 0; i < json.length; i++){
                if (i > 0){
                    value += ',';
                }
                value += json[i][key]
            }
            return value;
        },
        selected_brand: function(values){
            this.selectBrands =values.map(function(obj){
                return obj.id
                console.log(obj.id);
            });
            console.log(this.sel_brand_obj);
        },

        /*删除*/
        deleteTemplate:function(){
            let _this = this;
            /*indices参数是不让传角标  要加空格*/
            axios.post('/template/deleteTemplate',Qs.stringify({idx: _this.selectIds},{indices: false})).then(function (response) {
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
        },

        /*记录删除多选*/
        deleteCke:function(event,id){
            //如果复选框选中
            if (event.target.checked){
                //记录选中的
                this.selectIds.push(id);
            }else {
                let ind = this.selectIds.indexOf(id);
                this.selectIds.splice(ind,1);
            }
            console.log(this.selectIds);
        },


        selected_spec: function(values){
            this.selectSpecs =values.map(function(obj){
                return obj.id
            });
        },

        selLoadData:function () {
            let _this = this;
            axios.get("/brand/selectOptionList")
                .then(function (response) {
                    console.log(response.data);
                    _this.brandsOptions = response.data;
                }).catch(function (reason) {
                console.log(reason);
            }),
                axios.get("/spec/selectOptionList")
                    .then(function (response) {
                        _this.specOptions = response.data;
                    }).catch(function (reason) {
                    console.log(reason);
                });
        },
    },
    created:function () {
        this.pageHandler(1);
        this.selLoadData();
    }
});