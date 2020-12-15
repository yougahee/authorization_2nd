<ul>
      <li>
        아이디(이메일)
      </li>
      <li>
        <input type="text" id="inputId" placeholder="Enter the Email id" />
      </li>
    </ul>
    <ul>
      <li>
        비밀번호
      </li>
      <li>
        <input type="text" id="inputPW" placeholder="Enter the PassWord" />
      </li>
    </ul>
    <ul>
      <li>
        비밀번호 확인
      </li>
      <li>
        <input type="text" id="inputPW" placeholder="Enter the Same PassWord" />
      </li>
    </ul>
    <ul>
      <li>
        사용자 이름
      </li>
      <li>
        <input type="text" id="inputName" placeholder="Enter your name" />
      </li>
    </ul>
     <ul>
      <li>
        생년월일
      </li>
      <li>
        <input type="text" id="inputName" placeholder="Enter your name" />
      </li>
    </ul>
    <button id="send_url_btn" @click="submit">회원가입하기</button>




    ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
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
  width: 300px;
  margin-left: auto;
  margin-right: auto;
  padding: 10px 15px;
  font-size: 14px;
  line-height: 1.5;
  border: 1px solid #cccccc;
  border-radius: 5px;
}
button {
  background-color: #e08128;
  width: 200px;
  height: 35px;
  border: none;
  border-radius: 3px;
  display: block;
  align-content: center;
  text-align: center;
  margin:auto;
  margin-top: 20px;
  font-family: Dotum,'돋움',Helvetica,sans-serif;
}
p{
  text-align: center;
  display: block;
  margin-top: 20px;
  color: red;
}