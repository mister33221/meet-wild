package com.KAI.meetWildBackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity的Bean是告訴Spring這是數據模型層的宣告
@Entity
//@Table註釋指定了Entity所要對映帶資料庫表
@Table(name = "roles")
public class Role {
//	@Id : 是此資料表的Primary Key
	@Id
//	@GeneratedValue : 告訴此Column的生成方式 ,如果設定成GenerationType.AUTO讓容器來自動產生
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	使用@Enumerated註解
//	在儲存列舉時，JPA預設儲存 列舉序號；
//	在序列化列舉時，JPA預設顯示 列舉字面值；
//	可以通過@Enumerated註解切換列舉儲存時實際儲存的行為
//	@Enumerated(EnumType.ORDINAL) - 儲存序號
//	@Enumerated(EnumType.STRING) - 儲存字面值
//	可以通過在列舉屬性上新增@JsonValue註解作為序列化的值；
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
