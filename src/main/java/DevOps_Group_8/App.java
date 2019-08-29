package DevOps_Group_8;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 */

public class App {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1)
        {
            a.connect("localhost:33060");
        }
        else
        {
            a.connect(args[0]);
        }

        City c2 = a.getCity(2710);
        System.out.println(c2);
        //All the countries in the world organised by largest population to smallest.
        ArrayList<Country> cous = a.getCountry();
        a.displayCountry(cous);

        // Get All the countries in a continent organised by largest population to smallest.
        ArrayList<Country> countries=a.countries_continent_largest_to_smallest();
        a.displayCountries_continent_largest_to_smallest(countries);

        // All the countries in a region organised by largest population to smallest.
        ArrayList<Country> Countries=a.countries_region_largest_to_smallest();
        a.displayCountries_region_largest_to_smallest(Countries);

        //All the cities in the world organised by largest population to smallest.
        ArrayList<City> cities = a.getCity();
        a.displayCity(cities);

        //All the cities in a continent organised by largest population to smallest.
        ArrayList<City> cities2=a.getCity_continent();
        a.displayCityContinent(cities2);

        //All the cities in a region organised by largest population to smallest.
        ArrayList<City> cities3 = a.getCity_Region();
        a.displayCityRegion(cities3);

        // All the cities in a country organised by largest population to smallest.
        ArrayList<City> cities4= a.getCitybyCountry();
        a.displayCityCountry(cities4);

        //All the cities in a district organised by largest population to smallest.
        ArrayList<City> cities5 = a.getCitybyDistrict();
        a.displayCityDistrict(cities5);

        //All the capital cities in the world organised by largest population to smallest.
        ArrayList<City> cities6 = a.getCapitalCities();
        a.displayCapitalCities(cities6);

        //All the capital cities in a continent organised by largest population to smallest.
        ArrayList<City> cities7 = a.getCapitalCitiesbyContinent();
        a.displayCapitalCitiesbyContinent(cities7);

        //All the capital cities in a region organised by largest to smallest.
        ArrayList<City> cities8 = a.getCapitalCitiesbyRegion();
        a.displayCapitalCitiesbyRegion(cities8);

        //Top N Countries in the world organised by largest to smallest.
        ArrayList<Country> countries1 = a.gettopCountryWorld();
        a.displayTopCountriesbyWorld(countries1);

        //Top N populated countries in a continent where N is provided by the user
        ArrayList<Country> countries2 = a.gettopCountryContinent();
        a.displayTopCountriesbyContinent(countries2);

        //Top N populated countries in a region where N is provided by the user.
        ArrayList<Country> countries3 = a.gettopCountryRegion();
        a.displayTopCountriesbyRegion(countries3);

        //Top N populated cities in the world where N is provided by the user.
        ArrayList<City> cities9 = a.gettopCityWorld();
        a.displayTopCitiesWorld(cities9);

        //Top N populated cities in a continent where N is provided by the user.
        ArrayList<City> cities10 = a.gettopCityContinent();
        a.displayTopCitiesContinent(cities10);

        //Top N populated cities in a region where N is provided by the user.
        ArrayList<City> cities11 = a.gettopCityRegion();
        a.displayTopCitiesRegion(cities11);

        //Top N populated cities in a country where N is provided by the user.
        ArrayList<City> cities12 = a.gettopCityCountry();
        a.displayTopCitiesCountry(cities12);

        //Top N populated cities in a district where N is provided by the user.
        ArrayList<City> cities13 = a.gettopCityDistrict();
        a.displayTopCitiesDistrict(cities13);

        //Top N populated capital cities in the world where N is provided by the user.
        ArrayList<City> cities14 = a.gettopCapitalCityWorld();
        a.displayTopCapitalCityWorld(cities14);

        //Top N populated capital cities in a continent where N is provided by the user.
        ArrayList<City> cities15 = a.gettopCapitalCityContinent();
        a.displayTopCapitalCityContinent(cities15);

        //Top N populated capital cities in a region where N is provided by the user.
        ArrayList<City> cities16 = a.gettopCapitalCityRegion();
        a.displayTopCapitalCityRegion(cities16);


        // Disconnect from database
        a.disconnect();
    }


    /**
     * Connection to MySQL database.
     */
    public Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public ArrayList<Country> getCountry()
    {
        ArrayList<Country> cous = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SELECT  Code, Name, Continent, Region, Population, Capital FROM country ORDER BY country.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
               //Return new country if valid.
               //Check one is returned
               while (rset.next())
               {
                    Country country = new Country();
                    country.setCode(rset.getString("Code"));
                    country.setName(rset.getString("Name"));
                    country.setContinent(rset.getString("Continent"));
                    country.setRegion(rset.getString("Region"));
                    country.setPopulation(rset.getInt("Population"));
                    country.setCapital(rset.getInt("Capital"));
                    cous.add(country);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country in the World details");
        }
        return cous;
    }

    //displaying countries information
    public void displayCountry(ArrayList<Country> cous)
    {
        System.out.print("***********************Countries in the World organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s%25s%25s%25s","Code","Name","Continent","Region","Population","Capital\n");
        for(Country co:cous)
        {
            System.out.printf("%25s%25s%25s%25s%25s%25s",co.getCode(),co.getName(),co.getContinent(),co.getRegion(),co.getPopulation(),co.getCapital());
            System.out.print("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving population of the countries within a continent
    public ArrayList<Country> countries_continent_largest_to_smallest()
    {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Region, Population, Capital "
                            + "FROM country "
                            + "WHERE Continent = 'Europe' "
                            + "ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
               //Return new city if valid.
               //Check one is returned
               while (rset.next())
               {
                    Country country = new Country();
                    country.setName(rset.getString("Name"));
                    country.setRegion(rset.getString("Region"));
                    country.setPopulation(rset.getInt("Population"));
                    country.setCapital(rset.getInt("Capital"));
                    countries.add(country);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
        }
        return countries;
    }

    public void displayCountries_continent_largest_to_smallest(ArrayList<Country> countries)
    {
        System.out.print("***********************countries in a continent organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s%25s","Name","Region","Population","Capital\n");
      for(Country c:countries)
      {
          System.out.printf("%25s%25s%25s%25s",c.getName(),c.getRegion(),c.getPopulation(),c.getCapital());
          System.out.print("\n");
      }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving countries's population within a region
    public ArrayList<Country> countries_region_largest_to_smallest()
    {
        ArrayList<Country> Countries = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent, Population, Capital "
                            + "FROM country "
                            + "WHERE Region = 'Middle East' "
                            + "ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country country = new Country();
                    country.setName(rset.getString("Name"));
                    country.setContinent(rset.getString("Continent"));
                    country.setPopulation(rset.getInt("Population"));
                    country.setCapital(rset.getInt("Capital"));
                    Countries.add(country);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Country details");
        }
        return Countries;
    }

    public void displayCountries_region_largest_to_smallest(ArrayList<Country> Countries)
    {
        // Check countries is not null
        if (Countries == null)
        {
            System.out.println("No data record for countries");
            return;
        }
        System.out.print("***********************countries in a region organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s%25s","Name","Continent","Population","Capital\n");
        for(Country c:Countries)
        {
            if (c == null)
                continue;
            System.out.printf("%25s%25s%25s%25s",c.getName(),c.getContinent(),c.getPopulation(),c.getCapital());
            System.out.print("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving population of the cities in the world largest to smallest
    public ArrayList<City> getCity()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, CountryCode, District, Population "
                            + "FROM city "
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    City   city = new City();
                    city.setName(rset.getString("Name"));
                    city.setDistrict(rset.getString("District"));
                    city.setPopulation(rset.getInt("Population"));
                    cities.add(city);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
        }
        return cities;
    }

    public void displayCity(ArrayList<City> cities)
    {
        //error control for null city values with unique testing
        if (cities==null)
        {
            System.out.println("No data record for cities");
            return;
        }
        System.out.print("***********************cities in the world organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s","Name","District","Population\n");
        for(City c:cities)
        {
            if(c==null)
                continue;
                System.out.printf("%25s%25s%25s",c.getName(),c.getDistrict(),c.getPopulation());
                System.out.print("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving cities's population within a continent
    public ArrayList<City> getCity_continent() {
        ArrayList<City> cties = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.continent, country.Name,city.CountryCode,city.Name, city.Population "
                            + "FROM city,country "
                            + "WHERE city.CountryCode = country.Code AND country.continent='Asia'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
                {
//             Return new city if valid.
//             Check one is returned
               while (rset.next())
               {
                 Country c =new Country();
                 c.setContinent(rset.getString(1));
                 c.setName(rset.getString(2));
                 c.setCode(rset.getString(3));

                 City ci = new City();
                 ci.setCountry(c);
                 ci.setName(rset.getString(4));
                 ci.setPopulation(rset.getInt(5));
                 cties.add(ci);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City by Continent details");
        }
        return cties;
    }

    public void displayCityContinent(ArrayList<City> cties2)
    {
        if(cties2==null){
            System.out.println("no data record for cities");
        }
        System.out.print("***********************cities in a continent organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s%25s%25s","Continent","Name","CountryCode","CityName","Population\n");
        for(City ct:cties2)
        {
            if(ct==null)
                continue;

            System.out.printf("%25s%25s%25s%25s%25s",ct.getCountry().getContinent(),ct.getCountry().getName(),ct.getCountry().getCode(),ct.getName(),ct.getPopulation());
            System.out.print("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //getting cities' population within region
    public ArrayList<City> getCity_Region()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.District , city.Population, country.Region "
                            + "FROM country, city "
                            + "WHERE city.ID=country.Capital AND country.Region='Southeast Asia'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
                {

                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setRegion(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(1));
                    ci.setDistrict(rset.getString(2));
                    ci.setPopulation(rset.getInt(3));
                    cities.add(ci);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Cities in a Region details");
        }
        return cities;
    }

    public void displayCityRegion(ArrayList<City> cties3)
    {
        //check cities_data is not null
        if (cties3==null){
            System.out.println("No data record for cities in region");
        }
        System.out.print("***********************cities in a region organised by largest population to smallest***********************\n");
        System.out.printf("%25s%25s%25s%25s","Name","District","Population","Region\n");
        for(City ct:cties3)
        {
            if (ct==null)
            System.out.printf("%25s%25s%25s%25s",ct.getName(),ct.getDistrict(),ct.getPopulation(),ct.getCountry().getRegion());
            System.out.print("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }
    public City getCity(int id){
        City city=null;
        try {

            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT city.ID, city.Name, city.District, city.Population "
                            + "FROM city "
                            + "WHERE city.ID = "+ id +" "
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
//             Return new city if valid.
//             Check one is returned

                while (rset.next()){
                    city=new City();
                city.setId(rset.getInt(1));
                city.setName(rset.getString(2));
                city.setDistrict(rset.getString(3));
                city.setPopulation(rset.getInt(4));
                }

            }

        }
             catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Failed to get One City!");
            }
        return city;

    }


    //retrieving cities' population for a country
    public ArrayList<City> getCitybyCountry()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Name, city.Name, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.CountryCode = country.Code AND country.Name = 'Myanmar'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
//             Return new city if valid.
//             Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(1));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(3));
                    ci.setPopulation(rset.getInt(4));
                    cities.add(ci);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Cities in a Country details");
        }
    return cities;
    }
    public void displayCityCountry(ArrayList<City> cties4)
    {
        if (cties4 == null)
        {
            System.out.println("No data record for city in a country");
            return;
        }
        System.out.print("***********************Cities in a country organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s", "Name","CityName","District","Population\n");
        for(City ct:cties4)
        {
            if (ct == null)
                continue;
            System.out.printf("%20s%20s%20s%20s", ct.getCountry().getName(),ct.getName(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving cities' population for a district
    public ArrayList<City> getCitybyDistrict() {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.CountryCode, District, city.Population "
                            + "FROM city "
                            + "WHERE District = 'California'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
//             Return new city if valid.
//             Check one is returned
                while (rset.next())
                {
                    City ci = new City();
                    ci.setName(rset.getString(1));
                    ci.setCountrycode(rset.getString(2));
                    ci.setDistrict(rset.getString(3));
                    ci.setPopulation(rset.getInt(4));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get City details");
        }
        return cities;
    }

    public void displayCityDistrict(ArrayList<City> cties5)
    {
        if (cties5 == null)
        {
            System.out.println("No data record for city in a district");
            return;
        }
        System.out.print("***********************cities in a district organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s","Name","CountryCode","District","Population\n");
        for(City ct:cties5)
        {
            if (ct == null)
                continue;
            System.out.printf("%20s%20s%20s%20s",ct.getName(),ct.getCountry(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //getting capital cities in the world
    public ArrayList<City> getCapitalCities()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, country.Name, country.Continent, city.District , city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(2));
                    c.setContinent(rset.getString(3));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(1));
                    ci.setDistrict(rset.getString(4));
                    ci.setPopulation(rset.getInt(5));
                    cities.add(ci);

                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital Cities details");
        }
        return cities;
    }

    public void displayCapitalCities(ArrayList<City> cties6)
    {
        // Check captial city is not null
        if (cties6 == null)
        {
            System.out.println("No data record for capital_cities");
            return;
        }
        System.out.print("***********************Capital Cities in the World organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s","Name","CountryName","Continent","District","Population\n");
        for(City ct:cties6)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s", ct.getName(),ct.getCountry().getName(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving capital's population for a continent
    public ArrayList<City> getCapitalCitiesbyContinent()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Capital, city.Name, country.Name, country.Continent, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital AND country.Continent = 'Asia'"
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset == null)
            {
                System.out.println("Not Found");
            }
            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setCapital(rset.getInt(1));
                    c.setName(rset.getString(3));
                    c.setContinent(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(5));
                    ci.setPopulation(rset.getInt(6));
                    cities.add(ci);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital City in a Continent details");
        }
        return cities;
    }

    public void displayCapitalCitiesbyContinent(ArrayList<City> cties7)
    {
        // Check cities is not null
        if (cties7 == null)
        {
            System.out.println("no data record for captial city in a continent");
            return;
        }
        System.out.print("***********************Capital Cities in a Continent organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s%20s","Capital","Name","CountryName","Continent","District","Population\n");
        for(City ct:cties7)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s%20s%20s",ct.getCountry().getCapital(),ct.getName(),ct.getCountry().getName(),ct.getCountry().getContinent(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    //retrieving captial's population within a region
    public ArrayList<City> getCapitalCitiesbyRegion()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Capital, city.Name, country.Name, country.Region, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital AND country.Region='Southeast Asia'"
                            + "ORDER BY city.Population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setCapital(rset.getInt(1));
                    c.setName(rset.getString(3));
                    c.setRegion(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(5));
                    ci.setPopulation(rset.getInt(6));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Capital City in a Region details");
        }
        return cities;
    }

    public void displayCapitalCitiesbyRegion(ArrayList<City> cties8)
    {
        // Check cities data is not null
        if (cties8 == null)
        {
            System.out.println("No captial city record in a region");
            return;
        }
        System.out.print("***********************Capital Cities in a Region organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s%20s", "Capital","Name","CountryName","Region","District","Population\n");
        for(City ct:cties8)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s%20s%20s",ct.getCountry().getCapital(),ct.getName(),ct.getCountry().getName(),ct.getCountry().getRegion(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<Country> gettopCountryWorld()
    {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Countries in the Wrold : ");
            String number = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent, Region, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC "
                            + "Limit " + number;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(1));
                    c.setContinent(rset.getString(2));
                    c.setRegion(rset.getString(3));
                    c.setPopulation(rset.getInt(4));
                    countries.add(c);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top countries in the World details");
        }
        return countries;
    }

    public void displayTopCountriesbyWorld(ArrayList<Country> countries1)
    {
        // Check cities data is not null
        System.out.print("***********************Top Countries in the World organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "Name","Continent","Region","Population\n");
        for(Country ct:countries1)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getContinent(),ct.getRegion(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<Country> gettopCountryContinent()
    {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Countries in Asia : ");
            String input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent, Region, Population "
                            + "FROM country "
                            + "Where Continent = 'Asia'"
                            + "ORDER BY Population DESC "
                            + "Limit " + input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(1));
                    c.setContinent(rset.getString(2));
                    c.setRegion(rset.getString(3));
                    c.setPopulation(rset.getInt(4));
                    countries.add(c);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top countries in Asia details");
        }
        return countries;
    }

    public void displayTopCountriesbyContinent(ArrayList<Country> countries2)
    {
        // Check cities data is not null
        System.out.print("***********************Top Countries in Asia organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "Name","Continent","Region","Population\n");
        for(Country ct:countries2)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getContinent(),ct.getRegion(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<Country> gettopCountryRegion()
    {
        ArrayList<Country> countries = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Countries in Middle East : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent, Region, Population "
                            + "FROM country "
                            + "Where Region = 'Middle East'"
                            + "ORDER BY Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(1));
                    c.setContinent(rset.getString(2));
                    c.setRegion(rset.getString(3));
                    c.setPopulation(rset.getInt(4));
                    countries.add(c);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top countries in Middle East details");
        }
        return countries;
    }

    public void displayTopCountriesbyRegion(ArrayList<Country> countries3)
    {
        // Check cities data is not null
        System.out.print("***********************Top Countries in Middle East organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "Name","Continent","Region","Population\n");
        for(Country ct:countries3)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getContinent(),ct.getRegion(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCityWorld()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Cities in the World : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, CountryCode, District, Population "
                            + "FROM city "
                            + "ORDER BY Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    City c =new City();
                    c.setName(rset.getString(1));
                    c.setCountrycode(rset.getString(2));
                    c.setDistrict(rset.getString(3));
                    c.setPopulation(rset.getInt(4));
                    cities.add(c);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top cities in the World details");
        }
        return cities;
    }

    public void displayTopCitiesWorld(ArrayList<City> cities9)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Cities in the World organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "Name","CountryCode","District","Population\n");
        for(City ct:cities9)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getCountrycode(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCityContinent()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Cities in Europe : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.continent, country.Name,city.CountryCode,city.Name, city.Population "
                            + "FROM city,country "
                            + "WHERE city.CountryCode = country.Code AND country.continent='Europe'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setContinent(rset.getString(1));
                    c.setName(rset.getString(2));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setCountrycode(rset.getString(3));
                    ci.setName(rset.getString(4));
                    ci.setPopulation(rset.getInt(5));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top cities in Europe details");
        }
        return cities;
    }

    public void displayTopCitiesContinent(ArrayList<City> cities10)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Cities in Europe organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s%20s", "Continent","CountryName","CountryCode","CityName","Population\n");
        for(City ct:cities10)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s%20s",ct.getCountry().getContinent(),ct.getCountry().getName(),ct.getCountrycode(),ct.getName(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCityRegion()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Cities in Central America : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.District , city.Population, country.Region "
                            + "FROM country, city "
                            + "WHERE city.ID=country.Capital AND country.Region='Central America'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setRegion(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(1));
                    ci.setDistrict(rset.getString(2));
                    ci.setPopulation(rset.getInt(3));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top cities in Central America details");
        }
        return cities;
    }

    public void displayTopCitiesRegion(ArrayList<City> cities11)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Cities in Central America organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "Name","District", "Population" ,"Region\n");
        for(City ct:cities11)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getDistrict(), ct.getPopulation() ,ct.getCountry().getRegion());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCityCountry()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Cities in Myanmar : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Name, city.Name, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.CountryCode = country.Code AND country.Name = 'Myanmar'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(1));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(3));
                    ci.setPopulation(rset.getInt(4));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top cities in Myanmar details");
        }
        return cities;
    }

    public void displayTopCitiesCountry(ArrayList<City> cities12)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Cities in Myanmar organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "CountryName","CityName", "District" ,"Population\n");
        for(City ct:cities12)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getCountry().getName(),ct.getName(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCityDistrict()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Cities in California : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.CountryCode, District, city.Population "
                            + "FROM city "
                            + "WHERE District = 'California'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    City ci = new City();
                    ci.setName(rset.getString(1));
                    ci.setCountrycode(rset.getString(2));
                    ci.setDistrict(rset.getString(3));
                    ci.setPopulation(rset.getInt(4));
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top cities in California details");
        }
        return cities;
    }

    public void displayTopCitiesDistrict(ArrayList<City> cities13)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Cities in California organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%30s%20s", "CityName","CountryCode", "District" ,"Population\n");
        for(City ct:cities13)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%30s%20s",ct.getName(),ct.getCountrycode(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCapitalCityWorld()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Capital City in the World : ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, country.Name, country.Continent, city.District , city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setName(rset.getString(2));
                    c.setContinent(rset.getString(3));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(1));
                    ci.setDistrict(rset.getString(4));
                    ci.setPopulation(rset.getInt(5));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top Capital City in the World details");
        }
        return cities;
    }

    public void displayTopCapitalCityWorld(ArrayList<City> cities14)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Capital City in the World organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s","Name","CountryName","Continent","District","Population\n");
        for(City ct:cities14)
        {
            if (ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s", ct.getName(),ct.getCountry().getName(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCapitalCityContinent()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Capital City in Asia: ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Capital, city.Name, country.Name, country.Continent, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital AND country.Continent = 'Asia'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setCapital(rset.getInt(1));
                    c.setName(rset.getString(3));
                    c.setContinent(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(5));
                    ci.setPopulation(rset.getInt(6));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top Capital City in Asia details");
        }
        return cities;
    }

    public void displayTopCapitalCityContinent(ArrayList<City> cities15)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Capital City in Asia organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s%20s","Capital","Name","CountryName","Continent","District","Population\n");
        for(City ct:cities15)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s%20s%20s",ct.getCountry().getCapital(),ct.getName(),ct.getCountry().getName(),ct.getCountry().getContinent(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

    public ArrayList<City> gettopCapitalCityRegion()
    {
        ArrayList<City> cities = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter a number to print Top Populated Capital City in SouthEast Asia: ");
            String user_input = scanner.next(); // get string
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Capital, city.Name, country.Name, country.Region, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital AND country.Region='Southeast Asia'"
                            + "ORDER BY city.Population DESC "
                            + "Limit " + user_input;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset == null)
            {
                System.out.println("Not Found");
            }

            else
            {
                //Return new city if valid.
                //Check one is returned
                while (rset.next())
                {
                    Country c =new Country();
                    c.setCapital(rset.getInt(1));
                    c.setName(rset.getString(3));
                    c.setRegion(rset.getString(4));

                    City ci = new City();
                    ci.setCountry(c);
                    ci.setName(rset.getString(2));
                    ci.setDistrict(rset.getString(5));
                    ci.setPopulation(rset.getInt(6));
                    cities.add(ci);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get Top Capital City in SouthEast Asia details");
        }
        return cities;
    }

    public void displayTopCapitalCityRegion(ArrayList<City> cities16)
    {
        // Check cities data is not null
        System.out.print("***********************Top Populated Capital City in SouthEast Asia organised by largest population to smallest***********************\n");
        System.out.printf("%20s%20s%20s%20s%20s%20s", "Capital","Name","CountryName","Region","District","Population\n");
        for(City ct:cities16)
        {
            if(ct==null)
                continue;
            System.out.printf("%20s%20s%20s%20s%20s%20s",ct.getCountry().getCapital(),ct.getName(),ct.getCountry().getName(),ct.getCountry().getRegion(),ct.getDistrict(),ct.getPopulation());
            System.out.println("\n");
        }
        System.out.print("******************************************************************************************************************\n");
    }

}
