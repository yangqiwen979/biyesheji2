package top.lothar.sdims.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.EchartsDataDao;
import top.lothar.sdims.dto.EchartsDataBing;
import top.lothar.sdims.entity.EchartsData;
import top.lothar.sdims.entity.PurchaseOrder;
import top.lothar.sdims.entity.SaleOrder;
import top.lothar.sdims.service.EchartsDataService;

@Service
public class EchartsDataServiceimpl implements EchartsDataService {
	
	@Autowired
	private EchartsDataDao echartsDataDao;
	
	@Override
	public List<EchartsData> getEchartsDataList() {
		// TODO Auto-generated method stub
		String startDate;
		String endDate;
		//获取当前时间
		Date date = new Date();
		//当前年份
		String nowYear = new SimpleDateFormat("yyyy").format(date);
		//采购单12个月份的列表
		List<PurchaseOrder> purchaseOrderList = null;
		//存储每个月份数据实体的List
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		//12次循环查询，从01.01--12.31的采购单元组
		for (int i = 1; i <= 12; i++) {
			//每次查到的每月很多的订单算出总值所存放的实体
			EchartsData echartsData = new EchartsData();
			//起始时间当前年份的01-01
			startDate = nowYear+"-"+i+"-01";
			if (i==12) {
				endDate = nowYear+"-"+i+"-31";
			}else {
				endDate = nowYear+"-"+(i+1)+"-01";
			}
			//查询出每月的所有采购单
			purchaseOrderList = echartsDataDao.queryPurchaseOrderListByDate(startDate, endDate);
			//采购总数量
			int totalCount = 0 ;
			//采购总价格
			double totalPrice = 0;
			//遍历每个月份的所有采购单
			for (PurchaseOrder purchaseOrder : purchaseOrderList) {
				//计算出总数和总值
				totalCount = purchaseOrder.getCount()+totalCount;
				totalPrice = purchaseOrder.getTotalPrice()+totalPrice;
			}
			//放进当前实体中
			echartsData.setTotalCount(totalCount);
			echartsData.setTotalPrice(totalPrice);
			//记录在存放数据实体的List中
			echartsDataList.add(echartsData);
		}
		//返回存放 12 个实体的List，每个就是每月的总数和总值
		return echartsDataList;
	}

	@Override
	public List<EchartsData> getSaleOrderEchartsDataList() {
		// TODO Auto-generated method stub
		String startDate;
		String endDate;
		//获取当前时间
		Date date = new Date();
		//当前年份
		String nowYear = new SimpleDateFormat("yyyy").format(date);
		//销售单12个月份的列表
		List<SaleOrder> saleOrderList = null;
		//存储每个月份数据实体的List
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		//12次循环查询，从01.01--12.31的采购单元组
		for (int i = 1; i <= 12; i++) {
			//每次查到的每月很多的订单算出总值所存放的实体
			EchartsData echartsData = new EchartsData();
			//起始时间当前年份的01-01
			startDate = nowYear+"-"+i+"-01";
			if (i==12) {
				endDate = nowYear+"-"+i+"-31";
			}else {
				endDate = nowYear+"-"+(i+1)+"-01";
			}
			//查询出每月的所有采购单
			saleOrderList = echartsDataDao.querySaleOrderListByDate(startDate, endDate);
			//采购总数量
			int totalCount = 0 ;
			//采购总价格
			double totalPrice = 0;
			//遍历每个月份的所有采购单
			for (SaleOrder saleOrder : saleOrderList) {
				//计算出总数和总值
				totalCount = saleOrder.getCount()+totalCount;
				totalPrice = saleOrder.getTotalPrice()+totalPrice;
			}
			//放进当前实体中
			echartsData.setTotalCount(totalCount);
			echartsData.setTotalPrice(totalPrice);
			//记录在存放数据实体的List中
			echartsDataList.add(echartsData);
		}
		//返回存放 12 个实体的List，每个就是每月的总数和总值
		return echartsDataList;
	}

	@Override
	public EchartsDataBing querySaleOrderListByType() {
		//获取当前时间
		Date date = new Date();
		//当前年份
		String nowYear = new SimpleDateFormat("yyyy").format(date);
		String startDate = nowYear+"-"+1+"-01";
		String endDate = nowYear+"-"+12+"-31";
		List<EchartsDataBing> data = echartsDataDao.querySaleOrderListByType(startDate, endDate);

		List<String> legendData = data.stream().map(EchartsDataBing::getType).collect(Collectors.toList());
		List<Double> seriesData = data.stream().map(EchartsDataBing::getTotal).collect(Collectors.toList());

		EchartsDataBing dataBing = new EchartsDataBing();
		dataBing.setLegendData(legendData);
		dataBing.setSeriesData(seriesData);
		return dataBing;
	}

	@Override
	public EchartsDataBing querySaleOrderListYuCe() {


		Date endDay  = new Date();
		Calendar instance = Calendar.getInstance();
		instance.setTime(endDay);
		instance.add(Calendar.YEAR,-1);
		Date star = instance.getTime();
		Date nextDay=star;
		DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		List<Double[]> forecastList = new ArrayList<>();
		List<EchartsDataBing> dataBings = echartsDataDao.querySaleOrderListYuCe(dft.format(star), dft.format(endDay));
		Map<String, List<EchartsDataBing>> map = dataBings.stream().collect(Collectors.groupingBy(EchartsDataBing::getDays));
		int index = 0;
		while(nextDay.before(endDay)){//当明天不在结束时间之前是终止循环
			String format = dft.format(star);
			Double[] doubles = new Double[2];
			doubles[0] = (double) (index+1);
			doubles[1] = 0D;
			if(map.containsKey(format)){
				doubles[1] = map.get(format).get(0).getTotal();
			}
			forecastList.add(index++,doubles);
			Calendar cld = Calendar.getInstance();
			cld.setTime(star);
			cld.add(Calendar.DATE, 1);
			star = cld.getTime();
			nextDay = star;
		}
		EchartsDataBing dataBing = new EchartsDataBing();
		dataBing.setForecastList(forecastList);
		return dataBing;
	}


}
