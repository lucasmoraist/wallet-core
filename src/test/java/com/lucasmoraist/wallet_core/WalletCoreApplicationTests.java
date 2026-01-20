package com.lucasmoraist.wallet_core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WalletCoreApplicationTests {

	@Test
	void contextLoads() {
        WalletCoreApplication.main(new String[] {"--spring.profiles.active=test"});
	}

}
