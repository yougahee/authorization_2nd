<template>
  <div class="login">
    <img src="../assets/logo.png">
    <h1>{{ msg }}</h1>
    <ul>
      <li>
        <input type="text" id="inputId" placeholder="Enter the Email id" />
      </li>
    </ul>
    <ul>
      <li>
        <input type="text" id="inputPW" placeholder="Enter the password" />
      </li>
    </ul>
    <button id="Login" @click="submit">Login</button>
    <p id="result" style="display:none">Hello</p>
    <ul>
      <li>
        <router-link to='/SignUp'>
          회원가입
        </router-link>
      </li>
    </ul>
    <ul>
      <li>
        <router-link to='/FindPW'>
          비밀번호 찾기
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Login Page'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
div {
  margin: 0 15% 0 15%;
  display: block;
  align-items: center;
  justify-content: center;
}
input {
  display: block;
  text-align: center;
  margin:auto;
  padding: 10px 15px;
  font-size: 14px;
  line-height: 1.5;
  border: 1px solid #cccccc;
  border-radius: 5px;
}
button {
  background-color: #e08128;
  width: 100px;
  height: 35px;
  border: none;
  border-radius: 3px;
  display: block;
  align-content: center;
  text-align: center;
  margin:auto;
  margin-top: 20px;
}
p{
  text-align: center;
  display: block;
  margin-top: 20px;
  color: red;
}
</style>
