package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * argumentResolver 작동을 위해 등록
     */
/*    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }*/

    /**
     * 스프링 인터셉터 작동을 위해 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //LogInterceptor
        registry.addInterceptor(new LogInterceptor())
                .order(1) //인터셉터 호출 순서
                .addPathPatterns("/**") //인터셉터를 적용할 URL 패턴
                .excludePathPatterns("/css/**", ",*.ico", "/error"); //인터셉터에서 제외할 패턴

        //LoginCheckInterceptor
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2) //인터셉터 호출 순서
                .addPathPatterns("/**") //인터셉터를 적용할 URL 패턴
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"); //인터셉터에서 제외할 패턴
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

    @Autowired
    LoginCheckFilter loginCheckFilter; //스프링 빈으로 의존관계 주입 형식

    @Bean //LoginCheckFilter 작동을 위해 필요
    public FilterRegistrationBean LoginCheckFilter() { //스프링 부트를 사용해 필터 등록하는 방법
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(loginCheckFilter); //우리가 만든 LoginCheckFilter.java
        filterRegistrationBean.setOrder(2); //필터 순서(체인 형식이니까)

        filterRegistrationBean.addUrlPatterns("/*"); //모든 url에 적용
        return filterRegistrationBean;
    }
}
