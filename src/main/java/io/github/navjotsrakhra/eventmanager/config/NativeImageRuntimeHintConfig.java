/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.aot.hint.ProxyHints;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class NativeImageRuntimeHintConfig implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(@NonNull RuntimeHints hints, ClassLoader classLoader) {
        try {
            ProxyHints proxies = hints.proxies();
            proxies.registerJdkProxy(HttpServletRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not register RuntimeHint: " + e.getMessage());
        }
    }
}
