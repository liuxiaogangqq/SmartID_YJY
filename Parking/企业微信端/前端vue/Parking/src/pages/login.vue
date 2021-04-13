<template>
  <div>
    <div class="content">
      <div class="loading">认证中<mt-spinner :size="24" color="#4175B8" type="triple-bounce"></mt-spinner></div>
    </div>
  </div>
</template>

<script>
  import { Toast } from 'mint-ui'
export default {
  data() {
    return {
      code: 0,
    }
  },
  created(){
    this.code = this.getUrlKey('code')
    this.signIn()
  },
  methods: {
    getUrlKey(name){//获取url 参数
      return decodeURIComponent((new RegExp('[?|&]'+name+'='+'([^&;]+?)(&|#|;|$)').exec(location.href)||[,""])[1].replace(/\+/g,'%20'))||null;
    },
    signIn(){
      var that = this
      that.$axios.post("signIn",{
        code: that.code,
        requestType: 1
      })
      .then(response => {
        if( response.data.status == '200' ){
          // var arrList = JSON.stringify({userName:response.data.result.operator.userName,userCode:response.data.result.operator.userId,userPub:response.data.result.operator.departmentName,userMoney:response.data.result.operator.sex})
          // localStorage.setItem('arrList',arrList)
          localStorage.setItem('userImg',response.data.result.operator.imageUrl)
          localStorage.setItem('userName',response.data.result.operator.userName)
          localStorage.setItem('userInnerId',response.data.result.operator.userInnerId)
          localStorage.setItem('userCode',response.data.result.operator.userId)
          localStorage.setItem('userPub',response.data.result.operator.departmentName)
          localStorage.setItem('userToken',response.data.result.operator.token)
          localStorage.setItem('ustate',response.data.result.operator.ustate)
          let ustate = localStorage.getItem('ustate')
          if( ustate == 3 ){
            that.$router.push('/index')
          }else{
            that.$router.push('/staff')
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
  }
}
</script>

<style scoped>
  .content-div{
    margin-bottom: 0;
  }
  .content{
    margin-top: 0;
  }
  .loading{
    color: #666;
    font-size: 16px;
    width: 100%;
    text-align: center;
    padding: 30px;
    margin: 50% auto 0;
    box-sizing: border-box;
  }
</style>