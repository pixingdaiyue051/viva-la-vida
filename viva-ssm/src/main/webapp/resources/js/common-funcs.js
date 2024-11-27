$(function() {
	console.log('###################');
	console.log('here you found me  ');
	console.log('###################');
});
//******************通用,无关前段框架只要支持js和jquery插件就能用******************START
//判断数组内是否有某元素,并得到索引
//by hexk
function getIndexInArray(arr, obj) {
	var index = -1;
	if (arr.length) {
		$.each(arr,function(i, d) {
			if (isEqual(obj, d)) {
				index = i;
				return false;
			}
		});
	}
	return index;
}
//网上粘的
function isEqual(a, b) {
	//如果a和b本来就全等
	if (a === b) {
		//判断是否为0和-0
		return a !== 0 || 1 / a === 1 / b;
	}
	//判断是否为null和undefined
	if (a == null || b == null) {
		return a === b;
	}
	//接下来判断a和b的数据类型
	var classNameA = toString.call(a),
	classNameB = toString.call(b);
	//如果数据类型不相等，则返回false
	if (classNameA !== classNameB) {
		return false;
	}
	//如果数据类型相等，再根据不同数据类型分别判断
	switch (classNameA) {
	case '[object RegExp]':
	case '[object String]':
		//进行字符串转换比较
		return '' + a === '' + b;
	case '[object Number]':
		//进行数字转换比较,判断是否为NaN
		if ( + a !== +a) {
			return + b !== +b;
		}
		//判断是否为0或-0
		return + a === 0 ? 1 / +a === 1 / b: +a === +b;
	case '[object Date]':
	case '[object Boolean]':
		return + a === +b;
	}
	//如果是对象类型
	if (classNameA == '[object Object]') {
		//获取a和b的属性长度
		var propsA = Object.getOwnPropertyNames(a),
		propsB = Object.getOwnPropertyNames(b);
		if (propsA.length != propsB.length) {
			return false;
		}
		for (var i = 0; i < propsA.length; i++) {
			var propName = propsA[i];
			//如果对应属性对应值不相等，则返回false
			if (a[propName] !== b[propName]) {
				return false;
			}
		}
		return true;
	}
	//如果是数组类型
	if (classNameA == '[object Array]') {
		if (a.toString() == b.toString()) {
			return true;
		}
		return false;
	}
}
//网上粘的
//根据dom的class元素获取到el对象返回一个数组
function getElementsByClass(classnames) {
	var classobj = new Array(); //定义数组 
	var classint = 0; //定义数组的下标 
	var tags = document.getElementsByTagName("*"); //获取HTML的所有标签 
	for (var i in tags) { //对标签进行遍历 
		if (tags[i].nodeType == 1) { //判断节点类型 
			if (tags[i].getAttribute("class") == classnames) //判断和需要CLASS名字相同的，并组成一个数组 
			{
				classobj[classint] = tags[i];
				classint++;
			}
		}
	}
	return classobj; //返回组成的数组 
}

function CloseWindow(action) {
   if (window.CloseOwnerWindow) 
	   return window.CloseOwnerWindow(action);
   else window.close();
}
	
function selfJumpTo(url) {
	if(url){
		document.location.href = url;
	}else{
		document.location.reload();
	}
}
//******************通用,无关前段框架只要支持js和jquery插件就能用******************END