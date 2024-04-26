/*
 *onclick创建角色管理显示页面
 */
function SaleLineStatistics() {
	//得到左侧导航题目
	var SaleStatisticsName = $('#SaleStatisticsLineManager').text();
	var item = {
		'id' : '17',
		'name' : SaleStatisticsName,
		'url' : 'salestatistics.html',
		'closable' : true
	};
	closableTab.addTab(item);
}

function SaleBingStatistics() {
	//得到左侧导航题目
	var SaleStatisticsName = $('#SaleStatisticsBingManager').text();
	var item = {
		'id' : '18',
		'name' : SaleStatisticsName,
		'url' : 'salestatisticsBingtu.html',
		'closable' : true
	};
	closableTab.addTab(item);
}
function SaleYuCeStatistics() {
	//得到左侧导航题目
	var SaleStatisticsName = $('#SaleStatisticsYuCeManager').text();
	var item = {
		'id' : '19',
		'name' : SaleStatisticsName,
		'url' : 'salestatisticsYuCe.html',
		'closable' : true
	};
	closableTab.addTab(item);
}