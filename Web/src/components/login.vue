<template>
    <div class="center">
      <vs-dialog prevent-close not-close blur v-model="active2">
        <template #header>
          <h4 class="not-margin">
            欢迎使用电子地图系统
          </h4>
        </template>

        <div class="con-form">
          <vs-input v-model="loginForm.username" placeholder="Username">
            <template #icon>
              @
            </template>
          </vs-input>
          <vs-input type="password" v-model="loginForm.password" placeholder="Password">
            <template #icon>
              <i class='bx bxs-lock'></i>
            </template>
          </vs-input>
        </div>

        <template #footer>
          <div class="footer-dialog">
            <vs-button @click="login" block>
              登陆
            </vs-button>
            <div class="new">
              还没有账号嘛? <a href="#/register">点击这里创建一个新账号吧</a>
            </div>
          </div>
        </template>
      </vs-dialog>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name: "login",
  data(){
    return {
      loginForm: {
        username: '',
        password: ''
      },
      active2 : true
    }
  },
  methods:{
    async login () {
      try {
        const {data} = await axios.post("http://localhost:82/user/login",this.loginForm)
        console.log(data)
        if (data.data != null) {
          localStorage.setItem('token',data.data.token)
          console.log(data.data.token)
          // alert("登陆成功")
          console.log(data.data.identity)
          if (data.data.identity === '超级管理员'){

            this.$router.push('/mapControl')
          }else {
            this.$router.push('/markPlace')
          }
        }else {
          alert("账号或密码错误")
          localStorage.removeItem('token')
        }
      }catch (e){
        alert("账号或密码错误")
      }
    },
  }
}
</script>

<style lang="stylus">
getColor(vsColor, alpha = 1)
unquote("rgba(var(--vs-"+vsColor+"), "+alpha+")")
getVar(var)
unquote("var(--vs-"+var+")")
.not-margin
  margin 0px
  font-weight normal
  padding 10px
.con-form
  width 100%
  .flex
    display flex
    align-items center
    justify-content space-between
    a
      font-size .8rem
      opacity .7
      &:hover
        opacity 1
  .vs-checkbox-label
    font-size .8rem
  .vs-input-content
    margin 10px 0px
    width calc(100%)
    .vs-input
      width 100%
.footer-dialog
  display flex
  align-items center
  justify-content center
  flex-direction column
  width calc(100%)
  .new
    margin 0px
    margin-top 20px
    padding: 0px
    font-size .7rem
    color getColor('primary') !important
    margin-left 6px
    &:hover
      text-decoration underline
  .vs-button
    margin 0px

</style>