<template>
  <div class="appointmentDetails">
    <div class="content">
      <div class="detailStatus">
          <p class="statusTips">预约状态</p>
          <p class="statusText" :class="{ 'statusText1': statusText === '未入场','statusText2': statusText === '逾期失效','statusText3': statusText === '未出场' }">{{statusText}}</p>
          <p class="statusLine"></p>
      </div>
      <mt-cell title="单位" value="十五所"></mt-cell>
      <mt-cell title="姓名" value="王所"></mt-cell>
      <mt-cell title="车牌号码" value="京Q 87W78"></mt-cell>
      <mt-cell title="来访时间" value="07-12 17:00"></mt-cell>
      <mt-cell title="结束时间" value="07-12 19:00"></mt-cell>
      <mt-cell title="车辆入场时间" value="未入场"></mt-cell>
      <mt-cell title="车辆出场时间" value="未出场"></mt-cell>
      <mt-cell title="预约提交时间" value="07-12 09:00"></mt-cell>
      <div class="btnGroup flexBetween">
          <mt-button v-show="statusText == '未入场'" class="cancelBtn cancelAppointBtn" @click="cancelClick">取消预约</mt-button>
          <mt-button v-show="statusText == '已入场'" class="cancelBtn cancelAppointBtn" @click="beforeCloseClick">提前结束</mt-button>
      </div>
    </div>

    <!-- 选择时间弹窗 -->
    <mt-datetime-picker style="width:100%;" ref="pickerStart" type="datetime" :startDate='startValue' :endDate='endValue' v-model="startValue" @confirm="handleStart"></mt-datetime-picker>
    <mt-datetime-picker style="width:100%;" ref="pickerEnd" type="datetime" :startDate='startValue' v-model="startValue" @confirm="handleEnd"></mt-datetime-picker>

    <!-- 预约弹窗 -->
    <mt-popup
      class="appointmentPopup"
      style="width:80%"
      v-model="appointmentPopup"
      modal="true"
      closeOnClickModal="true"
      popup-transition="popup-fade">
      <div class="popupContent">
          <div class="popupIcon"><img :src="imgUrl" alt=""></div>
          <p class="popupTxt">{{popupTxt}}</p>
          <mt-button class="stationBtn" @click="searchForm('formInline')">确认</mt-button>
      </div>
    </mt-popup>
  </div>
</template>
<style scoped>
  .content {
    /* margin-top: 54px; */
    padding: 0 30px;
    color: #242323;
  }  
</style>
<script>
  import { Toast } from 'mint-ui'
  import {formatDate} from '../components/date.js'
  import img_url from '../assets/image/iconGan.png'
  export default {
    filters: {
        formatDate(time) {
            var date = new Date(time).toLocaleString();
            return formatDate(date,'yyyy-MM-dd hh:mm:ss');
        }
    },
    data() {
      return {
        username: '',
        company: '',
        password: '',
        startDate: formatDate(new Date(),'yyyy-MM-dd hh:mm'),
        endDate: '',
        startValue: new Date(),
        // endValue: '',
        msg: 'nikan',
        statusText: '未出场',
        popupTxt: '查无此人，请确认后重新输入',
        imgUrl: img_url,
        appointmentPopup: false
      } 
    },
    created(){
      if(this.$route.query.iswhere=="detailsCompany"){
        this.$route.meta.title='预约详情'
        window.document.title = '预约详情'
      }
    },
    methods: {
      dateClickStart() {
          this.$refs.pickerStart.open();
      },
      dateClickEnd() {
          this.$refs.pickerEnd.open();
      },
      handleStart(value) {
          this.startDate = formatDate(value,'yyyy-MM-dd hh:mm')
          this.startValue = this.startDate
      },
      handleEnd(value) {
          this.endDate = formatDate(value,'yyyy-MM-dd hh:mm')
          this.endValue = this.endDate
      },
      // 取消预约
      cancelClick(){
        var that = this
        that.appointmentPopup = true
      },
      // 提前结束
      beforeCloseClick(){
        var that = this
        that.appointmentPopup = true
      },
    }
  }
</script>
