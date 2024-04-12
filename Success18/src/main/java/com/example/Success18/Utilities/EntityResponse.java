package com.example.Success18.Utilities;

//import com.example.Success18.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data //to create getters and setters including to strings() and equals methods
@AllArgsConstructor// generate constructors with parameters for all fields
@NoArgsConstructor
//This entity response has a generic type T to make sure that it can be used with different entities to ensure versatility and reusability
 public class EntityResponse<T> {
    private String message;
    private Integer statusCode;
    private T entity;

//constructor with parameters
//    public EntityResponse(EntityResponse response, HttpStatus httpStatus) {
//    }


//method to give the success status of an operation
    public void setSuccess(boolean b) {
    }


//    public void setSuccess(boolean b) {
//    }
//
//    public void setData(Role savedRole) {
//    }
}
