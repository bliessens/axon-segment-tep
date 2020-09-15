package be.cegeka.vconsult.axon.tep;

import org.axonframework.common.jpa.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

class AxonEntityProvider implements EntityManagerProvider {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
