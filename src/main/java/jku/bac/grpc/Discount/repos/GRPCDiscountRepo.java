package jku.bac.grpc.Discount.repos;

import jku.bac.grpc.Discount.entity.GRPCDiscountEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GRPCDiscountRepo extends MongoRepository<GRPCDiscountEntry, String> {
    public GRPCDiscountEntry findByItemLabel(String label);
}
