package my.application.api.controllers.google;

import com.drew.lang.Iterables;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import my.domain.redis.entities.google.CredentialToken;
import my.domain.redis.repositories.google.CredentialTokenRepository;
import org.apache.commons.lang3.StringUtils;

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
        return Iterables.toList(credentialTokenRepository.findAll()).stream().map(token -> {
            StoredCredential storedCredential = new StoredCredential();
            storedCredential.setAccessToken(token.getAccessToken());
            storedCredential.setRefreshToken(token.getRefreshToken());
            storedCredential.setExpirationTimeMilliseconds(token.getExpirationTimeMilliseconds());
            return storedCredential;
        }).toList();
    }

    @Override
    public StoredCredential get(String key) throws IOException {
        Optional<CredentialToken> byId = credentialTokenRepository.findById(key);

        if (byId.isEmpty()) {
            return null;
        }
        CredentialToken credentialToken = byId.get();
        StoredCredential storedCredential = new StoredCredential();
        storedCredential.setAccessToken(credentialToken.getAccessToken());
        storedCredential.setRefreshToken(credentialToken.getRefreshToken());
        storedCredential.setExpirationTimeMilliseconds(credentialToken.getExpirationTimeMilliseconds());
        return storedCredential;
    }

    @Override
    public DataStore<StoredCredential> set(String key, StoredCredential value) throws IOException {
        if (StringUtils.isEmpty(value.getRefreshToken())) {
            credentialTokenRepository.findById(key).ifPresent(credentialToken -> value.setRefreshToken(credentialToken.getRefreshToken()));
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
