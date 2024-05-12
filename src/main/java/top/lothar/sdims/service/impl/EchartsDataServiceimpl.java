package top.lothar.sdims.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;
import org.apache.commons.collections.ArrayStack;
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
	public Map<String, Object> getEchartsDataList() {
		Date endDay = new Date();//yuan
		Calendar instance = Calendar.getInstance();
		instance.setTime(endDay);
		instance.add(Calendar.YEAR, -1);
		Date startDate = instance.getTime();
		DateFormat dft = new SimpleDateFormat("yyyy-MM");
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		//采购单12个月份的列表
		List<PurchaseOrder> purchaseOrderList = null;
		//存储每个月份数据实体的List
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		Calendar cld = Calendar.getInstance();
		cld.setTime(startDate);
		List<String> dateList = new ArrayList<>();
		while (cld.getTime().before(endDay)) {
			String format = yearFormat.format(cld.getTime());
			int i = cld.get(Calendar.MONTH);
			dateList.add(format + "-" + (i+1)) ;
			String startDay = format + "-" + (i + 1) + "-01";
			String endDayy = null;
			if (i == 11) {
				endDayy = format + "-" + (i + 1) + "-31";
			}else {
				endDayy = format + "-" + (i + 2) + "-01";
			}
			if (i < 11) {
				cld.add(Calendar.MONTH, 1);
			} else {
				cld.add(Calendar.YEAR, 1);
				cld.set(Calendar.MONTH, 0);
			}

			EchartsData echartsData = new EchartsData();
			//查询出每月的所有采购单
			purchaseOrderList = echartsDataDao.queryPurchaseOrderListByDate(startDay, endDayy);
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
		Map<String, Object> map = new HashMap<>();
		map.put("data", echartsDataList);
		map.put("date", dateList);
		//返回存放 12 个实体的List，每个就是每月的总数和总值
		return map;
	/*	// TODO Auto-generated method stub
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
		return echartsDataList;*/
	}

	@Override
	public Map<String, Object> getSaleOrderEchartsDataList() {
		// TODO Auto-generated method stub

//		String startDate;
//		String endDate;
//		//获取当前时间
//		Date date = new Date();
//		//当前年份
//		String nowYear = new SimpleDateFormat("yyyy").format(date);
// 	String lastYear = String.valueOf(Integer.getInteger(nowYear) - 1);// 去年
		Date endDay = new Date();//yuan
		Calendar instance = Calendar.getInstance();
		instance.setTime(endDay);
		instance.add(Calendar.YEAR, -1);
		Date startDate = instance.getTime();
		DateFormat dft = new SimpleDateFormat("yyyy-MM");
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		//销售单12个月份的列表
		List<SaleOrder> saleOrderList = null;
		//存储每个月份数据实体的List
		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
		Calendar cld = Calendar.getInstance();
		cld.setTime(startDate);
		List<String> dateList = new ArrayList<>();
		while (cld.getTime().before(endDay)) {
			String format = yearFormat.format(cld.getTime());
			int i = cld.get(Calendar.MONTH);
			dateList.add(format + "-" + (i+1)) ;
			String startDay = format + "-" + (i + 1) + "-01";
			String endDayy = null;
			if (i == 11) {
				endDayy = format + "-" + (i + 1) + "-31";
			}else {
				endDayy = format + "-" + (i + 2) + "-01";
			}
			if (i < 11) {
				cld.add(Calendar.MONTH, 1);
			} else {
				cld.add(Calendar.YEAR, 1);
				cld.set(Calendar.MONTH, 0);
			}
			EchartsData echartsData = new EchartsData();
			saleOrderList = echartsDataDao.querySaleOrderListByDate(startDay, endDayy);
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
		Map<String, Object> map = new HashMap<>();
		map.put("data", echartsDataList);
		map.put("date", dateList);
		//返回存放 12 个实体的List，每个就是每月的总数和总值
		return map;
		//12次循环查询，从01.01--12.31的采购单元组
//		for (int i = 1; i <= 12; i++) {
//			//每次查到的每月很多的订单算出总值所存放的实体
//			EchartsData echartsData = new EchartsData();
//			//起始时间当前年份的01-01
//			startDate = nowYear+"-"+i+"-01";
//			if (i==12) {
//				endDate = nowYear+"-"+i+"-31";
//			}else {
//				endDate = nowYear+"-"+(i+1)+"-01";
//			}
//
//			//查询出每月的所有采购单
//			saleOrderList = echartsDataDao.querySaleOrderListByDate(startDate, endDate);
//			//采购总数量
//			int totalCount = 0 ;
//			//采购总价格
//			double totalPrice = 0;
//			//遍历每个月份的所有采购单
//			for (SaleOrder saleOrder : saleOrderList) {
//				//计算出总数和总值
//				totalCount = saleOrder.getCount()+totalCount;
//				totalPrice = saleOrder.getTotalPrice()+totalPrice;
//			}
//			//放进当前实体中
//			echartsData.setTotalCount(totalCount);
//			echartsData.setTotalPrice(totalPrice);
//			//记录在存放数据实体的List中
//			echartsDataList.add(echartsData);
//		}
//		//返回存放 12 个实体的List，每个就是每月的总数和总值
//		return echartsDataList;
//		// TODO Auto-generated method stub
//		String startDate;
//		String endDate;
//		//获取当前时间
//		Date date = new Date();
//		//当前年份
//		String nowYear = new SimpleDateFormat("yyyy").format(date);
//		//销售单12个月份的列表
//		List<SaleOrder> saleOrderList = null;
//		//存储每个月份数据实体的List
//		List<EchartsData> echartsDataList = new ArrayList<EchartsData>();
//		//12次循环查询，从01.01--12.31的采购单元组
//		for (int i = 1; i <= 12; i++) {
//			//每次查到的每月很多的订单算出总值所存放的实体
//			EchartsData echartsData = new EchartsData();
//			//起始时间当前年份的01-01
//			startDate = nowYear+"-"+i+"-01";
//			if (i==12) {
//				endDate = nowYear+"-"+i+"-31";
//			}else {
//				endDate = nowYear+"-"+(i+1)+"-01";
//			}
//			//查询出每月的所有采购单
//			saleOrderList = echartsDataDao.querySaleOrderListByDate(startDate, endDate);
//			//采购总数量
//			int totalCount = 0 ;
//			//采购总价格
//			double totalPrice = 0;
//			//遍历每个月份的所有采购单
//			for (SaleOrder saleOrder : saleOrderList) {
//				//计算出总数和总值
//				totalCount = saleOrder.getCount()+totalCount;
//				totalPrice = saleOrder.getTotalPrice()+totalPrice;
//			}
//			//放进当前实体中
//			echartsData.setTotalCount(totalCount);
//			echartsData.setTotalPrice(totalPrice);
//			//记录在存放数据实体的List中
//			echartsDataList.add(echartsData);
//		}
//		//返回存放 12 个实体的List，每个就是每月的总数和总值
//		return echartsDataList;
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
	public EchartsDataBing querySaleOrderListYuCe() throws ParseException {


		Date endDay = new Date();//yuan
		Calendar instance = Calendar.getInstance();
		instance.setTime(endDay);
		instance.add(Calendar.YEAR, -1);
		Date startDate = instance.getTime();
		DateFormat dft = new SimpleDateFormat("yyyy-MM");

		List<EchartsDataBing> dataBings = echartsDataDao.querySaleOrderListYuCe(dft.format(startDate), dft.format(endDay));
		Map<String, List<EchartsDataBing>> map = dataBings.stream().collect(Collectors.groupingBy(EchartsDataBing::getDays));

		List<Double[]> forecastList = new ArrayList<>();
		Calendar cld = Calendar.getInstance();
		cld.setTime(startDate);
		int index = 0;
		List<Pair<String, Double>> retList = new ArrayList<>();
		while (cld.getTime().before(endDay)) {
			String format = dft.format(cld.getTime());
			Double[] doubles = new Double[2];
			doubles[0] = (double) (index);
			doubles[1] = 0D;
			if (map.containsKey(format)) {
				List<EchartsDataBing> list = map.get(format);
				if (!list.isEmpty()) {
					doubles[1] = list.get(0).getTotal();
				}
			}
			forecastList.add(doubles);
			Pair<String, Double> pair = new Pair<String, Double>(format, doubles[1]);
			retList.add(pair);
			index++;
			cld.add(Calendar.MONTH, 1);
		}

		EchartsDataBing dataBing = new EchartsDataBing();
		dataBing.setForecastList(forecastList);
		return dataBing;


/*		Date endDay = new Date();//xin
// 设置日期格式为月份
		DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
// 获取开始和结束月份（假设endDay是某个日期的实例）
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(endDay);
		startCal.add(Calendar.MONTH, -12); // 假设我们要过去12个月的数据

		Date startDateMonth = monthFormat.parse(monthFormat.format(startCal.getTime()));
		Date endDateMonth = monthFormat.parse(monthFormat.format(endDay));

// 从数据库中查询按月分组的数据
		List<EchartsDataBing> monthlyDataBings = echartsDataDao.querySaleOrderListYuCe(monthFormat.format(startDateMonth), monthFormat.format(endDateMonth));

// 按月份分组数据
		Map<String, List<EchartsDataBing>> monthlyMap = monthlyDataBings.stream().collect(Collectors.groupingBy(bing -> monthFormat.format(bing.getDays())));

// 初始化预测列表
		List<Double[]> forecastList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDateMonth);
		int index = 0;

// 按月迭代
		while (cal.getTime().before(endDateMonth) || cal.getTime().equals(endDateMonth)) {
			String month = monthFormat.format(cal.getTime());
			Double[] monthData = new Double[2];
			monthData[0] = (double) (index + 1); // 索引或月份（取决于你的需求）
			monthData[1] = 0D; // 默认销量为0

			if (monthlyMap.containsKey(month)) {
				List<EchartsDataBing> list = monthlyMap.get(month);
				if (!list.isEmpty()) {
					// 假设我们只关心每个月的总销量，所以取第一个或聚合它们
					monthData[1] = list.stream().mapToDouble(EchartsDataBing::getTotal).sum();
				}
			}

			forecastList.add(monthData);
			index++;
			cal.add(Calendar.MONTH, 1);
		}

// 封装数据并返回
		EchartsDataBing dataBing = new EchartsDataBing();
		dataBing.setForecastList(forecastList);
		return dataBing;*/
	}
}
