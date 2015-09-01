package org.sdrc.evm.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the v_esmikshya_userRoleScheme_area database table.
 * 
 */
@Entity
@NamedQuery(name = "V_esmikshya_userRoleScheme_area.findAll", query = "SELECT v FROM V_esmikshya_userRoleScheme_area v")
public class V_esmikshya_userRoleScheme_area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "area_code")
	private String area_Code;

	@Column(name = "Area_ID")
	private String area_ID;

	@Column(name = "Area_Name")
	private String area_Name;

	/**
	 * @return the area_Code
	 */
	public String getArea_Code() {
		return area_Code;
	}

	/**
	 * @param area_Code the area_Code to set
	 */
	public void setArea_Code(String area_Code) {
		this.area_Code = area_Code;
	}

	@Column(name = "Area_NId")
	private Integer Area_NId;

	/**
	 * @return the area_ID
	 */
	public String getArea_ID() {
		return area_ID;
	}

	/**
	 * @param area_ID the area_ID to set
	 */
	public void setArea_ID(String area_ID) {
		this.area_ID = area_ID;
	}

	/**
	 * @return the area_Name
	 */
	public String getArea_Name() {
		return area_Name;
	}

	/**
	 * @param area_Name the area_Name to set
	 */
	public void setArea_Name(String area_Name) {
		this.area_Name = area_Name;
	}

	/**
	 * @return the area_NId
	 */
	public Integer getArea_NId() {
		return Area_NId;
	}

	/**
	 * @param area_NId the area_NId to set
	 */
	public void setArea_NId(Integer area_NId) {
		Area_NId = area_NId;
	}

	/**
	 * @return the area_Parent_NId
	 */
	public Integer getArea_Parent_NId() {
		return Area_Parent_NId;
	}

	/**
	 * @param area_Parent_NId the area_Parent_NId to set
	 */
	public void setArea_Parent_NId(Integer area_Parent_NId) {
		Area_Parent_NId = area_Parent_NId;
	}

	/**
	 * @return the expr1
	 */
	public Integer getExpr1() {
		return expr1;
	}

	/**
	 * @param expr1 the expr1 to set
	 */
	public void setExpr1(Integer expr1) {
		this.expr1 = expr1;
	}

	/**
	 * @return the expr2
	 */
	public Integer getExpr2() {
		return expr2;
	}

	/**
	 * @param expr2 the expr2 to set
	 */
	public void setExpr2(Integer expr2) {
		this.expr2 = expr2;
	}

	/**
	 * @return the expr3
	 */
	public Integer getExpr3() {
		return expr3;
	}

	/**
	 * @param expr3 the expr3 to set
	 */
	public void setExpr3(Integer expr3) {
		this.expr3 = expr3;
	}

	/**
	 * @return the expr4
	 */
	public String getExpr4() {
		return expr4;
	}

	/**
	 * @param expr4 the expr4 to set
	 */
	public void setExpr4(String expr4) {
		this.expr4 = expr4;
	}

	/**
	 * @return the expr5
	 */
	public String getExpr5() {
		return expr5;
	}

	/**
	 * @param expr5 the expr5 to set
	 */
	public void setExpr5(String expr5) {
		this.expr5 = expr5;
	}

	/**
	 * @return the feature_permission_id
	 */
	public Integer getFeature_permission_id() {
		return feature_permission_id;
	}

	/**
	 * @param feature_permission_id the feature_permission_id to set
	 */
	public void setFeature_permission_id(Integer feature_permission_id) {
		this.feature_permission_id = feature_permission_id;
	}

	/**
	 * @return the last_updated_by
	 */
	public String getLast_updated_by() {
		return last_updated_by;
	}

	/**
	 * @param last_updated_by the last_updated_by to set
	 */
	public void setLast_updated_by(String last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	/**
	 * @return the last_updated_date
	 */
	public Timestamp getLast_updated_date() {
		return last_updated_date;
	}

	/**
	 * @param last_updated_date the last_updated_date to set
	 */
	public void setLast_updated_date(Timestamp last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	/**
	 * @return the role_code
	 */
	public String getRole_code() {
		return role_code;
	}

	/**
	 * @param role_code the role_code to set
	 */
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	/**
	 * @return the role_id
	 */
	public Integer getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	/**
	 * @return the role_name
	 */
	public String getRole_name() {
		return role_name;
	}

	/**
	 * @param role_name the role_name to set
	 */
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	/**
	 * @return the role_scheme_id
	 */
	@Id
    @Column(name = "role_scheme_id", nullable = false)
	public Integer getRole_scheme_id() {
		return role_scheme_id;
	}

	/**
	 * @param role_scheme_id the role_scheme_id to set
	 */
	public void setRole_scheme_id(Integer role_scheme_id) {
		this.role_scheme_id = role_scheme_id;
	}

	/**
	 * @return the role_scheme_name
	 */
	public String getRole_scheme_name() {
		return role_scheme_name;
	}

	/**
	 * @param role_scheme_name the role_scheme_name to set
	 */
	public void setRole_scheme_name(String role_scheme_name) {
		this.role_scheme_name = role_scheme_name;
	}

	/**
	 * @return the user_contact_no
	 */
	public String getUser_contact_no() {
		return user_contact_no;
	}

	/**
	 * @param user_contact_no the user_contact_no to set
	 */
	public void setUser_contact_no(String user_contact_no) {
		this.user_contact_no = user_contact_no;
	}

	/**
	 * @return the user_email_id
	 */
	public String getUser_email_id() {
		return user_email_id;
	}

	/**
	 * @param user_email_id the user_email_id to set
	 */
	public void setUser_email_id(String user_email_id) {
		this.user_email_id = user_email_id;
	}

	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return the user_role_scheme_id
	 */
	public Integer getUser_role_scheme_id() {
		return user_role_scheme_id;
	}

	/**
	 * @param user_role_scheme_id the user_role_scheme_id to set
	 */
	public void setUser_role_scheme_id(Integer user_role_scheme_id) {
		this.user_role_scheme_id = user_role_scheme_id;
	}

	@Column(name = "Area_Parent_NId")
	private Integer Area_Parent_NId;

	@Column(name = "Expr1")
	private Integer expr1;

	@Column(name = "Expr2")
	private Integer expr2;

	@Column(name = "Expr3")
	private Integer expr3;

	@Column(name = "Expr4")
	private String expr4;

	@Column(name = "Expr5")
	private String expr5;

	@Column(name = "feature_permission_id")
	private Integer feature_permission_id;

	@Column(name = "last_updated_by")
	private String last_updated_by;

	@Column(name = "last_updated_date")
	private Timestamp last_updated_date;

	@Column(name = "role_code")
	private String role_code;

	@Column(name = "role_id")
	private Integer role_id;

	@Column(name = "role_name")
	private String role_name;

	@Column(name = "role_scheme_id")
	private Integer role_scheme_id;

	@Column(name = "role_scheme_name")
	private String role_scheme_name;

	@Column(name = "user_contact_no")
	private String user_contact_no;

	@Column(name = "user_email_id")
	private String user_email_id;

	@Column(name = "user_id")
	private Integer user_id;

	@Column(name = "user_name")
	private String user_name;

	@Column(name = "user_role_scheme_id")
	private Integer user_role_scheme_id;

	public V_esmikshya_userRoleScheme_area() {
	}

	
}