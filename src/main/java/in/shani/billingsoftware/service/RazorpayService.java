package in.shani.billingsoftware.service;


import com.razorpay.RazorpayException;
import in.shani.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}

