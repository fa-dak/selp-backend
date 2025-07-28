package org.fadak.selp.selpbackend.configuration.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        try (var serviceAccount = classLoader.getResourceAsStream(
            "firebase/firebaseAdminSdk.json")) {
            if (serviceAccount == null) {
                throw new IllegalArgumentException("firebaseAdminSdk.json not found in classpath");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("FirebaseApp 초기화 완료");
            }

            return FirebaseMessaging.getInstance();
        }
    }
}