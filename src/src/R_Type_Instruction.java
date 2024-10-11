public class R_Type_Instruction {
    State state;
    int opcode;
    int rA;
    int rB;
    int rD;
    public R_Type_Instruction(State s,int opcode,int rs,int rt,int rd){
        this.state = s;
        this.opcode = opcode;
        rA = rs;
        rB = rt;
        rD = rd;
    }

    public void do_RType(){
        int[] reg = state.getRegister();
        if(opcode == 0b000){   // ADD Command
            reg[rD] = reg[rA] + reg[rB];
            state.countInstructionUp();
            state.setNextPC(state.getPC()+1);
            state.setRegister(reg); // set new register value
        }
        else if(opcode == 0b001){   // NAND Command
            reg[rD] = ~(reg[rA] & reg[rB]);
            state.countInstructionUp();
            state.setNextPC(state.getPC()+1);
            state.setRegister(reg); // set new register value
        }
        else{
            System.out.println("Invalid Operation Code");
            System.exit(1);
        }
    }

}
