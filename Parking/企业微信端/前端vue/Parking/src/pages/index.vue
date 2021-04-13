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
            <!-- <div class="main-body" ref="wrapper" :style="{ width: '100%',height: (wrapperHeight-187) + 'px' }"> -->
            <div class="main-body" ref="wrapper">
                <mt-loadmore :top-method="loadTop" :bottom-method="loadBottom" @bottom-status-change="handleBottomChange" :bottom-all-loaded="allLoaded" ref="loadmore" :autoFill="isAutoFill">
                  <div class="rongUl">
                    <p v-if="len1" style="text-align:center;margin-top:50px;">暂无数据</p>
                    <ul class="mui-table-view mui-grid-view plateList">
                      <li v-for="(item,index) in dataCompany" :key="index" class="mui-table-view-cemui-ll media mui-col-xs-6 plateLi flexBetween" @click="toCompanyDetails(item.recordInnerId,dataCompany[index])">
                          <!-- <img class="mui-media-object" v-lazy="item.img"> -->
                          <div>
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
                          <div class="jian"></div>
                      </li>
                    </ul>
                    <div class="noMoreData" v-if="more">没有更多数据了...</div>
                  </div>
                </mt-loadmore>
                <div class="orderInfo companyBg">
                  <div class="xingz">
                      <button @click="companyAppointmentBtn"><img src="../assets/image/selfBtn.png" />车位预约</button>
                  </div>
                  <div class="infoContent">
                    <div class="infoCount flexBetween">
                      <button class="lookCount" @click="lookCalendar"><img src="../assets/image/lookBtn.png" />车位预约</button>
                      <div class="todayCount">{{todayCount}}<span style="display:block;">当前预约数量</span></div>
                      <div class="onlyCount">{{onlyCount}}<span style="display:block;">今日剩余车位</span></div>
                    </div>
                    <div class="infoHelp flexBetween companyText">
                      <div class="question" @click="toHelpGsBtn"></div>
                      <div class="historyText" @click="companyHistoryBtn">历史记录</div>
                      <div class="userInfo" @click="companyInfoBtn"></div>
                    </div>
                  </div>
                </div>
            </div>
          </mt-tab-container-item>
          <mt-tab-container-item id="2">
            <!-- <div class="main-body" ref="wrapper" :style="{ width: '100%',height: (wrapperHeight-187) + 'px' }"> -->
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
                          <div class="jian"></div>
                      </li>
                    </ul>
                    <div class="noMoreData" v-if="more">没有更多数据了...</div>
                  </div>
                </mt-loadmore>
                <div class="orderInfo">
                  <div class="xingz">
                      <button @click="appointmentBtn"><img src="../assets/image/selfBtn.png" />车位预约</button>
                  </div>
                  <div class="infoContent">
                    <div class="infoCount flexBetween">
                      <button class="lookCount" @click="lookCalendar"><img src="../assets/image/lookBtn.png" />车位预约</button>
                      <div class="todayCount">{{todayCount}}<span style="display:block;">当前预约数量</span></div>
                      <div class="onlyCount">{{onlyCountMine}}<span style="display:block;">今日剩余车位</span></div>
                    </div>
                    <div class="infoHelp flexBetween">
                      <div class="question" @click="toHelpBtn"></div>
                      <div class="historyText" @click="historyBtn">历史记录</div>
                      <div class="userInfo" @click="mineBtn"></div>
                    </div>
                  </div>
                </div>
            </div>
          </mt-tab-container-item>
      </mt-tab-container>
    </div>

    <mt-popup
      class="mine"
      v-model="popupVisible"
      popup-transition="popup-fade"
      position="left">
      <div class="mineInfo">
        <div class="photoDiv">
          <div class="photo">
            <img :src="userImg" alt="">
          </div>
          <div class="photoName">{{userName}}</div>
        </div>
        <ul class="infoDiv">
          <li>
            <div class="icon"><img src="./../assets/image/icon01.png"/></div>
            <div class="desc">编号</div>
            <div class="flex-box ellipsis" style="text-align:right;">{{userCode}}</div>
          </li>
          <li>
            <div class="icon"><img src="./../assets/image/icon02.png"/></div>
            <div class="desc">部门</div>
            <div class="flex-box ellipsis" style="text-align:right;">{{userPub}}</div>
          </li>
          <li v-if="ustate == 3">
              <div class="icon"><img src="./../assets/image/icon03.png"/></div>
              <div class="desc">公司账户余额</div>
              <div class="flex-box ellipsis" style="text-align:right;">{{userMoney}}</div>
          </li>
        </ul>
        <div class="logoDiv"><img src="./../assets/logo.png"/></div>
      </div>
      <!-- <button class="unbindBtn">解&nbsp;&nbsp;绑</button> -->
    </mt-popup>
    <mt-popup
      class="parkLotPopup"
      v-model="parkLotVisible"
      popup-transition="popup-fade"
      position="center">
      <div class="parkLotPopupTips">日期下方显示的<span></span>内数字为剩余车位数量</div>
      <van-calendar
        v-model="show"
        :show-confirm="false"
        title=""
        :min-date="minDate"
        :max-date="maxDate"
        :formatter="formatter"
      />
      <!-- <ul class="parkLotTitle">
        <li>
          <span>日期</span>
          <span>剩余车位</span>
        </li>
      </ul>
      <ul class="parkLotUl">
        <li class="parkLotLi" v-for="(item,index) in parkLotList" :key="index">
          <span>{{ item.ParkDate }}</span>
          <span>{{ item.ParkResidue }}</span>
        </li>
      </ul> -->
    </mt-popup>
    
  </div>
