package com.KAI.meetWildBackend.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.KAI.meetWildBackend.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	
	//唯一的使用者ID
	private Long id;
	
	//唯一的使用者名稱
	private String username;
	
	//唯一的使用者email
	private String email;
	
//	.@JsonIgnore此注解用于属性或者方法上（最好是属性上），
//	用来完全忽略被注解的字段和方法对应的属性，
//	即便这个字段或方法可以被自动检测到或者还有其
//	他的注解，一般标记在属性或者方法上，返回的json数据即不包含该属性。
//	使用情景：需要把一个List<CustomerInfo >转换成json格式的数据传递给前台。但实体类中基本属性字段的值都存储在快照属性字段中。此时我可以在业务层中做处理，
//	把快照属性字段的值赋给实体类中对应的基本属性字段。
//	最后，我希望返回的json数据中不包含这两个快照字段，
//	那么在实体类中快照属性上加注解@JsonIgnore，
//	那么最后返回的json数据，将不会包含customerId和productId两个属性值。
	//使用者加密後的密碼
	//看不懂
	@JsonIgnore
	private String password;
	
	//使用者的許可權集
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	//目前固定返回true即可
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//目前固定返回true即可
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//目前固定返回true即可
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//目前固定返回true即可
	@Override
	public boolean isEnabled() {
		return true;
	}

	//這要幹嘛?
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	
	
	
}
