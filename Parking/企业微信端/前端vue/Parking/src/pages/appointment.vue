<template>
  <div class="appointment">
    <div class="content">
      <div class="staffAppointment flexBetween">
        <div class="userLeft"><img :src="userImg" alt=""></div>
        <div class="userRight">
          <p>预约姓名：<span>{{visitorName}}</span></p>
          <p>预约部门：<span>{{visitorCompany}}</span></p>
        </div>
      </div>
      <!-- <mt-field label="车牌号码" placeholder="请选择" readonly @click.native="choiceCarNum" class="carNumber" v-model="carNum" @blur.native.capture="checkCarNum"><input @click="on($event)" type="text" placeholder="请输入来访车辆车牌号码" v-model="carNumber"></mt-field> -->
      <!-- <mt-field label="车牌号码" placeholder="请选择" readonly @click.native="choiceCarNumber" class="carNumber" v-model="carNumber"></mt-field> -->

      <mt-field label="车牌号码" readonly class="carNumber">
        <input class="choiceCarNumber" ref="carNumTxt" @focus="choiceCarNumber" autocomplete="on" readonly="readonly" unselectable="on" placeholder="请选择" v-model="carNumber">
        <div class="historyCar" @click="historyCarClick">
          <img src="../assets/image/historyCar.png" alt="">
        </div>
      </mt-field>
      
      <mt-field label="来访时间" placeholder="请选择来访时间" v-model="startDate" readonly @click.native="dateClickStart" class="date" :class="ifError==true?'error':''"></mt-field>
      <mt-field label="结束时间" placeholder="请选择结束时间" v-model="endDate" readonly @click.native="dateClickEnd" class="date"></mt-field>
      <div class="appointBtnGroup flexBetween">
        <mt-button class="appointCancelBtn" @click="appointmentCancelBtn">取消</mt-button>
        <mt-button class="appointSubmitBtn" :disabled="appointBtnDis" @click="appointmentBtn">提交</mt-button>
      </div>
    </div>

    
    <!-- 历史车牌弹窗 -->
    <!-- <mt-popup
      class="thingForPopup"
      style="width:100%;"
      v-model="historyCarVisible"
      position="bottom">
      <mt-picker :slots="slotsHistoryCar" showToolbar ref="pickerCar">
        <div class="carno-header">
          <mt-button @click="cancelHistoryCar" class="canael">取消</mt-button>
          <mt-button @click="confirmHistoryCar" class="confirm">确认</mt-button>
        </div>
      </mt-picker>
    </mt-popup> -->

    <mt-popup
      class="historyCarVisible"
      v-model="historyCarVisible">
      <ul class="historyCarList">
        <li v-for="(item,index) in dataHistoryCar" :key="index" @click="choiceHistoryCar(item)">{{item.text}}</li>
      </ul>
    </mt-popup>

    <!-- 选择时间弹窗 -->
    <!-- <mt-datetime-picker style="width:100%;" ref="pickerStart" type="datetime" :start-date='startValue' :end-date='endValue' v-model="startValue" @confirm="handleStart"></mt-datetime-picker>
    <mt-datetime-picker style="width:100%;" ref="pickerEnd" type="datetime" :start-date='startValue' v-model="startValue" @confirm="handleEnd"></mt-datetime-picker> -->
    <mt-datetime-picker 
        style="width:100%;" 
        ref="pickerStart" 
        year-format="{value} 年"
        month-format="{value} 月"
        date-format="{value} 日"
        hour-format="{value} 时"
        minute-format="{value} 分"
        :start-date='startDefDate'
        :end-date='endDefDate'
        type="datetime" v-model="startValue" @confirm="handleStart" @touchmove.native.stop.prevent>
    </mt-datetime-picker>
    <mt-datetime-picker 
        style="width:100%;" 
        ref="pickerEnd" 
        year-format="{value} 年"
        month-format="{value} 月"
        date-format="{value} 日"
        hour-format="{value} 时"
        minute-format="{value} 分"
        :start-date='startDefDate'
        :end-date='endDefDate'
        type="datetime" v-model="endValue" @confirm="handleEnd" @touchmove.native.stop.prevent>
    </mt-datetime-picker>

    <!-- 选择车牌弹窗 -->
    <mt-popup
      class="visibleCarNoPopup"
      style="width:100%;"
      v-model="popupVisibleCarNo"
      position="bottom">
      <div class="carno">
        <div class="carno-header">
          <span class="canael" @click="popupVisibleCarNo = false">取消</span>
          <span class="title">请选择车牌号</span>
          <span class="confirm" @click="confirmCarNo">确认</span>
        </div>
        <div class="carno-input">
          <div class="input-box">
            <li>{{first}}</li>
            <li>{{numArr[0]}}</li>
            <li>{{numArr[1]}}</li>
            <li>{{numArr[2]}}</li>
            <li>{{numArr[3]}}</li>
            <li>{{numArr[4]}}</li>
            <li>{{numArr[5]}}</li>
            <li>{{numArr[6]}}</li>
          </div>
        </div>

        <!-- 英文 数字 键盘 -->
        <div class="carno-keyboard">

          <div class="plate_chinese_box" v-if="show_chinese">
            <mt-button
              v-for="(item, index) in ChineseList"
              :key="item.id"
              @click="checkChinese(index)"
            >{{item.name}}</mt-button>
          </div>

          <div class="plate_chinese_box plate_number_box" v-if="show_allBoard">
            <mt-button
              v-for="(item, index) in English_Number"
              :key="item.id"
              @click="checkEnglish_num(index)"
            >{{item.name}}</mt-button>
          </div>

        </div>
      </div>
    </mt-popup>

    <!-- 预约弹窗 -->
    <div class="popup" v-on:click="hidePanel" v-if="popupShow">
      <mt-popup
        class="popupAppointVisible"
        id="popupAppointVisible"
        ref="appointPopup"
        style="width:80%"
        v-model="appointmentPopup"
        modal="true"
        closeOnClickModal="true"
        popup-transition="popup-fade">
        <div class="popupContent">
            <div class="popupIcon"><img :src="imgUrl" alt=""></div>
            <p class="popupTxt">{{popupTxt}}</p>
            <p class="carPrice" v-if="appointCarPrice==0">确认缴费￥{{carPrice}}元</p>
            <mt-button class="stationBtn" @click="sureBtn">确认</mt-button>
        </div>
        
      </mt-popup>
    </div>
  </div>
