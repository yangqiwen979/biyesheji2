package top.lothar.sdims.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import top.lothar.sdims.BaseTest;
import top.lothar.sdims.entity.Employee;
import top.lothar.sdims.entity.Repository;

public class RepositoryDaoTest extends BaseTest{
	
	@Autowired
	private RepositoryDao repositoryDao;
	
	@Ignore
	public void testAInsertRepository() {
		Employee employee = new Employee();
		employee.setEmployeeId(6L);
		Repository repository = new Repository();
		repository.setName("仓库");
		repository.setCode("01");
		repository.setAddress("大连");
		repository.setRepoDesc("库存充足");
		repository.setUpdater("张三");
		repository.setUpdateTime(new Date());
		repository.setEmployee(employee);	
		int effectNum = repositoryDao.insertRepository(repository);
		System.out.println(effectNum);
	}
	@Ignore
	public void testBDeleteRepositoryById() {
		int repositoryId = 2;
		int effectNum = repositoryDao.deleteRepositoryById(repositoryId);
		System.out.println(effectNum);
	}
	@Ignore
	public void testCUpdateRepository() {
		Employee employee = new Employee();
		employee.setEmployeeId(6L);
		Repository repository = new Repository();
		repository.setRepoId(3L);
		repository.setAddress("北京");
		repository.setUpdateTime(new Date());
		repository.setEmployee(employee);
		int effectNum = repositoryDao.updateRepository(repository);
		System.out.println(effectNum);
	}
	@Ignore
	public void testDQueryRepositoryList() {
		List<Repository> repositoryList = repositoryDao.queryRepositoryList(null, 0, 2);
		for (Repository repository : repositoryList) {
			System.out.println(repository.getName());
		}
	}
	@Ignore
	public void testEQueryRepositoryCount() {
		int effectNum = repositoryDao.queryRepositoryCount(null);
		System.out.println("共有"+effectNum+"条");
	}
	@Ignore
	public void testFQueryRepositoryById() {
		long repoId = 1L;
		Repository queryRepositoryById = repositoryDao.queryRepositoryById(repoId);
		System.out.println(queryRepositoryById.getEmployee().getName());
	}
	
	@Test
	public void testGetRepositoryList() {
		List<Repository> allRepositoryList = repositoryDao.queryAllRepositoryList();
		for (Repository repository : allRepositoryList) {
			System.out.println(repository.getRepoId());
		}
	}

}
