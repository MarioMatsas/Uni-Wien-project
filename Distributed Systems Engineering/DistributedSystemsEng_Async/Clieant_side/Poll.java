package Clieant_side;
import java.util.ArrayList;

import Shared_elements.ResponseMessage;

public class Poll {
    // We want it to be static
    private static ArrayList<ResponseMessage<String>> responses = new ArrayList<>();

    public static boolean hasResponse(){
        if (responses.isEmpty()){
            return false;
        }
        return true;
    }

    public static ArrayList<ResponseMessage<String>> getReponses(){
        return responses;
    }

    public static void addResponse(ResponseMessage<String> resp){
        responses.add(resp);
    }
}
