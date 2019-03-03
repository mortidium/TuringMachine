Transition.java
Typ
Java
Rozmiar
2 KB (1 917 bajtów)
Wykorzystane miejsce
2 KB (1 917 bajtów)
Lokalizacja
machine
Właściciel
ja
Zmodyfikowany
20 lip 2018 przeze mnie
Otwarty
20 lip 2018 przeze mnie
Utworzono
20 lip 2018 w aplikacji Google Drive Web
Dodaj opis
Przeglądający mogą pobierać

package machine;

public class Transition {
    private String q_from, q_to;
    private char before, after, step = '.';

    private String sanitize(String s){ return s.replace(" ", ""); }

    Transition(String s) throws BadInputException {  // q_from, before, after, step, q_to
        String delim = ",";
        String[] tokens = s.split(delim);
        try{
            q_from = sanitize(tokens[0]);
            before = sanitize(tokens[1]).charAt(0);
            after = sanitize(tokens[2]).charAt(0);
            char c = sanitize(tokens[3]).charAt((0));
            if( c == '>'|| c == '<' || c== '.') step = c;
            else throw new BadInputException();
            q_to = sanitize(tokens[4]);
        }catch (Exception e){
            throw new BadInputException();
        }
    }

    public void edit(String s) throws BadInputException{
        String delim = ",";
        String[] tokens = s.split(delim);
        try {
            q_from = sanitize(tokens[0]);
            before = sanitize(tokens[1]).charAt(0);
            after = sanitize(tokens[2]).charAt(0);
            char c = sanitize(tokens[3]).charAt((0));
            if( c == '>'|| c == '<' || c== '.') step = c;
            else throw new BadInputException();
            q_to = sanitize(tokens[4]);
        } catch (Exception e){
            throw new BadInputException();
        }
    }

    public String state(){ return q_from; }
    public String next_state(){ return q_to; }

    public int step(int i){
        if(step == '<') return i-1;
        if(step == '>') return i+1;
        return i;
    }
    public char current_symbol(){ return before; }
    public char replace_char(){ return after; }
    public void print(Printer pr){
        pr.ps("yellow", q_from+", "+before+", ");
        pr.ps("green", after+", ");
        pr.ps("cyan", step+", "+q_to);
    }

}
 
