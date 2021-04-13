<template>
  <div>
    <!--头部-->
    <!-- <mt-header fixed title="首页"></mt-header> -->
    <!---内容区域-->
    <div class="content">
      <mt-navbar v-model="selected" class="fixedNav">
        <mt-tab-item id="1"><div class="tabBorder" @click="haveVisType1">访客预约</div></mt-tab-item>
        <mt-tab-item id="2"><div class="tabBorder" @click="haveVisType2">个人预约</div></mt-tab-item>
      </mt-navbar>

      <!-- tab-container -->
      <mt-tab-container v-model="selected" class="marTopNav">
          <mt-tab-container-item id="1">
            <!-- <div class="main-body" ref="wrapper" :style="{ width: '100%',height: (wrapperHeight-20) + 'px' }"> -->
            <div class="main-body" ref="wrapper">
                <mt-loadmore :top-method="loadTop" :bottom-method="loadBottom" @bottom-status-change="handleBottomChange" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill="isAutoFill">
                  <div class="rongUl">
                    <p v-if="len1" style="text-align:center;margin-top:50px;">暂无数据</p>
                    <ul class="mui-table-view mui-grid-view plateList">
                      <li v-for="(item,index) in dataCompany" :key="index" class="mui-table-view-cell mui-media mui-col-xs-6 plateLi flexBetween" @click="toCompanyDetails(item.recordInnerId,dataCompany[index])">
                          <!-- <img class="mui-media-object" v-lazy="item.img"> -->
                          <div>
                            <!-- <div>
                              <span class="plateNumber" v-text="item.visitorCarCode"></span>
                              <span v-if="item.tips=='逾期失效'" class="recordTips" v-text="item.tips"></span>
                              <span v-else class="recordTips recordTipsOver" v-text="item.tips"></span>
                            </div> -->
                            <div class="listDiv">
                              <span class="plateVisName ellipsis" v-text="item.visitorName"></span>
                              <span class="plateNumber" v-text="item.visitorCarCode"></span>
                              <span v-if="item.visitorState==1" class="plateTips">待入场</span>
                              <span v-else-if="item.visitorState==3" class="plateTips plateTipsOverHui">已取消</span>
                              <span v-else-if="item.visitorState==4" class="plateTips plateTipsOver">已入场</span>
                              <span v-else-if="item.visitorState==5" class="plateTips plateTipsOver">已支付</span>
                              <span v-else-if="item.visitorState==6" class="plateTips plateTipsOverHui2">已完成</span>
                              <span v-else-if="item.visitorState==7" class="plateTips plateTipsOverHui">已出场</span>
                              <span v-else class="plateTips plateTipsOverRed">已逾期</span>
                            </div>
                            <div class="plateTime">
                                <span class="plateStartTime" v-text="cutTime(item.visitorBeginTime)"></span> —
                                <span class="plateEndTime" v-text="cutTime(item.visitorEndTime)"></span>
                            </div>
                          </div>
                          <div>
                            <div class="money">￥<span v-if="item.money==null" v-text="0"></span><span v-else v-text="item.money/100"></span></div>
                            <div class="jian"></div>
                          </div>
                      </li>
                    </ul>
                    <div class="noMoreData" v-if="more">没有更多数据了...</div>
                  </div>
                </mt-loadmore>
                
            </div>
          </mt-tab-container-item>
          <mt-tab-container-item id="2">
            <div class="main-body" ref="wrapper">
                <mt-loadmore :top-method="loadTop" :bottom-method="loadBottom" @bottom-status-change="handleBottomChange" :bottom-all-loaded="allLoaded" ref="loadmore1" :autoFill="isAutoFill">
                  <div class="rongUl">
                    <p v-if="len2" style="text-align:center;margin-top:50px;">暂无数据</p>
                    <ul class="mui-table-view mui-grid-view plateList">
                      <li v-for="(item,index) in datas" :key="index" class="mui-table-view-cell mui-media mui-col-xs-6 plateLi flexBetween" @click="toDetails(item.recordInnerId,datas[index])">
                          <!-- <img class="mui-media-object" v-lazy="item.img"> -->
                          <div>
                            <div class="listDiv">
                              <span class="plateNumber" v-text="item.visitorCarCode"></span>
                              <span v-if="item.visitorState==1" class="plateTips">待入场</span>
                              <span v-else-if="item.visitorState==3" class="plateTips plateTipsOverHui">已取消</span>
                              <span v-else-if="item.visitorState==4" class="plateTips plateTipsOver">已入场</span>
                              <span v-else-if="item.visitorState==5" class="plateTips plateTipsOver">已支付</span>
                              <span v-else-if="item.visitorState==6" class="plateTips plateTipsOverHui2">已完成</span>
                              <span v-else-if="item.visitorState==7" class="plateTips plateTipsOverHui">已出场</span>
                              <span v-else class="plateTips plateTipsOverRed">已逾期</span>
                            </div>
                            <div class="plateTime">
                                <span class="plateStartTime" v-text="cutTime(item.visitorBeginTime)"></span> —
                                <span class="plateEndTime" v-text="cutTime(item.visitorEndTime)"></span>
                            </div>
                          </div>
                          <div>
                            <div class="money">￥<span v-if="item.money==null" v-text="0"></span><span v-else v-text="item.money/100"></span></div>
                            <div class="jian"></div>
                          </div>
                      </li>
                    </ul>
                    <div class="noMoreData" v-if="more">没有更多数据了...</div>
                  </div>
                </mt-loadmore>
            </div>
          </mt-tab-container-item>
      </mt-tab-container>
    </div>

  </div>
