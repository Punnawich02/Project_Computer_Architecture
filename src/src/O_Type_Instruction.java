public class O_Type_Instruction {
    private State state;
    private int opcode;

    public O_Type_Instruction(State s ,int opcode){
        state = s;
        this.opcode = opcode;
    }

    public void do_OType(){
        if(opcode == 0b110){    // Found halt
            state.setNextPC(state.getPC()+1);   // set pc = pc+1
            state.setHaltFound();   // make a program know that found halt
        }
        else if(opcode == 0b111){ // noop: Don't do anything
            state.setNextPC(state.getPC()+1);   // set pc = pc+1
        }
        else{
            System.out.println("Invalid Operation Code");
            System.exit(1);
        }
    }
}
