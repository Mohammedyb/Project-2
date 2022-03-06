package revature.ProjectManagementAPI.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IdNotFoundException extends Exception{
    public IdNotFoundException(String message){
        super(message);
    }
}
