package flowable;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class ProcessEngine {
    private static org.flowable.engine.ProcessEngine engine = null;

    private static org.flowable.engine.ProcessEngine getEngine(){
       if(java.util.Objects.isNull(engine)) {
           var cfg = new StandaloneProcessEngineConfiguration()
                   .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                   .setJdbcUsername("sa")
                   .setJdbcPassword("")
                   .setJdbcDriver("org.h2.Driver")
                   .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

           engine = cfg.buildProcessEngine();
       }

       return engine;
    }

    public static void setSampleProcess(){
        var repos = getEngine().getRepositoryService();
        var deployment = repos.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        var processDefinition = repos.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    public static RuntimeService getRunrimeService(){
       return getEngine().getRuntimeService();
    }

    public static TaskService getTaskService(){
       return getEngine().getTaskService();
    }

    public static HistoryService getHistoryService() {
        return getEngine().getHistoryService();
    }
}
