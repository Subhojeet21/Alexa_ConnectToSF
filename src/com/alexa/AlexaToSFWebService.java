package com.alexa;

import java.util.Optional;

import javax.security.auth.login.Configuration;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;
import com.amazon.ask.model.ui.Reprompt;
import com.amazon.ask.model.ui.SimpleCard;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.response.ResponseBuilder;
import com.amazon.ask.response.SkillResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
  
@Path("/hello")
public class AlexaToSFWebService{
	
	/*@GET
    public Response getMsg() {
  
        String output = "Welcome   : HTTP GET";
  
        return Response.status(200).entity(output).build();
  
    }
	
	@POST
	@Consumes("application/json")
    public Response postMsg() {
  
        String output = "Welcome   : HTTP POST";
  
        return Response.status(200).entity(output).build();
  
    }*/
	
	@POST
	@Consumes("application/json")
	public String postMsg() throws InstantiationException, IllegalAccessException, JsonProcessingException {
		/*HandlerInput input = null;
		String speechText = "You have uploaded the image of Mahendra Singh Dhoni";
        /*return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("HelloWorld", speechText)
                .build();
		ResponseBuilder builder = new ResponseBuilder();
		Optional<com.amazon.ask.model.Response> resp = builder
        .withSpeech(speechText)
        .withSimpleCard("HelloWorld", speechText)
        .build();
		
		ObjectMapper Obj = new ObjectMapper();
		System.out.println(Obj.writeValueAsString(resp));
		return Obj.writeValueAsString(resp);*/
		String result = "";
		try{
			result = GetImageInforFromSF.connectAndPredictinSF();
		}catch(Exception e){
			
		}
		
		
		ObjectMapper Obj = new ObjectMapper();
		OutputSpeech resp1 = new OutputSpeech();
		resp1.type = "SSML";
		if(result != "" && result != null){
			resp1.ssml = "<speak>You have uploaded the image of "+result+".</speak>";
		}else{
			resp1.ssml = "<speak>There is an issue. Please try again.</speak>";
		}
		
		
		
		Response rp = new Response();
		rp.outputSpeech = resp1;
		rp.shouldEndSession = true;
		
		Prediction pred = new Prediction();
		pred.version = "1.0";
		pred.response = rp;
		
		return Obj.writeValueAsString(pred);
    }
	
	public class Prediction{
		public String version;
		public Response response;
		
	}
	
	public class Response{
		public OutputSpeech outputSpeech;
		public boolean shouldEndSession;
	}
	
	public class OutputSpeech{
		public String type;
        public String ssml;
	}
	
	
}