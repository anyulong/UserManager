$(document).ready(function(){
	let count = 0;
	$("#mainMenu").on('click','#menu_ul_one>li:nth-child(1)',function(){
		if(count==0){
			$("#menu_ul_one li:nth-child(1)+ul").slideUp(500);
			$("#menu_ul_one li:nth-child(1) i").css({"transform":"rotate(0deg)","transition":"0.5s"});
			count = 1;
		}else if(count==1){
			$("#menu_ul_one li:nth-child(1)+ul").slideDown(500);
			$("#menu_ul_one li:nth-child(1) i").css({"transform":"rotate(180deg)","transition":"0.5s"});
			count = 0;
		}
	});
	$(".footer i:nth-child(1)").click(()=>{
		window.open("https://github.com/zhangjianbang","_new");
	});
});
function init(){
}

window.addEventListener('load',init,false);
