public class J_Type_Instruction {
    private State state;
    private int opcode;
    private int rA;
    private int rB;

    public J_Type_Instruction(State s, int opcode, int rA, int rB) {
        state = s;
        this.opcode = opcode;
        this.rA = rA;
        this.rB = rB;
    }

    public void do_J_Type(){    //
        int[] reg = state.getRegister();
        if(opcode == 0b101) {
            reg[rB] = state.getPC()+1;
            state.countInstructionUp();
            state.setNextPC(reg[rA]);
            state.setRegister(reg);
        }
        else {
            System.out.println("Invalid Operation Code");
            System.exit(1);
        }
    }
}
