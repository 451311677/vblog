$(function () {
    $("#labelId").bind("change", function () {
        var labelSelector = document.getElementById("labelId");//获取select对象
        var index = labelSelector.selectedIndex; //获取选项中的索引，selectIndex表示的是当前所选中的index

        var label = document.getElementById("label").value;
        console.log(label)
        console.log(labelSelector.options[index].text);

        if (label.trim().replaceAll("").split(" ").length >= 5) {
            alert("标签最多五个，如需添加请先删除")
        } else {
            console.log("111")
            label = label + " " + labelSelector.options[index].text;
            // $("#label").attr("value", label);
            document.getElementById("label").value=label;
        }


    })
    // $("#labelId").click(labelAdd);
});

function labelAdd() {
    var labelSelector = document.getElementById("labelId");//获取select对象
    var index = labelSelector.selectedIndex; //获取选项中的索引，selectIndex表示的是当前所选中的index
    // console.log(labelSelector.options[index].value);//获取选项中options的value值
    // console.log(labelSelector.options[index].text);//获取选项中options的text值

    var label = document.getElementById("label").value;
    console.log(label)

    label = label + " " + labelSelector.options[index].text;
    $("#label").attr("value", label)
}