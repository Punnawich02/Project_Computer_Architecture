public class Convert {

    // Convert R Type instruction to Machine Code
    public String RType_to_MachineCode(String opcode ,String rs ,String rt ,String rd){
        int opcodes = opcode.equals("add") ? 0:1 ;
        int rA = Integer.parseInt(rs);
        int rB = Integer.parseInt(rt);
        int rD = Integer.parseInt(rd);

        int instruction = (opcodes << 22) | (rA << 19) | (rB << 16) | rD;

        return instruction+"";
    }

    // Convert I Type instruction to Machine Code
    public String IType_to_MachineCode(String opcode ,String rs ,String rt ,String offsetField){
        int opcodes = 0;
        if(opcode.equals("lw")){
            opcodes = 0b010;
        } else if (opcode.equals("sw")) {
            opcodes = 0b011;
        } else if (opcode.equals("beq")) {
            opcodes = 0b100;
        }
        int rA = Integer.parseInt(rs);
        int rB = Integer.parseInt(rt);
        int offSet = Integer.parseInt(offsetField);

        if(offSet < 0){
            offSet = (1 << 16) + offSet;
        }

        int instruction = (opcodes << 22) | (rA << 19) | (rB << 16) | offSet;

        return instruction+"";
    }

    // Convert J type instruction to Machine Code
    public String JType_to_MachineCode(String opcode ,String rs ,String rt){
        int opcodes = 0b101;
        int rA = Integer.parseInt(rs);
        int rB = Integer.parseInt(rt);

        int instruction = (opcodes << 22) | (rA << 19) | (rB << 16);

        return instruction+"";
    }

    // Convert O Type to Machine Code
    public String OType_to_MachineCode(String opcode){
        int opcodes = opcode.equals("halt") ? 0b110:0b111;

        int instruction = (opcodes << 22);

        return instruction+"";
    }

    // Convert .fill to Machine Code
    public String dot_fill(String value){
        return value;
    }
}