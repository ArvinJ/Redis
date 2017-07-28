
package com.my.test.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *系统用户表 7
 * 
 * 
 * ****/

@Entity
@Table(name="tbl_pcard_sys_usr")
public class PcardSysUsr implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="usr_id")
	private String usrId ;//用户ID
	@Column(name="usr_code")
	private String usrCode ;//用户代码（登陆名）
	@Column(name="usr_nm")
	private String usrNm ;// 用户姓名
	@Column(name="usr_mnt")
	private String usrMnt ;// 用户所属商户
	@Column(name="usr_role")
	private String usrRole ;// 用户角色
	@Column(name="usr_passwd")
	private String usrPasswd ;// 用户密码
	@Column(name="create_time")
	private Date createTime ; //创建时间 
	@Column(name="last_modify_time")
	private Date lastModifyTime ;// 最后修改时间
	@Column(name="st")
	private String st;//状态(0:可用,1:不可用)
	@Column(name="login_cnt")
	private Integer loginCnt;//登录次数
	@Column(name="usr_identity")
	private String usrIdentity;//用户身份 1 管理员 2商户 3卡商

	@Column(name="password_reset")
	private Integer passwordReset;//密码是否被重置（0：初始，1：重置）
	
	@Column(name="usr_demo")
	private String usrDemo;//备注
	
	@Column(name="passwd_expire_time")
	private Date passwdExpireTime;//密码到期时间
	
	@Column(name="usr_uk_id")
	private String usrUkId;//ukid
	
	
	public String getUsrUkId() {
		return usrUkId;
	}
	public void setUsrUkId(String usrUkId) {
		this.usrUkId = usrUkId;
	}
	public Date getPasswdExpireTime() {
		return passwdExpireTime;
	}
	public void setPasswdExpireTime(Date passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}
	public String getUsrDemo() {
		return usrDemo;
	}
	public void setUsrDemo(String usrDemo) {
		this.usrDemo = usrDemo;
	}
	public Integer getPasswordReset() {
		return passwordReset;
	}
	public void setPasswordReset(Integer passwordReset) {
		this.passwordReset = passwordReset;
	}
	public Integer getLoginCnt() {
		return loginCnt;
	}
	public void setLoginCnt(Integer loginCnt) {
		this.loginCnt = loginCnt;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getUsrCode() {
		return usrCode;
	}
	public void setUsrCode(String usrCode) {
		this.usrCode = usrCode;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getUsrMnt() {
		return usrMnt;
	}
	public void setUsrMnt(String usrMnt) {
		this.usrMnt = usrMnt;
	}
	public String getUsrRole() {
		return usrRole;
	}
	public void setUsrRole(String usrRole) {
		this.usrRole = usrRole;
	}
	public String getUsrPasswd() {
		return usrPasswd;
	}
	public void setUsrPasswd(String usrPasswd) {
		this.usrPasswd = usrPasswd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getUsrIdentity() {
		return usrIdentity;
	}
	public void setUsrIdentity(String usrIdentity) {
		this.usrIdentity = usrIdentity;
	}
	
}

