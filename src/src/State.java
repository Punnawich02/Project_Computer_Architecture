public class State {
    private int[] instructionMem;
    private int[] register;
    private int PC;
    private int instructionCount;
    private boolean haltFound;

    public State(){
        instructionMem = new int[65536];
        register = new int[8];
        PC = 0;
        instructionCount = 0;
        haltFound = false;
    }

    public int getLength(){
        return instructionMem.length;
    }

    public int[] getInstructionMem(){
        return instructionMem;
    }
    public int[] getRegister(){
        return register;
    }
    public int getPC(){
        return PC;
    }
    public int getInstructionCount(){
        return instructionCount;
    }
    public boolean getHaltFound(){
        return haltFound;
    }

    public void setInstructionMem(int[] insMem){
        instructionMem = insMem;
    }
    public void setRegister(int[] reg){
        register = reg;
    }
    public void setNextPC(int new_pc){
        PC = new_pc;
    }
    public void countInstructionUp(){
        instructionCount += 1;
    }
    public void setHaltFound(){
        haltFound = true;
    }
}
