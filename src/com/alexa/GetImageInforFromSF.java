package com.alexa;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class GetImageInforFromSF {
	
	private static String accesstoken = "";
	private static String instanceUrl = "";
	private static String authEndpoint = "";
	private static String username = "";
	private static String password = "";
	private static String clientId = "";
	private static String clientPass = "";
	
	
	public static String connectAndPredictinSF() throws ClientProtocolException, IOException{
		
		String result = "";
		try {
			readPropertiesFile();
		
			String finalEndpoint = authEndpoint+"?grant_type=password&username="+username+"&password="+password+"&client_id="+clientId+"&client_secret="+clientPass;
			System.out.println(finalEndpoint);
		
			getAccessToken(finalEndpoint);
			if(accesstoken != "" && accesstoken != null){
				result = predictImageInSF();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void readPropertiesFile(){
		
		authEndpoint = System.getenv("authEndpoint");
		username = System.getenv("username");
		password = System.getenv("password");
		clientId = System.getenv("clientId");
		clientPass = System.getenv("clientPass");
		
	}
	
	private static void getAccessToken(String endpoint) throws ClientProtocolException, IOException, JSONException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(endpoint);
		CloseableHttpResponse response = httpclient.execute(httppost);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {
		        	String json = EntityUtils.toString(entity);
		        	JSONObject result = new JSONObject(json);
		            System.out.println(json);
		            accesstoken = result.getString("access_token");
		            instanceUrl = result.getString("instance_url");
		        
		    }
		} finally {
		    response.close();
		}
	}
	
	private static String predictImageInSF() throws IOException{
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		try{
			HttpGet httpget = new HttpGet(instanceUrl+"/services/apexrest/Predict");
			httpget.setHeader("Authorization", "OAuth " +accesstoken);
			
			CloseableHttpResponse response = httpclient.execute(httpget);
			System.out.println(response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				System.out.println(result);
			}
			
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		return result;
	}
}