</template>
<script>
  import { Toast } from 'mint-ui'
  import {formatDate} from '../components/date.js'
  import img_url from '../assets/image/iconNo.png'
  import img_url1 from '../assets/image/iconYes.png'
  import img_url2 from '../assets/image/iconGan.png'
  import img_url3 from '../assets/image/iconWen.png'
  export default {
    filters: {
        formatDate(time) {
            var date = new Date(time).toLocaleString();
            return formatDate(date,'yyyy/MM/dd hh:mm:ss');
        }
    },
    data() {
      return {
        ifError: false,
        userImg: '',
        userId: '',
        visitorName: '',
        visitorCompany: '',
        carNumber: '',
        carPrice: '',
        appointCarPrice: 1,
        startDate: '',
        endDate: '',
        startValue: '',
        endValue: '',
        startDefDate: new Date(),
        endDefDate: '',
        msg: 'nikan',
        popupTxt: '查无此人，请确认后重新输入',
        imgUrl: img_url,
        historyCarVisible: false,
        appointmentPopup: false,
        popupShow: false,
        NameStatus: '',
        appointBtnDis: false,
        
        Identify: '1',

        popupVisibleCarNo: false,
        show_allBoard: false, // 是否显示英文数字键盘
        show_chinese: true, // 是否显示汉字键盘
        ChineseList: [
          {name: '京', id: 1},
          {name: '津', id: 2},
          {name: '冀', id: 3},
          {name: '晋', id: 4},
          {name: '蒙', id: 5},
          {name: '辽', id: 6},
          {name: '吉', id: 7},
          {name: '黑', id: 8},
          {name: '沪', id: 9},
          {name: '苏', id: 10},
          {name: '浙', id: 11},
          {name: '皖', id: 12},
          {name: '闽', id: 13},
          {name: '赣', id: 14},
          {name: '鲁', id: 15},
          {name: '豫', id: 16},
          {name: '鄂', id: 17},
          {name: '湘', id: 18},
          {name: '粤', id: 19},
          {name: '桂', id: 20},
          {name: '琼', id: 21},
          {name: '渝', id: 22},
          {name: '川', id: 23},
          {name: '贵', id: 24},
          {name: '云', id: 25},
          {name: '藏', id: 26},
          {name: '陕', id: 27},
          {name: '甘', id: 28},
          {name: '青', id: 29},
          {name: '宁', id: 30},
          {name: '新', id: 31},
          {name: '.', id: 99}
        ],
        English_Number: [
          {name: '1', id: 28},
          {name: '2', id: 29},
          {name: '3', id: 30},
          {name: '4', id: 31},
          {name: '5', id: 32},
          {name: '6', id: 33},
          {name: '7', id: 34},
          {name: '8', id: 35},
          {name: '9', id: 36},
          {name: '0', id: 37},
          {name: 'Q', id: 38},
          {name: 'W', id: 39},
          {name: 'E', id: 40},
          {name: 'R', id: 41},
          {name: 'T', id: 42},
          {name: 'Y', id: 43},
          {name: 'U', id: 44},
          {name: 'I', id: 45},
          {name: 'O', id: 46},
          {name: 'P', id: 47},
          {name: 'A', id: 48},
          {name: 'S', id: 49},
          {name: 'D', id: 50},
          {name: 'F', id: 51},
          {name: 'G', id: 52},
          {name: 'H', id: 53},
          {name: 'J', id: 54},
          {name: 'K', id: 55},
          {name: 'L', id: 56},
          {name: 'Z', id: 57},
          {name: 'X', id: 58},
          {name: 'C', id: 59},
          {name: 'V', id: 60},
          {name: 'B', id: 61},
          {name: 'N', id: 62},
          {name: 'M', id: 63},
          {name: '挂', id: 64},
          {name: '.', id: 99}
        ],
        first: '',
        carList: [],
        dateArrDis: [],
        numArr: [],

        dataHistoryCar: []
      } 
    },
    created(){
      if(this.$route.query.iswhere=="appointment"){
        this.$route.meta.title='车位预约'
        window.document.title = '车位预约'
      }
      this.Identify = this.$route.query.Identify  //获取身份 1是公司访客 2是员工个人
      // 初始化开始时间加一个小时10分钟
      // 结束时间必须在开始时间后15分钟
      // this.startValue = this.startDate = formatDate(new Date(new Date().getTime()+4200000),'yyyy/MM/dd hh:mm')
      this.startValue = formatDate(new Date(new Date().getTime()),'yyyy/MM/dd hh:mm')
      this.endValue = formatDate(new Date(new Date(this.startValue).getTime()+900000),'yyyy/MM/dd hh:mm')
      this.userImg = localStorage.getItem('userImg')
      this.visitorName = localStorage.getItem('userName')
      this.userId = localStorage.getItem('userCode')
      this.visitorCompany = localStorage.getItem('userPub')

      var date0 = formatDate(new Date(new Date().getTime()+2592000000),'yyyy/MM/dd hh:mm') //30天*24小时*60分钟*60秒*1000 = 2592000000
      this.endDefDate = new Date(date0)

      this.getHistoryCar();
    },
    methods: {
      // 选择车牌
      choiceCarNumber(){
        var that = this
        // that.$refs.carNumTxt.setAttribute('readonly', 'readonly');
        that.popupVisibleCarNo = true
        if (!this.first) {
          this.show_chinese = true
        } else {
          this.show_chinese = false
          this.show_allBoard = true
        }
      },
      // 选择车牌号前面的汉字
      checkChinese (index) {
        // 如果点击删除键，删除第一个格的内容
        if (this.ChineseList[index].id === 99) {
          this.first = ''
        } else {
          // 把选中的字赋值给第一个格，并且切换键盘
          this.first = this.ChineseList[index].name
          this.show_chinese = false
          this.show_allBoard = true
        }
      },
      // 选择车牌号后面的数字和字母
      checkEnglish_num (index) {
        // 如果点击删除键，删除 numArr 的最后一个值
        if (this.English_Number[index].id === 99) {
          this.numArr.pop()
          // 如果 numArr 里面被删的没有值了，切换键盘
          if (this.numArr.length === 0) {
            this.show_chinese = true
            this.show_allBoard = false
          }
        } else {
          // 把选中的值 push 到 numArr 内
          this.numArr.push(this.English_Number[index].name)
          // 如果 numArr 中的值超过 7 个（车牌号的最大位数），删除最后一个
          if (this.numArr.length > 7) {
            this.numArr.pop()
          }
        }
      },

      confirmCarNo () {
        // this.$refs.carNumTxt.removeAttribute('readonly');
        if (this.first && this.numArr.length === 7) {
          let numStr = this.numArr.join('')
          this.carNumber = this.first + numStr
          this.popupVisibleCarNo = false
        }else if (this.first && this.numArr.length === 6) {
          let numStr = this.numArr.join('')
          this.carNumber = this.first + numStr
          this.popupVisibleCarNo = false
        } else {
          this.$toast({message: '请输入正确车牌号', className: 'addClassToast', position: 'top'})
        }
      },
      // 调出开始时间
      dateClickStart() {
          this.$refs.pickerStart.open();
      },
      // 调出结束时间
      dateClickEnd() {
          this.$refs.pickerEnd.open();
      },
      // 确定开始时间
      handleStart(value) {
          this.startDate = formatDate(value,'yyyy/MM/dd hh:mm')
          // this.startValue = this.startDate
      },
      // 确定结束时间
      handleEnd(value) {
          this.endDate = formatDate(value,'yyyy/MM/dd hh:mm')
          // this.endValue = this.endDate
      },
      // 获取历史车牌
      getHistoryCar(){
        var that = this
        that.$axios.post("getHisCarInfo",{
          UserInnerId: localStorage.getItem('userInnerId'),
          OrderType: 2
        })
        .then(response => {
          if( response.data.status == 200 ){
            // that.slotsHistoryCar.values = response.data.result

            // pickerArray = response.data.result;
            // that.slotsHistoryCar[0].values = pickerArray;

            that.dataHistoryCar = response.data.result;
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
      // 历史车牌
      historyCarClick() {
        var that = this
        if( that.dataHistoryCar.length != 0 ){
          that.historyCarVisible = true
        }else{
          let instance = Toast({
            message: '暂无历史车牌号码记录',
            position: 'bottom',
            duration: 5000
          });
          setTimeout(() => {
            instance.close();
          }, 2000);
        }
      },
      choiceHistoryCar(item){
        var that = this
        that.carNumber = item.text
        that.historyCarVisible = false
      },
      // 预约提交
      appointmentBtn(){
          var that = this
          var regex = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
          var regex6 = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}$/;
          var name = that.carNumber;
          if ( name.length == 7 && regex.test(name) || name.length == 8 && regex6.test(name) ){
            if( that.startDate == '' ){
              let instance = Toast({
                message: "请选择开始时间",
                position: 'bottom',
                duration: 5000
              });
              setTimeout(() => {
                instance.close();
              }, 2000);
            }else{
              // that.nowTime = new Date().getTime() + 3600000//当前时间+1小时
              that.nowTime = new Date().getTime()-60000//当前时间
              that.startTime = new Date(that.startDate).getTime()//来访时间
              that.startTime1 = new Date(that.startDate).getTime() + 900000//来访时间后+15分钟
              that.endTime1 = new Date(that.endDate).getTime()//结束时间
              
              if( that.startTime <= that.nowTime ){//如果来访时间小于当前时间的一小时，错误提示
                let instance = Toast({
                  // message: "来访时间必须在当前时间一小时后",
                  message: "来访时间不能小于当前时间",
                  position: 'bottom',
                  duration: 5000
                });
                setTimeout(() => {
                  instance.close();
                }, 2000);
                that.ifError = true
              }else{
                if( that.endTime1 >= that.startTime1 ){//如果结束时间大于来访时间的15分钟，执行提交
                  that.appointBtnDis = true


                  var startTimeN = new Date(that.startDate.slice(0,10)).getTime()
                  var endTimeN = new Date(that.endDate.slice(0,10)).getTime()  // 取出来的new Date(that.endDate).getTime()是当天00:00的时间戳
                  // console.log(startTimeN)
                  // console.log(endTimeN)

                  that.$axios.post("searchParkNumByDate",{
                    ParkType: 2  // 1是公司，2是个人
                  })
                  .then(response => {
                    if( response.data.status == 200 ){
                      that.carList = response.data.result

                      // 循环出后台返回的一个月内每天的剩余车位数，取出剩余车位数为0的日期放在一个数组里
                      for( var i = 0; i < that.carList.length; i++ ){
                        if( that.carList[i].ParkResidue == 0 ){
                          that.dateArrDis.push(that.carList[i].ParkDate);
                        }
                      }

                      // 循环没有剩余车位的日期，跟开始时间和结束时间的时间戳做对比
                      var axiosFlag = null;
                      for( var k = 0; k < that.dateArrDis.length; k++ ){
                        var timeSec = new Date(that.dateArrDis[k]).getTime()-28800000;  // 取出来的new Date(that.dateArrDis[k]).getTime()是当天08:00的时间戳
                        // console.log(timeSec)
                        if( timeSec >= startTimeN && timeSec <= endTimeN ){
                          axiosFlag = 1;
                        }
                      }
                      // console.log(that.dateArrDis)

                      if( axiosFlag != 1 ){
                        that.$axios.post("insertStafferVistor",{
                          VisitorCarCode: that.carNumber,
                          VisitorBeginTime: that.startDate.replace(/\//g, '-')+':00',
                          VisitorEndTime: that.endDate.replace(/\//g, '-')+':00',
                          requestType: 1
                        })
                        .then(response => {
                          if( response.data.status == 200 ){
                            that.imgUrl = img_url1
                            that.popupTxt = response.data.message
                            // appointCarPrice = 0
                            // that.carPrice = response.data.result.carPrice

                            // 清空表单所填信息
                            that.carNumber = ''
                            // that.startDate = formatDate(new Date(new Date().getTime()+4200000),'yyyy/MM/dd hh:mm')
                            that.startDate = formatDate(new Date(new Date().getTime()),'yyyy/MM/dd hh:mm')
                            that.endDate = ''

                            that.appointmentPopup = true
                            that.popupShow = true
                            that.appointBtnDis = false
                          }else{
                            that.imgUrl = img_url
                            that.popupTxt = response.data.message
                            that.appointmentPopup = true
                            that.popupShow = true
                            that.appointBtnDis = false
                          }
                          that.appointBtnDis = false
                        })
                        .catch(error => {
                          console.log(error);
                        });
                      }else{
                        let instance = Toast({
                            message: '预约时间段内车位不足！请查看日历确认',
                            position: 'bottom',
                            duration: 5000
                          });
                          setTimeout(() => {
                            instance.close();
                          }, 2000);
                        that.appointBtnDis = false
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
                      that.appointBtnDis = false
                    }
                  })
                  .catch(error => {
                    console.log(error);
                  });
                }else{
                  let instance = Toast({
                    message: "结束时间必须在来访时间15分钟后",
                    position: 'bottom',
                    duration: 5000
                  });
                  setTimeout(() => {
                    instance.close();
                  }, 2000);
                }
              }
            }
          }else{
            let instance = Toast({
              message: "车牌号码有误",
              position: 'bottom',
              duration: 5000
            });
            setTimeout(() => {
              instance.close();
            }, 2000);
          }
      },
      // 提交预约--确定
      sureBtn(){
        var that = this
        that.appointmentPopup = false
        that.popupShow = false
        let ustate = localStorage.getItem('ustate')
        if( that.imgUrl == img_url1 ){
          if( ustate == 3 ){
            that.$router.push('/index')
          }else{
            that.$router.push('/staff')
          }
        }
      },
      hidePanel: function(event){
        var that = this
        var sp = document.getElementById("popupAppointVisible");
        if(sp){
          if(!sp.contains(event.target)){            //如果我们点击到了id为myPanel以外的区域
            // that.carNumber = '',
            // that.startDate = formatDate(new Date(new Date().getTime()+4200000),'yyyy/MM/dd hh:mm'),
            that.startDate = formatDate(new Date(new Date().getTime()),'yyyy/MM/dd hh:mm'),
            // that.endDate = '',
            that.appointmentPopup = false;
            that.popupShow = false;
          }
        }
      },
      // 预约取消
      appointmentCancelBtn(){
        var that = this
        let ustate = localStorage.getItem('ustate')
        if( ustate == 3 ){
          that.$router.push('/index')
        }else{
          that.$router.push('/staff')
        }
      }
    }
  }
</script>
<style scoped>
.appointment{
  padding:20px 20px 80px
}
.appointment .appointBtnGroup.flexBetween{position: fixed; left: 0; right: 0; bottom: 0; height: 80px; margin: 0; padding:10px 20px; box-sizing: border-box; background-color: #fff;}
.appointment .appointBtnGroup.flexBetween .appointSubmitBtn,
.appointment .appointBtnGroup.flexBetween .appointCancelBtn { height: 50px; line-height: 50px; flex:1}
.appointment .appointBtnGroup.flexBetween button + button { margin-left: 20px;}
.appointment .appointBtnGroup.flexBetween .appointSubmitBtn{color: #fff; background-color: #931d36;}
.appointment .appointBtnGroup.flexBetween .appointCancelBtn{color:#505050 ; background-color: #eaeaea;}
  .content {
    padding: 20px;
    color: #242323;
    background-color: #fff;
    border-radius: 4px;
  }
  .mint-cell{
    height: 66px;
    border-bottom: 1px solid #efeff3;
  }
  .staffAppointment{
    padding-bottom: 20px;
    margin-bottom: 20px;
    border-bottom: 1px solid #efeff3;
  }
  .userLeft{
    width: 30%;
  }
  .userLeft img{
    display: block;
    width: 80px;
    height: 80px;
    border-radius: 50%;
    overflow: hidden;
  }
  .userRight{
    color: #141313;
    font-size: 16px;
    font-weight: 600;
    width: 70%;
    margin-top: 5px;
  }
  .userRight p{
    margin-top: 10px;
  }
  .popup{
    position: absolute;
    top: 0;
    left: 0;
    z-index: 10;
    width: 100%;
    height: 100%;
  }
  .popupAppointVisible{
    position: fixed!important;
  }

  /* 车牌弹窗内样式--start */
  .carno{
    height: auto;
    background-color: #ffffff;
  }
  .carno-header{
    width: 100%;
    height: auto;
    padding: 2vw 0;
    font-size: 4.26vw;
    color: #666666;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .canael,.confirm{
    /* color: #000000; */
    color: #26a2ff;
  }

  .carno-input{
    height: 14vw;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .input-box{
    width: 80vw;
    height: 10vw;
    border-radius:.4rem;
    border:1px solid rgba(206,208,210,1);
    display: flex;
    justify-content: center;
  }
  .input-box li{
    flex: 1;
    border-right: 1px solid rgba(206,208,210,1);
    box-sizing: border-box;
    margin-left: -1px;
    font-size: 5.33vw;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #323233;
  }
  .input-box li:last-child{
    border: none;
  }

  .carno-keyboard{
    width: 100%;
    height: auto;
  }
  .plate_chinese_box{
    background-color: #fff;
    padding: 0 2vw;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
  }
  .plate_chinese_box button{
    width: 14%;
    height: 9vw;
    font-size: 4.6vw;
    color: #333333;
    margin: 1vw;
    background-color: #ffffff;
    box-shadow: 2px 2px 2px 2px #888888;
  }
  .plate_chinese_box button:last-child{
    background: #ffffff url(../assets/image/back.png) center center no-repeat;
    background-size: 60% 50%;
  }

  .plate_number_box{
    background-color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
  }
  .plate_number_box button{
    width: 9%;
    height: 9vw;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 1vw 0.4vw;
    box-shadow: 1px 1px 1px 1px #888888;
  }
  .plate_number_box button:nth-child(21){
    margin-left: 4.5%;
  }
  .plate_number_box button:nth-child(29){
    margin-right: 4.5%;
  }
  .plate_number_box button:nth-child(30){
    margin-left: 10%;
  }
  .plate_number_box button:nth-child(36){
    margin-right: 15%;
  }
  /* 车牌弹窗内样式--end */

</style>

<style>
  .picker-items{width:100%;}
  /* .error input::placeholder {
    color: red;
  } */
  .error input {
    color: red;
  }
</style>
