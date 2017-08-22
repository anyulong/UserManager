(function(ajax) {
	let userId = "";

	function getMenu() {
		let param = "";
		param += "&userId=" + userId;
		ajax.remotePost("permission.servlet?param=getMenu", getMenuInit, param);
	}

	function getMenuInit(xhr) {
		let json = JSON.parse(xhr.responseText);
		let mainMenu = document.getElementById("mainMenu");
		if(json.result == "ok") {
			let permission = json.list;
			let ul1 = document.createElement("ul");
			ul1.id="menu_ul_one"
			permission.forEach((item) => {
				if(item.parentId == 0) {
					let li1 = document.createElement("li");
					let a1 = document.createElement("a");
					let i1 = document.createElement("i");
					i1.setAttribute("class","fa fa-caret-down");
					a1.innerHTML = item.permissionName+"&nbsp;&nbsp;";
					li1.appendChild(a1);
					li1.appendChild(i1);
					ul1.appendChild(li1);
					let ul2 = document.createElement("ul");
					permission.forEach((item2) => {
						if(item2.parentId == item.id) {
							let li2 = document.createElement("li");
							let a2 = document.createElement("a");
							a2.innerHTML = item2.permissionName;
							a2.href = "http://127.0.0.1:8020/userWeb/" + item2.url;
							a2.target="mainIfarme";
							li2.appendChild(a2);
							ul2.appendChild(li2);
						}
					});
					ul1.appendChild(ul2);
				}
			});
			mainMenu.appendChild(ul1);
		} else {
			mainMenu.innerHTML = "没有权限";
		}
	}

	function init() {
		userId = sessionStorage.getItem("userId");
		getMenu();
	}
	window.addEventListener("load", init, false);
})(window.ajaxOperation);