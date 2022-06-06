package package_one;

public class Data {

    private String distID;
    private String name;
    private String zone;
    private int totalCases;
    private int newCasesDay;
    private int deathsDay;
    private int dischargedDay;
    private int totalDeaths;
    private int totalDischarged;
    private int activeCases;

    public Data(){}

    public Data(String line)
    {
        try{
            String v[]=line.split(",");
            
            this.distID=v[0];
            this.name=v[1];
            this.zone=v[2];
            this.totalCases=Integer.valueOf(v[3]);
            this.newCasesDay=Integer.valueOf(v[4]);
            this.deathsDay=Integer.valueOf(v[5]);
            this.dischargedDay=Integer.valueOf(v[6]);
            this.totalDeaths=Integer.valueOf(v[7]);
            this.totalDischarged=Integer.valueOf(v[8]);
            this.activeCases=Integer.valueOf(v[9]);
        } catch (NumberFormatException e) {
           e.printStackTrace();
        }
    }

    public String get_distID() {
        return this.distID;
    }

    public void set_distID(String distID) {
        this.distID = distID;
    }

    public String get_name() {
        return this.name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public String get_zone() {
        return this.zone;
    }

    public void set_zone(String zone) {
        this.zone = zone;
    }

    public int get_totalCases() {
        return this.totalCases;
    }

    public void set_totalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int get_newCasesDay() {
        return this.newCasesDay;
    }

    public void set_newCasesDay(int newCasesDay) {
        this.newCasesDay = newCasesDay;
    }

    public int get_deathsDay() {
        return this.deathsDay;
    }

    public void set_deathsDay(int deathsDay) {
        this.deathsDay = deathsDay;
    }

    public int get_dischargedDay() {
        return this.dischargedDay;
    }

    public void set_dischargedDay(int dischargedDay) {
        this.dischargedDay = dischargedDay;
    }

    public int get_totalDeaths() {
        return this.totalDeaths;
    }

    public void set_totalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int get_totalDischarged() {
        return this.totalDischarged;
    }

    public void set_totalDischarged(int totalDischarged) {
        this.totalDischarged = totalDischarged;
    }

    public int get_activeCases() {
        return this.activeCases;
    }

    public void set_activeCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public Data(String distID, String name, String zone, int totalCases, int newCasesDay, int deathsDay,
            int dischargedDay, int totalDeaths, int totalDischarged, int activeCases) {
        this.distID = distID;
        this.name = name;
        this.zone = zone;
        this.totalCases = totalCases;
        this.newCasesDay = newCasesDay;
        this.deathsDay = deathsDay;
        this.dischargedDay = dischargedDay;
        this.totalDeaths = totalDeaths;
        this.totalDischarged = totalDischarged;
        this.activeCases = activeCases;
    }

    public static void printHeader(){
        System.out.println(String.format("%-8s","distID")+String.format("%-26s","name")+String.format("%-10s","zone")+String.format("%-12s","totalCases")+String.format("%-13s","newCasesDay")+String.format("%-11s","deathsDay")+String.format("%-15s","dischargedDay")+String.format("%-13s","totalDeaths")+String.format("%-17s","totalDischarged")+String.format("%-13s\n","activeCases"));
    }

    @Override
    public String toString() {
        return String.format("%-8s",this.distID) +String.format("%-26s",this.name)+String.format("%-10s",this.zone)+String.format("%-12s",this.totalCases)+String.format("%-13s",this.newCasesDay)+String.format("%-11s",this.deathsDay)+String.format("%-15s",this.dischargedDay)+String.format("%-13s",this.totalDeaths)+String.format("%-17s",this.totalDischarged)+String.format("%-13s\n",this.activeCases);
    }
}