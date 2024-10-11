import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Assembler {

    private Map<String, Integer> map = new HashMap<>();
    private final String instruction_file_name;
    private final String MachineCode_file_name;
    private final String result_file_name;

    public Assembler(String instruction_file_name, String MachineCode_file_name, String resultFileName) {
        this.instruction_file_name = instruction_file_name;
        this.MachineCode_file_name = MachineCode_file_name;
        result_file_name = resultFileName;
    }

    // Convert instruction to machine code and simulate
    public void ConvertAndSim() {
        try (FileReader fileReader = new FileReader(instruction_file_name);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            String[] instruction_line;
            List<String[]> list_of_instruction = new LinkedList<>();

            // Cut a string and filter
            int a=0;
            while ((line = bufferedReader.readLine()) != null) {
                instruction_line = line.split("\\s+");

                // Ensure proper instruction format before proceeding
                if (instruction_line.length < 2) {
                    System.out.println("Invalid instruction: " + line);
                    System.exit(1);
                }

                String[] instruction_line1 = new String[5];
                String label = instruction_line[0];


                String opcode = instruction_line[1];
                String field1 = "", field2 = "", field3 = "";

                if (opcode.equals("add") || opcode.equals("nand")) {
                    if(instruction_line.length < 5){
                        System.out.println("Invalid field: Must have 3 field in add or nand");
                        System.out.println("At line: "+(a+1));
                        System.exit(1);
                    }
                    field1 = instruction_line[2];
                    field2 = instruction_line[3];
                    field3 = instruction_line[4];
                } else if (opcode.equals("lw") || opcode.equals("sw") || opcode.equals("beq")) {
                    if(instruction_line.length < 5){
                        System.out.println("Invalid field: Must have 3 field in lw ,sw or beq");
                        System.out.println("At line: "+(a+1));
                        System.exit(1);
                    }
                    field1 = instruction_line[2];
                    field2 = instruction_line[3];
                    field3 = instruction_line[4];
                } else if (opcode.equals("jalr")) {
                    if(instruction_line.length < 4){
                        System.out.println("Invalid field: Must have 2 field in jalr");
                        System.out.println("At line: "+(a+1));
                        System.exit(1);
                    }
                    field1 = instruction_line[2];
                    field2 = instruction_line[3];
                } else if (opcode.equals("halt") || opcode.equals("noop")) {
                    if(instruction_line.length < 2){
                        System.out.println("Invalid field: Don't have any field in halt or noop");
                        System.out.println("At line: "+(a+1));
                        System.exit(1);
                    }
                } else if (opcode.equals(".fill")) {
                    if(instruction_line.length != 3){
                        System.out.println("Invalid field: Must have 1 field in .fill");
                        System.out.println("At line: "+(a+1));
                        System.exit(1);
                    }
                    field1 = instruction_line[2];
                }

                instruction_line1[0] = label;
                instruction_line1[1] = opcode;
                instruction_line1[2] = field1;
                instruction_line1[3] = field2;
                instruction_line1[4] = field3;

                list_of_instruction.add(instruction_line1);
                a++;
            }

            // Map symbolic and there address
            for (int i = 0; i < list_of_instruction.size(); i++) {
                if(map.containsKey(list_of_instruction.get(i)[0])){
                    System.out.println("Invalid Label: Duplicate labels detected in the program.");
                    System.out.println("At line: "+(i+1));
                    System.exit(1);
                }

                if (!list_of_instruction.get(i)[0].isBlank()) {
                    map.put(list_of_instruction.get(i)[0], i);
                }
            }

            Convert c = new Convert();
            String label = "", opcode = "";
            String field1 = "", field2 = "", field3 = "";
            List<String> list_of_MachineCode = new LinkedList<>(); // Used to keep machine code

            // Convert to Machine Code
            for (int i = 0; i < list_of_instruction.size(); i++) {
                label = list_of_instruction.get(i)[0];
                opcode = list_of_instruction.get(i)[1];
                field1 = list_of_instruction.get(i)[2];
                field2 = list_of_instruction.get(i)[3];
                field3 = list_of_instruction.get(i)[4];



                // Check Label
                checkLabel(label,i);

                if (opcode.equals("add") || opcode.equals("nand")) {
                    if (!isNumber(field1) || !isNumber(field2) || !isNumber(field3)) {
                        System.out.println("Invalid Input: The field must contain a valid register number.");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) > 7 || Integer.parseInt(field2) > 7 || Integer.parseInt(field3) > 7){
                        System.out.println("Invalid Input: Register field must less than 7");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) < 0 || Integer.parseInt(field2) < 0 || Integer.parseInt(field3) < 0){
                        System.out.println("Invalid Input: Register field must more than 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if(field3.equals("0")){
                        System.out.println("Invalid Output: Can not change value at Register 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    list_of_MachineCode.add(c.RType_to_MachineCode(opcode, field1, field2, field3));
                } else if (opcode.equals("lw") || opcode.equals("sw")) {
                    if (!isNumber(field1) || !isNumber(field2)) {
                        System.out.println("Invalid Input: The field must contain a valid register number.");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) > 7 || Integer.parseInt(field2) > 7){
                        System.out.println("Invalid Input: Register field must less than 7");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) < 0 || Integer.parseInt(field2) < 0){
                        System.out.println("Invalid Input: Register field must more than 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if(field2.equals("0") && opcode.equals("lw")){
                        System.out.println("Invalid Output: Can not change value at Register 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (!isNumber(field3)) {
                        if(map.get(field3) == null){
                            System.out.println("Invalid Input: Undefined symbolic address detected.");
                            System.out.println("At line: "+(i+1));
                            System.exit(1);
                        }
                        else {
                            field3 = map.get(field3).toString();
                        }
                    }
                    list_of_MachineCode.add(c.IType_to_MachineCode(opcode, field1, field2, field3));
                } else if (opcode.equals("beq")) {
                    if (!isNumber(field1) || !isNumber(field2)) {
                        System.out.println("Invalid Input: The field must contain a valid register number.");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) > 7 || Integer.parseInt(field2) > 7){
                        System.out.println("Invalid Input: Register field must less than 7");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) < 0 || Integer.parseInt(field2) < 0){
                        System.out.println("Invalid Input: Register field must more than 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (!isNumber(field3)) {
                        if(map.get(field3) == null){
                            System.out.println("Invalid Input: Undefined symbolic address detected.");
                            System.out.println("At line: "+(i+1));
                            System.exit(1);
                        }
                        else {
                            int f3 = map.get(field3);
                            field3 = String.valueOf(f3 - i - 1);
                        }
                    }
                    list_of_MachineCode.add(c.IType_to_MachineCode(opcode, field1, field2, field3));
                } else if (opcode.equals("jalr")) {
                    if (!isNumber(field1) || !isNumber(field2)) {
                        System.out.println("Invalid Input: The field must contain a valid register number.");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) > 7 || Integer.parseInt(field2) > 7){
                        System.out.println("Invalid Input: Register field must less than 7");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    if (Integer.parseInt(field1) < 0 || Integer.parseInt(field2) < 0){
                        System.out.println("Invalid Input: Register field must more than 0");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }

                    list_of_MachineCode.add(c.JType_to_MachineCode(opcode, field1, field2));
                } else if (opcode.equals("halt") || opcode.equals("noop")) {
                    list_of_MachineCode.add(c.OType_to_MachineCode(opcode));
                } else if (opcode.equals(".fill")) {
                    if (!isNumber(field1)) {
                        if(map.get(field1) == null){
                            System.out.println("Invalid Field: Don't know symbolic address");
                            System.out.println("At line: "+(i+1));
                            System.exit(1);
                        }
                        field1 = String.valueOf(map.get(field1));
                    } else if(Integer.parseInt(field1) < -32768 || Integer.parseInt(field1) > 32767){
                        System.out.println("Invalid Offset Field: The offset exceeds the 16-bit limit.");
                        System.out.println("At line: "+(i+1));
                        System.exit(1);
                    }
                    list_of_MachineCode.add(field1);
                } else {
                    System.out.println("Invalid Opcode: The specified opcode is not recognized.");
                    System.out.println("At line: "+(i+1));
                    System.exit(1);
                }
            }

            // Write machine code to file
            WriteAFile(list_of_MachineCode,MachineCode_file_name);

            // Start simulation
            Simulator s = new Simulator(MachineCode_file_name,result_file_name);
            s.simulate();

        } catch (IOException e){
            System.out.println("Error: File not found");
            System.exit(1);
        }
    }

    // Write a machine code to file
    public boolean WriteAFile(List<String> s,String instruction_file_name){
        try {
            File file = new File(instruction_file_name);
            FileWriter writer = new FileWriter(file);
            for (int i=0;i<s.size();i++){
                writer.write(s.get(i)+"\n");
            }
            writer.close();
            System.out.println("Successfully wrote to the file at "+MachineCode_file_name+"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    // check that filed was number ?
    public boolean isNumber(String a) {
        try {
            int i = Integer.parseInt(a);
            return Integer.toString(i).equals(a);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void checkLabel(String label,int i){
        if( !label.isBlank() ){

            // Check label: label must less or equal than 6 char
            if(label.length() > 6){
                System.out.println("Invalid Label: Label must less or equal than 6 character");
                System.out.println("At line: "+(i+1));
                System.exit(1);
            }

            // Check label: label must start with character
            char c = label.charAt(0);
            if(Character.isDigit(c)){
                System.out.println("Invalid Label: Label must start with character");
                System.out.println("At line: "+(i+1));
                System.exit(1);
            }

        }
    }
}
