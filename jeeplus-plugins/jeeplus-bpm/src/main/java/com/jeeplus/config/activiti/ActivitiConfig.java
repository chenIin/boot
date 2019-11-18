package com.jeeplus.config.activiti;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource(value={"classpath:bpm-plugin.properties"}, encoding = "UTF-8")
public class ActivitiConfig {
    //流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现

    @Value("${activiti.export.diagram.path}")
    private String activitiExportDiagramPath;
    @Value("activiti.diagram.activityFontName")
    private String activitiDiagramActivityFontName;
    @Value("databaseSchemaUpdate")
    private String databaseSchemaUpdate;

    public String getDatabaseSchemaUpdate() {
        return databaseSchemaUpdate;
    }

    public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }

    public String getActivitiDiagramActivityFontName() {
        return activitiDiagramActivityFontName;
    }

    public void setActivitiDiagramActivityFontName(String activitiDiagramActivityFontName) {
        this.activitiDiagramActivityFontName = activitiDiagramActivityFontName;
    }

    public String getActivitiExportDiagramPath() {
        return activitiExportDiagramPath;
    }

    public void setActivitiExportDiagramPath(String activitiExportDiagramPath) {
        this.activitiExportDiagramPath = activitiExportDiagramPath;
    }

    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate(databaseSchemaUpdate);
//        processEngineConfiguration.setDatabaseType("mysql");

        processEngineConfiguration.setTransactionManager(transactionManager);

        //流程图字体
        processEngineConfiguration.setActivityFontName(activitiDiagramActivityFontName);
        processEngineConfiguration.setAnnotationFontName(activitiDiagramActivityFontName);
        processEngineConfiguration.setLabelFontName(activitiDiagramActivityFontName);

        return processEngineConfiguration;
    }

    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    //八大接口
    @Bean
    public RestResponseFactory restResponseFactory() {
        return new RestResponseFactory();
    }

    @Bean
    public ContentTypeResolver contentTypeResolver() {
        return new DefaultContentTypeResolver();
    }


    //八大接口
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
        return processEngine.getDynamicBpmnService();
    }

    //八大接口 end
}
