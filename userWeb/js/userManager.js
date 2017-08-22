(function(ajax) {
	var pageCount = 1;
	var pageSum = 0;
	var likeText = "";
	var userManagerCount = 0;
	var userRoleId = "";
	function checkAll() {
		let names = document.getElementsByName('checks');
		let check = document.getElementById('check').checked;
		names.forEach(function(item) {
			item.checked = check;
		});
	}
	function tableInit(xhr) {
		pageCount = 1;
		let tbody = document.getElementById("myTbody");
		if(tbody.rows.length != 0) {
			Array.from(tbody.rows).forEach((item) => {
				tbody.deleteRow(0);
			});
		}
		let json = JSON.parse(xhr.responseText);
		if(json.result == "ok") {
			pageSum = json.pageCount;
			console.log("正常/现在的行数"+pageSum+"当前页："+pageCount);
			json.table.forEach((item) => {
				let index = tbody.rows.length;
				let tr = tbody.insertRow(index);
				tr.id = "row_" + item.id;
				tr.insertCell(0).innerHTML = "<input type='checkbox' name='checks' value='" + item.id + "'>";
				tr.insertCell(1).innerHTML = item.name;
				tr.insertCell(2).innerHTML = item.password;
				tr.insertCell(3).innerHTML = item.telephone;
				tr.insertCell(4).innerHTML = "<i class='fa fa-pencil' title='编辑'></i>";
				tr.insertCell(5).innerHTML = "<i class='fa fa-close' title='删除'></i>";
			});
		}
	}
	function updateTeable(){
		let param = "";
		ajax.remotePost("user.servlet?param=updateTable",tableInit,param);
	}
	function pageDateInit(xhr){
		let tbody = document.getElementById("myTbody");
		if(tbody.rows.length != 0) {
			Array.from(tbody.rows).forEach((item) => {
				tbody.deleteRow(0);
			});
		}
		let json = JSON.parse(xhr.responseText);
		if(json.result == "ok") {
			pageSum = json.pageCount;
			console.log("模糊/现在的行数"+pageSum+"当前页："+pageCount);
			json.table.forEach((item) => {
				let index = tbody.rows.length;
				let tr = tbody.insertRow(index);
				tr.id = "row_" + item.id;
				tr.insertCell(0).innerHTML = "<input type='checkbox' name='checks' value='" + item.id + "'>";
				tr.insertCell(1).innerHTML = item.name;
				tr.insertCell(2).innerHTML = item.password;
				tr.insertCell(3).innerHTML = item.telephone;
				tr.insertCell(4).innerHTML = "<i class='fa fa-pencil' title='编辑'></i>";
				tr.insertCell(5).innerHTML = "<i class='fa fa-close' title='删除'></i>";
			});
		}	
	}
	function pageData(event){
		var pageCounts = pageSum%5==0?pageSum/5:parseInt(pageSum/5)+1;
		if(event.target.id=="pageNext"){
			if(pageCount >= pageCounts){
				alert("已经是最后一页了！");	
			}else{
				let param = "?param=like&page="+(++pageCount);
				param+="&likeText="+likeText;
				console.log("下一页："+pageCount);
				ajax.remoteGet("user.servlet",pageDateInit,param);
			}
		}
		if(event.target.id=="pagePrevious"){
			if(pageCount <=1){
				alert("已经是第一页了！");
			}else{
				let param = "?param=like&page="+(--pageCount);
				param+="&likeText="+likeText;
				console.log("上一页："+pageCount);
				ajax.remoteGet("user.servlet",pageDateInit,param);
			}
		}
		if(event.target.id=="pageFirst"){
			pageCount = 1;
			let param = "?param=like&page=1";
			likeText = document.getElementById("text_like").value;
			param+="&likeText="+likeText;
			console.log("首页："+pageCount);
			ajax.remoteGet("user.servlet",pageDateInit,param);
		}
	}
	function deleteTable() {
			let param = "?param=deleteTable";
			let names = document.getElementsByName('checks');
			let count = 0;
			names.forEach((item) => {
				if(item.checked) {
					param += "&row_id=" + item.value;
					count++;
				}
			});
			if(count==0){
				alert("没有选择要删除的行！");
			}else{
				if(confirm("是否要删除？")){
					ajax.remoteGet("user.servlet", deleteInit, param);
				}				
			}
	}
	function deleteInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("删除成功！");
			document.getElementById("text_like").value="";
			likeText = "";
			updateTeable();
		}else{
			alert("删除失败！");
		}
	}
	function showAddTable(){	
		$("#add_id").slideDown(500);
		$(".main").slideUp(500);
	}
	function closeAddTale(){
		$("#add_id").slideUp(500);
		$(".main").slideDown(500);
	}
	function addTable(){
		let param = "";
		param+="&params="+document.getElementById("add_userName").value;
		param+="&params="+document.getElementById("add_passWord").value;
		param+="&params="+document.getElementById("add_telePhone").value;
		ajax.remotePost("user.servlet?param=addTable",addInit,param);
	}
	function addInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("添加成功");
			document.getElementById("text_like").value="";
			likeText = "";
			closeAddTale();
			updateTeable();
		}else{
			alert("添加失败！");
		}
	}
	//鼠标点击tr选中checkbox
	function trClick(){
		$("#myTbody").on('click','td:nth-child(2),td:nth-child(3),td:nth-child(4)',function(){
			let td = $(this).parent()[0].firstElementChild.firstElementChild;
			if(td.checked){
				td.checked = false;
			}else{
				td.checked = true;
			}
		});
	}
	//单条数据删除
	function oneDelete(){
		$("#myTbody").on('click','td:nth-child(6) i',function(){
			let row_id = $(this).parent().parent().attr("id").split('_')[1];
			if(confirm("是否要删除？")) {
			let param = "?param=deleteTable";
			param+="&row_id="+row_id;
			ajax.remoteGet("user.servlet", deleteInit, param);
			}
		});
	}
	//对数据进行排序
	var orderCount = 1;
	function order(){
		if(orderCount==1){
			$(".order").children().css({"transform":"rotate(180deg)","transition":"0.5s"});
			$(".order span").html("&nbsp;升序");
			let param = "?param=order";
			param+="&orderParam=asc&page="+pageCount+"&likeText="+likeText;
			ajax.remoteGet("user.servlet",pageDateInit,param);
			orderCount=0;
		}else{
			$(".order").children().css({"transform":"rotate(0deg)","transition":"0.5s"});
			$(".order span").html("&nbsp;降序");
			let param = "?param=order";
			param+="&orderParam=desc&page="+pageCount+"&likeText="+likeText;
			ajax.remoteGet("user.servlet",pageDateInit,param);
			orderCount=1;
		}
	}
	//单行数据更新
	function showUpdate(){
		$("#myTbody").on('click','td:nth-child(5) i',function(){
			let tr = $(this).parent().parent()[0];
			let tds = tr.children;
			 up_id = tr.id.split('_')[1];
			 name = document.getElementById("up_userName").value = tds[1].innerHTML;
			 pwd = document.getElementById("up_passWord").value = tds[2].innerHTML;
			 tel = document.getElementById("up_telePhone").value = tds[3].innerHTML;
			$("#up_id").slideDown(500);
			$(".main").slideUp(500);
		});
	}
	function upDate() {
		let new_name = document.getElementById("up_userName").value;
		let new_pwd = document.getElementById("up_passWord").value;
		let new_tel = document.getElementById("up_telePhone").value;
		if(new_name == name && new_pwd == pwd && new_tel == tel) {
			alert("请修改后再提交!");
		} else {
			let param = "&params=" + up_id + "&params=" + new_name + "&params=" + new_pwd + "&params=" + new_tel;
			ajax.remotePost("user.servlet?param=upDate", upInit, param);
		}
	}
	function upInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("更新成功！");
			document.getElementById("text_like").value="";
			likeText = "";
			updateTeable();
			closeUpTale();
			
		}else{
			alert("更新失败！");
		}
	}
	function closeUpTale(){
		$("#up_id").slideUp(500);
		$(".main").slideDown(500);
	}
	//模糊查询
	function likeQuery(){
		let param = "?param=like&page=1";
		likeText = document.getElementById("text_like").value;
		param+="&likeText="+likeText;
		//console.log(param);
		ajax.remoteGet("user.servlet",pageDateInit,param);
	}
	//用户权限管理
	function userManager(){
		let names = document.getElementsByName("checks");
		let count = 0;
		let id = 0;
		names.forEach((item)=>{
			if(item.checked==true){
				count++;
				id = item.value;
			}
		});
		if(count==0){
			alert("请选择一条数据！");
		}else if(count>1){
			alert("请勿选择多条数据");
		}else{
			let param = "";
			param+="&id="+id;
			userRoleId = id;
			ajax.remotePost("role.servlet?param=getRole",userManagerInit,param);
		}
	}
	function userManagerInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			if(++userManagerCount>1){
				deleteUserManager();	
			}
			let inform = document.getElementById("updateRole_inform");
			let h5 = document.createElement("h5");
			h5.innerHTML = "用户信息管理&nbsp;&nbsp;|&nbsp;&nbsp;用户角色权限";
			inform.appendChild(h5);
			let node = document.getElementById("updateRole_tbody");
			let count = 0;
			let tr = document.createElement("tr");
			json.roles.forEach((item)=>{
				let td = document.createElement("td");
				let input = document.createElement("input");
				input.id = "userRole"+item.id;
				input.type="checkbox";
				input.name = "userRole";
				input.value = item.id;
				if(item.checked==true){
					input.checked = true;
				}
				let label = document.createElement("label");
				label.setAttribute("for","userRole"+item.id);
				label.innerHTML = item.name;
				td.appendChild(input);
				td.appendChild(label);
				tr.appendChild(td);
			});
			node.appendChild(tr);
			let btn = document.getElementById("updateRole_btn");
			let button1 = document.createElement("button");
			button1.type = "button";
			button1.innerHTML = "返回";
			button1.id = "updateRole_btn_back";
			let button2 = document.createElement("button");
			button2.type = "button";
			button2.innerHTML = "修改";
			button1.id = "updateRole_btn_ok";
			btn.appendChild(button1);
			btn.appendChild(button2);
			$(".main").slideUp(500)
			$("#updateRole_div").slideDown(500);
			button1.addEventListener("click",()=>{
				$(".main").slideDown(500);
				$("#updateRole_div").slideUp(500);
			},false);
			button2.addEventListener("click",updateRole,false);
		}else{
			console.log("error");
		}
	}
	//执行用户角色权限更新操作
	function updateRole(){
		let names = document.getElementsByName("userRole");
		let param = "?param=updateRole&id="+userRoleId;
		names.forEach((item)=>{
			if(item.checked){
				param+="&ids="+item.value;
			}
		});
		ajax.remoteGet("role.servlet",updateRoleInit,param);
	}
	function updateRoleInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result="ok"){
			alert("更新成功！");
			updateTeable();
			$(".main").slideDown(500);
			$("#updateRole_div").slideUp(500);
		}else{
			alert("更新失败！");
		}
	}
	//删除用户权限管理节点信息
	function deleteUserManager(){
		let node = document.getElementById("updateRole_tbody");
		let btn = document.getElementById("updateRole_btn");
		let inform = document.getElementById("updateRole_inform");
		btn.removeChild(btn.firstElementChild);
		btn.removeChild(btn.lastElementChild);
		inform.removeChild(inform.firstElementChild);
		let tr = node.firstElementChild;
		node.removeChild(tr);
	}
	function init() {
		updateTeable();
		trClick();
		oneDelete();
		showUpdate();
		document.getElementById('check').addEventListener("change", checkAll, false);
		document.getElementById("btn_delete").addEventListener("click",deleteTable,false);
		document.getElementById("btn_add").addEventListener("click",showAddTable,false);
		document.getElementById("close_i").addEventListener("click",closeAddTale,false);
		document.getElementById("add_ok").addEventListener("click",addTable,false);
		document.getElementById("id_order").addEventListener("click",order,false);
		document.getElementById("up_close_i").addEventListener("click",closeUpTale,false);
		document.getElementById("up_ok").addEventListener("click",upDate,false);
		document.getElementById("pageNext").addEventListener("click",pageData,false);
		document.getElementById("pagePrevious").addEventListener("click",pageData,false);
		document.getElementById("pageFirst").addEventListener("click",pageData,false);
		document.getElementById("btn_like").addEventListener("click",likeQuery,false);
		document.getElementById("btn_userManager").addEventListener("click",userManager,false);
	}

	window.addEventListener("load", init, false);
})(window.ajaxOperation);