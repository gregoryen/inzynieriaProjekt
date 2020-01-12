//package com.engineering.shop.cart.ApiError;
//
//import com.engineering.shop.warehouse.errorHandlers.ApiError;
//import com.engineering.shop.warehouse.exceptions.UnprocessableEntityException;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
//public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//    private ResponseEntity<Object> buildResponseEntity(com.engineering.shop.cart.ApiError.ApiError apiError) {
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    protected ResponseEntity<Object> handleEntityNotFound(
//            ResourceNotFoundException ex) {
//        com.engineering.shop.cart.ApiError.ApiError apiError = new com.engineering.shop.cart.ApiError.ApiError(HttpStatus.NOT_FOUND);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }
//
//    @ExceptionHandler(UnprocessableEntityException.class)
//    protected ResponseEntity<Object> handleEntityNotFound(
//            UnprocessableEntityException ex) {
//        com.engineering.shop.cart.ApiError.ApiError apiError = new com.engineering.shop.cart.ApiError.ApiError(HttpStatus.UNPROCESSABLE_ENTITY);
//        apiError.setMessage(ex.getMessage());
//        return buildResponseEntity(apiError);
//    }
//}