package com.sparta.coffeedeliveryproject;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.coffeedeliveryproject.config.QueryDSLConfig;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@DataJpaTest
@ActiveProfiles("test")
@Import(QueryDSLConfig.class)
public class H2DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    public void testDataSource() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getMetaData().getURL()).isEqualTo("jdbc:h2:mem:test");
            assertThat(connection.getMetaData().getUserName()).isEqualTo("SA");
        }
    }

    @Test
    public void testCafeRepository() {
        Cafe cafe = new Cafe("Test Cafe", "Test Info", "Test Address");
        cafeRepository.save(cafe);
        assertThat(cafeRepository.findAll()).hasSize(1);
    }
}
