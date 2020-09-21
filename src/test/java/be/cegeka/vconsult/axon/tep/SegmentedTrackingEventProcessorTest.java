package be.cegeka.vconsult.axon.tep;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.eventsourcing.eventstore.GapAwareTrackingToken;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SegmentedTrackingEventProcessorTest.SpringContext.class})
@Transactional
public class SegmentedTrackingEventProcessorTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JpaTokenStore tokenStore;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void testDatabaseSchema() {
        Integer recordCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM DOMAINEVENTENTRY", Integer.class);

        assertThat(recordCount).isZero();
    }

    @Test
    public void testJpaTokenStoreIsEmpty() {
        tokenStore.storeToken(GapAwareTrackingToken.newInstance(0, emptyList()), "abc", 0);

        TrackingToken abc = tokenStore.fetchToken("abc", 0);

        assertThat(abc).isNotNull();

    }


    @ImportResource("classpath:spring/context.xml")
    public static class SpringContext {

        @Bean
        public DataSource dataSourceBuilder() {
            return new EmbeddedDatabaseBuilder()
                    .addScript("classpath:sql/schema.sql")
                    .setType(EmbeddedDatabaseType.HSQL)
                    .setSeparator("GO")
                    .build();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setPersistenceUnitName("axon-tep");
            factory.setDataSource(dataSource);
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            factory.setPackagesToScan("org.axonframework");
            factory.setJpaProperties(jpaProperties());
            return factory;
        }

        @Bean
        public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

        @Bean
        public JpaTokenStore jpaTokenStore(EntityManagerProvider provider) {
            return new JpaTokenStore(provider, new XStreamSerializer());
        }

        private Properties jpaProperties() {
            final Properties properties = new Properties();
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            properties.setProperty("hibernate.show_sql", "false");
            properties.setProperty("hibernate.format_sql", "true");
            return properties;
        }
    }
}
