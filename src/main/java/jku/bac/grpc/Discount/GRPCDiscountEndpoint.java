package jku.bac.grpc.Discount;

import jku.bac.grpc.Discount.service.GRPCDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GRPCDiscountEndpoint {
    @Autowired
    GRPCDiscountService grpcDiscountService;

    @GetMapping("/resetDB")
    public String resetDB() throws Exception{
        grpcDiscountService.insertItems();
        return "Discount database has been reset!";
    }
}
