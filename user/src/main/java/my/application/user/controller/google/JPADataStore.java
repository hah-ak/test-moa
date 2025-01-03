package my.application.user.controller.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import my.application.gateway.entities.google.CredentialToken;
import my.application.user.repositories.google.CredentialTokenRepository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

public class JPADataStore extends AbstractDataStore<StoredCredential> {
    private final CredentialTokenRepository credentialTokenRepository;

    protected JPADataStore(JPADataStoreFactory dataStoreFactory, String id, CredentialTokenRepository credentialTokenRepository) {
        super(dataStoreFactory, id);
        this.credentialTokenRepository = credentialTokenRepository;
    }

    @Override
    public Set<String> keySet() throws IOException {
        HashSet<String> strings = new HashSet<>();
        for (CredentialToken credentialToken : credentialTokenRepository.findAll()) {
            strings.add(credentialToken.getKey());
        }
        return strings;
    }

    @Override
    public Collection<StoredCredential> values() throws IOException {
        return Lists.newArrayList(credentialTokenRepository.findAll()).stream().map(CredentialToken::getStoredCredential).toList();
    }

    @Override
    public StoredCredential get(String key) throws IOException {
        Optional<CredentialToken> byId = credentialTokenRepository.findById(key);
        return byId.map(CredentialToken::getStoredCredential).orElse(null);
    }

    @Override
    public DataStore<StoredCredential> set(String key, StoredCredential value) throws IOException {
        if (StringUtils.hasText(value.getRefreshToken())) {
            credentialTokenRepository.findById(key).ifPresent(credentialToken -> value.setRefreshToken(Objects.requireNonNull(credentialToken.getStoredCredential().getRefreshToken())));
        }
        CredentialToken save = credentialTokenRepository.save(new CredentialToken(key, value));
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() throws IOException {
        credentialTokenRepository.deleteAll();
        return this;
    }

    @Override
    public DataStore<StoredCredential> delete(String key) throws IOException {
        credentialTokenRepository.deleteById(key);
        return this;
    }
}
