package top.lothar.sdims.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import top.lothar.sdims.dto.EchartsDataBing;
import top.lothar.sdims.entity.EchartsData;

public interface EchartsDataService {
	/**
	 * 得到12月份的12个存储总数和总额的实体-采购单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Map<String, Object> getEchartsDataList();
	/**
	 * 得到12月份的12个存储总数和总额的实体-销售单
	 * @return
	 */
	Map<String,Object> getSaleOrderEchartsDataList();

	EchartsDataBing querySaleOrderListByType();

	EchartsDataBing querySaleOrderListYuCe() throws ParseException;
}
