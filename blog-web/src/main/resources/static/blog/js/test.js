var legp = function (ele) { return ({
    that: Array.prototype.slice.call(document.querySelectorAll(ele), 0),
    stopPropagation: function (e) {
        e = e || window.event;
        if (e.stopPropagation) { //W3C阻止冒泡方法  
            e.stopPropagation();
        }
        else {
            e.cancelBubble = true; //IE阻止冒泡方法  
        }
    },
    hide: function () {
        legp(ele).that.forEach(function (item) {
            item.style.cssText += "display:none;";
        });
    },
    show: function () {
        legp(ele).that.forEach(function (item) {
            item.style.cssText += "display:block;";
        });
	},
	legp_searchList:function(name){
		let arr=[];
		legp("input[name="+name+"]").that.forEach(item => {
			arr.push(item.value)
		});
		return arr;
	},
    legp_search: function (name, tagData) {
        var domId = legp(ele).that[0];
        var html = "\n            <div>\n                <label>\u5DF2\u9009\u62E9:</label>\n                <div class=\"AD\">\n                </div>\n            </div>\n            <div>\n                <label>\u9009\u62E9</label>\n                <div class=\"selectId\">\n                    <input type=\"text\" placeholder=\"\u8F93\u5165\u6216\u9009\u62E9\" autocomplete=\"off\">\n                    <dl style=\"display: none;\">\n                    </dl>\n                </div>\n            </div>";
        domId.innerHTML = html;
        // domId.appendChild(dom);
        legp(".AD").that[0].parentNode.style.cssText += "display:none;";
        //获取当前广告
        function myClick() {
            legp(".selectId dl dd").that.forEach(function (item) {
                item.onclick = function (e) {
                    var id = this.attributes.value.value;
                    if (id != '') {
                        legp(".AD").that[0].innerHTML += "<a href=\"javascript:;\" class=\"label\"><span>" + this.innerHTML + "</span><input type=\"hidden\" name=\"" + name + "\" id=\"" + id + "\" value=\"" + id + "\"/><span class=\"close\">x</span></a>";
                        legp(".AD").that[0].parentNode.style.cssText += "display:block;";
                        for (var i = 0; i < tagData.length; i++) {
                            if (tagData[i].id == id) {
                                tagData.splice(i, 1);
                                i = tagData.length;
                            }
                        }
                        removeAdvertising();
                    }
                    legp(".selectId dl").hide();
                    legp(".selectId input").that[0].value = '';
                    e.stopPropagation();
                };
            });
        }
        var ddRemove = function (dd) {
            var temp = dd.nextElementSibling;
            dd.remove();
            if (temp != null && temp.nodeName == 'DD') {
                ddRemove(temp);
            }
        };
        //筛选
        legp(".selectId input").that[0].oninput = function () {
            var val = this.value; //获取input值
            var dd = legp(".selectId dl dd").that[0];
            if (dd == "DD") {
                ddRemove(dd);
            }
            legp(".selectId dl").hide();
            if (tagData.length > 0) {
                legp(".selectId dl").show();
                var sear_1 = new RegExp(val);
                var judge_1 = false;
                legp(".selectId dl").that[0].innerHTML = "";
                tagData.forEach(function (item) {
                    if (sear_1.test(item.name)) {
                        judge_1 = true;
                        legp(".selectId dl").that[0].innerHTML += "<dd value=\"" + item.id + "\">" + item.name + "</dd>";
                    }
                });
                if (!judge_1) {
                    legp(".selectId dl").that[0].innerHTML = "<dd>\u6682\u65E0\u6570\u636E</dd>";
                }
                myClick();
            }
        };
        //显示没被选择的
        legp(".selectId input").that[0].onclick = function (e) {
            var dd = legp(".selectId dl dd").that[0];
            if (dd == "DD") {
                ddRemove(dd);
            }
            if (tagData.length == 0) {
                this.innerHTML = "暂无数据";
            }
            else {
                legp(".selectId dl").show();
            }
            legp(".selectId dl").that[0].innerHTML = "";
            tagData.sort(function (a, b) {
                return a.id - b.id;
            });
            tagData.forEach(function (item) {
                legp(".selectId dl").that[0].innerHTML += "<dd value=\"" + item.id + "\">" + item.name + "</dd>";
            });
            myClick();
            e.stopPropagation();
        };
        var AD = /** @class */ (function () {
            function AD(name, id) {
                this.name = name;
                this.id = id;
            }
            return AD;
        }());
        //删除当前广告
        function removeAdvertising() {
            legp(".close").that.forEach(function (item) {
                item.onclick = function () {
                    ddRemove(this.parentNode);
                    var id = this.parentNode.children[1].id;
                    var text = this.parentNode.children[0].innerHTML;
                    tagData.push(new AD(text, id));
                    if (legp(".close").that.length == 0) {
                        legp(".AD").that[0].parentNode.style.cssText += "display:none;";
                    }
                };
            });
        }
        // //封装
        // function clickoutSide(nameClass, callback) {
        // 	// 全局注册点击事件
        // 	document.onclick = function (e) {
        // 		//若点击元素为目标元素则返回
        // 		if (e.target.className === nameClass) return
        // 		//否则执行回调函数
        // 		callback()
        // 	}
        // }
        //隐藏
        document.onclick = function (e) {
            legp(".selectId dl").hide();
            legp(".selectId input").that[0].value = "";
        };
    }
}); };
