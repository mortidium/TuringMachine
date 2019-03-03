 NDTuringMachine.java
Typ
Java
Rozmiar
5 KB (5 344 bajty)
Wykorzystane miejsce
5 KB (5 344 bajty)
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

import java.util.*;

public class NDTuringMachine implements Machine {
    private Printer pr = new Printer();
    private HashMap<String, HashMap<Character, HashSet<Transition>>> transitions = new HashMap<>();
    Random r = new Random();

    private static final char BLANK = '_', ANY = '*';
    private static final String START_STATE = "START", END_STATE = "FINISH", SUCC = "END_SUCC";
    private boolean running = false, suspended = false;
    private String word, state;
    private int idx;

    private Transition get_transition(String state, char c){
        if(transitions.containsKey(state) &&
                (transitions.get(state).containsKey(c) || transitions.get(state).containsKey(ANY))){
            if(!transitions.get(state).containsKey(c)) c = ANY;
            ArrayList<Transition> tr = new ArrayList<>(this.transitions.get(state).get(c));
            Transition t = tr.get(r.nextInt(tr.size()));
            StringBuilder new_word = new StringBuilder(word);
            new_word.setCharAt(idx, t.replace_char());
            if(t.replace_char() == ANY) new_word.setCharAt(idx, t.current_symbol());
            if(t.current_symbol() == ANY && t.replace_char() == ANY) new_word = new StringBuilder(word);
            word = new_word.toString();
            idx = t.step(idx);
            if(idx == word.length()) word+=BLANK;
            this.state = t.next_state();
            if(this.state.equals(END_STATE)) stop();
            if(this.state.equals(SUCC)) stop();
            return t;
        }
        return null;
    }

    private void print_situation(){
        pr.ps("green", state+" :: ");
        if(idx>0) pr.ps("blue", word.substring(0,idx));
        pr.pc("yellow", word.charAt(idx));
        pr.ps("blue", word.substring(idx+1, word.length()));
        pr.pl();
    }

    public void print_machine(){
        for(String state: transitions.keySet())
            for(HashSet<Transition> h : transitions.get(state).values())
                for(Transition t : h) {
                    t.print(pr);
                    pr.pl();
                }
    }

    public void add_transition(String s) {
        Transition t;
        try{
            t = new Transition(s);
        } catch (BadInputException e){
            pr.psl("red", "Bad input: \""+s+"\"");
            return;
        }
        if(!transitions.containsKey(t.state())) transitions.put(t.state(), new HashMap<>());
        if(!transitions.get(t.state()).containsKey(t.current_symbol()))
            transitions.get(t.state()).put(t.current_symbol(), new HashSet<>());
        transitions.get(t.state()).get(t.current_symbol()).add(t);
    }

    public void remove_transition(String s) {
        Transition t;
        try{
            t = new Transition(s);
        } catch (BadInputException e){
            pr.psl("red", "Bad input: \""+s+"\"");
            return;
        }
        if(transitions.containsKey(t.state())){
            if(transitions.get(t.state()).containsKey(t.current_symbol())){
                transitions.get(t.state()).get(t.current_symbol()).remove(t);
                if(transitions.get(t.state()).get(t.current_symbol()).isEmpty())
                    transitions.get(t.state()).remove(t.current_symbol());
                if(transitions.get(t.state()).isEmpty())
                    transitions.remove(t.state());
            }
        }

            transitions.put(t.state(), new HashMap<>());
        if(!transitions.get(t.state()).containsKey(t.current_symbol()))
            transitions.get(t.state()).put(t.current_symbol(), new HashSet<>());
        transitions.get(t.state()).get(t.current_symbol()).add(t);
    }

    public void start(String input){
        state = START_STATE;
        word = input;
        idx = 0;
        suspended = false;
        running = true;
    }

    public void start(String input, String start_state) {
        state = start_state;
        word = input;
        idx = 0;
        suspended = false;
        running = true;
    }

    public void stop() {
        if(running){
            running = false;
            pr.psl("white", "Stopping machine");
            pr.psl("white", "Final state of the machine: ");
            if(suspended) pr.psl("purple", "Suspended");
            if(state.equals(SUCC)) pr.psl("green", "Success");
            print_situation();
        }
    }

    public void step() {
        if(running){
            if(!suspended){
                char c = word.charAt(idx);
                Transition t = get_transition(state, c);
                if(t == null){
                    suspended = true;
                    pr.psl("purple", "Suspending");
                }
            }
            else{
                pr.psl("purple", "Suspended, don't know how to make a step");
            }
            if(running) print_situation();
        } else {
            pr.psl("red", "The machine is not running");
        }
    }

    public void retry(){
        if(running){
            suspended = false;
            pr.psl("purple", "Waking up");
        }
    }

    public void step(int steps) {
        for(int i = 0; i < steps; i++){
            step();
        }
    }

}
