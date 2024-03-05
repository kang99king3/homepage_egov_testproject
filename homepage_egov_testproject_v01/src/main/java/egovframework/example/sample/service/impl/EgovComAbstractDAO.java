package egovframework.example.sample.service.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

public abstract class EgovComAbstractDAO extends EgovAbstractMapper {

	@Override
	@Resource(name = "egov.sqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}

}
