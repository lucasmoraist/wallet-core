package com.lucasmoraist.wallet_core;

import com.lucasmoraist.wallet_core.config.RedisTestContainerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(RedisTestContainerConfig.class)
class WalletCoreApplicationTests {

	@Test
	void contextLoads() {
        WalletCoreApplication.main(new String[] {"--spring.profiles.active=test"});
	}

}
