package ru.improve.abs.service.configuration.security.aclConfig;

//@RequiredArgsConstructor
//@Configuration
public class AclConfig {

    /*private final CacheConfig cacheConfig;

    private final AclDataSourceConfig aclDataSourceConfig;

    @Bean
    public DataSource aclDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(aclDataSourceConfig.getUrl());
        dataSource.setUsername(aclDataSourceConfig.getUsername());
        dataSource.setPassword(aclDataSourceConfig.getPassword());
        dataSource.setDriverClassName(aclDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Bean
    public ConsoleAuditLogger auditLogger() {
        return new ConsoleAuditLogger();
    }

    @Bean
    public DefaultPermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(auditLogger());
    }

    @Bean
    public AclAuthorizationStrategyImpl aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(ADMIN_ROLE));
    }

    @Bean
    public SpringCacheBasedAclCache cacheBasedAclCache(CacheManager cacheManager) {
        return new SpringCacheBasedAclCache(
                cacheManager.getCache(cacheConfig.getCacheNames().get(0)),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy()
        );
    }

    @Bean
    public BasicLookupStrategy lookupStrategy(DataSource dataSource, SpringCacheBasedAclCache cacheBasedAclCache) {
        BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(
                dataSource,
                cacheBasedAclCache,
                aclAuthorizationStrategy(),
                permissionGrantingStrategy()
        );
        lookupStrategy.setAclClassIdSupported(true);
        return lookupStrategy;
    }

    @Bean
    public MutableAclService mutableAclService(DataSource dataSource, CacheManager cacheManager) {
        SpringCacheBasedAclCache cacheBasedAclCache = cacheBasedAclCache(cacheManager);
        BasicLookupStrategy lookupStrategy = lookupStrategy(dataSource, cacheBasedAclCache);

        JdbcMutableAclService mutableAclService = new JdbcMutableAclService(
                dataSource,
                lookupStrategy,
                cacheBasedAclCache
        );
        mutableAclService.setSidIdentityQuery("select currval('acl_sid_id_seq')");
        mutableAclService.setClassIdentityQuery("select currval('acl_class_id_seq')");
        mutableAclService.setAclClassIdSupported(true);

        return mutableAclService;
    }*/
}
