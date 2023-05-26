<template>
  <div class="center">
    <vs-dialog prevent-close not-close blur v-model="active2">
      <template #header>
        <h4 class="not-margin">
          欢迎注册页面
        </h4>
      </template>

      <div class="con-form">
        <vs-input v-model="registerForm.username" placeholder="用户名">
          <template #icon>
            @
          </template>
        </vs-input>
        <vs-input type="password" v-model="registerForm.password" placeholder="密码">
          <template #icon>
            <i class='bx bxs-lock'></i>
          </template>
        </vs-input>
        <vs-input type="password" v-model="registerForm.confirmPassword" placeholder="确认密码">
          <template #icon>
            <i class='bx bxs-lock'></i>
          </template>
        </vs-input>
      </div>

      <template #footer>
        <div class="footer-dialog">
          <vs-button @click="register" block>
            注册
          </vs-button>
        </div>
      </template>
    </vs-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "register",
  data(){
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      active2 : true
    }
  },
  methods:{
    async register(){
        if (this.registerForm.password != this.registerForm.confirmPassword){
          alert('注册失败，密码和确认密码不一致')
        }else{
          const {data:isAvailable} = await axios.get('http://localhost:82/user/info/'+this.registerForm.username)
          console.log(isAvailable)
          if (isAvailable === true){
            let userInfo = {
              username: this.registerForm.username,
              password: this.registerForm.password,
              identity: '普通用户'
            }
            const {data} = await axios.post('http://localhost:82/user/register',userInfo)
            console.log(data)
            alert('注册成功')
            this.$router.push('/login')
          }
          else{
            alert('用户名已被注册!')
          }
        }
    }
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