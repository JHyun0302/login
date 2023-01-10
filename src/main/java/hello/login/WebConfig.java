package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 스프링 인터셉터 작동을 위해 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //LogInterceptor
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", ",*.ico", "/error");

        //LoginCheckInterceptor
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error");


    }

    /**
     * 서블릿 필터를 작동을 위해 등록
     */
//    @Bean //LogFilter 작동을 위해 필요
    public FilterRegistrationBean logFilter() { //스프링 부트를 사용해 필터 등록하는 방법
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); //우리가 만든 LogFilter.java
        filterRegistrationBean.setOrder(1); //필터 순서(체인 형식이니까)

        filterRegistrationBean.addUrlPatterns("/*"); //모든 url에 적용
        return filterRegistrationBean;
    }

    //    @Bean //LoginCheckFilter 작동을 위해 필요
    public FilterRegistrationBean LoginCheckFilter() { //스프링 부트를 사용해 필터 등록하는 방법
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //우리가 만든 LogFilter.java
        filterRegistrationBean.setOrder(2); //필터 순서(체인 형식이니까)

        filterRegistrationBean.addUrlPatterns("/*"); //모든 url에 적용
        return filterRegistrationBean;
    }
}