</template>
<script>
  import { Toast } from 'mint-ui'
  import {formatDate} from '../components/date.js'
  export default {
    name: 'index',
    filters: {
        formatDate(time) {
            var date = new Date(time).toLocaleString();
            return formatDate(date,'yyyy/MM/dd hh:mm:ss');
        }
    },
    data: function(){
      return {
          parkLotVisible: false,
          carList: [
            // {
            //   ParkDate: '2020-05-23',
            //   ParkResidue: 20
            // },{
            //   ParkDate: '2020-05-24',
            //   ParkResidue: 19
            // },{
            //   ParkDate: '2020-05-25',
            //   ParkResidue: 13
            // },{
            //   ParkDate: '2020-05-26',
            //   ParkResidue: 6
            // },{
            //   ParkDate: '2020-05-27',
            //   ParkResidue: 8
            // },{
            //   ParkDate: '2020-05-28',
            //   ParkResidue: 13
            // },{
            //   ParkDate: '2020-05-29',
            //   ParkResidue: 6
            // },{
            //   ParkDate: '2020-05-30',
            //   ParkResidue: 8
            // }
          ],
          // parkLotList: [],
          minDate: new Date(),
          // maxDate: new Date(),
          show: true,
          len1: false,
          len2: false,
          more: false,
          dataCompany: [],
          datas: [],
          //可以进行上拉
          allLoaded: false,
          //是否自动触发上拉函数
          isAutoFill: false,
          wrapperHeight: 0,
          courrentPage: 0,
          page: 1,
          todayCount: 0,
          onlyCount: 0,
          onlyCountMine: 0,
          selected: '1',
          visType: 1,
          // 个人/公司信息
          popupVisible: false,
          ustate: 0,
          userImg: '',
          userName: '',
          userCode: '',
          userPub: '',
          userMoney: 0,
          dateArrDis: []
      }
    },
    mounted() {
      // 父控件要加上高度，否则会出现上拉不动的情况
      this.wrapperHeight =
        document.documentElement.clientHeight -
        this.$refs.wrapper.getBoundingClientRect().top;

      //监测回退
      history.pushState(null, null, document.URL) // pushState() 带有三个参数：一个状态对象，一个标题（现在被忽略了），以及一个可选的URL地址
      window.addEventListener('popstate', this.forbidback);
    },
    created(){
      this.getWechatInfo()// 获取微信信息
      this.getNumber()    // 获取访客数量及今日剩余车位数
      this.getVisList()   // 获取预约列表
      this.getMoney()     // 获取公司账户余额
    },
    computed: {
      // 最大日期为当前时间日期+预约时间段
      maxDate() {
        let curDate = new Date().getTime()
        // 增加天数为29 页面会显示30天 
        let one = 29 * 24 * 3600 * 1000
        let oneYear = curDate + one
        return new Date(oneYear)
      }
    },
    beforeDestroy(){
      //销毁
      window.removeEventListener('popstate',this.forbidback);
    },
    methods: {
      forbidback(){
        //回退按钮点击处理
        setTimeout(function() {
          //这个可以关闭安卓系统的手机
          document.addEventListener(
              "WeixinJSBridgeReady",
              function() {
                WeixinJSBridge.call("closeWindow");
              },
            false
          );
            //这个可以关闭ios系统的手机
          WeixinJSBridge.call("closeWindow");
        })
      },
      cutTime(time){
        if (time !== '' && time !== null) {
            time = time.substring(0,16);
            return time;
          }
      },
      // 点击剩余车位查看
      lookCalendar(){
        var that = this
        that.parkLotVisible = true
        var parkType;
        if( that.visType == 1 ){
          parkType = 1
        }else{
          parkType = 2
        }
        that.$axios.post("searchParkNumByDate",{
          ParkType: parkType  // 1是公司，2是个人
        })
        .then(response => {
          if( response.data.status == 200 ){
            // that.parkLotList =  response.data.result
            that.carList =  response.data.result

            for( var i = 0; i < that.carList.length; i++ ){
              if( that.carList[i].ParkResidue == 0 ){
                that.dateArrDis.push(that.carList[i].ParkDate);
              }
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
      formatter(day) {
        const year = day.date.getFullYear();
        const month = day.date.getMonth() + 1;
        const date = day.date.getDate();
        for( var i = 0; i < this.carList.length; i++ ){
          var parkDate0 = this.carList[0].ParkDate.split('-')
          var year0 = parkDate0[0]
          var month0 = parkDate0[1]
          var date0 = parkDate0[2]
          if ( year == year0 && month == month0 && date == date0 ) {
            day.text = '今天';
          }

          var parkDate = this.carList[i].ParkDate.split('-')
          var year1 = parkDate[0]
          var month1 = parkDate[1]
          var date1 = parkDate[2]
          if ( year == year1 && month == month1 && date == date1 ) {
            day.bottomInfo = this.carList[i].ParkResidue;
          }
        }
        return day;
      },
      // 获取微信信息
      getWechatInfo(){
        var that = this
        that.userImg = localStorage.getItem('userImg')
        that.userName = localStorage.getItem('userName')
        that.userCode = localStorage.getItem('userCode')
        that.userPub = localStorage.getItem('userPub')
        that.ustate = localStorage.getItem('ustate')
      },
      // 获取访客数量及今日剩余车位数
      getNumber(){
        var that = this
        that.$axios.post("getParkInfo",{
          OrderType: that.visType,
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
            that.todayCount =  response.data.result.visNum
            that.onlyCount =  response.data.result.parkCarNum
            that.onlyCountMine =  response.data.result.staffCarNum
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
      // 获取预约列表
      getVisList(){
        var that = this
        that.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: that.visType,
          VisitorStateNow: '1,4,5,7,8',
          requestType: 1
        })
        .then(response => {
            if( response.data.status == 200 ){
              // alert(that.visType)
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
      // 获取公司账户余额
      getMoney(){
        var that = this
        that.$axios.post("searchDepartMoney",{
          requestType: 1
        })
        .then(response => {
          if( response.data.status == 200 ){
            that.userMoney = response.data.result.moneyYE/100;
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
        that.getVisList()
        that.getNumber()
      },
      haveVisType2(){
        var that = this
        that.visType = 2
        that.page = 1
        that.getVisList()
        that.getNumber()
      },
      //   下拉刷新
      loadTop() {
        this.loadFrist();
      },
      // 上拉加载
      loadBottom() {
        this.loadMore();
      },
      // 下来刷新加载
      loadFrist() {
        var that = this
        that.$axios.post("getCompanyVistor",{
          page: 1,
          size: 10,
          OrderType: that.visType,
          VisitorStateNow: '1,4,5,7,8',
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
      // 加载更多
      loadMore() {
        // this.$axios
        //   .get("goodslist.json")
        //   .then(response => {
        //     // concat数组的追加
        //     this.datas = this.datas.concat(response.data.result);
        //     if (this.courrentPage > 2) {
        //       this.allLoaded = true; // 若数据已全部获取完毕
        //     }
        //     this.courrentPage++;
        //     this.$refs.loadmore.onBottomLoaded();
        //   })
        //   .catch(error => {
        //     console.log(error);
        //     alert("网络错误，不能访问");
        //   });
        var that = this
        that.page++
        that.$axios.post("getCompanyVistor",{
          page: that.page,
          size: 10,
          OrderType: that.visType,
          VisitorStateNow: '1,4,5,7,8',
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
      },
      companyAppointmentBtn(){
        var that = this
        // if( that.onlyCount > 0 ){
          that.$router.push({
            path: '/appointmentCompany',
            query: {
              Identify: '1'
            }
          })
        // }else{
        //   let instance = Toast({
        //     message: '抱歉！没有车位了',
        //     position: 'bottom',
        //     duration: 5000
        //   });
        //   setTimeout(() => {
        //     instance.close();
        //   }, 2000);
        // }
        // this.$router.push('/appointmentCompany');
      },
      appointmentBtn(){
        var that = this
        // if( that.onlyCountMine > 0 ){
          that.$router.push({
            path: '/appointment',
            query: {
              Identify: '2'
            }
          })
        // }else{
        //   let instance = Toast({
        //     message: '抱歉！没有车位了',
        //     position: 'bottom',
        //     duration: 5000
        //   });
        //   setTimeout(() => {
        //     instance.close();
        //   }, 2000);
        // }
        // this.$router.push('/appointment');
      },
      // 点击跳转帮助说明
      toHelpGsBtn(){
        this.$router.push({
          path: '/helpGs'
        })
      },
      // 点击跳转帮助说明
      toHelpBtn(){
        this.$router.push({
          path: '/helpGr'
        })
      },
      // 公司-点击跳转历史纪录
      companyHistoryBtn(){
        this.$router.push({
          path: '/record',
          query: {
            selected: '1'
          }
        })
      },
      // 公司-信息
      companyInfoBtn(){
        var that = this
        that.popupVisible = true
      },
      // 个人-点击跳转历史纪录
      historyBtn(){
        this.$router.push({
          path: '/record',
          query: {
            selected: '2'
          }
        })
      },
      // 个人-信息
      mineBtn(){
        var that = this
        that.popupVisible = true
      },
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
    color: #931d36;
  }
  .mint-navbar{
    position: fixed;
    z-index: 9;
    
    background-color: transparent;
  }
  .companyBg{
    /* background: #fff; */
  }
  .companyBg .xingz button{
    color: #931d36;
    background: #fff;
  }
  .companyBg .todayCount,.companyBg .onlyCount{
    color: #333;
  }.companyText{
    color: #fff;
  }
  .companyText .question{
    background: url("../assets/image/questionCompany.png") center 0 no-repeat;
    background-size: 100% 100%;
  }
  .logoDiv{position: absolute; left: 0; right: 0; bottom: 15px; text-align: center;}
  .logoDiv img{width: 60%;}
</style>
<style>
  /* 预约列表 */
  /* .mint-navbar .mint-tab-item{
    height: 50px;
    line-height: 50px;
    padding: 0;
  }
  .mint-navbar .mint-tab-item.is-selected {
    border-bottom: none;
    color: #931d36;
    margin-bottom: 0;
    box-sizing: border-box;
  }
  .flexBetween{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
  }
  .main-body { overflow: scroll; }
  .rongUl{ padding: 0px 15px; }
  .plateList{ padding: 10px 0px 20px;min-height: 300px; }
  .plateLi{
    width: 100%;
    height: 62.5px;
    padding: 7.5px 18px;
    margin-top: 10px;
    background:rgba(255,255,255,1);
    box-shadow:0px 0px 10px 0px rgba(0,2,11,0.2);
    border-radius: 10px;
    box-sizing: border-box;
  }
  .listDiv{
    vertical-align: middle;
  }
  .plateVisName{
    display: inline-block;
    width: 80px;
    color: #18191A;
    font-size: 18px;
    font-weight: 400;
    vertical-align: middle;
  }
  .plateNumber{
    display: inline-block;
    width: 100px;
    color: #18191A;
    font-size: 18px;
    font-weight: 400;
    vertical-align: middle;
  }
  .plateTips{
    font-size: 10px;
    font-weight: 400;
    padding: 2px 8px;
    vertical-align: middle;
    border-radius: 4px;
    background: rgba(244,145,88,.23);
    color: #C77247;
  }
  .plateTipsOver{
    background: rgba(65,154,235,.14);
    color: #2C73B3;
  }
  .plateTipsOverRed{
    color: #fff;
    background: #E31C1C;
  }
  .plateTipsOverHui{
    background-color: rgba(177,177,177,.14);
    color: #999999;
  }
  .plateTipsOverHui2{
    background-color: rgba(177,177,177,.14);
    color: #999999;
  }
  .plateTime{
    color: #606060;
    font-size: 12px;
    font-weight: 400;
    margin-top: 8px;
  }
  .jian{
    display: inline-block;
    width: 7px;
    height: 44.5px;
    color: #931d36;;
    background: url("../assets/image/jian.png") center center no-repeat;
    background-size: 7px 13px;
  }
  .orderInfo{
    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 2;
    width: 100%;
    height: 146px;
    box-sizing: border-box;
  }
  .xingz{
    position: absolute;
    top: 10px;
    z-index: 3;
    left: 20px;
    right: 20px;
    height: 70px;
    border-radius: 4px;
    box-shadow: 0 0 30px rgba(0,2,11,0.2);
    background-color: #fff;
    text-align: center;
  }
  .xingz button{
    position: relative;
    top: -15px;
    z-index: 4;
    color: #931d36;
    font-size: 14px;
    font-weight: 500;
    font-family: "SourceHanSansCN-Medium";
    width: 75px;
    min-height: 75px;
    background: #fff;
    border: none;
    border-radius: 50% 50% 0 0;
  }
  .infoContent{
    height: inherit;
  }
  .infoCount{
    position: relative;
  }
  .lookCount{
    width: 52px;
    height: 52px;
    overflow: hidden;
    position: fixed;
    bottom: 150px;
    right: 15px;
    z-index: 1000000;
    background: none;
    outline: none;
  }
  .lookCount img{
    width: 100%;
    height: 100%;
  }
  .todayCount,.onlyCount{
    width: 30%;
    text-align: center;
    padding: 20px 15px 0;
    color: #333333;
    font-size: 22px;
    font-weight: 700;
    z-index: 4;
  }
  .todayCount span,.onlyCount span{
    margin-top: 6px;
    font-size: 12px;
    font-weight: 400;
    color: #808080;
  }
  .infoHelp{
    margin-top: 24px;
    width: 100%;
    text-align: center;
    padding: 10px 15px;
    box-sizing: border-box;
    color: #fff;
    font-size: 12px;
    font-weight: 400;
    vertical-align: top;
    background-color: #931d36;
    line-height: 2;
  }
  .question{
    width: 16.5px;
    height: 16.5px;
    background: url("../assets/image/questionSelf.png") center 0 no-repeat;
    background-size: 100% 100%;
  }
  .historyText{
    position: relative;
    left: 9px;
    text-decoration: underline;
  }
  .userInfo{
    position: relative;
    width: 32px;
    height: 31.5px;
    background: url("../assets/image/userPhoto.png") center 0 no-repeat;
    background-size: 100% 100%;
  }
  .xingz button{
    color: #931d36;
    font-size: 12px;
    background: #fff;
  }
  .xingz button img{
    display: block;
    margin: 2px auto 0;
    width:60px;
  }
  .noMoreData{
    width: 100%;
    text-align: center;
  }
  .parkLotPopup.mint-popup{
    width: 86%;
    height: 470px;
    overflow: hidden;
    border-radius: 10px;
    padding: 0px 0px 20px;
    box-sizing: border-box;
  }
  .parkLotPopupTips{
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    color: #fff;
    font-size: 14px;
    background-color: #931d36;
  }
  .parkLotPopupTips span{
    display: inline-block;
    width: 20px;
    height: 20px;
    line-height: 20px;
    text-align: center;
    border: 1px solid #fff;
    border-radius: 50%;
    color: #fff;
    margin: 0 5px;
    vertical-align: middle;
  }
  .van-calendar{
    width: 94%;
    margin: 0 auto;
  }
  .van-calendar__popup.van-popup--bottom, .van-calendar__popup.van-popup--top {
    height: 92%!important;
  }
  .van-calendar__month:last-child{
    padding-bottom: 0px!important;
  }
  .parkLotPopup .van-calendar{
    border-radius: 10px;
    overflow-y: auto;
  }
  .van-calendar__header{
    box-shadow: unset!important;
  }
  .van-calendar__body{
    overflow: unset!important;
  }
  .van-calendar__weekdays{
    position: fixed;
    top: 50px;
    left: 2%;
    width: 96%;
    height: 50px;
    background: #fff;
    z-index: 1000;
  }
  .van-calendar__weekday{
    line-height: 50px!important;
  }
  .van-calendar__body{
    margin-top: 70px;
  }
  .parkLotPopup .van-calendar__header-title, .van-calendar__header-subtitle, .van-calendar__month-title{
    display: none;
  }
  .van-calendar__day, .van-calendar__selected-day{
    align-items: flex-start!important;
  }
  .van-calendar__selected-day {
    font-weight: 600;
    color: #931d36!important;
    background-color: transparent!important;
    outline:none!important;
  }
  .van-calendar__bottom-info {
    bottom: 2px!important;
    width: 20px;
    height: 20px;
    line-height: 20px!important;
    border-radius: 50%;
    margin: 0 auto;
    border: 1px solid #931d36;
    background: #fff;
    color: #931d36;
  }
  .van-calendar__selected-day .van-calendar__bottom-info {
    background: #931d36;
    color: #fff;
  }
  .van-calendar__day{
    height: 50px!important;
    margin-bottom: 10px;
  }
  .van-calendar__day:focus{
    background-color: unset;
    border: 0;
    outline: 0;
    outline: none;
  } */
  
  /* 查看近期停车位的表格样式 */
  /* .parkLotTitle{
    padding: 0px 30px;
  }
  .parkLotTitle li{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    color: #6C6C6C;
    line-height: 40px;
    border-top: 1px solid #f0f0f0;
  }
  .parkLotTitle li span:first-child{
    flex: 2;
    text-align: center;
  }
  .parkLotTitle li span:last-child{
    flex: 1;
    text-align: center;
  }
  .parkLotUl{
    max-height: 500px;
    overflow-y: scroll;
    padding: 0px 30px;
  }
  .parkLotLi{
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    color: #AAA;
    line-height: 40px;
    border-top: 1px solid #f0f0f0;
  }
  .parkLotLi span:first-child{
    flex: 2;
    text-align: center;
  }
  .parkLotLi span:last-child{
    flex: 1;
    text-align: center;
  }
  .parkLotLi:nth-child(1){
    color: #FA9819;
  }
  .parkLotLi:nth-child(1) span:first-child{
    background: url("../assets/image/carToday.png") 5px center no-repeat;
    background-size: 16px 10px;
  }
  .parkLotLi:nth-child(2){
    color: #15E7A6;
  }
  .parkLotLi:nth-child(2) span:first-child{
    background: url("../assets/image/carTorrow.png") 5px center no-repeat;
    background-size: 16px 10px;
  }
  .parkLotLi:nth-child(3){
    color:#931d36;;
  }
  .parkLotLi:nth-child(3) span:first-child{
    background: url("../assets/image/carThird.png") 5px center no-repeat;
    background-size: 16px 10px;
  } */
</style>
