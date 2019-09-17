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
  
@Path("/fun")
public class AlexaForFun{
	
	@POST
	@Consumes("application/json")
	public String postMsg() throws JsonProcessingException {
		
		ObjectMapper Obj = new ObjectMapper();
		OutputSpeech resp1 = new OutputSpeech();
		resp1.type = "SSML";
		resp1.ssml = "<speak><emphasis level=\"strong\">oh my God look, who is here. <break time=\"1s\"/>Mr. Ghosh has arrived.</emphasis></speak>";
		
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
