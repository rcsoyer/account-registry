package org.acme.accountregistry.config;

import com.neovisionaries.i18n.CountryCode;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Configuration
class WebMvcConfig {

    @Bean
    Converter<String, CountryCode> countryConverter() {
        return new Converter<>() {
            @Override
            public CountryCode convert(@Nullable final String source) {
                return isNotBlank(source) ? CountryCode.getByCode(source) : null;
            }
        };
    }
}
