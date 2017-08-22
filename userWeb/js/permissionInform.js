(function(ajax){
	var pageCount = 1;
	var pageSum = 0;
	var likeText = "";
	var userManagerCount = 0;
	var userRoleId = "";
	function updateTeable(){
		let param = "";
		ajax.remotePost("permission.servlet?param=updatepermission",tableInit,param);
	}
	function tableInit(xhr){
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
				tr.insertCell(1).innerHTML = item.id;
				tr.insertCell(2).innerHTML = item.permissionName;
				if(item.url!=undefined){
					tr.insertCell(3).innerHTML = item.url;
				}else{
					tr.insertCell(3).innerHTML = "无";
				}
				if(item.remark!=undefined){
					tr.insertCell(4).innerHTML = item.remark;	
				}else{
					tr.insertCell(4).innerHTML = "无";
				}
				tr.insertCell(5).innerHTML = item.parentId;
				tr.insertCell(6).innerHTML = "<i class='fa fa-pencil' title='编辑'></i>";
				tr.insertCell(7).innerHTML = "<i class='fa fa-close' title='删除'></i>";
			});
		}	
	}
	//鼠标点击tr选中checkbox
	function trClick(){
		$("#myTbody").on('click','td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5)',function(){
			let td = $(this).parent()[0].firstElementChild.firstElementChild;
			if(td.checked){
				td.checked = false;
			}else{
				td.checked = true;
			}
		});
	}
	function checkAll() {
		let names = document.getElementsByName('checks');
		let check = document.getElementById('check').checked;
		names.forEach(function(item) {
			item.checked = check;
		});
	}
	//添加
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
		param+="&params="+document.getElementById("add_name").value;
		param+="&params="+document.getElementById("add_url").value;
		param+="&params="+document.getElementById("add_remark").value;
		param+="&params="+document.getElementById("add_parent").value;
		ajax.remotePost("permission.servlet?param=addTable",addInit,param);
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
	//多条删除数据
	function deleteTable() {
			let param = "?param=deletePermission";
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
					ajax.remoteGet("permission.servlet", deleteInit, param);
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
	//单条数据删除
	function oneDelete(){
		$("#myTbody").on('click','td:nth-child(8) i',function(){
			let row_id = $(this).parent().parent().attr("id").split('_')[1];
			if(confirm("是否要删除？")) {
			let param = "?param=deletePermission";
			param+="&row_id="+row_id;
			ajax.remoteGet("permission.servlet", deleteInit, param);
			}
		});
	}
	//单行数据更新
	function showUpdate(){
		$("#myTbody").on('click','td:nth-child(7) i',function(){
			let tr = $(this).parent().parent()[0];
			let tds = tr.children;
			up_id = tr.id.split('_')[1];
			name = document.getElementById("up_name").value = tds[2].innerHTML;
			url = document.getElementById("up_url").value = tds[3].innerHTML;
			remark = document.getElementById("up_remark").value = tds[4].innerHTML;
			parent = document.getElementById("up_parent").value = tds[5].innerHTML;
			$("#up_id").slideDown(500);
			$(".main").slideUp(500);
		});
	}
	function upDate() {
		let new_name = document.getElementById("up_name").value;
		let new_url = document.getElementById("up_url").value;
		let new_remark = document.getElementById("up_remark").value;
		let new_parent = document.getElementById("up_parent").value;
		if(new_name == name && new_url == url && new_remark == remark && new_parent == parent) {
			alert("请修改后再提交!");
		} else {
			let param = "&params=" + up_id + "&params=" + new_name + "&params=" + new_url + "&params=" + new_remark + "&params=" + new_parent;
			ajax.remotePost("permission.servlet?param=upDate", upInit, param);
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
			ajax.remotePost("permission.servlet?param=getRole",userManagerInit,param);
			//console.log(id);
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
			h5.innerHTML = "权限信息管理&nbsp;&nbsp;|&nbsp;&nbsp;角色权限";
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
		ajax.remoteGet("permission.servlet",updateRoleInit,param);
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
	function init(){
		updateTeable();
		trClick();
		oneDelete();
		showUpdate();
		document.getElementById("btn_add").addEventListener("click",showAddTable,false);
		document.getElementById("close_i").addEventListener("click",closeAddTale,false);
		document.getElementById("add_ok").addEventListener("click",addTable,false);
		document.getElementById('check').addEventListener("change", checkAll, false);
		document.getElementById("btn_delete").addEventListener("click",deleteTable,false);
		document.getElementById("btn_add").addEventListener("click",showAddTable,false);
		document.getElementById("up_close_i").addEventListener("click",closeUpTale,false);
		document.getElementById("up_ok").addEventListener("click",upDate,false);
		document.getElementById("btn_permission").addEventListener("click",userManager,false);
	}
	window.addEventListener("load",init,false);
})(window.ajaxOperation);
