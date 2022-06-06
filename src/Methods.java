import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import package_one.*;

interface recordMaintenance
{ 
    void tableformation();
    void add_data(String args[]);
    void delete_data(String args[]);
    void update_data(String args[]);
    void retrieve_data_id(String args[]);
    void retrieve_data_zone(String args[]);
    void retrieve_data_tc(String args[]);
    void display_data();
    void average_data(String args[]);
    void sumtcbyzone(String args[]);
    //void retrieve_data_sub_string(String args[]);
}

public class Methods implements recordMaintenance 
{
    Connection con = dbmsConnect.getConnection();

    // HELP PRINTER

    public static void printHelp()
    {
        System.out.format("\n%-11s -  Create a table and populate it\n","-create");
        System.out.format("%-11s -  Insert data [District ID, Name, Zone,... Active Cases]\n","-add");
        System.out.format("%-11s -  Delete data [District ID]\n","-del");
        System.out.format("%-11s -  Update data [District ID, Total Cases, New Cases,... Active Cases]\n","-upd");
        System.out.format("%-11s -  Display all data\n","-disp");
        System.out.format("%-11s -  Retreive data from ID [District ID]\n","-ret_id");
        System.out.format("%-11s -  Retreive data from Zone [Zone Code]\n","-ret_zone");
        System.out.format("%-11s -  Retreive data from Total cases [Total Cases]\n","-ret_tc");
        System.out.format("%-11s -  Calculate average cases emerging in a day\n\n","-avg");
        //System.out.format("%-11s -  etrieve data via substring [District name]\n\n","-ret_substr");
    }

    // TO CREATE TABLE

    private void createTable()
    {
        try 
        {
            final String newTable = "create table TS_dists(id varchar(5), name varchar(50), zone varchar(10), totalCases int, newCasesDay int, deathsDay int, dischargedDay int, totalDeaths int, totalDischarged int, activeCases int)";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(newTable);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    // TO LOAD DATA FROM TS_dists.csv INTO TABLE

    private void insertData(Data d)
    {
        try
        {
            final String fillTable="insert into TS_dists values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt=con.prepareStatement(fillTable);
            stmt.setString(1, d.get_distID());
            stmt.setString(2, d.get_name());
            stmt.setString(3, d.get_zone());
            stmt.setInt(4,d.get_totalCases());
            stmt.setInt(5,d.get_newCasesDay());
            stmt.setInt(6,d.get_deathsDay());
            stmt.setInt(7,d.get_dischargedDay());
            stmt.setInt(8,d.get_totalDeaths());
            stmt.setInt(9,d.get_totalDischarged());
            stmt.setInt(10,d.get_activeCases());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TABLE FORMATION

    @Override
    public void tableformation()
    {
        try {
            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader=new BufferedReader(new FileReader("C:\\Users\\Varshith\\Desktop\\Mini_Project\\src\\TS_dists.csv"));
            String line=reader.readLine();
            while((line=reader.readLine()) != null)
            {
                Data temp = new Data(line);
                insertData(temp);  
                System.out.printf("District %s inserted\n",temp.get_distID());               
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Table has been populated in database");
    }
    
    // TO INSERT NEW ROW
    @Override
    public void add_data(String[] args) 
    {
        final String SQL="insert into TS_dists values(?,?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(1,args[1]);
            stmt.setString(2,args[2]);
            stmt.setString(3,args[3]);
            stmt.setInt(4,Integer.parseInt(args[4]));
            stmt.setInt(5,Integer.parseInt(args[5]));
            stmt.setInt(6,Integer.parseInt(args[6]));
            stmt.setInt(7,Integer.parseInt(args[7]));
            stmt.setInt(8,Integer.parseInt(args[8]));
            stmt.setInt(9,Integer.parseInt(args[9]));
            stmt.setInt(10,Integer.parseInt(args[10]));
            stmt.executeUpdate();
            System.out.println("Row Created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TO DELETE DATA VIA ID

    @Override
    public void delete_data(String[] args) 
    {
        final String SQL="delete from TS_dists where id=?";
        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(1,args[1]);
            stmt.executeUpdate();
            System.out.println("Row Deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // TO UPDATE DATA VIA ID

    @Override
    public void update_data(String[] args)
    {
        final String SQL="update TS_dists set totalCases=?, newCasesDay=?, deathsDay=?, dischargedDay=?, totalDeaths=?,totalDischarged=?, activeCases=? where id=?";
        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            stmt.setString(8,(args[1]));
            stmt.setInt(1,Integer.parseInt(args[2]));
            stmt.setInt(2,Integer.parseInt(args[3]));
            stmt.setInt(3,Integer.parseInt(args[4]));
            stmt.setInt(4,Integer.parseInt(args[5]));
            stmt.setInt(5,Integer.parseInt(args[6]));
            stmt.setInt(6,Integer.parseInt(args[7]));
            stmt.setInt(7,Integer.parseInt(args[8]));
            stmt.executeUpdate();
            System.out.println("Row Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TO RETRIEVE DATA VIA ID

    @Override
    public void retrieve_data_id(String[] args) 
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
            System.out.println("Displaying data regarding to "+args[1]);
            Data.printHeader();
            temp.forEach(System.out::println);
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TO RETRIEVE DATA VIA ZONE

    @Override
    public void retrieve_data_zone(String[] args) 
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

    /* // TO RETRIEVE VIA PARTIAL STRING

    @Override
    public void retrieve_data_sub_string(String[] args) 
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists where name like ?" ;

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
    } */


    // TO RETRIEVE DATA VIA TOTAL CASES

    @Override
    public void retrieve_data_tc(String[] args) 
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists where totalCases>?";

        System.out.println("Diplaying data where total cases = "+args[1]);
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

    // TO DISPLAY THE WHOLE TABLE

    @Override
    public void display_data() 
    {
        List<Data> temp=new ArrayList<>();
        
        final String SQL="select * from TS_dists";

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
                Data da=new Data(r.getString("id"),r.getString("name"),r.getString("zone"),Integer.parseInt(r.getString("totalCases")) ,Integer.parseInt(r.getString("newCasesDay")),Integer.parseInt(r.getString("deathsDay")),Integer.parseInt(r.getString("dischargedDay")),Integer.parseInt(r.getString("totalDeaths")),Integer.parseInt(r.getString("totalDischarged")),Integer.parseInt(r.getString("activeCases")));
                temp.add(da);
            }
            System.out.println("Diplaying data  ");
            Data.printHeader();
            temp.forEach(System.out::println);
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TO FIND AVERAGE OF DIFF CASES

    @Override
    public void average_data(String[] args) 
    {
        final String SQL="select avg(newcasesday) as average from TS_dists";

        try(PreparedStatement stmt=con.prepareStatement(SQL))
        {
            ResultSet r =  stmt.executeQuery();
            if(!r.isBeforeFirst())
            {
                System.out.println("Table does not exist");
                return;
            }
            System.out.print("Average of new cases per day: ");
            while(r.next())
            {
                System.out.println(Double.parseDouble(r.getString("average")));
            }
            System.out.println();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // SUM OF TOTAL CASES GROUPED BY ZONE

    @Override
    public void sumtcbyzone(String[] args) {
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
