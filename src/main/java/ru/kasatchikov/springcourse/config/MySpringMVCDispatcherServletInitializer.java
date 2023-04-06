package ru.kasatchikov.springcourse.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMVCDispatcherServletInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    // Этот метод использовать не будем
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    /* Равносильно тому, как мы указывали в web.xml путь к
       ApplicationContextMVC.xml, здесь мы должны указать
       путь к SpringConfig классу
    */
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    /* "/" - эквивалентно тому, что мы все запросы отправляем
       на DispatcherServlet
     */
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override // метод запускается на старте приложения, здесь мы выполняем приватный метод
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }
    // в этом методе мы вызываем фильтр
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}
