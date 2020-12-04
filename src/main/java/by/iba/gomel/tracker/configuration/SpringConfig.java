package by.iba.gomel.tracker.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("by.iba.gomel.tracker")
@EnableWebMvc
@EnableTransactionManagement
@Resource()
// интерфейс WebMvcConfigurer имплементируется тогда, когда мы хотим под себя настроить Spring MVC.
//В данном случае вместо стандартного шаблонизаторы, мы хотим использовать шаблонизатор Thymeleaf.
// Именно поэтому мы имплементируем этот интерфейс и опимываем его метод configureViewResolvers - задаем в нем новый шаблонизатор.
public class SpringConfig implements WebMvcConfigurer {

    //Так же мы внедряем ApplicationContext, который используется бином templateResolver, чтобы настроить thymeleaf
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean(destroyMethod = "close")
    // Define database dataSource / connection pool
    public ComboPooledDataSource userTrackerDataSource() throws PropertyVetoException {
        ComboPooledDataSource customerTrackerDataSource = new ComboPooledDataSource();
        customerTrackerDataSource.setDriverClass("com.mysql.jdbc.Driver");
        customerTrackerDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/web_user_tracker?useSSL=false");
        customerTrackerDataSource.setUser("admin");
        customerTrackerDataSource.setPassword("admin");

        // these are connection pool properties for C3P0
        customerTrackerDataSource.setMinPoolSize(5);
        customerTrackerDataSource.setMaxPoolSize(20);
        customerTrackerDataSource.setMaxIdleTime(30000);
        return customerTrackerDataSource;
    }

    @Bean
    @Autowired
    // Setup Hibernate session factory
    public LocalSessionFactoryBean sessionFactory(@Qualifier("userTrackerDataSource") ComboPooledDataSource userTrackerDataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        sessionFactory.setDataSource(userTrackerDataSource);
        sessionFactory.setPackagesToScan("by.iba.gomel.tracker.entity");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    @Autowired
    //      Когда мы работали с Hibernate, мы обычно писали session.beginTransaction(), session.getTransaction().commit()
    //      Spring позволяет исключить этот код из ваших DAO классов благодаря HibernateTransactionManager
    public HibernateTransactionManager transactionManager(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

}
