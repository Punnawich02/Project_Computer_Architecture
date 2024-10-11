public class I_Type_Instruction {
    private State state;
    private int opcode;
    private int rA;
    private int rB;
    private final int offsetField;

    public I_Type_Instruction(State s, int opcode, int rA, int rB, int offsetField) {
        state = s;
        this.opcode = opcode;
        this.rA = rA;
        this.rB = rB;
        this.offsetField = offsetField;
    }

    public void do_I_Type(){
        int[] reg = state.getRegister();
        int[] mem = state.getInstructionMem();

        if(opcode == 0b010){    // lw command
            reg[rB] = mem[reg[rA]+offsetField];
            state.countInstructionUp();
            state.setRegister(reg);
            state.setNextPC(state.getPC()+1);
        }
        else if(opcode == 0b011){   // sw command
            mem[reg[rA]+offsetField] = reg[rB];
            state.countInstructionUp();
            state.setInstructionMem(mem);
            state.setNextPC(state.getPC()+1);
        }
        else if(opcode == 0b100){ // beq command
            if(reg[rA] == reg[rB]){
                state.setNextPC(state.getPC()+1+offsetField);
            }
            else{
                state.setNextPC(state.getPC()+1);
            }
            state.countInstructionUp();
        }
        else{
            System.out.println("Invalid Operation Code");
            System.exit(1);
        }
        state.setRegister(reg);
    }
}
