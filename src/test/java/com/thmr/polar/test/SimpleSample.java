package com.thmr.polar.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import com.thmr.polar.Config;
import com.thmr.polar.PolarFlowAPI;
import com.thmr.polar.Training;
import com.thmr.polar.TrainingList;


/**  
* Simple sample class for using the Polar Java API to to demonstrate login and getting training data
* 
* java SimpleSample <username> <password>
*   
* @author Thomas Mayer
*/ 
public class SimpleSample
{
	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			System.out.println("ERROR: USAGE: java SimpleSample <username> <password>, please provide username and password as arguments");
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
		List presentSportIds = trainingList.getSportsIds();
		
		System.out.println("present sport ids: " + presentSportIds);
		
		for (Training training : trainingList.getTrainings())
		{
			
			System.out.println("no: " + count++ + ", " +
					"id: " + training.getId() + ", " + 
					"sportName: " + training.getSportName() + ", " +
					"sportId: " + training.getSportId() + ", " +
					"distance: " + training.getDistance() + ", " +
					"duration: " + training.getDuration() + ", " + 
					"duration as hour:minute:seconds: " + training.getDurationAsString());
			
			System.out.println("overall mountainbiking distance: " + trainingList.getOverallDistanceInKm(new int[]{Config.SPORT_ID_MOUNTAIN_BIKING}) + ", overall duration: " + trainingList.getOverallDurationAsString(new int[]{Config.SPORT_ID_MOUNTAIN_BIKING}));
			System.out.println("overall indoor cycling distance: " + trainingList.getOverallDistanceInKm(new int[]{Config.SPORT_ID_INDOOR_CYCLING}) + ", overall duration: " + trainingList.getOverallDurationAsString(new int[]{Config.SPORT_ID_INDOOR_CYCLING}));
			System.out.println("overall road cycling distance: " + trainingList.getOverallDistanceInKm(new int[]{Config.SPORT_ID_ROAD_CYCLING}) + ", overall duration: " + trainingList.getOverallDurationAsString(new int[]{Config.SPORT_ID_ROAD_CYCLING}));
		}
	}

}
