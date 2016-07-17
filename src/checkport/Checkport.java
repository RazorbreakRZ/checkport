/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkport;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Razorbreak
 */
public class Checkport {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length>0){
            String status;
            String varValue;
            String url = "http://www.canyouseeme.org/";
            Map<String,String> data;
            URLConection con = new URLConection();
            Analyzer ana = new Analyzer();
            for(int i=0;i<args.length;i++){
                varValue = args[i];
                if(varValue.matches("^[0-9]*$") && varValue.length()<6 && Integer.parseInt(varValue)<=65535){
                    Service ser = new Service(varValue);
                    ser.start();
                    status = String.format("%1$5s",varValue);
                    data = new HashMap<>();
                    data.put("port",varValue);
                    data.put("submit","OK");
                    if(con.doSubmit(url,data)==0){
                        int portStatus = ana.analyzeResponse(con.getResponse());//1=opened 0=closed -1=error
                        if(portStatus==1){
                            status += "...OK";
                        }else if(portStatus==0){
                            status += "...Error: puerto innacesible";
                        }
                        System.out.println(status);
                    }
                    ser.killService();
                }else{
                    System.out.println(String.format("%1$5s",varValue)+"...Error: puerto no valido");
                }
            }
            System.exit(0);
        }else{
            System.err.println("\nComprueba si un puerto esta abierto. Opcionalmente se le puede pasar una lista de puertos separados por un espacio:\n"
                            + "\n"
                            + "\t [java -jar] checkport.jar port [+port]\n"
                            + "\n"
                            +    "Creado por Dani Jimenez (razorbreak@gmail.com)"
                            + "\n");
            System.exit(1);
        }
    }
}
