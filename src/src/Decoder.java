import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Decoder {
    private State state;
    private String result;
    private int line_count;

    public Decoder(State state,String result,int line_count){
        this.state = state;
        this.result = result;
        this.line_count = line_count;
    }

    // Start Simulate a program
    public void simulate() throws IOException {
        int[] istMem = state.getInstructionMem();
        int command,opcode;
        File file = new File(result);
        FileWriter writer = new FileWriter(file);

        while(!state.getHaltFound()){ // while don't found end of program
            command = istMem[state.getPC()]; // get Instruction mem
            opcode = (command >> 22) & 0b111; // get opcode at bits 24-22

            int[] reg = state.getRegister();

            // Call printState before instruction executes
            printState();
            writer.write(writeState());

            if(opcode == 0b000 || opcode == 0b001){     // R-type
                int rA = (command >> 19) & 0b111; // get Address of rA
                int rB = (command >> 16) & 0b111; // get Address of rB
                int rD = (command & 0b111); // get Address of rD
                R_Type_Instruction r = new R_Type_Instruction(state,opcode,rA,rB,rD); // Call R_type_instruction
                r.do_RType();
            }
            else if(opcode == 0b010 || opcode == 0b011 ||opcode == 0b100) {     // I-type
                int rA = (command >> 19) & 0b111;
                int rB = (command >> 16) & 0b111;
                int offsetField = convertNum(command & 0xFFFF);
                I_Type_Instruction i = new I_Type_Instruction(state,opcode,rA,rB,offsetField);
                i.do_I_Type();
            }
            else if(opcode == 0b101){   // J-type
                int rA = (command >> 19) & 0b111;
                int rB = (command >> 16) & 0b111;
                J_Type_Instruction j = new J_Type_Instruction(state,opcode,rA,rB);
                j.do_J_Type();
            }
            else if(opcode == 0b110 || opcode == 0b111){    // O-type
                O_Type_Instruction o = new O_Type_Instruction(state,opcode);
                o.do_OType();
            }
            else{
                System.exit(1);
            }
        }
        System.out.println("machine halted");
        writer.write("machine halted\n");

        System.out.println("total of " + (state.getInstructionCount() + 1) + " instructions executed");
        writer.write("total of " + (state.getInstructionCount() + 1) + " instructions executed\n");

        System.out.println("final state of machine:\n");
        writer.write("final state of machine:\n\n");

        // Call printState before exit
        printState();
        writer.write(writeState());

        writer.close();

    }

    // Print state on Terminal
    public void printState(){
        System.out.println("@@@");
        System.out.println("state:");
        System.out.println("\tpc "+state.getPC());
        System.out.println("\tmemory:");
        int[] mem = state.getInstructionMem();
        for(int i=0;i<line_count;i++){
            if(i != 0){
                System.out.println("\t\tmem[ "+i+" ] "+mem[i]);
            }
        }
        System.out.println("\tregisters:");
        int[] reg = state.getRegister();
        for(int i=0;i<8;i++){
            System.out.println("\t\treg[ "+i+" ] "+reg[i]);
        }
        System.out.println("end state\n");
    }

    // Create a printState string
    public String writeState(){
        StringBuilder s = new StringBuilder();
        s.append("@@@\n").append("state:\n").append("\tpc ").append(state.getPC()).append("\n").append("\tmemory:\n");

        int[] mem = state.getInstructionMem();
        int i = 0;
        for(int j=0;j<line_count;j++){
            if( j != 0){
                s.append("\t\tmem[ ").append(i).append(" ] ").append(mem[j]).append("\n");
            }
            i++;
        }

        s.append("\tregisters:\n");

        int[] reg = state.getRegister();
        for(i=0;i<8;i++){
            s.append("\t\treg[ ").append(i).append(" ] ").append(reg[i]).append("\n");
        }

        s.append("end state\n\n");

        return s.toString();
    }

    // Convert 32 bits number to 16 bits
    private int convertNum(int num) {
        if ((num & (1 << 15)) != 0) {  // If the sign bit (bit 15) is set
            num -= (1 << 16);
        }
        return num;
    }

}