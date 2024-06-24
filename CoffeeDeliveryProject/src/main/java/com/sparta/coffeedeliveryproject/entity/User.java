package com.sparta.coffeedeliveryproject.entity;

import com.sparta.coffeedeliveryproject.dto.SignupRequestDto;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatusEnum userStatus = UserStatusEnum.ACTIVE;

    @ManyToMany
    @JoinTable(
            name = "user_role_matches",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role")
    )
    private Set<UserRole> userRoles;

    // 컬렉션 객체임을 알려줌
    @ElementCollection
    // pastPasswords의 데이터를 past_passwords 테이블에 저장후에 user_id의 컬럼과 join
    @CollectionTable(name = "past_passwords", joinColumns = @JoinColumn(name = "user_id"))
    // pastPasswords 테이블의 컬렉션 값이 past_password로 저장
    @Column(name = "past_password")
    private List<String> pastPasswords = new ArrayList<>();

    @Column
    private String refreshToken;

    public User(String userName, String password, String nickName) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
    }

    public User(SignupRequestDto signupRequestDto) {
        this.userName = signupRequestDto.getUserName();
        this.password = signupRequestDto.getPassword();
        this.nickName = signupRequestDto.getNickName();
    }

    public void addUserRoles(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public void editUserNickName(String nickName) {
        this.nickName = nickName;
    }

    public void editUserPassword(String password) {
        this.password = password;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public void editRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
