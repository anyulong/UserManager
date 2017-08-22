$(document).ready(function() {
	//界面加载后显示login、隐藏register
	$("#register").hide();
	//当注册时隐藏login，并显示register
	$("#btn_register").click(function() {
		$("#login").hide(500);
		$("#register").show(500);
		$(".top_img").hide(500);
	});
	//注册成功后显示login、隐藏register
	/*$(".btn_ok").click(function(){
		$("#login").show(500);
		$("#register").hide(500);
		$(".top_img").show(500);
	});
	*/
	$("#login #passWord").focus(function() {
		$(".top_img").css({ "animation-name": "imgs", "animation-duration": "2s", "animation-fill-mode": "forwards" });
	});
	$("#login #passWord").blur(function() {
		$(".top_img").css({ "animation-name": "imgsout", "animation-duration": "2s", "animation-fill-mode": "forwards" });
	});
});
(function(ajax) {
	let userName="";
	let passWord="";
	
	//获取省份信息
	function province(){
		let param = "?param=provinceInit";
		ajax.remoteGet("area.servlet",provinceInit,param);
	}
	function provinceInit(xhr) {
		let json = JSON.parse(xhr.responseText);
		json.province.forEach((item)=>{
			let province = document.getElementById("province");
			let option = document.createElement("option");
			option.innerHTML = item.name;
			option.value = item.code;
			province.appendChild(option);
		});
	}

	function createName() {
		let province = document.getElementById('province');
		let city = document.getElementById('city');
		let defaultOption_p = document.createElement("option");
		let defaultOption_c = document.createElement("option");
		defaultOption_p.value = "00";
		defaultOption_p.innerHTML = "请选择省份";
		defaultOption_p.selected = true;
		province.appendChild(defaultOption_p);
		defaultOption_c.value = "01";
		defaultOption_c.innerHTML = "请选择城市";
		defaultOption_c.selected = true;
		city.appendChild(defaultOption_c);
	}
	//获取城市信息
	function city(){
		let param = "?param=cityInit";
		let provinceValue = document.getElementById("province").value;
		param+="&province="+provinceValue
		ajax.remoteGet("area.servlet",cityInit,param);
	}
	function cityInit(xhr) {
		let json = JSON.parse(xhr.responseText);
		let provinceValue = document.getElementById("province").value;
		if(json.result == "ok") {
			let cityNode = document.getElementById("city");
			cityNode.length = 0;
			json.province.forEach((item) => {
				let option = document.createElement("option");
				option.value = item.code;
				option.innerHTML = item.name;
				cityNode.appendChild(option);
			});
		}
	}
	//焦点离开检查用户名是否重复
	function userCheck(){
		let param = "?param=userCheck";
		param+="&checkName="+document.getElementById("userName2").value;
		ajax.remoteGet("user.servlet",userCheckCallback,param);
	}
	function userCheckCallback(xhr){
		let json = JSON.parse(xhr.responseText);//转换成js的json对象
		if(json.result=="repeat"){
			
			$("#MyForm>div:nth-child(1)>.message").show(500).html("用户名重复！").css({"color":"red","font-size":"15px","line-height":"30px"});
			$("#MyForm>div:nth-child(1)>.message_jiao").show(500);
		}else{
			$("#MyForm>div:nth-child(1)>.message").show(500).html("用户名未被注册！").css({"color":"black","font-size":"15px","line-height":"30px"});
			$("#MyForm>div:nth-child(1)>.message_jiao").show(500);	
		}	
	}
	function checkPwd() {
		let pwd = document.getElementById('passWord2');
		let cpwd = document.getElementById('checkPwd');
		if(pwd.value != cpwd.value) {
			$("#MyForm>div:nth-child(3)>.message").show(500).html("密码不一致！").css({"color":"red","font-size":"15px","line-height":"30px"});
			$("#MyForm>div:nth-child(3)>.message_jiao").show(500);
		}else{
			$("#MyForm>div:nth-child(3)>.message").hide(500);
			$("#MyForm>div:nth-child(3)>.message_jiao").hide(500);
		}
	}
	function checkEmail() {
		var regCode = /^\w+@\w+\.[a-zA-Z]{2,3}(\.[a-zA-Z]{2,3})?$/;
		let email = document.getElementById('email');
		if(!regCode.test(email.value)) {
			$("#MyForm>div:nth-child(4)>.message").show(500).html("邮箱格式不正确！").css({"color":"red","font-size":"15px","line-height":"30px"});
			$("#MyForm>div:nth-child(4)>.message_jiao").show(500);
		}else{
			$("#MyForm>div:nth-child(4)>.message").hide(500);
			$("#MyForm>div:nth-child(4)>.message_jiao").hide(500);
		}
	}
	//验证登录
	function login(){
		userName = document.getElementById("userName").value;
		passWord = document.getElementById("passWord").value;
		if(userName!=""||passWord!=""){
			let param = "?param=login";
			param+="&userName="+userName
			param+="&passWord="+passWord;
			ajax.remoteGet("user.servlet",loginInit,param);	
		}else{
			alert("用户名或密码不能为空!");
		}
	}
	function loginInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			sessionStorage.setItem("userId",json.userId);
			window.location.href="main.html";
		}else{
			alert("用户名或密码错误！");
			window.history.go(0);
		}
	}
	function init() {
		createName();
		province();
		document.getElementById("userName2").addEventListener("blur",userCheck,false);
		document.getElementById("province").addEventListener("change",city,false);
		document.getElementById('checkPwd').addEventListener("blur", checkPwd, false);
		document.getElementById('email').addEventListener("blur", checkEmail, false);
		document.getElementById("btn_login").addEventListener("click",login,false);
	}
	window.addEventListener("load", init, false);
})(window.ajaxOperation);