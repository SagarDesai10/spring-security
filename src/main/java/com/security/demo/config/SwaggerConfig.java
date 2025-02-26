package com.security.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@OpenAPIDefinition(
		info= @Info(
			title="Spring - sec",
			description="Spring_Sec API",
			termsOfService="T&C",
			contact = @Contact(name="Spring-sec",
							   email="developer@SpringSec.com",
							   url="https://springsec.com"),
			license=@License(name="Apache 2.0",
							 url="http://www.apache.org/licenses/LICENSE-2.0"),
			version="v1"
			),

		security=@SecurityRequirement(name="Security")

	)
	@SecurityScheme(name="Security", in=SecuritySchemeIn.HEADER, type=SecuritySchemeType.HTTP, bearerFormat="JWT", scheme="bearer")
public class SwaggerConfig {

}
