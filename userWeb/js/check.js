function init(){
	document.getElementById("MyForm").addEventListener("submit",function(e){
		let userName = document.getElementById("userName2").value;
		let passWord = document.getElementById('passWord2').value;
		let checkPwd = document.getElementById('checkPwd').value;
		if(userName==""){
			alert("用户名不能为空！");
			e.preventDefault();
		}else if(passWord==""){
			alert("密码不能为空！");
			e.preventDefault();
		}else if(passWord!=checkPwd){
			alert("两次密码输入不一致！");
			e.preventDefault();
		}else{
			$("#login").show(500);
			$("#register").hide(500);
			$(".top_img").show(500);
		}
	},false);
}
window.addEventListener("load",init,false);