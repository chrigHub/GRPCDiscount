package jku.bac.grpc.Discount.service;

import io.grpc.stub.StreamObserver;
import jku.bac.grpc.Discount.entity.GRPCDiscountEntry;
import jku.bac.grpc.Discount.repos.GRPCDiscountRepo;
import jku.bac.grpc.server.offeringdiscount.DiscountResponse;
import jku.bac.grpc.server.offeringdiscount.OfferingDiscountItem;
import jku.bac.grpc.server.offeringdiscount.OfferingDiscountServiceGrpc;
import jku.bac.grpc.server.offeringdiscount.OfferingRequest;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GRPCDiscountService {
    @Autowired
    GRPCDiscountRepo grpcDiscountRepo;

    public void insertItems() throws Exception{
        grpcDiscountRepo.deleteAll();
        grpcDiscountRepo.save(new GRPCDiscountEntry("Screw",0));
        grpcDiscountRepo.save(new GRPCDiscountEntry("Screwdriver",10));
        grpcDiscountRepo.save(new GRPCDiscountEntry("Drill", 50));
    }

    public List<OfferingDiscountItem> calcDiscountFromList(List<OfferingDiscountItem> itemList){
        List<OfferingDiscountItem> returnList = new ArrayList<>();
        for(int i = 0; i < itemList.size(); i++){
            OfferingDiscountItem item = itemList.get(i);
            GRPCDiscountEntry discountItem = grpcDiscountRepo.findByItemLabel(item.getLabel());
            if(discountItem != null){
                item = item.toBuilder()
                        .setPrize(item.getPrize() - item.getPrize() * ((double)discountItem.getDiscount()/100))
                        .build();
            }
            returnList.add(item);
        }
        return returnList;
    }

    public void removeEntry (String label){
        GRPCDiscountEntry entry = grpcDiscountRepo.findByItemLabel(label);
        if(entry != null){
            grpcDiscountRepo.delete(entry);
        }
    }
}