</template>
<script>
  import { Toast } from 'mint-ui'
  export default {
    name: 'record',
    data: function(){
      return {
        len1: false,
        len2: false,
        more: false,
        dataCompany: [],
        visitorName: '',
        visitorCarCode: '',
        datas: [
        //   {
        //   visitorCarCode: '京Q87W78',
        //   visitorState: 2,
        //   visitorBeginTime: '07/12 17:00:22',
        //   visitorEndTime: '07/12 19:00:33',
        //   money: '4.00'
        // }
        // ,{
        //   index: 1,
        //   title: '京A88888',
        //   tips: '已取消',
        //   startTime: '07/29 09:00',
        //   endTime: '07/29 18:00',
        //   money: '104.00'
        // },{
        //   index: 2,
        //   title: '京Q87W78',
        //   tips: '逾期失效',
        //   startTime: '07/12 17:00',
        //   endTime: '07/12 19:00',
        //   money: '4.00'
        // },{
        //   index: 3,
        //   title: '京Q87W78',
        //   tips: '已取消',
        //   startTime: '07/12 17:00',
        //   endTime: '07/12 19:00',
        //   money: '4.00'
        // },{
        //   index: 4,
        //   title: '京Q87W78',
        //   tips: '已取消',
        //   startTime: '07/12 17:00',
        //   endTime: '07/12 19:00',
        //   money: '4.00'
        // },{
        //   index: 5,
        //   title: '京Q87W78',
        //   tips: '已取消',
        //   startTime: '07/12 17:00',
        //   endTime: '07/12 19:00',
        //   money: '0.00'
        // },{
        //   index: 6,
        //   title: '京Q87W78',
        //   tips: '已取消',
        //   startTime: '07/12 17:00',
        //   endTime: '07/12 19:00',
        //   money: '4.00'
        // },{
        //   index: 7,
        //   title: '京A88888',
        //   tips: '已取消',
        //   startTime: '07/29 09:00',
        //   endTime: '07/29 18:00',
        //   money: '4.00'
        // },{
        //   index: 8,
        //   title: '京A88888',
        //   tips: '已取消',
        //   startTime: '07/29 09:00',
        //   endTime: '07/29 18:00',
        //   money: '4.00'
        // }
        ],
        //可以进行上拉
        allLoaded: false,
        //是否自动触发上拉函数
        isAutoFill: false,
        wrapperHeight: 0,
        courrentPage: 0,
        page: 1,
        selected: '1',
        visType: 1
      }
    },
    mounted() {
      // 父控件要加上高度，否则会出现上拉不动的情况
      this.wrapperHeight =
        document.documentElement.clientHeight -
        this.$refs.wrapper.getBoundingClientRect().top;
    },
    created(){
      if(this.$route.query.iswhere=="record"){
        this.$route.meta.title='历史记录'
        window.document.title = '历史记录'
      }
      this.getParams()
      this.getRecord()
    },
    methods: {
      cutTime(time){
        if (time !== '' && time !== null) {
            time = time.substring(0,16);
            return time;
          }
      },
      // 取到路由带过来的参数
      getParams() {
        var that = this;
        that.selected = that.$route.query.selected
        if( that.selected == '1' ){
          that.visType = 1
        }else{
          that.visType = 2
        }
      },
      getRecord(){
        var that = this
        that.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: that.visType,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
            if( that.visType == 1 ){
                that.dataCompany = response.data.result;
                var length1 = that.dataCompany.length
                // alert(length1)
                if( length1 < 1){
                  that.len1 = true
                }else{
                  that.len1 = false
                }
            }else{
              that.datas = response.data.result;
              var length2 = that.datas.length
              // alert(length2)
              if( length2 < 1){
                that.len2 = true
              }else{
                that.len2 = false
              }
              // that.total = response.data.total;
              // alert(Math.ceil(that.total/10))
            }
          }else{
            let instance = Toast({
              message: response.data.message,
              position: 'bottom',
              duration: 5000
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 进入公司预约详情
      toCompanyDetails(id,list){
        console.log(list)
        localStorage.setItem('dataVistorInfo',JSON.stringify(list))
        this.$router.push({
          path: '/details',
          query: {
            visType: 1,
            VisRecordInnerId: id
          }
        })
      },
      // 进入个人预约详情
      toDetails(id,list){
        console.log(list)
        localStorage.setItem('dataVistorInfo1',JSON.stringify(list))
        this.$router.push({
          path: '/details',
          query: {
            visType: 2,
            VisRecordInnerId: id
          }
        })
      },
      haveVisType1(){
        var that = this
        that.visType = 1
        that.page = 1
        that.getRecord()
      },
      haveVisType2(){
        var that = this
        that.visType = 2
        that.page = 1
        that.getRecord()
      },
      // 下拉刷新
      loadTop() {
        this.loadFrist();
      },
      // 上拉加载
      loadBottom() {
        this.loadMore();
      },
      // 下拉刷新
      loadFrist() {
        var that = this
        that.$axios.post("getCompanyVistor",{
          page: 1,
          size: 10,
          OrderType: that.visType,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
          if( that.visType == 1 ){
            if( response.data.status == 200 ){
              that.courrentPage = 0;
              that.allLoaded = false; // 可以进行上拉
              that.dataCompany = response.data.result;
              that.$refs.loadmore.onTopLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }else{
            if( response.data.status == 200 ){
              that.courrentPage = 0;
              that.allLoaded = false; // 可以进行上拉
              that.datas = response.data.result;
              that.$refs.loadmore1.onTopLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
      },
      // 上拉加载
      loadMore() {
        var that = this
        that.page++
        that.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: that.visType,
          VisitorStateHis: '1,2,3,4,5,6,7,8',
          requestType: 1
        })
        .then(response => {
          if( that.visType == 1 ){
            if( response.data.status == 200 ){
              // concat数组的追加
              that.dataCompany = that.dataCompany.concat(response.data.result);
              if (that.courrentPage > 2) {
                that.allLoaded = true; // 若数据已全部获取完毕
              }
              that.courrentPage++;
              that.$refs.loadmore.onBottomLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }else{
            if( response.data.status == 200 ){
              // concat数组的追加
              that.datas = that.datas.concat(response.data.result);
              if (that.courrentPage > 2) {
                that.allLoaded = true; // 若数据已全部获取完毕
              }
              that.courrentPage++;
              that.$refs.loadmore1.onBottomLoaded();
            }else{
              let instance = Toast({
                message: response.data.message,
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
      },
      handleBottomChange(){
        var that = this
        if( that.allLoaded == true ){
          that.more = true
          setTimeout(function(){
            that.more = false
          },2000)
        }
      }
    }
  }
</script>
<style scoped>
  .content {
    overflow: hidden;
  }
  .tabBorder{
    height: 50px;
    line-height: 50px;
    margin: 0 auto;
    font-size: 16px;
  }
  .money{
    display: inline-block;
    color: #D71313;
    margin-right: 8px;
    vertical-align: top;
    padding-top: 12px;
  }
</style>
