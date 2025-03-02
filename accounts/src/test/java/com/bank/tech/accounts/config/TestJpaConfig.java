package com.bank.tech.accounts.config;

import com.bank.tech.accounts.audit.AuditAwareImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
/*@EnableJpaAuditing(auditorAwareRef = "AuditAwareImp")*/
public class TestJpaConfig {
  /*  @Bean
    public AuditorAware<String> auditAwareImp() {
        return new AuditAwareImp();
    }*/
}