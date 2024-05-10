//默认引入js文件时，执行函数
//-----------------饼图数据--------------------
var bingtuChar = null;
optionBing = {
	title: {
		text: '商品销量比重图',
		left: 'center'
	},
	tooltip: {
		trigger: 'item',
		formatter: '{a} <br/>{b} : {c} ({d}%)'
	},
	legend: {
		type: 'scroll',
		orient: 'vertical',
		right: 10,
		top: 20,
		bottom: 20,
		// data: data.legendData
	},
	series: [
		{
			name: '商品类型',
			type: 'pie',
			radius: '55%',
			center: ['40%', '50%'],
			// data: data.seriesData,
			label: {
				show: true,
				position: 'outside', // 设置标签在饼图外部
				formatter: '{b}: :{c} :{d}%' // 设置标签的显示格式，{b} 是数据项的名称，{d} 是百分比
			},
			emphasis: {
				itemStyle: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}
	]
};

$(function() {
	var element = document.getElementById('saleBing');
	element.style.width = '100%';
	bingtuChar= echarts.init(element);
	bingtuChar.showLoading({
		text : "采购数据正在努力加载..."
	});
	// 设置图表信息
	bingtuChar.setOption(optionBing);
	// 隐藏加载提示
	bingtuChar.hideLoading();
	getBingTuCharData();



});
// 从后台得到数据
function getBingTuCharData(){

	// 后台数据地址
	var echartsDataUrl = "/sdims/admin/getsaleorderechartsdatalistBing";
	$.ajax({
		type : "get",
		async : false, // 同步执行
		url : echartsDataUrl,
		data : {},
		dataType : "json", // 返回数据形式为json
		success : function(data) {
			if (data.success) {
				console.info(data.echartsDataList.legendData)
				console.info(data.echartsDataList.seriesData)
				const legendData = [];
				const seriesData = [];
				for (var i = 0; i < data.echartsDataList.legendData.length; i++) {
					legendData.push(data.echartsDataList.legendData[i]);
					seriesData.push({
						name: legendData[i],
						value:  data.echartsDataList.seriesData[i]
					});
				}
				bingtuChar.setOption({
					legend:{
						data: legendData
					},
					series: [
						{
							data: seriesData
						}
					]
				})
			} else {
				alert("获取数据失败");
			}
		}
	});
}