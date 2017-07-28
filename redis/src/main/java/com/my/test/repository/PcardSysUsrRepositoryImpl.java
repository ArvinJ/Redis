package com.my.test.repository;


import org.springframework.data.repository.NoRepositoryBean;






import javax.persistence.Query;

@NoRepositoryBean
public class PcardSysUsrRepositoryImpl extends BaseRepository implements PcardSysUsrRepositoryCustom {

//	@PersistenceContext
//	EntityManager entityManager;

	


	
	
	@Override
	public  String getInstChildList(String instId){
		StringBuffer sql = new StringBuffer("select getInstChildList(?1)");
		Query query = entityManager.createNativeQuery(sql.toString());
				query.setParameter(1,instId);
				return query.getSingleResult().toString();
		
	}
	
	@Override
	public  String getInstParentList(String instId){
		StringBuffer sql = new StringBuffer("select getInstParentList(?1)");
		Query query = entityManager.createNativeQuery(sql.toString());
				query.setParameter(1,instId);
				return query.getSingleResult().toString();
		
	}

	@Override
	public String getInstRootInst(String instId) {
		StringBuffer sql = new StringBuffer("select getRootParentList(?1)");
		Query query = entityManager.createNativeQuery(sql.toString());
				query.setParameter(1,instId);
				return query.getSingleResult().toString();
	}


	
	
}
