(function(ajax){
	var pageCount = 1;
	var pageSum = 0;
	var likeText = "";
	var userManagerCount = 0;
	var userRoleId = "";
	function updateRole(){
		let param = "";
		ajax.remotePost("role.servlet?param=updateRoleInform",updateRoleInit,param);
	}
	function updateRoleInit(xhr) {
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
				tr.insertCell(1).innerHTML = item.id;
				tr.insertCell(2).innerHTML = item.name;
				tr.insertCell(3).innerHTML = item.remark;
				tr.insertCell(4).innerHTML = "<i class='fa fa-pencil' title='编辑'></i>";
				tr.insertCell(5).innerHTML = "<i class='fa fa-close' title='删除'></i>";
			});
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
	//多选删除    
	function deleteTable() {
			let param = "?param=deleteRole";
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
					ajax.remoteGet("role.servlet", deleteInit, param);
				}				
			}
	}
	//单条数据删除             
	function oneDelete(){
		$("#myTbody").on('click','td:nth-child(6) i',function(){
			let row_id = $(this).parent().parent().attr("id").split('_')[1];
			if(confirm("是否要删除？")) {
			let param = "?param=deleteRole";
			param+="&row_id="+row_id;
			ajax.remoteGet("role.servlet", deleteInit, param);
			}
		});
	}
	function deleteInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("删除成功！");
			document.getElementById("text_like").value="";
			likeText = "";
			updateRole();
		}else{
			alert("删除失败！");
		}
	}
	//角色添加--添加界面显示
	function showAddRole(){	
		$("#add_id").slideDown(500);
		$(".main").slideUp(500);
	}
	//角色添加--添加界面隐藏
	function closeAddRole(){
		$("#add_id").slideUp(500);
		$(".main").slideDown(500);
	}
	//增加     
	function addRoleInform(){
		let param = "";
		param+="&params="+document.getElementById("add_name").value;
		param+="&params="+document.getElementById("add_remark").value;
		ajax.remotePost("role.servlet?param=addRoleInform",addRoleInformInit,param);
	}
	function addRoleInformInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("添加成功");
			document.getElementById("text_like").value="";
			likeText = "";
			closeAddRole();
			updateRole();
		}else{
			alert("添加失败！");
		}
	}
	//单行数据更新
	function showUpdate(){
		$("#myTbody").on('click','td:nth-child(5) i',function(){
			let tr = $(this).parent().parent()[0];
			let tds = tr.children;
			 up_id = tr.id.split('_')[1];
			 name = document.getElementById("up_name").value = tds[2].innerHTML;
			 remark = document.getElementById("up_remark").value = tds[3].innerHTML;
			$("#up_id").slideDown(500);
			$(".main").slideUp(500);
		});
	}
	function upDate() {
		let new_name = document.getElementById("up_name").value;
		let new_remark = document.getElementById("up_remark").value;
		if(new_name == name && new_remark == remark) {
			alert("请修改后再提交!");
		} else {
			let param = "&id=" + up_id + "&params=" + new_name + "&params=" + new_remark;
			ajax.remotePost("role.servlet?param=upRole", upRoleInit, param);
		}
	}
	function upRoleInit(xhr){
		let json = JSON.parse(xhr.responseText);
		if(json.result=="ok"){
			alert("更新成功！");
			document.getElementById("text_like").value="";
			likeText = "";
			updateRole();
			closeUpRole();
			
		}else{
			alert("更新失败！");
		}
	}
	function closeUpRole(){
		$("#up_id").slideUp(500);
		$(".main").slideDown(500);
	}
	//模糊查询
	function likeQuery(){
		let param = "?param=like&page=1";
		likeText = document.getElementById("text_like").value;
		param+="&likeText="+likeText;
		ajax.remoteGet("role.servlet",updateRoleInit,param);
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
				tr.insertCell(1).innerHTML = item.id;
				tr.insertCell(2).innerHTML = item.name;
				tr.insertCell(3).innerHTML = item.remark;
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
				ajax.remoteGet("role.servlet",pageDateInit,param);
			}
		}
		if(event.target.id=="pagePrevious"){
			if(pageCount <=1){
				alert("已经是第一页了！");
			}else{
				let param = "?param=like&page="+(--pageCount);
				param+="&likeText="+likeText;
				console.log("上一页："+pageCount);
				ajax.remoteGet("role.servlet",pageDateInit,param);
			}
		}
		if(event.target.id=="pageFirst"){
			pageCount = 1;
			let param = "?param=like&page=1";
			likeText = document.getElementById("text_like").value;
			param+="&likeText="+likeText;
			console.log("首页："+pageCount);
			ajax.remoteGet("role.servlet",pageDateInit,param);
		}
	}
	//对数据进行排序
	var orderCount = 1;
	function order(){
		if(orderCount==1){
			$(".order").children().css({"transform":"rotate(180deg)","transition":"0.5s"});
			$(".order span").html("&nbsp;升序");
			let param = "?param=order";
			param+="&orderParam=asc&page="+pageCount+"&likeText="+likeText;
			ajax.remoteGet("role.servlet",pageDateInit,param);
			orderCount=0;
		}else{
			$(".order").children().css({"transform":"rotate(0deg)","transition":"0.5s"});
			$(".order span").html("&nbsp;降序");
			let param = "?param=order";
			param+="&orderParam=desc&page="+pageCount+"&likeText="+likeText;
			ajax.remoteGet("role.servlet",pageDateInit,param);
			orderCount=1;
		}
	}
	function checkAll() {
		let names = document.getElementsByName('checks');
		let check = document.getElementById('check').checked;
		names.forEach(function(item) {
			item.checked = check;
		});
	}
	function init(){
		updateRole();
		trClick();
		oneDelete();
		showUpdate();
		document.getElementById("btn_delete").addEventListener("click",deleteTable,false);
		document.getElementById("btn_add").addEventListener("click",showAddRole,false);
		document.getElementById("close_i").addEventListener("click",closeAddRole,false);
		document.getElementById("add_ok").addEventListener("click",addRoleInform,false);
		document.getElementById("up_close_i").addEventListener("click",closeUpRole,false);
		document.getElementById("up_ok").addEventListener("click",upDate,false);
		document.getElementById("btn_like").addEventListener("click",likeQuery,false);
		document.getElementById("pageNext").addEventListener("click",pageData,false);
		document.getElementById("pagePrevious").addEventListener("click",pageData,false);
		document.getElementById("pageFirst").addEventListener("click",pageData,false);
		document.getElementById("id_order").addEventListener("click",order,false);
		document.getElementById('check').addEventListener("change", checkAll, false);

	}
	window.addEventListener("load",init,false);
})(window.ajaxOperation);
