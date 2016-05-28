package com.fh.taolijie.component.ds;

import org.apache.tomcat.jdbc.pool.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 动态数据源实现类
 * Created by whf on 5/28/16.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Autowired
    private DataSourceEntry dataSourceEntry;

    /**
     * Tomcat JDBC连接池
     * 此处通过委托模式, 使得动态数据源支持tomcat jdbc连接池
     */
    private DataSource pooledDataSource;


    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceEntry.get();
    }

    public void postDeregister() {
        pooledDataSource.postDeregister();
    }

    public boolean isTestWhileIdle() {
        return pooledDataSource.isTestWhileIdle();
    }

    public Object getDataSource() {
        return pooledDataSource.getDataSource();
    }

    public PoolConfiguration getPoolProperties() {
        return pooledDataSource.getPoolProperties();
    }

    public int getPoolSize() {
        return pooledDataSource.getPoolSize();
    }

    public String getJdbcInterceptors() {
        return pooledDataSource.getJdbcInterceptors();
    }

    public boolean isLogAbandoned() {
        return pooledDataSource.isLogAbandoned();
    }

    public String getUrl() {
        return pooledDataSource.getUrl();
    }

    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
        return pooledDataSource.preRegister(server, name);
    }

    public void setLogAbandoned(boolean logAbandoned) {
        pooledDataSource.setLogAbandoned(logAbandoned);
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return pooledDataSource.getTimeBetweenEvictionRunsMillis();
    }

    public XAConnection getXAConnection(String username, String password) throws SQLException {
        return pooledDataSource.getXAConnection(username, password);
    }

    public void close() {
        pooledDataSource.close();
    }

    public int getNumActive() {
        return pooledDataSource.getNumActive();
    }

    public String getInitSQL() {
        return pooledDataSource.getInitSQL();
    }

    public int getValidationQueryTimeout() {
        return pooledDataSource.getValidationQueryTimeout();
    }

    public PoolProperties.InterceptorDefinition[] getJdbcInterceptorsAsArray() {
        return pooledDataSource.getJdbcInterceptorsAsArray();
    }

    public boolean isRemoveAbandoned() {
        return pooledDataSource.isRemoveAbandoned();
    }

    public String getPassword() {
        return pooledDataSource.getPassword();
    }

    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        pooledDataSource.setAccessToUnderlyingConnectionAllowed(accessToUnderlyingConnectionAllowed);
    }

    public int getSuspectTimeout() {
        return pooledDataSource.getSuspectTimeout();
    }

    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        pooledDataSource.setDefaultTransactionIsolation(defaultTransactionIsolation);
    }

    public long getValidationInterval() {
        return pooledDataSource.getValidationInterval();
    }

    public void setValidationQuery(String validationQuery) {
        pooledDataSource.setValidationQuery(validationQuery);
    }

    public void setPropagateInterruptState(boolean propagateInterruptState) {
        pooledDataSource.setPropagateInterruptState(propagateInterruptState);
    }

    public ConnectionPool createPool() throws SQLException {
        return pooledDataSource.createPool();
    }

    public String getName() {
        return pooledDataSource.getName();
    }

    public boolean isTestOnConnect() {
        return pooledDataSource.isTestOnConnect();
    }

    public int getWaitCount() {
        return pooledDataSource.getWaitCount();
    }

    public void setMinIdle(int minIdle) {
        pooledDataSource.setMinIdle(minIdle);
    }

    public void setDefaultAutoCommit(Boolean autocommit) {
        pooledDataSource.setDefaultAutoCommit(autocommit);
    }

    public int getMaxWait() {
        return pooledDataSource.getMaxWait();
    }

    public void setMaxWait(int maxWait) {
        pooledDataSource.setMaxWait(maxWait);
    }

    public void setTestOnReturn(boolean testOnReturn) {
        pooledDataSource.setTestOnReturn(testOnReturn);
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        pooledDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    }

    public int getAbandonWhenPercentageFull() {
        return pooledDataSource.getAbandonWhenPercentageFull();
    }

    public void postRegister(Boolean registrationDone) {
        pooledDataSource.postRegister(registrationDone);
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        pooledDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    }

    public void testIdle() {
        pooledDataSource.testIdle();
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        pooledDataSource.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
    }

    public void setValidatorClassName(String className) {
        pooledDataSource.setValidatorClassName(className);
    }

    public void setUrl(String url) {
        pooledDataSource.setUrl(url);
    }

    public void setCommitOnReturn(boolean commitOnReturn) {
        pooledDataSource.setCommitOnReturn(commitOnReturn);
    }

    public boolean getUseDisposableConnectionFacade() {
        return pooledDataSource.getUseDisposableConnectionFacade();
    }

    public void setLogValidationErrors(boolean logValidationErrors) {
        pooledDataSource.setLogValidationErrors(logValidationErrors);
    }

    public boolean isJmxEnabled() {
        return pooledDataSource.isJmxEnabled();
    }

    public void setUseDisposableConnectionFacade(boolean useDisposableConnectionFacade) {
        pooledDataSource.setUseDisposableConnectionFacade(useDisposableConnectionFacade);
    }

    public int getRemoveAbandonedTimeout() {
        return pooledDataSource.getRemoveAbandonedTimeout();
    }

    public boolean getRollbackOnReturn() {
        return pooledDataSource.getRollbackOnReturn();
    }

    public void setUseEquals(boolean useEquals) {
        pooledDataSource.setUseEquals(useEquals);
    }

    public boolean isFairQueue() {
        return pooledDataSource.isFairQueue();
    }

    public boolean getLogValidationErrors() {
        return pooledDataSource.getLogValidationErrors();
    }

    public long getMaxAge() {
        return pooledDataSource.getMaxAge();
    }

    public Boolean getDefaultAutoCommit() {
        return pooledDataSource.getDefaultAutoCommit();
    }

    public String getDataSourceJNDI() {
        return pooledDataSource.getDataSourceJNDI();
    }

    public void setPassword(String password) {
        pooledDataSource.setPassword(password);
    }

    public void setMaxAge(long maxAge) {
        pooledDataSource.setMaxAge(maxAge);
    }

    public void setMaxIdle(int maxIdle) {
        pooledDataSource.setMaxIdle(maxIdle);
    }

    public void setIgnoreExceptionOnPreLoad(boolean ignoreExceptionOnPreLoad) {
        pooledDataSource.setIgnoreExceptionOnPreLoad(ignoreExceptionOnPreLoad);
    }

    public void setRollbackOnReturn(boolean rollbackOnReturn) {
        pooledDataSource.setRollbackOnReturn(rollbackOnReturn);
    }

    public int getNumIdle() {
        return pooledDataSource.getNumIdle();
    }

    public void setFairQueue(boolean fairQueue) {
        pooledDataSource.setFairQueue(fairQueue);
    }

    public int getMaxIdle() {
        return pooledDataSource.getMaxIdle();
    }

    public boolean isPoolSweeperEnabled() {
        return pooledDataSource.isPoolSweeperEnabled();
    }

    public Boolean isDefaultAutoCommit() {
        return pooledDataSource.isDefaultAutoCommit();
    }

    public void setAlternateUsernameAllowed(boolean alternateUsernameAllowed) {
        pooledDataSource.setAlternateUsernameAllowed(alternateUsernameAllowed);
    }

    public void setDefaultCatalog(String catalog) {
        pooledDataSource.setDefaultCatalog(catalog);
    }

    public void checkAbandoned() {
        pooledDataSource.checkAbandoned();
    }

    public boolean getCommitOnReturn() {
        return pooledDataSource.getCommitOnReturn();
    }

    public int getIdle() {
        return pooledDataSource.getIdle();
    }

    public String getConnectionProperties() {
        return pooledDataSource.getConnectionProperties();
    }

    public Properties getDbProperties() {
        return pooledDataSource.getDbProperties();
    }

    public void setName(String name) {
        pooledDataSource.setName(name);
    }

    public String getValidatorClassName() {
        return pooledDataSource.getValidatorClassName();
    }

    public void setDataSourceJNDI(String jndiDS) {
        pooledDataSource.setDataSourceJNDI(jndiDS);
    }

    public ConnectionPool getPool() {
        return pooledDataSource.getPool();
    }

    public int getInitialSize() {
        return pooledDataSource.getInitialSize();
    }

    public XAConnection getXAConnection() throws SQLException {
        return pooledDataSource.getXAConnection();
    }

    public int getDefaultTransactionIsolation() {
        return pooledDataSource.getDefaultTransactionIsolation();
    }

    public void purgeOnReturn() {
        pooledDataSource.purgeOnReturn();
    }

    public boolean isTestOnReturn() {
        return pooledDataSource.isTestOnReturn();
    }

    public String getUsername() {
        return pooledDataSource.getUsername();
    }

    public void setDbProperties(Properties dbProperties) {
        pooledDataSource.setDbProperties(dbProperties);
    }

    public int getMinEvictableIdleTimeMillis() {
        return pooledDataSource.getMinEvictableIdleTimeMillis();
    }

    public boolean getPropagateInterruptState() {
        return pooledDataSource.getPropagateInterruptState();
    }

    public void setPoolProperties(PoolConfiguration poolProperties) {
        pooledDataSource.setPoolProperties(poolProperties);
    }

    public void preDeregister() throws Exception {
        pooledDataSource.preDeregister();
    }

    public PooledConnection getPooledConnection(String username, String password) throws SQLException {
        return pooledDataSource.getPooledConnection(username, password);
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        pooledDataSource.setTestOnBorrow(testOnBorrow);
    }

    public void setInitSQL(String initSQL) {
        pooledDataSource.setInitSQL(initSQL);
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        pooledDataSource.setRemoveAbandoned(removeAbandoned);
    }

    public int getMaxActive() {
        return pooledDataSource.getMaxActive();
    }

    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        pooledDataSource.setDefaultReadOnly(defaultReadOnly);
    }

    public int getActive() {
        return pooledDataSource.getActive();
    }

    public int getMinIdle() {
        return pooledDataSource.getMinIdle();
    }

    public void setValidator(Validator validator) {
        pooledDataSource.setValidator(validator);
    }

    public void setJdbcInterceptors(String interceptors) {
        pooledDataSource.setJdbcInterceptors(interceptors);
    }

    public String getDriverClassName() {
        return pooledDataSource.getDriverClassName();
    }

    public int getNumTestsPerEvictionRun() {
        return pooledDataSource.getNumTestsPerEvictionRun();
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        pooledDataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
    }

    public String getValidationQuery() {
        return pooledDataSource.getValidationQuery();
    }

    public Future<Connection> getConnectionAsync() throws SQLException {
        return pooledDataSource.getConnectionAsync();
    }

    public void setMaxActive(int maxActive) {
        pooledDataSource.setMaxActive(maxActive);
    }

    public String getDefaultCatalog() {
        return pooledDataSource.getDefaultCatalog();
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        pooledDataSource.setTestWhileIdle(testWhileIdle);
    }

    public boolean isAlternateUsernameAllowed() {
        return pooledDataSource.isAlternateUsernameAllowed();
    }

    public void setDataSource(Object ds) {
        pooledDataSource.setDataSource(ds);
    }

    public boolean isAccessToUnderlyingConnectionAllowed() {
        return pooledDataSource.isAccessToUnderlyingConnectionAllowed();
    }

    public boolean isTestOnBorrow() {
        return pooledDataSource.isTestOnBorrow();
    }

    public void setTestOnConnect(boolean testOnConnect) {
        pooledDataSource.setTestOnConnect(testOnConnect);
    }

    public void setUseLock(boolean useLock) {
        pooledDataSource.setUseLock(useLock);
    }

    public void setConnectionProperties(String properties) {
        pooledDataSource.setConnectionProperties(properties);
    }

    public void setDriverClassName(String driverClassName) {
        pooledDataSource.setDriverClassName(driverClassName);
    }

    public void setSuspectTimeout(int seconds) {
        pooledDataSource.setSuspectTimeout(seconds);
    }

    public void purge() {
        pooledDataSource.purge();
    }

    public void setValidationInterval(long validationInterval) {
        pooledDataSource.setValidationInterval(validationInterval);
    }

    public int getSize() {
        return pooledDataSource.getSize();
    }

    public ObjectName createObjectName(ObjectName original) throws MalformedObjectNameException {
        return pooledDataSource.createObjectName(original);
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        pooledDataSource.setValidationQueryTimeout(validationQueryTimeout);
    }

    public Boolean getDefaultReadOnly() {
        return pooledDataSource.getDefaultReadOnly();
    }

    public void close(boolean all) {
        pooledDataSource.close(all);
    }

    public boolean getUseLock() {
        return pooledDataSource.getUseLock();
    }

    public boolean isUseEquals() {
        return pooledDataSource.isUseEquals();
    }

    public void checkIdle() {
        pooledDataSource.checkIdle();
    }

    public Boolean isDefaultReadOnly() {
        return pooledDataSource.isDefaultReadOnly();
    }

    public PooledConnection getPooledConnection() throws SQLException {
        return pooledDataSource.getPooledConnection();
    }

    public boolean isIgnoreExceptionOnPreLoad() {
        return pooledDataSource.isIgnoreExceptionOnPreLoad();
    }

    public void setAbandonWhenPercentageFull(int percentage) {
        pooledDataSource.setAbandonWhenPercentageFull(percentage);
    }

    public void setUsername(String username) {
        pooledDataSource.setUsername(username);
    }

    public void setInitialSize(int initialSize) {
        pooledDataSource.setInitialSize(initialSize);
    }

    public void setJmxEnabled(boolean enabled) {
        pooledDataSource.setJmxEnabled(enabled);
    }

    public Validator getValidator() {
        return pooledDataSource.getValidator();
    }

    public String getPoolName() {
        return pooledDataSource.getPoolName();
    }

    public void setPooledDataSource(DataSource pooledDataSource) {
        this.pooledDataSource = pooledDataSource;
    }
}
