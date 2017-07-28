package com.my.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.my.test.entity.PcardSysUsr;

import java.util.Date;
import java.util.List;

public interface PcardSysUsrRepository extends
		JpaRepository<PcardSysUsr, String>, PcardSysUsrRepositoryCustom {

	PcardSysUsr findByUsrCode(String usrcode);

	@Modifying
	@Query("update PcardSysUsr psu set psu.loginCnt=?1,psu.lastModifyTime=?2 where psu.usrId=?3")
	void updateLoginCnt(long loginCnt, Date date, String userId);
	
	

	
	
    
	
}
