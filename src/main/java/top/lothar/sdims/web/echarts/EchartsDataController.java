package top.lothar.sdims.web.echarts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.lothar.sdims.dto.EchartsDataBing;
import top.lothar.sdims.entity.EchartsData;
import top.lothar.sdims.service.EchartsDataService;

@Controller
@RequestMapping("/admin")
public class EchartsDataController {
		
	@Autowired
	private EchartsDataService echartsDataService;
	/**
	 * 得到采购单12个月份的采购量和采购总额
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getechartsdatalist",method=RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	private Map<String, Object> getEchartsDataList(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String, Object> echartsDataList = echartsDataService.getEchartsDataList();
			modelMap.put("success", true);
			modelMap.put("echartsDataList", echartsDataList);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", "采购单数据获取错误");
		}
		//跨域请求，用注解实现了
//		response.setHeader("Access-Control-Allow-Origin", "*");
		return modelMap;
	}
	/**
	 * 得到销售单的12个月份的销售单数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getsaleorderechartsdatalist",method=RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	private Map<String, Object> getSaleOrderEchartsDataList(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			Map<String,Object> echartsDataList = echartsDataService.getSaleOrderEchartsDataList();
			modelMap.put("success", true);
			modelMap.put("echartsDataList", echartsDataList);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "采购单数据获取错误");
		}
		return modelMap;
	}

	/**
	   获取每件商品占据的比重
	 */
	@ResponseBody
	@RequestMapping(value="/getsaleorderechartsdatalistBing",method=RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	private Map<String, Object> getsaleorderechartsdatalistBing(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			EchartsDataBing dataBing = echartsDataService.querySaleOrderListByType();
			modelMap.put("success", true);
			modelMap.put("echartsDataList", dataBing);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", "销售单数据获取错误");
		}
		return modelMap;
	}

	/**
	 *  预测 每件商品的销量
	 */
	@ResponseBody
	@RequestMapping(value="/getsaleorderechartsdatalistYuCe",method=RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	private Map<String, Object> getsaleorderechartsdatalistYuCe(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			EchartsDataBing list = echartsDataService.querySaleOrderListYuCe();
			modelMap.put("success", true);
			modelMap.put("echartsDataList", list);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", "销售单数据获取错误");
		}
		return modelMap;
	}
	
}
