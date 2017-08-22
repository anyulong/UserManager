var ajaxOperation = (function() {
	let remoteSite="http://localhost:8080/ums/";//站点名
	//非跨域操作
	function get(url,callback,param) {
		let xhr = new XMLHttpRequest();
		if(param!=""||param!=null||param!=undefined){
			xhr.open("GET",url+param, true); 
		}else{
			xhr.open("GET",url, true); 
		}
		xhr.addEventListener("readystatechange",()=>{
			if(xhr.readyState==4&&xhr.status==200){
				callback(xhr);
			}
		},false);
		xhr.send(null); //真正的提交操作
	}
	//跨域操作
	function remoteGet(url,callback,param) {
		let xhr = new XMLHttpRequest();
		if(param!=""||param!=null||param!=undefined){
			xhr.open("GET",remoteSite+url+param, true); 
		}else{
			xhr.open("GET",remoteSite+url, true); 
		}
		xhr.addEventListener("readystatechange",()=>{
			if(xhr.readyState==4&&xhr.status==200){
				callback(xhr);
			}
		},false);
		xhr.send(null); //真正的提交操作
	}
	//跨域post操作
	function remotePost(url,callback,param){
		let xhr = new XMLHttpRequest(); 
		xhr.open("POST",remoteSite+url, true); 
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhr.addEventListener("readystatechange",()=>{
			if(xhr.readyState == 4 && xhr.status == 200||xhr.status == 0) {
				callback(xhr);
			}
		},false);
		xhr.send(param); 
	}
	return {get:get,remoteGet:remoteGet,remotePost:remotePost};
})();