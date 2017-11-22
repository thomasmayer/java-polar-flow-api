package com.thmr.polar.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.thmr.polar.Config;
import com.thmr.polar.PolarFlowAPI;
import com.thmr.polar.Training;
import com.thmr.polar.TrainingList;


/**  
* Sample class for using the Polar Java API to download gpx, tcx and csv track data
* 
* java DownloadFilesSample <username> <password> <destination-dir>
*   
* @author Thomas Mayer
*/ 
public class DownloadFilesSample
{
	public static void main(String[] args)
	{
		if(args.length < 3)
		{
			System.out.println("ERROR: USAGE: java DownloadFilesSample <username> <password> <destination-dir>, please provide username, password and destination-directory as arguments");
			return;
		}
		
		// create java sdk object
		PolarFlowAPI polarFlowAPI = new PolarFlowAPI();
		
		// login with username (email) and password
		polarFlowAPI.login(args[0], args[1]);
		
		// get training data
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		int[] sportIds = {}; // get all sport ids
		fromDate.set(2017, 9, 1);
		toDate.set(2017, 11, 31);
		
		TrainingList trainingList = polarFlowAPI.getTrainingData(fromDate.getTime(), toDate.getTime(), sportIds);
		int count = 0;
		
		for (Training training : trainingList.getTrainings())
		{
			
			System.out.println("no: " + count++ + ", " +
					"date: " + training.getStartDate() + 
					"sportName: " + training.getSportName() + ", " +
					"sportId: " + training.getSportId() + ", " +
					"distance: " + training.getDistance() + ", " +
					"duration: " + training.getDuration() + ", " + 
					"duration as hour:minute:seconds: " + training.getDurationAsString());
		}
		
		Training t = trainingList.getTrainings().get(0);
		try
		{
			String destinationDirectory = args[2];
			
			File myTCXFile = new File(destinationDirectory + File.separator + "test.tcx");
			FileOutputStream fosTCX = new FileOutputStream(myTCXFile);
			if(polarFlowAPI.downloadTCX(t, fosTCX, true))
			{
				System.out.println("file test.tcx downloaded to " + destinationDirectory);
			}
			else
			{
				System.out.println("failed to download tcx file");
			}
			fosTCX.close();
			
			
			File myGPXFile = new File(destinationDirectory + File.separator + "test.gpx");
			FileOutputStream fosGPX = new FileOutputStream(myGPXFile);
			if(polarFlowAPI.downloadGPX(t, fosGPX, true))
			{
				System.out.println("file test.gpx downloaded to " + destinationDirectory);				
			}
			else
			{
				System.out.println("failed to download gpx file");
			}
			fosGPX.close();


			File myCSVFile = new File(destinationDirectory + File.separator + "test.csv");
			FileOutputStream fosCSV = new FileOutputStream(myCSVFile);
			if(polarFlowAPI.downloadCSV(t, fosCSV, true))
			{
				System.out.println("file test.csv downloaded to " + destinationDirectory);				
			}
			else
			{
				System.out.println("failed to download csv file");
			}
			fosCSV.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
