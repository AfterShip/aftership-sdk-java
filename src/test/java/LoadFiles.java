import com.afership.sdk.ConnectionAPI;
import com.afership.sdk.Tracking;
import com.aftership.sdk.enums.ISO3Country;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by User on 17/6/14.
 */
public class LoadFiles {

    public static void main( String args[])throws Exception{

      LoadFiles loadFiles = new LoadFiles();
    //    loadFiles.loadFile();





    }

    public void loadFile()throws IOException, JSONException{
        BufferedReader br =
                new BufferedReader( new InputStreamReader(new FileInputStream("../TrackingsToAdd.txt"), "UTF8"));
        ConnectionAPI connection  = new ConnectionAPI("a61d6204-6477-4f6d-93ec-86c4f872fb6b");
        try {
            String line;
            String [] elements;

            int added =0;
            Tracking newTracking;
            while ((line = br.readLine()) != null)
            {

//              slug, smses11, emails10, title5, customerName6, orderID7, orderIDPath8,
//              destinationCountryISO3 27
                elements=line.split("\t");
                // System.out.println("**long "+elements.length + "    number "+elements[2]);
                newTracking = new Tracking(elements[2]);
                newTracking.setSlug(elements[4]);
                if(5<elements.length && !elements[5].equals("")) newTracking.setTitle(elements[5]);
                if(6<elements.length && !elements[6].equals("")) newTracking.setCustomerName(elements[6]);
                if(7<elements.length &&!elements[7].equals("")) newTracking.setOrderID(elements[7]);
                if(8<elements.length &&!elements[8].equals("")) newTracking.setOrderIDPath(elements[8]);
                if(27<elements.length &&!elements[27].equals("")) newTracking.setDestinationCountryISO3(ISO3Country.valueOf(elements[27]));
                if(11<elements.length &&!elements[11].equals("")){
                    String smses[] = elements[11].split(",");
                    for(int i=0;i<smses.length;i++)
                        newTracking.addSmses(smses[i]);
                }
                if(10<elements.length &&!elements[10].equals("")){
                    String emails[] = elements[10].split(",");
                    for(int i=0;i<emails.length;i++)
                        newTracking.addEmails(emails[i]);
                }
                System.out.println(newTracking.generateJSON());
                try {
                    connection.postTracking(newTracking);
                    added++;
                    System.out.println("added " + added);
                }catch(Exception e){
                    System.out.println(e.getMessage()+"   number"+ elements[2]);
                }


            }
        } finally {
            br.close();
        }
    }
}

//order elements in file
//            System.out.println("created_at" +elements[0]+
//                    "\tupdated_at" +elements[1]+
//                    "\ttracking_number" +elements[2]+
//                    "\ttags" +elements[3]+
//                    "\tcourier" +elements[4]+
//                    "\ttitle" +elements[5]+
//                    "\tcustomer_name" +elements[6]+
//                    "\torder_id" +elements[7]+
//                    "\torder_id_path" +elements[8]+
//                    "\tsource" +elements[9]+
//                    "\temails" +elements[10]+
//                    "\tsmses" +elements[11]+
//                    "\tshipment_type" +elements[12]+
//                    "\tshipment_weight" +elements[13]+
//                    "\tshipment_package_count" +elements[14]+
//                    "\tshipment_pickup_date" +elements[15]+
//                    "\tshipment_scheduled_delivery_date" +elements[16]+
//                    "\tshipment_delivery_date" +elements[17]+
//                    "\tsigned_by" +elements[18]+
//                    "\torigin_address" +elements[19]+
//                    "\torigin_country_name" +elements[20]+
//                    "\torigin_country_iso3" +elements[21]+
//                    "\torigin_state" +elements[22]+
//                    "\torigin_city" +elements[23]+
//                    "\torigin_zip" +elements[24]+
//                    "\tdestination_address" +elements[25]+
//                    "\tdestination_country_name" +elements[26]+
//                    "\tdestination_country_iso3" +elements[27]+
//                    "\tdestination_state" +elements[28]+
//                    "\tdestination_city" +elements[29]+
//                    "\tdestination_zip");
