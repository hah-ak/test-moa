package my.application.api.controllers.google;

import com.drew.lang.Iterables;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import my.domain.redis.entities.google.CredentialToken;
import my.domain.redis.repositories.google.CredentialTokenRepository;

import java.io.IOException;
import java.util.*;

public class JPADataStore extends AbstractDataStore<CredentialToken> {
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
    public Collection<CredentialToken> values() throws IOException {
        return Iterables.toList(credentialTokenRepository.findAll());
    }

    @Override
    public CredentialToken get(String key) throws IOException {
        return credentialTokenRepository.findById(key).orElse(null);
    }

    @Override
    public DataStore<CredentialToken> set(String key, CredentialToken value) throws IOException {
        CredentialToken save = credentialTokenRepository.save(new CredentialToken(key, value));
        return this;
    }

    @Override
    public DataStore<CredentialToken> clear() throws IOException {
        credentialTokenRepository.deleteAll();
        return this;
    }

    @Override
    public DataStore<CredentialToken> delete(String key) throws IOException {
        credentialTokenRepository.deleteById(key);
        return this;
    }
}
