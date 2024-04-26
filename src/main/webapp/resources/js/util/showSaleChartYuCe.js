
let myChart =null;
$(function() {

	initChart();
})

function initChart(){
	myChart = echarts.init(document.getElementById('saleYuCe'));
	myChart.showLoading();
	// 获取数据
	getData();
	myChart.hideLoading();
	// 配置项
	const option = {
		title:{

		},
		legend: {
			bottom: 5
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'cross'
			}
		},
		xAxis: {
			splitLine: {
				lineStyle: {
					type: 'dashed'
				}
			}
		},
		yAxis: {
			splitLine: {
				lineStyle: {
					type: 'dashed'
				}
			}
		},
		series: [
			{
				name: '原始销量',
				type: 'scatter',
			},
			{
				name: '预测销量',
				type: 'line',
				datasetIndex: 1,
				symbolSize: 0.1,
				symbol: 'circle',
				label: { show: true, fontSize: 16 },
				labelLayout: { dx: -20 },
				encode: { label: 2, tooltip: 1 }
			},
			]
	};
	setTimeout(function (){
		echarts.registerTransform(ecStat.transform.regression);
		myChart.setOption(option);
	})

}
function getData(){
	// const data =[[0, 10], [1, 12], [2, 20], [3, 25], [4, 31], [5, 38]];
	let dataSource =[];
	const  dataList =[];
	// 后台数据地址
	var echartsDataUrl = "/sdims/admin/getsaleorderechartsdatalistYuCe";
	let titleStr = "";
	// 通过Ajax获取数据
	$.ajax({
		type : "get",
		async : false, // 同步执行
		url : echartsDataUrl,
		data : {},
		dataType : "json", // 返回数据形式为json
		success : function(data) {
			if (data.success) {
				dataSource = data.echartsDataList.forecastList;
				for (var i = 0; i < dataSource.length; i++) {
					dataList.push(dataSource[i][1]);
				}
				dataList.sort((a,b) => a-b)
				var varianceValue = ecStat.statistics.sampleVariance(dataList); // 方差
				var sampleDeviation = ecStat.statistics.deviation(dataList); // 标准差
				var medianValue = ecStat.statistics.median(dataList); // 中位数
				var meanValue = ecStat.statistics.mean(dataList); // 平均值
				const titleArr =[];
				titleArr.push("方差-" + varianceValue);
				titleArr.push("标准差-" + sampleDeviation);
				titleArr.push("中位数-" + medianValue);
				titleArr.push("平均值-" + meanValue);
				titleStr = titleArr.join(" ");
				console.info(titleArr)
			} else {
				alert("获取数据失败");
			}
		}
	});
	myChart.setOption({
		title:{
			text: '销量线性回归图',
			subtext: titleStr,
			left: 'center'
		},
		dataset: [
			{
				source: dataSource
			},
			{
				transform: {
					type: 'ecStat:regression'
				}
			}
		],

	})

}