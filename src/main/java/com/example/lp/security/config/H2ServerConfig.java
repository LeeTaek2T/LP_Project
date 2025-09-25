package com.example.lp.security.config;

import org.h2.tools.Server;                    // ✅ H2 Server 임포트
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("local")
public class H2ServerConfig {

    // initMethod 대신 직접 start 호출, 종료는 destroyMethod로 stop
    @Bean(destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        Server server = Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-tcpPort", "9092"
        );
        server.start();
        return server;
    }
}
