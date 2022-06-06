import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import package_one.*;
public class MethodsAdv extends MethodsBasic
{
    public void retrieve_data_id(String[] args)//displays data of a district for a given id
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists where id=?";

        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(1,args[1]);
            ResultSet r =  stmt.executeQuery();

            if(!r.isBeforeFirst()) 
            {    
                System.out.println("Data not found"); 
                return;
            } 

            while(r.next())
            {
                Data da=new Data(r.getString("id"),r.getString("name"),r.getString("zone"),Integer.parseInt(r.getString("totalCases")) ,Integer.parseInt(r.getString("newCasesDay")),Integer.parseInt(r.getString("deathsDay")),Integer.parseInt(r.getString("dischargedDay")),Integer.parseInt(r.getString("totalDeaths")),Integer.parseInt(r.getString("totalDischarged")),Integer.parseInt(r.getString("activeCases")));
                temp.add(da);
            }
            System.out.println("Diplaying data regarding to "+args[1]);
            Data.printHeader();
            temp.forEach(System.out::println);
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieve_data_zone(String[] args)//displays data of all districts in a given zone
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists where zone=?";

        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(1,args[1]);
            ResultSet r=  stmt.executeQuery();
            
            if(!r.isBeforeFirst()) 
            {    
                System.out.println("Data not found"); 
                return;
            } 

            while(r.next())
            {
                Data da=new Data(r.getString("id"),r.getString("name"),r.getString("zone"),Integer.parseInt(r.getString("totalCases")) ,Integer.parseInt(r.getString("newCasesDay")),Integer.parseInt(r.getString("deathsDay")),Integer.parseInt(r.getString("dischargedDay")),Integer.parseInt(r.getString("totalDeaths")),Integer.parseInt(r.getString("totalDischarged")),Integer.parseInt(r.getString("activeCases")));
                temp.add(da);
            }
            System.out.println("Diplaying data regarding to "+args[1]);
            Data.printHeader();
            temp.forEach(System.out::println);
            System.out.println();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieve_data_tc(String[] args)//displays data of all districts with total cases greater than given total cases by user
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists where totalCases>?";

        System.out.println("Diplaying data where total cases are greater than "+args[1]);
        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(1,args[1]);
            ResultSet r=  stmt.executeQuery();

            if(!r.isBeforeFirst()) 
            {    
                System.out.println("Data not found"); 
                return;
            } 
            
            while(r.next())
            {
                Data da=new Data(r.getString("id"),r.getString("name"),r.getString("zone"),Integer.parseInt(r.getString("totalCases")) ,Integer.parseInt(r.getString("newCasesDay")),Integer.parseInt(r.getString("deathsDay")),Integer.parseInt(r.getString("dischargedDay")),Integer.parseInt(r.getString("totalDeaths")),Integer.parseInt(r.getString("totalDischarged")),Integer.parseInt(r.getString("activeCases")));
                temp.add(da);
            }
            Data.printHeader();
            temp.forEach(System.out::println);
            System.out.println();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void average_data(String[] args)//calculates average number of new cases emerging for a day in Telangana state
    {
        final String SQL="select avg(NewCasesDay) as average from TS_dists";

        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            ResultSet r =  stmt.executeQuery();
            if(!r.isBeforeFirst())
            {
                System.out.println("Table does not exist");
                return;
            }
            System.out.print("Average of newcases per day: ");
            while(r.next())
            {
                System.out.println(Math.round(Double.parseDouble(r.getString("average"))));
            }
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sumtcbyzone(String[] args)//displays total cases in a zone given by user
    {
        final String SQL="select zone,sum(totalCases) as totalcases from TS_dists group by zone";

        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            ResultSet r =  stmt.executeQuery();
            if(!r.isBeforeFirst())
            {
                System.out.println("Table does not exist");
                return;
            }
            while(r.next())
            {
                System.out.print(r.getString("zone"));
                System.out.print("\t");
                System.out.println(Double.parseDouble(r.getString("totalcases")));
            }
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
