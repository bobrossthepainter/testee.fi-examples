package fi.testee.examples.junit5;

import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyRequestScopeBean {
    @Resource(mappedName = "concurrent/test")
    private ExecutorService executorService;

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
