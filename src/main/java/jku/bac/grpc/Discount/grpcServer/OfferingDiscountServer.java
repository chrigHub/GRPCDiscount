package jku.bac.grpc.Discount.grpcServer;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import jku.bac.grpc.Discount.service.GRPCDiscountService;
import jku.bac.grpc.server.offeringdiscount.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;

import java.util.List;

@GrpcService
public class OfferingDiscountServer extends OfferingDiscountServiceGrpc.OfferingDiscountServiceImplBase{
    @Autowired
    private GRPCDiscountService grpcDiscountService;

    @Override
    public void getDiscount(OfferingRequest request, StreamObserver<DiscountResponse> responseObserver) {
        List<OfferingDiscountItem> itemList = request.getTransferItemsList();
        System.out.println("Entered Discount GRPC Server");
        try{
            itemList = grpcDiscountService.calcDiscountFromList(itemList);
        }catch(Exception e){
            e.printStackTrace();
        }
        DiscountResponse response = DiscountResponse.newBuilder()
                .addAllTransferItems(itemList)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void syncDB(Sync request, StreamObserver<Null> responseObserver) {
        Context ctx = Context.current().fork();
        ctx.run(() -> {
            System.out.println("Incoming Sync request");
            grpcDiscountService.removeEntry(request.getItemLabel());
        });
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }
}
