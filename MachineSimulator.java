package machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MachineSimulator {
    Machine tm;
    private boolean finish = false;

    private enum Command{ ADD, REMOVE, EDIT, START, STOP, STEP, WAKE, PRINT, END, HELP }

    private Command parse_command(String line){
        String command = line;
        if(command.contains(" ")){
            command = command.substring(0, command.indexOf(" "));
        }
        command = command.toUpperCase();

        if(command.contains("ADD")) return Command.ADD;
        if(command.contains("REMOVE")) return Command.REMOVE;
        if(command.contains("RM")) return Command.REMOVE;
        if(command.contains("START")) return Command.START;
        if(command.contains("BEGIN")) return Command.START;
        if(command.contains("RUN")) return Command.START;
        if(command.contains("STOP")) return Command.STOP;
        if(command.contains("HALT")) return Command.STOP;
        if(line.isEmpty()) return Command.STEP;
        if(command.contains("STEP")) return Command.STEP;
        if(command.contains("NEXT")) return Command.STEP;
        if(command.contains("WAKE")) return Command.WAKE;
        if(command.contains("RETRY")) return Command.ADD;
        if(command.contains("STATES")) return Command.PRINT;
        if(command.contains("PRINT")) return Command.PRINT;
        if(command.contains("HELP")) return Command.HELP;
        if(command.contains("H")) return Command.HELP;
        if(command.contains("END")) return Command.END;
        if(command.contains("EXIT")) return Command.END;
        if(command.contains("EDIT")) return Command.EDIT;
        return Command.HELP;
    }

    private void parse_input(String line, Printer pr){
        Command c = parse_command(line);
        if(c == Command.ADD){
            String args = line.substring(line.indexOf(" ")+1, line.length());
            tm.add_transition(args);
        }
        if(c == Command.REMOVE){
            try{
                String args = line.substring(line.indexOf(" ")+1, line.length());
                tm.remove_transition(args);
            }catch (Exception e){
                return;
            }
        }
        if(c == Command.START){
            if(line.contains(" ")){
                String args = line.substring(line.indexOf(" ")+1, line.length());
                if(args.isEmpty()) pr.psl("red", "No input word given");
                else {
                    if(args.contains(",")){
                        String state = line.substring(line.indexOf(" ")+1, line.length()).replace(" ", "");
                        String word = line.substring(line. indexOf(","), line.length()). replace(" ", "");
                        tm.start(state, word);
                    }
                    else tm.start(args);
                }
            }
        }
        if(c == Command.STOP){
            tm.stop();
        }
        if(c == Command.STEP){
            if(line.contains(" ")){
                String args = line.substring(line.indexOf(" ")+1, line.length());
                int k = Integer.parseInt(args);
                tm.step(k);
            } else{
                tm.step();
            }
        }
        if(c == Command.WAKE){
            tm.retry();
        }
        if(c == Command.PRINT){
            tm.print_machine();
        }
        if(c == Command.END) finish = true;
        if(c == Command.HELP) {
            pr.psl("yellow","Commands to use:");
            pr.psl("Print all transitions: PRINT (also STATES)");
            pr.psl("Add transition:        ADD state, symbol_from, symbol_to, move, next_state");
            pr.psl("Remove transition:     RM state, symbol_from [, symbol_to, move, next_state] (also REMOVE)");
          //  pr.psl("Editing: EDIT state, symbol_from, new_state, new_symbol_from, new_symbol_to, new_move, new_next_state");
            pr.psl("Start simulation:      START input_word - with default starting_state \"START\" (also RUN or BEGIN)");
            pr.psl("                       START state, input_word");
            pr.psl("Stop simulation:       STOP (also HALT)");
            pr.psl("Next state:            press enter (also STEP or NEXT)");
            pr.psl("Next k states:         STEP k (also NEXT)");
            pr.psl("Wake after suspension: WAKE (also RETRY)");
            pr.psl("Help:                  HELP");
            pr.psl("Quit:                  END (also EXIT)");
        }
    }

    public void simulate(){
        Printer pr = new Printer();
        pr.psl("white", "Let's start simulating!");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(!finish){
            String s;
            try {
                s = br.readLine();
                parse_input(s, pr);
            } catch (IOException e) {
                finish = true;
            }
        }
        pr.psl("white", "Bye!");
    }

    public static void main(String[] args) {
        MachineSimulator ms = new MachineSimulator();
        ms.tm = new NDTuringMachine();
        ms.simulate();
    }
}
