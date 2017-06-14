/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception 
	{
		/*//create scanner
		Scanner console = new Scanner(System.in);
		
		ArrayList<String> Grades = new ArrayList<String>();
		ArrayList<Boolean> Status = new ArrayList<Boolean>();
		
		boolean done = false;
		while(!done)
		{
			System.out.println("Please enter your grade (or 0 if done)");
			String currentGrade = console.nextLine();
			if(currentGrade.equals("0"))
			{
				done = true;
			}
			else
			{
				while(convert(currentGrade) < 0)
				{
					System.out.println("Your input was invalid. Please enter a real grade: ");
					currentGrade = console.nextLine();
				}
				Grades.add(currentGrade);
				
				System.out.println("Was that class an honors or AP Class? (y/n)");
				String currentStatus = console.nextLine();
				Boolean Honors;
				if(currentStatus.substring(0, 1).toLowerCase().equals("y"))
				{
					Honors = true;
				}
				else
				{
					Honors = false;
				}
				Status.add(Honors);
			}
		}
		System.out.println("Your GPA is: " + calculateGPA(Grades, Status));
	}
	public static double calculateGPA(ArrayList<String> Grades, ArrayList<Boolean> Status)
	{
		double GPA;
		double scoreSum = 0;
		double classCount = 0;
		
		for(String Grade : Grades)
		{
			scoreSum = scoreSum + convert(Grade);
			classCount++;
		}
		
		double amountOfBoost = 0;
		for(Boolean Honors : Status)
		{
			if(Honors)
			{
				amountOfBoost+=0.3;
			}
		}
		
		scoreSum+=amountOfBoost;
		
		GPA = scoreSum/classCount;
		return GPA;
	}
	
public static double convert(String Grade)
	{
		double value;
		Grade = Grade.toUpperCase();
		
		if(Grade.equals("A"))
		{
			value = 4.0;
		}
		else if(Grade.equals("A-"))
		{
			value = 3.67;
		}
		else if(Grade.equals("B+"))
		{
			value = 3.33;
		}
		else if(Grade.equals("B"))
		{
			value = 3.0;
		}
		else if(Grade.equals("B-"))
		{
			value = 2.67;
		}
		else if(Grade.equals("C+"))
		{
			value = 2.33;
		}
		else if(Grade.equals("C"))
		{
			value = 2.0;
		}
		else if(Grade.equals("C-"))
		{
			value = 1.67;
		}
		else if(Grade.equals("D+"))
		{
			value = 1.33;
		}
		else if(Grade.equals("D"))
		{
			value = 1.0;
		}
		else if(Grade.equals("D-"))
		{
			value = 0.67;
		}
		else if(Grade.equals("F"))
		{
			value = 0.0;
		}
		else
		{
			value = -1;
		}
		return value;
	}*/
	SpringApplication.run(Main.class, args)l
}

  @RequestMapping("/")
  String index() {
    return "index";
  }

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

}
