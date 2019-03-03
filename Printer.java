package machine;

import java.util.Collection;
import java.util.HashMap;

class Printer {
    private HashMap<String, String> p = new HashMap<>();
    Printer(){
        p.put("white", "\u001B[30m");
        p.put("red", "\u001B[31m");
        p.put("green", "\u001B[32m");
        p.put("yellow", "\u001B[33m");
        p.put("blue", "\u001B[34m");
        p.put("purple", "\u001B[35m");
        p.put("cyan", "\u001B[36m");
        p.put("reset", "\u001B[0m");
    }

    // print strings
    void ps(String color, String text){ System.out.print(p.get(color) + text + p.get("reset")); }
    void ps(String text){ System.out.print(p.get("reset") + text); }
    void psl(String color, String text){ System.out.println(p.get(color) + text + p.get("reset")); }
    void psl(String text){ System.out.println(p.get("reset") + text);}
    void plsl(String color, String text){ System.out.println("\n"+ p.get(color) + text + p.get("reset")); }
    void plsl(String text){ System.out.println("\n"+ p.get("reset") + text); }

    // print chars
    void pc(String color, char text){ System.out.print(p.get(color) + text + p.get("reset")); }
    void pcl(String color, char text){ System.out.println(p.get(color) + text + p.get("reset")); }
    void plcl(String color, char text){ System.out.println("\n"+ p.get(color) + text + p.get("reset")); }

    // print newline or "DEBUG"
    void pl(){ System.out.println(); }
    void pd(){ psl("red", "DEBUG");}

    // print collection
    void pC(String color, Collection collection){
        System.out.print(p.get(color));
        if(collection.size() == 0){
            System.out.println("Collection is empty"+ p.get("reset"));
        }
        else {
            for(Object o: collection){
                System.out.print(o.toString()+", ");
            }
            System.out.println(p.get("reset"));
        }
    }
    void pCl(String color, Collection collection){
        System.out.print(p.get(color));
        if(collection.size() == 0){
            System.out.println("Collection is empty"+ p.get("reset"));
        }
        else {
            for(Object o: collection){
                System.out.println(o.toString()+", ");
            }
            System.out.println(p.get("reset"));
        }

    }
}
