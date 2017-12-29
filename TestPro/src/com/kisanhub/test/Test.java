package com.kisanhub.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Test {
		
	public static void main(String[] args) {
		
		try {
				String current = System.getProperty("user.dir");
			
				File file = new File(current+"/weather.csv");
				FileWriter fileWriter = new FileWriter(file);
				
				prepareColumnLabels(fileWriter);
				
				getWeatherData("UK","Max temp",fileWriter);
				getWeatherData("UK","Min temp",fileWriter);
				getWeatherData("UK","Mean temp",fileWriter);
				getWeatherData("UK","Sunshine",fileWriter);
				getWeatherData("UK","Rainfall",fileWriter);
				
				getWeatherData("England","Max temp",fileWriter);
				getWeatherData("England","Min temp",fileWriter);
				getWeatherData("England","Mean temp",fileWriter);
				getWeatherData("England","Sunshine",fileWriter);
				getWeatherData("England","Rainfall",fileWriter);
				
				getWeatherData("Wales","Max temp",fileWriter);
				getWeatherData("Wales","Min temp",fileWriter);
				getWeatherData("Wales","Mean temp",fileWriter);
				getWeatherData("Wales","Sunshine",fileWriter);
				getWeatherData("Wales","Rainfall",fileWriter);
				
				getWeatherData("Scotland","Max temp",fileWriter);
				getWeatherData("Scotland","Min temp",fileWriter);
				getWeatherData("Scotland","Mean temp",fileWriter);
				getWeatherData("Scotland","Sunshine",fileWriter);
				getWeatherData("Scotland","Rainfall",fileWriter);
				
				fileWriter.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	
	}
	
	 static void prepareColumnLabels(FileWriter fileWriter)
	 {
		 try {
				 				 	
					fileWriter.write("region_code");	
					fileWriter.write(",");
					fileWriter.write("weather_param");
					fileWriter.write(",");
					fileWriter.write("year");
					fileWriter.write(",");
					fileWriter.write("key");
					fileWriter.write(",");
					fileWriter.write("value");
					
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	 }
	 
	 static void getWeatherData(String region, String weather_param, FileWriter fileWriter)
	 {
		
		try {
				String weather_api_param = "";
				
				switch(weather_param)
				{
					case "Max temp":
						weather_api_param = "Tmax";
						break;
					case "Min temp":
						weather_api_param = "Tmin";
						break;					
					case "Mean temp":
						weather_api_param = "Tmean";
						break;
					case "Sunshine":
						weather_api_param = "Sunshine";
						break;
					case "Rainfall":
						weather_api_param = "Rainfall";
						break;
				}
				
				URL url = new URL("https://www.metoffice.gov.uk/pub/data/weather/uk/climate/datasets/"+ weather_api_param +"/date/"+region+".txt");
					 
				String line = "";		
				int lineCnt=1;		
				String[] headers = null;
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				
				while((line=reader.readLine())!=null)
				{
					if(lineCnt>7)
					{
						if(line.length()<=122)
							line=line.concat(", ,");
						
						line = line.replace("     ", ",");
						line = line.replace("    ", ",");
						line = line.replace("   ", ",");
						line = line.replace("  ", ",");
						if(lineCnt==8)
						{
							
							headers = line.split(",");
						}
						else	
						{
							String[] params = line.split(",");
							
							for (int i = 1; i < params.length; i++) {
								
								fileWriter.append("\n");
								fileWriter.append(region);
								fileWriter.append(",");
								fileWriter.append(weather_param);
								fileWriter.append(",");
								fileWriter.append(params[0]);
								fileWriter.append(",");
								fileWriter.append(headers[i]);
								fileWriter.append(",");
								
								if(params[i]==null || params[i].trim().equals("") || params[i].contains("--"))
									fileWriter.append("N/A");
								else
									fileWriter.append(params[i].trim());
							
								
							}
						}
					}
					 
					lineCnt++;
				}
			
				
		 
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
		
	 }

}
