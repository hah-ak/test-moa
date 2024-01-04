package my.application.gateway.controllers.google;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import my.application.gateway.repositories.google.CredentialTokenRepository;

import java.io.IOException;
import java.io.Serializable;

public class JPADataStoreFactory extends AbstractDataStoreFactory {
    private final CredentialTokenRepository credentialTokenRepository;
    public JPADataStoreFactory(CredentialTokenRepository credentialTokenRepository) {
        this.credentialTokenRepository = credentialTokenRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <V extends Serializable> DataStore<V> createDataStore(String id) throws IOException {
        return (DataStore<V>) new JPADataStore(this,id,credentialTokenRepository);
    }
}