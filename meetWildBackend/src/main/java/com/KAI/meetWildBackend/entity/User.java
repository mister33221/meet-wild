package com.KAI.meetWildBackend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


//@Entity的Bean是告訴Spring這是數據模型層的宣告 像controller那種宣告
@Entity
@Table(	name = "users", 
//			@UniqueConstraint 表示被此宣告的column只能是唯一值  //但這樣的話 若非唯一 該如何通知??
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
//	PK
	@Id
//	自動產生流水號水號
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotNull 和 @NotEmpty 和@NotBlank 區別
//	@NotEmpty 用在集合類上面   如map list array等
//	@NotBlank 用在String上面  
//	@NotNull 用在基本型別上 (及其包裝型別? 應該說除上述之外 都可以用此?)  如 byte short int long float double char boolean 
//	exanple
//	@NotNull(message="訂單id不能為空")
//	@NotBlank(message="orderId不能為空")
//	@NotEmpty(message = "認證項authList不能為空")
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
//	Hibernate Validator 附加的 constraint
//	spring-boot-starter-web 包裡面有 hibernate-validator 包，
//	所以如果開發 web 就不需要重複添加 spring-boot-starter-validation 依賴了。
//	但如果沒用 web 依賴時候想要實現 Bean 驗證，
//	則只要單單加入 spring-boot-starter-validation 依賴即可。
//	@Email	被註解的元素必須是電子郵箱地址
//	@Length	被註解的字符串的大小必須在指定的範圍內
//	@NotEmpty	被註解的字符串的必須非空
//	@Range	被註解的元素必須在合適的範圍內
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	//怎麼沒有寫mappedBy? ex:@ManyToMany(mappedBy="address", fetch=FetchType.EAGER)
	//因為預設是EAGER 在抓取這個資料的時候 會連同user_roles的完整資料都抓出來，會吃效能，為了避免所以改能lzay
	@ManyToMany(fetch = FetchType.LAZY)
	//joinColumns inverseJoinColumns是甚麼?   多對多還不是非常懂 要再研究
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
//	set無序且不重複
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}