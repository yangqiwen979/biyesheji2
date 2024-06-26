package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Goods;
public class GoodsDaoTest extends BaseTest{
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Ignore
	public void testAInsertGoods() {
		Goods goods = new Goods();
		goods.setName("发电机");
		goods.setCode("1");
		goods.setType("test");
		goods.setBrand("test");
		goods.setStandard("test");
		goods.setMaterial("test");
		goods.setBuyPrice("998");
		goods.setSalePrice("1588");
		goods.setGoodsDesc("test");
		goods.setUpdateTime(new Date());
		goods.setPicture("test");
		int insertGoods = goodsDao.insertGoods(goods);
		System.out.println(insertGoods);
	}

	@Ignore
	public void testBDeleteGoods() {
		long goodsId = 3L;
		int deleteGoodsById = goodsDao.deleteGoodsById(goodsId);
		System.out.println(deleteGoodsById);
	}
	
	@Ignore
	public void testCUpdateGoods() {
		Goods goods = new Goods();
		goods.setGoodsId(2L);
		goods.setBrand("品牌");
		goods.setUpdateTime(new Date());
		int updateGoods = goodsDao.updateGoods(goods);
		System.out.println(updateGoods);
	}
	
	@Ignore
	public void testDQueryGoodsById() {
		long goodsId = 1L;
		Goods queryGoodsById = goodsDao.queryGoodsById(goodsId);
		System.out.println(queryGoodsById.getName());
	}
	
	@Ignore
	public void testEQueryGoodsList() {
		Goods goodsCondition = new Goods();
		goodsCondition.setName("发电机");
		goodsCondition.setCode("1");
		List<Goods> queryGoodsList = goodsDao.queryGoodsList(goodsCondition, 0, 10);
		for (Goods goods : queryGoodsList) {
			System.out.println(goods.getName());
		}
	}
	
	@Ignore
	public void testFQueryGoodsCount() {
		Goods goodsCondition = new Goods();
		goodsCondition.setName("汽油发电机");
		goodsCondition.setCode("2");
		int queryGoodsCount = goodsDao.queryGoodsCount(goodsCondition);
		System.out.println(queryGoodsCount);
	}
	@Test
	public void testGetAllGoodsList() {
		List<Goods> queryAllGoodsList = goodsDao.queryAllGoodsList();
		for (Goods goods : queryAllGoodsList) {
			System.out.println(goods.getGoodsId());
		}
	}
}
