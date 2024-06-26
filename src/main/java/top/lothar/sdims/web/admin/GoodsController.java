package top.lothar.sdims.web.admin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.lothar.sdims.dto.TExecution;
import top.lothar.sdims.entity.Goods;
import top.lothar.sdims.service.GoodsService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.ImageUtils;
import top.lothar.sdims.util.PageBean;
/**
 * 商品
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/admin")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	private PageBean<Goods> pageBean;
	/**
	 * 条件查询商品列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getgoodslist",method=RequestMethod.GET)
	private Map<String, Object> getGoodsList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取每页展示的数据量
		int pageSize = 5;
		// 获取查询条件
		String name = HttpServletRequestUtil.getString(request, "name");
		String code = HttpServletRequestUtil.getString(request, "code");
		String type = HttpServletRequestUtil.getString(request, "type");
		String brand = HttpServletRequestUtil.getString(request, "brand");
		String standard = HttpServletRequestUtil.getString(request, "standard");
		String material = HttpServletRequestUtil.getString(request, "material");
		Goods goodsCondition = new Goods();
		goodsCondition.setName(name);
		goodsCondition.setCode(code);
		goodsCondition.setType(type);
		goodsCondition.setBrand(brand);
		goodsCondition.setStandard(standard);
		goodsCondition.setMaterial(material);
		// 非空判断
		if (pageIndex > -1) {
			TExecution<Goods> goodsExecution = goodsService.getGoodsList(goodsCondition, pageIndex, pageSize);
			pageBean = new PageBean<Goods>();
			// 总记录数
			pageBean.setAllRowCounts(goodsExecution.getCount());
			// 仓库列表
			pageBean.setDatas(goodsExecution.getData());
			// 每页记录数
			pageBean.setPageSize(5);
			// 当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(goodsExecution.getCount(), pageSize);
			// 总页数
			pageBean.setSumPages(sumPages);
			// 定义数组(用于页码展示组件)
			int[] tempNum = new int[sumPages];
			for (int i = 0; i < sumPages; i++) {
				tempNum[i] = i + 1;
			}
			// 页码数字
			pageBean.setNavigatepageNums(tempNum);
			modelMap.put("success", true);
			modelMap.put("pageBean", pageBean);
			modelMap.put("stateInfo", goodsExecution.getStateInfo());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 得到所有商品信息，展示在前台添加点单的select中，用与创建订单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getallgoodslist",method=RequestMethod.GET)
	private Map<String, Object> getAllGoodsList(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Goods> allGoodsList = null;
		try {
			allGoodsList = goodsService.getAllGoodsList();
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			e.printStackTrace();
		}
		modelMap.put("success", true);
		modelMap.put("allGoodsList", allGoodsList);
		return modelMap;
	}
	/**
	 * 添加商品信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addgoods",method=RequestMethod.POST)
	private Map<String, Object> addGoods(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String goodsStr = HttpServletRequestUtil.getString(request, "goodsStr");
		//JSON转化后的仓库存储对象
		Goods goods= null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			goods = objectMapper.readValue(goodsStr, Goods.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (goods!=null) {
				int effectNum = goodsService.addGoods(goods);
				if (effectNum < 1) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "插入失败");
				}else {
					modelMap.put("success", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传的ID移除对应商品
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removegoods",method=RequestMethod.GET)
	private Map<String, Object> removeGoods(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long goodsId = HttpServletRequestUtil.getLong(request, "goodsId");
		try {
			int effectNum = goodsService.removeGoods(goodsId);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "删除失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传递的商品ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getgoodsbyid",method=RequestMethod.GET)
	private Map<String, Object> getGoodsById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long goodsId = HttpServletRequestUtil.getLong(request, "goodsId");
		Goods goods = new Goods();
		try {
			goods = goodsService.getGoodsById(goodsId);
			if (goods!=null) {
				modelMap.put("success", true);
				modelMap.put("goods", goods);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "查询错误");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID进行更新
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifygoods",method=RequestMethod.POST)
	private Map<String, Object> modifyGoods(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String goodsStr = HttpServletRequestUtil.getString(request, "goodsStr");
		//JSON转化后的商品存储对象
		Goods goods = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Goods信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在Employee实体类中
			goods = objectMapper.readValue(goodsStr, Goods.class);

			String path = goods.getPicture();
//			//TODO 保存图片路径
//			InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(path)));
//			String dbpath = ImageUtils.getRandomFileName()+".jpg";
//			OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get("D:\\IDEAsamles\\biyesheji\\sdims\\src\\main\\webapp\\resources\\images" + dbpath))) ;
//			byte [] flush = new byte[1024];
//			int len = 0 ;
//			while(-1!=(len=is.read(flush))){
//				os.write(flush, 0, len);
//			}
//			os.flush();
//			os.close();
//			is.close();
//			//设置新路径
//			goods.setPicture(dbpath);
			//设置更新的时间
			goods.setUpdateTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = goodsService.modifyGoods(goods);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新员工失败");
			}else {
				modelMap.put("success", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	
}
