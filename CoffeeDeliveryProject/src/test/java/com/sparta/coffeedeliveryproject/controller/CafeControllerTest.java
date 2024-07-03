package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.MockSpringSecurityFilter;
import com.sparta.coffeedeliveryproject.config.WebSecurityConfig;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.MenuDto;
import com.sparta.coffeedeliveryproject.dto.MenuSearchCond;
import com.sparta.coffeedeliveryproject.entity.MenuType;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.CafeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = CafeController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class CafeControllerTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @MockBean
    private CafeService cafeService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String userId = "test1234";
        String password = "password";
        String userName = "박강현";
        User testUser = new User(userId, password, userName);
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Test
    @DisplayName("본인이 좋아요한 카페의 메뉴 조회")
    void getUserFavoriteCafe() throws Exception {
        // given
        this.mockUserSetup();

        MenuDto menu1 = new MenuDto(1L, "아메리카노", 2500L, "메가 커피1", MenuType.COFFEE);
        MenuDto menu2 = new MenuDto(2L, "수박주스", 3500L, "메가 커피1", MenuType.JUICE);
        List<MenuDto> menuList = Arrays.asList(menu1, menu2);
        Page<MenuDto> menuPage = new PageImpl<>(menuList, PageRequest.of(0, 5), menuList.size());

        // CafeService의 getUserFavoriteCafe() 메소드를 모킹하여 메뉴 페이지를 반환하도록 설정
        when(cafeService.getUserFavoriteCafe(Mockito.anyInt(), Mockito.any(User.class), Mockito.any(MenuSearchCond.class)))
                .thenReturn(menuPage);

        // when & then
        mvc.perform(get("/cafes/users/favorite")
                        .param("page", "1")
                        .param("menuType", "COFFEE")
                        .param("minPrice", "2000")
                        .param("maxPrice", "4000") // searchCond 관련 내용은 서비스 테스트 시에 검증 예정
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // HTTP 응답 상태가 200인지 확인
                .andExpect(jsonPath("$.content").isArray())  // content가 배열인지 확인
                .andExpect(jsonPath("$.content.length()").value(menuList.size()))  // 반환된 메뉴 개수가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.content[0].menuName").value("아메리카노"))  // 첫 번째 메뉴의 이름이 예상과 일치하는지 확인
                .andExpect(jsonPath("$.content[1].menuName").value("수박주스"))  // 두 번째 메뉴의 이름이 예상과 일치하는지 확인
                .andDo(print());
    }

    @Test
    @DisplayName("좋아요 TOP10 카페 조회")
    void getFavoriteCafe() throws Exception {
        // given
        this.mockUserSetup();

        CafeResponseDto cafe1 = Mockito.mock(CafeResponseDto.class);
        CafeResponseDto cafe2 = Mockito.mock(CafeResponseDto.class);
        CafeResponseDto cafe3 = Mockito.mock(CafeResponseDto.class);

        when(cafe1.getCafeId()).thenReturn(1L);
        when(cafe1.getCafeName()).thenReturn("카페1");
        when(cafe1.getCafeInfo()).thenReturn("카페1입니다");
        when(cafe1.getCafeAddress()).thenReturn("지구");
        when(cafe1.getCafeLikeCount()).thenReturn(10L);

        when(cafe2.getCafeId()).thenReturn(2L);
        when(cafe2.getCafeName()).thenReturn("카페2");
        when(cafe2.getCafeInfo()).thenReturn("카페2입니다");
        when(cafe2.getCafeAddress()).thenReturn("달");
        when(cafe2.getCafeLikeCount()).thenReturn(20L);

        when(cafe3.getCafeId()).thenReturn(3L);
        when(cafe3.getCafeName()).thenReturn("카페3");
        when(cafe3.getCafeInfo()).thenReturn("카페3입니다");
        when(cafe3.getCafeAddress()).thenReturn("별");
        when(cafe3.getCafeLikeCount()).thenReturn(30L);

        List<CafeResponseDto> cafeList = Arrays.asList(cafe1, cafe2, cafe3);
        Page<CafeResponseDto> cafePage = new PageImpl<>(cafeList, PageRequest.of(0, 10), cafeList.size());

        when(cafeService.getFavoriteCafe()).thenReturn(cafePage);

        // when & then
        mvc.perform(get("/cafes/favorite")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // HTTP 응답 상태가 200인지 확인
                .andExpect(jsonPath("$.content").isArray())  // content가 배열인지 확인
                .andExpect(jsonPath("$.content.length()").value(cafeList.size()))  // 반환된 카페 개수가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.content[0].cafeId").value(1L))  // 첫 번째 카페의 ID가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.content[0].cafeName").value("카페1"))  // 첫 번째 카페의 이름이 예상과 일치하는지 확인-
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))  // 현재 페이지 번호가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.pageable.pageSize").value(10))  // 페이지 크기가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.totalElements").value(cafeList.size()))  // 전체 요소 수가 예상과 일치하는지 확인
                .andExpect(jsonPath("$.totalPages").value(1))  // 전체 페이지 수가 예상과 일치하는지 확인
                .andDo(print());  // 테스트 실행 결과를 출력
    }



}